package com.dpu.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class CompanyResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "company_id")
	private Integer companyId;
	
	@JsonProperty(value = "name")
	private String name;
	
	@JsonProperty(value = "address")
	private String address;
	
	@JsonProperty(value = "unit_no")
	private String unitNo;
	
	@JsonProperty(value = "city")
	private String city;
	
	@JsonProperty(value = "province_state")
	private String provinceState;
	
	@JsonProperty(value = "zip")
	private String zip;
	
	@JsonProperty(value = "email")
	private String email;
	
	@JsonProperty(value = "website")
	private String website;
	
	@JsonProperty(value = "contact")
	private String contact;
	
	@JsonProperty(value = "position")
	private String position;
	
	@JsonProperty(value = "phone")
	private String phone;
	
	@JsonProperty(value = "ext")
	private String ext;
	
	@JsonProperty(value = "fax")
	private String fax;
	
	@JsonProperty(value = "company_prefix")
	private String companyPrefix;
	
	@JsonProperty(value = "tollfree")
	private String tollfree;
	
	@JsonProperty(value = "cellular")
	private String cellular;
	
	@JsonProperty(value = "pager")
	private String pager;
	
	@JsonProperty(value = "customer_notes")
	private String customerNotes;
	
	@JsonProperty(value = "after_hours")
	private String afterHours;

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvinceState() {
		return provinceState;
	}

	public void setProvinceState(String provinceState) {
		this.provinceState = provinceState;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCompanyPrefix() {
		return companyPrefix;
	}

	public void setCompanyPrefix(String companyPrefix) {
		this.companyPrefix = companyPrefix;
	}

	public String getTollfree() {
		return tollfree;
	}

	public void setTollfree(String tollfree) {
		this.tollfree = tollfree;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getCustomerNotes() {
		return customerNotes;
	}

	public void setCustomerNotes(String customerNotes) {
		this.customerNotes = customerNotes;
	}

	public String getAfterHours() {
		return afterHours;
	}

	public void setAfterHours(String afterHours) {
		this.afterHours = afterHours;
	}
	
	

}
