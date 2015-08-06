package cn.ichano.common.entity.info;

import java.io.Serializable;
import java.util.Date;

import cn.ichano.common.util.JsonUtil;

public class CompanyAppInfo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1531056220858579241L;

	private String companyId;
	private String appId;
	private String productId;
	private String appName;

	// 采集端、观看端
	private int appType;
	private int osType;
	private String appDesc;
	private String newestVersion;
	private String oldestVersion;
	private int tokenType;
	private String pushParams;

	private Date createTime;
	private Date updateTime;
	private String appStanza;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public int getOsType() {
		return osType;
	}

	public void setOsType(int osType) {
		this.osType = osType;
	}


	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getNewestVersion() {
		return newestVersion;
	}

	public void setNewestVersion(String newestVersion) {
		this.newestVersion = newestVersion;
	}

	public String getOldestVersion() {
		return oldestVersion;
	}

	public void setOldestVersion(String oldestVersion) {
		this.oldestVersion = oldestVersion;
	}

	public int getTokenType() {
		return tokenType;
	}

	public void setTokenType(int tokenType) {
		this.tokenType = tokenType;
	}



	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAppStanza() {
		return appStanza;
	}

	public void setAppStanza(String appStanza) {
		this.appStanza = appStanza;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPushParams() {
		return pushParams;
	}

	public void setPushParams(String pushParams) {
		this.pushParams = pushParams;
	}

	public CompanyAppInfo(String companyId, String appId, String appName,
			int appType, int osType, int licenseFlag, int licenseCount,
			String appDesc, String newestVersion, String oldestVersion,
			int tokenType, String pushParams, Date createTime,
			Date updateTime, String appStanza) {
		super();
		this.companyId = companyId;
		this.appId = appId;
		this.appName = appName;
		this.appType = appType;
		this.osType = osType;

		this.appDesc = appDesc;
		this.newestVersion = newestVersion;
		this.oldestVersion = oldestVersion;
		this.tokenType = tokenType;
		this.pushParams = pushParams;

		this.createTime = createTime;
		this.updateTime = updateTime;
		this.appStanza = appStanza;
	}

	public CompanyAppInfo() {
	}

	@Override
	public String toString() {
		return JsonUtil.toJsonString(this);
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
