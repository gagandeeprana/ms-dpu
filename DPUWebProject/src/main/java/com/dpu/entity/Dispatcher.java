package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dispatcher")
public class Dispatcher {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "dispatcher_name")
	private String dispatcherName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carrier_contract_id")
	private CarrierContract carrierContract;

	public CarrierContract getCarrierContract() {
		return carrierContract;
	}

	public void setCarrierContract(CarrierContract carrierContract) {
		this.carrierContract = carrierContract;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDispatcherName() {
		return dispatcherName;
	}

	public void setDispatcherName(String dispatcherName) {
		this.dispatcherName = dispatcherName;
	}

}
