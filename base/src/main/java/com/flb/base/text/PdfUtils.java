package com.flb.base.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

public class PdfUtils
{
//	public static String url = ":8085/eastcoal/auction/hb/pact/template.jsp?pactId=";
//	public static String url = "/eastcoal/auction/hb/pact/template.jsp?pactId=";
	public static String url = "/export/template.jsp?pactId=";
	
	@SuppressWarnings("resource")
	public static void createPdfByHtmlFile(String outputPDFFile, String inputHTMLFileName) throws IOException, DocumentException, ParserConfigurationException, SAXException
	{
		StringBuffer content = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(new File(inputHTMLFileName)));

		String line = null;

		while((line = reader.readLine()) != null)
		{
			line = line.replace("&nbsp;", " ");
			content.append(line).append("\r\n");
		}

		createPDFByContent(outputPDFFile, content.toString());
	}

	public static void createPDFByContent(String outputPDFFile, String content) throws IOException, DocumentException, ParserConfigurationException, SAXException
	{
		InputSource is = new InputSource(new BufferedReader(new StringReader(content)));
		Document document = XMLResource.load(is).getDocument();

		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver font = renderer.getFontResolver();
		
		//本地
		//font.addFont("C:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		//font.addFont("C:/Windows/Fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		
		//	线上
		font.addFont("/usr/share/fonts/my_fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		font.addFont("/usr/share/fonts/my_fonts/STZHONGS.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		
		OutputStream os = new FileOutputStream(outputPDFFile);
		renderer.setDocument(document, null);
		renderer.layout();
		renderer.createPDF(os);
		os.close();
	}
	
	public static void main(String[] args)
	{
		try
		{
//			System.setProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
//			System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
//			System.setProperty("org.xml.sax.driver", "com.sun.org.apache.xerces.internal.parsers.SAXParser");
			createPdfByHtmlFile("D:/tmp/zixun2_2.pdf", "D:/tmp/contact-year(4).shtml");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}