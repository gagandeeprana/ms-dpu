package com.dpu.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class OrderPickUpDeliveryModel implements Serializable{

	 
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String pickupDeliveryNo;
	
	private String typeName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPickupDeliveryNo() {
		return pickupDeliveryNo;
	}

	public void setPickupDeliveryNo(String pickupDeliveryNo) {
		this.pickupDeliveryNo = pickupDeliveryNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
}
