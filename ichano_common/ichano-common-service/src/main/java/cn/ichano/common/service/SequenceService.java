package cn.ichano.common.service;

/**
 * 序列服务
 * @author wenjp
 *
 */
public interface SequenceService {

	/**
	 * 获取指定实体下一个序列
	 * @param entity
	 * @return
	 */
	public Long getNext(String entity);
	
	//public Long getNext(int serverId,String entity);

}
