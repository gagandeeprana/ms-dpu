package com.dpu.service;

import java.util.List;

import com.dpu.model.CarrierContractModel;

public interface CarrierContractService {

	List<CarrierContractModel> getAllCarrierContract();

	Object addCarrierContract(CarrierContractModel carrierContract);

	CarrierContractModel getOpenAdd();

}
