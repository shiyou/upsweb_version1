package com.eshore.upsweb.service;

import java.util.List;

import com.eshore.upsweb.model.User;

public interface UserService {
	 public User getUser(Long id);
	 public List<User> listAllUser();
	 public void addUser(User user);
	 public void delUser(Long id);
	 public void updateUser(User user);
	 
}
