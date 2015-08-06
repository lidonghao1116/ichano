package cn.ichano.common.push.extend;

import cn.ichano.common.push.Message;
import cn.ichano.common.push.PushService;

/**
 * 内部消息推送
 * @author wenjp
 *
 */
public interface InnerPushService extends PushService {

	/**
	 * 给一个监控端所有会话发送推送
	 * @param deviceServiceId
	 * @param cid
	 * @param message
	 * @return
	 */
	public boolean pushToAllView(int deviceServiceId,String cid,Message message);
	
	/**
	 * 给一个监控端指定用户所有会话发送推送
	 * @param deviceServiceId
	 * @param userId
	 * @param message
	 * @return
	 */
	public boolean pushToUser(int deviceServiceId,String userId,Message message);
		
	/**
	 * 给一个指定的监控端发送推送消息
	 * @param deviceServiceId
	 * @param deviceId
	 * @param message
	 * @return
	 */
	public boolean pushToSession(int deviceServiceId,String deviceId,Message message );
}
