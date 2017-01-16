/**
 * 
 */
package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author jagvir
 *
 */

@Entity
@Table(name = "service")
@JsonSerialize(include = Inclusion.NON_NULL)
public class Service {
	
	@Id
	@Column(name = "service_id")
	@GeneratedValue
	//@JsonProperty(value = "service_id")
	private int serviceId;

	@Column(name = "service_name")
	//@JsonProperty(value = "service_name")
	private String serviceName;
	
	@Column(name = "association_with")
	private String associationWith;
	
	@Column(name = "text_field")
	private String textField;

	@Column(name = "service_response")
	//@JsonProperty(value = "service_response")
	private int serviceResponse;

	@Column(name = "status")
	//@JsonProperty(value = "status")
	private int status;

	public String getAssociationWith() {
		return associationWith;
	}

	public void setAssociationWith(String associationWith) {
		this.associationWith = associationWith;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(int serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
