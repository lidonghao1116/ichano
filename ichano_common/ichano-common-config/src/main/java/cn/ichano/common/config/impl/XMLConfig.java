package cn.ichano.common.config.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


public class XMLConfig extends FileConfig
{

    public XMLConfig(String name, String filename, boolean monitor)
        throws Exception
    {
        super(name, filename, monitor);
    }
    
    public XMLConfig(String name, String[] filenames, boolean monitor)
            throws Exception
        {
            super(name, filenames, monitor);
        }

    public XMLConfig(String name, String filename)
        throws Exception
    {
        super(name, filename);
    }

    protected Map<String,String> loadValues()
        throws Exception
    {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File(getFilename()));
        Element root = doc.getRootElement();
        if(root.getName().equals("project"))
            return loadValues_SimpleXML(root);
        if(root.getName().equals("config"))
            return loadValues_ComplexXML(root);
        else
            return new HashMap<String,String>();
    }

    private Map<String,String> loadValues_SimpleXML(Element root)
    {
        List<Element> elements = root.getChildren("property");
        Map<String,String> values = new HashMap<String,String>();
        for(int i = 0; i < elements.size(); i++)
        {
            Element element = (Element)elements.get(i);
            String key = element.getAttributeValue("name").toLowerCase();
            String value = element.getTextTrim();
            values.put(key, value);
        }

        return values;
    }

    private Map<String,String> loadValues_ComplexXML(Element root)
    {
        Map<String,String> values = new HashMap<String,String>();
        List<Element> elements = root.getChildren();
        for(int i = 0; i < elements.size(); i++)
            loadValues_ComplexXML((Element)elements.get(i), "", values);

        return values;
    }

    private void loadValues_ComplexXML(Element element, String name, Map<String,String> values)
    {
        name = (new StringBuilder(String.valueOf(name))).append(".").append(element.getName()).toString();
        if(name.startsWith("."))
            name = name.substring(1);
        name = name.toLowerCase();
        String value = element.getTextTrim();
        if(value != null && value.length() > 0)
            values.put(name, value);
        List<Element> elements = element.getChildren();
        for(int i = 0; i < elements.size(); i++)
        {
            Element sub = elements.get(i);
            loadValues_ComplexXML(sub, name, values);
        }

    }
    
    public static class EMPTY extends XMLConfig{

		public EMPTY(String name, String filename) throws Exception {
			super(name, filename);
			
		}
    	
    }

	@Override
	protected Map<String, String> loadValues(String file) throws Exception {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File(file));
        Element root = doc.getRootElement();
        if(root.getName().equals("project"))
            return loadValues_SimpleXML(root);
        if(root.getName().equals("config"))
            return loadValues_ComplexXML(root);
        else
            return new HashMap<String,String>();
    }
}
