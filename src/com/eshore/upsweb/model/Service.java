package com.eshore.upsweb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Service {
	// Fields
	@Id
	@GeneratedValue
	@Column(name="SERVICE_ID")
	private Long serviceId; // SERVICE_ID
	@Column(name="BUSI_TYPE_ID")
	private String busiTypeId; // BUSI_TYPE_ID
	@Column(name="PAY_METHOD_ID")
	private Long payMethodId; // PAY_METHOD_ID
	@Column(name="CYCLE_ID")
	private Long cycleId; // CYCLE_ID
	@Column(name="SPID")
	private Long spid; // SPID
	@Column(name="SERVICE_CODE")
	private String serviceCode; // SERVICE_CODE
	@Column(name="SERVICE_NAME")
	private String serviceName; // SERVICE_NAME
	@Column(name="SERVICE_TYPE")
	private Long serviceType; // SERVICE_TYPE
	@Column(name="SERVICE_DESC")
	private String serviceDesc; // SERVICE_DESC
	@Column(name="EFF_DATE")
	private Date effDate; // EFF_DATE
	@Column(name="EXP_DATE")
	private Date expDate; // EXP_DATE
	@Column(name="URL")
	private String url; // URL
	@Column(name="CANCEL_URL")
	private String cancelUrl; // CANCEL_URL
	@Column(name="SERVICE_AREA")
	private String serviceArea; // SERVICE_AREA
	@Column(name="STATE")
	private String state; // STATE
	@Column(name="ROLLABLE")
	private String rollable; // ROLLABLE
	@Column(name="REDOABLE")
	private String redoable; // REDOABLE
	@Column(name="DZ_TYPE")
	private String dzType; // DZ_TYPE
	@Column(name="CUR_SELL_COUNT")
	private Long curSellCount; // CUR_SELL_COUNT
	@Column(name="TOTLE_SELL_COUNT")
	private Long totleSellCount; // TOTLE_SELL_COUNT
	@Column(name="DZ_CYCLE")
	private Long dzCycle; // DZ_CYCLE
	@Column(name="DZ_START_TIME")
	private Date dzStartTime; // DZ_START_TIME
	@Column(name="LAST_CHECK_ID")
	private Long lastCheckId; // LAST_CHECK_ID
	@Column(name="authPwd")
	private String authPwd; // AUTH_PWD


	// Getters/Setters
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getBusiTypeId() {
		return busiTypeId;
	}
	public void setBusiTypeId(String busiTypeId) {
		this.busiTypeId = busiTypeId;
	}

	public Long getPayMethodId() {
		return payMethodId;
	}
	public void setPayMethodId(Long payMethodId) {
		this.payMethodId = payMethodId;
	}

	public Long getCycleId() {
		return cycleId;
	}
	public void setCycleId(Long cycleId) {
		this.cycleId = cycleId;
	}

	public Long getSpid() {
		return spid;
	}
	public void setSpid(Long spid) {
		this.spid = spid;
	}

	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Long getServiceType() {
		return serviceType;
	}
	public void setServiceType(Long serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public Date getEffDate() {
		return effDate;
	}
	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	public String getServiceArea() {
		return serviceArea;
	}
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getRollable() {
		return rollable;
	}
	public void setRollable(String rollable) {
		this.rollable = rollable;
	}

	public String getRedoable() {
		return redoable;
	}
	public void setRedoable(String redoable) {
		this.redoable = redoable;
	}

	public String getDzType() {
		return dzType;
	}
	public void setDzType(String dzType) {
		this.dzType = dzType;
	}

	public Long getCurSellCount() {
		return curSellCount;
	}
	public void setCurSellCount(Long curSellCount) {
		this.curSellCount = curSellCount;
	}

	public Long getTotleSellCount() {
		return totleSellCount;
	}
	public void setTotleSellCount(Long totleSellCount) {
		this.totleSellCount = totleSellCount;
	}

	public Long getDzCycle() {
		return dzCycle;
	}
	public void setDzCycle(Long dzCycle) {
		this.dzCycle = dzCycle;
	}

	public Date getDzStartTime() {
		return dzStartTime;
	}
	public void setDzStartTime(Date dzStartTime) {
		this.dzStartTime = dzStartTime;
	}

	public Long getLastCheckId() {
		return lastCheckId;
	}
	public void setLastCheckId(Long lastCheckId) {
		this.lastCheckId = lastCheckId;
	}

	public String getAuthPwd() {
		return authPwd;
	}
	public void setAuthPwd(String authPwd) {
		this.authPwd = authPwd;
	}
}
