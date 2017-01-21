/**
 * 
 */
package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private Long serviceId;

	@Column(name = "service_name")
	//@JsonProperty(value = "service_name")
	private String serviceName;
	
	@Column(name = "service_response")
	//@JsonProperty(value = "service_response")
	private Integer serviceResponse;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;
	
	@ManyToOne
	//@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "association_with")
	private Type associationWith;
	
	@ManyToOne
	//@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "text_field")
	private Type textField;

	public Type getAssociationWith() {
		return associationWith;
	}

	public void setAssociationWith(Type associationWith) {
		this.associationWith = associationWith;
	}

	public Type getTextField() {
		return textField;
	}

	public void setTextField(Type textField) {
		this.textField = textField;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setServiceResponse(Integer serviceResponse) {
		this.serviceResponse = serviceResponse;
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

}
