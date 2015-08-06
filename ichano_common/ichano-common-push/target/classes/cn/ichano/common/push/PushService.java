package cn.ichano.common.push;


/**
 * 消息推送接口
 * @author wenjp
 *
 */
public interface PushService {

	
	public boolean push(Platform plat,Message message);
	
}
