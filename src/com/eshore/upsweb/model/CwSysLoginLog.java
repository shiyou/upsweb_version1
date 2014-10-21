package com.eshore.upsweb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CW_SYS_LOGIN_LOG")
public class CwSysLoginLog {
	// Fields
	@Id
	@GeneratedValue
	@Column(name="LOGIN_LOG_ID")
	private Long loginLogId; // LOGIN_LOG_ID
	@Column(name="USER_ID")
	private Long userId; // USER_ID
	@Column(name="PHYSICAL_ADDR")
	private String physicalAddr; // PHYSICAL_ADDR
	@Column(name="IP_ADDR")
	private String ipAddr; // IP_ADDR
	@Column(name="BROWSER_INFO")
	private String browserInfo; // BROWSER_INFO
	@Column(name="OS_INFO")
	private String osInfo; // OS_INFO
	@Column(name="LOGIN_TIME")
	private Date loginTime; // LOGIN_TIME
	@Column(name="LOGOUT_TIME")
	private Date logoutTime; // LOGOUT_TIME
	@Column(name="HOST_NAME")
	private String hostName; // HOST_NAME


	// Getters/Setters
	public Long getLoginLogId() {
		return loginLogId;
	}
	public void setLoginLogId(Long loginLogId) {
		this.loginLogId = loginLogId;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPhysicalAddr() {
		return physicalAddr;
	}
	public void setPhysicalAddr(String physicalAddr) {
		this.physicalAddr = physicalAddr;
	}

	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getBrowserInfo() {
		return browserInfo;
	}
	public void setBrowserInfo(String browserInfo) {
		this.browserInfo = browserInfo;
	}

	public String getOsInfo() {
		return osInfo;
	}
	public void setOsInfo(String osInfo) {
		this.osInfo = osInfo;
	}

	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
}
