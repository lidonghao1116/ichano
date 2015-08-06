package cn.ichano.common.entity.mail;

import java.io.Serializable;

/**
 * mail实体
 * @author wenjp
 *
 */
public interface MailMessage extends Serializable {

	/**
	 * 邮件标题
	 * 
	 * @return
	 */
	public String getMailTitle();
	
	/**
	 * 邮件内容
	 * 
	 * @return
	 */
	public String toMailMessage();
	
	/**
	 * 绑定的email地址，多个以";"分割
	 * 
	 * @return
	 */
	public String getBindEmail();
}
