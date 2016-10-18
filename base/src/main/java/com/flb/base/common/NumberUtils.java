package com.flb.base.common;

public abstract class NumberUtils
{
	public static int parseInt(String s)
	{
		int value;

		try
		{
			value = Integer.parseInt(s);
		}
		catch (Exception e)
		{
			value = 0;
		}

		return value;
	}

	public static long parseLong(String s)
	{
		long value;

		try
		{
			value = Long.parseLong(s);
		}
		catch (Exception e)
		{
			value = 0L;
		}

		return value;
	}

	public static float parseFloat(String s)
	{
		float value;

		try
		{
			value = Float.parseFloat(s);
		}
		catch (Exception e)
		{
			value = 0F;
		}

		return value;
	}

	public static double parseDouble(String s)
	{
		double value;

		try
		{
			value = Double.parseDouble(s);
		}
		catch (Exception e)
		{
			value = 0.0D;
		}

		return value;
	}
}