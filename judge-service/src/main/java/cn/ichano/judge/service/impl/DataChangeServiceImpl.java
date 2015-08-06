package cn.ichano.judge.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import cn.ichano.judge.service.DataChangeService;
import cn.ichano.common.db.dao.IBaseDao;
import cn.ichano.common.entity.message.NoticeMessage;
import cn.ichano.common.entity.message.NoticeMessage.Sync;
import cn.ichano.common.util.JsonUtil;
@Service
public class DataChangeServiceImpl implements DataChangeService {

	private Logger LOGGER = LoggerFactory.getLogger(DataChangeServiceImpl.class);
	
	private Logger sqlLogger = LoggerFactory
			.getLogger("cn.ichano.notice.db.receive");
	@Autowired
	IBaseDao baseDao;
	
	@Autowired
	ThreadPoolTaskExecutor threadPool;
	

	@Override
	public int applyNotice(NoticeMessage noticeMessage) {
		
		sqlLogger.error("notice:{}",
				JsonUtil.toJsonString(noticeMessage));
		Map<String, Object> params = JsonUtil.fromJsonString(noticeMessage.getValue(),
				Map.class);
		// if (p.equals(Position.LOCAL) && canRunLocal()) {
		final String sql = (String) params.get("sql");
		final Object[] obj = (Object[]) params.get("objs");
		final Map bean = (Map) params.get("bean");
		int result = 0;
		if (Sync.SYNC.equals(noticeMessage.getSync())) {
			result = baseDao.updateImmediately(sql, obj, bean);
		} else {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					baseDao.updateImmediately(sql, obj, bean);
				}
			});
		}
		return result;
	}
	
	
}
