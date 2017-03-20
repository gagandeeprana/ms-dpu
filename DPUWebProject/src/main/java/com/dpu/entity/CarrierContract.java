package com.dpu.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "carriercontract")
public class CarrierContract {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "carrier_contract_id")
	private Long contractNoId;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "contract_rate")
	private Double contractRate;

	@Column(name = "carrier_rate")
	private String carrierRat;

	@Column(name = "hours")
	private String hours;

	@Column(name = "miles")
	private String miles;

	@Column(name = "dispatched")
	private Date dispatched;

	@Column(name = "createdBy")
	private String createdBy;

	@Column(name = "ins_expires")
	private String insExpires;

	@Column(name = "cargo")
	private String cargo;

	@Column(name = "libility")
	private String liabity;

	@Column(name = "trans_doc")
	private String transDoc;

	@Column(name = "mc_no")
	private String mCno;

	@Column(name = "dot_no")
	private String dOTno;

	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "carrier")
	// private List<CarrierAdditionalContact> carrierAdditionalContact;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carriercontract")
	private List<Carrier> carrierlList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carriercontract")
	private List<ArrangedWith> arrangedWithList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carriercontract")
	private List<Driver> driverList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carriercontract")
	private List<Currency> currencyList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carriercontract")
	private List<Category> categoryList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carriercontract")
	private List<Role> roleList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carriercontract")
	private List<Equipment> equipmentList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carriercontract")
	private List<Commodity> commodityList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carriercontract")
	private List<Division> divisionList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carriercontract")
	private List<Dispatcher> dispatcherList;

	public Long getContractNoId() {
		return contractNoId;
	}

	public void setContractNoId(Long contractNoId) {
		this.contractNoId = contractNoId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Double getContractRate() {
		return contractRate;
	}

	public void setContractRate(Double contractRate) {
		this.contractRate = contractRate;
	}

	public String getCarrierRat() {
		return carrierRat;
	}

	public void setCarrierRat(String carrierRat) {
		this.carrierRat = carrierRat;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getMiles() {
		return miles;
	}

	public void setMiles(String miles) {
		this.miles = miles;
	}

	public Date getDispatched() {
		return dispatched;
	}

	public void setDispatched(Date dispatched) {
		this.dispatched = dispatched;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getInsExpires() {
		return insExpires;
	}

	public void setInsExpires(String insExpires) {
		this.insExpires = insExpires;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getLiabity() {
		return liabity;
	}

	public void setLiabity(String liabity) {
		this.liabity = liabity;
	}

	public String getTransDoc() {
		return transDoc;
	}

	public void setTransDoc(String transDoc) {
		this.transDoc = transDoc;
	}

	public String getmCno() {
		return mCno;
	}

	public void setmCno(String mCno) {
		this.mCno = mCno;
	}

	public String getdOTno() {
		return dOTno;
	}

	public void setdOTno(String dOTno) {
		this.dOTno = dOTno;
	}

	public List<Carrier> getCarrierlList() {
		return carrierlList;
	}

	public void setCarrierlList(List<Carrier> carrierlList) {
		this.carrierlList = carrierlList;
	}

	public List<ArrangedWith> getArrangedWithList() {
		return arrangedWithList;
	}

	public void setArrangedWithList(List<ArrangedWith> arrangedWithList) {
		this.arrangedWithList = arrangedWithList;
	}

	public List<Driver> getDriverList() {
		return driverList;
	}

	public void setDriverList(List<Driver> driverList) {
		this.driverList = driverList;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<Currency> currencyList) {
		this.currencyList = currencyList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Equipment> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<Equipment> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public List<Commodity> getCommodityList() {
		return commodityList;
	}

	public void setCommodityList(List<Commodity> commodityList) {
		this.commodityList = commodityList;
	}

	public List<Division> getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List<Division> divisionList) {
		this.divisionList = divisionList;
	}

	public List<Dispatcher> getDispatcherList() {
		return dispatcherList;
	}

	public void setDispatcherList(List<Dispatcher> dispatcherList) {
		this.dispatcherList = dispatcherList;
	}

}
