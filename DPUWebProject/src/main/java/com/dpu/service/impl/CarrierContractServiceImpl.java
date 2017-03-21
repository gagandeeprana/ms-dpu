package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CarrierContractDao;
import com.dpu.entity.ArrangedWith;
import com.dpu.entity.CarrierContract;
import com.dpu.model.CarrierContractModel;
import com.dpu.service.CarrierContractService;

@Component
public class CarrierContractServiceImpl implements CarrierContractService {

	@Autowired
	CarrierContractDao carrierContractDao;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<CarrierContractModel> getAllCarrierContract() {


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
			e.printStackTrace();
		}
		if (session != null) {
			session.close();
		}
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

}
