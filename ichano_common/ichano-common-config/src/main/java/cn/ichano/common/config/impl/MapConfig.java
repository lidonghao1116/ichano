package cn.ichano.common.config.impl;

import java.util.*;

import cn.ichano.common.config.Config;

public abstract class MapConfig implements Config {

	public MapConfig(String name) {
		this.name = null;
		values = new HashMap();
		this.name = name;
	}

	public MapConfig(String name, MapConfig config) {
		this.name = null;
		values = new HashMap();
		this.name = name;
		values = (HashMap) config.values.clone();
	}

	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	public boolean getBoolean(String name, boolean defValue) {
		try {
			String str = getValue(name);
			if(str == null || str.length() == 0){
				return defValue;
			}
			str = str.trim().toLowerCase();
			if ("yes".equals(str) || "y".equals(str) || "true".equals(str) || "t".equals(str)){
				return true;
			}
			return false;
		} catch (Exception ex) {
			return defValue;
		}
	}

	public double getDouble(String name) {
		return getDouble(name, 0.0D);
	}

	public double getDouble(String name, double defValue) {
		String str = getValue(name);
		try {
			return Double.parseDouble(str.trim());
		} catch (Exception ex) {
			return defValue;
		}
	}

	public float getFloat(String name) {
		return getFloat(name, 0.0F);
	}

	public float getFloat(String name, float defValue) {
		String str = getValue(name);
		try {
			return Float.parseFloat(str.trim());
		} catch (Exception ex) {
			return defValue;
		}
	}

	public int getInteger(String name) {
		return getInteger(name, 0);
	}

	public int getInteger(String name, int defValue) {
		String str = getValue(name);
		try {
			return Integer.parseInt(str.trim());
		} catch (Exception ex) {
			return defValue;
		}
	}

	public long getLong(String name) {
		return getLong(name, 0L);
	}

	public long getLong(String name, long defValue) {
		String str = getValue(name);
		try {
			return Long.parseLong(str.trim());
		} catch (Exception ex) {
			return defValue;
		}
	}

	public String getName() {
		return name;
	}

	public Vector getPropertyNameList() {
		HashMap values = (HashMap) this.values.clone();
		Vector result = new Vector((Collection) values.keySet());
		Collections.sort(result);
		return result;
	}

	public Vector getPropertyNameList(String perfix) {
		if (perfix == null)
			return new Vector();
		HashMap values = (HashMap) this.values.clone();
		Vector result = new Vector((Collection) values.keySet());
		for (int i = result.size() - 1; i >= 0; i--) {
			String str = (String) result.get(i);
			if (!str.startsWith(perfix))
				result.remove(i);
		}

		Collections.sort(result);
		return result;
	}

	public String getValue(String name) {
		try {
			name = name.trim().toLowerCase();
			return (String) values.get(name);
		} catch (Exception ex) {
			return null;
		}
	}

	public String getValue(String name, String defValue) {
		String val = getValue(name);
		return val != null ? val : defValue;
	}

	public HashMap<String,String> getValues() {
		return (HashMap) values.clone();
	}

	public void setValues(Map<String,String> values) {
		this.values = (HashMap<String,String>) ((HashMap<String,String>)values).clone();
	}

	public String toString() {
		HashMap<String,String> values = (HashMap<String,String>) this.values.clone();
		Vector keys = new Vector((Collection) values.keySet());
		Collections.sort(keys);
		StringBuffer buf = new StringBuffer(500);
		buf.append("config name:").append(getName()).append("\r\n");
		buf.append("values:");
		for (int i = 0; i < keys.size(); i++) {
			Object key = keys.get(i);
			buf.append("\r\n\t");
			buf.append(key).append("\t= ").append(values.get(key));
		}

		return buf.toString();
	}
	
	private String name;
	protected HashMap<String,String> values;
}
