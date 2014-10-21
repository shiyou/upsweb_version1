package com.eshore.upsweb.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.common.page.Pager;
import com.eshore.upsweb.model.PayLog;

public interface PayLogDAO {
	
	public List<PayLog> listPayLogs();
	
	public PayLog findPayLogById(String id);
	
	public void delPayLogById(String id);
	
	public void updatePayLog(PayLog payLog);
	
	public void addPayLog(PayLog payLog);
	
	/**
	 * 按照返回所有pager(分页）
	 * 
	 */
	public Pager findPage();

	
	/**
	 * 返回指定页数的pager
	 */
	public Pager findPage(int pageNumber);
	
	/**
	 * 指定每页显示的数量
	 */
	public Pager findPage(int pageSize,int pageNumber);
	
	/**
	 * 按照条件返回pager
	 */
	public Pager findPageByParam(PayLog payLog,int pageSize,int pageNumber);
	
	public Pager findPageByCriteria(DetachedCriteria detachedCriteria,int pageSize,int pageNumber);
}
