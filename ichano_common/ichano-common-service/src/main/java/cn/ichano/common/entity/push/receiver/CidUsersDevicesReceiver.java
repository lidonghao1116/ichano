package cn.ichano.common.entity.push.receiver;

import cn.ichano.common.service.push.Receiver;

public class CidUsersDevicesReceiver implements Receiver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3347126251496747828L;

	private Long cid;

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

}
