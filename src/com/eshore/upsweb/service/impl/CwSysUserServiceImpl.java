package com.eshore.upsweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eshore.upsweb.dao.CwSysUserDAO;
import com.eshore.upsweb.model.CwSysUser;
import com.eshore.upsweb.service.CwSysUserService;

@Service
@Transactional
public class CwSysUserServiceImpl implements CwSysUserService {
	
	@Autowired
	CwSysUserDAO cwSysUserDAO;

	@Override
	public List<CwSysUser> findUsers(CwSysUser user) {
		return cwSysUserDAO.findUsers(user);
	}

}
