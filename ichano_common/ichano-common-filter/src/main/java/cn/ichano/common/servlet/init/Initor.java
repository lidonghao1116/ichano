package cn.ichano.common.servlet.init;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import cn.ichano.common.config.Env;

public class Initor extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(Initor.class);

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static ServletContext sc;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		try {
			super.init(servletConfig);

			System.out.println("start server ...");
		} catch (ServletException e) {
			logger.error("Initor OpenApi System Faild!!! The Exception: ", e);
			throw e;
		}

		sc = servletConfig.getServletContext();
		String appPath = servletConfig.getServletContext().getRealPath("/");
		classPath = appPath + File.separator + "WEB-INF" + File.separator
				+ "classes" + File.separator;

		try {
			this.initorConfig();

		} catch (Exception e) {
			logger.error("Initor System Config Faild!!!,The Exception: ", e);

			System.exit(1);
		}
		
		System.out.println("start server init success.");
	}

	private void initorConfig() {
		try {
//			String sqlFileName = getSqlFileName();
			// String sqlFileName =classPath + "systemconfig"
			// + File.separator + "sql-oracle.xml";
//			Env.buildConfig4Sql(sqlFileName);
			Env.buildConfig4Sql(getSqlFileNames());

			String configName = getConfigPath();
			Env.buildXmlConfig(configName);

		} catch (Exception e) {
			throw new RuntimeException("init error:", e);
		}
	}

//	private String getSqlFileName() {
//		String sqlFileName = "";
//		// String sqlFileName =classPath + "systemconfig"
//		// + File.separator + "sql-oracle.xml";
//		String sqlFile = getInitParameter("sqlFile");
//		if (sqlFile.startsWith("classpath:")) {
//			String tmpFilePath = sqlFile.substring(10).trim();
//			try {
//				ClassPathResource resource = new ClassPathResource(tmpFilePath);
//				sqlFileName = resource.getFile().getCanonicalPath();
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return sqlFileName;
//	}
	
	private String[] getSqlFileNames() {
		Vector<String> v = new Vector<String>();
		String sqlFile = getInitParameter("sqlFile");
		if (sqlFile.startsWith("classpath:")) {
			ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
			try {
				Resource[] source = resourceLoader.getResources(sqlFile);
				for(Resource rs : source){
					v.add(rs.getFile().getCanonicalPath());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.error("读取SQL配置文件失败");
			}
		}
		return v.toArray(new String[v.size()]);
	}

	private String getConfigPath() {
		String configName = classPath + "systemconfig" + File.separator
				+ "config.xml";
		String configFile = getInitParameter("configFile");
		if (configFile.startsWith("classpath:")) {
			String tmpFilePath = configFile.substring(10).trim();
			try {
				ClassPathResource resource = new ClassPathResource(tmpFilePath);
				configName = resource.getFile().getCanonicalPath();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return configName;
	}

	String classPath = File.separator + "WEB-INF" + File.separator + "classes"
			+ File.separator;

}