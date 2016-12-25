package com.dpu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "trailermaster")
public class Trailer {

	@Id
	@Column(name = "trailer_id")
	@GeneratedValue
	private int trailerId;
	
	@Column(name = "class_id")
	private Integer classId;
	
	@Column(name = "equipment_id")
	private int equipmentId;
	
	@Column(name = "length")
	private int length;
	
	@Column(name = "VIN")
	private String VIN;
	
	@Column(name = "make")
	private String make;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "year")
	private int year;
	
	@Column(name = "plate_no")
	private String plateNo;
	
	@Column(name = "jurisdiction")
	private String jurisdiction;
	
	@Column(name = "tare_weight")
	private String tareWeight;
	
	@Column(name = "rgw")
	private String rgw;
	
	@Column(name = "current_odometer")
	private String currentOdometer;
	
	@Column(name = "reading_taken_date")
	private Date readingTakenDate;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Column(name = "created_on")
	private Date createdOn;

	public int getTrailerId() {
		return trailerId;
	}

	public void setTrailerId(int trailerId) {
		this.trailerId = trailerId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
