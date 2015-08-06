package cn.ichano.common.cache.impl;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import cn.ichano.common.cache.Cache;
import cn.ichano.common.cache.Cacheable;
import cn.ichano.common.cache.LocalCache;
import cn.ichano.common.config.Env;

@Component
@Aspect
public class CacheAspect {

	@Autowired
	Cache<String, Object> cache;
	@Autowired
	LocalCache lcCache;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CacheAspect.class);

	/**
	 * 定义缓存逻辑
	 * 
	 * @throws Throwable
	 */
	@Around("@annotation(cn.ichano.common.cache.Cacheable)")
	public Object cache(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		Boolean cacheEnable = Env.getConfig().getBoolean("common.cache.enable",
				true);
		Method method = getMethod(pjp);
		// 如果参数是null，就无法取到对应的参数。
		if (method == null) {
			return pjp.proceed();
		}
		Cacheable cacheable = method.getAnnotation(Cacheable.class);
		LOGGER.debug("execute CacheAspect, invoke method:{} .",
				method.getName());
		// 判断是否开启缓存
		if (!cacheEnable || cacheable == null) {
			return pjp.proceed();
		}
		// CacheKey cacheKey = findAnnotation(method);
		String realCacheKey = getRealCacheKey(pjp, cacheable);
		LOGGER.debug("execute CacheAspect, invoke method:{},find cache key:{}",
				method.getName(), realCacheKey);
		if (StringUtils.isEmpty(realCacheKey)) {
			return pjp.proceed();
		}
		// 获取方法的返回类型,让缓存可以返回正确的类型
		// 使用redis 的hash进行存取，易于管理
		if (cacheable.type().equals(Cacheable.Type.DELETE)) {
			lcCache.delValue(realCacheKey);
			cache.remove(realCacheKey);
			return pjp.proceed();
		} else if (cacheable.type().equals(Cacheable.Type.QUERY)) {
			result = lcCache.getValue(realCacheKey);
			if(result != null){
				return result;
			}
			result = cache.get(realCacheKey);
			if (result != null) {
				lcCache.setValue(realCacheKey, result);
				LOGGER.debug("cache query result. key:{}", realCacheKey);
				return result;
			} else {
				result = pjp.proceed();
				LOGGER.debug("un find cache query result. key:{}", realCacheKey);
				
				cache.set(realCacheKey, result, cacheable.expireTime(),
						TimeUnit.SECONDS);
				boolean as =cache.hasKey(realCacheKey);
				lcCache.setValue(realCacheKey, result);
			}
		} else {// update
			// 更新或者查询失败，都需要再次设置值
			result = pjp.proceed();
			Object[] args = pjp.getArgs();
			if (args.length == 1) {
				lcCache.setValue(realCacheKey, args[0]);
				cache.set(realCacheKey, args[0], cacheable.expireTime(),
						TimeUnit.SECONDS);
				LOGGER.debug("cache update entity. key:{},value:{}",
						realCacheKey, args[0]);
			} else {
				for (Object arg : args) {
					if (arg.getClass().equals(cacheable.cacheClass())) {
						lcCache.setValue(realCacheKey, arg);
						cache.set(realCacheKey, arg, cacheable.expireTime(),
								TimeUnit.SECONDS);
						LOGGER.debug("cache update entity. key:{},value:{}",
								realCacheKey, arg);
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取被拦截方法对象
	 * 
	 * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象 而缓存的注解在实现类的方法上
	 * 所以应该使用反射获取当前对象的方法对象
	 */
	public Method getMethod(ProceedingJoinPoint pjp) {
		// 获取参数的类型
		Object[] args = pjp.getArgs();
		@SuppressWarnings("rawtypes")
		Class[] argTypes = new Class[pjp.getArgs().length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null) {
				argTypes[i] = args[i].getClass();
			}
		}
		Method method = null;
		try {
			method = pjp.getTarget().getClass()
					.getMethod(pjp.getSignature().getName(), argTypes);
		} catch (NoSuchMethodException e) {
			LOGGER.error("", e);
		} catch (SecurityException e) {
			LOGGER.error("", e);
		}
		return method;

	}

	/**
	 * 获取缓存的key值
	 * 
	 * @param pjp
	 * @param cache
	 * @return
	 */
	private String getRealCacheKey(ProceedingJoinPoint pjp, Cacheable cache) {
		String field = cache.field();
		String value = getSpelKey(pjp, field);
		return getRealCacheKey(cache, value);
	}

	/**
	 * 取得field中spel表达是的值
	 * 
	 * @param pjp
	 * @param field
	 * @return
	 */
	private String getSpelKey(ProceedingJoinPoint pjp, String field) {
		Method method = getMethod(pjp);
		Object[] args = pjp.getArgs();
		// String field = cache.field();

		if (StringUtils.isEmpty(field)) {
			LOGGER.error(
					"method {}.{}'s annotation field is empty. This may be an error.",
					method.getClass(), method.getName());
		}
		LocalVariableTableParameterNameDiscoverer u =

		new LocalVariableTableParameterNameDiscoverer();

		String[] paraNameArr = u.getParameterNames(method);

		// 应用SPEL进行key的解析

		ExpressionParser parser = new SpelExpressionParser();

		// SPEL高低文

		StandardEvaluationContext context = new StandardEvaluationContext();

		// 把办法参数放入SPEL高低文中

		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}
		if (field.contains(",")) {
			return getKeyForStr(parser, context, field);
		}
		String value = parser.parseExpression(field).getValue(context,
				String.class);
		return value;
	}

	private String getRealCacheKey(Cacheable cache, String value) {
		StringBuffer sb = new StringBuffer();
		if (!cache.cacheClass().equals(Void.class)) {
			sb.append(cache.cacheClass().getName()).append(".");
		}
		if (!StringUtils.isEmpty(cache.prefix())) {
			sb.append(cache.prefix()).append(".");
		}
		sb.append(value);
		return sb.toString();
	}

	private String getKeyForStr(ExpressionParser parser,
			StandardEvaluationContext context, String field) {
		StringBuffer value = new StringBuffer();
		String[] valus = field.split(",");
		for (String lv : valus) {
			if (StringUtils.isEmpty(lv)) {
				break;
			}
			if (lv.contains("#")) {
				String rv = parser.parseExpression(lv).getValue(context,
						String.class);
				value.append(rv);
			} else {
				value.append(lv);
			}
			value.append("-");
		}
		String val = value.toString();
		val = val.substring(0, val.length() - 1);
		return val;
	}

}