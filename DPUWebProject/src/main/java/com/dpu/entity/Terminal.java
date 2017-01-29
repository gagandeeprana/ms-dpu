package com.dpu.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "terminalmaster")
public class Terminal {

	@Id
	@Column(name = "terminal_id")
	@GeneratedValue
	//@JsonProperty(value = "terminal_id")
	private Long terminalId;

	@Column(name = "terminal_name")
	//@JsonProperty(value = "terminal_name")
	private String terminalName;

	@Column(name = "facility")
	//@JsonProperty(value = "facility")
	private String facility;

	@Column(name = "location")
	//@JsonProperty(value = "location")
	private String location;
	
	@Column(name = "available_services")
	//@JsonProperty(value = "available_services")
	private String availableServices;

	@Column(name = "created_by")
	//@JsonProperty(value = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	//@JsonProperty(value = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "modified_by")
	//@JsonProperty(value = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_on")
	//@JsonProperty(value = "modified_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedOn;
		
	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;
	
	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAvailableServices() {
		return availableServices;
	}

	public void setAvailableServices(String availableServices) {
		this.availableServices = availableServices;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

//	public Status getStatusmaster() {
//		return statusmaster;
//	}
//
//	public void setStatusmaster(Status statusmaster) {
//		this.statusmaster = statusmaster;
//	}

	
	
	
}
