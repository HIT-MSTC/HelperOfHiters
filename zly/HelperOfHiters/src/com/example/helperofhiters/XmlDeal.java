package com.example.helperofhiters;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.apache.commons.lang3.StringEscapeUtils;

public class XmlDeal {
	public static boolean getBoolean(String s)
	{
		if(s.contains("true"))
			return true;
		else
			return false;
	}
	public static String getString(String s)
	{
		if(s.contains("<string />"))
			return "";
		int b = s.indexOf("<string");
		int e = s.lastIndexOf("</string>");
		s = s.substring(b, e);
		s = s.substring(s.indexOf(">")+1);
		return StringEscapeUtils.unescapeHtml4(s.trim());
	}
	public static Document getXml(String s)
	{
		try {
			s = s.replaceAll(">\\s+<", "><");
			InputSource is = new InputSource(new StringReader(s));
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
			return doc;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static List<Map<String,String>> getData(Document doc)
	{
		if(doc==null)
			return null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Element root = doc.getDocumentElement();
		NodeList rl = root.getChildNodes();
		for(int i=0;i<rl.getLength();i++)
		{
			NodeList row = rl.item(i).getChildNodes();
			Map<String,String> m = new HashMap<String,String>();
			for(int j=0;j<row.getLength();j++)
			{
				m.put(row.item(j).getNodeName(), row.item(j).getTextContent());
			}
			list.add(m);
		}
		return list;
	}
}
