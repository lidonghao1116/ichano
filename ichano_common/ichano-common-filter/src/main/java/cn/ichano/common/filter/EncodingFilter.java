package cn.ichano.common.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.amazonaws.util.IOUtils;

//@WebFilter(filterName="encodingFilter",urlPatterns="/*")
public class EncodingFilter implements Filter {

	private FilterConfig config;
	private String encoding = "utf-8";

	public void destroy() {
		config = null;

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		
//		ByteArrayOutputStream b = new ByteArrayOutputStream();
//		IOUtils.copy(request.getInputStream(), b);
//		System.out.println(b.toString("utf-8"));
		//response.setContentType("text/html;charset=utf8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding(encoding);

		chain.doFilter(request, response);
		response.setContentType("text/html;charset=utf-8");
		
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		String s = config.getInitParameter("encoding");
		if (s != null) {
			this.encoding = s;
		}
	}

}