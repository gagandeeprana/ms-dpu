package com.dpu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "newtruckmaster")
public class Truck implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "truck_id")
	@GeneratedValue
	private Long truckId;

	@ManyToOne
	@JoinColumn(name = "division_id")
	private Division division;

	@ManyToOne
	@JoinColumn(name = "terminal_id")
	private Terminal terminal;

	@Column(name = "unit_no")
	private Integer unitNo;

	@Column(name = "truck_usage")
	private String usage;

	@Column(name = "owner")
	private String owner;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "truck_type")
	private String truckType;

	@Column(name = "finance")
	private String finance;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_on")
	private Date modifiedOn;

	@Column(name = "oo_name")
	private String oOName;

	public String getoOName() {
		return oOName;
	}

	public void setoOName(String oOName) {
		this.oOName = oOName;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	public String getFinance() {
		return finance;
	}

	public void setFinance(String finance) {
		this.finance = finance;
	}

	public Long getTruckId() {
		return truckId;
	}

	public void setTruckId(Long truckId) {
		this.truckId = truckId;
	}

	public Integer getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(Integer unitNo) {
		this.unitNo = unitNo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	/*
	 * 
	 * public Long getTruckId() { return truckId; }
	 * 
	 * public void setTruckId(Long truckId) { this.truckId = truckId; }
	 * 
	 * public Integer getUnitNo() { return unitNo; }
	 * 
	 * public void setUnitNo(Integer unitNo) { this.unitNo = unitNo; }
	 * 
	 * public String getStatus() { return status; }
	 * 
	 * public void setStatus(String status) { this.status = status; }
	 * 
	 * public String getTruckClass() { return truckClass; }
	 * 
	 * public void setTruckClass(String truckClass) { this.truckClass =
	 * truckClass; }
	 * 
	 * public Integer getOwnerId() { return ownerId; }
	 * 
	 * public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }
	 * 
	 * public String getVin() { return vin; }
	 * 
	 * public void setVin(String vin) { this.vin = vin; }
	 * 
	 * public String getMake() { return make; }
	 * 
	 * public void setMake(String make) { this.make = make; }
	 * 
	 * public String getModel() { return model; }
	 * 
	 * public void setModel(String model) { this.model = model; }
	 * 
	 * public Integer getTruckYear() { return truckYear; }
	 * 
	 * public void setTruckYear(Integer truckYear) { this.truckYear = truckYear;
	 * }
	 * 
	 * public String getPlateNo() { return plateNo; }
	 * 
	 * public void setPlateNo(String plateNo) { this.plateNo = plateNo; }
	 * 
	 * public String getJurisdiction() { return jurisdiction; }
	 * 
	 * public void setJurisdiction(String jurisdiction) { this.jurisdiction =
	 * jurisdiction; }
	 * 
	 * public Integer getTareWeight() { return tareWeight; }
	 * 
	 * public void setTareWeight(Integer tareWeight) { this.tareWeight =
	 * tareWeight; }
	 * 
	 * public String getRgw() { return rgw; }
	 * 
	 * public void setRgw(String rgw) { this.rgw = rgw; }
	 * 
	 * public String getCurrentOdometer() { return currentOdometer; }
	 * 
	 * public void setCurrentOdometer(String currentOdometer) {
	 * this.currentOdometer = currentOdometer; }
	 * 
	 * public String getEquipmentType() { return equipmentType; }
	 * 
	 * public void setEquipmentType(String equipmentType) { this.equipmentType =
	 * equipmentType; }
	 * 
	 * public String getCreatedBy() { return createdBy; }
	 * 
	 * public void setCreatedBy(String createdBy) { this.createdBy = createdBy;
	 * }
	 * 
	 * public Date getCreatedOn() { return createdOn; }
	 * 
	 * public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }
	 * 
	 * public String getModifiedBy() { return modifiedBy; }
	 * 
	 * public void setModifiedBy(String modifiedBy) { this.modifiedBy =
	 * modifiedBy; }
	 * 
	 * public Date getModifiedOn() { return modifiedOn; }
	 * 
	 * public void setModifiedOn(Date modifiedOn) { this.modifiedOn =
	 * modifiedOn; }
	 */

}
