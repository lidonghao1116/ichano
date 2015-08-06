package cn.ichano.common.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
/**
 * 配置服务接口
 * @author wenjp
 *
 */
public interface Config
{
    public abstract boolean getBoolean(String s);
    public abstract boolean getBoolean(String s, boolean flag);
    public abstract double getDouble(String s);
    public abstract double getDouble(String s, double d);
    public abstract float getFloat(String s);
    public abstract float getFloat(String s, float f);
    public abstract int getInteger(String s);
    public abstract int getInteger(String s, int i);
    public abstract long getLong(String s);
    public abstract long getLong(String s, long l);
    public abstract Vector getPropertyNameList();
    public abstract Vector getPropertyNameList(String s);
    public abstract String getValue(String s);
    public abstract String getValue(String s, String s1);
    public abstract Map getValues();
    
 
}
