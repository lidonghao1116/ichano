package org.ichano.common.constant.usedcode;

public class ReturnCode {

	private ReturnCode() {
	}
	
	/** 
	* @ClassName: DeviceType 
	* @Description: TODO(设备类型编码) 
	* @author zhangshaoyun 
	* @date 2015年3月13日 下午1:59:36  
	*/
	public class DeviceType{
		/** 
		* @Fields DT0 : TODO(default) 
		*/ 
		public final static int DT0 = 0;
		
		/** 
		* @Fields DT1 : TODO(windowpc) 
		*/ 
		public final static int DT1 = 1;
		
		/** 
		* @Fields DT2 : TODO(iphone) 
		*/ 
		public final static int DT2 = 2;
		
		/** 
		* @Fields DT3 : TODO(ipad) 
		*/ 
		public final static int DT3 = 3;
		
		/** 
		* @Fields DT4 : TODO(android phone) 
		*/ 
		public final static int DT4 = 4;
		
		/** 
		* @Fields DT5 : TODO(android tv) 
		*/ 
		public final static int DT5 = 5;
		
		/** 
		* @Fields DT6 : TODO(ipcam) 
		*/ 
		public final static int DT6 = 6;
		
		/** 
		* @Fields DT7 : TODO(mac pc) 
		*/ 
		public final static int DT7 = 7;
	}

	/**
	 * @ClassName: DeviceType
	 * @Description: TODO(设备类型编码 枚举型定义)
	 * @author zhangshaoyun
	 * @date 2015年3月12日 下午6:51:57
	 */
	public enum DeviceTypeEm {
		DT0 {
			@Override
			public int getCode() {
				return 0;
			}
			@Override
			public String getName() {
				return "default";
			}
		},
		DT1 {
			@Override
			public int getCode() {
				return 1;
			}
			@Override
			public String getName() {
				return "windowpc";
			}
		},
		DT2 {
			@Override
			public int getCode() {
				return 2;
			}
			@Override
			public String getName() {
				return "iphone";
			}
		},
		DT3 {
			@Override
			public int getCode() {
				return 3;
			}
			@Override
			public String getName() {
				return "ipad";
			}
		},
		DT4 {
			@Override
			public int getCode() {
				return 4;
			}
			@Override
			public String getName() {
				return "android phone";
			}
		},
		DT5 {
			@Override
			public int getCode() {
				return 5;
			}
			@Override
			public String getName() {
				return "android tv";
			}
		},
		DT6 {
			@Override
			public int getCode() {
				return 6;
			}
			@Override
			public String getName() {
				return "ipcam";
			}
		},
		DT7 {
			@Override
			public int getCode() {
				return 7;
			}
			@Override
			public String getName() {
				return "mac pc";
			}
		};
		public abstract int getCode();
		public abstract String getName();
	}
	
	/** 
	* @ClassName: OsType 
	* @Description: TODO(操作系统类型) 
	* @author zhangshaoyun 
	* @date 2015年3月13日 下午2:04:21  
	*/
	public class OsType{
		/** 
		* @Fields OS0 : TODO(default) 
		*/ 
		public final static int OS0 = 0;
		
		/** 
		* @Fields OS1 : TODO(windows) 
		*/ 
		public final static int OS1 = 1;
		
		/** 
		* @Fields OS2 : TODO(ios) 
		*/ 
		public final static int OS2 = 2;
		
		/** 
		* @Fields OS3 : TODO(mac) 
		*/ 
		public final static int OS3 = 3;
		
		/** 
		* @Fields OS4 : TODO(android) 
		*/ 
		public final static int OS4 = 4;
		
		/** 
		* @Fields OS5 : TODO(ipcam linux) 
		*/ 
		public final static int OS5 = 5;
		
		/** 
		* @Fields OS6 : TODO(WEB) 
		*/ 
		public final static int OS6 = 6;
	}

