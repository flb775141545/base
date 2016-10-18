package com.flb.base.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtils
{
	private FileUtils()
	{
	}

	/**
	 * 与系统有关的默认行分隔符，为了方便，它被表示为一个字符串
	 */
	public static final String SYS_LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * 根据指定文件路径检测文件是否存在
	 * 
	 * @param sFilePath 完整的文件路径
	 * @return true 文件存在，false 文件不存在
	 */
	public static boolean exists(String sFilePath)
	{
		File oFile = new File(sFilePath);

		return oFile.exists() && oFile.isFile();
	}

	/**
	 * 根据指定文件路径、内容、编码创建一个新文件
	 * 
	 * @param sFilePath 完整的文件路径
	 * @param sText 文件内容
	 * @param sEncoding 编码，如：GBK、UTF-8等
	 * @return true 文件创建成功，false 文件创建失败
	 */
	public static boolean create(String sFilePath, String sText, String sEncoding)
	{
		PrintWriter printWriter = null;

		try
		{
			printWriter = new PrintWriter(sFilePath, sEncoding);
			printWriter.print(sText);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();

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

	/**
	 * 根据指定文件路径、内容创建一个新文件
	 * 
	 * @param sFilePath 完整的文件路径
	 * @param sText 文件内容
	 * @return true 文件创建成功，false 文件创建失败
	 */
	public static boolean create(String sFilePath, String sText)
	{
		PrintWriter printWriter = null;
		
		try
		{
			printWriter = new PrintWriter(new FileOutputStream(sFilePath));
			printWriter.print(sText);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();

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

	/**
	 * 根据指定文件路径追加其内容
	 * 
	 * @param sFilePath 完整的文件路径
	 * @param sText 追加内容
	 * @param bAppend true 追加，false 复写
	 * @return true 追加成功，false 追加失败
	 */
	public static boolean append(String sFilePath, String sText, boolean bAppend)
	{
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		
		try
		{
			fileWriter = new FileWriter(sFilePath, bAppend);
			printWriter = new PrintWriter(fileWriter);
			printWriter.print(sText);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();

			return false;
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

					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 根据指定文件路径读取文件内容
	 * 
	 * @param sFilePath 完整的文件路径
	 * @return 文件内容
	 */
	public static String read(String sFilePath)
	{
		return read(sFilePath, SYS_LINE_SEPARATOR);
	}

	/**
	 * 根据指定文件路径读取文件内容，可设置换行符
	 * 
	 * @param sFilePath 完整的文件路径
	 * @param sLineSeparator 换行符
	 * @return 文件内容
	 */
	public static String read(String sFilePath, String sLineSeparator)
	{
		StringBuilder stringBuilder = new StringBuilder();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try
		{
			fileReader = new FileReader(sFilePath);
			bufferedReader = new BufferedReader(fileReader);

			String text;
			while ((text = bufferedReader.readLine()) != null)
			{
				stringBuilder.append(text);
				if (sLineSeparator != null && !"".equals(sLineSeparator)) 
				{
					stringBuilder.append(sLineSeparator);
				}
			}
		}
		catch (FileNotFoundException fnfe) 
		{
			fnfe.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			try 
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
				if (fileReader != null)
				{
					fileReader.close();
				}
			} 
			catch (IOException ioe) 
			{
				ioe.printStackTrace();
			}
		}

		return stringBuilder.toString();
	}

	/**
	 * 根据指定文件路径删除一个文件
	 * 
	 * @param sFilePath 完整的文件路径
	 * @return true 删除成功，false 删除失败
	 */
	public static boolean delete(String sFilePath)
	{
		File oFile = new File(sFilePath);
		if (exists(sFilePath))
		{
			return oFile.delete();
		}
		else
		{
			return false;
		}
	}

	/**
	 * 重命名一个文件
	 * 
	 * @param sFromFile 完整的源文件路径
	 * @param sToFile 完整的目标文件路径
	 * @return true 重命名成功，false 重命名失败
	 */
	public static boolean rename(String sFromFile, String sToFile)
	{
		File fFile = new File(sFromFile);
		File tFile = new File(sToFile);

		if (fFile.exists() && fFile.isFile() && !tFile.exists() && !tFile.isDirectory())
		{
			return fFile.renameTo(tFile);
		}

		return false;
	}

	/**
	 * 移动一个文件
	 * 
	 * @param sFromFile 完整的源文件路径
	 * @param sToFile 完整的目录文件路径
	 * @return true 移动成功，false 移动失败
	 */
	public static boolean move(String sFromFile, String sToFile)
	{
		File fFile = new File(sFromFile);
		File tFile = new File(sToFile);

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