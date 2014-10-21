package com.eshore.upsweb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PAY_LOG")
public class PayLog {
	// Fields
	@Id
	@GeneratedValue
	@Column(name="PAY_LOG_ID")
	private Long payLogId; // PAY_LOG_ID
	@Column(name="PAY_ID")
	private String payId; // PAY_ID
	@Column(name="PAY_DATE")
	private Date payDate; // PAY_DATE
	@Column(name="REFUND_ID")
	private String refundId; // REFUND_ID
	@Column(name="REFUND_DATE")
	private Date refundDate; // REFUND_DATE
//	@Column(name="SPID")
//	private Long spid; // SPID
	@Column(name="SP_ORDER_NO")
	private String spOrderNo; // SP_ORDER_NO
	/*@Column(name="SERVICE_ID")
	private Long serviceId; // SERVICE_ID
*/	@Column(name="UPS_ORDER_ID")
	private Long upsOrderId; // UPS_ORDER_ID
/*	@Column(name="PAY_ACCT_TYPE")
	private Long payAcctType; // PAY_ACCT_TYPE
*/	@Column(name="PAY_ACCT_NO")
	private String payAcctNo; // PAY_ACCT_NO
	/*@Column(name="CONNECT_ID")
	private Long connectId; // CONNECT_ID
*/	@Column(name="CONNECT_NO")
	private String connectNo; // CONNECT_NO
	/*@Column(name="DEPARTMENT_ID")
	private Long departmentId; // DEPARTMENT_ID
*/	/*@Column(name="PAY_METHOD_ID")
	private Long payMethodId; // PAY_METHOD_ID
*/	@Column(name="ORDER_ID")
	private Long orderId; // ORDER_ID
	@Column(name="PAY_MODE_ID")
	private Long payModeId; // PAY_MODE_ID
	@Column(name="PAY_CHARGE")
	private Long payCharge; // PAY_CHARGE
	@Column(name="STATE")
	private String state; // STATE
	@Column(name="STATE_DATE")
	private Date stateDate; // STATE_DATE
	@Column(name="PAY_CHECK_STATE")
	private String payCheckState; // PAY_CHECK_STATE
	@Column(name="PAY_CHECK_STATE_DATE")
	private Date payCheckStateDate; // PAY_CHECK_STATE_DATE
	@Column(name="DESC_INFO")
	private String descInfo; // DESC_INFO
	@Column(name="DEPT_PAY_ID")
	private String deptPayId; // DEPT_PAY_ID
	@Column(name="REMAIN_CHARGE")
	private Long remainCharge; // REMAIN_CHARGE
	@Column(name="SETTLE_DATE")
	private String settleDate; // SETTLE_DATE
	@Column(name="TRANS_DATE")
	private String transDate; // TRANS_DATE
	@Column(name="NOTIFY_SP")
	private String notifySp; // NOTIFY_SP
	@Column(name="PAY_TYPE")
	private String payType; // PAY_TYPE
	@Column(name="SP_REFUND_ID")
	private String spRefundId; // SP_REFUND_ID
	@Column(name="CYCLE_ID")
	private Long cycleId; // CYCLE_ID
	@Column(name="REFUND_SETTLE_DATE")
	private String refundSettleDate; // REFUND_SETTLE_DATE
	private String telenum;
	private String partCode;
	
	@ManyToOne(targetEntity=SP.class)
	@JoinColumn(name="SPID")
	private SP sp;
	@ManyToOne(targetEntity=ConnectMethod.class)
	@JoinColumn(name="CONNECT_ID")
	private ConnectMethod connectMethod;
	@ManyToOne(targetEntity=PayAcctType.class)
	@JoinColumn(name="PAY_ACCT_TYPE")
	private PayAcctType payAcctType;
	@ManyToOne(targetEntity=PayMethod.class)
	@JoinColumn(name="PAY_METHOD_ID")
	private PayMethod payMethod;
	@ManyToOne(targetEntity=Service.class)
	@JoinColumn(name="SERVICE_ID")
	private Service service;
	@ManyToOne(targetEntity=PayDepartment.class)
	@JoinColumn(name="DEPARTMENT_ID")
	private PayDepartment payDepartment;
	
	
	// Getters/Setters
	
