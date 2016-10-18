package com.flb.base.http;

import java.util.Map;
import java.util.Map.Entry;

import com.flb.base.common.ObjectUtils;

public class UrlUtils
{
	public static String getUrl(Map<String, Object> urlMap)
	{
		StringBuffer url = new StringBuffer();

		if (ObjectUtils.notEmpty(urlMap))
		{
			int i = 0;
			
			for (Entry<String, Object> entry : urlMap.entrySet())
			{
				if (i == 0)
				{
					url.append("?");
				}
				else
				{
					url.append("&");
				}

				url.append(entry.getKey()).append("=").append(entry.getValue());
				i++;
			}

			url.append("&");
		}
		else
		{
			url.append("?");
		}

		return url.toString();
	}
}
