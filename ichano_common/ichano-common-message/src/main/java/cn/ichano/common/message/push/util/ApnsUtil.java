package cn.ichano.common.message.push.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ichano.common.util.JsonUtil;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Payload;

public class ApnsUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(ApnsUtil.class);

	private static String APS = "aps";

	public static void init(List<Properties> configList) {
		for (Properties p : configList) {
			init(p);
		}
	}

	public static IApnsService init(Properties p) {
		ApnsConfig config = new ApnsConfig();
		InputStream is = null;
		try {
			is = new FileInputStream(new File(p.getProperty("fileName")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		config.setKeyStore(is);
		config.setDevEnv(Boolean.valueOf(p.getProperty("isDev")));
		config.setPassword(p.getProperty("password"));
		config.setPoolSize(Integer.valueOf(p.getProperty("poolSize")));

		// 假如需要在同个java进程里给不同APP发送通知，那就需要设置为不同的name
		if (p.getProperty("name") != null) {
			config.setName(p.getProperty("name"));
		}

		IApnsService apnsService = ApnsServiceImpl.createInstance(config);
		return apnsService;
	}
	

	public static Properties createProperties(String poolName, String fileName,
			String password) {
		Properties p = new Properties();
		p.setProperty("fileName", fileName);
		p.setProperty("name", poolName);
		p.setProperty("isDev", "false");
		p.setProperty("password", password);
		p.setProperty("poolSize", "10");
		return p;
	}

	/**
	 * 
	 * @return
	 */
	// todo 同步控制
	public static IApnsService getApnsService() {
		return getApnsService("product-env");
	}

	// todo
	public static IApnsService getApnsService(String name) {
		return ApnsServiceImpl.getCachedService(name);
	}

	@SuppressWarnings("unchecked")
	public static Payload covernet(String message) {
		Map<String, Object> map = JsonUtil.fromJsonString(message, Map.class);

		Map<String, Object> aps = (Map<String, Object>) map.remove(APS);

		if (aps == null) {
			LOGGER.error("can't create payload ,is an error? message:{}",
					message);
			return null;
		}
		Payload p = new Payload();

		p.setParams(map);

		Object alert = aps.get("alert");

		if (alert instanceof String) {
			p.setAlert((String) alert);
		} else {
			p.setAlert(JsonUtil.toJsonString(alert));
		}
		p.setBadge((Integer) aps.get("badge"));
		p.setSound((String) aps.get("sound"));
		return p;
	}
}
