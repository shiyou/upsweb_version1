package com.eshore.upsweb.dao;

import java.util.List;

import com.eshore.upsweb.model.User;

public interface UserDAO {
	 public User getUser(Long id);
	 public List<User> listAllUser();
	 public void addUser(User user);
	 public void delUser(Long id);
	 public void updateUser(User user);
	 
}
