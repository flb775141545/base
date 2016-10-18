package com.flb.base.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BreedAssortmentUtil 
{
	private static Map<String, Object> result = new HashMap<String, Object>();
	private static String BREEDASSORTMENT = "breedAssortment.xml";
	private static String CITYSASSORTMENT = "citysAssortment.xml";
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getBreedAssortmentMap(String file)
	{
		if (result.size() == 0)
		{
			reader(file);
		}
		return (Map<String, Object>) result.get(file.substring(0, file.length()-4));
	}
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	private static void assemberBreedsMap(String file)
	{
		Document document = reader(file);
		Set set = null;
		Element root = document.getRootElement();
		List<Element> childElements = root.elements();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (Element child : childElements) 
		{
			set = new HashSet();
			List<Element> elementList = child.elements();
			for (Element ele : elementList) 
			{
				set.add(ele.getText());
			}
			map.put(child.attributeValue("name"), set);
		}
		result.put(file.substring(0, file.length()-4), map);
	}
	
	@SuppressWarnings({"unchecked" })
	private static void assemberCitysMap(String file)
	{
		Document document = reader(file);
		Element root = document.getRootElement();
		List<Element> childElements = root.elements();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (Element child : childElements) 
		{
			map.put(child.attributeValue("code"), child.attributeValue("name"));
		}
		result.put(file.substring(0, file.length()-4), map);
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private static Document reader(String file)
	{
		
		SAXReader reader = new SAXReader();
		InputStream in = BreedAssortmentUtil.class.getClassLoader().getResourceAsStream(file);
		Document document = null;
		
		Set set = null;
		
		try {
			document = reader.read(in);
		} 
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
		
		if (in != null)
		{
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return document;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String concatBreedNameAssortment(String param)
	{
		if (!result.containsKey(BREEDASSORTMENT.substring(0,BREEDASSORTMENT.length()-4)))
		{
			assemberBreedsMap(BREEDASSORTMENT);
		}
		
		Map<String, Object> map = (Map<String, Object>) result.get(BREEDASSORTMENT.substring(0,BREEDASSORTMENT.length()-4));
		
		for (String key : map.keySet())
		{
			if (((Set)map.get(key)).contains(param))
			{
				return key;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings({"unchecked" })
	public static String concatCitysAssortment(String param)
	{
		if (!result.containsKey(CITYSASSORTMENT.substring(0,CITYSASSORTMENT.length()-4)))
		{
			assemberCitysMap(CITYSASSORTMENT);
		}
		
		Map<String, Object> map = (Map<String, Object>) result.get(CITYSASSORTMENT.substring(0,CITYSASSORTMENT.length()-4));
		
		if(map.containsKey(param))
		{
			return map.get(param).toString();
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		System.out.println(BreedAssortmentUtil.concatCitysAssortment("0101"));
	}
}
