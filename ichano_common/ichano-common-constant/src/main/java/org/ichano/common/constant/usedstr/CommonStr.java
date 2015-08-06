package org.ichano.common.constant.usedstr;

public final class CommonStr {
	
	
	public final static String CODE = "code"; 
	
	public final static String DESC = "desc"; 
	
	public final static String RETURN = "return"; 
	
	public final static String SERVICE = "service";
	/** 
	* @Fields PID : TODO(套餐包ID) 
	*/ 
	public final static String PID = "pid"; 
	/** 
	* @Fields POID : TODO(套餐ID) 
	*/ 
	public final static String POID = "poid"; 
	
	public final static String CID = "cid"; 
	
	/** 
	* @Fields TS : TODO(时间戳) 
	*/ 
	public final static String TS = "ts"; 
	/** 
	* @Fields CURRENCY : TODO(币种) 
	*/ 
	public final static String CURRENCY = "currency"; 
	/** 
	* @Fields AVSCID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	public final static String AVSCID = "avscid"; 
	
	/** 
	* @Fields SESSIONID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	public final static String SESSIONID = "sessionid";
	
	/** 
	* @Fields ACCOUNT : TODO(用户账号) 
	*/ 
	public final static String ACCOUNT = "account";
	
	/** 
	* @Fields POINT : TODO(积分) 
	*/ 
	public final static String POINT = "point";

	/** 
	* @Fields ORDERID : TODO(订单号) 
	*/ 
	public final static String ORDERID = "orderid";
	
	public final static String TRUE = "true";
	
	public final static String FALSE = "false";
	
	public final static String NULL = "null";

	public final static String TYPE = "type";
	
	public final static String BUSINESS = "business";
	
	/**
	 * 客户端类型
	 * 
	 * @author wenjp
	 *
	 */
	public static class Service {

		/**
		 * avs 端
		 */
		public static int AVS_SERVICE = 0;
		/**
		 * client端
		 */
		public static int CLIENT_SERVICE = 1;
	}

}
