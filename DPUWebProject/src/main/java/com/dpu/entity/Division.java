/**
 * 
 */
package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author jagvir
 *
 */

@Entity
@Table(name = "divisionmaster")
@JsonSerialize(include = Inclusion.NON_NULL)
public class Division {

	@Id
	@Column(name = "division_id")
	@GeneratedValue
	private int divisionId;

	@Column(name = "division_code")
	private String divisionCode;

	@Column(name = "division_name")
	private String divisionName;

	@Column(name = "fedral")
	private String fedral;

	@Column(name = "provincial")
	private String provincial;

	@Column(name = "SCAC")
	private String SCAC;

	@Column(name = "carrier_code")
	private int carrierCode;

	@Column(name = "contract_prefix")
	private String contractPrefix;

	@Column(name = "invoice_prefix")
	private String invoicePrefix;

	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getFedral() {
		return fedral;
	}

	public void setFedral(String fedral) {
		this.fedral = fedral;
	}

	public String getProvincial() {
		return provincial;
	}

	public void setProvincial(String provincial) {
		this.provincial = provincial;
	}

	public String getSCAC() {
		return SCAC;
	}

	public void setSCAC(String sCAC) {
		SCAC = sCAC;
	}

	public int getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(int carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getContractPrefix() {
		return contractPrefix;
	}

	public void setContractPrefix(String contractPrefix) {
		this.contractPrefix = contractPrefix;
	}

	public String getInvoicePrefix() {
		return invoicePrefix;
	}

	public void setInvoicePrefix(String invoicePrefix) {
		this.invoicePrefix = invoicePrefix;
	}


}
