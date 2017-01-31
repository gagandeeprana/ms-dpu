package com.dpu.service;

import java.util.List;

import com.dpu.entity.Truck;
import com.dpu.model.DivisionReq;
import com.dpu.model.TruckResponse;

public interface TruckService {

	List<TruckResponse> update(Long id, TruckResponse truckResponse);

	List<TruckResponse> delete(Long id);

	TruckResponse get(Long id);

	List<TruckResponse> getAllTrucks(String owner);

	List<TruckResponse> add(TruckResponse truckResponse);

}
