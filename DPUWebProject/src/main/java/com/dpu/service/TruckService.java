package com.dpu.service;

import java.util.List;

import com.dpu.entity.Truck;

public interface TruckService {
	

	boolean addTruck(Truck driver);
	
	List<Truck> getAllTruck();
	
	Truck getTruckById(Long id);

	boolean updateTruck(Long id, Truck truck);

	boolean deleteTruck(Long id);

}