	/** 
	* @ClassName: OsType 
	* @Description: TODO(操作系统类型 枚举型) 
	* @author zhangshaoyun 
	* @date 2015年3月13日 下午2:02:24  
	*/
	public enum OsTypeEm {
		OS0 {
			@Override
			public int getCode() {
				return 0;
			}
			@Override
			public String getName() {
				return "default";
			}
		},
		OS1 {
			@Override
			public int getCode() {
				return 1;
			}
			@Override
			public String getName() {
				return "windows";
			}
		},
		OS2 {
			@Override
			public int getCode() {
				return 2;
			}
			@Override
			public String getName() {
				return "ios";
			}
		},
		OS3 {
			@Override
			public int getCode() {
				return 3;
			}
			@Override
			public String getName() {
				return "mac";
			}
		},
		OS4 {
			@Override
			public int getCode() {
				return 4;
			}
			@Override
			public String getName() {
				return "android";
			}
		},
		OS5 {
			@Override
			public int getCode() {
				return 5;
			}
			@Override
			public String getName() {
				return "ipcam linux";
			}
		},
		OS6 {
			@Override
			public int getCode() {
				return 6;
			}
			@Override
			public String getName() {
				return "WEB";
			}
		};
		public abstract int getCode();
		public abstract String getName();
	}

	/** 
	* @ClassName: TerminalServerType 
	* @Description: TODO(终端服务类型) 
	* @author zhangshaoyun 
	* @date 2015年3月13日 下午2:08:14  
	*/
	public class TerminalServerType{
		/** 
		* @Fields TST0 : TODO(Streamer) 
		*/ 
		public final static int TST0 = 0;
		
		/** 
		* @Fields TST1 : TODO(Viewer) 
		*/ 
		public final static int TST1 = 1;
	}
	
	/** 
	* @ClassName: SoftwareType 
	* @Description: TODO(软件类型) 
	* @author zhangshaoyun 
	* @date 2015年3月13日 下午2:10:57  
	*/
	public class SoftwareType{
		/** 
		* @Fields SFT1 : TODO(viewer iOS cn) 
		*/ 
		public final static int SFT1 = 1;
		/** 
		* @Fields SFT2 : TODO(viewer iOS us) 
		*/ 
		public final static int SFT2 = 2;
		
		/** 
		* @Fields SFT4 : TODO(viewer android) 
		*/ 
		public final static int SFT4 = 4;
		
		/** 
		* @Fields SFT5 : TODO(viewer windows) 
		*/ 
		public final static int SFT5 = 5;
		
		/** 
		* @Fields SFT6 : TODO(viewer web) 
		*/ 
		public final static int SFT6 = 6;
		
		/** 
		* @Fields SFT7 : TODO(viewer public camera iOS) 
		*/ 
		public final static int SFT7 = 7;
		
		/** 
		* @Fields SFT8 : TODO(viewer public camera android) 
		*/ 
		public final static int SFT8 = 8;
		
		/** 
		* @Fields SFT17 : TODO(AVS windows) 
		*/ 
		public final static int SFT17 = 17;
		
		/** 
		* @Fields SFT18 : TODO(AVS MAC) 
		*/ 
		public final static int SFT18 = 18;
		
		/** 
		* @Fields SFT19 : TODO( AVS iOS) 
		*/ 
		public final static int SFT19 = 19;
		
		/** 
		* @Fields SFT21 : TODO(AVS Android Phone) 
		*/ 
		public final static int SFT21 = 21;
		
		/** 
		* @Fields SFT22 : TODO(AVS AndroidTV) 
		*/ 
		public final static int SFT22 = 22;
		
		/** 
		* @Fields SFT23 : TODO(AVS IPCam) 
		*/ 
		public final static int SFT23 = 23;
		
		/** 
		* @Fields SFT24 : TODO(Android IPCam) 
		*/ 
		public final static int SFT24 = 24;
	}
	
