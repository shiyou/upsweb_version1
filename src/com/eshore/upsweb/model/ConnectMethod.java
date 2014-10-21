package com.eshore.upsweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CONNECT_METHOD")
public class ConnectMethod {
	// Fields
	@Id
	@GeneratedValue
	@Column(name="CONNECT_ID")
	private Long connectId; // CONNECT_ID
	@Column(name="CONNECT_NAME")
	private String connectName; // CONNECT_NAME


	// Getters/Setters
	public Long getConnectId() {
		return connectId;
	}
	public void setConnectId(Long connectId) {
		this.connectId = connectId;
	}

	public String getConnectName() {
		return connectName;
	}
	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}
}
