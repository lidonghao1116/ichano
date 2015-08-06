package cn.ichano.common.util;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
/**
 * Json操作工具类.
 */
public class JsonUtil {



	    private static final ObjectMapper objectMapper = new ObjectMapper();
	    static {
	        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
	        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	        objectMapper.setSerializationInclusion(Include.NON_NULL); 
	    };
	    private static final ObjectWriter writer = objectMapper.writer();
	    private static final ObjectWriter prettyWriter = objectMapper.writerWithDefaultPrettyPrinter();

	    public static String toJsonPrettyString(Object value) {
	        try {
	            return prettyWriter.writeValueAsString(value);
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }

	    public static String toJsonString(Object value) {
	    	if(value == null){
	    		return null;
	    	}
	        try {
	            return writer.writeValueAsString(value);
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }

	    /**
	     * Returns the deserialized object from the given json string and target
	     * class; or null if the given json string is null.
	     */
	    public static <T> T fromJsonString(String json, Class<T> clazz) {
	        if (json == null)
	            return null;
	        try {
	            return objectMapper.readValue(json, clazz);
	        } catch (Exception e) {
	            throw new RuntimeException("Unable to parse Json String.", e);
	        }
	    }
	    
	    public static <T> T fromJsonString(String json, TypeReference<T> clazz) {
	        if (json == null)
	            return null;
	        try {
	            return objectMapper.readValue(json, clazz);
	        } catch (Exception e) {
	            throw new RuntimeException("Unable to parse Json String.", e);
	        }
	    }

	    public static JsonNode jsonNodeOf(String json) {
	        return fromJsonString(json, JsonNode.class);
	    }

	    public static JsonGenerator jsonGeneratorOf(Writer writer) throws IOException {
	        return new JsonFactory().createGenerator(writer);
	    }

	    public static ObjectMapper getObjectMapper() {
	        return objectMapper;
	    }

	    public static ObjectWriter getWriter() {
	        return writer;
	    }

	    public static ObjectWriter getPrettywriter() {
	        return prettyWriter;
	    }



	/**
	 * 将对象转换成json，在转换前插入值
	 * 
	 * @param o
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String toJsonAppendValue(Object o, String key,
			Object value) {

		Map<String, Object> readJSON2Map = null;
		if (o == null || o instanceof String || o instanceof Integer
				|| o instanceof List) {
			readJSON2Map = new HashMap<String, Object>();
			// return "";
		} else if (o instanceof Map) {
			readJSON2Map = (Map<String, Object>) o;
		} else {
			readJSON2Map = fromJsonToMap(toJsonString(o),Object.class);
		}
		readJSON2Map.put(key, value);
		return toJsonString(readJSON2Map);

	}
	
	/**
	 * 将对象转换成json，在转换前插入值
	 * 
	 * @param o
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String toJsonAppendValue(Object o, Map<String,Object> map) {

		Map<String, Object> readJSON2Map = null;
		if (o == null || o instanceof String || o instanceof Integer) {
			readJSON2Map = new HashMap<String, Object>();
			// return "";
		} else if (o instanceof Map) {
			readJSON2Map = (Map<String, Object>) o;
		} else {
			readJSON2Map = fromJsonToMap(toJsonString(o),Object.class);
		}
		readJSON2Map.putAll(map);
		return toJsonString(readJSON2Map);

	}

	/**
	 * 把jsonString字符串转换成{@code Map<String, Object>};
	 * 
	 * @param jsonString
	 * @return
	 */
	public static  Map<String, Object> fromJsonToMap(String jsonString) {
		return fromJsonToMap(jsonString, Object.class);
	}
	
	/**
	 * 把jsonString字符串转换成{@code Map<String, Object>};
	 * 
	 * @param jsonStrin
	 * @return
	 */
	public static <T> Map<String, T> fromJsonToMap(String jsonString,Class<T> t) {
		if(StringUtils.isEmpty(jsonString)){
			return null;
		}
		try {
			return fromJsonString(jsonString,
					new TypeReference<Map<String, T>>() {
					});
		} catch (Exception e) {
			throw new RuntimeException("Json Exception", e);
		}
	}

	/**
	 * 把jsonString字符串转换成{@code <String, Object>};
	 * @param <T>
	 * 
	 * @param jsonString
	 * @return
	 */
	public static <T> List<T> fromJsonToList(String jsonString,Class<T> t) {
		try {
			return fromJsonString(jsonString,
					new TypeReference<List<T>>() {
					});
		} catch (Exception e) {
			throw new RuntimeException("Json Exception", e);
		}
	}

	/**
	 * 将对象转换成字节数组
	 * 
	 * @param o
	 * @return
	 */
	public static byte[] toJsonBytes(Object o) {
		try {
			return objectMapper.writeValueAsBytes(o);
		} catch (Exception e) {
			throw new RuntimeException("Json Exception", e);
		}
	}

	public static void main(String[] args) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("aaa", "bbb");

		map.put("aaaa1", "bbbb1");
		System.out.println(toJsonAppendValue(map, "aaaa2", "bbbb2"));
	}

}
