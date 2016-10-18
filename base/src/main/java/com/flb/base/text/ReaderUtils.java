package com.flb.base.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.flb.base.common.NumberUtils;
import com.flb.base.common.ObjectUtils;

public class ReaderUtils 
{
	private static Map<String, String> titleMap = null;
	static
	{/*
		品种：类别，物资名称，产品，存货名称，产品名称，品种名称，材料名称，货物名称，现货品种，货品名称
		牌号：材质，钢种，材料，参考牌号，现货材质，牌号（或材质），材质*
		规格：现货库存规格，规格型号，成品规格，产品规格，此规格仅供参考，现货规格，规格（mm），规格/型号/厚度，品种规格，规格*，参考规格
		产地：钢厂，钢厂/仓库，产地/仓库，生产厂家，产地名称，生产商，厂家或品牌
		仓库：存放仓库，仓库/到站，交货地，库位，提货地址，仓库名称，交货仓库，提货点，场地
		可销量：可卖重量，吨位，库存数量，可供量，重量，数量，数量（吨），参考重量，可供件数，件重（T），库存量
		限价：价格，总部价格，切割价格，过磅价，理算价，单价（元），销售定价，单价，销售单价，零售价格，统盘价格*/
		
		titleMap = new HashMap<String, String>();
		
		titleMap.put("品种", "品种");
		titleMap.put("类别", "品种");
		titleMap.put("名称", "品种");
		titleMap.put("物资名称", "品种");
		titleMap.put("产品", "品种");
		titleMap.put("存货名称", "品种");
		titleMap.put("产品名称", "品种");
		titleMap.put("品种名称", "品种");
		titleMap.put("材料名称", "品种");
		titleMap.put("货物名称", "品种");
		titleMap.put("现货品种", "品种");
		titleMap.put("货品名称", "品种");
		titleMap.put("品名", "品种");
		
		titleMap.put("牌号", "牌号");
		titleMap.put("材质", "牌号");
		titleMap.put("钢种", "牌号");
		titleMap.put("材料", "牌号");
		titleMap.put("参考牌号", "牌号");
		titleMap.put("现货材质", "牌号");
		titleMap.put("牌号（或材质）", "牌号");
		titleMap.put("牌号(或材质)", "牌号");
		titleMap.put("材质*", "牌号");
		titleMap.put("钢号", "牌号");
		
		titleMap.put("规格", "规格");
		titleMap.put("现货库存规格", "规格");
		titleMap.put("规格型号", "规格");
		titleMap.put("成品规格", "规格");
		titleMap.put("产品规格", "规格");
		titleMap.put("此规格仅供参考", "规格");
		titleMap.put("现货规格", "规格");
		titleMap.put("规格（mm）", "规格");
		titleMap.put("规格(mm)", "规格");
		titleMap.put("规格/型号/厚度", "规格");
		titleMap.put("品种规格", "规格");
		titleMap.put("规格*", "规格");
		titleMap.put("参考规格", "规格");
		
		titleMap.put("产地", "产地");
		titleMap.put("钢厂", "产地");
		titleMap.put("钢厂/仓库", "产地");
		titleMap.put("产地/仓库", "产地");
		titleMap.put("生产厂家", "产地");
		titleMap.put("产地名称", "产地");
		titleMap.put("生产商", "产地");
		titleMap.put("厂家或品牌", "产地");
		titleMap.put("产地/品牌", "产地");
		titleMap.put("品牌", "产地");
		
		titleMap.put("仓库", "仓库");
		titleMap.put("存放仓库", "仓库");
		titleMap.put("仓库/到站", "仓库");
		titleMap.put("交货地", "仓库");
		titleMap.put("库位", "仓库");
		titleMap.put("提货地址", "仓库");
		titleMap.put("仓库名称", "仓库");
		titleMap.put("交货仓库", "仓库");
		titleMap.put("提货点", "仓库");
		titleMap.put("场地", "仓库");
		titleMap.put("提货地", "仓库");
		
		titleMap.put("可销量", "可销量");
		titleMap.put("可卖重量", "可销量");
		titleMap.put("吨位", "可销量");
		titleMap.put("库存数量", "可销量");
		titleMap.put("可供量", "可销量");
		titleMap.put("重量", "可销量");
		titleMap.put("数量", "可销量");
		titleMap.put("数量（吨）", "可销量");
		titleMap.put("数量(吨)", "可销量");
		titleMap.put("参考重量", "可销量");
		titleMap.put("可供件数", "可销量");
		titleMap.put("件重（T）", "可销量");
		titleMap.put("件重(T)", "可销量");
		titleMap.put("件重", "可销量");
		titleMap.put("库存量", "可销量");
		
		titleMap.put("价格", "价格");
		titleMap.put("限价", "价格");
		titleMap.put("总部价格", "价格");
		titleMap.put("切割价格", "价格");
		titleMap.put("过磅价", "价格");
		titleMap.put("理算价", "价格");
		titleMap.put("单价（元）", "价格");
		titleMap.put("单价(元)", "价格");
		titleMap.put("单价(元/吨)", "价格");
		titleMap.put("单价（元/吨）", "价格");
		titleMap.put("价格(元/吨)", "价格");
		titleMap.put("价格（元/吨）", "价格");
		titleMap.put("销售定价", "价格");
		titleMap.put("单价", "价格");
		titleMap.put("销售单价", "价格");
		titleMap.put("零售价格", "价格");
		titleMap.put("统盘价格", "价格");
		titleMap.put("挂牌价", "价格");
		titleMap.put("议价", "价格");
		titleMap.put("特价", "价格");
	}
	
