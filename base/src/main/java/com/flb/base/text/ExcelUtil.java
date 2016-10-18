package com.flb.base.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.flb.base.common.DateUtils;
import com.flb.base.common.StringUtils;

public class ExcelUtil
{
	private static final int MAX_LENGTH = 20;
	
	/**
	 * 读取Excel
	 * @param excel			上传的excel文件
	 * @param memberCertificationInfo		
	 * @return
	 * @throws ExcelParseException
	 */
	public static List<Map<String, String>> readExcel(MultipartFile excel, String[] RESOURCE_HEAD_FIELD)
	{
		try{
			Workbook workbook = null; 
	
	        try 
	        { 
	        	DiskFileItem df = (DiskFileItem) ((CommonsMultipartFile) excel).getFileItem();
	        	
	        	workbook = new XSSFWorkbook(df.getStoreLocation().getAbsolutePath()); 
	        }
	        catch (Exception e)
	        {
	        	try
				{
					workbook = new HSSFWorkbook(excel.getInputStream());
				} catch (IOException e1)
				{
					e1.printStackTrace();
				} 
	        }
	
			Sheet sheet = workbook.getSheet("Sheet1");
			int iRowCount = sheet.getLastRowNum() + 1;
			
			List<Map<String, String>> datas = new ArrayList<Map<String, String>>(iRowCount);
	
			Map<String, String> rows;
			Row row;
			String sError;
			int iErrorCount = 0;
			StringBuilder sInValidateData = new StringBuilder();
	
			int headlength = RESOURCE_HEAD_FIELD.length;
			int iColumns = headlength;
			String head_filed[] = new String[iColumns];
	
			for (int i = 0; i < headlength; i++)
			{
				head_filed[i] = RESOURCE_HEAD_FIELD[i];
			}
	
			for(int iRow = 1; iRow < iRowCount; iRow++)
			{
				row = sheet.getRow(iRow);
				rows = new HashMap<String, String>(iColumns);
				
				for(int iCol = 0; iCol < iColumns; iCol++)
				{
					rows.put(head_filed[iCol], getCellValue(row.getCell(iCol)));				
				}
				
				sError = dataValid(rows);

				if(!sError.equals(""))
				{
					if(iErrorCount == 0)
					{
						sInValidateData.append("您的商品表格存在如下不合要求数据:<br/>");
					}

					sInValidateData.append("第<font color='red'>").append(iRow + 1).append("</font>行:").append(sError).append("<br/>");

					iErrorCount++;
				}
				
				datas.add(rows);
			}
	
			if (!sInValidateData.toString().equals(""))
			{
				throw new ExcelParseException(sInValidateData.toString());
			}
			
			return datas;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			if(e instanceof ExcelParseException)
			{
				throw new ExcelParseException(e.getMessage());
			}
			else
			{
				throw new ExcelParseException("读取Excel文档发生错误，请检查后重试！");
			}
		}
			
	}

	private static String getCellValue(Cell cell)
	{
		if(cell == null)
		{
			return "";
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_STRING)
		{
			return StringUtils.isTrimEmpty(cell.getStringCellValue()) ? "" : cell.getStringCellValue();
		}
		else if(DateUtil.isCellDateFormatted(cell))
		{
			Date theDate = cell.getDateCellValue();

			return String.valueOf(DateUtils.formatDate(theDate.getTime(), "yyyy-MM-dd mm:HH:ss"));
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
		{
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return StringUtils.isTrimEmpty(cell.getStringCellValue()) ? "" : cell.getStringCellValue();
//			return String.valueOf(cell.getNumericCellValue());
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_BLANK)
		{
			return String.valueOf("");
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
				sValue = String.valueOf(cell.getNumericCellValue());
			}
			catch(Exception e)
			{
				sValue = cell.getStringCellValue();
			}
			
			return sValue;
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_ERROR)
		{
			return String.valueOf(cell.getErrorCellValue());
		}
		else
		{
			return "";
		}		
	}
	
	private static String dataValid(Map<String, String> datasMap)
	{
		StringBuilder sError = new StringBuilder();
		
		errorData(sError, datasMap, MAX_LENGTH, "breedName", 0);
		errorData(sError, datasMap, MAX_LENGTH, "spec", 0);
		errorData(sError, datasMap, MAX_LENGTH, "material", 0);
		errorData(sError, datasMap, MAX_LENGTH, "factory", 0);
		errorData(sError, datasMap, 50, "baleNum", 1);
		errorData(sError, datasMap, MAX_LENGTH, "weightType", 1);
		errorData(sError, datasMap, MAX_LENGTH, "num", 0);
		errorData(sError, datasMap, MAX_LENGTH, "weight", 0);
		errorData(sError, datasMap, MAX_LENGTH, "price", 0);
		errorData(sError, datasMap, MAX_LENGTH, "productTime", 1);

		return sError.toString();
	}
	
