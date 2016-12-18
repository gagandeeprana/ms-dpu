package com.dpu.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.controller.DriverController;
import com.dpu.dao.DriverDao;
import com.dpu.entity.DriverEntity;
import com.dpu.service.DriverService;


/**
 * @author sumit
 *
 */

@Component
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverDao driverDao;

	Logger logger = Logger.getLogger(DriverController.class);

	@Override
	public boolean addDriver(DriverEntity driver) {
		logger.info("[addDriver]:Service:  Enter");
		boolean returnValue = false;
		try {
			driverDao.save(driver);
			returnValue = true;
			return returnValue;
		} catch (Exception e) {
			return returnValue;
		} finally {
			logger.info("[addDriver]:Service:  returnValue : " + returnValue);
		}

	}

	@Override
	public boolean updateDriver(String driverCode, DriverEntity driver) {
		logger.info("[updateDriver] : Srvice: Enter");
		Criterion getDriverByDriverCode = Restrictions.eq("driverCode",
				driverCode);
		List<DriverEntity> listofDriver = driverDao.find(getDriverByDriverCode);

		if (listofDriver != null) {
			DriverEntity driverEntity = listofDriver.get(0);

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
	public boolean deleteDriver(String driverCode) {
		logger.info("[deleteDriver] :driverCode : " + driverCode);
		try {
			Criterion deleteDriverCriteria = Restrictions.eq("driverCode",
					driverCode);
			List<DriverEntity> driverEntity = driverDao
					.find(deleteDriverCriteria);

			if (driverEntity != null) {
				DriverEntity driver = driverEntity.get(0);
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
	public List<DriverEntity> getAllDriver() {
		List<DriverEntity> listOfDriver = null;
		try {
			logger.info("[getAllDrivers]:  Service : Enter");

			listOfDriver = driverDao.findAll();
			logger.info("[getAllDrivers]: Service: listOfDriver : "
					+ listOfDriver);
			return listOfDriver;
		} catch (Exception e) {
			logger.error("[getAllDrivers ] Service: Exception :"
					+ e.getMessage());
		}
		return listOfDriver;
	}

	@Override
	public DriverEntity getDriverByDriverCode(String driverCode) {

		logger.info("[getDriverByDriverCode]:  Service : Enter");
		Criterion getDriverByDriverCodecriteria = Restrictions.eqOrIsNull(
				"driverCode", driverCode);
		List<DriverEntity> listOfDriver = driverDao
				.find(getDriverByDriverCodecriteria);

		if (listOfDriver != null) {
			DriverEntity driver = listOfDriver.get(0);
			logger.info("[getDriverByDriverCode]:  Driver details got succesfully. ");
			return driver;
		}
		return null;
	}

}
