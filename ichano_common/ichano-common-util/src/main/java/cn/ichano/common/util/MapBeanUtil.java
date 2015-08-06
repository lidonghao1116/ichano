package cn.ichano.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MapBeanUtil {
	private final static Logger logger = LoggerFactory
			.getLogger(MapBeanUtil.class);

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> T map2Bean(Class<T> t, Map<String, Object> map)
			throws Exception {
		Class clazz = t;
		// 实例化类
		T entity = (T) clazz.newInstance();
		mapAppendToBean(entity, map);
		return entity;
	}

	@SuppressWarnings("rawtypes")
	public static <T> T mapAppendToBean(T entity, Map<String, ? extends Object> map) {
		Class clazz = entity.getClass();
		Set<String> keys = map.keySet();
		// 变量map 赋值
		for (String key : keys) {
			PropertyDescriptor descriptor = null;
			Field fd = null;
			try {
				fd = clazz.getDeclaredField(key);
			} catch (NoSuchFieldException | SecurityException e1) {
			}
			if (null == fd) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					String rf = field.getName();
					JsonProperty jp = field.getAnnotation(JsonProperty.class);
					if (null != jp) {
						rf = jp.value();
					}
					if (rf.equals(key)) {
						fd = field;
						break;
					}
				}
			}
			if (null == fd) {
				logger.warn("key '{}' cannot invoke", key);
				continue;
			}
			try {
				descriptor = new PropertyDescriptor(fd.getName(), clazz);
			} catch (IntrospectionException e) {
			}

			if (null != descriptor) {
				// 设置赋值
				try {
					// 根据名称获取方法
					Method method = descriptor.getWriteMethod();
					Object vm = map.get(key);
					// 属性为数组的进行特别处理
					if (descriptor.getPropertyType().isArray()) {
						Object ovm = ((ArrayList) vm).toArray();
						int al = Array.getLength(ovm);
						Class ct = descriptor.getPropertyType()
								.getComponentType();
						Object po = Array.newInstance(ct, al);
						for (int i = 0; i < al; i++) {
							Object ivm = Array.get(ovm, i);
							Array.set(po, i, ivm);
						}
						method.invoke(entity, po);
					} else if (descriptor.getPropertyType().equals(
							java.util.Date.class)) {
						method.invoke(entity,
								TimeUtil.str2date(String.valueOf(vm)));
					} else if (descriptor.getPropertyType().equals(Long.class)||descriptor.getPropertyType().equals(long.class)) {
						method.invoke(entity, Long.valueOf(String.valueOf(vm)));
					} else if (descriptor.getPropertyType().equals(java.lang.String.class)) {
						method.invoke(entity, String.valueOf(vm));
					} else if (descriptor.getPropertyType().equals(Integer.class)||descriptor.getPropertyType().equals(int.class)) {
						method.invoke(entity, Integer.parseInt(String.valueOf(vm)));
					} else if (descriptor.getPropertyType().equals(Float.class)||descriptor.getPropertyType().equals(float.class)) {
						method.invoke(entity, Float.parseFloat(String.valueOf(vm)));
					}else {
						method.invoke(entity, vm);
					}
				} catch (Exception e) {
					logger.error("Array cast erro {}",key, e);
				}
			} else {
				logger.warn("{} not exist in {}", key, clazz.getName());
			}
		}
		return entity;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Class clazz = obj.getClass();
		Field[] fds = clazz.getDeclaredFields();
		for (Field fd : fds) {
			String key = fd.getName();
			if (!key.equals("class") && !"serialVersionUID".equals(key)) {
				Object value = null;
				try {
					PropertyDescriptor descriptor = new PropertyDescriptor(key,
							clazz);
					Method getter = descriptor.getReadMethod();
					value = getter.invoke(obj);
					if(null==value){
						continue;
					}
					JsonProperty jp = fd.getAnnotation(JsonProperty.class);
					if (null != jp) {
						key = jp.value();
					}
				} catch (Exception e) {
					logger.error("transBean2Map Error ", e);
				}
				map.put(key, value);
			}
		}
		return map;
	}

	public static String getStrByStr(Object obj, String str) {
		if (obj == null) {
			return null;
		}
		Object or = "";
		//将入参的首字母变成小写 
		char[] chars=new char[1];  
		chars[0]=str.charAt(0);  
        String temp=new String(chars);  
        if(chars[0]>='A'  &&  chars[0]<='Z')  
        {  
        	String templ = temp.toLowerCase();
            str = str.replaceFirst(temp,templ);  
        }  
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (key.equals(str)) {
					Method getter = property.getReadMethod();
					or = getter.invoke(obj);
				}
			}
		} catch (Exception e) {
			logger.error("transBean2Map Error ", e);
		}
		if (null == or) {
			return null;
		}
		String jsonString = String.valueOf(or);
		return jsonString;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> doFunnelByStr(Object obj, String[] str) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Class clazz = obj.getClass();
		Field[] fds = clazz.getDeclaredFields();
		List<String> strList = Arrays.asList(str); 
		for(Field fd : fds){
			String key = fd.getName();
			String mapkey = key;
			if(!strList.contains(key)){
				JsonProperty jp = fd.getAnnotation(JsonProperty.class);
				if(null==jp||!strList.contains(jp.value())){
					continue;
				}else{
					mapkey = jp.value();
					strList.remove(jp.value());
				}
			}
			try {
				PropertyDescriptor descriptor = new PropertyDescriptor(key,
						clazz);
				Method getter = descriptor.getReadMethod();
				Object value = getter.invoke(obj);
				map.put(mapkey, value);
			} catch (Exception e) {
				logger.error("doFunnelByStr Error ", e);
			}
		}
		return map;
	}
	
	public static void printMap(Map<String,Object> map){
		Set<String> ks = map.keySet();
		System.out.println("printMap start:");
		for(String o : ks){
			System.out.println("key is ("+o+") value is("+String.valueOf(map.get(o))+")");
		}
		System.out.println("printMap end!");
	}
}
