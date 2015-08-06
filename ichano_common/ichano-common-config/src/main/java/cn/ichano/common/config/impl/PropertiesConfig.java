package cn.ichano.common.config.impl;

import java.io.*;
import java.util.*;

public class PropertiesConfig extends FileConfig {

	public PropertiesConfig(String name, String filename) throws Exception {
		super(name, filename);
		properties = null;
	}

	public PropertiesConfig(String name, String filename, boolean monitor)
			throws Exception {
		super(name, filename, monitor);
		properties = null;
	}

	protected Map loadValues() throws Exception {
		FileInputStream inputStream = null;
		properties = new Properties();

		try {
			inputStream = new FileInputStream(getFilename());
			properties.load(inputStream);
		} catch (Exception ex) {

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		Map<String, String> result = new HashMap<String, String>();
		Object keys[] = properties.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			String key = (String) keys[i];
			result.put(key.toLowerCase(), properties.getProperty(key));
		}

		return result;
	}

	public void saveToFile(String filename) throws IOException {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(filename, false);
			properties.store(outputStream, "");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}

		}
	}

	Properties properties;

	@Override
	protected Map<String, String> loadValues(String file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
