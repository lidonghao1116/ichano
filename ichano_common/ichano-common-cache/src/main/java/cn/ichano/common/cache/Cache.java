package cn.ichano.common.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * cache接口，便于后续扩展
 * @author wenjp
 *
 * @param <K>
 * @param <V>
 */
public interface Cache<K, V> {

	public abstract void set(K key, V value);

	public abstract void set(K key, V value, long timeout, TimeUnit unit);

	public abstract Boolean setIfAbsent(K key, V value);

	public abstract V get(K key);

	public abstract Long increment(K key, long delta);

	public abstract Double increment(K key, double delta);

	public abstract String get(K key, long start, long end);

	public abstract void set(K key, V value, long offset);

	public abstract void remove(K key);

	public abstract Long size(K key);

	public abstract void rename(K oldKey, K newKey);

	void setMap(K key, V value, String hashKey);

	void delMap(K key, String hashKey);

	Map<Object, Object> getMapByKey(K key);

	boolean hasKey(K key, String hashKey);

	List<Object> getListByKey(K key);

	<T> T getMap(String hashKey, Class<T> cla);

	<T> void setMap(String hashKey, T cla);

	void delMapKey(K key);

	Object getKeyMap(K key, String hashKey);

	boolean hasKey(K key);

}