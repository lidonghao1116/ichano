package cn.ichano.common.service.info;

import java.util.List;

import cn.ichano.common.entity.info.DeviceInfo;

public interface DeviceInfoService {
	
	DeviceInfo getDeviceInfo(DeviceInfo dvInfo);

	/**
	 * 获取cid信息，默认为1
	 * 
	 * @param cid
	 * @return
	 */
	DeviceInfo getDeviceInfo(String cid);

	/**
	 * 获取cid信息
	 * 
	 * @param cid
	 * @return
	 */
	DeviceInfo getDeviceInfo(String cid, int service);

	/**
	 * 清楚client端APPle设备的token。<br>
	 * 此接口可以异步执行，不需要实时
	 * 
	 * @param tokens
	 */
	public void clearToken(List<String> tokens);

	/** 
	* @Title: getCidByLicense 
	* @Description: 通过license获取cid
	* @param @param license
	* @param @return 
	* @return Long
	* @throws 
	*/
	Long getCidByLicense(String license);

	/** 
	* @Title: updateAvsCloudConfig 
	* @Description: user系统调用info对云存储配置开关
	* @param @param cloudswitch
	* @param @param cid
	* @param @return 
	* @return int
	* @throws 
	*/
	int updateAvsCloudConfig(boolean cloudswitch, Long cid);

}
