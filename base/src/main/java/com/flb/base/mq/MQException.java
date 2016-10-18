package com.flb.base.mq;

public class MQException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public MQException(String message)
	{
		super(message);
	}
	
	public MQException(String message, Throwable cause)
	{
		super(message, cause);
	}
}