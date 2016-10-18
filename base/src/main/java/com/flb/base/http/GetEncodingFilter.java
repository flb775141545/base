package com.flb.base.http;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <filter>
		<filter-name>CharacterEncodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>GBK</param-value>
		</init-param>
		<init-param>
			<param-name>forceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	
	<filter>
		<filter-name>GetEncodingFilter</filter-name>
		<filter-class>com.flb.tools.http.GetEncodingFilter</filter-class>
		
	</filter>
 *
 */
public class GetEncodingFilter implements Filter
{

	@Override
	public void destroy()
	{
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest req=(HttpServletRequest)request;
		if(req.getMethod().equalsIgnoreCase("get"))
		{
			Map map=req.getParameterMap();
			if(map.size()>0){
				Collection values=map.values();
				Iterator it=values.iterator();
				while (it.hasNext()) {
					String[] object = (String[]) it.next();
					for (int i = 0; i < object.length; i++)
					{
						object[i]=new String(object[i].getBytes("iso-8859-1"),"GBK");
					}
				}

			}
		}
		request.setCharacterEncoding("gbk");
        response.setCharacterEncoding("gbk");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}
	
}
