package com.eshore.upsweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eshore.upsweb.dao.UserDAO;
import com.eshore.upsweb.model.User;
import com.eshore.upsweb.service.UserService;
@Service
@Transactional(readOnly=false)
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDao;

	@Override
	public void addUser(User user) {
		userDao.addUser(user);
	}

	@Override
	public void delUser(Long id) {
		userDao.delUser(id);
	}

	@Override
	public User getUser(Long id) {
		return userDao.getUser(id);
	}

	@Override
	public List<User> listAllUser() {
		return userDao.listAllUser();
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

}
