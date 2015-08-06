package cn.ichano.common.entity.push.receiver;

import cn.ichano.common.service.push.Receiver;

/**
 * 指定的设备接受者
 * @author wenjp
 *
 */
public class DeviceReceiver implements Receiver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7007043098410452930L;

	private String appId;
	
	private int language;
	
	private String token;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}
	
	
}
