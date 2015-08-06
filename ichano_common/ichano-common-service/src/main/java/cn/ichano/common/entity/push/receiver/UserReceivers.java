package cn.ichano.common.entity.push.receiver;

import cn.ichano.common.service.push.Receiver;

public class UserReceivers implements Receiver {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
