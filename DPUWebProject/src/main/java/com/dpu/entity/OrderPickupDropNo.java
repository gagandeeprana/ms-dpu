package com.dpu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "orderpickupdeliveryno")
public class OrderPickupDropNo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orderpickupdelivery_id")
	private Long id;
	
	@Column(name = "probil_no")
	private Long probilNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "probil_id")
	private Probil probil;
	
	@Column(name = "pickupdelivery_no")
	private String pickupDeliveryNo;
	
	@Column(name = "type")
	private Long type;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProbilNo() {
		return probilNo;
	}

	public void setProbilNo(Long probilNo) {
		this.probilNo = probilNo;
	}

	public Probil getProbil() {
		return probil;
	}

	public void setProbil(Probil probil) {
		this.probil = probil;
	}

	public String getPickupDeliveryNo() {
		return pickupDeliveryNo;
	}

	public void setPickupDeliveryNo(String pickupDeliveryNo) {
		this.pickupDeliveryNo = pickupDeliveryNo;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

}
