package com.flb.base.text;

import org.springframework.core.NestedRuntimeException;

public class ExcelParseException extends NestedRuntimeException
{
	private static final long serialVersionUID = 2636462047606564848L;

	public ExcelParseException(String message)
	{
		super(message);
	}

	public ExcelParseException(String message, Throwable cause)
	{
		super(message, cause);
	}
}