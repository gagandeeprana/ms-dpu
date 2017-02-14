package com.dpu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ProbilModel implements Serializable{

	 
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long probilNo;
	
	private String shipperName;
	private Long shipperId;
	private List<ShipperResponse> shipperList;
	
	private String consineeName;
	private Long consineeId;
	private List<ShipperResponse> consineeList;
	
	private String pickupName;
	private Long pickupId;
	private List<TypeResponse> pickupList;
	
	private String deliveryName;
	private Long deliveryId;
	private List<TypeResponse> deliveryList;
	
	private Date pickupScheduledDate;
	
	private Date pickupScheduledTime;
	
	private Date deliverScheduledDate;
	
	private Date deliverScheduledTime;
	
	private Date pickupMABDate;
	
	private Date pickupMABTime;
	
	private Date deliveryMABDate;
	
	private Date deliveryMABTime;

	private List<OrderPickUpDeliveryModel> OrderPickUpDeliveryList;
	
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

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public Long getShipperId() {
		return shipperId;
	}

	public void setShipperId(Long shipperId) {
		this.shipperId = shipperId;
	}

	public List<ShipperResponse> getShipperList() {
		return shipperList;
	}

	public void setShipperList(List<ShipperResponse> shipperList) {
		this.shipperList = shipperList;
	}

	public String getConsineeName() {
		return consineeName;
	}

	public void setConsineeName(String consineeName) {
		this.consineeName = consineeName;
	}

	public Long getConsineeId() {
		return consineeId;
	}

	public void setConsineeId(Long consineeId) {
		this.consineeId = consineeId;
	}

	public List<ShipperResponse> getConsineeList() {
		return consineeList;
	}

	public void setConsineeList(List<ShipperResponse> consineeList) {
		this.consineeList = consineeList;
	}

	public String getPickupName() {
		return pickupName;
	}

	public void setPickupName(String pickupName) {
		this.pickupName = pickupName;
	}

	public Long getPickupId() {
		return pickupId;
	}

	public void setPickupId(Long pickupId) {
		this.pickupId = pickupId;
	}

	public List<TypeResponse> getPickupList() {
		return pickupList;
	}

	public void setPickupList(List<TypeResponse> pickupList) {
		this.pickupList = pickupList;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public List<TypeResponse> getDeliveryList() {
		return deliveryList;
	}

	public void setDeliveryList(List<TypeResponse> deliveryList) {
		this.deliveryList = deliveryList;
	}

	public Date getPickupScheduledDate() {
		return pickupScheduledDate;
	}

	public void setPickupScheduledDate(Date pickupScheduledDate) {
		this.pickupScheduledDate = pickupScheduledDate;
	}

	public Date getPickupScheduledTime() {
		return pickupScheduledTime;
	}

	public void setPickupScheduledTime(Date pickupScheduledTime) {
		this.pickupScheduledTime = pickupScheduledTime;
	}

	public Date getDeliverScheduledDate() {
		return deliverScheduledDate;
	}

	public void setDeliverScheduledDate(Date deliverScheduledDate) {
		this.deliverScheduledDate = deliverScheduledDate;
	}

	public Date getDeliverScheduledTime() {
		return deliverScheduledTime;
	}

	public void setDeliverScheduledTime(Date deliverScheduledTime) {
		this.deliverScheduledTime = deliverScheduledTime;
	}

	public Date getPickupMABDate() {
		return pickupMABDate;
	}

	public void setPickupMABDate(Date pickupMABDate) {
		this.pickupMABDate = pickupMABDate;
	}

	public Date getPickupMABTime() {
		return pickupMABTime;
	}

	public void setPickupMABTime(Date pickupMABTime) {
		this.pickupMABTime = pickupMABTime;
	}

	public Date getDeliveryMABDate() {
		return deliveryMABDate;
	}

	public void setDeliveryMABDate(Date deliveryMABDate) {
		this.deliveryMABDate = deliveryMABDate;
	}

	public Date getDeliveryMABTime() {
		return deliveryMABTime;
	}

	public void setDeliveryMABTime(Date deliveryMABTime) {
		this.deliveryMABTime = deliveryMABTime;
	}

	public List<OrderPickUpDeliveryModel> getOrderPickUpDeliveryList() {
		return OrderPickUpDeliveryList;
	}

	public void setOrderPickUpDeliveryList(
			List<OrderPickUpDeliveryModel> orderPickUpDeliveryList) {
		OrderPickUpDeliveryList = orderPickUpDeliveryList;
	}

	
}
