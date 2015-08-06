package cn.ichano.common.util;

import java.util.HashMap;
import java.util.Map;

import org.ichano.common.constant.usedcode.ReturnCode;

public class ResponseV2Util {

	/**
	 * 返回成功
	 * 
	 * @return
	 */
	public static String responseSuccess() {
		return responseSuccess("");
	}

	/**
	 * 响应结果
	 * 
	 * @param o
	 * @return
	 */
	public static String responseSuccess(Object o) {
		int code = ReturnCode.SucessCode.SC1000;
		return response(code, o);
	}

	public static String response(int code, Object o) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("desc", o);
		return JsonUtil.toJsonString(map);
	}
	
	public static String responseError(int code) {
		return response(code, "");
	}

	public static void main(String[] args) {
		System.out.println(responseSuccess(null));
		System.out.println(responseSuccess(""));
		System.out.println(responseSuccess(12341234));
	}

}
