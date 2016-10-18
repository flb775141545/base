package com.flb.base.page;

import java.io.Serializable;
import java.util.List;

/**
 * PageSet
 * 
 * @author wuxiaoping
 * @version 1.0.0
 */
public class PageSet<T> implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int totalRows;
	private int pageSize;
	private int currPageNum;

	private int totalPages;
	private int startRow;
	private int endRow;

	private List<T> pageData;

	private PageSet()
	{
	}

	private void init(int totalRows, int currPageNum, int pageSize)
	{
		this.totalRows = totalRows;
		this.pageSize = pageSize;
		this.currPageNum = currPageNum;

		this.totalPages = totalRows / pageSize;

		if (totalRows % pageSize != 0)
		{
			this.totalPages++;
		}

		if (currPageNum <= 0)
		{
			this.currPageNum = 1;
		}

		if (this.totalPages < currPageNum)
		{
			this.currPageNum = this.totalPages;
		}

		this.startRow = (this.currPageNum - 1) * this.pageSize;
		this.endRow = this.startRow + this.pageSize;

		if (this.totalRows < this.endRow)
		{
			this.endRow = this.totalRows;
		}
	}

	public int getTotalRows()
	{
		return totalRows;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public int getCurrPageNum()
	{
		return currPageNum;
	}

	public int getTotalPages()
	{
		return totalPages;
	}

	public int getStartRow()
	{
		return startRow;
	}

	public int getEndRow()
	{
		return endRow;
	}

	public List<T> getPageData()
	{
		return pageData;
	}

	public void setPageData(List<T> pageData)
	{
		this.pageData = pageData;
	}

	public static <T> PageSet<T> createPageSet(int totalRows, int currPageNum,
			int pageSize)
	{
		PageSet<T> pageset = new PageSet<>();
		pageset.init(totalRows, currPageNum, pageSize);

		return pageset;
	}
}