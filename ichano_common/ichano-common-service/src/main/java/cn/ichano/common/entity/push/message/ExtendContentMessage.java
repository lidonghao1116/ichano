package cn.ichano.common.entity.push.message;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import cn.ichano.common.util.JsonUtil;

/**
 * 指定title, content push 发送
 * 
 * @author wenjp
 *
 */
public class ExtendContentMessage extends ContentMessage implements
		ExtendsPushMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7980752509432234243L;

	private String title;

	private Integer language;

	private Map<String, String> messageMap = new HashMap<String, String>();

	private Map<String, Object> params = new HashMap<String, Object>();

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setLanguage(int language) {
		this.language = language;
	}

	@Override
	public String getTitle() {
		if (language != null) {
			return messageMap.get(language.intValue() + "_t");
		}
		return title;
	}

	@Override
	public String getContent() {
		if (language != null) {
			String content = messageMap.get(language.intValue() + "_c");
			if (content != null) {
				return content;
			}
		}
		return super.getContent();

	}

	@Override
	public String toPushMessage() {

		String sendContent = this.getContent();

		String resposne = "{\"aps\":{\"alert\":\"" + sendContent
				+ "\",\"badge\":1,\"sound\":\"beep-beep.caf\"}";

		if (!CollectionUtils.isEmpty(params)) {
			resposne += "," + JsonUtil.toJsonString(params).substring(1);
		} else {
			resposne += "}";
		}
		return resposne;
	}

	public void setLanguageTitle(int language, String title) {
		messageMap.put(language + "_t", title);
	}

	public void setLanguageContent(int language, String content) {
		messageMap.put(language + "_c", content);
	}

	@Override
	public void setExtends(String key, Object value) {
		if ("aps".equals(key)) {
			throw new RuntimeException("extends key can't be \"aps\". ");
		}
		params.put(key, value);
	}
}
