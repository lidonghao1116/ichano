package cn.ichano.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import cn.ichano.common.util.pool.ComponentHttpPool;

/**
 * 与http相关的服务方法调�?. <br>
 * 类详细说�?.
 * <p>
 * Copyright: Copyright (c) Sep 7, 2012 3:22:44 PM
 * <p>
 * Company: 北京宽连十方数字�?术有限公�?
 * <p>
 * 
 * @author wenjp@c-platform.com
 * @version 1.0.0
 */
public class HttpUtil {

	/**
	 * 日志对象
	 */
	private static Logger logger = Logger.getLogger(HttpUtil.class);

	private final static ExecutorService service = ExecuteServiceUtil.getService();

	private static ContentType ct = ContentType.create("application/text",
			Charset.forName("utf-8"));

	/**
	 * 执行异步调用
	 * 
	 * @param callUrl
	 * @param postData
	 */
	public static void asyncCall(final String callUrl, final byte[] postData) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					commonPostMethod(callUrl, postData);
				} catch (Exception e) {
					logger.error("执行调用失败：", e);
				}
			}
		};
		// FutureTask<Object> task = new FutureTask<Object>(runnable, null);
		service.execute(runnable);
	}

	/**
	 * 执行异步调用
	 * 
	 * @param callUrl
	 * @param postData
	 */
	public static void asyncCall(final String callUrl,
			final ContentType contentType, final byte[] postData) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					HttpUtil.commonPostMethod(callUrl, contentType, postData);
				} catch (Exception e) {
					logger.error("执行调用失败：", e);
				}
			}
		};
		// FutureTask<Object> task = new FutureTask<Object>(runnable, null);
		service.execute(runnable);
	}

	/**
	 * 提交数据
	 * 
	 * @param callUrl
	 * @param postData
	 */
	private static void commonPost(String callUrl, byte[] postData) {
		try {
			HttpUtil.commonPostMethod(callUrl, postData);
		} catch (IOException e) {
			logger.error("调用远端接口失败", e);
			throw new RuntimeException("调用接口失败�?", e);
		}
	}

	/**
	 * 根据前缀与后�?生成�?终调用链�?
	 * 
	 * @param callUrl
	 *            原始url
	 * @param lastParams
	 *            附加参数
	 * @return
	 */
	public static String getFianllyCallUrl(String callUrl, String lastParams) {
		int index = callUrl.indexOf("?");
		if (index == -1) {
			callUrl = callUrl + "?" + lastParams;
		} else if (index == callUrl.length()) {
			callUrl = callUrl + lastParams;
		} else {
			callUrl = callUrl + "&" + lastParams;
		}
		return callUrl;
	}

	/**
	 * 获取参数拼接的字符串
	 * 
	 * @param params
	 *            map参数
	 * @return
	 */
	public static String getMapString(Map<String, String> params) {
		if (params == null || params.size() == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		Set<String> keySet = params.keySet();

		for (String key : keySet) {
			sb.append(key).append("=").append(params.get(key)).append("&");
		}

		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 获得用户的ip
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 调用者IP
	 */
	public static String getIp(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");

		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip.split(",")[0];
		}

		return request.getRemoteAddr();

	}

	/**
	 * 执行方法
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String execute(HttpRequestBase request)
			throws ClientProtocolException, IOException {
		DefaultHttpClient httpClient = (DefaultHttpClient) ComponentHttpPool
				.getHttpClient();

//		int retry = Env.getConfig().getInteger("common.util.httpClient.retry", 3);
//		// 设置重试
//		HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(
//				retry, true);
//		httpClient.setHttpRequestRetryHandler(retryHandler);
		HttpResponse response = null;
		InputStream is = null;
		String result = null;
		try {
			response = httpClient.execute(request);

			if (response != null) {
				// 判断状�?�码
				StatusLine status = response.getStatusLine();

				// 请求成功
				if (HttpStatus.SC_OK == status.getStatusCode()) {
					// 获取应答
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						is = entity.getContent();
						result = EntityUtils.toString(entity, "utf-8");
					}

				}
				return result;

			} else {
				logger.error(request.getURI() + ",请求响应为空�?");
				return result;
			}
		} catch (ClientProtocolException e) {
			logger.error("调用接口异常�?" + request.getURI(), e);
			throw e;
		} catch (IOException e) {
			logger.error("调用接口异常�?" + request.getURI(), e);
			throw e;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				logger.error("关闭is异常�?", e);
			}
		}

	}
	
	public static byte[] executeForByte(HttpRequestBase request)
			throws ClientProtocolException, IOException {
		DefaultHttpClient httpClient = (DefaultHttpClient) ComponentHttpPool
				.getHttpClient();


		HttpResponse response = null;
		InputStream is = null;
		byte[] result2 = null;
		try {
			response = httpClient.execute(request);

			if (response != null) {
				// 判断状�?�码
				StatusLine status = response.getStatusLine();

				// 请求成功
				if (HttpStatus.SC_OK == status.getStatusCode()) {
					// 获取应答
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						is = entity.getContent();
						 return EntityUtils.toByteArray(entity);

					}

				}
				

			} else {
				logger.error(request.getURI() + ",请求响应为空�?");
			}
		} catch (ClientProtocolException e) {
			logger.error("调用接口异常�?" + request.getURI(), e);
			throw e;
		} catch (IOException e) {
			logger.error("调用接口异常�?" + request.getURI(), e);
			throw e;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				logger.error("关闭is异常�?", e);
			}
		}
		return result2;
	}

	/**
	 * 通用的提交数据请求方法
	 * 
	 * @param callUrl
	 * @param postData
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String commonPostMethod(String callUrl, byte[] postData)
			throws ClientProtocolException, IOException {

		// method.addHeader("Content-Type", "application/text;charset=utf-8");
		return commonPostMethod(callUrl, ct, postData);
	}

	/**
	 * 通用的提交数据请求方法
	 * 
	 * @param callUrl
	 * @param postData
	 * @param contentType
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String commonPostMethod(String callUrl, ContentType contentType,
			byte[] postData) throws ClientProtocolException, IOException {
		HttpPost method = new HttpPost(callUrl);

		if (postData != null) {
			HttpEntity entity = new ByteArrayEntity(postData, contentType);

			method.setEntity(entity);
		}
		return execute(method);
	}

	/**
	 * 通用的get方法
	 * 
	 * @param callUrl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String commonGetMehtod(String callUrl)
			throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(callUrl);
		httpGet.addHeader("Content-Type", "application/text;charset=utf-8");
		return execute(httpGet);
	}
}
