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



/**
 * 请求适配器
 * @author wenjp
 *
 */
public class BlowfishRequestWrapper extends HttpServletRequestWrapper {

	private byte[] body = null; // 报文
	

	public BlowfishRequestWrapper(HttpServletRequest request)
			throws IOException {
		super(request);
		byte[] requestByte = IOUtils.toByteArray(request.getInputStream());
		//System.out.println(new String(base64Bytes));
		//byte[] encryptBytes = EncryptUtil.decodeBase64(base64Bytes);
		body = EncryptUtil.decrypt(requestByte);
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