	/** 
	* @ClassName: TokenType 
	* @Description: TODO(token类型) 
	* @author zhangshaoyun 
	* @date 2015年3月13日 下午2:17:02  
	*/
	public class TokenType{
		/** 
		* @Fields TKT0 : TODO(IOS CN) 
		*/ 
		public final static int TKT0 = 0;
		/** 
		* @Fields TKT1 : TODO(Google) 
		*/ 
		public final static int TKT1 = 1;
		/** 
		* @Fields TKT2 : TODO(JPUSH) 
		*/ 
		public final static int TKT2 = 2;
		/** 
		* @Fields TKT3 : TODO(umeng) 
		*/ 
		public final static int TKT3 = 3;
		/** 
		* @Fields TKT4 : TODO(GeTui Android) 
		*/ 
		public final static int TKT4 = 4;
		/** 
		* @Fields TKT5 : TODO(iOS EN) 
		*/ 
		public final static int TKT5 = 5;
		/** 
		* @Fields TKT6 : TODO(iOS public) 
		*/ 
		public final static int TKT6 = 6;
		/** 
		* @Fields TKT7 : TODO(Android GeTui Public) 
		*/ 
		public final static int TKT7 = 7;
	}
	
	/** 
	* @ClassName: LanguageType 
	* @Description: TODO(语言类型) 
	* @author zhangshaoyun 
	* @date 2015年3月13日 下午2:20:33  
	*/
	public class LanguageType{
		/** 
		* @Fields TKT0 : TODO(Default) 
		*/ 
		public final static int LGT0 = 0;
		/** 
		* @Fields LGT1 : TODO(CN) 
		*/ 
		public final static int LGT1 = 1;
		/** 
		* @Fields LGT2 : TODO(EN) 
		*/ 
		public final static int LGT2 = 2;
		/** 
		* @Fields LGT3 : TODO(繁体中文) 
		*/ 
		public final static int LGT3 = 3;
		/** 
		* @Fields LGT4 : TODO(法语) 
		*/ 
		public final static int LGT4 = 4;
		/** 
		* @Fields LGT5 : TODO(日语) 
		*/ 
		public final static int LGT5 = 5;
		/** 
		* @Fields LGT6 : TODO(西班牙语) 
		*/ 
		public final static int LGT6 = 6;
		/** 
		* @Fields LGT7 : TODO(韩语) 
		*/ 
		public final static int LGT7 = 7;
	}
	
	/** 
	* @ClassName: SucessCode 
	* @Description: TODO(响应成功编码) 
	* @author zhangshaoyun 
	* @date 2015年3月13日 下午2:35:13  
	*/
	public class SucessCode {
		/**
		 * @Fields sc1000 : TODO(响应成功)
		 */
		public final static int SC1000 = 1000;

		/**
		 * @Fields sc1007 : TODO(不需要更新)
		 */
		public final static int SC1007 = 1007;

	}

	/**
	 * @ClassName: ErroCode
	 * @Description: TODO(响应失败编码)
	 * @author zhangshaoyun
	 * @date 2015年3月10日 下午2:07:03
	 */
	public class ErroCode {
		/**
		 * @Fields ER1001 : TODO(无效参数)
		 */
		public final static int ER1001 = 1001;

		/**
		 * @Fields ER1002 : TODO(失败)
		 */
		public final static int ER1002 = 1002;
		/**
		 * @Fields ER1003 : TODO(session失效)
		 */
		public final static int ER1003 = 1003;

		/**
		 * @Fields ER1004 : TODO(解析异常)
		 */
		public final static int ER1004 = 1004;
		
		/**
		 * @Fields ER1005 : TODO(用户名密码错误／账号不存在)
		 */
		public final static int ER1005 = 1005;

