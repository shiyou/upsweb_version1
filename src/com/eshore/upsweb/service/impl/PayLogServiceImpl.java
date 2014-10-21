package com.eshore.upsweb.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.page.Pager;
import com.eshore.upsweb.dao.PayLogDAO;
import com.eshore.upsweb.model.PayLog;
import com.eshore.upsweb.service.PayLogService;

@Service
public class PayLogServiceImpl implements PayLogService {
	
	@Autowired
	private PayLogDAO payLogDAO;

	public PayLogDAO getPayLogDAO() {
		return payLogDAO;
	}

	public void setPayLogDAO(PayLogDAO payLogDAO) {
		this.payLogDAO = payLogDAO;
	}

	@Override
	public void addPayLog(PayLog payLog) {
		payLogDAO.addPayLog(payLog);
	}

	@Override
	public void delPayLogById(String id) {
		payLogDAO.delPayLogById(id);

	}

	@Override
	public Pager findPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pager findPage(int pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pager findPage(int pageSize, int pageNumber) {
		return payLogDAO.findPage(pageSize, pageNumber);
	}

	@Override
	public PayLog findPayLogById(String id) {
		return payLogDAO.findPayLogById(id);
	}

	@Override
	public List<PayLog> listPayLogs() {
		return payLogDAO.listPayLogs();
	}

	@Override
	public void updatePayLog(PayLog payLog) {
		payLogDAO.updatePayLog(payLog);
	}

	@Override
	public Pager findPageByParam(PayLog payLog) {
		// TODO Auto-generated method stub
		return null;
	}

	public Pager findPageByCriteria(DetachedCriteria detachedCriteria,int pageSize,int pageNumber){
		return payLogDAO.findPageByCriteria(detachedCriteria, pageSize, pageNumber);
	}
}
