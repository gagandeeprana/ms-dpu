package com.dpu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Company;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.entity.Status;

@JsonSerialize(include = Inclusion.NON_NULL)
public class OrderModel implements Serializable{

	 
	private static final long serialVersionUID = 1L;

	private Long driverId;

	private String driverCode;

	private String firstName;

	private String lastName;

	private String address;

	private String unit;
	 
	private String city;
 
	private String pvs;
	 
	private String postalCode;

	private String email;

	private String home;
	 
	private String faxNo;

	private String cellular;
	 
	private String poNo;
	 
	private String companyName;
	private Long companyId;
	private List<Company> companyList;

	private String billingLocationName;
	private Long billingLocationId;
	private List<CompanyBillingLocation> billingLocationList;

	private String contactName;
	private Long contactId;
	private List<CompanyAdditionalContacts> contactsList;
	 
	private String roleName;
	private Long roleId;
	private List<TypeResponse> roleList;
	 
	private String statusName;
	private Long statusId;
	private List<Status> statusList;
	 
	private String driverClassName;
	private Long driverClassId;
	private List<TypeResponse> driverClassList;
 
	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}

	public List<TypeResponse> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<TypeResponse> roleList) {
		this.roleList = roleList;
	}

	public List<TypeResponse> getDriverClassList() {
		return driverClassList;
	}

	public void setDriverClassList(List<TypeResponse> driverClassList) {
		this.driverClassList = driverClassList;
	}

	private String createdBy;
	 
	private Date createdOn;
	
	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPvs() {
		return pvs;
	}

	public void setPvs(String pvs) {
		this.pvs = pvs;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public Long getDriverClassId() {
		return driverClassId;
	}

	public void setDriverClassId(Long driverClassId) {
		this.driverClassId = driverClassId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	public String getBillingLocationName() {
		return billingLocationName;
	}

	public void setBillingLocationName(String billingLocationName) {
		this.billingLocationName = billingLocationName;
	}

	public Long getBillingLocationId() {
		return billingLocationId;
	}

	public void setBillingLocationId(Long billingLocationId) {
		this.billingLocationId = billingLocationId;
	}

	public List<CompanyBillingLocation> getBillingLocationList() {
		return billingLocationList;
	}

	public void setBillingLocationList(
			List<CompanyBillingLocation> billingLocationList) {
		this.billingLocationList = billingLocationList;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public List<CompanyAdditionalContacts> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<CompanyAdditionalContacts> contactsList) {
		this.contactsList = contactsList;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	
	
}
