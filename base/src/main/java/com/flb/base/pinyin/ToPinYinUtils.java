package com.flb.base.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import com.flb.base.common.StringUtils;

public class ToPinYinUtils
{
	public static String[] toPinYin(String sChinese)
	{
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        char[] chars = sChinese.toCharArray();

		String[] sPinYin = new String[2];
		String sPinYin_1 = "";
		StringBuilder sPinYin_2 = new StringBuilder();
		for (char temp : chars)
		{
			if (' ' != temp)
			{
				try
				{
					String pinyin = concatPinyinStringArray(PinyinHelper.toHanyuPinyinStringArray(temp, outputFormat));
					
					if (!StringUtils.isTrimEmpty(pinyin))
					{
						sPinYin_1 += pinyin;
						sPinYin_2.append(pinyin.substring(0, 1));
					}
				}
				catch(Exception ex)
				{
				}
			}
		}

		sPinYin[0] = sPinYin_1;
		sPinYin[1] = sPinYin_2.toString();

		return sPinYin;
	}

	private static String concatPinyinStringArray(String[] pinyinArray)
	{
		return pinyinArray[0].toString();
	}
}
