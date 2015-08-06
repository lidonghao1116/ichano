package cn.ichano.common.cache.impl.redis;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import cn.ichano.common.cache.Cache;


@Service
public class RedisClientWapper<K,V> implements Cache<K, V> {
	
	@Autowired
	private RedisTemplate<K, V> redisTemplate;

	
	@PostConstruct
	public void init(){
		HessionRedisSerializer hessionRedisSerializer = new HessionRedisSerializer();

		StringRedisSerializer stringSerializer = new StringRedisSerializer();
		init(stringSerializer, hessionRedisSerializer);
	}
	
	public void init(RedisSerializer redisSerializer){
		redisTemplate.setDefaultSerializer(redisSerializer);
		//在配置中初始化，因redisTemplat在初始设置DefaultSerializer时会掉用propertiesChange,再次调用时不会。更改会导致在aop中redis反序列化默认哦jdk序列化
		init(redisSerializer, redisSerializer);
	}
	
	public void init(RedisSerializer key, RedisSerializer value) {
		// 在配置中初始化，因redisTemplat在初始设置DefaultSerializer时会掉用propertiesChange,再次调用时不会。更改会导致在aop中redis反序列化默认哦jdk序列化
		redisTemplate.setKeySerializer(key);
		redisTemplate.setValueSerializer(value);
		redisTemplate.setHashKeySerializer(key);
		redisTemplate.setHashValueSerializer(value);
	}

	public RedisTemplate<K, V> getRedisTemplate() {
		return this.redisTemplate;
	}
	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#set(K, V)
	 */
	@Override
	public void set(K key, V value){
		redisTemplate.opsForValue().set(key,value);
		
	}
	
	@Override
	public void setMap(K key, V value,String hashKey){
		redisTemplate.opsForHash().put(key, hashKey, value);
	}
	@Override
	public void delMap(K key,String hashKey){
		redisTemplate.opsForHash().delete(key, hashKey);
	}
	@Override
	public void delMapKey(K key){
		Set<Object> so = redisTemplate.opsForHash().keys(key);
		redisTemplate.opsForHash().delete(key, so);
	}
	@Override
	public Object getKeyMap(K key,String hashKey){
		return redisTemplate.opsForHash().get(key, hashKey);
	}
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getMap(String hashKey,Class<T> cla){
		return (T)redisTemplate.opsForHash().get((K)cla.getName(), hashKey);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void setMap(String hashKey,T cla){
		redisTemplate.opsForHash().put((K)cla.getClass().getName(), hashKey, cla);
	}
	
	@Override
	public Map<Object, Object> getMapByKey(K key){
		return redisTemplate.opsForHash().entries(key);
	}
	@Override
	public boolean hasKey(K key,String hashKey){
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}
	@Override
	public List<Object> getListByKey(K key){
		return redisTemplate.opsForHash().values(key);
	}
	
	
	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#set(K, V, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public void set(K key, V value, long timeout, TimeUnit unit){
		redisTemplate.opsForValue().set(key,value, timeout, unit);
	}

	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#setIfAbsent(K, V)
	 */
	@Override
	public Boolean setIfAbsent(K key, V value){
		return redisTemplate.opsForValue().setIfAbsent(key,value);
	}

	public void multiSet(Map<? extends K, ? extends V> m){
		redisTemplate.opsForValue().multiSet(m);
	}

	public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> m){
		return redisTemplate.opsForValue().multiSetIfAbsent(m);
	}
	
	public Set<K> keys(K key){
		return redisTemplate.keys(key);
	}

	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#get(K)
	 */
	@Override
	public V get(K key){
		return redisTemplate.opsForValue().get(key);
	}

	public V getAndSet(K key, V value){
		return redisTemplate.opsForValue().getAndSet(key, value);
	}

	public List<V> multiGet(Collection<K> keys){
		return redisTemplate.opsForValue().multiGet(keys);
	}

	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#increment(K, long)
	 */
	@Override
	public Long increment(K key, long delta){
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#increment(K, double)
	 */
	@Override
	public Double increment(K key, double delta){
		return redisTemplate.opsForValue().increment(key, delta);
	}

	public Integer append(K key, String value){
		return redisTemplate.opsForValue().append(key, value);
	}

	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#get(K, long, long)
	 */
	@Override
	public String get(K key, long start, long end){
		return redisTemplate.opsForValue().get(key, start, end);
	}

	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#set(K, V, long)
	 */
	@Override
	public void set(K key, V value, long offset){
		 redisTemplate.opsForValue().set(key, value, offset);
	}
	
	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#remove(K)
	 */
	@Override
	public void remove(K key){
		redisTemplate.delete(key);
	}
	
	public void batchRemove(Collection<K> keys){
		redisTemplate.delete(keys);
	}
	
	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#size(K)
	 */
	@Override
	public Long size(K key){
		return redisTemplate.opsForValue().size(key);
	}
	
	public void watch(K key){
		redisTemplate.watch(key);
	}

	public void BatchWatch(Collection<K> keys){
		redisTemplate.watch(keys);
	}
	
	public List<V> sort(SortQuery<K> query){
		return redisTemplate.sort(query);
	}

	public <T> List<T> sort(SortQuery<K> query, RedisSerializer<T> resultSerializer){
		return redisTemplate.sort(query,resultSerializer);
	}
	
	public Long sort(SortQuery<K> query, K storeKey){
		return redisTemplate.sort(query, storeKey);
	}
	
	/* (non-Javadoc)
	 * @see cn.ichano.common.cache.impl.redis.Cache#rename(K, K)
	 */
	@Override
	public void rename(K oldKey, K newKey){
		 redisTemplate.rename(oldKey, newKey);
	}

	public Boolean renameIfAbsent(K oldKey, K newKey){
		return redisTemplate.renameIfAbsent(oldKey, newKey);
	}

	public Boolean expire(K key, long timeout, TimeUnit unit){
		return redisTemplate.expire(key, timeout, unit);
	}

	public Boolean expireAt(K key, Date date){
		return redisTemplate.expireAt(key, date);
	}
	
	@Override
	public boolean hasKey(K key){
		return redisTemplate.hasKey(key);
	}
}
