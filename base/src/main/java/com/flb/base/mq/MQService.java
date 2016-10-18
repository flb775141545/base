package com.flb.base.mq;

public interface MQService
{
	void append(String name, Object value) throws MQException;
	
	Object get(String name) throws MQException;
}