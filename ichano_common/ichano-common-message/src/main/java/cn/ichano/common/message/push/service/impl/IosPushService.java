package cn.ichano.common.message.push.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.Feedback;
import com.dbay.apns4j.model.Payload;

import cn.ichano.common.message.StatService;
import cn.ichano.common.message.StatService.Plat;
import cn.ichano.common.message.StatService.Type;
import cn.ichano.common.message.push.service.PlatPushService;
import cn.ichano.common.message.push.util.ApnsUtil;
import cn.ichano.common.service.push.PushMessage;

public class IosPushService implements PlatPushService {

	private static Logger LOGGER = LoggerFactory
			.getLogger(IosPushService.class);

	private static final String APS = "aps";

	private String path;

	private String pass;

	private String name;

	/**
	 * 用于回调push发送的feedback
	 */
	private static List<IApnsService> apnsServiceList = new ArrayList<IApnsService>();

	public IosPushService(String path, String pass) {
		this.pass = pass;
		this.path = path;
		this.name = this.toString();
		Properties p = ApnsUtil.createProperties(name, path, pass);

		IApnsService apnsService = ApnsUtil.init(p);
		apnsServiceList.add(apnsService);
	}

	@Override
	public void push(String token, PushMessage message) {
		this.push(token, message.toPushMessage());
	}

	@Override
	public void push(String token, String message) {
		StatService.getInstance().getPushStat().addTotal(Type.NEED, Plat.IOS);

		IApnsService tmpApns = ApnsServiceImpl.getCachedService(name);
		if (tmpApns == null) {
			return;
		}

		Payload p = ApnsUtil.covernet(message);
		if (p == null) {
			LOGGER.error("payload is null, please check message:{}", message);
			return;
		}
		tmpApns.sendNotification(token, p);
	}

	public static List<Feedback> getFeedBack() {

		List<Feedback> feedbacks = new ArrayList<Feedback>();
		for (IApnsService apnsService : apnsServiceList) {
			List<Feedback> feedback = apnsService.getFeedbacks();
			if (feedback != null) {
				feedbacks.addAll(feedback);
			}
		}
		return feedbacks;
	}

}
