package cn.ichano.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceUtils {
	private final static Logger logger = LoggerFactory
			.getLogger(DeviceUtils.class);
	/**
	 * @Title: versionCompar
	 * @Description: 判断版本号大小 第一个参数>=第二个参数为true
	 * @param @param hightV
	 * @param @param lowV
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean versionCompar(String hightV, String lowV) {
		//版本号str 转换为 整型数组
		int[] hightI = getIntArryByVersionStr(hightV);
		if (null == hightI) {
			return false;
		}
		int[] lowI = getIntArryByVersionStr(lowV);
		if (null == lowI) {
			return false;
		}
		//两个数组按位比较
		int hl = 0;
		if(hightI.length>=lowI.length){
			hl = hightI.length;
		}else{
			hl = lowI.length;
		}
		for (int i = 0; i < hl; i++) {
			if (i < lowI.length && i < hightI.length) {
				if (hightI[i] > lowI[i]) {
					return true;
				} else if (hightI[i] < lowI[i]) {
					return false;
				}
			}else{
				if(i>=lowI.length){
					if(hightI[i] >0){
						return true;
					}
				}
				if(i>=hightI.length){
					if(lowI[i] >0){
						return false;
					}
				}
			}
		}
		return true;
	}

	public static int[] getIntArryByVersionStr(String verStr) {
		if (StringUtils.isEmpty(verStr)) {
			return null;
		}
		String[] vs = verStr.split("\\.");
		if (vs.length == 1) {
			return new int[] { Integer.valueOf(verStr) };
		}
		int[] vi = new int[vs.length];
		for (int i = 0; i < vs.length; i++) {
			int di = 0;
			try {
				di = Integer.valueOf(vs[i]);
			} catch (Exception e) {
				logger.error("Check verStr {} faild",verStr);
			}
			vi[i] = di;
		}
		return vi;
	}
	
	/**
	 * @Title: filterMap
	 * @Description: 过滤不在入参中的字段
	 * @param @param resultMap 需过滤的map
	 * @param @param filterStr 过滤用的字符串 以,分隔
	 * @return void
	 * @throws
	 */
	public static void filterMap(Map<String,Object> resultMap,String filterStr){
		if(!StringUtils.isEmpty(filterStr)){
			String[] fstrarry = filterStr.split(",");
			List<String> fsList = Arrays.asList(fstrarry);

			List<String> removeList = new ArrayList<String>();
			Set<Entry<String, Object>> keySet = resultMap.entrySet();
			for (Entry<String, Object> key : keySet) {
				if (!fsList.contains(key.getKey())) {
					removeList.add(key.getKey());
				}
			
			}

			for (String key : removeList) {
				resultMap.remove(key);
			}

		}
	}

//	public static final void main(String[] args) {
//
//		System.out.println(versionCompar("3", "2.1.12"));
//	}
}
