package com.flb.base.common;

import java.io.File;


public class AppUtils
{
	private AppUtils()
	{
	}

	// 利用Log4j初使化应用程序根目录
	private static String sAppRootPath = System.getProperty("webapp.root");

	/**
	 * 获取系统运行根目录。
	 * 
	 * @return 目录。
	 */
	public static String getWebRootPath()
	{
		return sAppRootPath;
	}

	/**
	 * 根据指定目录路径创建一个目录
	 * 
	 * @param sFolder 完整目录路径
	 * @return true 创建成功，false 创建失败
	 */
	public static boolean mkdirs(String sFolder)
	{
		File file = new File(sFolder);
		if (file.exists())
		{
			if (!file.isDirectory())
			{
				return file.mkdirs();
			}
			else
			{
				return true;
			}
		}
		else
		{
			return file.mkdirs();
		}
	}

	public static String getWebRealPath(String path)
	{
		String sPath = sAppRootPath;

		if (!StringUtils.isTrimEmpty(sPath))
		{
			path = path.trim();
			
			if (path.startsWith(File.separator))
			{
				sPath += path + File.separator;
			}
			else
			{
				sPath +=  File.separator + path +  File.separator;
			}
		}

		return sPath;
	}
}