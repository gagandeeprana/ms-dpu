package com.dpu.service;

import java.util.List;

import com.dpu.entity.Driver;
import com.dpu.model.DriverReq;

/**
 * @author sumit
 *
 */

public interface DriverService {

	boolean updateDriver(String driverCode, Driver driver);
	
	List<DriverReq> getAllDriver();
	
	boolean deleteDriver(Integer driverId);

	Driver getDriverByDriverCode(Integer driverId);

	DriverReq getOpenAdd();

	Object addDriver(DriverReq driverReq);
	
}

