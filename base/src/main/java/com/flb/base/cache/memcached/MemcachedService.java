package com.flb.base.cache.memcached;

import java.util.Collection;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flb.base.cache.CacheService;
import com.flb.base.common.ObjectUtils;
import com.google.code.yanf4j.core.impl.StandardSocketOption;

public class MemcachedService implements CacheService
{
	private static final Logger logger = LoggerFactory.getLogger(MemcachedService.class);

	private MemcachedClient client;
	private MemcachedClientBuilder builder;
	
	public MemcachedService(String servers, int readThreadCount, long connectTimeout, long opTimeout)
	{
		builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers));
		builder.setConnectionPoolSize(1);

		builder.getConfiguration().setReadThreadCount(readThreadCount);
		builder.getConfiguration().setHandleReadWriteConcurrently(true);
		
		builder.setSocketOption(StandardSocketOption.SO_RCVBUF, 32 * 1024);                   
		builder.setSocketOption(StandardSocketOption.SO_SNDBUF, 16 * 1024);
		builder.setSocketOption(StandardSocketOption.TCP_NODELAY, false);

		try
		{
			client = builder.build();
			client.setOpTimeout(opTimeout);
			client.setConnectTimeout(connectTimeout);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}

	public void destroy()
	{
		try
		{
			client.shutdown();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}

	public void set(String key, Object value)
	{
		set(key, value, 0);;
	}

	public void set(String key, Object value, int expiry)
	{
		try
		{
			client.setWithNoReply(key, expiry, value);
		}
		catch(Throwable t)
		{
			logger.error(t.getMessage(), t);
		}
	}
	
	public void delete(String key)
	{
		try
		{
			client.deleteWithNoReply(key);
		}
		catch(Throwable t)
		{
			logger.error(t.getMessage(), t);
		}
	}

	public boolean exists(String key) 
	{
		return ObjectUtils.notNull(get(key));
	}

	public <T> T get(String key)
	{
		T value = null;

		try
		{
			value = client.get(key);
		} 
		catch(Throwable t)
		{
			logger.error(t.getMessage(), t);
		}

		return value;
	}

	public <T> Map<String, T> gets(Collection<String> keys)
	{
		Map<String, T> values = null;

		try
		{
			values = client.get(keys);
		} 
		catch(Throwable t)
		{
			logger.error(t.getMessage(), t);
		}

		return values;
	}
}