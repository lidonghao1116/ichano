package cn.ichano.common.entity.push.message;

import cn.ichano.common.service.push.PushMessage;

/**
 * 将content作为文本内容发送
 * @author wenjp
 *
 */
public class ContentMessage implements PushMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4842269160008766363L;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toPushMessage(){
		return "{\"aps\":{\"alert\":\"" + content + "\",\"badge\":1,\"sound\":\"beep-beep.caf\"}}"; 
	}
	
	@Override
	public String toString(){
		return content;
	}

}
