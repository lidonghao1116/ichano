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
import cn.ichano.common.filter.wrapper.ContentTypeRequestWrapper;
import cn.ichano.common.util.EncryptUtil;

public class ContentTypeFilter implements Filter {

	private String contentType = null;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (contentType != null) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;

			ContentTypeRequestWrapper requestWrapper = null;
			if (httpRequest instanceof HttpServletRequest) {
				requestWrapper = new ContentTypeRequestWrapper(
						(HttpServletRequest) request,contentType);
			}

			chain.doFilter(requestWrapper, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		String s = config.getInitParameter("contentType");
		if (s != null) {
			this.contentType = s;
		}
	}
}
