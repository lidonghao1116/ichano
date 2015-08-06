package cn.ichano.common.entity.message;

public abstract class AbstractNoticeMessage implements NoticeMessage{

	private Urgent urgent = Urgent.NORMAL;
	
	private Sync sync = Sync.ASYNC;
	
	/**
	 * 
	 */
	public Urgent getUrgent(){
		return urgent;
	}

	public void setUrgent(Urgent urgent) {
		this.urgent = urgent;
	}

	/**
	 * 同步、异步
	 * 
	 * @return
	 */
	public Sync getSync(){
		return sync;
	}
	
	public void setSync(Sync sync) {
		this.sync = sync;
	}
	
}
