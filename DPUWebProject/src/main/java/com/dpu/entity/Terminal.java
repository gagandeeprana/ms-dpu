package com.dpu.entity;

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
@Table(name = "terminalmaster")
public class Terminal {

	@Id
	@Column(name = "terminal_id")
	@GeneratedValue
	@JsonProperty(value = "terminal_id")
	private int terminalId;

	@Column(name = "terminal_name")
	@JsonProperty(value = "terminal_name")
	private String terminalName;

	@Column(name = "facility")
	@JsonProperty(value = "facility")
	private String facility;

	@Column(name = "location")
	@JsonProperty(value = "location")
	private String location;
	
	@Column(name = "available_services")
	@JsonProperty(value = "available_services")
	private String availableServices;

	public int getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(int terminalId) {
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
	
	
}
