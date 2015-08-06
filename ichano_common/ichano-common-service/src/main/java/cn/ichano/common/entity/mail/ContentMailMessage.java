package cn.ichano.common.entity.mail;

import cn.ichano.common.util.JsonUtil;



/**
 * 普通邮件内容
 * @author wenjp
 *
 */
public class ContentMailMessage implements MailMessage {

	private String mailTitle;

	private String content;

	private String bindEmail;

	@Override
	public String getMailTitle() {

		return mailTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public void setBindEmail(String bindEmail) {
		this.bindEmail = bindEmail;
	}

	@Override
	public String toMailMessage() {
		// TODO Auto-generated method stub
		return content;
	}

	@Override
	public String getBindEmail() {
		// TODO Auto-generated method stub
		return bindEmail;
	}

	@Override
	public String toString() {
		return JsonUtil.toJsonString(this);
	}
}
