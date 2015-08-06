package cn.ichano.common.util.pool;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import cn.ichano.common.config.Env;



/**
 * 开通关闭接口Http链接池. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2012-8-8 下午2:46:40
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author baowr@c-platform.com
 * @version 1.0.0
 */
public class ComponentHttpPool {

	/**
	 * 连接超时时间
	 */
	public static int CONNECT_TIMEOUT = 10000;

	/**
	 * 每个路由最大连接数
	 */
	public static int MAX_ROUTE_CONNECTIONS = 20;

	/**
	 * 最大连接数
	 */
	public static int MAX_TOTAL_CONNECTIONS = 500;

	/**
	 * 读取超时时间
	 */
	public static int READ_TIMEOUT = 10000;

	/**
	 * 获取连接的最大等待时间
	 */
	public static int WAIT_TIMEOUT = 60000;

	/**
	 * http链接管理类
	 */
	private static PoolingClientConnectionManager connectionManager;

	/**
	 * http参数
	 */
	private static HttpParams httpParams;

	static {
		httpParams = new BasicHttpParams();

		WAIT_TIMEOUT = Env.getConfig().getInteger("common.util.httpClient.waitTimeout",
				WAIT_TIMEOUT);

		CONNECT_TIMEOUT = Env.getConfig().getInteger(
				"common.util.httpClient.connctionTimeout", CONNECT_TIMEOUT);

		READ_TIMEOUT = Env.getConfig().getInteger("common.util.httpClient.soTimeout",
				READ_TIMEOUT);

		MAX_TOTAL_CONNECTIONS = Env.getConfig().getInteger(
				"common.util.httpClient.maxTotalConn", MAX_TOTAL_CONNECTIONS);

		MAX_ROUTE_CONNECTIONS = Env.getConfig().getInteger(
				"common.util.httpClient.maxRouteconn", MAX_ROUTE_CONNECTIONS);

		// 设置获取连接的最大等待时间
		HttpClientParams.setConnectionManagerTimeout(httpParams, WAIT_TIMEOUT);
		// 设置连接超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
		// 设置读取超时时间
		HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);

		connectionManager = new PoolingClientConnectionManager();
		// 设置最大连接数
		connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		// 设置每个路由最大连接数
		connectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

	}

	/**
	 * 从http链接池中获取一个可用的http连接
	 * 
	 * @return 可用的http连接
	 */
	public static HttpClient getHttpClient() {
		return new DefaultHttpClient(connectionManager, httpParams);

	}

}
