package com.eshore.upsweb.dao;

import java.util.List;

import com.eshore.upsweb.model.CwSysUser;


public interface CwSysUserDAO {

	List<CwSysUser> findUsers(CwSysUser user);
}
