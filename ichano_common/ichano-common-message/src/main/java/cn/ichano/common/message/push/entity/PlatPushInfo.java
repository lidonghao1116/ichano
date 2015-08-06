package cn.ichano.common.message.push.entity;

import java.io.Serializable;

import cn.ichano.common.util.JsonUtil;

public class PlatPushInfo implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 739262876804123899L;

	private long id;
	
	private int tokenType;
	
	private int companyId;
	
	private String serviceDesc;
	
	private String iosKeyPath;
	
	private String iosKeypass;
	
	private String androidAppId;
	
	private String androidAppKey;
	
	private String androidMasterKey;
	
	private String androidApi;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTokenType() {
		return tokenType;
	}

	public void setTokenType(int tokenType) {
		this.tokenType = tokenType;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public String getIosKeyPath() {
		return iosKeyPath;
	}

	public void setIosKeyPath(String iosKeyPath) {
		this.iosKeyPath = iosKeyPath;
	}

	public String getIosKeypass() {
		return iosKeypass;
	}

	public void setIosKeypass(String iosKeypass) {
		this.iosKeypass = iosKeypass;
	}

	public String getAndroidAppId() {
		return androidAppId;
	}

	public void setAndroidAppId(String androidAppId) {
		this.androidAppId = androidAppId;
	}

	public String getAndroidAppKey() {
		return androidAppKey;
	}

	public void setAndroidAppKey(String androidAppKey) {
		this.androidAppKey = androidAppKey;
	}

	public String getAndroidMasterKey() {
		return androidMasterKey;
	}

	public void setAndroidMasterKey(String androidMasterKey) {
		this.androidMasterKey = androidMasterKey;
	}

	public String getAndroidApi() {
		return androidApi;
	}

	public void setAndroidApi(String androidApi) {
		this.androidApi = androidApi;
	}
	
	
	public String toString(){
		return JsonUtil.toJsonString(this);
	}
	
}
