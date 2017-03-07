package com.dpu.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrierAdditionalContactModel implements Serializable {

	/**
	 * @author sumit
	 */
	private static final long serialVersionUID = 1L;

	private Long additionalContactId;

	private String incCompany;

	private String policyNumber;

	private String incBroker;

	private String brokerContact;

	private String brokerPhone;

	private String ext;

	private String brokerFax;

	private String email;

	private Date expiryDate;

	private String congCoverage;

	private String libilityCoverage;

	public Long getAdditionalContactId() {
		return additionalContactId;
	}

	public void setAdditionalContactId(Long additionalContactId) {
		this.additionalContactId = additionalContactId;
	}

	public String getIncCompany() {
		return incCompany;
	}

	public void setIncCompany(String incCompany) {
		this.incCompany = incCompany;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getIncBroker() {
		return incBroker;
	}

	public void setIncBroker(String incBroker) {
		this.incBroker = incBroker;
	}

	public String getBrokerContact() {
		return brokerContact;
	}

	public void setBrokerContact(String brokerContact) {
		this.brokerContact = brokerContact;
	}

	public String getBrokerPhone() {
		return brokerPhone;
	}

	public void setBrokerPhone(String brokerPhone) {
		this.brokerPhone = brokerPhone;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getBrokerFax() {
		return brokerFax;
	}

	public void setBrokerFax(String brokerFax) {
		this.brokerFax = brokerFax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCongCoverage() {
		return congCoverage;
	}

	public void setCongCoverage(String congCoverage) {
		this.congCoverage = congCoverage;
	}

	public String getLibilityCoverage() {
		return libilityCoverage;
	}

	public void setLibilityCoverage(String libilityCoverage) {
		this.libilityCoverage = libilityCoverage;
	}

}
