package com.flb.base.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.flb.base.common.ObjectUtils;


public class ExcelShower
{
	public static Map<String, String> read(String filePath, String resourceId)
	{
		if (filePath.substring(filePath.lastIndexOf(".") + 1).equalsIgnoreCase("xls"))
		{
			return readXls(filePath, resourceId);
		}
		else if (filePath.substring(filePath.lastIndexOf(".") + 1).equalsIgnoreCase("xlsx"))
		{
			return readXlsx(filePath, resourceId);
		}

		return null;
	}

	public static Map<String, String> readXls(String filePath, String resourceId)
	{
		StringBuilder builder = new StringBuilder();
		StringBuilder anchorbuilder = new StringBuilder();

		File file = new File(filePath);
		Map<String, String> rtnMap = new HashMap<String, String>(2);	//���ê��,����
		if (file.exists())
		{
			try
			{
				FileInputStream is = new FileInputStream(file);
				HSSFWorkbook workbook = new HSSFWorkbook(is);

				if (workbook != null)
				{
					int sheetNum = workbook.getNumberOfSheets();

					if (sheetNum > 0)
					{
						for (int i = 0; i < sheetNum; i++)
						{
							HSSFSheet sheet = workbook.getSheetAt(i);
							if (sheet != null)
							{
								String sheetName = workbook.getSheetName(i);
								
								String divName = "sheet" + i;
								String mName = "#sheet" + i;
								
								anchorbuilder.append("<a href="+ mName +">");
								anchorbuilder.append(sheetName);
								anchorbuilder.append("</a> |  ");
								
								builder.append("<h2 id=\"" + divName + "\" style=\"margin-top:0; font-weight:blod;\">"); 
								builder.append(sheetName); 
								builder.append("</h2>"); 


								int firstRowNum = sheet.getFirstRowNum();
								int lastRowNum = sheet.getLastRowNum();

								builder.append("<table style=\"width:100%;border:0 px;cellpadding:0; cellspacing:0;\">");
								builder.append("<tbody>");

								for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++)
								{
									HSSFRow row = sheet.getRow(rowNum);

									if(!isNullRow(row))
									{
										int lastCellNum = row.getLastCellNum();
										
										StringBuilder sbRow = new StringBuilder(); 
										
										sbRow.append("<tr class=\"bg\" onmouseover=\"this.className='thover'\" onmouseout=\"this.className='bg'\">");

										for (int cellNum = 0; cellNum < lastCellNum; cellNum++)
										{
											HSSFCell cell =  row.getCell(cellNum);
											
											if (cell != null)
											{
												cell.setCellType(Cell.CELL_TYPE_STRING);
												sbRow.append("<td align=\"center\" width=\"7%\">" + getCellValue(cell) + "</td>");
											}
											else
											{
												sbRow.append("<td align=\"center\" width=\"3%\">&nbsp;</td>");
											}
										}
										sbRow.append("</tr>");
										builder.append(sbRow.toString());
									}
								}

								builder.append("</tbody>");
								builder.append("</table>");
							}
						}
					}
				}
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		rtnMap.put("anchor", anchorbuilder.toString());	//ê��
		rtnMap.put("html", builder.toString());		//����
		
		return  rtnMap;
	}
	
	private static boolean isNullRow(HSSFRow row)
	{
		Map<Object, Object> map = null;
		
		if(ObjectUtils.notNull(row))
		{
			int firstCellNum = row.getFirstCellNum();
			int lastCellNum = row.getLastCellNum();
			
			for(int i=firstCellNum; i<lastCellNum; i++)
			{
				Cell cell = row.getCell(i);
				if(ObjectUtils.notNull(cell))
				{
					if(cell.getCellType() == Cell.CELL_TYPE_BLANK)
					{
						continue;
					}
					
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String value = cell.getStringCellValue();
					
					if(value != null)
					{
						value = replaceBlank(value);
						
						if(!"".equals(value))
						{
							if(ObjectUtils.isNull(map))
							{
								map = new HashMap<>();
							}
							map.put(value, value);
						}
					}
				}
			}
		}
		
		if(ObjectUtils.notNull(map))
		{
			return false;
		}
		
		return true;
	}
	
	
	//�滻�ַ�ո�
	public static String replaceBlank(String str) 
	{
        String returnStr = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*");
            Matcher m = p.matcher(str);
            returnStr = m.replaceAll("");
        }
        return returnStr;
    }

