package com.eshore.upsweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="PAY_ACCT_TYPE")
public class PayAcctType{

	@Id
	@GeneratedValue
	@Column(name="PAY_ACCT_TYPE")
	private Long payAcctType; // PAY_ACCT_TYPE
	@Column(name="acctTypeName")
	private String acctTypeName; // ACCT_TYPE_NAME


	// Getters/Setters
	public Long getPayAcctType() {
		return payAcctType;
	}
	public void setPayAcctType(Long payAcctType) {
		this.payAcctType = payAcctType;
	}

	public String getAcctTypeName() {
		return acctTypeName;
	}
	public void setAcctTypeName(String acctTypeName) {
		this.acctTypeName = acctTypeName;
	}
}