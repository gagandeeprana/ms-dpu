package com.dpu.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vendor")
public class Vendor {

	@Id
	@Column(name = "vendor_id")
	@GeneratedValue
	private Long vendorId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "unit_no")
	private String unitNo;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "province_state")
	private String provinceState;
	
	@Column(name = "zip")
	private String zip;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "contact")
	private String contact;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "ext")
	private String ext;
	
	@Column(name = "fax")
	private String fax;
	
	@Column(name = "vendor_prefix")
	private String vendorPrefix;
	
	@Column(name = "tollfree")
	private String tollfree;
	
	@Column(name = "cellular")
	private String cellular;
	
	@Column(name = "pager")
	private String pager;
	
	@Column(name = "vendor_notes")
	private String vendorNotes;
	
	@Column(name = "after_hours")
	private String afterHours;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vendor")
    private Set<VendorBillingLocation> billingLocations = new HashSet<VendorBillingLocation>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vendor")
    private Set<VendorContacts> additionalContacts = new HashSet<VendorContacts>();
	
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

	public String getAfterHours() {
		return afterHours;
	}

	public void setAfterHours(String afterHours) {
		this.afterHours = afterHours;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorPrefix() {
		return vendorPrefix;
	}

	public void setVendorPrefix(String vendorPrefix) {
		this.vendorPrefix = vendorPrefix;
	}

	public String getVendorNotes() {
		return vendorNotes;
	}

	public void setVendorNotes(String vendorNotes) {
		this.vendorNotes = vendorNotes;
	}

	public Set<VendorBillingLocation> getBillingLocations() {
		return billingLocations;
	}

	public void setBillingLocations(Set<VendorBillingLocation> billingLocations) {
		this.billingLocations = billingLocations;
	}

	public Set<VendorContacts> getAdditionalContacts() {
		return additionalContacts;
	}

	public void setAdditionalContacts(Set<VendorContacts> additionalContacts) {
		this.additionalContacts = additionalContacts;
	}
	
}
