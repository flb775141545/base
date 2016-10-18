package com.flb.base.email;

import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.flb.base.common.DateUtils;
import com.flb.base.common.StringUtils;
import com.flb.base.spring.SpringContext;

/**
 * 邮件发送工具类
 *
 * @author Rick
 * @time 2013-10-20上午9:15:02
 */
public class SendUtils
{
	public static int sendEmail(String sFrom, String email, String sSubject, String md5Code) throws Exception
	{
		int iresult = 0;
		
		try
		{
			String sText = getHtml(email, md5Code);
			
			JavaMailSender javaMailSender = (JavaMailSender)SpringContext.getBean("javaMailSender");
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");

			mimeMessageHelper.setFrom(sFrom);
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setSubject(sSubject);
			mimeMessageHelper.setText(sText, true);

			javaMailSender.send(mimeMessage);
			
			iresult = 1;
		}
		catch (Exception e)
		{
			iresult = -1;
			
			e.printStackTrace();
		}
		
		return iresult;
	}
	
	public static String toMd5Code()
	{
		return StringUtils.md5(toPW(8)+"$"+System.currentTimeMillis());
	}
	
	public static String toPW(int num)
	{
		String pw = "";
		String[] arr = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", 
				"H", "J", "K", "L", "M", "N", "P", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b",
				"c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "p", "r", "s", "t", "w", "x", "y", "z"};
		Random random = new Random();
		while (pw.length() < (num+1))
		{
			String temp = arr[random.nextInt(52)];
			if (pw.indexOf(temp) == -1)
			{
				pw += temp;
			}
		}
		return pw;
	}
	
	public static String getHtml(String email, String sencode)
	{
		String time = DateUtils.formatDate(System.currentTimeMillis(), "yyyy-MM-dd");
		StringBuffer sbf = new StringBuffer();
		
//		sbf.append("<table>");
//		sbf.append("<tr><td><b>您好！</b></td></tr>");
//		sbf.append("<tr>");
//		sbf.append("<td><b>欢迎加入钢银现货交易平台（<a target=\"_blank\" href=\"http://www.banksteel.com\">http://www.banksteel.com</a>)。您已经于"+time+"申请了用户注册（注册用户名为："+email+"），为确保您的注册信息安全，请点击以下链接进行激活： </b></td>");
//		sbf.append("</tr>");
//		sbf.append("<tr><td height=\"50\"><b><a href=\"http://pai.eastcoal.cn/activate.jsp?actiontype=M_REGISTER_ACTIVATE&iuserid="+Password.encode(user.getEmail())+"&sencode="+sencode+"\" target=\"_blank\">http://pai.eastcoal.cn/activate.jsp?actiontype=M_REGISTER_ACTIVATE&iuserid="+Password.encode(user.getEmail())+"&sencode="+sencode+"</a></b></td></tr>");
//		sbf.append("<tr><td><b>为了保障您帐号的安全性，请在 24小时内完成激活，此链接将在您激活过一次后失效！</b></td></tr>");
//		sbf.append("<tr><td height=\"30\"><b>如果点击以上链接无法激活，请您尝试以下方法：</b></td></tr>");
//		sbf.append("<tr><td height=\"30\"><b>第一步：将上述链接拷贝到您计算机浏览器(例如IE)的地址栏中；</b></td></tr>");
//		sbf.append("<tr><td height=\"30\"><b>第二步：按回车（Enter）键。</b></td></tr>");
//		sbf.append("<tr><td height=\"30\"><b>请勿直接回复本邮件。</b></td></tr>");
//		sbf.append("<tr><td height=\"30\"><b>感谢您使用东方煤炭交易平台！ </b></td></tr>");
//		sbf.append("<tr><td height=\"50\"></td></tr>");
//		sbf.append("<tr><td align=\"right\"><b>东方煤炭交易平台</b></td></tr>");
//		sbf.append("<tr><td align=\"right\"><b>"+time+"</b></td></tr>");
//		sbf.append("</table>");
		
		return sbf.toString();
	}
}