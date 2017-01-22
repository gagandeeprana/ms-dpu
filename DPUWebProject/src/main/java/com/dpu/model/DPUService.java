package com.dpu.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author jagvir
 *
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class DPUService {
	
	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setServiceResponse(Integer serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	//@JsonProperty(value = "service_id")
	private Long serviceId;

	//@JsonProperty(value = "service_name")
	private String serviceName;

	//@JsonProperty(value = "service_response")
	private Integer serviceResponse;

	//@JsonProperty(value = "status")
	private String status;
	
	private String textField;
	
	private String associationWith;
	
	private Long statusId;

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getAssociationWith() {
		return associationWith;
	}

	public void setAssociationWith(String associationWith) {
		this.associationWith = associationWith;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getServiceResponse() {
		return serviceResponse;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

}
