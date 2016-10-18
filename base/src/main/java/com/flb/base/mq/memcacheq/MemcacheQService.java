package com.flb.base.mq.memcacheq;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import com.flb.base.mq.MQException;
import com.flb.base.mq.MQService;
import com.google.code.yanf4j.core.impl.StandardSocketOption;

public class MemcacheQService implements MQService
{
	private MemcachedClient client;
	private MemcachedClientBuilder builder;
	
	public MemcacheQService(String servers, int readThreadCount, long connectTimeout, long opTimeout)
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
			client.setOptimizeGet(false);
			client.setOptimizeMergeBuffer(false);
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
	
	public void append(String name, Object value) throws MQException
	{
		try
		{
			client.set(name, 0, value);
		}
		catch (Throwable t)
		{
			throw new MQException(t.getMessage(), t);
		}
	}
	
	public Object get(String name) throws MQException
	{
		try
		{
			return client.get(name);
		}
		catch (Throwable t)
		{
			throw new MQException(t.getMessage(), t);
		}
	}
}