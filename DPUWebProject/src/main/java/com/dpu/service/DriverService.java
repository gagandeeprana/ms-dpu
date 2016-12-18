package com.dpu.service;

import java.util.List;

import com.dpu.entity.DriverEntity;

/**
 * @author sumit
 *
 */

public interface DriverService {

	boolean addDriver(DriverEntity driver);
	
	boolean updateDriver(String driverCode, DriverEntity driver);
	
	boolean deleteDriver(String driverCode);
	
	List<DriverEntity> getAllDriver();
	
	DriverEntity getDriverByDriverCode(String driverCode);
	
}

