package cn.ichano.common.message.push.service.impl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dbay.apns4j.model.Payload;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.em.EPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;

import cn.ichano.common.entity.push.message.ExtendsPushMessage;
import cn.ichano.common.message.StatService;
import cn.ichano.common.message.StatService.Plat;
import cn.ichano.common.message.StatService.Type;
import cn.ichano.common.message.push.service.PlatPushService;
import cn.ichano.common.message.push.util.ApnsUtil;
import cn.ichano.common.service.push.PushMessage;

/**
 * androidpush发送
 * 
 * @author wenjp
 *
 */
public class AndroidPushService implements PlatPushService {

	private static Logger LOGGER = LoggerFactory
			.getLogger(AndroidPushService.class);

	private String appKey;

	private String appId;

	IIGtPush push = null;

	public AndroidPushService(String api, String appKey, String masterKey,
			String appId) {
		super();

		this.appKey = appKey;

		this.appId = appId;

		push = new IGtPush(api, appKey, masterKey, true);
		try {
			push.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void push(String token, PushMessage message) {
		push(token, message.toPushMessage(), getTitle(message));

	}

	@Override
	public void push(String token, String message) {
		push(token, message, PushMessage.DEFAULT_TITLE);
	}

	public void push(String token, String message, String title) {
		StatService.getInstance().getPushStat()
				.addTotal(Type.NEED, Plat.ANDROID);
		try {
			SingleMessage singleMessage = new SingleMessage();
			NotificationTemplate template = new NotificationTemplate();

			template.setAppId(appId);
			template.setAppkey(appKey);

			Payload p = ApnsUtil.covernet(message);
			template.setTitle(title);
			template.setText(p.getAlert());
			template.setTransmissionContent(message);
			template.setTransmissionType(1);

			singleMessage.setData(template);
			singleMessage.setOffline(true);
			singleMessage.setOfflineExpireTime(2 * 3600 * 1000);

			Target target1 = new Target();
			target1.setAppId(appId);
			target1.setClientId(token);

			IPushResult ret = push.pushMessageToSingle(singleMessage, target1);
			if (EPushResult.RESULT_OK.equals(ret.getResultCode())) {
				StatService.getInstance().getPushStat()
						.addTotal(Type.REAL, Plat.ANDROID);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	/**
	 * 获得title对象
	 * 
	 * @param message
	 * @return
	 */
	private String getTitle(PushMessage message) {

		if (message instanceof ExtendsPushMessage) {
			String title = ((ExtendsPushMessage) message).getTitle();
			if (StringUtils.isEmpty(title)) {
				title = PushMessage.DEFAULT_TITLE;
			}
			return title;
		}

		return PushMessage.DEFAULT_TITLE;
	}

}
