package cn.ichano.judge.dao;

import java.util.List;

import cn.ichano.judge.entity.Sequence;



public interface SequenceDao {

	public List<Sequence> querySequenceList(int serverId);
	
	public Sequence querySingleSequence(int serverId, String entity);
	
	/**
	 * 将max设置为数据库当前的值
	 * @param serverId
	 * @param entity
	 * @param max
	 * @return
	 */
	public Long getNext(int serverId,String entity, long max);
	
	
}
