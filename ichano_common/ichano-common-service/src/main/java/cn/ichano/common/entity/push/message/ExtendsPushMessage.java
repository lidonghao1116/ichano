package cn.ichano.common.entity.push.message;

import cn.ichano.common.service.push.PushMessage;

/**
 * 扩展发送消息接口
 * @author wenjp
 *
 */
public interface ExtendsPushMessage extends PushMessage{

	/**
	 * 获得发送的tilte,暂时仅android有效
	 * @return
	 */
	public String getTitle();
	
	/**
	 * 设置消息的语言，如果可以取到接受端的语言，则会自动调用此方法
	 * @param language
	 */
	public void setLanguage(int language);

	/**
	 * 设置扩展值
	 * 
	 * @param key
	 * @param value
	 */
	public void setExtends(String key, Object value);
}
