package com.flb.base.text;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.flb.base.common.DateUtils;

public class ExportExcelUtil
{

	public static void exportExcel(String sheetTitle, String[] attrNames, String[] attrNames2,
			List<?> dataList, List<?> dataList2, HttpServletRequest request,
			HttpServletResponse response, String[] HEADERS,
			String[] NEXT_HEADERS, String[] NEXT_HEADERS2, String fileName, String[] WIDEN, String sum, String lowSum, String numSum, String weightSum)
	{
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		int dsize = dataList.size();
		int size = dsize % 20000 == 0 ? dsize / 20000 : dsize / 20000 + 1;

		if (size == 1)
		{
			data(workbook, sheetTitle + 1, attrNames, dataList, response,
					HEADERS, NEXT_HEADERS, WIDEN, sum, lowSum, numSum, weightSum);
		}
		else
		{
			List<Object> sds = new ArrayList<Object>();
			for (int i = 1; i <= size; i++)
			{
				if (i == size)
				{
					for (int j = 0; j < dsize - (size - 1) * 20000; j++)
					{
						sds.add(dataList.get(j + (size - 1) * 20000));
					}
				}
				else
				{
					for (int j = 0; j < 20000; j++)
					{
						sds.add(dataList.get(j + 20000 * (i - 1)));
					}
				}
				data(workbook, sheetTitle + i, attrNames, sds, response,
						HEADERS, NEXT_HEADERS, WIDEN, sum, lowSum, numSum, weightSum);

				sds.removeAll(sds);
			}
		}
		
		if (dataList2 != null && dataList2.size() > 0)
		{
			int dcsize = dataList2.size();
			int csize = dcsize % 20000 == 0 ? dcsize / 20000 : dcsize / 20000 + 1;
			
			if (csize == 1)
			{
				data(workbook, "count" + 1, attrNames2, dataList2, response,
						HEADERS, NEXT_HEADERS2, null, sum, lowSum, numSum, weightSum);
			}
			else
			{
				List<Object> sds = new ArrayList<Object>();
				for (int i = 1; i <= size; i++)
				{
					if (i == size)
					{
						for (int j = 0; j < dsize - (size - 1) * 20000; j++)
						{
							sds.add(dataList2.get(j + (size - 1) * 20000));
						}
					}
					else
					{
						for (int j = 0; j < 20000; j++)
						{
							sds.add(dataList2.get(j + 20000 * (i - 1)));
						}
					}
					data(workbook, "count" + i, attrNames2, sds, response,
							HEADERS, NEXT_HEADERS2, WIDEN, sum, lowSum, numSum, weightSum);

					sds.removeAll(sds);
				}
			}

		}

		try
		{

			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ fileName + ".xls");

			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			request.getSession().removeAttribute("flag");
			System.gc();
		}

	}

	@SuppressWarnings("static-access")
	private static HSSFCellStyle createHeaderCellStyle(HSSFWorkbook workbook)
	{
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		// style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;
	}

	@SuppressWarnings("static-access")
	private static HSSFCellStyle createBodyCellStyle(HSSFWorkbook workbook)
	{
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		// style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;
	}

	@SuppressWarnings("static-access")
	private static HSSFFont createHeaderFont(HSSFWorkbook workbook)
	{
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		return font;
	}

	@SuppressWarnings("static-access")
	private static HSSFFont createBodyFont(HSSFWorkbook workbook)
	{
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		return font;
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	private static void data(HSSFWorkbook workbook, String sheetTitle,
			String[] attrNames, List<?> dataList, HttpServletResponse response,
			String[] HEADERS, String[] NEXT_HEADERS, String[] WIDEN, String sum, String lowSum, String numSum, String weightSum)
	{
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetTitle);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 10);

		if (WIDEN != null)
		{
			for (String rowName : WIDEN)
			{
				inner: for (int i = 0; i < NEXT_HEADERS.length; i++)
				{
					if (rowName.equals(NEXT_HEADERS[i]))
					{
						sheet.setColumnWidth(i, 5000);
						break inner;
					}
				}
			}
		}

		HSSFCellStyle headerStyle = createHeaderCellStyle(workbook);
		// 把字体应用到当前的样式
		headerStyle.setFont(createHeaderFont(workbook));

		HSSFCellStyle bodyStyle = createBodyCellStyle(workbook);
		// 把字体应用到当前的样式
		bodyStyle.setFont(createBodyFont(workbook));

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("我的钢铁(www.mysteel.com)"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("mysteel");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 400);
		int headrow = 1;

		if (HEADERS != null)
		{
			for (short i = 0; i < HEADERS.length; i++)
			{
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(new HSSFRichTextString(HEADERS[i]));
			}

			// 合并表头单元格
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
					NEXT_HEADERS.length - 1));
			headrow = 2;
		}

		// 产生表格次标题行
		HSSFRow row2 = sheet.createRow(headrow - 1);
		row2.setHeight((short) 400);
		HSSFCell cell = null;
		for (short i = 0; i < NEXT_HEADERS.length; i++)
		{
			cell = row2.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(new HSSFRichTextString(NEXT_HEADERS[i]));
		}

		Pattern p = Pattern.compile("^//d+(//.//d+)?$");
		Object obj = null;
		// 遍历集合数据，产生数据行
		for (int i = 0; i < dataList.size(); i++)
		{
			row = sheet.createRow(i + headrow);
			obj = dataList.get(i);
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			for (short j = 0; j < attrNames.length; j++)
			{
				cell = row.createCell(j);
				cell.setCellStyle(bodyStyle);
				String fieldName = attrNames[j];
				String methodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try
				{
					Method method = obj.getClass().getMethod(methodName,
							new Class[] {});
					Object value = method.invoke(obj, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;

					if (value != null)
					{
						if (value instanceof Boolean)
						{
							//
						}
						else if (value instanceof Date)
						{
							textValue = DateUtils.formatJustDate(((Date) value)
									.getTime());
						}
						else if (value instanceof byte[])
						{
							// 有图片时，设置行高为60px;
							row.setHeightInPoints(60);
							// 设置图片所在列宽度为80px,注意这里单位的一个换算
							sheet.setColumnWidth(j, (short) (35.7 * 80));
							// sheet.autoSizeColumn(i);
							byte[] byteValue = (byte[]) value;
							HSSFClientAnchor anchor = new HSSFClientAnchor(0,
									0, 1023, 255, (short) 6, i + 1, (short) 6,
									i + 1);
							anchor.setAnchorType(2);
							patriarch.createPicture(anchor, workbook
									.addPicture(byteValue,
											HSSFWorkbook.PICTURE_TYPE_JPEG));
						}
						else
						{
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
						}
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null)
					{
						if (p.matcher(textValue).matches())
						{
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						}
						else
						{
							cell.setCellValue(textValue);
						}
					}
				}
				catch (SecurityException e)
				{
					e.printStackTrace();
				}
				catch (NoSuchMethodException e)
				{
					e.printStackTrace();
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					e.printStackTrace();
				}
				finally
				{
					// 清理资源
				}
			}
		}
		row = sheet.createRow(dataList.size() + headrow);
		for (short j = 0; j < NEXT_HEADERS.length; j++)
		{
			cell = row.createCell(j);
			cell.setCellStyle(headerStyle);
			if (j == 0)
			{
				cell.setCellValue("合计:");
			}
			if (NEXT_HEADERS[j].equals("数量")) 
			{
				cell.setCellValue(numSum);
			}
			if (NEXT_HEADERS[j].equals("重量")) 
			{
				cell.setCellValue(weightSum);
			}
			if (NEXT_HEADERS[j].equals("货值"))
			//if (j == attrNames.length - 2)
			{
				cell.setCellValue(sum);
			}
			if (NEXT_HEADERS[j].equals("底价货值"))
			//if (j == attrNames.length - 1)
			{
				cell.setCellValue(lowSum);
			}
		}
		
	}
}
