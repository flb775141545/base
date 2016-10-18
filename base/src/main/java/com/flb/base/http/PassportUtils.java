package com.flb.base.http;

import javax.servlet.http.HttpServletRequest;

public abstract class PassportUtils
{
	public static String getRemoteAddr(HttpServletRequest request)
	{
		String remoteAddr = request.getHeader("x-forwarded-for");

		if (remoteAddr == null
				|| "".equals(remoteAddr)
				|| "unknown".equalsIgnoreCase(remoteAddr))
		{
			remoteAddr = request.getHeader("Proxy-Client-IP");
		}

		if (remoteAddr == null
				|| "".equals(remoteAddr)
				|| "unknown".equalsIgnoreCase(remoteAddr))
		{
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}

		if (remoteAddr == null
				|| "".equals(remoteAddr)
				|| "unknown".equalsIgnoreCase(remoteAddr))
		{
			remoteAddr = request.getRemoteAddr();
		}

		if (remoteAddr != null && !"".equals(remoteAddr))
		{
			int index = remoteAddr.indexOf(",");

			if (index != -1)
			{
				remoteAddr = remoteAddr.substring(0, index);
			}
		}

		return remoteAddr;
	}
}