package com.dpu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "powerunitmaster")
public class Truck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="unit_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int unit_no;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "truck_class")
	private String truck_class;
	
	@Column(name = "owner_id")
	private int owner_id;
	
	@Column(name = "VIN")
	private String VIN;
	
	@Column(name = "make")
	private String make;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "truck_year")
	private int truck_year;
	
	@Column(name = "plate_no")
	private String plate_no;
	
	@Column(name = "jurisdiction")
	private String jurisdiction;
	
	@Column(name = "tare_weight")
	private int tare_weight;
	
	@Column(name = "rgw")
	private String rgw;
	
	@Column(name = "tracking_id")
	private int tracking_id;
	
	@Column(name = "current_odometer")
	private String current_odometer;
	
	@Column(name = "equipment_type")
	private String equipment_type;
	
	@Column(name = "terminal_id")
	private int terminal_id;

	 

	public int getUnit_no() {
		return unit_no;
	}

	public void setUnit_no(int unit_no) {
		this.unit_no = unit_no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTruck_class() {
		return truck_class;
	}

	public void setTruck_class(String truck_class) {
		this.truck_class = truck_class;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
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

	public int getTruck_year() {
		return truck_year;
	}

	public void setTruck_year(int truck_year) {
		this.truck_year = truck_year;
	}

	public String getPlate_no() {
		return plate_no;
	}

	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public int getTare_weight() {
		return tare_weight;
	}

	public void setTare_weight(int tare_weight) {
		this.tare_weight = tare_weight;
	}

	public String getRgw() {
		return rgw;
	}

	public void setRgw(String rgw) {
		this.rgw = rgw;
	}

	public int getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(int tracking_id) {
		this.tracking_id = tracking_id;
	}

	public String getCurrent_odometer() {
		return current_odometer;
	}

	public void setCurrent_odometer(String current_odometer) {
		this.current_odometer = current_odometer;
	}

	public String getEquipment_type() {
		return equipment_type;
	}

	public void setEquipment_type(String equipment_type) {
		this.equipment_type = equipment_type;
	}

	public int getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(int terminal_id) {
		this.terminal_id = terminal_id;
	}

 
	
	

}
