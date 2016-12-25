package com.dpu.service;

import java.util.List;

import com.dpu.entity.Truck;

public interface TruckService {
	

	boolean addTruck(Truck driver);
	
	boolean updateTruck(int id, Truck driver);
	
	boolean deleteTruck(int id);
	
	List<Truck> getAllTruck();
	
	Truck getTruckById(int id);

}
