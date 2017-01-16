package com.dpu.service.impl;

import java.util.Date;
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
			
//			truck.setCreated("sumit");	
//			truck.setCreatedOn(new Date());
//			
//			truck.setModifiedBy("sumit");
//			truck.setModifiedOn(new Date());
			
			Truck truckR = truckDao.save(truck);
			System.out.println("[addTruck]truck Id :" + truckR.getTruckId());
			returnValue = true;
			return returnValue;
					 
		} catch (Exception e) {
			System.out.println(e);
			return returnValue;
		} finally {
			logger.info("[addDriver]:Service:  returnValue : " + returnValue);
		}
 
	}

	@Override
	public boolean updateTruck(Long id, Truck truck) {
		logger.info("[updateTruck] : Srvice: Enter");
		Criterion getDriverById = Restrictions.eq("truckId",
				id);
		List<Truck> listofDriver = truckDao.find(getDriverById);

		if (listofDriver != null) {
			Truck updateTruck = listofDriver.get(0);

			//updateTruck.setMake(truck.getTruck_class());
			//updateTruck.setModel(truck.getStatus());
//			if(truck.getCurrentOdometer() != null){
//				updateTruck.setCurrentOdometer(truck.getCurrentOdometer());
//			}
//			if(truck.getEquipmentType() != null){
//				updateTruck.setEquipmentType(truck.getEquipmentType());
//			}
//			if(truck.getJurisdiction() != null){
//				updateTruck.setJurisdiction(truck.getJurisdiction());
//			}
//			if(truck.getMake() != null){
//				updateTruck.setMake(truck.getMake());
//			}
//			if(truck.getModel() != null){
//				updateTruck.setModel(truck.getModel());
//			}
//			if(truck.getOwnerId() != null){
//				updateTruck.setOwnerId(truck.getOwnerId());
//			}
//			if(truck.getPlateNo() != null){
//				updateTruck.setPlateNo(truck.getPlateNo());
//			}
//			if(truck.getRgw() != null){
//				updateTruck.setRgw(truck.getRgw());
//			}
//			if(truck.getStatus() != null){
//				updateTruck.setStatus(truck.getStatus());
//			}
//			if(truck.getTareWeight() != null){
//				updateTruck.setTareWeight(truck.getTareWeight());
//			}
//			 
//			if(truck.getTruckClass() != null){
//				updateTruck.setTruckClass(truck.getTruckClass());
//			}
//			if(truck.getTruckYear() != null){
//				updateTruck.setTruckYear(truck.getTruckYear());
//			}
//			if(truck.getUnitNo() != null){
//				updateTruck.setUnitNo(truck.getUnitNo());
//			}
//			if(truck.getVin() != null){
//				updateTruck.setVin(truck.getVin());
//			}
			
			 
			
//			truck.setModifiedBy("sumit");
//			truck.setModifiedOn(new Date());

			// update Driver
			truckDao.update(truck);
			System.out.println("[updateDriver]: Truck updated Successfully.");
			logger.info("[updateDriver]: Truck updated Successfully.");
			return true;
		}

		return false;
	}

	@Override
	public boolean deleteTruck(Long id) {
		logger.info("[deleteTruck] :driverCode : " + id);
		System.out.println("id: "+id);
		try {
			Criterion deleteTruckCriteria = Restrictions.eq("truckId",
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
	public Truck getTruckById(Long id) {
		logger.info("[getTruckById]:  Service : Enter");
		System.out.println("Enter getById");
		Criterion getTruckById= Restrictions.eqOrIsNull(
				"truckId", id);
		List<Truck> listOfDriver = truckDao
				.find(getTruckById);

		if (listOfDriver != null) {
			Truck truck = listOfDriver.get(0);
			logger.info("[getTruckById]:  Driver details got succesfully. ");
			return truck;
		}
		return null;
	}
	 
	public boolean isTruckExist(){
		boolean isExist = false;
		return isExist;
	}

}
