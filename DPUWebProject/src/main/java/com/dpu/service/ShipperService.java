package com.dpu.service;

import java.util.List;

import com.dpu.model.ShipperResponse;

public interface ShipperService {

	Object add(ShipperResponse shipperResponse);

	Object delete(Long id);

	List<ShipperResponse> getAll();

	ShipperResponse get(Long id);

	ShipperResponse getMasterData();

	List<ShipperResponse> getShipperByCompanyName(String companyName);

	Object update(Long id, ShipperResponse shipperResponse);
	
	List<ShipperResponse> getSpecificData();

	ShipperResponse getParticularData(Long id);
}