		/** 
		* @Fields ER1006 : TODO(该deviceid已存在／账号已存在) 
		*/ 
		public final static int ER1006 = 1006;
		/** 
		* @Fields ER1008 : TODO(该cid已存在) 
		*/ 
		public final static int ER1008 = 1008;
		/** 
		* @Fields ER1009 : TODO(密码错误 avs) 
		*/ 
		public final static int ER1009 = 1009;
		/** 
		* @Fields ER1010 : TODO(该cid已删除) 
		*/ 
		public final static int ER1010 = 1010;
		/** 
		* @Fields ER1011 : TODO(该用户为免费用户) 
		*/ 
		public final static int ER1011 = 1011;
		/** 
		* @Fields ER1012 : TODO(授权码失效) 
		*/ 
		public final static int ER1012 = 1012;
		/** 
		* @Fields ER1013 : TODO(deviceid找不到相关信息) 
		*/ 
		public final static int ER1013 = 1013;
		/** 
		* @Fields ER1014 : TODO(the notice not reg) 
		*/ 
		public final static int ER1014 = 1014;
		/** 
		* @Fields ER1015 : TODO(cid不支持设备类型) 
		*/ 
		public final static int ER1015 = 1015;
		/** 
		* @Fields ER1016 : TODO(该cid未注册激活) 
		*/ 
		public final static int ER1016 = 1016;
		/** 
		* @Fields ER1018 : TODO(授权过期/该cid被绑定并独占不允许添加) 
		*/ 
		public final static int ER1018 = 1018;
		/** 
		* @Fields ER1020 : TODO(用户名不存在) 
		*/ 
		public final static int ER1020 = 1020;
		/** 
		* @Fields ER1021 : TODO(cid info未上传) 
		*/ 
		public final static int ER1021 = 1021;
		/** 
		* @Fields ER1027 : TODO(需要更新cid lis) 
		*/ 
		public final static int ER1027 = 1027;
		/** 
		* @Fields ER1060 : TODO(通过授权码获取cid值) 
		*/ 
		public final static int ER1060 = 1060;
		
		/** 
		* @Fields ER1100 : TODO(数据库执行失败) 
		*/ 
		public final static int ER1100 = 1100;
	}
	/** 
	* @ClassName: SDKSucCode 
	* @Description: TODO(SDK响应成功编码 默认1000) 
	* @author zhangshaoyun 
	* @date 2015年5月15日 下午2:21:55  
	*/
	public static class SDKSucCode{
		/** 
		* @Fields SUC1001 : TODO(鉴权预授权获取) 
		*/ 
		public static final int SUC1001 = 1001;
		/** 
		* @Fields SUC1002 : TODO(鉴权symbol有效，获取cid/cpass) 
		*/ 
		public static final int SUC1002 = 1002;
		/** 
		* @Fields SUC1003 : TODO(该avs正在初始化中) 
		*/ 
		public static final int SUC1003 = 1003;
		/** 
		* @Fields SUC1004 : TODO(信息不需要更新) 
		*/ 
		public static final int SUC1004 = 1004;
		/** 
		* @Fields SUC1005 : TODO(鉴权成功，但cid重复，需要重新生成ci) 
		*/ 
		public static final int SUC1005 = 1005;

		/**
		 * 设备已经注册
		 */
		public static final int SUC1006 = 1006;
		/** 
		* @Fields SUCDEF : TODO(默认) 
		*/ 
		public static final int SUCDEF = 1000;
	}
	
	/** 
	* @ClassName: SDKErrCode 
	* @Description: TODO(SDK 响应错误码) 
	* @author zhangshaoyun 
	* @date 2015年5月15日 下午2:23:16  
	*/
	public static class SDKErrCode{
		/** 
		* @ClassName: Er1110 
		* @Description: TODO(1110错误类 无效参数) 
		* @author zhangshaoyun 
		* @date 2015年5月15日 下午2:23:35  
		*/
		public static class Er1110{
			/** 
			* @Fields ER1111 : TODO(参数不匹配) 
			*/ 
			public static final int ER1111 = 1111;
			/** 
			* @Fields ER1112 : TODO(解析异常) 
			*/ 
			public static final int ER1112 = 1112;

			/**
			 * @Fields ER1112 : TODO(版本号错误/不兼容)
			 */
			public static final int ER1113 = 1113;
			/** 
			* @Fields ERDEF : TODO(默认) 
			*/ 
			public static final int ERDEF = 1110;
		}
		/** 
		* @ClassName: Er1120 
		* @Description: TODO(1120错误类 无权限操作) 
		* @author zhangshaoyun 
		* @date 2015年5月15日 下午2:24:25  
		*/
		public static class Er1120{
			/** 
			* @Fields ER1121 : TODO(该avs没有该项业务配置) 
			*/ 
			public static final int ER1121 = 1121;
			/** 
			* @Fields ER1122 : TODO(修改avs的部分配置没有权限) 
			*/ 
			public static final int ER1122 = 1122;
			/** 
			* @Fields ER1123 : TODO(没有对应接口的调用权限) 
			*/ 
			public static final int ER1123 = 1123;

