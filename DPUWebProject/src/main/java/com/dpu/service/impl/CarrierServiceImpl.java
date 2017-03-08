package com.dpu.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.dao.CarrierAdditionalContactsDao;
import com.dpu.dao.CarrierDao;
import com.dpu.entity.Carrier;
import com.dpu.entity.CarrierAdditionalContact;
import com.dpu.model.CarrierAdditionalContactModel;
import com.dpu.model.CarrierModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CarrierAdditionalContactService;
import com.dpu.service.CarrierService;
import com.dpu.util.MessageProperties;

@Component
public class CarrierServiceImpl extends MessageProperties implements CarrierService {

	Logger logger = Logger.getLogger(CarrierServiceImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	CarrierDao carrierDao;

	@Autowired
	CarrierAdditionalContactService carrierAdditionalContactService;

	@Autowired
	CarrierAdditionalContactsDao carrierAdditionalContactsDao;

	@Value("${carrier_deleted_message}")
	private String carrier_deleted_message;

	@Value("${carrier_unable_to_delete_message}")
	private String carrier_unable_to_delete_message;

	@Value("${carrier_dependent_message}")
	private String carrier_dependent_message;
	
	@Value("${carrier_unable_to_update_message}")
	private String carrier_unable_to_update_message;
	
	@Value("${carrier_updated_message}")
	private String carrier_updated_message;

	@Override
	public List<CarrierModel> getAll() {
		List<Carrier> listOfCarrier = carrierDao.findAll();
		List<CarrierModel> returnResponse = new ArrayList<CarrierModel>();

		if (listOfCarrier != null && !listOfCarrier.isEmpty()) {
			for (Carrier carrier : listOfCarrier) {
				CarrierModel response = new CarrierModel();
				setCarrierData(carrier, response);
				returnResponse.add(response);
			}

		}

		return returnResponse;
	}

	private void setCarrierData(Carrier carrier, CarrierModel response) {

		response.setCarrierId(carrier.getCarrierId());
		response.setAddress(carrier.getAddress());
		response.setCellular(carrier.getCellular());
		response.setCity(carrier.getCity());
		response.setContact(carrier.getContact());
		response.setEmail(carrier.getEmail());
		response.setExt(carrier.getExt());
		response.setFax(carrier.getFax());
		response.setPhone(carrier.getPhone());
		response.setPosition(carrier.getPosition());
		response.setPrefix(carrier.getPrefix());
		response.setProvinceState(carrier.getProvinceState());
		response.setTollfree(carrier.getTollfree());
		response.setUnitNo(carrier.getUnitNo());
		response.setWebsite(carrier.getWebsite());
		response.setZip(carrier.getZip());

	}

	@Override
	public Object delete(Long carrierId) {
		logger.info("Inside CarrierServiceImpl delete() starts");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Carrier carrier = carrierDao.findById(carrierId, session);

			if (carrier != null) {

				List<CarrierAdditionalContact> carrierAddContacts = carrierAdditionalContactService.getAll(carrierId,
						session);
				if (carrierAddContacts != null && !carrierAddContacts.isEmpty()) {
					for (CarrierAdditionalContact companyAdditionalContacts : carrierAddContacts) {
						carrierAdditionalContactsDao.deleteAdditionalContact(companyAdditionalContacts, session);
					}
				}
				carrierDao.deleteCarrier(carrier, session);
			} else {
				return createFailedObject(carrier_unable_to_delete_message);
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(carrier_dependent_message);
			}
			return createFailedObject(carrier_unable_to_delete_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}
		return createSuccessObject(carrier_deleted_message);
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	private Object createSuccessObject(String message) {
		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAll());
		return success;
	}
	
	
	@Override
	public Object update(Long id, CarrierModel carrierResponse) {
		Carrier carrier = carrierDao.findById(id);
		Session session = null;
		Transaction tx = null;
		
		try{
			if(carrier != null){
				session = sessionFactory.openSession();
				tx = session.beginTransaction();
				
				carrierDao.updateData(carrier, carrierResponse,session);
			} else{
				return createFailedObject(carrier_unable_to_update_message);
			}
		} catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
			return createFailedObject(carrier_unable_to_update_message);
		} finally{
			if(tx != null){
				tx.commit();
			}
			if(session != null){
				session.close();
			}
		}
		
		return createSuccessObject(carrier_updated_message);
	}

	@Override
	public CarrierModel get(Long id) {
		Session session = null;
		CarrierModel carrierResponse = new CarrierModel();
		try{
			session = sessionFactory.openSession();
			Carrier carrier = carrierDao.findById(id, session);
			
			if (carrier != null) {
				setCarrierData(carrier,carrierResponse);
				 
				
				List<CarrierAdditionalContact> carrierAddContacts = carrierAdditionalContactService.getAll(id, session);
				
				if(carrierAddContacts != null && !carrierAddContacts.isEmpty()){
					List<CarrierAdditionalContactModel> addContacts = new ArrayList<CarrierAdditionalContactModel>();
					
					for (CarrierAdditionalContact carrierAdditionalContact : carrierAddContacts) {
						CarrierAdditionalContactModel addContact = new CarrierAdditionalContactModel();
						try {
							BeanUtils.copyProperties(addContact, carrierAdditionalContact);
							//addContact.setStatusId(companyAdditionalContacts.getStatus().getId());
						} catch (IllegalAccessException | InvocationTargetException e) {
							e.printStackTrace();
						}
						
						addContacts.add(addContact);
					}
					
					carrierResponse.setCarrierAdditionalContactModel(addContacts);
				}
				
				//List<Status> statusList = statusService.getAll();
				//response.setStatusList(statusList);
			}
		}finally {
			if (session != null) {
				session.close();
			}
		}
		
		return carrierResponse;
	}

	 

}
