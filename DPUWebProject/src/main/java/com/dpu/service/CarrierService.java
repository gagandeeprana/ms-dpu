package com.dpu.service;

import java.util.List;

import com.dpu.model.CarrierAdditionalContactModel;
import com.dpu.model.CarrierModel;

public interface CarrierService {

	List<CarrierModel> getAll();
	
	Object delete(Long carrierId);
	
	Object updateCarrier(Long id, CarrierModel carrierResponse);
	
	CarrierModel get(Long id);
	
	Object addCarrierData(CarrierModel carrierResponse);
	
	CarrierAdditionalContactModel getContactById(Long id);

	List<CarrierModel> getCarriersByCarrierName(String carrierName);

	List<CarrierModel> getAllCarriersIdAndName();

}
