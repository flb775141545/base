package com.flb.base.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DownLoadServlet extends HttpServlet
{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
	   String path = request.getParameter("path");
	   download(path, request, response);
    }

    public HttpServletResponse download(String path, HttpServletRequest request, HttpServletResponse response)
    {
	   try
	   {
		  File file = new File(System.getProperty("http://consult-mgr.banksteel.com/") + path.substring(0));
		  String name = file.getName();
		  String filename = name;

		  // 以流的形式下载文件。
		  InputStream fis = new BufferedInputStream(new FileInputStream(file));
		  byte[] buffer = new byte[fis.available()];
		  fis.read(buffer);
		  fis.close();
		  // 清空response
		  response.reset();
		  // 设置response的Header
		  response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gbk"), "ISO-8859-1"));
		  response.addHeader("Content-Length", "" + file.length());
		  OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		  response.setContentType("application/octet-stream");
		  toClient.write(buffer);
		  toClient.flush();
		  toClient.close();

	   }catch(IOException ex)
	   {
		  ex.printStackTrace();
	   }
	   return response;
    }
}
