package cn.ichano.common.util.vo;

import java.util.HashMap;
import java.util.Map;

public class Status {

	int code;

	String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Status(int code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public Map<String,Object> convertMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("desc", desc);
		return map;
	}

}