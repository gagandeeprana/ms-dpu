package com.dpu.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "truck")
public class Truck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String usage;
	
	private String owner;
	
	private String division;
	
	@Column(name = "oo_name")
	private String oOName;
	
	private String terminal;
	
	private String category;

	@Column(name = "truck_type")
	private String truckType;
	
	private String finance;
	
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

	@Id
	@Column(name="truck_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long truck_id;
	
	@Column(name="unit_no")
	private Integer unit_no;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "truck_class")
	private String truck_class;
	
	@Column(name = "owner_id")
	private Integer owner_id;
	
	@Column(name = "VIN")
	private String VIN;
	
	@Column(name = "make")
	private String make;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "truck_year")
	private Integer truck_year;
	
	@Column(name = "plate_no")
	private String plate_no;
	
	@Column(name = "jurisdiction")
	private String jurisdiction;
	
	@Column(name = "tare_weight")
	private Integer tare_weight;
	
	@Column(name = "rgw")
	private String rgw;
	
	 
	@Column(name = "current_odometer")
	private String current_odometer;
	
	@Column(name = "equipment_type")
	private String equipment_type;
	
	@Column(name = "created_by")
	private String created_by;
	
	@Column(name = "created_on")
	private Date created_on;
	
	@Column(name = "modified_by")
	private String modified_by;
	
	@Column(name = "modified_on")
	private Date modified_on;
	 
	 
	public Long getTruck_id() {
		return truck_id;
	}

	public void setTruck_id(Long truck_id) {
		this.truck_id = truck_id;
	}

	public Integer getUnit_no() {
		return unit_no;
	}

	public void setUnit_no(Integer unit_no) {
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

	public Integer getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Integer owner_id) {
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

	public Integer getTruck_year() {
		return truck_year;
	}

	public void setTruck_year(Integer truck_year) {
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

	public Integer getTare_weight() {
		return tare_weight;
	}

	public void setTare_weight(Integer tare_weight) {
		this.tare_weight = tare_weight;
	}

	public String getRgw() {
		return rgw;
	}

	public void setRgw(String rgw) {
		this.rgw = rgw;
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

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	public Date getModified_on() {
		return modified_on;
	}

	public void setModified_on(Date modified_on) {
		this.modified_on = modified_on;
	}

	 
	 

	 
}
