package cn.ichano.common.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ichano.common.config.impl.EmptyConfig;
import cn.ichano.common.config.impl.XMLConfig;


public class Env {

	private static final Logger LOGGER = LoggerFactory.getLogger(Env.class);

	/**
	 * 用于全局数据交换:是否需要？
	 */
//	private static Map<String, Object> map = new ConcurrentHashMap<String, Object>();

	private Env() {

	}

	/**
	 * 系统SQL语句
	 */
	private static Config sqlResource = new EmptyConfig();

	/**
	 * 配置参数
	 */
	private static Config config =new EmptyConfig();

	public static Config getSqlResource() {
		if(sqlResource instanceof EmptyConfig){
			LOGGER.info("sqlResource doesn't init !!!");
		}
		return sqlResource;
	}

	public static Config getConfig() {
		if(sqlResource instanceof EmptyConfig){
			LOGGER.info("config doesn't init !!!");
		}
		return config;
	}

	public static Config buildXmlConfig(String fileName) {

		try {
			if(!(config instanceof EmptyConfig)){
				LOGGER.error("config has load, now reload, is an error?");
			}
			config = new XMLConfig("config", fileName, true);
		} catch (Exception e) {
			LOGGER.error("init config fileName error, fileName:{}", fileName, e);
			throw new RuntimeException("init config fileName error:", e);
		}
		return config;
	}

	public static Config buildConfig4Sql(String fileName) {
		try {
			if(!(sqlResource instanceof EmptyConfig)){
				LOGGER.error("sqlResource has load, now reload, is an error?");
			}
			sqlResource = new XMLConfig("sqlResource", fileName, true);
		} catch (Exception e) {
			LOGGER.error("init config fileName error, fileName:{}", fileName, e);
			//throw new RuntimeException("init config fileName error:", e);
		}
		return sqlResource;
	}
	
	public static Config buildConfig4Sql(String[] fileNames) {
		try {
			if(!(sqlResource instanceof EmptyConfig)){
				LOGGER.error("sqlResource has load, now reload, is an error?");
			}
			sqlResource = new XMLConfig("sqlResource", fileNames, true);
		} catch (Exception e) {
			LOGGER.error("init config fileName error.", e);
		}
		return sqlResource;
	}

	/**
	 * 解析一个xml文件为配置接口
	 * 
	 * @param fileName
	 * @return
	 */
	public static Config buildCommonXmlConfig(String fileName) {
		try {
			return new XMLConfig("", fileName, true);
		} catch (Exception e) {
			LOGGER.error("init config fileName error, fileName:{}", fileName, e);
			throw new RuntimeException("init config fileName error:", e);

		}

	}

//	/**
//	 * 获取一个前期设置的值
//	 * @param key
//	 * @return
//	 */
//	public static Object get(String key) {
//		return map.get(key);
//	}
//
//	/**
//	 * 设置一个值
//	 * @param key
//	 * @param value
//	 */
//	public static void set(String key, Object value) {
//		map.put(key, value);
//	}
}
