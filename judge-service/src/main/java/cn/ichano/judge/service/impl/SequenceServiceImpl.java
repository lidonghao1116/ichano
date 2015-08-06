package cn.ichano.judge.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


import cn.ichano.common.service.SequenceService;
import cn.ichano.judge.dao.SequenceDao;
import cn.ichano.judge.entity.Sequence;
import cn.ichano.judge.entity.SequenceVo;

public class SequenceServiceImpl implements SequenceService {

	private static Logger LOGGER = LoggerFactory
			.getLogger(SequenceServiceImpl.class);

	@Value("${server.id}")
	private int serverId;

	@Autowired
	private SequenceDao sequenceDao;

	@Autowired
	private ThreadPoolTaskExecutor threadPool;

	/**
	 * 用于返回序列
	 */
	Map<String, SequenceVo> sequenceCurrent = new ConcurrentHashMap<String, SequenceVo>();

	@PostConstruct
	private void init() {
		try {
			initData();
		} catch (Exception e) {
			LOGGER.error("init error:", e);
		}
	}

	/**
	 * 初始化
	 */
	private void initData() {
		// serverId = Env.getConfig().getInteger("common.shard.server.id", 1);

		List<Sequence> sequenceList = sequenceDao.querySequenceList(serverId);

		LOGGER.info("init all sequence, size:{}", sequenceList.size());

		for (Sequence sequence : sequenceList) {
			initSingle(sequence);
		}

	}
	
	
	@Override
	public Long getNext(String entity) {
		return getNext(serverId, entity);
	}

	public Long getNext(int serverId, final String entity) {

		SequenceVo sequenceVo = getSequenceVo(serverId, entity);
		AtomicLong maxAtomci = sequenceVo.getMax();
		long current = sequenceVo.getCurrent().get();
		long max = maxAtomci.get();

		int batch = sequenceVo.getBatch();
		long range = max - current;
		if (range < (batch / 5)) {
			asyncUpdateMax(sequenceVo);
		}

		// 不足一个序列，需要强制等待取序列结果
		while ((maxAtomci.get() - current) < sequenceVo.getDiv()) {
			updateMax(sequenceVo);
			// 休眠等待
			if ((maxAtomci.get() - current) < sequenceVo.getDiv()) {
				sleep(10);
			}
		}

		return sequenceVo.getCurrent().addAndGet(sequenceVo.getDiv());
	}

	/**
	 * 初始化处理sequence对象
	 * @param sequence
	 */
	private void initSingle(Sequence sequence) {
		SequenceVo sequenceVo = converSingle(sequence);
		asyncUpdateMax(sequenceVo);
		sequenceCurrent.put(sequenceVo.getEntity(), sequenceVo);
	}

	/**
	 * 异步更新序列最大值
	 * 
	 * @param vo
	 */
	private void asyncUpdateMax(final SequenceVo vo) {
		ReentrantLock lock = vo.getReentrantLock();
		if (!lock.isLocked()) {
			threadPool.execute(new Runnable() {
				public void run() {
					updateMax(vo);
				}
			});
		}
	}

	/**
	 * 更新序列最大值
	 * 
	 * @param vo
	 */
	private void updateMax(SequenceVo vo) {
		ReentrantLock lock = vo.getReentrantLock();
		if (lock.tryLock()) {
			try {
				long expect = vo.getMax().longValue();
				long current = vo.getCurrent().longValue();
				long update = expect + vo.getBatch();

				// 保证在需要时才预取
				if (expect < current + (vo.getBatch() / 2)) {
					long startTime = System.currentTimeMillis();
					sequenceDao.getNext(serverId, vo.getEntity(), update);
					vo.getMax().set(update);
					long endTime = System.currentTimeMillis();
					LOGGER.debug(
							"begin remote get sequence, entity:{},current:{},currentMax:{},cost:{}ms",
							vo.getEntity(), current, expect,
							(endTime - startTime));
				}
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * 将数据库中查询出来的sequence对象转换成SequenceVo对象
	 * @param sequence
	 * @return
	 */
	private SequenceVo converSingle(Sequence sequence) {
		SequenceVo sequenceVo = new SequenceVo();
		sequenceVo.setServerId(sequence.getServerId());
		sequenceVo.setEntity(sequence.getEntity());
		sequenceVo.setBatch(sequence.getBatch());
		sequenceVo.setDiv(sequence.getDiv());

		Long currentValue = convert4Server(sequence);
		sequenceVo.setCurrent(new AtomicLong(currentValue));
		sequenceVo.setMax(new AtomicLong(currentValue));
		sequenceVo.setReentrantLock(new ReentrantLock());
		return sequenceVo;
	}

	/**
	 * 匹配余数和server的值，serverId 从0开始
	 * 
	 * @param sequence
	 * @return
	 */
	private Long convert4Server(Sequence sequence) {
		Long current = sequence.getCurrent();
		Integer div = sequence.getDiv();
		if (current == null || current.longValue() < 0) {
			LOGGER.error(
					"Sequenc's current can't be null or negative , serverId:{},entity:{}",
					serverId, sequence.getEntity());
			throw new RuntimeException(sequence.getEntity()+ "'s current can't be negative");
		}
		if (sequence.getDiv() == null || div.intValue() < 1) {
			LOGGER.error(
					"Sequenc's div can't be null or zero or negative, serverId:{},entity:{}",
					serverId, sequence.getEntity());
			throw new RuntimeException("div can't be zero or null");
		}
		// if (serverId >= sequence.getDiv()) {
		// LOGGER.error(
		// "div mus larger than serverId, serverId:{},entity:{}",
		// serverId, sequence.getEntity());
		// throw new RuntimeException("ServerId can't be larger than div");
		// }
		while (current % div != serverId % div) {
			current++;
		}
		return current;
	}



	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {

		}
	}

	/**
	 * 取得SequenceVo对象
	 * 
	 * @param serverId
	 * @param entity
	 * @return
	 */
	private SequenceVo getSequenceVo(int serverId, final String entity) {
		SequenceVo sequenceVo = sequenceCurrent.get(entity);

		if (sequenceVo == null) {
			Sequence sequence = sequenceDao.querySingleSequence(serverId,
					entity);
			if (sequence == null) {
				throw new RuntimeException("Can't find entity:" + entity);
			}
			initSingle(sequence);
			sequenceVo = sequenceCurrent.get(entity);
		}
		return sequenceVo;
	}

}
