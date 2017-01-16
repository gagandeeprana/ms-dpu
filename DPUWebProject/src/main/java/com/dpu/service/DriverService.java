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
	
	List<Driver> getAllDriver();
	
	boolean deleteDriver(Integer driverId);

	Driver getDriverByDriverCode(Integer driverId);
	
}

