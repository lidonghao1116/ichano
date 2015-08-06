package cn.ichano.common.service.push;


/**
 * 给指定的客户端发送push
 * 
 * @author wenjp
 *
 */
public interface UserMessageService {

	/**
	 * 推送给指定client cid的消息
	 * 
	 * @param cid
	 * @param message
	 * @return
	 */
	public boolean push(Long cid, PushMessage message);

	public boolean push(Long cid, String cidMessage);

	public boolean mail(String receiver, String cidMessage);
}
