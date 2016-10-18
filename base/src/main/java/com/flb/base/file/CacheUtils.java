package com.flb.base.file;

import com.flb.base.common.StringUtils;

public class CacheUtils
{
	private CacheUtils()
	{
	}

	public static String getId(Class<?> type, Object object)
	{
		if (object == null)
		{
			return StringUtils.md5(type.getName() + "!");
		}

		String sSeparator = "@";

		if (object instanceof Long)
		{
			sSeparator = "#";
		}
		else if (object instanceof Integer)
		{
			sSeparator = "$";
		}

		return StringUtils.md5(type.getName() + sSeparator + object.toString());
	}
}
