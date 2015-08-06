package cn.ichano.common.service.push;

import cn.ichano.common.entity.info.DeviceInfo;



/**
 * 给指定的设备发送消息
 * 
 * @author wenjp
 *
 */
public interface PushService {

	/**
	 * 推送消息
	 * 
	 * @param receiver
	 * @param message
	 * @return
	 */
	public boolean push(DeviceInfo deviceInfo, PushMessage message);
	
}
