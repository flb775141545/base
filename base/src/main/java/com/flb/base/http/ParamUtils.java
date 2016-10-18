package com.flb.base.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

public abstract class ParamUtils
{
	public static String getStringParameter(HttpServletRequest request, String name)
	{
		return getStringParameter(request, name, "");
	}

	public static String getStringParameter(HttpServletRequest request, String name, String defaultValue)
	{
		return getStringParameter(request, name, defaultValue, "");
	}

	public static String getStringParameter(HttpServletRequest request, String name, String defaultValue, String charset)
	{
		String value = request.getParameter(name);

		if (value == null || "".equals(value))
		{
			return defaultValue;
		}

		if (!"".equals(charset))
		{
			try
			{
				return URLDecoder.decode(value, charset);
			}
			catch (UnsupportedEncodingException e)
			{
			}
		}

		return value;
	}

	public static boolean getBooleanParameter(HttpServletRequest request, String name)
	{
		return getBooleanParameter(request, name, false);
	}

	public static boolean getBooleanParameter(HttpServletRequest request, String name, boolean defaultValue)
	{
		String value = request.getParameter(name);

		if ("true".equals(value))
		{
			return true;
		}
		else if ("false".equals(value))
		{
			return false;
		}

		return defaultValue;
	}

	public static int getIntParameter(HttpServletRequest request, String name)
	{
		return getIntParameter(request, name, 0);
	}

	public static int getIntParameter(HttpServletRequest request, String name, int defaultValue)
	{
		String value = request.getParameter(name);

		if (value != null && !"".equals(value))
		{
			try
			{
				defaultValue = Integer.parseInt(value);
			}
			catch (Exception e)
			{
			}
		}

		return defaultValue;
	}

	public static double getDoubleParameter(HttpServletRequest request, String name)
	{
		return getDoubleParameter(request, name, 0D);
	}

	public static double getDoubleParameter(HttpServletRequest request, String name, double defaultValue)
	{
		String value = request.getParameter(name);

		if (value != null && !"".equals(value))
		{
			try
			{
				defaultValue = Double.parseDouble(value);
			}
			catch (Exception e)
			{
			}
		}

		return defaultValue;
	}

	public static long getLongParameter(HttpServletRequest request, String name)
	{
		return getLongParameter(request, name, 0L);
	}

	public static long getLongParameter(HttpServletRequest request, String name, long defaultValue)
	{
		String value = request.getParameter(name);

		if (value != null && !"".equals(value))
		{
			try
			{
				defaultValue = Long.parseLong(value);
			}
			catch (Exception e)
			{
			}
		}

		return defaultValue;
	}

	public static String getStringAttribute(HttpServletRequest request, String name)
	{
		return getStringAttribute(request, name, "");
	}

	public static String getStringAttribute(HttpServletRequest request, String name, String defaultValue)
	{
		String value = (String) request.getAttribute(name);

		if (value == null || "".equals(value))
		{
			return defaultValue;
		}

		return value;
	}

	public static boolean getBooleanAttribute(HttpServletRequest request, String name)
	{
		return getBooleanAttribute(request, name, false);
	}

	public static boolean getBooleanAttribute(HttpServletRequest request, String name, boolean defaultValue)
	{
		String value = (String) request.getAttribute(name);

		if ("true".equals(value))
		{
			return true;
		}
		else if ("false".equals(value))
		{
			return false;
		}

		return defaultValue;
	}

	public static int getIntAttribute(HttpServletRequest request, String name)
	{
		return getIntAttribute(request, name, 0);
	}

	public static int getIntAttribute(HttpServletRequest request, String name, int defaultValue)
	{
		String value = (String) request.getAttribute(name);

		if (value != null && !"".equals(value))
		{
			try
			{
				defaultValue = Integer.parseInt(value);
			}
			catch (Exception e)
			{
			}
		}

		return defaultValue;
	}

	public static double getDoubleAttribute(HttpServletRequest request, String name)
	{
		return getDoubleAttribute(request, name, 0D);
	}

	public static double getDoubleAttribute(HttpServletRequest request, String name, double defaultValue)
	{
		String value = (String) request.getAttribute(name);

		if (value != null && !"".equals(value))
		{
			try
			{
				defaultValue = Double.parseDouble(value);
			}
			catch (Exception e)
			{
			}
		}

		return defaultValue;
	}

	public static long getLongAttribute(HttpServletRequest request, String name)
	{
		return getLongAttribute(request, name, 0L);
	}

	public static long getLongAttribute(HttpServletRequest request, String name, long defaultValue)
	{
		String value = (String) request.getAttribute(name);

		if (value != null && !"".equals(value))
		{
			try
			{
				defaultValue = Long.parseLong(value);
			}
			catch (Exception e)
			{
			}
		}

		return defaultValue;
	}
}