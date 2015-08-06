package cn.ichano.common.entity.info;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/** 
* @ClassName: DeviceInfo 
* @Description: TODO(给其他系统调用,根据cid和service(avs:0,client:1)(目前不提供avs信息查询)获取设备版本信息) 
* @author zhangshaoyun 
* @date 2015年4月16日 上午10:15:43  
*/
public class DeviceInfo implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -122922777697708411L;
	private Long cid;

	private int service;
	@JsonProperty("device_type")
	private Integer deviceType = 0; // Device Type.
									// 0.Default;1.Windows;2.iPhone;3.iPad;4.AndroidPhone;5.AndroidTV;6.IPCam;7.MAC;
	private Integer os = 0; // 0.Default;1.Window;2.iOS;3.MAC
							// OS;4.Android;5IPCam Linux;6.WEB;
	@JsonProperty("os_version")
	private String osVersion = "";
	private int serviceFlag = 0; // 0. Streamer. 1.Viewer;
	// 0.Default;1.Viewer iOS CN;2.Viewer iOS US;3.Viewer Pro;4.Viewer
	// Android;5.Vierer Window;6.Viewer WEB;
	// 17.AVS Window;18.AVS MAC;19.AVS iOS;21.AVS Android;22.AVS ANDroid
	// TV;23.AVS IPCam;24.Android IPCam;
	@JsonProperty("service_type")
	private String serviceType = "0";
	@JsonProperty("service_version")
	private String serviceVersion = ""; // 2.0.0Beta

	private String updateTime = "2011-05-13 12:00:00";

	private String token;
	
	private String appversion;
	
	private String platversion;

	@JsonProperty("app_id")
	private String appId;
	@JsonProperty("company_id")
	private String companyId;

	private int language = 2;

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getOs() {
		return os;
	}

	public void setOs(Integer os) {
		this.os = os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public int getServiceFlag() {
		return serviceFlag;
	}

	public void setServiceFlag(int serviceFlag) {
		this.serviceFlag = serviceFlag;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public int getService() {
		return service;
	}

	public void setService(int service) {
		this.service = service;
	}

	public DeviceInfo(Long cid) {
		super();
		this.cid = cid;
		this.service = 1;
	}

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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getPlatversion() {
		return platversion;
	}

	public void setPlatversion(String platversion) {
		this.platversion = platversion;
	}
}
