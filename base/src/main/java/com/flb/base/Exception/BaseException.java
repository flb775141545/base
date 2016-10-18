package com.flb.base.Exception;

import java.util.Locale;

import com.flb.base.spring.SpringContext;

/**
 * 基础异常类
 * 
 * @author wuxiaoping
 *
 */
public class BaseException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	private int code;
	private Object[] params;
	
	public BaseException(int code, Object...params)
	{
		this.code =  code;
		this.params = params;
	}
	
	public int getMessageCode()
	{
		return code;
	}
	
	public String getMessage()
	{
		return getMessage(Locale.getDefault());
	}
	
	public String getMessage(Locale locale)
	{
		return SpringContext.getMessage(String.valueOf(code), params,
				"No such message code [" + code + "]", locale);
	}
}
