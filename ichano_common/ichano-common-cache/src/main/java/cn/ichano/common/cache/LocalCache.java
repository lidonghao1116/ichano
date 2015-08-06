package cn.ichano.common.cache;


public interface LocalCache {
	
	
	public Object getValue(Object key);

	void setValue(Object key, Object cla);
	
	void delValue(Object key);

}
