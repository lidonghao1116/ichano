package cn.ichano.common.config.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class FileConfig extends MapConfig {

	public FileConfig(String name, String filename) throws Exception {
		this(name,filename,false);
	}

	public FileConfig(String name, String filename, boolean monitor)
			throws Exception {
		super(name);
		timeMark = 0L;
		checkModifiedTimeMark = 0L;
		this.filename = filename;
		this.monitor = monitor;
		reload();
	}
	
	public FileConfig(String name, String[] filenames, boolean monitor)
			throws Exception {
		super(name);
		timeMark = 0L;
		checkModifiedTimeMark = 0L;
		this.filenames = filenames;
		this.monitor = monitor;
		reloads();
	}

	public String getFilename() {
		return filename;
	}

	public long getTimeMark() {
		return timeMark;
	}

	public String getValue(String name) {
		try {
			if (monitor && isModified()){
				if(null!=filename){
					reload();
				}else{
					reloads();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.getValue(name);
	}

	protected boolean isModified() {
		if (monitor) {
			if (Math.abs(checkModifiedTimeMark - System.currentTimeMillis()) > 5000L) {
				if(null==filename){
					boolean isr = false;
					for(String fn : filenames){
						File file = new File(fn);
						checkModifiedTimeMark = System.currentTimeMillis();
						isr =  timeMark != file.lastModified();
						if(isr){
							return isr;
						}
					}
					return isr;
				}else{
					File file = new File(filename);
					checkModifiedTimeMark = System.currentTimeMillis();
					return timeMark != file.lastModified();	
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean isMonitor() {
		return monitor;
	}

	protected abstract Map<String,String> loadValues() throws Exception;
	
	protected abstract Map<String,String> loadValues(String file) throws Exception;

	public void reload() throws Exception {
		String s = filename;
		synchronized (this) {

			try {
				Map<String,String> tmp = loadValues();
				setValues(tmp);
			} catch (Exception ex) {
				throw ex;
			}finally{
				timeMark = System.currentTimeMillis();
			}
		}

	}
	
	
	public void reloads() throws Exception {
		String[] rs = filenames;
		synchronized (this) {
			try {
				for(String r : rs){
					Map<String,String> tmp = loadValues(r);
					this.values.putAll(tmp);
				}
			} catch (Exception ex) {
				throw ex;
			}finally{
				timeMark = System.currentTimeMillis();
			}
		}
	}

	public String toString() {
		try {
			if (monitor && isModified()){
				if(null!=filename){
					reload();
				}else{
					reloads();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.toString();
	}

	private String filename;
	private String[] filenames;
	private boolean monitor;
	private long timeMark;
	private long checkModifiedTimeMark;
}
