package com.eshore.upsweb.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eshore.upsweb.dao.CwSysUserDAO;
import com.eshore.upsweb.model.CwSysUser;

@Repository
public class CwSysUserDAOImpl implements CwSysUserDAO {
	@Autowired
	private SessionFactory sessionFactory;  
	  
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<CwSysUser> findUsers(CwSysUser user) {
		return getCurrentSession().createQuery("from CwSysUser where userNo = '"+user.getUserNo()+"'").list();
	}

}
