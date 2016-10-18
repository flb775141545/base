package com.flb.base.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flb.base.common.ObjectUtils;
import com.flb.base.common.StringUtils;

public abstract class ServletUtils
{
	public static String getRequestValue(HttpServletRequest request, String key)
	{
		return ObjectUtils.nullSafe(request.getParameter(key), "");
	}

	public static String[] getRequestValues(HttpServletRequest request, String key)
	{
		return ObjectUtils.nullSafe(request.getParameterValues(key), new String[0]);
	}

	public static String getCookieValue(HttpServletRequest request, String sName)
	{
		Cookie[] cookies = request.getCookies();

		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (sName.equalsIgnoreCase(cookie.getName()))
				{
					return cookie.getValue();
				}
			}
		}

		return "";
	}

	public static void setCookieValue(HttpServletRequest request, HttpServletResponse response, String name, String value, String domain, int maxAge)
	{
		Cookie cookie = new Cookie(name, value);

		cookie.setMaxAge(maxAge);
		cookie.setPath("/");

		if (StringUtils.notTrimEmpty(domain))
		{
			cookie.setDomain(domain);
		}

		response.addCookie(cookie);
	}
	
	public static String getRemoteIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");

		if (StringUtils.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (StringUtils.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (StringUtils.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}

		if (!StringUtils.isTrimEmpty(ip))
		{
			int i = ip.indexOf(",");
			
			if (i != -1)
			{
				ip = ip.substring(0, i);
			}
		}

		return ip;
	}

	@Deprecated
	public static String getRemoteAddr(HttpServletRequest request)
	{
		return getRemoteIpAddr(request);
	}
}