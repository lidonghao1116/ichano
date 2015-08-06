package cn.ichano.common.cache.impl.mapdb;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.ichano.common.cache.LocalCache;

@Service
public class MapDbImpl implements LocalCache {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	DB db = null;
	HTreeMap<Object, Object> map = null;

	@PostConstruct
	public void init() {
		db = DBMaker.newMemoryDirectDB().closeOnJvmShutdown().transactionDisable().make();
		// 对象存入5分钟后 或者 读取之后2分钟 没有其他操作则超时 存入最大上限为100000
		map = db.createHashMap("icm").expireMaxSize(100000)
				.expireAfterWrite(5, TimeUnit.MINUTES)
				.expireAfterAccess(2, TimeUnit.MINUTES).make();
		logger.info("..MapDB init sucess!");
	}


	@Override
	public Object getValue(Object key) {
		return map.get(key);
	}

	@Override
	public void setValue(Object key, Object cla) {
		if(null==cla){
			logger.warn("MapDb key {} want set a null value", key);
			return;
		}
		map.put(key, cla);
	}


	@Override
	public void delValue(Object key) {
		map.remove(key);
	}

}
