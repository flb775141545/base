package com.flb.base.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

public class uploadFile
{
	//上传文件
	public static String upload(MultipartFile uploadFile,String name)
		{
			String root = System.getProperty("webapp.root");
			String url = "/bankmineral/upload/"+name+"/";
			String ext ="";
			String path  =System.currentTimeMillis()+"_"+new Random().nextInt(10000);
			try
			{
				long size = uploadFile.getSize();
				byte[] data = new byte[(int )size];
				InputStream input = uploadFile.getInputStream();
				input.read(data);
				File folder = new File(root + url);
				if (!folder.exists())
				{
					folder.mkdirs();
				}
				String fileName = uploadFile.getOriginalFilename();
				// 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
				ext = fileName.substring(fileName.lastIndexOf(".") + 1,
						fileName.length());
				// 对扩展名进行小写转换
				ext = ext.toLowerCase();
				File outFile = new File(root + url + path+"."+ext);
				if(!outFile.exists())
				{
					outFile.createNewFile();
				}
				
				FileOutputStream outStream = new FileOutputStream(outFile);
				
				outStream.write(data);
				outStream.close();
				input.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			String imgUrl =url + path +"." +ext;
			
			return imgUrl;
		}
}
