package com.flb.base.dao;

import java.util.List;
import java.util.Map;

import com.flb.base.model.BaseEntity;

/**
 * 基础DAO
 * 
 * @author wuxiaoping
 *
 * @param <T>
 */
public interface BaseDao<T extends BaseEntity>
{
	/**
	 * 新增
	 * 
	 * @param entity
	 */
	public void insert(T entity);
	
	/**
	 * 修改
	 * 
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * 根据主键查找对象
	 * 
	 * @param id
	 * @return
	 */
	public T findById(long id);
	
	/**
	 * 根据主键删除对象
	 * 
	 * @param id
	 */
	public void deleteById(long id);
	
	/**
	 * 条件查询记录数
	 * 
	 * @param params
	 * @return
	 */
	public int count(Map<String, Object> params);
	
	/**
	 * 条件查询列表
	 * 
	 * @param params
	 * @return
	 */
	public List<T> query(Map<String, Object> params);
}
