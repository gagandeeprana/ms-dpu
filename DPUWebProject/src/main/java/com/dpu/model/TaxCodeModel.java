package com.dpu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
@JsonIgnoreProperties(ignoreUnknown = true) 
@JsonSerialize(include = Inclusion.NON_NULL)
public class TaxCodeModel {

 
	private Long taxCodeId;

	private String taxCode;
	
	private String description;
	
	private Double percentage;
	
	private Boolean taxable;

	public Long getTaxCodeId() {
		return taxCodeId;
	}

	public void setTaxCodeId(Long taxCodeId) {
		this.taxCodeId = taxCodeId;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Boolean getTaxable() {
		return taxable;
	}

	public void setTaxable(Boolean taxable) {
		this.taxable = taxable;
	}

	
}
