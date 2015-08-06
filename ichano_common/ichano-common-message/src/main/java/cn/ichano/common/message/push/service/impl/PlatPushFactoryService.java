package cn.ichano.common.message.push.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ichano.common.entity.info.CompanyAppInfo;
import cn.ichano.common.message.push.service.PlatPushService;
import cn.ichano.common.service.info.AppInfoService;
import cn.ichano.common.util.JsonUtil;

@Service
public class PlatPushFactoryService {
	private static Logger LOGGER = LoggerFactory
			.getLogger(PlatPushFactoryService.class);
	private Map<String, PlatPushService> map = new HashMap<String, PlatPushService>();

	@Autowired
	private AppInfoService appInfoService;

	@PostConstruct
	private void init() {
		initPlatInfo();

	}

	/**
	 * 初始化发送平台信息
	 */
	private void initPlatInfo() {
		List<CompanyAppInfo> platPushInfoList = appInfoService
				.queryAllCompanyAppInfo();
		LOGGER.info("query token type info, list:{}", platPushInfoList);
		if (platPushInfoList != null && platPushInfoList.size() > 0) {
			for (CompanyAppInfo pushInfo : platPushInfoList) {

				initSingle(pushInfo);
			}
		}
	}

	/**
	 * 初始化当个数据
	 * 
	 * @param pushInfo
	 */
	private void initSingle(CompanyAppInfo pushInfo) {
		if (pushInfo == null || StringUtils.isEmpty(pushInfo.getPushParams())) {
			return;
		}

		Map<String, String> params = JsonUtil.fromJsonString(
				pushInfo.getPushParams(), Map.class);

		if (1 == pushInfo.getTokenType()) {

		}
		if (!StringUtils.isEmpty(params.get("ios_key_path"))
				&& !StringUtils.isEmpty(params.get("ios_key_pass"))) {
			IosPushService iosPushService = new IosPushService(
					params.get("ios_key_path"), params.get("ios_key_pass"));
			map.put(pushInfo.getAppId(), iosPushService);
		} else if (!StringUtils.isEmpty(params.get("getui_api"))) {
			AndroidPushService androidPushService = new AndroidPushService(
					params.get("getui_api"), params.get("getui_app_key"),
					params.get("getui_master_key"), params.get("getui_app_id"));
			map.put(pushInfo.getAppId(), androidPushService);
		} else {
			LOGGER.error("empty push config, please fix data. data:{}",
					pushInfo);
		}
	}

	public PlatPushService getPlatPushService(String appId) {
		PlatPushService pushService = map.get(appId);
		if (pushService == null) {
			CompanyAppInfo pushInfo = appInfoService.queryCompanyAppInfo(appId);
			if (pushInfo != null) {
				initSingle(pushInfo);
			}
			pushService = map.get(appId);
			if (pushInfo == null || pushService == null) {
				LOGGER.error("can't get token type push Service, appId:{}",
						appId);
				throw new RuntimeException(
						"can't get token type push Service, appId:" + appId);
			}
		}
		return pushService;
	}
}
