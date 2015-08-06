package cn.ichano.common.message.email.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import cn.ichano.common.config.Env;
import cn.ichano.common.entity.mail.ContentMailMessage;
import cn.ichano.common.entity.mail.MailMessage;
import cn.ichano.common.message.StatService;
import cn.ichano.common.message.StatService.Type;
import cn.ichano.common.service.mail.EmailService;

//@Service
public class EmailServiceImpl implements EmailService {
	private Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	JavaMailSenderImpl sendImpl = new JavaMailSenderImpl();

	@Override
	public void send(MailMessage message) {

		LOGGER.debug("send mail, to:{}, message:{}", message.getBindEmail(),
				message.toString());
		StatService.getInstance().getEmailStat().addTotal(Type.NEED);

		// 这段需要改为配置
		sendImpl.setHost(Env.getConfig().getValue("push.mail.send.smtp",
				"smtp.mxhichina.com"));
		sendImpl.setPassword(Env.getConfig().getValue("push.mail.send.pass",
				"YunEn1234"));
		sendImpl.setUsername(Env.getConfig().getValue("push.mail.send.user",
				"AtHomeApp@ichano.com"));

		// sendImpl.setProtocol("mail");
		MimeMessage mailMessage = sendImpl.createMimeMessage();

		try {

			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mailMessage, true, "utf-8");
			messageHelper.setTo(message.getBindEmail().split(";"));
			messageHelper.setFrom(Env.getConfig().getValue(
					"push.mail.send.from", "AtHomeApp@ichano.com"));
			messageHelper.setSubject(message.getMailTitle());
			messageHelper.setText(message.toMailMessage());

			sendImpl.send(mailMessage);
			StatService.getInstance().getEmailStat().addTotal(Type.REAL);
		} catch (MessagingException e) {
			LOGGER.error("send mail error, to:{}", message.getBindEmail(), e);
		}

		//
		// SimpleMailMessage simpleMessage = new SimpleMailMessage();
		// simpleMessage.setFrom(mMailFrom);
		//
		// simpleMessage.setTo(message.getBindEmail());
		// simpleMessage.setSubject(message.getMailTitle());
		// simpleMessage.setText(message.toMailMessage());
		// sendImpl.send(simpleMessage);
	}

	public void asyncSend(MailMessage message) {

	}

	public static void main(String[] args) {
		EmailServiceImpl service = new EmailServiceImpl();

		ContentMailMessage mailMessage = new ContentMailMessage();
		mailMessage.setBindEmail("185247945@qq.com");
		mailMessage.setContent("1234");
		mailMessage.setMailTitle("aaa title");
		service.send(mailMessage);
	}
}
