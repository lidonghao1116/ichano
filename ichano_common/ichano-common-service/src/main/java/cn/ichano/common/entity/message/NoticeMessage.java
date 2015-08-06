package cn.ichano.common.entity.message;

public interface NoticeMessage {

	public static enum Position {
		LOCAL, REMOTE
	}

	/**
	 * 紧急程度,IMMEDIATELY 会立即调用，其他等待批量调用
	 * 
	 * @author wenjp
	 *
	 */
	public static enum Urgent {
		IMMEDIATELY, NORMAL, NOT_URGENT
	}

	/**
	 * 同步or异步调用
	 * @author wenjp
	 *
	 */
	public static enum Sync{
		SYNC, ASYNC
	}

	/**
	 * 递送内容
	 * 
	 * @return
	 */
	public String getValue();

	public Urgent getUrgent();

	/**
	 * 同步、异步,只有紧急的此参数才有效
	 * 
	 * @return
	 */
	public Sync getSync();

	
	public void setValue(String value);
	
	public void setSync(Sync sync);
	
	public void setUrgent(Urgent urgent);
	

}
