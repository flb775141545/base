package com.flb.base;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * 参数测试
 * 
 * @author wuxiaoping
 *
 */
@RunWith(Parameterized.class)
public class TestParameters
{
	private int param;
	private int result;
	
	public TestParameters(int param, int result)
	{
		this.param = param;
		this.result = result;
	}
	
	@Parameters
	public static Collection<Object[]> data()
	{
		return Arrays.asList(new Object[][]{{1,1},{2,2},{3,3}});
	}
	
	@Test
	public void execute()
	{
		System.out.println("方法执行");
		
		int num = param;
		
		Assert.assertEquals(result, num);
	}
}
