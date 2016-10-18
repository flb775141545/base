package com.flb.base;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestUnit
{
	@BeforeClass
	public static void beforeClass()
	{
		System.out.println("每个类执行前执行！");
	}
	
	@AfterClass
	public static void afterClass()
	{
		System.out.println("每个类执行后执行！");
	}
	
	@Before
	public void before()
	{
		System.out.println("每个方法执行前执行！");
	}
	
	@After
	public void after()
	{
		System.out.println("每个方法执行后执行！");
	}
	
	@Test(timeout = 1000)
	public void execute()
	{
		System.out.println("方法执行！");
		int num = 2 + 3;
		
//		try
//		{
//			Thread.sleep(2000);
//		} catch (InterruptedException e)
//		{
//			e.printStackTrace();
//		}
		Assert.assertEquals(5, num);
	}
	
	@Ignore("未实现暂时忽略")
	public void execute1()
	{
		System.out.println("方法执行1！");
	}
	
	@Test(expected = ArithmeticException.class)
	public void exception()
	{
		System.out.println(1/0);
	}
}
