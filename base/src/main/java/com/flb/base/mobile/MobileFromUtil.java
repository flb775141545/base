package com.flb.base.mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.flb.base.common.StringUtils;

public class MobileFromUtil
{
	private final static String finderPath = "/WEB-INF/classes/finder.conf";
	private static final String APPLICATION_ROOT_PATH = System.getProperty("webapp.root");
	
	private static Map<String, String> finderMap = new HashMap<String, String>(208497);
	
	static
	{
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try
		{
			File file = new File(APPLICATION_ROOT_PATH + finderPath);
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			String text = bufferedReader.readLine();
			
			while (!StringUtils.isTrimEmpty(text))
			{
				String number = text.substring(0, 7);
				String desc = text.substring(8);
				
				finderMap.put(number, desc);

				text = bufferedReader.readLine();
			}
		}
		catch (FileNotFoundException fnfe) 
		{
			fnfe.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			try 
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
				if (fileReader != null)
				{
					fileReader.close();
				}
			} 
			catch (IOException ioe) 
			{
				ioe.printStackTrace();
			}
		}
	}
	
	public static String getFinder(String number)
	{
		if (!StringUtils.isTrimEmpty(number))
		{
			if (number.length() == 11)
			{
				return finderMap.get(number.subSequence(0, 7));
			}
			else if (number.length() == 12)
			{
				return finderMap.get(number.subSequence(1, 8));
			}
		}
		
		return "";
	}
	
	public static String getShortFinder(String number)
	{
		String finder = getFinder(number);
		
		if (!StringUtils.isTrimEmpty(finder))
		{
			String[] finders = finder.split(":");
			
			return finders[finders.length - 1];
		}
		
		return "";
	}
	
	public static String getProvince(String number)
	{
		String finder = getFinder(number);
		
		if (!StringUtils.isTrimEmpty(finder))
		{
			String[] finders = finder.split(":");
			
			return finders[finders.length - 2];
		}
		
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println(getProvince("15895369529"));
	}
}
