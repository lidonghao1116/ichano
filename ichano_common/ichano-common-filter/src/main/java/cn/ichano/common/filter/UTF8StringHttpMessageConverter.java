package cn.ichano.common.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StreamUtils;

public class UTF8StringHttpMessageConverter extends StringHttpMessageConverter {

	private static final MediaType utf8 = new MediaType("text", "html",
			Charset.forName("UTF-8"));
	private boolean writeAcceptCharset = true;

	@Override
	protected MediaType getDefaultContentType(String dumy) {
		return utf8;
	}

	protected List<Charset> getAcceptedCharsets() {
		return Arrays.asList(utf8.getCharSet());
	}

	protected void writeInternal(String s, HttpOutputMessage outputMessage)
			throws IOException {
		Charset charset = utf8.getCharSet();
		byte[] stream = s.getBytes(charset);
//		super.writeInternal(s, outputMessage);
		if (this.writeAcceptCharset) {
			outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
		}
		outputMessage.getHeaders().setContentLength(stream.length);
		StreamUtils.copy(s, charset, outputMessage.getBody());
	}

	public boolean isWriteAcceptCharset() {
		return writeAcceptCharset;
	}

	public void setWriteAcceptCharset(boolean writeAcceptCharset) {
		this.writeAcceptCharset = writeAcceptCharset;
	}
}
