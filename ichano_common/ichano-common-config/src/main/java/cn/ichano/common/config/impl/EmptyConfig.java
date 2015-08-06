package cn.ichano.common.config.impl;

import java.util.Map;
import java.util.Vector;

import cn.ichano.common.config.Config;

/**
 * empty config
 * @author wenjp
 *
 */
public  class EmptyConfig implements Config{

		@Override
		public boolean getBoolean(String s) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean getBoolean(String s, boolean flag) {
			// TODO Auto-generated method stub
			return flag;
		}

		@Override
		public double getDouble(String s) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getDouble(String s, double d) {
			// TODO Auto-generated method stub
			return d;
		}

		@Override
		public float getFloat(String s) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float getFloat(String s, float f) {
			// TODO Auto-generated method stub
			return f;
		}

		@Override
		public int getInteger(String s) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getInteger(String s, int i) {
			// TODO Auto-generated method stub
			return i;
		}

		@Override
		public long getLong(String s) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getLong(String s, long l) {
			// TODO Auto-generated method stub
			return l;
		}

		@Override
		public Vector getPropertyNameList() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Vector getPropertyNameList(String s) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getValue(String s) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getValue(String s, String s1) {
			// TODO Auto-generated method stub
			return s1;
		}

		@Override
		public Map getValues() {
			// TODO Auto-generated method stub
			return null;
		}
 	
 }