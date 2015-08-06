package cn.ichano.judge.entity;

public class Sequence {

	private int serverId;
	
	/**
	 * 实体名
	 */
	private String entity;
	
	/**
	 * 当前序列号
	 */
	private Long current;
	
	/**
	 * 批量获取数据量
	 */
	private int batch;
	
	/**
	 * 余数
	 */
	private Integer div;
	
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getEntity() {
		return entity;
	}


	public Integer getDiv() {
		return div;
	}

	public void setDiv(Integer div) {
		this.div = div;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Long getCurrent() {
		return current;
	}

	public void setCurrent(Long current) {
		this.current = current;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	
}
