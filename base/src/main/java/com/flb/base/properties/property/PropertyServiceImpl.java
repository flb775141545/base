package com.flb.base.properties.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyServiceImpl implements IPropertyService
{
	private static final Logger logger = Logger.getLogger(PropertyServiceImpl.class);

	private String sPropeFile;
	private Properties properties;

	private PropertyServiceImpl(String sPropeFile) throws Exception
	{
		setPropeFile(sPropeFile);
		init();
	}

	private PropertyServiceImpl(long lTimer, String sPropeFile) throws Exception
	{
		if (lTimer > 100L)
		{
			new ReloadPropertyThread(lTimer, sPropeFile).start();
		}

		setPropeFile(sPropeFile);
		init();
	}

	public void init() throws IOException
	{
		InputStream inputStream = this.getClass().getResourceAsStream(getPropeFile());

		properties = new Properties();
		properties.load(inputStream);

		logger.info("Loading properties file from class path resource [" + getPropeFile().substring(1) + "]");
	}

	public String get(String sKey)
	{
		return get(sKey, "");
	}

	public String get(String sKey, String sDefaultValue)
	{
		return properties.getProperty(sKey, sDefaultValue);
	}

	private String getPropeFile()
	{
		return sPropeFile;
	}
	private void setPropeFile(String sPropeFile)
	{
		if (sPropeFile.startsWith("classpath:"))
		{
			sPropeFile = "/" + sPropeFile.substring(10);
		}

		this.sPropeFile = sPropeFile;
	}

	private static class ReloadPropertyThread extends Thread
	{
		public ReloadPropertyThread(long lTimer, String sPropeFile)
		{
		}

		public void run()
		{
		}
	}
}