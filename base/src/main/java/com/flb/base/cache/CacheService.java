package com.flb.base.cache;

import java.util.Collection;
import java.util.Map;

import com.flb.base.common.StringUtils;

public interface CacheService
{
	void set(String key, Object value);
	
	void set(String key, Object value, int ttl);
	
	void delete(String key);
	
	boolean exists(String key);
	
	<T> T get(String key);
	
	<T> Map<String, T> gets(Collection<String> keys);
	
	public static class CacheKey
	{
		public static String build(Class<?> type, Object key)
		{
			if (type == null || key == null)
			{
				throw new NullPointerException("type or key is null.");
			}
			
			StringBuffer buf = new StringBuffer(32);
			buf.append(type.getName()).append("-");
			buf.append(key.getClass().getName());
			buf.append("-").append(key);

			return StringUtils.md5(buf.toString());
		}
	}
}