package com.dpu.service;

import java.util.List;

import com.dpu.entity.Driver;

/**
 * @author sumit
 *
 */

public interface DriverService {

	boolean addDriver(Driver driver);
	
	boolean updateDriver(String driverCode, Driver driver);
	
	boolean deleteDriver(String driverCode);
	
	List<Driver> getAllDriver();
	
	Driver getDriverByDriverCode(String driverCode);
	
}

