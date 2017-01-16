package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "newtrailermaster")
public class Trailer {

	@Id
	@Column(name = "trailer_id")
	@GeneratedValue
	private Integer trailerId;
	
	@Column(name = "unit_no")
	private Integer unitNo;
	
	@Column(name = "trailer_usage")
	private String usage;
	
	@Column(name = "owner")
	private String owner;
	
	@Column(name = "division")
	private String division;
	
	@Column(name = "oo_name")
	private String oOName;
	
	@Column(name = "terminal")
	private String terminal;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "trailer_type")
	private String trailerType;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "finance")
	private String finance;

	public Integer getTrailerId() {
		return trailerId;
	}

	public void setTrailerId(Integer trailerId) {
		this.trailerId = trailerId;
	}

	public Integer getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(Integer unitNo) {
		this.unitNo = unitNo;
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

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getoOName() {
		return oOName;
	}

	public void setoOName(String oOName) {
		this.oOName = oOName;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTrailerType() {
		return trailerType;
	}

	public void setTrailerType(String trailerType) {
		this.trailerType = trailerType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFinance() {
		return finance;
	}

	public void setFinance(String finance) {
		this.finance = finance;
	}
	
	/*
	@Column(name = "class_id")
	//@JsonProperty(value = "class_id")
	private Integer classId;
	
	@Column(name = "equipment_id")
	//@JsonProperty(value = "equipment_id")
	private Integer equipmentId;
	
	@Column(name = "length")
	//@JsonProperty(value = "length")
	private Integer length;
	
	@Column(name = "VIN")
	//@JsonProperty(value = "vin")
	private String VIN;
	
	@Column(name = "make")
	//@JsonProperty(value = "make")
	private String make;
	
	@Column(name = "model")
	//@JsonProperty(value = "model")
	private String model;
	
	@Column(name = "year")
	//@JsonProperty(value = "year")
	private Integer year;
	
	@Column(name = "plate_no")
	//@JsonProperty(value = "plate_no")
	private String plateNo;
	
	@Column(name = "jurisdiction")
	//@JsonProperty(value = "jurisdiction")
	private String jurisdiction;
	
	@Column(name = "tare_weight")
	//@JsonProperty(value = "tare_weight")
	private String tareWeight;
	
	@Column(name = "rgw")
	//@JsonProperty(value = "rgw")
	private String rgw;
	
	@Column(name = "current_odometer")
	//@JsonProperty(value = "current_odometer")
	private String currentOdometer;
	
	@Column(name = "reading_taken_date")
	//@JsonProperty(value = "reading_taken_date")
	private Date readingTakenDate;
	
	@Column(name = "created_by")
	//@JsonProperty(value = "created_by")
	private Integer createdBy;
	
	@Column(name = "created_on")
	//@JsonProperty(value = "created_on")
	private Date createdOn;

	@Column(name = "modified_by")
	@JsonProperty(value = "modified_by")
	private Integer modifiedBy;
	
	@Column(name = "modified_on")
	@JsonProperty(value = "modified_on")
	private Date modifiedOn;
	
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Integer getTrailerId() {
		return trailerId;
	}

	public void setTrailerId(Integer trailerId) {
		this.trailerId = trailerId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(String tareWeight) {
		this.tareWeight = tareWeight;
	}

	public String getRgw() {
		return rgw;
	}

	public void setRgw(String rgw) {
		this.rgw = rgw;
	}

	public String getCurrentOdometer() {
		return currentOdometer;
	}

	public void setCurrentOdometer(String currentOdometer) {
		this.currentOdometer = currentOdometer;
	}

	public Date getReadingTakenDate() {
		return readingTakenDate;
	}

	public void setReadingTakenDate(Date readingTakenDate) {
		this.readingTakenDate = readingTakenDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}*/


}
