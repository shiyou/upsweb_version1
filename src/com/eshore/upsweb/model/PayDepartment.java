package com.eshore.upsweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PAY_DEPARTMENT")
public class PayDepartment {
	// Fields
	@Id
	@GeneratedValue
	@Column(name="DEPARTMENT_ID")
	private Long departmentId; // DEPARTMENT_ID
	@Column(name="DEP_TYPE_ID")
	private Long depTypeId; // DEP_TYPE_ID
	@Column(name="DEP_NAME")
	private String depName; // DEP_NAME
	@Column(name="DEP_CODE")
	private String depCode; // DEP_CODE
	@Column(name="KEY_VER")
	private Long keyVer; // KEY_VER
	@Column(name="DEP_KEY")
	private String depKey; // DEP_KEY


	// Getters/Setters
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getDepTypeId() {
		return depTypeId;
	}
	public void setDepTypeId(Long depTypeId) {
		this.depTypeId = depTypeId;
	}

	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepCode() {
		return depCode;
	}
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public Long getKeyVer() {
		return keyVer;
	}
	public void setKeyVer(Long keyVer) {
		this.keyVer = keyVer;
	}

	public String getDepKey() {
		return depKey;
	}
	public void setDepKey(String depKey) {
		this.depKey = depKey;
	}
}
