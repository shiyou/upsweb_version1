package com.eshore.upsweb.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.common.page.Pager;
import com.eshore.upsweb.dao.PayLogDAO;
import com.eshore.upsweb.model.PayLog;

@Repository
public class PayLogDAOImpl implements PayLogDAO{
	@Autowired
	private SessionFactory sessionFactory;  
	  
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	@Override
	public void addPayLog(PayLog payLog) {
		getCurrentSession().save(payLog);
		
	}

	@Override
	public void delPayLogById(String id) {
		PayLog payLog = (PayLog) getCurrentSession().get(PayLog.class, id);
		if(payLog!=null)
			getCurrentSession().delete(payLog);
	}

	@Override
	public PayLog findPayLogById(String id) {
		return (PayLog) getCurrentSession().get(PayLog.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayLog> listPayLogs() {
//		return getCurrentSession().createCriteria("from PayLog").list();
		List<PayLog> list = getCurrentSession().createQuery("from PayLog").list();
		return list;
	}

	@Override
	public void updatePayLog(PayLog payLog) {
		getCurrentSession().update(payLog);
	}
	@Override
	public Pager findPage() {
		return null;
	}
	@Override
	public Pager findPage(int pageNumber) {
		return null;
	}
	@Override
	public Pager findPage(int pageSize, int pageNumber) {
		String hql="from PayLog p order by p.payDate desc";
		Query query = getCurrentSession().createQuery(hql);
		query.setFirstResult((pageNumber-1)*pageSize);  // 从第一条记录开始
		query.setMaxResults(pageSize);
		int totalCount = getCount();
		Pager pager = new Pager(pageSize, totalCount, query.list(), pageNumber);
		return pager ;
	}
	
	/**
	 * 查询总数量
	 */
	public int getCount(){
//		return getCurrentSession().createQuery("from PayLog").list().size();
//		return (Integer) getCurrentSession().createCriteria("PayLog").setProjection(Projections.rowCount()).uniqueResult();
		return ((Long)getCurrentSession().createQuery("select count(*) from PayLog").uniqueResult()).intValue();
	}
	@Override
	public Pager findPageByParam(PayLog payLog,int pageSize,int pageNumber) {
		String hql = "from PayLog p where ";
		return null;
	}
	
	////////////////////////torrow
	public Pager findPageByCriteria(DetachedCriteria detachedCriteria,int pageSize,int pageNumber){
		int firstIndex = (pageNumber-1)*pageSize;
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(getCurrentSession());  
		int totalCount = (int) ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();  
        criteria.setProjection(null);  
        List items = criteria.setFirstResult(firstIndex).setMaxResults(pageSize).list();  
        Pager pager = new Pager(pageSize, totalCount, items, pageNumber);
		return pager ;
	}
	
	public Pager findPageTest(int pageSize,int pageNumber,String dataTime,String id){
		String hql ="from PayLog where 1=1 ";
		StringBuffer buff = new StringBuffer();
		buff.append(hql);
		if(id !=null ){
			buff.append("and id=?");
		}
		if(dataTime !=null){
			buff.append(dataTime);
		}
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter(1, id);
		query.setParameter(2, dataTime);
		query.setFirstResult((pageNumber-1)*pageSize);
		query.setMaxResults(pageSize);
		int totalCount = getCount();
		Pager pager = new Pager(pageSize, totalCount, query.list(), pageNumber);
		return pager;
		
	}
	
}
