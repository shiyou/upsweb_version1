package com.eshore.upsweb.common;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.page.Pager;
import com.eshore.upsweb.model.PayLog;

/**
 * 抽象业务类
 * @author hjd
 *
 */

public abstract class AbstractManager {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public  Session getCurrentSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	public void save(final Object entity){
		getCurrentSession().save(entity);
	}
	
	public void update(final Object entity){
		getCurrentSession().update(entity);
	}
	
	public void delete(final Object entity){
		getCurrentSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	public Object load(final Class  entity,final Serializable id){
		return getCurrentSession().load(entity, id);
	}
	
	public Object get(final Class entity,final Serializable id){
		return getCurrentSession().get(entity, id);
	}
	
	/**
	* 根据对象名，使用HQL查询所有对象
	**/
	public List findAll(final Class entity){
		return getCurrentSession().createQuery("from "+entity.getName()).list();
	}

	/**
	* 使用HQL查询
	**/
	public List findByHQL(final String query){
		return getCurrentSession().createQuery(query).list();
	}
	
	public List findBySQL(final String query){
		return getCurrentSession().createSQLQuery(query).list();
	}
	
	/**
	 * 使用hql分页
	 */
	public Pager findPageByQuery(String hql,Map map,int pageSize,int pageNumber){
		Query query = getCurrentSession().createQuery(hql);
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			Object key = it.next();
			query.setParameter(key.toString(), map.get(key));
		}
		query.setFirstResult(pageSize*(pageNumber-1));
		query.setMaxResults(pageSize);
		List<Object> items= query.list();
		int totalCount = 0;
		Pager pager = new Pager(pageSize, totalCount, items, pageNumber);
		return pager;
	}
	/**
	 * 查询记录数，不过和查询数据有重叠之嫌
	 * @param hql
	 * @param map
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public int getCountByQuery(final String hql,final Map map,final int pageSize,final int pageNumber){
		Query query = getCurrentSession().createQuery(hql);
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			Object key = it.next();
			query.setParameter(key.toString(), map.get(key));
		}
		query.setFirstResult(pageSize*(pageNumber-1));
		query.setMaxResults(pageSize);
		return ((Long) query.uniqueResult()).intValue();
	}
	
	public int getCountByCriteria(final int pageSize,final int pageNumber,final String entityName,final Map map){
		return 0;
	}
	


	/**
	 * 带map参数返回
	 * @param pageSize
	 * @param pageNumber
	 * @param map
	 * @param entity
	 * @return
	 */
	public Pager findPageByCriteria(final int pageSize,final int pageNumber,Map map,Object entity){
		Criteria criteria = getCurrentSession().createCriteria(entity.getClass());
		if(map!=null){
			Set<String> keys = map.keySet();
			for(String key:keys){
				criteria.add(Restrictions.like(key, map.get(key)));
			}
		}
		int totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);
		criteria.setFirstResult(pageSize*(pageNumber-1));
		criteria.setMaxResults(pageSize);
		List items = criteria.list();
		Pager pager = new Pager(pageSize, totalCount, items, pageNumber);
		return pager;
	}
	
	/**
	 * 使用detachedCriteria 返回List
	 * @param detachedCriteria
	 * @return
	 */
	public List findAllByCriteria(final DetachedCriteria detachedCriteria){
		Criteria criteria = detachedCriteria.getExecutableCriteria(getCurrentSession());
		return criteria.list();
	}
	
	/**
	 * 使用detachedCriteria返回分页Pager
	 * @param detachedCriteria 在web层设置
	 * @param pageSize
	 * @param firstIndex
	 * @return
	 */
	public Pager findPageByCriteria(final DetachedCriteria detachedCriteria,final int pageSize,final int pageNumber){
		 Criteria criteria = detachedCriteria.getExecutableCriteria(getCurrentSession());
		 int totalCount = ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		 criteria.setProjection(null);
		 List items = criteria.setFirstResult(pageSize*(pageNumber-1)).setMaxResults(pageSize).list();
		 Pager pager = new Pager(pageSize, totalCount, items, pageNumber);
		return pager;
	}
	
	public Pager findPageByCriteria(final DetachedCriteria detachedCriteria){
		return findPageByCriteria(detachedCriteria,Pager.PAGESIZE,0);
	}
	
	public Pager findPageByCriteria(final DetachedCriteria detachedCriteria, final int pageNumber){
		return findPageByCriteria(detachedCriteria, Pager.PAGESIZE, pageNumber);
	}
	
	public int getCountByCriteria(final DetachedCriteria detachedCriteria){
		Criteria  criteria = detachedCriteria.getExecutableCriteria(getCurrentSession());
		return  ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();  
	}
	

	
	
}