	public static Map<String, String> readXlsx(String filePath, String resourceId)
	{
		StringBuilder builder = new StringBuilder();
		StringBuilder anchorbuilder = new StringBuilder();

		File file = new File(filePath);
		Map<String, String> rtnMap = new HashMap<String, String>(2);	//���ê��,����
		if (file.exists())
		{
			try
			{
				FileInputStream is = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(is);

				int sheetNum = workbook.getNumberOfSheets();

				if (sheetNum > 0)
				{
					for (int i = 0; i < sheetNum; i++)
					{
						XSSFSheet sheet = workbook.getSheetAt(i);
						
						if (sheet != null)
						{
							String sheetName = workbook.getSheetName(i);
							
							String divName = "sheet" + i;
							String mName = "#sheet" + i;
							
							anchorbuilder.append("<a href="+ mName +">");
							anchorbuilder.append(sheetName);
							anchorbuilder.append("</a> |  ");
							
							builder.append("<h2 id=\"" + divName + "\" style=\"margin-top:0; font-weight:blod;\">"); 
							builder.append(sheetName); 
							builder.append("</h2>"); 
							
							int firstRowNum = sheet.getFirstRowNum();
							int lastRowNum = sheet.getLastRowNum();

							builder.append("<table style=\"width:100%;border:0 px;cellpadding:0; cellspacing:0;\">");
							builder.append("<tbody>");
							for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++)
							{
								XSSFRow row = sheet.getRow(rowNum);
								if(!isNullXSSFRowRow(row))
								{
									int firstCellNum = row.getFirstCellNum();
									int lastCellNum = row.getLastCellNum();
									
									StringBuilder sbRow = new StringBuilder(); 
									
									sbRow.append("<tr class=\"bg\" onmouseover=\"this.className='thover'\" onmouseout=\"this.className='bg'\">");

									for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++)
									{
										XSSFCell cell =  row.getCell(cellNum);
										
										if (cell != null)
										{
											cell.setCellType(Cell.CELL_TYPE_STRING);
											sbRow.append("<td align=\"center\" width=\"7%\">" + getCellValue(cell) + "</td>");
										}
										else
										{
											sbRow.append("<td align=\"center\" width=\"3%\">&nbsp;</td>");
										}
									}
									sbRow.append("</tr>");
									builder.append(sbRow.toString());
								}
								
							}
							builder.append("</tbody>");
							builder.append("</table>");
						}
					}
					
				}
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		rtnMap.put("anchor", anchorbuilder.toString());	//ê��
		rtnMap.put("html", builder.toString());		//����
		
		return  rtnMap;
	}

	private static boolean isNullXSSFRowRow(XSSFRow row)
	{
		Map<Object, Object> map = null;
		
		if(ObjectUtils.notNull(row))
		{
			int firstCellNum = row.getFirstCellNum();
			int lastCellNum = row.getLastCellNum();
			
			for(int i=firstCellNum; i<lastCellNum; i++)
			{
				Cell cell = row.getCell(i);
				if(ObjectUtils.notNull(cell))
				{
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String value = cell.getStringCellValue();
					
					if(value != null)
					{
						value = replaceBlank(value);
						
						if(!"".equals(value))
						{
							if(ObjectUtils.isNull(map))
							{
								map = new HashMap<>();
							}
							map.put(value, value);
						}
					}
				}
			}
		}
		
		if(ObjectUtils.notNull(map))
		{
			return false;
		}
		
		return true;
	}
	
	private static String getCellValue(Cell cell)
	{
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);

		if(cell == null)
		{
			return "";
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_STRING)
		{
			return cell.getStringCellValue().replaceAll("\\s", "");
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
		{
			if (DateUtil.isCellDateFormatted(cell))
			{
				Date date = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(date);
			}

			return format.format(cell.getNumericCellValue());
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
		{
			return String.valueOf(cell.getBooleanCellValue());
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA)
		{
			String sValue = "";

			try
			{
				sValue = format.format(cell.getNumericCellValue());
			}
			catch(Exception e)
			{
				sValue = cell.toString();
			}

			return sValue;
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_ERROR)
		{
			return String.valueOf(cell.getErrorCellValue());
		}
		else
		{
			return cell.toString().replaceAll("\\s", "");
		}		
	}

	public static void main(String[] args) throws Exception
	{
		String excelFileName = "E:\\work\\banksteel\\������Դ.xls";
		read(excelFileName, "123");
	}
}