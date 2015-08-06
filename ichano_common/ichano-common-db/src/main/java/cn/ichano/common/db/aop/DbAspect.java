package cn.ichano.common.db.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import cn.ichano.common.config.Env;
import cn.ichano.common.db.dao.impl.BaseDao;
import cn.ichano.common.db.vo.DbNoticeMessage;
import cn.ichano.common.service.NoticeService;
import cn.ichano.common.entity.message.NoticeMessage;

import cn.ichano.common.entity.message.NoticeMessage.Sync;
import cn.ichano.common.entity.message.NoticeMessage.Urgent;
import cn.ichano.common.util.JsonUtil;

@Component
@Aspect
public class DbAspect {
	private Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);

	@Autowired(required=false)
	private NoticeService noticeService;

	@Around("execution (* cn.ichano.common.db.dao.impl.BaseDao.update(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {

		Map<String, Object> params = getPramsMap(pjp);

		String value = JsonUtil.toJsonString(params);
		LOGGER.error("execute sql:{}", value);
		boolean isAspect = Env.getConfig().getBoolean("notice.db.aspect", false);
		return process(pjp,isAspect, value, Urgent.NORMAL);
	}


	@Around("execution (* cn.ichano.common.db.dao.impl.BaseDao.updateImmediately(..))")
	public Object aroundUpdateImmediately(ProceedingJoinPoint pjp)
			throws Throwable {

		Map<String, Object> params = getPramsMap(pjp);

		String value = JsonUtil.toJsonString(params);
		LOGGER.error("execute immediately update, sql:{}", value);
		boolean isAspect = Env.getConfig().getBoolean("notice.db.aspect", false) && Env.getConfig().getBoolean("notice.db.remote", false);
		return process(pjp,isAspect, value, Urgent.IMMEDIATELY);
	}
	
	
	private Object process(ProceedingJoinPoint pjp,boolean isAspect, String value, Urgent urgent) throws Throwable {
		if(isAspect){
			if(noticeService == null){
				throw new RuntimeException("You want to execute sql at remote server,bat can't find service , please config 'noticeService'");
			}
			NoticeMessage n = createDbNoticeMessage(value,urgent);

			String result = noticeService.notice(n);
			if (StringUtils.isEmpty(result)) {
				return 0;
			}
			return Integer.parseInt(result);
		}else{
			return pjp.proceed();
		}
	}
	
	public NoticeMessage createDbNoticeMessage(String value,Urgent urgent){
		DbNoticeMessage n = new DbNoticeMessage();

		n.setValue(value);

		n.setUrgent(urgent);
		if(Urgent.IMMEDIATELY.equals(urgent)){
			//即时调用如果需要收到调用结果，则需要为同步,实际应该和immediately分开的
			n.setSync(Sync.SYNC);
		}
		return n;
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
		Class[] argTypes = new Class[pjp.getArgs().length];
		for (int i = 0; i < args.length; i++) {
			if(args[i] != null){
				argTypes[i] = Object.class;
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

	// 方法运行出现异常时调用

	private Map<String, Object> getPramsMap(ProceedingJoinPoint pjp) {
		int paramSize = 3;
		String sql = (String) pjp.getArgs()[0];
		Object[] objParams = (Object[]) pjp.getArgs()[1];
		Object bean = pjp.getArgs()[2];

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sql", sql);
		params.put("objs", objParams);
		params.put("bean", bean);
		return params;
	}

}
