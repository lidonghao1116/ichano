package cn.ichano.common.message.push.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ichano.common.entity.info.DeviceInfo;
import cn.ichano.common.entity.push.message.ExtendsPushMessage;
import cn.ichano.common.entity.push.receiver.DeviceReceiver;
import cn.ichano.common.message.push.service.PlatPushService;
import cn.ichano.common.service.push.PushMessage;
import cn.ichano.common.service.push.PushService;
import cn.ichano.common.service.push.Receiver;

//@Service
public class PushServiceImpl implements PushService {


	@Autowired
	private PlatPushFactoryService platPushFactoryService;

	@Override
	public boolean push(DeviceInfo deviceInfo, PushMessage message) {
		if (StringUtils.isEmpty(deviceInfo.getAppId())
				|| StringUtils.isEmpty(deviceInfo.getToken())) {
			return false;
		}

		DeviceReceiver receiver = new DeviceReceiver();
		receiver.setAppId(deviceInfo.getAppId());
		receiver.setLanguage(deviceInfo.getLanguage());
		receiver.setToken(deviceInfo.getToken());

		push(receiver, message);
		return true;
	}

	
	private boolean push(Receiver receiver, PushMessage message) {
		// if (receiver instanceof CidUsersDevicesReceiver) {
		// String cid = String.valueOf(((CidUsersDevicesReceiver) receiver)
		// .getCid());
		//
		// List<DeviceInfo> deviceInfoList = infoDao
		// .queryDeviceInfoListByCid(cid);
		// processDeviceInfo(message, deviceInfoList);
		// } else if (receiver instanceof UserReceivers) {
		// String userId = ((UserReceivers) receiver).getUserId();
		// List<DeviceInfo> deviceInfoList = infoDao
		// .queryDeviceInfoListByuserId(userId);
		// processDeviceInfo(message, deviceInfoList);
		//
		// } else if (receiver instanceof DeviceReceiver) {
			DeviceReceiver deviceReceiver = (DeviceReceiver) receiver;
			invokeLanguage(message, deviceReceiver.getLanguage());
			PlatPushService plat = platPushFactoryService
					.getPlatPushService(deviceReceiver.getAppId());
			plat.push(deviceReceiver.getToken(), message);
		// }
		return true;
	}


	/**
	 * 赋值给message对象
	 * 
	 * @param message
	 * @param language
	 */
	private void invokeLanguage(PushMessage message, int language) {
		if (message instanceof ExtendsPushMessage) {
			((ExtendsPushMessage) message).setLanguage(language);
		}

	}

}
