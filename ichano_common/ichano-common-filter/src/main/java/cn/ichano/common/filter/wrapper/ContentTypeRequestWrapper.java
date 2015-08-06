package cn.ichano.common.filter.wrapper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;



public class ContentTypeRequestWrapper extends HttpServletRequestWrapper {

	private String contentType = null;

	public ContentTypeRequestWrapper(HttpServletRequest request, String contentType)
			throws IOException {
		super(request);
		this.contentType = contentType;
		
	

	}
	
	public String getHeader(String param){
		if(param.equalsIgnoreCase("content-type") || param.equalsIgnoreCase("content=type")){
			return this.getContentType();
		}else{
			return super.getHeader(param);
		}
	}

	public String getContentType() {
		if (contentType != null) {
			return contentType;
		} else {
			return super.getContentType();
		}

	}

}
