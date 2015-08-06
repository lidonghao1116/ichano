package cn.ichano.judge.controller;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import cn.ichano.common.config.Env;

public class AbstractTest {
	public static ClassPathXmlApplicationContext re ;
	
	static {
		init();
	}
	
	
	
	public static ClassPathXmlApplicationContext init() {
		
		Resource config = new ClassPathResource("systemconfig/config.xml");
		Resource sql = new ClassPathResource("systemconfig/sql-oracle.xml");
		String configFileName = "";
		String sqlFileName = "";
		try {
			configFileName = config.getFile().getCanonicalPath();
			sqlFileName = sql.getFile().getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Env.buildXmlConfig(configFileName);
		Env.buildConfig4Sql(sqlFileName);
		re = new ClassPathXmlApplicationContext("classpath:systemconfig/rest-*.xml");
		return re;
	}
}