	/**
	 * 判断字符串长度
	 * @param sError
	 * @param datasMap
	 * @param length		最大长度
	 * @param key
	 * @param status		0字符串不能为空数据，1字符串可为空数据，2数字不为空并要大于0数据
	 */
	private static void errorData(StringBuilder sError, Map<String, String> datasMap, int length, String key, int status)
	{
		if (datasMap.containsKey(key))
		{
			if (status == 0)
			{
				if (datasMap.get(key).equals("-"))
				{
					sError.append("[").append(check(key) + "不能为空！").append("]");
				}
			}
			else if (status == 2)
			{
				if (datasMap.get(key).equals("-"))
				{
					sError.append("[").append(check(key) + "不能为空！").append("]");
				}
				else if (Float.valueOf(datasMap.get(key)) <= 0)
				{
					sError.append("[").append(check(key) + "必须要大于0！").append("]");
				}
			}

			if (datasMap.get(key).length() > length)
			{
				sError.append("[").append(check(key) + "长度不能超过" + length + "！").append("]");
			}
		}
	}

	private static String check(String key)
	{
		if(key.equals("breedName"))
		{
			return "品种名";
		}
		if(key.equals("spec"))
		{
			return "规格";
		}
		if(key.equals("material"))
		{
			return "材质";
		}
		if(key.equals("factory"))
		{
			return "钢厂";
		}if(key.equals("baleNum"))
		{
			return "捆包号";
		}
		if(key.equals("weightType"))
		{
			return "记重方式";
		}
		if(key.equals("num"))
		{
			return "数量";
		}
		if(key.equals("weight"))
		{
			return "重量";
		}
		if(key.equals("price"))
		{
			return "单价";
		}
		if(key.equals("productTime"))
		{
			return "生产日期";
		}
		
		return null;
	}
	
	public static List<Map<String, String>> readExcel(MultipartFile excelFile, String[] titleKey, String[] title)
	{
		if (excelFile == null)
		{
			throw new RuntimeException("Excel 文件为空！");
		}
		if (titleKey == null || 0 == titleKey.length)
		{
			throw new RuntimeException("Excel 标题键值为空！");
		}
		
		Workbook workbook = null; 
		
		try
		{
			workbook = new XSSFWorkbook(excelFile.getInputStream());
		}
		catch (Exception e)
		{
			try
			{
				workbook = new HSSFWorkbook(excelFile.getInputStream());
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		
		if (workbook == null)
		{
			throw new RuntimeException("读取Excel 文档发生错误，请检查后重试！");
		}
		
		Sheet sheet = workbook.getSheetAt(0);
		if (sheet != null && sheet.getLastRowNum() > 0)
		{
			if (title != null)
			{
				validateTitle(sheet.getRow(0), titleKey, title);
			}
			
			List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>(sheet.getLastRowNum());
			for (int i = 1; i <= sheet.getLastRowNum(); i++)
			{
				Row row = sheet.getRow(i);
				if (row != null)
				{
					Map<String, String> dataMap = new HashMap<String, String>(titleKey.length);
					for (int j = 0; j < titleKey.length; j++)
					{
						String value = null;
						Cell cell = row.getCell(j);
						if (cell != null)
						{
							value = getCellValue(cell);
						}
						
						dataMap.put(titleKey[j], value);
					}
					
					dataMapList.add(dataMap);
				}
			}
			
			return dataMapList;
		}
		
		return null;
	}

	private static void validateTitle(Row titleRow, String[] titleKey, String[] title)
	{
		if (title == null || 0 == title.length)
		{
			throw new RuntimeException("Excel 第一行标题为空！");
		}
		if (titleKey.length != title.length)
		{
			throw new RuntimeException("Excel 标题键值和标题长度不一致！");
		}
		
		if (titleRow != null)
		{
			StringBuilder sb = new StringBuilder();
			
			for (int k = 0; k < titleKey.length; k++)
			{
				Cell cell = titleRow.getCell(k);
				
				if (cell != null)
				{
					String value = getCellValue(cell);
					
					if (!value.trim().contains(title[k].trim()))
					{
						sb.append("[第 " + (k+1) + " 列应是" + title[k] + "]");
					}
				}
				else
				{
					sb.append("[第  " + (k+1) + " 列是" + title[k] + "]");
				}
			}
			
			if (!"".equals(sb.toString()))
			{
				throw new RuntimeException("Excel 模板读取错误 "+ sb.toString() + "，请检查后重试！");
			}
		}
	}
}