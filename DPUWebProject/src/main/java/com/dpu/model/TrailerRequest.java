package com.dpu.model;

import java.util.Date;

public class TrailerRequest {
	
	private int trailerId;
	

	private int classId;
	

	private int equipmentId;
	

	private int length;
	
	
	private String VIN;
	

	private String make;
	

	private String model;
	

	private int year;
	

	private String plateNo;
	

	private String jurisdiction;
	

	private String tareWeight;
	

	private String rgw;
	
	
	private String currentOdometer;
	

	private Date readingTakenDate;
	

	private int createdBy;
	

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
