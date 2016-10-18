package com.flb.base.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public abstract class Encoder
{
	private final static String CODEC_TABLE = "abcdefghijkmnoprstuvwxyz12345678";

	private final static int FIVE_BIT = 5;
	private final static int EIGHT_BIT = 8;
	private final static int BINARY = 2;

	public static String encode(String s)
	{
		return encode(s, null);
	}

	public static String encode(String s, String charsetName)
	{
		if (StringUtils.isEmpty(s))
		{
			return s;
		}

		byte[] keyBytes = null;

		if (StringUtils.isTrimEmpty(charsetName)) 
		{
			keyBytes = s.getBytes();
		} 
		else 
		{
			try 
			{
				keyBytes = s.getBytes(charsetName);
			} 
			catch (UnsupportedEncodingException e)
			{
			}
		}

		return encode(keyBytes);
	}

	public static String decode(String s) 
	{
		return decode(s, null);
	}

	public static String decode(String s, String charsetName)
	{
		if (StringUtils.isEmpty(s))
		{
			return s;
		}

		StringBuilder sbBinarys = new StringBuilder();

		for (int i = 0; i < s.length(); i++)
		{
			formatBinary(Integer.toBinaryString(getCodecTableIndex(s.charAt(i))), sbBinarys, FIVE_BIT);
		}

		byte[] binarys = new byte[sbBinarys.length() / EIGHT_BIT];

		for (int i = 0, j = 0; i < binarys.length; i++) 
		{
			binarys[i] = Integer.valueOf(sbBinarys.substring(j, j += EIGHT_BIT), BINARY).byteValue();
		}

		String decoded = null;

		if (StringUtils.isTrimEmpty(charsetName))
		{
			decoded = new String(binarys);
		} 
		else
		{
			try
			{
				return new String(binarys, charsetName);
			} 
			catch (UnsupportedEncodingException e) 
			{
			}
		}

		return decoded;
	}

	private static String encode(byte[] bs)
	{
		if (bs == null || bs.length < 1) 
		{
			return "";
		}

		StringBuilder mergrd = new StringBuilder();

		for (int i = 0; i < bs.length; i++)
		{
			formatBinary(bs[i], mergrd);
		}

		int groupCount = mergrd.length() / FIVE_BIT;
		int lastCount = mergrd.length() % FIVE_BIT;

		if (lastCount > 0)
		{
			groupCount += 1;
		}

		StringBuilder sbBinarys = new StringBuilder();

		int forMax = groupCount * FIVE_BIT;

		for (int i = 0; i < forMax; i += FIVE_BIT)
		{
			int end = i + FIVE_BIT;

			boolean flag = false;

			if (end > mergrd.length()) 
			{
				end = (i + lastCount);
				flag = true;
			}

			String strFiveBit = mergrd.substring(i, end);

			int intFiveBit = Integer.parseInt(strFiveBit, BINARY);

			if (flag) 
			{
				intFiveBit <<= (FIVE_BIT - lastCount);
			}

			sbBinarys.append(CODEC_TABLE.charAt(intFiveBit));
		}

		return sbBinarys.toString();
	}

	private static int getCodecTableIndex(char c)
	{
		for (int i = 0; i < CODEC_TABLE.length(); i++)
		{
			if (CODEC_TABLE.charAt(i) == c) 
			{
				return i;
			}
		}

		return -1;
	}

	private static StringBuilder formatBinary(byte b, StringBuilder toAppendTo) 
	{
		return formatBinary(b, toAppendTo, EIGHT_BIT);
	}

	private static StringBuilder formatBinary(byte b, StringBuilder toAppendTo, int bitCount) 
	{
		return formatBinary(Integer.toBinaryString(b), toAppendTo, bitCount);
	}

	private static StringBuilder formatBinary(String s, StringBuilder toAppendTo, int bitCount) 
	{
		if (s == null || s.length() < 1) 
		{
			return toAppendTo;
		}

		if (toAppendTo == null) 
		{
			toAppendTo = new StringBuilder();
		}

		if (s.length() == bitCount) 
		{
			return toAppendTo.append(s);
		}

		if (s.length() < bitCount) 
		{
			StringBuilder formatted = new StringBuilder();
			formatted.append(s);

			do 
			{
				formatted.insert(0, "0");
			} 
			while (formatted.length() < bitCount);

			return toAppendTo.append(formatted);
		}

		if (s.length() > bitCount) 
		{
			return toAppendTo.append(s.substring(s.length() - bitCount));
		}

		return toAppendTo;
	}

	public static void main(String[] args)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			while (true) 
			{
				System.out.print("�����ַ�Ŵ���");

				String in = br.readLine();
				
				if ("exit".equalsIgnoreCase(in))
				{
					break;
				}

				String enCode = encode(in, "GBK");
				String deCode = decode(enCode, "GBK");

				System.out.println();
				System.out.println("------------------------------");
				System.out.println("���룺" + enCode);
				System.out.println("���룺" + deCode);
				System.out.println("------------------------------");
				System.out.println();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}