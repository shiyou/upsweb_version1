package com.eshore.upsweb.model;

public class LoginInfo {
	private String msg = "";
	private boolean ok = false;
	private CwSysUser user;
	//private CwSysLoginLog loginLog;
//	private List<CwSysRole> roles = new ArrayList<CwSysRole>();
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public CwSysUser getUser() {
		return user;
	}
	public void setUser(CwSysUser user) {
		this.user = user;
	}
	/*public CwSysLoginLog getLoginLog() {
		return loginLog;
	}
	public void setLoginLog(CwSysLoginLog loginLog) {
		this.loginLog = loginLog;
	}
	public List<CwSysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<CwSysRole> roles) {
		this.roles = roles;
	}*/
}
