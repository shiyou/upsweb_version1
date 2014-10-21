package com.eshore.upsweb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SP {

	// Fields
	@Id
	@GeneratedValue
	@Column(name="SPID")
	private Long spid; // SPID
	@Column(name="PROVINCE_ID")
	private String provinceId; // PROVINCE_ID
	@Column(name="LATN_ID")
	private String latnId; // LATN_ID
	@Column(name="PART_CLASS")
	private Long partClass; // PART_CLASS
	@Column(name="PART_CODE")
	private String partCode; // PART_CODE
	@Column(name="APP_CLASS")
	private Long appClass; // APP_CLASS
	@Column(name="CN_NAME")
	private String cnName; // CN_NAME
	@Column(name="EN_NAME")
	private String enName; // EN_NAME
	@Column(name="URL")
	private String url; // URL
	@Column(name="SETTLE_METHOD")
	private Long settleMethod; // SETTLE_METHOD
	@Column(name="SP_TYPE")
	private Long spType; // SP_TYPE
	@Column(name="SP_CONNECT_TYPE")
	private Long spConnectType; // SP_CONNECT_TYPE
	@Column(name="SP_CONNECT")
	private Long spConnect; // SP_CONNECT
	@Column(name="SSO_PWD")
	private String ssoPwd; // SSO_PWD
	@Column(name="CALLBACK_USER")
	private String callbackUser; // CALLBACK_USER
	@Column(name="CALLBACK_PWD")
	private String callbackPwd; // CALLBACK_PWD
	@Column(name="EXP_DATE")
	private Date expDate; // EXP_DATE
	@Column(name="LOGIN_USER")
	private String loginUser; // LOGIN_USER
	@Column(name="LOGIN_PWD")
	private String loginPwd; // LOGIN_PWD
	@Column(name="STATE")
	private String state; // STATE
	@Column(name="CREDIT_ID")
	private Long creditId; // CREDIT_ID
	@Column(name="PARTNER_ID")
	private Long partnerId; // PARTNER_ID
	@Column(name="STATE_DATE")
	private Date stateDate; // STATE_DATE
	@Column(name="BUSI_PART_ID")
	private Long busiPartId; // BUSI_PART_ID
	@Column(name="SUPER_PART_CODE")
	private String superPartCode; // SUPER_PART_CODE


	// Getters/Setters
	public Long getSpid() {
		return spid;
	}
	public void setSpid(Long spid) {
		this.spid = spid;
	}

	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getLatnId() {
		return latnId;
	}
	public void setLatnId(String latnId) {
		this.latnId = latnId;
	}

	public Long getPartClass() {
		return partClass;
	}
	public void setPartClass(Long partClass) {
		this.partClass = partClass;
	}

	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public Long getAppClass() {
		return appClass;
	}
	public void setAppClass(Long appClass) {
		this.appClass = appClass;
	}

	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public Long getSettleMethod() {
		return settleMethod;
	}
	public void setSettleMethod(Long settleMethod) {
		this.settleMethod = settleMethod;
	}

	public Long getSpType() {
		return spType;
	}
	public void setSpType(Long spType) {
		this.spType = spType;
	}

	public Long getSpConnectType() {
		return spConnectType;
	}
	public void setSpConnectType(Long spConnectType) {
		this.spConnectType = spConnectType;
	}

	public Long getSpConnect() {
		return spConnect;
	}
	public void setSpConnect(Long spConnect) {
		this.spConnect = spConnect;
	}

	public String getSsoPwd() {
		return ssoPwd;
	}
	public void setSsoPwd(String ssoPwd) {
		this.ssoPwd = ssoPwd;
	}

	public String getCallbackUser() {
		return callbackUser;
	}
	public void setCallbackUser(String callbackUser) {
		this.callbackUser = callbackUser;
	}

	public String getCallbackPwd() {
		return callbackPwd;
	}
	public void setCallbackPwd(String callbackPwd) {
		this.callbackPwd = callbackPwd;
	}

	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public Long getCreditId() {
		return creditId;
	}
	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}

	public Long getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Date getStateDate() {
		return stateDate;
	}
	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	public Long getBusiPartId() {
		return busiPartId;
	}
	public void setBusiPartId(Long busiPartId) {
		this.busiPartId = busiPartId;
	}

	public String getSuperPartCode() {
		return superPartCode;
	}
	public void setSuperPartCode(String superPartCode) {
		this.superPartCode = superPartCode;
	}
}
