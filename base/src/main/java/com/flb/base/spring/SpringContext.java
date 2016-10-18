package com.flb.base.spring;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

public class SpringContext implements ApplicationContextAware
{
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		SpringContext.applicationContext = applicationContext;
	}

	public static Object getBean(String name) throws BeansException
	{
		return applicationContext.getBean(name);
	}
	
	public static <T> T getBean(Class<T> requiredType) throws BeansException
	{
		return applicationContext.getBean(requiredType);
	}

	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException
	{
		return applicationContext.getBean(name, requiredType);
	}

	public static boolean containsBean(String name)
	{
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException
	{
		return applicationContext.isSingleton(name);
	}

	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException
	{
		return applicationContext.getType(name);
	}

	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException
	{
		return applicationContext.getAliases(name);
	}
	
	public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale)
	{
		return applicationContext.getMessage(code, args, defaultMessage, locale);
	}
	
	public static String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException
	{
		return applicationContext.getMessage(code, args, locale);
	}
	
	public static String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException
	{
		return applicationContext.getMessage(resolvable, locale);
	}
}