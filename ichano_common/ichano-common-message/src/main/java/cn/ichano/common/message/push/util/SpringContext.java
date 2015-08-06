package cn.ichano.common.message.push.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class SpringContext implements ApplicationContextAware {

	private static ApplicationContext applicationContext ;
	
	@Override
	public void setApplicationContext(ApplicationContext inApplicationContext)
			throws BeansException {
		applicationContext = inApplicationContext;
		
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}

}
