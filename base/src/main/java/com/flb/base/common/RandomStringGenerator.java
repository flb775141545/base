package com.flb.base.common;

public interface RandomStringGenerator
{
	int getMinLength();
	
	int getMaxLength();
	
	String getNewString();
	
	byte[] getNewStringAsBytes();
}