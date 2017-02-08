package com.dpu.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "order")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private Long id;

	@Column(unique = true, name = "driver_code")
	private String driverCode;



	@Column(name = "unit")
	private String unit;

	@Column(name = "city")
	private String city;

	@Column(name = "province")
	private String pvs;

	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "email")
	private String email;

	@Column(name = "home")
	private String home;

	@Column(name = "fax_no")
	private String faxNo;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	@ManyToOne
	@JoinColumn(name = "billing_location_id")
	private CompanyBillingLocation billingLocation;

	@ManyToOne
	@JoinColumn(name = "contact_id")
	private CompanyAdditionalContacts contact;
	
	@ManyToOne
	@JoinColumn(name = "shipper_id")
	private Shipper shipper;

	@ManyToOne
	@JoinColumn(name = "consine_id")
	private Shipper consine;

	/*@ManyToOne
	@JoinColumn(name = "functional_id")
	private Long functional;*/
	
	@ManyToOne
	@JoinColumn(name = "temperature_id")
	private Type temperature;
	
	@Column(name = "temperature_value")
	private Double temperatureValue;
	
	@ManyToOne
	@JoinColumn(name = "temperature_type")
	private Type temperatureType;
	
	@ManyToOne
	@JoinColumn(name = "currency_id")
	private Type currency;
	
	@ManyToOne
	@JoinColumn(name = "pickup")
	private Type pickUp;
	
	@ManyToOne
	@JoinColumn(name = "delivery")
	private Type delivery;
	
	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "probil")
	private Long probilNo;
	
	@Column(name = "po_no")
	private Long poNo;
	
	@Column(name = "pickup_no")
	private String pickUpNo;
	
	@Column(name = "delivery_no")
	private String deliveryNo;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
//	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;


}
