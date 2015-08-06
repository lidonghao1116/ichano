package cn.ichano.judge.entity;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于获取序列的处理对象
 * @author wenjp
 *
 */
public class SequenceVo {

	/**
	 * server id，与数据库中分配相关
	 */
	private int serverId;
	
	/**
	 * 实体名或者表名
	 */
	private String entity;
	
	/**
	 * 内存中当前的值
	 */
	private AtomicLong current;
	
	/**
	 * 每次批量取得数据量
	 */
	private int batch;
	
	/**
	 * 每个序列值的间隔(或者跳跃数)。如为2，current为3，则下一个为5。
	 */
	private int div;
	
	/**
	 * 当前允许的最大值
	 */
	private AtomicLong max;
	
	/**
	 * 预取max使用的锁
	 */
	private ReentrantLock reentrantLock;

	public ReentrantLock getReentrantLock() {
		return reentrantLock;
	}

	public void setReentrantLock(ReentrantLock reentrantLock) {
		this.reentrantLock = reentrantLock;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public AtomicLong getCurrent() {
		return current;
	}

	public void setCurrent(AtomicLong current) {
		this.current = current;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public int getDiv() {
		return div;
	}

	public void setDiv(int div) {
		this.div = div;
	}

	public AtomicLong getMax() {
		return max;
	}

	public void setMax(AtomicLong max) {
		this.max = max;
	}

	
}
