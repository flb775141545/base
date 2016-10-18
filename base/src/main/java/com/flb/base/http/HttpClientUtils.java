package com.flb.base.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientUtils
{
	/**
	 * 模拟HTTP请求获取一个URL地址的内容，可设置换行符
	 * 
	 * @param sURL 可访问的URL地址
	 * @param sEncoding URL的编码，注意：当输入null或空时，默认为UTF-8编码
	 * @param sLineSeparator 换行符
	 * @return URL的文本内容
	 */
	public static String read(String url, String encoding)
	{
		BufferedReader bufferedReader = null;
		
		if (url != null && !"".equals(url.trim()))
		{
			if (encoding == null || "".equals(encoding))
			{
				encoding = "UTF-8";
			}

			try
			{
				HttpClient httpClient = new HttpClient();
				GetMethod getMethod = new GetMethod(url);
				int iStatus = httpClient.executeMethod(getMethod);

				if (iStatus == 200)
				{
					StringBuilder content = new StringBuilder();
					bufferedReader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), "GBK"));

					String text;
					while ((text = bufferedReader.readLine()) != null) 
					{
						content.append(text).append(System.getProperty("line.separator"));
					}

					bufferedReader.close();

					return content.toString();
				}
			}
			catch (MalformedURLException mue)
			{
				mue.printStackTrace();
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				if (bufferedReader != null)
				{
					try
					{
						bufferedReader.close();
					}
					catch (IOException ioe)
					{
						ioe.printStackTrace();
					}
				}
			}
		}

		return null;
	}
}