package com.eshore.upsweb.service;

import java.util.List;

import com.eshore.upsweb.model.CwSysUser;

public interface CwSysUserService {
	List<CwSysUser> findUsers(CwSysUser user);

}
