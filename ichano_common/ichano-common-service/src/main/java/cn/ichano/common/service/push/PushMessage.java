package cn.ichano.common.service.push;

import java.io.Serializable;

/**
 * 推送的消息体
 * @author wenjp
 *
 */
public interface PushMessage extends Serializable {
	/**
	 * 中文
	 */
	public static final int LANGUAGE_CN = 1;

	/**
	 * 英文
	 */
	public static final int LANGUAGE_EN = 2;

	/**
	 * 默认标题
	 */
	public static final String DEFAULT_TITLE = "AtHome Camera alert";

	/**
	 * push消息内容
	 * 
	 * @return
	 */
	public String toPushMessage();
}
