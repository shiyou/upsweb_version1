package com.eshore.upsweb.dao.impl;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.eshore.upsweb.dao.UserDAO;
import com.eshore.upsweb.model.User;

@Repository
public class UserDAOImpl  implements UserDAO{
	@Autowired
	private SessionFactory sessionFactory;  
	  
    public void setSessionFactory(SessionFactory sessionFactory) {  
        this.sessionFactory = sessionFactory;  
    }  
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addUser(User user) {
		getCurrentSession().save(user);
	}

	@Override
	public void delUser(Long id) {
		sessionFactory.getCurrentSession().delete(getUser(id));
	}

	@Override
	public User getUser(Long id) {
		return 	(User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listAllUser() {
		return 	sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	@Override
	public void updateUser(User user) {
	}

}
