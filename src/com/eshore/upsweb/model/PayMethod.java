package com.eshore.upsweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PAY_METHOD")
public class PayMethod {
	// Fields
	@Id
	@GeneratedValue
	@Column(name="PAY_METHOD_ID")
	private Long payMethodId; // PAY_METHOD_ID
	@Column(name="DEPARTMENT_ID")
	private Long departmentId; // DEPARTMENT_ID
	@Column(name="PAY_METHOD_NAME")
	private String payMethodName; // PAY_METHOD_NAME


	// Getters/Setters
	public Long getPayMethodId() {
		return payMethodId;
	}
	public void setPayMethodId(Long payMethodId) {
		this.payMethodId = payMethodId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getPayMethodName() {
		return payMethodName;
	}
	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}

}
