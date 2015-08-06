package cn.ichano.common.message.push.service;

import cn.ichano.common.service.push.PushMessage;

/**
 * push服务
 * 
 * @author wenjp
 *
 */
public interface PlatPushService {

	/**
	 * 给对应的token发送Message对象的消息
	 * 
	 * @param token
	 * @param message
	 */
	public void push(String token, PushMessage message);

	/**
	 * 给对应的token发送对应得字符串.<br/>
	 * 注意项：此处message为最终发送的字符串，如果是展示的文本，请使用PushMessage的ContentMessage实现。
	 * 
	 * @param token
	 * @param message
	 */
	public void push(String token, String message);

}
