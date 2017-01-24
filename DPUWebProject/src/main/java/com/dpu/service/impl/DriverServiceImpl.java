package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.DriverDao;
import com.dpu.entity.Driver;
import com.dpu.model.DriverReq;
import com.dpu.service.DriverService;

/**
 * @author sumit
 *
 */

@Component
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverDao driverDao;

	Logger logger = Logger.getLogger(DriverServiceImpl.class);

	@Override
	public boolean addDriver(Driver driver) {

		logger.info("[addDriver]:Service:  Enter");

		boolean returnValue = false;
		try {
				boolean isDriverExist = isDriverExist(driver.getDriverCode());
				if(!isDriverExist){
					driverDao.save(driver);
					returnValue = true;
					return returnValue;
				}else{
					return returnValue;
				}
			 
		} catch (Exception e) {
			return returnValue;
		} finally {
			logger.info("[addDriver]:Service:  returnValue : " + returnValue);
		}

	}

	@Override
	public boolean updateDriver(String driverCode, Driver driver) {
		logger.info("[updateDriver] : Srvice: Enter");
		Criterion getDriverByDriverCode = Restrictions.eq("driverCode",
				driverCode);
		List<Driver> listofDriver = driverDao.find(getDriverByDriverCode);

		if (listofDriver != null) {
			Driver driverEntity = listofDriver.get(0);

			driverEntity.setFirstName(driver.getFirstName());
			driverEntity.setLastName(driver.getLastName());

			// update Driver
			driverDao.update(driverEntity);
			logger.info("[updateDriver]: Driver updated Successfully.");
			return true;
		}

		return false;
	}

	@Override
	public boolean deleteDriver(Integer driverId) {
		logger.info("[deleteDriver] :driverCode : " + driverId);
		try {
			Criterion deleteDriverCriteria = Restrictions.eq("driverId", driverId);
			List<Driver> driverEntity = driverDao.find(deleteDriverCriteria);

			if (driverEntity != null) {
				Driver driver = driverEntity.get(0);
				driverDao.delete(driver);
				logger.info("[deleteDriver] :Driver Deleted Successfully. : ");
				return true;
			}
		} catch (Exception e) {
			logger.error("[deleteDriver] :Exception : " + e);
			return false;
		}
		return false;

	}

	@Override
	public List<DriverReq> getAllDriver() {
		
		List<Driver> listOfDriver = null;
		List<DriverReq> drivers = null;
		try {
			logger.info("[getAllDrivers]:  Service : Enter");

			listOfDriver = driverDao.findAll();
			logger.info("[getAllDrivers]: Service: listOfDriver : "+ listOfDriver);
			drivers = setDriverData(listOfDriver);
		} catch (Exception e) {
			logger.error("[getAllDrivers ] Service: Exception :"
					+ e.getMessage());
		}
		return drivers;
	}

	private List<DriverReq> setDriverData(List<Driver> listOfDriver) {
		
		List<DriverReq> drivers = new ArrayList<DriverReq>();
		if(listOfDriver != null && !listOfDriver.isEmpty()){
			for (Driver driver : listOfDriver) {
				DriverReq driverReq = new DriverReq();
				BeanUtils.copyProperties(driver, driverReq);
				driverReq.setCatogoryName(driver.getCategory().getName());
				driverReq.setTerminalName(driver.getTerminal().getTerminalName());
				driverReq.setStatusName(driver.getStatus().getStatus());
				driverReq.setDivisionName(driver.getDivision().getDivisionName());
				driverReq.setDriverClassName(driver.getDriverClass().getTypeName());
				driverReq.setRoleName(driver.getRole().getTypeName());
				drivers.add(driverReq);
			}
		}
		
		return drivers;
	}

	@Override
	public Driver getDriverByDriverCode(Integer driverId) {

		logger.info("[getDriverByDriverCode]:  Service : Enter");
		Criterion getDriverByDriverCodecriteria = Restrictions.eqOrIsNull(
				"driverId", driverId);
		List<Driver> listOfDriver = driverDao
				.find(getDriverByDriverCodecriteria);

		if (listOfDriver != null) {
			Driver driver = listOfDriver.get(0);
			logger.info("[getDriverByDriverCode]:  Driver details got succesfully. ");
			return driver;
		}
		return null;
	}
	
	public boolean isDriverExist(String driverCode){
		boolean isDriverExist = false;
		
		Criterion driverCriteria = Restrictions.eqOrIsNull("driverCode", driverCode);
		List<Driver> drivers = driverDao.find(driverCriteria);
		
		if(drivers.size() == 0){
			 
			return isDriverExist;
		}
		
		isDriverExist = true;
		return isDriverExist;
		
	}

}
