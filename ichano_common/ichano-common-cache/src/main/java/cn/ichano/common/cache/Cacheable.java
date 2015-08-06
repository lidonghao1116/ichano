package cn.ichano.common.cache;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * 完整的cache为:cacheCalssName.prifix.fieldValue
 * @author wenjp
 *
 */

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
	public enum Type {
		QUERY, // 查询，缓存中有则中断 ，没有需要查询后缓存
		DELETE,
		UPDATE;// 包含增删改 ，更新缓存并执行后续调用
		//CACHEONLY;//仅缓存，会中断方法执行，一般不需要使用

	}
	
	/**
	 * 缓存使用哪个实现（暂时只支持redis）
	 * @author wenjp
	 *
	 */
	public enum CacheType{
		REDIS,
		LOCAL
	}

	/**
	 * key中使用的filed参数，一个cacheable仅一个field
	 * @return
	 */
	String field() ;

	Type type() default Type.QUERY;

	/**
	 * 缓存的为那个class的前缀，转换成com.xxx.xxx.className的string来存储，不配置为空
	 * @return
	 */
	Class<?> cacheClass() default Void.class;

	/**
	 * 前缀，在class名后，不配置为空，建议不用和cacheClass一块配置，不配置cacheClass时配置此项
	 * @return
	 */
	String prefix() default "";

	/**
	 * 默认失效时间
	 * @return
	 */
	int expireTime() default 3600;
	
	/**
	 * 缓存使用哪个实现（暂时只支持redis）
	 * @author wenjp
	 *
	 */
	CacheType cacheType() default CacheType.REDIS;

}
