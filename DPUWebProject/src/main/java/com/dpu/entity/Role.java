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
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "roleId")
	private Long id;

	@Column(name = "roleName")
	private String roleName;

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
