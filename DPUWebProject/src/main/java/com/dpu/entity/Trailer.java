package com.dpu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "newtrailermaster")
public class Trailer {

	@Id
	@Column(name = "trailer_id")
	@GeneratedValue
	//@JsonProperty(value = "trailer_id")
	private Integer trailerId;
	
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
	}

}
