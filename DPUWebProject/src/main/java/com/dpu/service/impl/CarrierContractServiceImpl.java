package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CarrierContractDao;
import com.dpu.entity.CarrierContract;
import com.dpu.entity.Driver;
import com.dpu.model.CarrierContractModel;
import com.dpu.service.CarrierContractService;

@Component
public class CarrierContractServiceImpl implements CarrierContractService {

	Logger logger = Logger.getLogger(CarrierContractServiceImpl.class);

	@Autowired
	CarrierContractDao carrierContractDao;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<CarrierContractModel> getAllCarrierContract() {

		logger.info("Inside CarrierContractServiceImpl getAllCarrierContract() starts ");
		Session session = null;
		List<CarrierContractModel> returnResponse = new ArrayList<CarrierContractModel>();

		try {
			session = sessionFactory.openSession();
			List<CarrierContract> listOfCarrierContract = carrierContractDao.findAllCarrierContract(session);

			if (listOfCarrierContract != null && !listOfCarrierContract.isEmpty()) {
				for (CarrierContract carrierContract : listOfCarrierContract) {
					CarrierContractModel response = new CarrierContractModel();
					setCarrierContractData(carrierContract, response);
					returnResponse.add(response);
				}

			}
		} catch (Exception e) {
			logger.error("Exception inside CarrierContractServiceImpl getAllCarrierContract() :" + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.info("Inside CarrierContractServiceImpl getAllCarrierContract() ends ");
		return returnResponse;
	}

	private void setCarrierContractData(CarrierContract carrierContract, CarrierContractModel response) {
		response.setContractNoId(carrierContract.getContractNoId());
		response.setArrangedWithName(carrierContract.getArrangedWith().getArrangedWith());
		response.setCargo(carrierContract.getCargo());
		response.setCarrierName(carrierContract.getCarrier().getName());
		response.setCarrierRat(carrierContract.getCarrierRat());
		response.setCategoryName(carrierContract.getCategory().getName());
		response.setCommodityName(carrierContract.getCommodity().getCommodityName());
		response.setContractNo(carrierContract.getContractNo());
		response.setContractNoId(carrierContract.getContractNoId());
		response.setContractRate(carrierContract.getContractRate());
		response.setCreatedBy(carrierContract.getCreatedBy());
		response.setCurrencyName(carrierContract.getCurrency().getCurrencyName());
		response.setDispatched(carrierContract.getDispatched());
		response.setDispatcherName(carrierContract.getDispatcher().getDispatcherName());
		response.setDivisionName(carrierContract.getDivision().getDivisionName());
		response.setdOTno(carrierContract.getdOTno());
		response.setDriverName(carrierContract.getDriver().getFirstName());
		response.setEquipmentName(carrierContract.getEquipment().getEquipmentName());
		response.setHours(carrierContract.getHours());
		response.setInsExpires(carrierContract.getInsExpires());
		response.setLiabity(carrierContract.getLiabity());
		response.setmCno(carrierContract.getmCno());
		response.setMiles(carrierContract.getMiles());
		response.setRoleName(carrierContract.getRole().getRoleName());
		response.setTransDoc(carrierContract.getTransDoc());

	}

	@Override
	public Object addCarrierContract(CarrierContractModel carrierContractModel) {
		logger.info("Inside CarrierContractServiceImpl addCarrierContract() starts");
		Object obj = null;
		/*try {
		
			CarrierContract carrierContract = new CarrierContract();
			BeanUtils.copyProperties(carrierContractModel, carrierContract);
			carrierContract.setCategory(categoryDao.findById(driverReq.getCategoryId()));
			carrierContract.setDivision(divisionDao.findById(driverReq.getDivisionId()));
			carrierContract.setTerminal(terminalDao.findById(driverReq.getTerminalId()));
			carrierContract.setRole(typeService.get(driverReq.getRoleId()));
			carrierContract.setDriverClass(typeService.get(driverReq.getDriverClassId()));
			carrierContract.setStatus(statusService.get(driverReq.getStatusId()));
			carrierContractDao.save(carrierContract);
			obj = createSuccessObject(CarrierContract_added_message);
		} catch (Exception e) {
			logger.error("Exception inside CarrierContractServiceImpl addCarrierContract() :"+ e.getMessage());
			obj = createFailedObject(CarrierContract_unable_to_add_message);
		}

		logger.info("Inside CarrierContractServiceImpl addCarrierContract() Ends");*/
		return obj;
	}

}
