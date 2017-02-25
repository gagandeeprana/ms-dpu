package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Status;

@JsonSerialize(include = Inclusion.NON_NULL)
public class CustomBrokerResponse {
	
	private Long customBrokerId;
	private String customBrokerName;
	private String contactName;
	private String phone;
	private String extention;
	private String faxNumber;
	private String status;
	private Long statusId;
	private List<Status> statusList;
	private String email;
	private String website;
	
	
	public Long getCustomBrokerId() {
		return customBrokerId;
	}
	public void setCustomBrokerId(Long customBrokerId) {
		this.customBrokerId = customBrokerId;
	}
	
	
	public String getCustomBrokerName() {
		return customBrokerName;
	}
	public void setCustomBrokerName(String customBrokerName) {
		this.customBrokerName = customBrokerName;
	}
	
	
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public String getExtention() {
		return extention;
	}
	public void setExtention(String extention) {
		this.extention = extention;
	}
	
	
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	
	
	public List<Status> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
}
