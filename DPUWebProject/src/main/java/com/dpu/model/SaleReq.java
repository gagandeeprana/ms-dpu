package com.dpu.model;

import java.util.List;

import com.dpu.entity.Status;

public class SaleReq {

	private Long saleId;

	private String name;

	private List<Status> statusList;

	public Long getSaleId() {

		return saleId;
	}

	public void setSaleId(Long saleId) {

		this.saleId = saleId;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public List<Status> getStatusList() {

		return statusList;
	}

	public void setStatusList(List<Status> statusList) {

		this.statusList = statusList;
	}

}
