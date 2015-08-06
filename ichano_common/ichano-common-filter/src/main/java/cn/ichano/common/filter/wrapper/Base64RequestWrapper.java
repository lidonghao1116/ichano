package cn.ichano.common.filter.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

import cn.ichano.common.util.EncryptUtil;

public class Base64RequestWrapper extends HttpServletRequestWrapper {

	private byte[] body = null; // 报文
	

	public Base64RequestWrapper(HttpServletRequest request)
			throws IOException {
		super(request);
		byte[] base64Bytes = IOUtils.toByteArray(request.getInputStream());
		//System.out.println(new String(base64Bytes));
		body = EncryptUtil.decodeBase64(base64Bytes);
		//body = EncryptUtil.decrypt(encryptBytes);
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return bais.read();
			}
		};
	}

}
