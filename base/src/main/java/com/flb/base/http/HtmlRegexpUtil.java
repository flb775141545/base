package com.flb.base.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlRegexpUtil 
{   
    private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签   
  
    /**  
     * 基本功能：过滤所有以"<"开头以">"结尾的标签  
     * @param str  
     * @return String  
     */  
    public static String filterHtml(String str) 
    {   
        Pattern pattern = Pattern.compile(regxpForHtml);   
        Matcher matcher = pattern.matcher(str); 
        
        StringBuffer sb = new StringBuffer(); 
        
        boolean result = matcher.find();  
        
        while (result) 
        {   
            matcher.appendReplacement(sb, ""); 
            
            result = matcher.find();   
        }  
        
        matcher.appendTail(sb);
        
        return sb.toString();   
    }   
  
    /**  
     *   
     * 基本功能：过滤指定标签  
     * <p>  
     *   
     * @param str  
     * @param tag  
     *            指定标签  
     * @return String  
     */  
    public static String fiterHtmlTag(String str, String tag)
    {   
        String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";   
        Pattern pattern = Pattern.compile(regxp);   
        Matcher matcher = pattern.matcher(str);   
        StringBuffer sb = new StringBuffer(); 
        
        boolean result = matcher.find(); 
        
        while (result) 
        {   
            matcher.appendReplacement(sb, "");   
            result = matcher.find();   
        }   
        
        matcher.appendTail(sb);  
        
        return sb.toString();   
    }   
  
    /**  
     * 基本功能：替换指定的标签  
     * @param str  
     * @param beforeTag  
     *            要替换的标签  
     * @param tagAttrib  
     *            要替换的标签属性值  
     * @param startTag  
     *            新标签开始标记  
     * @param endTag  
     *            新标签结束标记  
     * @return String  
     * @如：替换img标签的src属性值为[img]属性值[/img]  
     */  
    public static String replaceHtmlTag(String str, String beforeTag,String tagAttrib, String startTag, String endTag)
    {   
        String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";   
        String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";   
        
        Pattern patternForTag = Pattern.compile(regxpForTag);   
        Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);   
        
        Matcher matcherForTag = patternForTag.matcher(str);   
        
        StringBuffer sb = new StringBuffer();   
        
        boolean result = matcherForTag.find();   
        
        while (result) 
        {   
            StringBuffer sbreplace = new StringBuffer();   
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));   
            
            if (matcherForAttrib.find()) 
            {   
                matcherForAttrib.appendReplacement(sbreplace, startTag + matcherForAttrib.group(1) + endTag);   
            }  
            
            matcherForTag.appendReplacement(sb, sbreplace.toString());   
            
            result = matcherForTag.find();   
        }   
        
        matcherForTag.appendTail(sb);   
        
        return sb.toString();   
    }   
}  