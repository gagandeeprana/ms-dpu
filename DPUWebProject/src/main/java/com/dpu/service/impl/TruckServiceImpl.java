package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.TruckDao;
import com.dpu.entity.Division;
import com.dpu.entity.Driver;
import com.dpu.entity.Truck;
import com.dpu.model.DivisionReq;
import com.dpu.model.DriverReq;
import com.dpu.model.TruckResponse;
import com.dpu.service.TruckService;

@Component
public class TruckServiceImpl implements TruckService {

	@Autowired
	TruckDao truckDao;

	@Autowired
	SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(TruckServiceImpl.class);

	@Override
	public List<TruckResponse> update(Long id, TruckResponse tuckResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TruckResponse> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TruckResponse get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TruckResponse> getAllTrucks(String owner) {
		logger.info("[TruckServiceImpl] [getAllTrucks] : Enter ");
		List<Truck> lstTruck = null;
		List<TruckResponse> lstTruckResponse = new ArrayList<TruckResponse>();
		try {

			if (owner != null && owner.length() > 0) {
				Criterion criterion = Restrictions.like("owner", owner,
						MatchMode.ANYWHERE);
				lstTruck = truckDao.find(criterion);
			} else {
				System.out.println(">>>>>>>>>>>>>>>>>>");
				lstTruck = truckDao.findAll();
			}
			if (lstTruck != null && lstTruck.size() > 0) {
				for (Truck truck : lstTruck) {
					TruckResponse truckResponse = new TruckResponse();
					truckResponse.setUnitNo(truck.getUnitNo());
					truckResponse.setOwner(truck.getOwner());
					truckResponse.setoOName(truck.getoOName());
					truckResponse
							.setCatogoryName(truck.getCategory().getName());
					truckResponse.setTruchUsage(truck.getUsage());
					truckResponse.setDivisionName(truck.getDivision()
							.getDivisionName());
					truckResponse.setTerminalName(truck.getTerminal()
							.getTerminalName());
					truckResponse.setTruckType(truck.getTruckType());
					truckResponse.setFinance(truck.getFinance());
					truckResponse.setStatusName(truck.getStatus().getStatus());
					lstTruckResponse.add(truckResponse);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[TruckServiceImpl] [getAllTrucks] : Exit ");
		return lstTruckResponse;
	}

	// private List<TruckResponse> setTruckData(List<Truck> listOfTrucks) {
	//
	// List<TruckResponse> lstTruckResponses = new ArrayList<TruckResponse>();
	// if(listOfTrucks != null && !listOfTrucks.isEmpty()){
	// for (Truck truck : listOfTrucks) {
	// TruckResponse truckResponse = new TruckResponse();
	// BeanUtils.copyProperties(truck, truckResponse);
	// truckResponse.setCatogoryName(truck.getCategory().getName());
	// truckResponse.setTerminalName(truck.getTerminal().getTerminalName());
	// truckResponse.setStatusName(truck.getStatus().getStatus());
	// truckResponse.setDivisionName(truck.getDivision().getDivisionName());
	// lstTruckResponses.add(truckResponse);
	// }
	// }
	//
	// return lstTruckResponses;
	// }

	@Override
	public List<TruckResponse> add(TruckResponse truckResponse) {
		logger.info("TruckServiceImpl: add():  STARTS");

		Session session = null;
		Transaction tx = null;
		List<TruckResponse> truckList = null;

		try {

			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Truck truck = truckDao.add(session, truckResponse);
			truckList = getAllTrucks("");
		} catch (Exception e) {
			logger.fatal("TruckServiceImpl: add(): Exception: "
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			logger.info("TruckServiceImpl: add():  finally block");
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		logger.info("TruckServiceImpl: add():  ENDS");

		return truckList;

	}

	/*
	 * @Override public boolean addTruck(Truck truck) {
	 * logger.info("[addDriver]:Service:  Enter");
	 * 
	 * boolean returnValue = false; try {
	 * 
	 * // truck.setCreated("sumit"); // truck.setCreatedOn(new Date()); // //
	 * truck.setModifiedBy("sumit"); // truck.setModifiedOn(new Date());
	 * 
	 * Truck truckR = truckDao.save(truck);
	 * System.out.println("[addTruck]truck Id :" + truckR.getTruckId());
	 * returnValue = true; return returnValue;
	 * 
	 * } catch (Exception e) { System.out.println(e); return returnValue; }
	 * finally { logger.info("[addDriver]:Service:  returnValue : " +
	 * returnValue); }
	 * 
	 * }
	 * 
	 * @Override public boolean updateTruck(Long id, Truck truck) {
	 * logger.info("[updateTruck] : Srvice: Enter"); Criterion getDriverById =
	 * Restrictions.eq("truckId", id); List<Truck> listofDriver =
	 * truckDao.find(getDriverById);
	 * 
	 * if (listofDriver != null) { Truck updateTruck = listofDriver.get(0);
	 * 
	 * //updateTruck.setMake(truck.getTruck_class());
	 * //updateTruck.setModel(truck.getStatus()); //
	 * if(truck.getCurrentOdometer() != null){ //
	 * updateTruck.setCurrentOdometer(truck.getCurrentOdometer()); // } //
	 * if(truck.getEquipmentType() != null){ //
	 * updateTruck.setEquipmentType(truck.getEquipmentType()); // } //
	 * if(truck.getJurisdiction() != null){ //
	 * updateTruck.setJurisdiction(truck.getJurisdiction()); // } //
	 * if(truck.getMake() != null){ // updateTruck.setMake(truck.getMake()); //
	 * } // if(truck.getModel() != null){ //
	 * updateTruck.setModel(truck.getModel()); // } // if(truck.getOwnerId() !=
	 * null){ // updateTruck.setOwnerId(truck.getOwnerId()); // } //
	 * if(truck.getPlateNo() != null){ //
	 * updateTruck.setPlateNo(truck.getPlateNo()); // } // if(truck.getRgw() !=
	 * null){ // updateTruck.setRgw(truck.getRgw()); // } //
	 * if(truck.getStatus() != null){ //
	 * updateTruck.setStatus(truck.getStatus()); // } //
	 * if(truck.getTareWeight() != null){ //
	 * updateTruck.setTareWeight(truck.getTareWeight()); // } // //
	 * if(truck.getTruckClass() != null){ //
	 * updateTruck.setTruckClass(truck.getTruckClass()); // } //
	 * if(truck.getTruckYear() != null){ //
	 * updateTruck.setTruckYear(truck.getTruckYear()); // } //
	 * if(truck.getUnitNo() != null){ //
	 * updateTruck.setUnitNo(truck.getUnitNo()); // } // if(truck.getVin() !=
	 * null){ // updateTruck.setVin(truck.getVin()); // }
	 * 
	 * 
	 * 
	 * // truck.setModifiedBy("sumit"); // truck.setModifiedOn(new Date());
	 * 
	 * // update Driver truckDao.update(truck);
	 * System.out.println("[updateDriver]: Truck updated Successfully.");
	 * logger.info("[updateDriver]: Truck updated Successfully."); return true;
	 * }
	 * 
	 * return false; }
	 * 
	 * @Override public boolean deleteTruck(Long id) {
	 * logger.info("[deleteTruck] :driverCode : " + id);
	 * System.out.println("id: "+id); try { Criterion deleteTruckCriteria =
	 * Restrictions.eq("truckId", id); List<Truck> truck = truckDao
	 * .find(deleteTruckCriteria);
	 * 
	 * if (truck != null) { Truck deleteTruck = truck.get(0);
	 * truckDao.delete(deleteTruck);
	 * logger.info("[deleteTruck] :Truck Deleted Successfully. : "); return
	 * true; } } catch (Exception e) { System.out.println(e);
	 * logger.error("[deleteTruck] :Exception : " + e); return false; } return
	 * false; }
	 * 
	 * @Override public List<Truck> getAllTruck() { List<Truck> listOfDriver =
	 * null; try { logger.info("[getAllTruck]:  Service : Enter");
	 * 
	 * listOfDriver = truckDao.findAll();
	 * logger.info("[getAllTruck]: Service: listOfDriver : " + listOfDriver);
	 * return listOfDriver; } catch (Exception e) {
	 * logger.error("[getAllTruck ] Service: Exception :" + e.getMessage()); }
	 * return listOfDriver; }
	 * 
	 * @Override public Truck getTruckById(Long id) {
	 * logger.info("[getTruckById]:  Service : Enter");
	 * System.out.println("Enter getById"); Criterion getTruckById=
	 * Restrictions.eqOrIsNull( "truckId", id); List<Truck> listOfDriver =
	 * truckDao .find(getTruckById);
	 * 
	 * if (listOfDriver != null) { Truck truck = listOfDriver.get(0);
	 * logger.info("[getTruckById]:  Driver details got succesfully. "); return
	 * truck; } return null; }
	 * 
	 * public boolean isTruckExist(){ boolean isExist = false; return isExist; }
	 */
	public static void main(String args[]) {
		System.out.println(new Date());
	}

}
