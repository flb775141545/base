package com.flb.base.model;

import java.io.Serializable;

public class BaseEntity implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	public Long id;
	/**
	 * 创建时间
	 */
	public Long createTime;
	/**
	 * 最后修改时间
	 */
	public Long lastAccess;
	/**
	 * 版本号
	 */
	public int version;
	/**
	 * 是否逻辑删除
	 */
	public int isDeleted;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Long createTime)
	{
		this.createTime = createTime;
	}
	public Long getLastAccess()
	{
		return lastAccess;
	}
	public void setLastAccess(Long lastAccess)
	{
		this.lastAccess = lastAccess;
	}
	public int getVersion()
	{
		return version;
	}
	public void setVersion(int version)
	{
		this.version = version;
	}
	public int getIsDeleted()
	{
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted)
	{
		this.isDeleted = isDeleted;
	}
}
