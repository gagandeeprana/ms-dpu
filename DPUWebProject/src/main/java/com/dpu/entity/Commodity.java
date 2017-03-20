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
@Table(name = "commodity")
public class Commodity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "commodity_name")
	private String commodityName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carrier_contract_id")
	private CarrierContract carrierContract;

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

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
		return commodityName;
	}

	public void setArrangedWith(String commodityName) {
		this.commodityName = commodityName;
	}

}
