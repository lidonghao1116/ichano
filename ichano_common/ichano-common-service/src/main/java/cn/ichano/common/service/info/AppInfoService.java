package cn.ichano.common.service.info;

import java.util.List;

import cn.ichano.common.entity.info.CompanyAppInfo;

public interface AppInfoService {

	public CompanyAppInfo queryCompanyAppInfo(String appId);

	public List<CompanyAppInfo> queryAllCompanyAppInfo();
}
