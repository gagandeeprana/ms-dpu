package com.dpu.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrierContractModel {

	private Long contractNoId;

	private String contractNo;

	private Double contractRate;

	private String carrierRat;

	private String hours;

	private String miles;

	private Date dispatched;

	private String createdBy;

	private String insExpires;

	private String cargo;

	private String liabity;

	private String transDoc;

	private String mCno;

	private String dOTno;

	private String carrierName;

	private String arrangedWithName;

	private String driverName;

	private String currencyName;

	private String categoryName;

	private String roleName;

	private String equipmentName;

	private String commodityName;

	private String divisionName;

	private String dispatcherName;

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

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getArrangedWithName() {
		return arrangedWithName;
	}

	public void setArrangedWithName(String arrangedWithName) {
		this.arrangedWithName = arrangedWithName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getDispatcherName() {
		return dispatcherName;
	}

	public void setDispatcherName(String dispatcherName) {
		this.dispatcherName = dispatcherName;
	}

}