			/**
			 * 设备没有初始化
			 */
			public static final int ER1124 = 1124;
			/** 
			* @Fields ERDEF : TODO(默认) 
			*/ 
			public static final int ERDEF = 1120;
		}
		/** 
		* @ClassName: Er1130 
		* @Description: TODO(授权失败) 
		* @author zhangshaoyun 
		* @date 2015年5月15日 下午2:25:10  
		*/
		public static class Er1130{
			/** 
			* @Fields ER1131 : TODO(company校验失败) 
			*/ 
			public static final int ER1131 = 1131;
			/** 
			* @Fields ER1132 : TODO(appid 不存在) 
			*/ 
			public static final int ER1132 = 1132;
			/** 
			* @Fields ER1133 : TODO(授权码无效) 
			*/ 
			public static final int ER1133 = 1133;
			/** 
			* @Fields ER1134 : TODO(授权已满) 
			*/ 
			public static final int ER1134 = 1134;
			/** 
			* @Fields ERDEF : TODO(1130错误类默认) 
			*/ 
			public static final int ERDEF = 1130;
		}
		/** 
		* @ClassName: Er1140 
		* @Description: TODO(1140类错误client端获取avs信息配置没有权限) 
		* @author zhangshaoyun 
		* @date 2015年5月15日 下午2:27:10  
		*/
		public static class Er1140{
			/** 
			* @Fields ER1141 : TODO(该avs 不支持client设备) 
			*/ 
			public static final int ER1141 = 1141;
			/** 
			* @Fields ER1142 : TODO(该avs cid 不存在) 
			*/ 
			public static final int ER1142 = 1142;
			/** 
			* @Fields ERDEF : TODO(1140默认) 
			*/ 
			public static final int ERDEF = 1140;
		}
		/** 
		* @Fields ER1199 : TODO(其他错误) 
		*/ 
		public static final int ER1199 = 1199;
	}
	
	/**
	 * 从1101开始的失败code
	 * 
	 * @author wenjp
	 *
	 */
	// public static class SdkErrorCode {
	//
	// /**
	// * 失败
	// */
	// public static final int ER1101 = 1101;
	//
	// /**
	// * @Fields ER1001 : TODO(无效参数)
	// */
	// public final static int ER1102 = 1102;
	//
	// /**
	// * @Fields ER1003 : TODO(请求参数不匹配)
	// */
	// public final static int ER1103 = 1103;
	//
	// /**
	// * @Fields ER1004 : TODO(解析异常)
	// */
	// public final static int ER1104 = 1104;
	//
	// /**
	// * @Fields ER1004 : TODO(设备还未初始化结束)
	// */
	// public final static int ER1105 = 1105;
	//
	// /**
	// * @Fields ER1006 : TODO(该deviceid已存在／账号已存在)
	// */
	// public final static int ER1106 = 1106;
	// /**
	// * @Fields ER1008 : TODO(该cid已存在)
	// */
	// public final static int ER1108 = 1108;
	//
	// /**
	// * 没有指定设备操作权限
	// */
	// public final static int ER1109 = 1109;
	//
	// /**
	// * 没有对应数据操作权限
	// *
	// */
	// public final static int ER1110 = 1110;
	//
	// /**
	// * 部分数据没有权限
	// */
	// public final static int ER1111 = 1111;
	//
	// /**
	// * 没有接口调用权限
	// */
	// public final static int ER1112 = 1112;
	//
	// /**
	// * 公司校验码无效
	// */
	// public final static int ER1113 = 1113;
	//
	// /**
	// * 应用校验错误
	// */
	// public final static int ER1114 = 1114;
	//
	// /**
	// * 授权无效:已满、无效、过期
	// */
	// public final static int ER1115 = 1115;
	//
	// /**
	// *
	// * CID重复.
	// */
	// public final static int ER1116 = 1116;
	// /**
	// * 有预授权
	// */
	// public final static int ER1117 = 1117;
	//
	// /**
	// * 不需要更新
	// */
	// public final static int ER1120 = 1120;
	//
	// }
	
