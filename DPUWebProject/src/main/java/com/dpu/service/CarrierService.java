package com.dpu.service;

import java.util.List;

import com.dpu.model.CarrierModel;

public interface CarrierService {

	List<CarrierModel> getAll();
	Object delete(Long carrierId);

}