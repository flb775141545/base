//package com.banksteel.erp.utils;
//
//import java.util.Comparator;
//
//import com.banksteel.erp.inventory.web.dto.InventoryExportDTO;
//
//@SuppressWarnings("rawtypes")
//public class ComparatorInventory implements Comparator
//{
//
//	@Override
//	public int compare(Object o1, Object o2)
//	{
//		InventoryExportDTO dto1 = (InventoryExportDTO)o1;
//		InventoryExportDTO dto2 = (InventoryExportDTO)o2;
//		
//		int flag = dto1.getFactory().compareTo(dto2.getFactory());
//		
//		 if (flag==0)
//		 {
//			 return dto2.getDay().compareTo(dto1.getDay());
//		 }
//		 else
//		 {
//			 return flag;
//		 }  
//	}
//
//}
