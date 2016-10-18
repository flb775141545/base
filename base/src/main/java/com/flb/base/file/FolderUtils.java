package com.flb.base.file;

import java.io.File;

public abstract class FolderUtils
{
	public static final String SYS_FILE_SEPARATOR = File.separator;

	public static String formatPath(Object...objects)
	{
		String path = "";
		
		if (objects != null)
		{
			for (Object obj : objects)
			{
				if ("".equals(path))
				{
					path += obj;
				}
				else
				{
					path += SYS_FILE_SEPARATOR + obj;
				}
			}
		}

		return path;
	}

	public static boolean exists(String folder)
	{
		File file = new File(folder);

		return file.isDirectory() && file.exists();
	}

	public static boolean mkdir(String folder)
	{
		File file = new File(folder);
		
		if (file.exists())
		{
			if (!file.isDirectory())
			{
				return file.mkdir();
			}
			else
			{
				return true;
			}
		}
		else
		{
			return file.mkdir();
		}
	}

	public static boolean mkdirs(String folder)
	{
		File file = new File(folder);
		
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

	public static boolean rename(String fromFolder, String toFolder)
	{
		File fromPath = new File(fromFolder);
		File toPath = new File(toFolder);
		
		if (exists(fromFolder) && !exists(toFolder)) 
		{
			return fromPath.renameTo(toPath);
		}

		return false;
	}

	public static boolean delete(File folder)
	{
		if (folder == null || !folder.exists() || !folder.isDirectory())
		{
			return false;
		}

		File[] files = folder.listFiles();
		
		for (File file : files) 
		{
			if (file.isDirectory())
			{
				if (!delete(file))
				{
					return delete(file);
				}
			}
			else
			{
				if (!file.delete())
				{
					return file.delete();
				}
			}
		}

		return folder.delete();
	}
}