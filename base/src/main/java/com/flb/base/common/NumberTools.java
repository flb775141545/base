package com.flb.base.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 数字工具类
 * 
 */
public class NumberTools
{
	public static final String NUMBER_PATTERN_1 = "####.0000";
	// 定义money中文
	private final static String[] pattern = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	private final static String[] cPattern = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿" };
	private final static String[] cfPattern = { "", "角", "分" };
	private final static String ZEOR = "零";

	/**
	 * 数字格式化
	 * 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static final String formatNumber(Double number, String pattern)
	{
		if (number == null)
		{
			number = 0.0;
		}
		if (pattern == null || "".equals(pattern))
		{
			pattern = NUMBER_PATTERN_1;
		}
		return new DecimalFormat(pattern).format(number);
	}

	/**
	 * 检测当前字符串能不能转化为对应的数字
	 * 
	 * @param text
	 * @return
	 */
	public static final Boolean checkTextToNum(String text)
	{
		try
		{
			Double.parseDouble(text);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public static double formatDoubleNum(double qty)
	{
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(4);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);

		return NumberUtils.parseDouble(formater.format(qty));
	}

	public static double formatDoublePrice(double price)
	{
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(2);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);

		return NumberUtils.parseDouble(formater.format(price));
	}

	/**
	 * 全舍小数
	 * 
	 * @param finalDouble
	 * @param num
	 *            小数位数
	 * 
	 * @return
	 */
	public static double mathDouble(double finalDouble, int num)
	{
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(num);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);

		return NumberUtils.parseDouble(formater.format(finalDouble));
	}

	/**
	 * 四舍五入小数
	 * 
	 * @param finalDouble
	 * @param num
	 *            小数位数
	 * 
	 * @return
	 */
	public static double roundDouble(double finalDouble, int num)
	{
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(num);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.HALF_UP);

		return NumberUtils.parseDouble(formater.format(finalDouble));
	}

	/**
	 * 实现浮点数的加法运算功能
	 * 
	 * @param v1
	 *            加数1
	 * @param v2
	 *            加数2
	 * @return v1+v2的和
	 */
	public static double add(double v1, double v2)
	{
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();
	}

	/**
	 * 实现浮点数的减法运算功能
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return v1-v2的差
	 */
	public static double sub(double v1, double v2)
	{
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 实现浮点数的乘法运算功能
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return v1×v2的积
	 */
	public static double multi(double v1, double v2)
	{
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 实现浮点数的除法运算功能
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @return v1/v2
	 */
	public static double div(double v1, double v2)
	{
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 转换金额到大写
	 * 
	 * @param amt
	 * 
	 * @return
	 */
	public static String amtEnConvertCn(String amt)
	{
		int dotPoint = amt.indexOf("."); // 判断是否为小数

		String moneyStr;

		if (dotPoint != -1)
		{
			moneyStr = amt.substring(0, amt.indexOf("."));
		}
		else
		{
			moneyStr = amt;
		}

		StringBuffer ms = new StringBuffer();                   // 整数部分的处理,以及最后的yuan.
		for (int i = 0; i < moneyStr.length(); i++)
		{
			ms.append(pattern[moneyStr.charAt(i) - 48]);        // 按数组的编号加入对应大写汉字
		}

		int cpCursor = 1;                                       // 添加位数字符
		for (int j = moneyStr.length() - 1; j > 0; j--)
		{
			ms.insert(j, cPattern[cpCursor]); 					// 在j之后加字符,不影响j对原字符串的相对位置
			cpCursor = cpCursor == 8 ? 1 : cpCursor + 1;        // 亿位之后重新循环
		}

		/* replace的起始于终止位置 */
		while (ms.indexOf("零拾") != -1)
		{
			// 当十位为零时用一个"零"代替"零拾"
			ms.replace(ms.indexOf("零拾"), ms.indexOf("零拾") + 2, ZEOR);
		}
		while (ms.indexOf("零佰") != -1)
		{
			// 当百位为零时,同理
			ms.replace(ms.indexOf("零佰"), ms.indexOf("零佰") + 2, ZEOR);
		}
		while (ms.indexOf("零仟") != -1)
		{
			ms.replace(ms.indexOf("零仟"), ms.indexOf("零仟") + 2, ZEOR);
		}
		while (ms.indexOf("零万") != -1)
		{
			// 万需保留，中文习惯
			ms.replace(ms.indexOf("零万"), ms.indexOf("零万") + 2, "万");
		}
		while (ms.indexOf("零亿") != -1)
		{
			ms.replace(ms.indexOf("零亿"), ms.indexOf("零亿") + 2, "亿");
		}
		while (ms.indexOf("零零") != -1)
		{
			// 有连续数位出现零，即有以下情况，此时根据习惯保留一个零即可
			ms.replace(ms.indexOf("零零"), ms.indexOf("零零") + 2, ZEOR);
		}
		while (ms.indexOf("亿万") != -1)
		{
			// 特殊情况，如:100000000,根据习惯保留高位
			ms.replace(ms.indexOf("亿万"), ms.indexOf("亿万") + 2, "亿");
		}
		while (ms.length() > 1 && ms.lastIndexOf("零") == ms.length() - 1)
		{
			// 当结尾为零j，不必显示,经过处理也只可能出现一个零 最后的一个零保留
			ms.delete(ms.lastIndexOf("零"), ms.lastIndexOf("零") + 1);
		}

		StringBuffer fraction = null; 							// 小数部分的处理,以及最后的yuan.
		int end;
		if ((dotPoint = amt.indexOf(".")) != -1)
		{
			// 是小数的进入
			String fs = amt.substring(dotPoint + 1, amt.length());

			if ("00".equals(fs) || "0".equals(fs))
			{
				fraction = new StringBuffer("元整");
			}
			else
			{
				// 若前两位小数全为零，则跳过操作
				end = fs.length() > 2 ? 2 : fs.length(); // 仅保留两位小数
				fraction = new StringBuffer(fs.substring(0, end));
				for (int j = 0; j < fraction.length(); j++)
				{
					fraction.replace(j, j + 1, pattern[fraction.charAt(j) - 48]); // 替换大写汉字
				}
				for (int i = fraction.length(); i > 0; i--)
				{ // 插入中文标识
					fraction.insert(i, cfPattern[i]);
				}
				fraction.insert(0, "元"); // 为整数部分添加标识
			}
		}
		else
		{
			fraction = new StringBuffer("元整");
		}

		ms.append(fraction); // 加入小数部分
		return ms.toString();
	}

	public static void main(String[] args)
	{
//		System.out.println("90807800.335");
//		System.out.println(amtEnConvertCn("90807800.335"));
//		System.out.println("900000000.335");
//		System.out.println(amtEnConvertCn("900000000.335"));
//		System.out.println("000000.0");
//		System.out.println(amtEnConvertCn("00.0"));
//		System.out.println(amtEnConvertCn("00.03"));
//		System.out.println(amtEnConvertCn("00.30"));
//		System.out.println(amtEnConvertCn("00.23"));
		
		double aa = 10.454;
		double bb = 10.455;
		double cc = 10.456;
		double aa1 = -10.454;
		double bb1 = -10.455;
		double cc1 = -10.456;
		System.out.println(aa+"==>"+roundDouble(aa,2));
		System.out.println(bb+"==>"+roundDouble(bb,2));
		System.out.println(cc+"==>"+roundDouble(cc,2));
		System.out.println(aa1+"==>"+roundDouble(aa1,2));
		System.out.println(bb1+"==>"+roundDouble(bb1,2));
		System.out.println(cc1+"==>"+roundDouble(cc1,2));
	}
}