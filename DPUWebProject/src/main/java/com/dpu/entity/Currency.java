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
@Table(name = "currency")
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "currency_name")
	private String currencyName;

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

	public String getArrangedWith() {
		return currencyName;
	}

	public void setArrangedWith(String currencyName) {
		this.currencyName = currencyName;
	}
}