	public Long getPayLogId() {
		return payLogId;
	}
	
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public void setPayLogId(Long payLogId) {
		this.payLogId = payLogId;
	}

	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}

	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}


	public String getSpOrderNo() {
		return spOrderNo;
	}
	public void setSpOrderNo(String spOrderNo) {
		this.spOrderNo = spOrderNo;
	}


	public Long getUpsOrderId() {
		return upsOrderId;
	}
	public void setUpsOrderId(Long upsOrderId) {
		this.upsOrderId = upsOrderId;
	}


	public String getPayAcctNo() {
		return payAcctNo;
	}
	public void setPayAcctNo(String payAcctNo) {
		this.payAcctNo = payAcctNo;
	}


	public String getConnectNo() {
		return connectNo;
	}
	public void setConnectNo(String connectNo) {
		this.connectNo = connectNo;
	}

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getPayModeId() {
		return payModeId;
	}
	public void setPayModeId(Long payModeId) {
		this.payModeId = payModeId;
	}

	public Long getPayCharge() {
		return payCharge;
	}
	public void setPayCharge(Long payCharge) {
		this.payCharge = payCharge;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public Date getStateDate() {
		return stateDate;
	}
	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	public String getPayCheckState() {
		return payCheckState;
	}
	public void setPayCheckState(String payCheckState) {
		this.payCheckState = payCheckState;
	}

	public Date getPayCheckStateDate() {
		return payCheckStateDate;
	}
	public void setPayCheckStateDate(Date payCheckStateDate) {
		this.payCheckStateDate = payCheckStateDate;
	}

	public String getDescInfo() {
		return descInfo;
	}
	public void setDescInfo(String descInfo) {
		this.descInfo = descInfo;
	}

	public String getDeptPayId() {
		return deptPayId;
	}
	public void setDeptPayId(String deptPayId) {
		this.deptPayId = deptPayId;
	}

	public Long getRemainCharge() {
		return remainCharge;
	}
	public void setRemainCharge(Long remainCharge) {
		this.remainCharge = remainCharge;
	}

	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getNotifySp() {
		return notifySp;
	}
	public void setNotifySp(String notifySp) {
		this.notifySp = notifySp;
	}

	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSpRefundId() {
		return spRefundId;
	}
	public void setSpRefundId(String spRefundId) {
		this.spRefundId = spRefundId;
	}

	public Long getCycleId() {
		return cycleId;
	}
	public void setCycleId(Long cycleId) {
		this.cycleId = cycleId;
	}

	public String getRefundSettleDate() {
		return refundSettleDate;
	}
	public void setRefundSettleDate(String refundSettleDate) {
		this.refundSettleDate = refundSettleDate;
	}

	public void setTelenum(String telenum) {
		this.telenum = telenum;
	}

	public String getTelenum() {
		return telenum;
	}


	public SP getSp() {
		return sp;
	}

	public void setSp(SP sp) {
		this.sp = sp;
	}
	

	public ConnectMethod getConnectMethod() {
		return connectMethod;
	}

	public void setConnectMethod(ConnectMethod connectMethod) {
		this.connectMethod = connectMethod;
	}


	public PayAcctType getPayAcctType() {
		return payAcctType;
	}

	public void setPayAcctType(PayAcctType payAcctType) {
		this.payAcctType = payAcctType;
	}

	
	public PayMethod getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(PayMethod payMethod) {
		this.payMethod = payMethod;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}


	public PayDepartment getPayDepartment() {
		return payDepartment;
	}

	public void setPayDepartment(PayDepartment payDepartment) {
		this.payDepartment = payDepartment;
	}


}
