package cn.ichano.common.entity.info;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品实体
 * 
 * @author wenjp
 *
 */
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 426775260672477740L;

	private String id;

	private String companyId;

	private String name;

	private int flag = 0;

	private Long licenseCount;

	private Long used;

	// 已经导入授权表数量
	private Long activeCount;

	private String desc;

	private String logo;

	private Date createTime;

	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Long getLicenseCount() {
		return licenseCount;
	}

	public void setLicenseCount(Long licenseCount) {
		this.licenseCount = licenseCount;
	}

	public Long getUsed() {
		return used;
	}

	public void setUsed(Long used) {
		this.used = used;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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

	public Long getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(Long activeCount) {
		this.activeCount = activeCount;
	}

}
