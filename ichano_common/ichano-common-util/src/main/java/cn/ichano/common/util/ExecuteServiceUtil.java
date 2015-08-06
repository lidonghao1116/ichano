package cn.ichano.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.ichano.common.config.Env;

public class ExecuteServiceUtil {

	private static ExecutorService service = Executors.newFixedThreadPool(Env
			.getConfig().getInteger("common.util.thread.poolSize", 50));

	public static ExecutorService getService() {
		return service;
	}
}
