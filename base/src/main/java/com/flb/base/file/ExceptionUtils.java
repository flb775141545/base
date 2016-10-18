package com.flb.base.file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionUtils
{
	private static final String filePath = "D:/exception.log";
	
	/**
	 * 
	 * @param message
	 * 异常消息格式:模块名|||时间|||内容
	 * 例：epay|||2013-12-12 15:38:27|||实体类的json格式
	 */
	public static void addExceptionLog(String message)
	{
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		
		try
		{
			fileWriter = new FileWriter(filePath, true);
			printWriter = new PrintWriter(fileWriter);
			printWriter.print(message);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			if (printWriter != null)
			{
				printWriter.close();
			}
			if (fileWriter != null)
			{
				try 
				{
					fileWriter.close();
				} 
				catch (IOException ioe) 
				{
					ioe.printStackTrace();
				}
			}
		}
	}
}
