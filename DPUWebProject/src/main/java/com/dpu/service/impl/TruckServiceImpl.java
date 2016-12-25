package com.dpu.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.TruckDao;
import com.dpu.entity.Truck;
import com.dpu.service.TruckService;

@Component
public class TruckServiceImpl implements TruckService {
	
	@Autowired
	TruckDao truckDao;
	
	Logger logger = Logger.getLogger(TruckServiceImpl.class);
	
	@Override
	public boolean addTruck(Truck truck) {
		logger.info("[addDriver]:Service:  Enter");
		
		boolean returnValue = false;
		try {
			 		 
			truckDao.save(truck);
			returnValue = true;
			return returnValue;
					 
		} catch (Exception e) {
			return returnValue;
		} finally {
			logger.info("[addDriver]:Service:  returnValue : " + returnValue);
		}
 
	}

	@Override
	public boolean updateTruck(int id, Truck truck) {
		logger.info("[updateTruck] : Srvice: Enter");
		Criterion getDriverById = Restrictions.eq("unit_no",
				id);
		List<Truck> listofDriver = truckDao.find(getDriverById);

		if (listofDriver != null) {
			Truck updateTruck = listofDriver.get(0);

			updateTruck.setMake(truck.getTruck_class());
			updateTruck.setModel(truck.getStatus());

			// update Driver
			truckDao.update(truck);
			logger.info("[updateDriver]: Truck updated Successfully.");
			return true;
		}

		return false;
	}

	@Override
	public boolean deleteTruck(int id) {
		logger.info("[deleteTruck] :driverCode : " + id);
		System.out.println("id: "+id);
		try {
			Criterion deleteTruckCriteria = Restrictions.eq("unit_no",
					id);
			List<Truck> truck = truckDao
					.find(deleteTruckCriteria);

			if (truck != null) {
				Truck deleteTruck = truck.get(0);
				truckDao.delete(deleteTruck);
				logger.info("[deleteTruck] :Truck Deleted Successfully. : ");
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error("[deleteTruck] :Exception : " + e);
			return false;
		}
		return false;
	}

	@Override
	public List<Truck> getAllTruck() {
		List<Truck> listOfDriver = null;
		try {
			logger.info("[getAllTruck]:  Service : Enter");

			listOfDriver = truckDao.findAll();
			logger.info("[getAllTruck]: Service: listOfDriver : "
					+ listOfDriver);
			return listOfDriver;
		} catch (Exception e) {
			logger.error("[getAllTruck ] Service: Exception :"
					+ e.getMessage());
		}
		return listOfDriver;
	}

	@Override
	public Truck getTruckById(int id) {
		logger.info("[getTruckById]:  Service : Enter");
		System.out.println("Enter getById");
		Criterion getTruckById= Restrictions.eqOrIsNull(
				"unit_no", id);
		List<Truck> listOfDriver = truckDao
				.find(getTruckById);

		if (listOfDriver != null) {
			Truck truck = listOfDriver.get(0);
			logger.info("[getTruckById]:  Driver details got succesfully. ");
			return truck;
		}
		return null;
	}
	 

}
