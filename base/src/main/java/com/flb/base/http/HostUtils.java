package com.flb.base.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public abstract class HostUtils
{
	public static String getResponseText(String url)
	{
		return getResponseText(url, "GBK");
	}
	
	public static String getResponseText(String url, String charset)
	{
		HttpClient client = new HttpClient();
		
		GetMethod method = new GetMethod(url);
		HttpMethodParams methodParams = method.getParams();
		methodParams.setParameter(HttpMethodParams.USER_AGENT, "-");
		methodParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		
		InputStream in = null;
		BufferedReader reader = null;
		
		try
		{
			int statusCode = client.executeMethod(method);
			
			if (statusCode == HttpStatus.SC_OK)
			{
				in = method.getResponseBodyAsStream();
				reader = new BufferedReader(new InputStreamReader(in, charset));
				
				String str;
				StringBuffer buf = new StringBuffer(128);
				
				while((str = reader.readLine()) != null)
				{
					buf.append(str);
				}
				
				return buf.toString();
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
				if (reader != null)
				{
					reader.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			method.releaseConnection();
		}
		
		return "";
	}
}