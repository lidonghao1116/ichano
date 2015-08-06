package cn.ichano.common.cache.impl.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Service;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;


public class HessionRedisSerializer implements RedisSerializer<Object>{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
	
	@Override
	public byte[] serialize(Object object) throws SerializationException {
		byte[] result = null;
		ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
		Hessian2Output out = new Hessian2Output(byteArrayOutputStream); 
		try {
			out.writeObject(object);
			out.flush();
			out.close();
			result = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			logger.error("Hession serialize error,will use JdkSerializationRedisSerializer to serialize",e);
			result = serializer.serialize(object);
		}
		return result;
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		Object obj=null;
		if(bytes == null || bytes.length == 0){
			return obj;
		}
		ByteArrayInputStream  byteArrayInputStream = new ByteArrayInputStream(bytes);
		Hessian2Input in = new Hessian2Input(byteArrayInputStream);  
		try {
			obj = in.readObject();
		} catch (IOException e) {
			logger.error("Hession deserialize error,will use JdkSerializationRedisSerializer to deserialize",e);
			obj = serializer.deserialize(bytes);
		}
		return obj;
	}
	
	

}
