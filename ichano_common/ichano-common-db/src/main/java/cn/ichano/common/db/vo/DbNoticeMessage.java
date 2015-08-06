package cn.ichano.common.db.vo;

import java.io.Serializable;

import cn.ichano.common.entity.message.AbstractNoticeMessage;
import cn.ichano.common.util.JsonUtil;

public class DbNoticeMessage extends AbstractNoticeMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7676068581886992402L;
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
		
	public String toString(){
		return JsonUtil.toJsonString(this);

	}
}
