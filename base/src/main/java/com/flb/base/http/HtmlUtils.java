package com.flb.base.http;

public class HtmlUtils
{
	public static String formatHtml(String sHtml)
	{
		sHtml = sHtml.replace("&", "&amp;");
		sHtml = sHtml.replace("\"", "&quot;");
		sHtml = sHtml.replace("<", "&lt;");
		sHtml = sHtml.replace(">", "&gt;");
		sHtml = sHtml.replace("'", "&#39;");

		return sHtml;
	}
}