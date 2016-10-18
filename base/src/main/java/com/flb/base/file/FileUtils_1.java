package com.flb.base.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.flb.base.common.StringUtils;

public abstract class FileUtils_1
{
	public static final String SYS_LINE_SEPARATOR = System.getProperty("line.separator");

	public static boolean exists(String filePath)
	{
		File file = new File(filePath);

		return file.exists() && file.isFile();
	}

	public static boolean create(String filePath, String text)
	{
		return create(filePath, text, "GBK");
	}

	public static boolean create(String filePath, String text, String charset)
	{
		PrintWriter printWriter = null;

		try
		{
			printWriter = new PrintWriter(filePath, charset);
			printWriter.print(text);
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return false;
		}
		finally
		{
			if (printWriter != null)
			{
				printWriter.close();
			}
		}

		return true;
	}

	public static String read(String filePath)
	{
		return read(filePath, "GBK");
	}
	
	public static String read(String filePath, String charset)
	{
		return read(filePath, charset, SYS_LINE_SEPARATOR);
	}

	public static String read(String filePath, String charset, String lineSeparator)
	{
		InputStreamReader streamReader = null;
		BufferedReader bufferedReader = null;

		StringBuilder stringBuilder = new StringBuilder();

		try
		{
			streamReader = new InputStreamReader(new FileInputStream(filePath), charset);
			bufferedReader = new BufferedReader(streamReader);

			String text;
			while ((text = bufferedReader.readLine()) != null)
			{
				stringBuilder.append(text);
				
				if (StringUtils.notTrimEmpty(lineSeparator)) 
				{
					stringBuilder.append(lineSeparator);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

		return stringBuilder.toString();
	}

	public static boolean delete(String filePath)
	{
		File file = new File(filePath);
		
		if (file.isFile() && file.exists())
		{
			return file.delete();
		}
		else
		{
			return false;
		}
	}

	public static boolean rename(String fromFile, String toFile)
	{
		File fFile = new File(fromFile);
		File tFile = new File(toFile);

		if (fFile.exists() && fFile.isFile() && !tFile.exists() && !tFile.isDirectory())
		{
			return fFile.renameTo(tFile);
		}

		return false;
	}

	public static boolean move(String fromFile, String toFile)
	{
		File fFile = new File(fromFile);
		File tFile = new File(toFile);

		if (fFile.exists() && fFile.isFile())
		{
			if (tFile.exists() && tFile.isFile())
			{
				if (tFile.delete())
				{
					return fFile.renameTo(tFile);
				}
			}

			return fFile.renameTo(tFile);
		}

		return false;
	}
}