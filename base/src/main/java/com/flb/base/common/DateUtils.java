package com.flb.base.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateUtils
{
	public static final String DATE_FROMAT1 = "yyyy-MM-dd";
	public static final String DATE_FROMAT2 = "yyyy-MM-dd HH:mm:ss";

	public static Date getDate(String s)
	{
		return getDate(s, null);
	}

	public static Date getJustDate(String s) 
	{
		return getDate(s, DATE_FROMAT1);
	}

	public static Date getDate(long date) 
	{
		return getDate(date, null);
	}

	public static Date getJustDate(long date) 
	{
		return getDate(date, DATE_FROMAT1);
	}

	public static Date getDate(long date, String format) 
	{
		if (StringUtils.isEmpty(format))
		{
			format = DATE_FROMAT2;
		}

		return getDate(formatDate(new Date(date), format), format);
	}

	public static Date getDate(String s, String format)
	{
		Date date;

		try 
		{
			if (StringUtils.isEmpty(format))
			{	
				format = DATE_FROMAT2;
			}

			date = new SimpleDateFormat(format).parse(s);
		}
		catch (Exception e) 
		{
			date = new Date(0);
		}

		return date;
	}

	public static String formatDate(long date, String format)
	{
		return formatDate(new Date(date), format);
	}

	public static String formatDate(long date)
	{
		return formatDate(new Date(date), null);
	}

	public static String formatJustDate(long date) 
	{
		return formatDate(new Date(date), DATE_FROMAT1);
	}

	public static String formatDate(Date date, String format)
	{
		if (StringUtils.isEmpty(format))
		{
			format = DATE_FROMAT2;
		}

		return new SimpleDateFormat(format).format(date);
	}
}