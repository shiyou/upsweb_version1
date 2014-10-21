package com.eshore.upsweb.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.common.page.Pager;
import com.eshore.upsweb.model.PayLog;

public interface PayLogService {
	
public List<PayLog> listPayLogs();
	
	public PayLog findPayLogById(String id);
	
	public void delPayLogById(String id);
	
	public void updatePayLog(PayLog payLog);
	
	public void addPayLog(PayLog payLog);
	
	public Pager findPage();

	
	public Pager findPage(int pageNumber);

	/**
	 * 根据指定页数，数量返回pager 
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Pager findPage(int pageSize,int pageNumber);
	
	/**
	 * 根据参数查询返回pager
	 */
	public Pager findPageByParam(PayLog payLog);
	
	public Pager findPageByCriteria(DetachedCriteria detachedCriteria,int pageSize,int pageNumber);
	

}