	public static List<Map<String, String>> readWordTable(String filePath)
	{
		List<Map<String, String>> list = null;
		try 
		{
			if(ObjectUtils.notNull(filePath))
			{
				if(filePath.endsWith(".doc"))
				{
					list = getWordTable03(filePath);
				}
				else if(filePath.endsWith(".docx"))
				{
					list = getWordTable07(filePath);
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 读文件信息
	 * @param filePath  文件路径
	 * @return
	 * @throws Exception 
	 */
	public static List<Map<String, String>> read(String filePath)
	{
		List<Map<String, String>> list = null;
		FileInputStream is = null;
		
		try 
		{
			is = new FileInputStream(new File(filePath));
			
			if(ObjectUtils.notNull(filePath))
			{
				list = new ArrayList<Map<String, String>>();
				
				if(filePath.endsWith(".xls"))
				{
					List<HSSFSheet> sheets = readExcel03(is);
					for(Sheet sheet : sheets)
					{
						list.addAll(excelList(sheet,filePath));
					}
				}
				else if(filePath.endsWith(".xlsx"))
				{
					List<XSSFSheet> sheets = readExcel07(is);
					for(Sheet sheet : sheets)
					{
						list.addAll(excelList(sheet,filePath));
					}
				}
				else if(filePath.endsWith(".doc"))
				{
					list = getWordTable03(filePath);
				}
				else if(filePath.endsWith(".docx"))
				{
					list = getWordTable07(filePath);
				}
				else if(filePath.endsWith(".txt"))
				{
					readTXT(filePath);
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 读word2003版本table
	 * @param filePath
	 * @return
	 */
	private static List<Map<String, String>> getWordTable03(String filePath)
    {  
    	Map<String, String> resultMap = null;
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = null;
		boolean flag = false;
		
        try{  
            FileInputStream in = new FileInputStream(filePath);
            POIFSFileSystem pfs = new POIFSFileSystem(in);
            
            HWPFDocument hwpf = new HWPFDocument(pfs);   
            Range range = hwpf.getRange();
            TableIterator it = new TableIterator(range);  
            
            while(it.hasNext())
			{
            	Table tb = (Table) it.next();  
            	for (int i = 0; i < tb.numRows(); i++) 
                {  
            		TableRow row = tb.getRow(i); 
            		
					if(flag)
					{
						map = new HashMap<>();
						
						Set<String> keys = resultMap.keySet();
						for(String key : keys)
						{
							TableCell cell = row.getCell(Integer.parseInt(key.split(",")[1]));
							if(ObjectUtils.notNull(cell))
							{
								String value = "";
		                        for(int k=0;k<cell.numParagraphs();k++)
		                        {     
		                            Paragraph para =cell.getParagraph(k);     
		                            value = para.text();     
		                        }  
		                        
								map.put(key.split(",")[0], value.substring(0, value.length()-1));
							}
						}
						
						if(checkRecord(map))
						{
							list.add(map);
						}
					}else
					{
						//列
						for (int j = 0; j < row.numCells(); j++) 
	                    {     
	                        TableCell cell = row.getCell(j);
	                        
	                        String value = "";
	                        for(int k=0;k<cell.numParagraphs();k++)
	                        {     
	                            Paragraph para =cell.getParagraph(k);     
	                            value = para.text();     
	                        }  
	                        
	                        value = replaceBlank(value.substring(0, value.length()-1));
	                        
	                        if(titleMap.containsKey(value))
							{
								if(ObjectUtils.isEmpty(resultMap))
								{
									resultMap = new HashMap<>();
									flag = true;
								}
								
								resultMap.put(titleMap.get(value)+","+j, value);
							}
	                    } 
					}
                }
				
				flag = false;
				if(ObjectUtils.notEmpty(resultMap))
				{
					resultMap.clear();
				}
			}
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        
        return list;
    }
	
	/**
	 * 读word2007及以上版本table
	 * @param filePath
	 * @return
	 */
	private static List<Map<String, String>> getWordTable07(String filePath)
    {  
    	Map<String, String> resultMap = null;
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = null;
		boolean flag = false;
		
        OPCPackage opcPackage;
		try 
		{
			opcPackage = POIXMLDocument.openPackage(filePath);
			XWPFDocument xwpf = new XWPFDocument(opcPackage);
			
			//table
			Iterator<XWPFTable> it = xwpf.getTablesIterator();
			while(it.hasNext())
			{
				XWPFTable table = it.next();
				List<XWPFTableRow> rows=table.getRows();
				
				for(XWPFTableRow row : rows)
				{
					List<XWPFTableCell> cells = row.getTableCells();
					
					if(flag)
					{
						map = new HashMap<>();
						
						Set<String> keys = resultMap.keySet();
						for(String key : keys)
						{
							XWPFTableCell cell = cells.get(Integer.parseInt(key.split(",")[1]));
							if(ObjectUtils.notNull(cell))
							{
								map.put(key.split(",")[0], cell.getText());
							}
						}
						
						if(checkRecord(map))
						{
							list.add(map);
						}
					}else
					{
						//列
						for(int i=0; i<cells.size(); i++)
						{
							XWPFTableCell cell = cells.get(i);
							
							if(ObjectUtils.notNull(cell))
							{
								String value = replaceBlank(cell.getText());
								
								if(titleMap.containsKey(value))
								{
									if(ObjectUtils.isEmpty(resultMap))
									{
										resultMap = new HashMap<>();
										flag = true;
									}
									
									resultMap.put(titleMap.get(value)+","+i, value);
								}
							}
						}
					}
				}
				
				flag = false;
				if(ObjectUtils.notEmpty(resultMap))
				{
					resultMap.clear();
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}  
		
		return list;
    }
	
	/**
	 * 读Excel表信息
	 * @param filePath  文件路径
	 * @return
	 * @throws Exception 
	 */
	public static List<Map<String, String>> readExel(String filePath)
	{
		List<Map<String, String>> list = null;
		FileInputStream is = null;
		
		try 
		{
			is = new FileInputStream(new File(filePath));
			
			if(ObjectUtils.notNull(filePath))
			{
				list = new ArrayList<Map<String, String>>();
				
				if(filePath.endsWith(".xls"))
				{
					List<HSSFSheet> sheets = readExcel03(is);
					for(Sheet sheet : sheets)
					{
						list.addAll(excelList(sheet,filePath));
					}
				}
				else if(filePath.endsWith(".xlsx"))
				{
					List<XSSFSheet> sheets = readExcel07(is);
					for(Sheet sheet : sheets)
					{
						list.addAll(excelList(sheet,filePath));
					}
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 读2003版本Excel文件
	 * @param is
	 */
	private static List<HSSFSheet> readExcel03(FileInputStream is)
	{
		List<HSSFSheet> sheets = null;
		HSSFWorkbook workbook;
		
		try 
		{
			workbook = new HSSFWorkbook(is);
			
			int sheetNum = workbook.getNumberOfSheets();
			
			sheets = new ArrayList<HSSFSheet>(sheetNum);
			
			for(int i=0; i<sheetNum; i++)
			{
				sheets.add(workbook.getSheetAt(i));
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return sheets;
	}
	
	/**
	 * 读2007以上版本Excel文件
	 * @param is
	 */
	private static List<XSSFSheet> readExcel07(FileInputStream is)
	{
		List<XSSFSheet> sheets = null;
		XSSFWorkbook workbook;
		
		try 
		{
			workbook = new XSSFWorkbook(is);
			int sheetNum = workbook.getNumberOfSheets();
			
			sheets = new ArrayList<XSSFSheet>(sheetNum);
			
			for(int i=0; i<sheetNum; i++)
			{
				sheets.add(workbook.getSheetAt(i));
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return sheets;
	}
	
	private static List<Map<String, String>> excelList(Sheet sheet,String filePath) throws Exception
	{
		if(ObjectUtils.isNull(sheet))
		{
			throw new Exception("读取的Excel工作簿不存在！！");
		}
		int rowCount = sheet.getLastRowNum()+1;
		
		Map<String, String> resultMap = null;
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = null;
		boolean flag = false;
		
		//行
		for(int i=0; i<rowCount; i++)
		{
			Row row = sheet.getRow(i);
			if(ObjectUtils.notNull(row))
			{
				if(ObjectUtils.notNull(row))
				{
					if(flag)
					{
						map = new HashMap<>();
						
						Set<String> keys = resultMap.keySet();
						for(String key : keys)
						{
							Cell cell = row.getCell(Integer.parseInt(key.split(",")[1]));
							if(ObjectUtils.notNull(cell))
							{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								map.put(key.split(",")[0], cell.getStringCellValue());
							}
						}
						
						if(checkRecord(map))
						{
							list.add(map);
						}
					}else
					{
						int cellCount = row.getLastCellNum();
						
						//列
						for(int j=0; j<cellCount; j++)
						{
							Cell cell = row.getCell(j);
							
							if(ObjectUtils.notNull(cell))
							{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								String value = replaceBlank(cell.getStringCellValue());
								
								if(titleMap.containsKey(value))
								{
									if(ObjectUtils.isEmpty(resultMap))
									{
										resultMap = new HashMap<>();
										flag = true;
									}
									
									resultMap.put(titleMap.get(value)+","+j, value);
								}
							}
						}
					}
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 读TXT文档
	 * @param filePath
	 * @return
	 */
	public static List<Map<String, String>> readTXT(String filePath)
	{
		Map<String, String> resultMap = null;
 		List<Map<String, String>> list = new ArrayList<>();
 		Map<String, String> map = null;
 		boolean flag = false;
 		int count = 0;
 		
        try 
        {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists())//判断文件是否存在
            { 
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null)
                {
                	lineTxt = replaceBlank(lineTxt, ",");
                    
					if(flag)
					{
						map = new HashMap<>();
						
						Set<String> keys = resultMap.keySet();
						for(String key : keys)
						{
							String [] cells1 = lineTxt.split(",");
							
							if(cells1.length >= count)
							{
								map.put(key.split(",")[0], cells1[NumberUtils.parseInt(key.split(",")[1])]);
							}
						}
						
						if(ObjectUtils.notEmpty(map) && checkRecord(map))
						{
							list.add(map);
						}
					}else
					{
						String [] cells = lineTxt.split(",");
						int cellCount = cells.length;
						
						for(int j=0; j<cellCount; j++)
						{
							String value = replaceBlank(cells[j]);
								
							if(titleMap.containsKey(value))
							{
								if(ObjectUtils.isEmpty(resultMap))
								{
									resultMap = new HashMap<>();
									flag = true;
								}
								
								resultMap.put(titleMap.get(value)+","+j, value);
							}
						}
						
						if(ObjectUtils.notNull(resultMap))
						{
							count = cellCount;
						}
					}
                }
                
                read.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return list;
    }
	
	//去字符串空格
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
	
	//替换字符串空格
	public static String replaceBlank(String str, String sign) 
	{
        String returnStr = "";
        if (str!=null) {
        	str = str.trim();
        	Pattern p = Pattern.compile("[' ']+"); 
            Matcher m = p.matcher(str);
            returnStr = m.replaceAll(sign);
        }
        return returnStr;
    }
	
	//检测数据是否规范,每条记录必须有品种、牌号、规格
	public static boolean checkRecord(Map<String, String> map)
	{
		String breed = map.get("品种");
		String material = map.get("牌号");
		String spec = map.get("规格");
		
		if(breed != null && !"".equals(breed) && material != null && !"".equals(material) && spec != null && !"".equals(spec))
		{
			return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) 
	{
//		List<Map<String, String>> excelList03 = ReaderUtils.read("E:\\tmp\\reader\\共佳资源导出).xlsx");
		List<Map<String, String>> excelList07 = ReaderUtils.read("E:\\tmp\\reader\\共佳资源导出).xlsx");
//		List<Map<String, String>> wordList03 = ReaderUtils.read("E://tmp//reader//test.doc");
//		List<Map<String, String>> wordList07 = ReaderUtils.read("E://tmp//reader//test.docx");
//		
//		if(ObjectUtils.notEmpty(excelList03))
//		{
//			System.out.println(excelList03.toString());
//		}
//		
		if(ObjectUtils.notEmpty(excelList07))
		{
			System.out.println(excelList07.toString());
		}
//		
//		if(ObjectUtils.notEmpty(wordList03))
//		{
//			System.out.println(wordList03.toString());
//		}
//		
//		if(ObjectUtils.notEmpty(wordList07))
//		{
//			System.out.println(wordList07.toString());
//		}
//		
//		System.out.println(getWordTable03("E://tmp//reader//浙江上物金属有限公司12.16.doc").toString());
//		
//		System.out.println(read("E:\\tmp\\reader\\浙江环洲钢板资源表12.5.txt").toString());
	}
}
