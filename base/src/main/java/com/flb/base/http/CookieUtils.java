package com.flb.base.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flb.base.common.Escape;

public abstract class CookieUtils
{
	public static String getStringCookie(HttpServletRequest request, String name)
	{
		return getStringCookie(request, name, "");
	}

	public static String getStringCookie(HttpServletRequest request, String name, String defaultValue)
	{
		Cookie[] cookies = request.getCookies();

		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (name.equals(cookie.getName()))
				{
					return cookie.getValue();
				}
			}
		}

		return defaultValue;
	}

	public static boolean getBooleanCookie(HttpServletRequest request, String name)
	{
		return getBooleanCookie(request, name, false);
	}

	public static boolean getBooleanCookie(HttpServletRequest request, String name, boolean defaultValue)
	{
		String value = getStringCookie(request, name);

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

	public static int getIntCookie(HttpServletRequest request, String name)
	{
		return getIntCookie(request, name, 0);
	}

	public static int getIntCookie(HttpServletRequest request, String name, int defaultValue)
	{
		String value = getStringCookie(request, name);

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

	public static double getDoubleCookie(HttpServletRequest request, String name)
	{
		return getDoubleCookie(request, name, 0D);
	}

	public static double getDoubleCookie(HttpServletRequest request, String name, double defaultValue)
	{
		String value = getStringCookie(request, name);

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

	public static long getLongCookie(HttpServletRequest request, String name)
	{
		return getLongCookie(request, name, 0L);
	}

	public static long getLongCookie(HttpServletRequest request, String name, long defaultValue)
	{
		String value = getStringCookie(request, name);

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

	public static void addCookie(HttpServletResponse response, String name, String value, String domain, int maxAge)
	{
		Cookie cookie = new Cookie(name, Escape.escape(value));
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");

		if (domain != null && !"".equals(domain))
		{
			cookie.setDomain(domain);
		}

		response.addCookie(cookie);
	}

	public static void delCookie(HttpServletRequest request, HttpServletResponse response, String domain, String name)
	{
		Cookie[] cookies = request.getCookies();

		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (name.equals(cookie.getName()))
				{
					cookie.setMaxAge(0);
					cookie.setPath("/");
					cookie.setDomain(domain);
					response.addCookie(cookie);
					return;
				}
			}
		}
	}
}