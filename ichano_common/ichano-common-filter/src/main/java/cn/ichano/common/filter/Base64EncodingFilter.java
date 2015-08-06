package cn.ichano.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ichano.common.filter.wrapper.Base64RequestWrapper;
import cn.ichano.common.filter.wrapper.Base64ResponseWrapper;
import cn.ichano.common.filter.wrapper.BlowfishRequestWrapper;
import cn.ichano.common.filter.wrapper.BlowfishResponseWrapper;
import cn.ichano.common.util.EncryptUtil;

/**
 * 加解密处理
 * 
 * @author wenjp
 *
 */
public class Base64EncodingFilter implements Filter {

	private boolean isEncrypt = true;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (isEncrypt) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;

			Base64RequestWrapper requestWrapper = null;
			if (httpRequest instanceof HttpServletRequest) {
				requestWrapper = new Base64RequestWrapper(
						(HttpServletRequest) request);

			}

			Base64ResponseWrapper wrapper = new Base64ResponseWrapper(
					httpResponse);
			chain.doFilter(requestWrapper, wrapper);
			if (wrapper.getStatus() == HttpServletResponse.SC_OK) {
				// 对响应进行处理，
//				byte[] resposneData = EncryptUtil.getBluefishEncrypt(wrapper
//						.getResponseData());

				byte[] resposneData = EncryptUtil.encodeBase64(wrapper
						.getResponseData());
				httpResponse.setContentLength(resposneData.length);
				ServletOutputStream output = response.getOutputStream();
				output.write(resposneData);
				output.flush();
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		String s = config.getInitParameter("isEncode");
		if (s != null) {
			this.isEncrypt = Boolean.valueOf(s);
		}
	}

}