	/** 
	* @ClassName: SequenceTable 
	* @Description: TODO(ID序列表名) 
	* @author zhangshaoyun 
	* @date 2015年3月13日 下午2:37:26  
	*/
	public class SequenceTable{
		/** 
		* @Fields ZY_MEMBERS : TODO(用户表) 
		*/ 
		public final static String ZY_MEMBERS = "zy_members";
		/** 
		* @Fields ZY_MEMBERS_SESSION : TODO(用户session表) 
		*/ 
		public final static String ZY_MEMBERS_SESSION = "zy_members_session";
		/** 
		* @Fields ZY_PRO_CID_LIST : TODO(用户AVS套餐列表) 
		*/ 
		public final static String ZY_PRO_CID_LIST = "zy_pro_cid_list";
		/** 
		* @Fields ZY_PRO_PACKAGE_RECORD : TODO(用户购买套餐记录表) 
		*/ 
		public final static String ZY_PRO_PACKAGE_RECORD = "zy_pro_package_record";
		/** 
		* @Fields ZY_POINT_RECORD : TODO(积分记录表) 
		*/ 
		public final static String ZY_POINT_RECORD = "zy_point_record";
		/** 
		* @Fields ZY_DEVICE_SERVICE_AVS : TODO(zy_device_service_avs) 
		*/ 
		public final static String ZY_DEVICE_SERVICE_AVS = "zy_device_service_avs";
		/** 
		* @Fields ZY_PRO_PACKAGE : TODO(套餐包) 
		*/ 
		public final static String ZY_PRO_PACKAGE = "zy_pro_package";
		/** 
		* @Fields ZY_PRO_PACKAGE_OBJ : TODO(套餐) 
		*/ 
		public final static String ZY_PRO_PACKAGE_OBJ = "zy_pro_package_obj";
		/** 
		* @Fields ZY_DEVICE_SERVICE_CLIENT : TODO(zy_device_service_client) 
		*/ 
		public final static String ZY_DEVICE_SERVICE_CLIENT = "zy_device_service_client";
	}
	
	/**
	 * @ClassName: Currency
	 * @Description: TODO(币种集合,以PAYPAL为准)为了防止以后工程拆分后部分工程不需要paypalSDK
	 * @author zhangshaoyun
	 * @date 2015年3月20日 下午2:18:25
	 */
	public class Currency{
		/** 
		* @Fields CNY : TODO(中国-人民币) 
		*/ 
		public final static String CNY = "CNY";
		
		/** 
		* @Fields HKD : TODO(香港-港币) 
		*/ 
		public final static String HKD = "HKD";
		
		/** 
		* @Fields TWD : TODO(台湾-新台币) 
		*/ 
		public final static String TWD = "TWD";
		
		/** 
		* @Fields KRW : TODO(韩国-韩元) 
		*/ 
		public final static String KRW = "KRW";
		
		/** 
		* @Fields JPY : TODO(日本-日元) 
		*/ 
		public final static String JPY = "JPY";
		
		/** 
		* @Fields AUD : TODO(澳大利亚-澳元) 
		*/ 
		public final static String AUD = "AUD";
		
		/** 
		* @Fields CAD : TODO(加拿大-加元) 
		*/ 
		public final static String CAD = "CAD";
		
		/** 
		* @Fields EUR : TODO(欧洲区域-欧元) 
		*/ 
		public final static String EUR = "EUR";
		
		/** 
		* @Fields GBP : TODO(英国-英镑) 
		*/ 
		public final static String GBP = "GBP";
		
		/** 
		* @Fields USD : TODO(美国-美元) 
		*/ 
		public final static String USD = "USD";
	}
	
	/** 
	* @ClassName: paytype 
	* @Description: TODO(支付平台类型) 
	* @author zhangshaoyun 
	* @date 2015年3月20日 下午3:22:04  
	*/
	public class paytype{
		/** 
		* @Fields PAYPAL : TODO(paypal支付) 
		*/ 
		public final static String PAYPAL = "paypal";
		/** 
		* @Fields IOSPAY : TODO(苹果支付) 
		*/ 
		public final static String IOSPAY = "iospay";
		/** 
		* @Fields ALIPAY : TODO(支付宝) 
		*/ 
		public final static String ALIPAY = "alipay";
		/** 
		* @Fields POINT : TODO(积分支付) 
		*/ 
		public final static String POINT = "point";
	}
}
