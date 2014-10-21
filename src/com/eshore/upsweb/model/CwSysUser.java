/*
* @(#)CwSysUser.java / 2009-09-18
*
* Copyright (c) 广州亿迅科技有限公司
*
* 说明：由程序 TableToJavaObject 生成的库表 CW_SYS_USER 对应的 Java对象 CwSysUser
*
*/
package com.eshore.upsweb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CW_SYS_USER")
public class CwSysUser{

	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Long userId; // USER_ID
	@Column(name="USER_NAME")
	private String userName; // USER_NAME
	@Column(name="USER_NO")
	private String userNo; // USER_NO
	private String password; // PASSWORD
	private String teleno; // TELENO
	private String address; // ADDRESS
	@Column(name="PASSWORD_TIME")
	private Date passwordTime; // PASSWORD_TIME
	@Column(name="PSWD_VALID_DAYS")
	private Long pswdValidDays; // PSWD_VALID_DAYS
	@Column(name="FORCE_MDF_PWD")
	private String forceMdfPwd; // FORCE_MDF_PWD
	private String state; // STATE
	@Column(name="USER_DESC")
	private String userDesc; // USER_DESC
	@Column(name="CREATE_TIME")
	private Date createTime; // CREATE_TIME
	@Column(name="MODIFY_TIME")
	private Date modifyTime; // MODIFY_TIME
	private String latnId;
	@Column(name="SP_ID")
	private String spId; // SP_ID

	public String getLatnId() {
		return latnId;
	}
	public void setLatnId(String latnId) {
		this.latnId = latnId;
	}
	// Getters/Setters
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getTeleno() {
		return teleno;
	}
	public void setTeleno(String teleno) {
		this.teleno = teleno;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public Date getPasswordTime() {
		return passwordTime;
	}
	public void setPasswordTime(Date passwordTime) {
		this.passwordTime = passwordTime;
	}

	public Long getPswdValidDays() {
		return pswdValidDays;
	}
	public void setPswdValidDays(Long pswdValidDays) {
		this.pswdValidDays = pswdValidDays;
	}

	public String getForceMdfPwd() {
		return forceMdfPwd;
	}
	public void setForceMdfPwd(String forceMdfPwd) {
		this.forceMdfPwd = forceMdfPwd;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}

}