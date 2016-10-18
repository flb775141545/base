package com.flb.base.properties.property;

public interface IPropertyService
{
	String get(String sKey);
	String get(String sKey, String sDefaultValue);
}