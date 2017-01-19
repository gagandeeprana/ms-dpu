package com.dpu.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class AdditionalContacts {

	////@JsonProperty("add_contact_id")
	private int additionalContactId;

	////@JsonProperty("customer_name")
	private String customerName;

	//@JsonProperty("position")
	private String position;

	//@JsonProperty("phone")
	private String phone;

	//@JsonProperty("ext")
	private String ext;

	//@JsonProperty("fax")
	private String fax;

	//@JsonProperty("additional_contact_prefix")
	private String prefix;

	//@JsonProperty("cellular")
	private String cellular;

	//@JsonProperty("status")
	private int status;

	//@JsonProperty("email")
	private String email;

	public int getAdditionalContactId() {
		return additionalContactId;
	}

	public void setAdditionalContactId(int additionalContactId) {
		this.additionalContactId = additionalContactId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
