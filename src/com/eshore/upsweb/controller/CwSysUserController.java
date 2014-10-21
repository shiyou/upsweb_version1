package com.eshore.upsweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eshore.upsweb.model.CwSysUser;
import com.eshore.upsweb.model.LoginInfo;
import com.eshore.upsweb.service.CwSysUserService;
import com.eshore.upsweb.util.EncryptUtil;

@Controller
@RequestMapping(value="/user")
public class CwSysUserController {
	@Autowired
	private CwSysUserService cwSysUserService;
	
	@RequestMapping(value="/login") 
	public String login(@RequestParam String userNo,@RequestParam String password,@RequestParam String verifyCode,HttpServletRequest request,Errors error){
		String verifyResult = request.getSession().getAttribute("verifyResult").toString();
		LoginInfo loginInfo = new LoginInfo();
		//1验证码错误
		if(verifyCode==null || !verifyResult.equals(verifyCode)){
			loginInfo.setMsg("验证码错误！");
			request.setAttribute("loginInfo", loginInfo);
			   error.rejectValue("msg","鸭掌门错误");
//			return "/front/login";
			return null;
		}
		
		CwSysUser user = new CwSysUser();
		user.setUserNo(userNo);
		List<CwSysUser> userList = cwSysUserService.findUsers(user);
		//2用户不存在
		if(userList == null || userList.size()==0 ){
			return "fail";
		}
		//3密码不正确
		if(!userList.get(0).getPassword().equals(EncryptUtil.getMD5(password))){
			return "fail";
		}
		
		//4查找用户角色
		
		
		//5记录登录信息
		
		//
		return "/success";
	}

}
