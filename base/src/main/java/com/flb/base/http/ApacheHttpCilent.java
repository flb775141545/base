package com.flb.base.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ApacheHttpCilent
{
	public static final String PLEDGE_URL = "http://mgt-fund.banksteel.com";
//	public static final String PLEDGE_URL = "http://192.168.18.181";
	public static final String BANKSTEEL_URL = "http://www.banksteel.com/api/rest";

	//这是模拟post请求
    public static String sendPost(String url,Map<String,String>  params) throws ClientProtocolException, IOException{
        
    	String encoding = "GBK";
    	
    	//实例化一个Httpclient的
    	//HttpClient client = new DefaultHttpClient();
    	CloseableHttpClient client = HttpClientBuilder.create().build(); 
        //实例化一个post请求
        HttpPost post = new HttpPost(url);
         
        //设置需要提交的参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String temp : params.keySet()) 
        {
            list.add(new BasicNameValuePair(temp,params.get(temp)));
        }
        post.setEntity(new UrlEncodedFormEntity(list,encoding));
                  
        //实行请求并返回
        HttpResponse response = client.execute(post);

        return new String(EntityUtils.toByteArray(response.getEntity()));
    } 
    
    public static void main(String[] args) throws ClientProtocolException, IOException
	{
    	/*String addMsgUrl = "http://member1.mysteel.com/message/publishEsite.shtml";
		String name = "11李斯";
		String address = "上海宝山";
		String title = "钢铁侠测试111";
		String text = "合力来拉萨进口";		
		Map<String, String> params = new HashMap<String, String>();

		params.put("objectIds", "248174");
		params.put("receiverIds", "1308013");
		params.put("messageType", "5");
		params.put("detailTag", "yes");
		params.put("name", name);
		params.put("email", "eamin@qq.com");
		params.put("phone", "1234567");
		params.put("address", address);
		params.put("title", title);
		params.put("text", text);
		
		sendPost(addMsgUrl, params);*/
    	ApacheHttpCilent.get("http://192.168.100.188:8091/api/rest?method=getMyAdminByOpenId&openId=oipWCuMYgRGGeJ2DSBRsbUCF9ods&v=1.0");
	}
    
    /** 
     * 模拟GET请求 
     * @param client 
     * @param url 
     * @throws IOException 
     * @throws ClientProtocolException 
     */  
    public static String get(String url) throws IOException, ClientProtocolException 
    {  
        //实例化一个Httpclient的
        CloseableHttpClient client = HttpClientBuilder.create().build(); 
        HttpGet get = new HttpGet(url); 
        HttpResponse response = client.execute(get);  
        HttpEntity entity = response.getEntity();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),"GBK"));
        StringBuffer content = new StringBuffer();
        String buffer = null;  
        while((buffer=reader.readLine())!=null){  
        	content.append(buffer); 
        }  
        get.abort();  
        
        return content.toString();
    }  
}
