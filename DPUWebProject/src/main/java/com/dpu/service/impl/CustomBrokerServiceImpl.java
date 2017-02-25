package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.common.CommonProperties;
import com.dpu.dao.CustomBrokerDao;
import com.dpu.entity.CustomBroker;
import com.dpu.entity.Status;
import com.dpu.model.CustomBrokerResponse;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CustomBrokerService;
import com.dpu.service.StatusService;
@Component
public class CustomBrokerServiceImpl implements CustomBrokerService {
	@Autowired
	CustomBrokerDao customBrokerDao;

	@Autowired
	StatusService statusService;

	@Autowired
	SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(CustomBrokerServiceImpl.class);

	private Object createSuccessObject(String msg, long code) {
		Success success = new Success();
		success.setCode(code);
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg, long code) {
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object add(CustomBrokerResponse customBrokerReponse) {
		CustomBroker customBroker = null;
		try {
			customBroker = setCustomBrokerValues(customBrokerReponse);
			customBrokerDao.save(customBroker);

		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerServiceImpl add() :"
					+ e.getMessage());
			return createFailedObject(
					CommonProperties.custombroker_unable_to_add_message,
					Long.parseLong(CommonProperties.custombroker_unable_to_add_code));
		}

		return createSuccessObject(CommonProperties.custombroker_added_message,
				Long.parseLong(CommonProperties.custombroker_added_code));
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	private CustomBroker setCustomBrokerValues(CustomBrokerResponse customBrokerReponse) {
		CustomBroker customBroker = new CustomBroker();
		customBroker.setCustomBrokerName(customBrokerReponse.getCustomBrokerName());
		customBroker.setContactName(customBrokerReponse.getContactName());
		customBroker.setPhone(customBrokerReponse.getPhone());
		customBroker.setExtention(customBrokerReponse.getExtention());
		customBroker.setFaxNumber(customBrokerReponse.getFaxNumber());
		Status status = statusService.get(customBrokerReponse.getStatusId());
		customBroker.setStatus(status);
		customBroker.setEmail(customBrokerReponse.getEmail());
		customBroker.setWebsite(customBrokerReponse.getWebsite());
		return customBroker;
	}

	@Override
	public Object update(Long id, CustomBrokerResponse customBrokerReponse) {
		logger.info("[CustomBrokerServiceImpl] [update] : Enter ");
	
		try {
			CustomBroker customBroker= setCustomBrokerValues(customBrokerReponse);
			customBroker.setCustomBrokerId(id);
			customBrokerDao.update(customBroker);
			
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerServiceImpl update() :"
					+ e.getMessage());
			return createFailedObject(
					CommonProperties.custombroker_unable_to_update_message,
					Long.parseLong(CommonProperties.custombroker_unable_to_update_code));
		}
		logger.info("[CustomBrokerServiceImpl] [update] : Exit ");
		return createSuccessObject(CommonProperties.custombroker_updated_message,
				Long.parseLong(CommonProperties.custombroker_unable_to_update_code));
	}

	@Override
	public Object delete(Long id) {
		logger.info("[CustomBrokerServiceImpl] [delete] : Enter ");
		CustomBroker customBroker = null;

		try {
			customBroker = customBrokerDao.findById(id);
			customBrokerDao.delete(customBroker);

		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerServiceImpl delete() :"
					+ e.getMessage());
			return createFailedObject(
					CommonProperties.custombroker_unable_to_delete_message,
					Long.parseLong(CommonProperties.custombroker_unable_to_delete_code));
		}
		logger.info("[CustomBrokerServiceImpl] [delete] : Service :  Exit");
		return createSuccessObject(CommonProperties.custombroker_deleted_message,
				Long.parseLong(CommonProperties.custombroker_deleted_code));

	}

	@Override
	public List<CustomBrokerResponse> getAll() {

		Session session = null;
		List<CustomBrokerResponse> customBrokerResponseList = new ArrayList<CustomBrokerResponse>();

		try {

			session = sessionFactory.openSession();
			List<CustomBroker> customBrokerList = customBrokerDao.findAll(null,session);

			if (customBrokerList != null && !customBrokerList.isEmpty()) {
				for (CustomBroker customBroker : customBrokerList) {
					CustomBrokerResponse customBrokerResponseObj = new CustomBrokerResponse();
					customBrokerResponseObj.setCustomBrokerId(customBroker.getCustomBrokerId());
					customBrokerResponseObj.setCustomBrokerName(customBroker.getCustomBrokerName());
					customBrokerResponseObj.setContactName(customBroker.getContactName());
					customBrokerResponseObj.setPhone(customBroker.getPhone());
					customBrokerResponseObj.setExtention(customBroker.getExtention());
					customBrokerResponseObj.setFaxNumber(customBroker.getFaxNumber());
					//customBrokerResponseObj.setStatus(customBroker.getStatus().getStatus());
					customBrokerResponseObj.setEmail(customBroker.getEmail());
					customBrokerResponseObj.setWebsite(customBroker.getWebsite());
					customBrokerResponseList.add(customBrokerResponseObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return customBrokerResponseList;
	}

	@Override
	public CustomBrokerResponse get(Long id) {

		Session session = null;
		CustomBrokerResponse customBrokerResponseObj = new CustomBrokerResponse();

		try {

			session = sessionFactory.openSession();
			CustomBroker customBroker= customBrokerDao.findById(id, session);

			if (customBroker != null) {
				customBrokerResponseObj.setCustomBrokerId(customBroker.getCustomBrokerId());
				customBrokerResponseObj.setCustomBrokerName(customBroker.getCustomBrokerName());
				customBrokerResponseObj.setContactName(customBroker.getContactName());
				customBrokerResponseObj.setPhone(customBroker.getPhone());
				customBrokerResponseObj.setExtention(customBroker.getExtention());
				customBrokerResponseObj.setFaxNumber(customBroker.getFaxNumber());
				//customBrokerResponseObj.setStatus(customBroker.getStatus().getStatus());
				customBrokerResponseObj.setEmail(customBroker.getEmail());
				customBrokerResponseObj.setWebsite(customBroker.getWebsite());
				
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return customBrokerResponseObj;
	}

	@Override
	public CustomBrokerResponse getOpenAdd() {

		CustomBrokerResponse customBrokerResponse = new CustomBrokerResponse();

		List<Status> statusList = statusService.getAll();
		customBrokerResponse.setStatusList(statusList);
	
		return customBrokerResponse;
	}

	@Override
	public List<CustomBrokerResponse> getCustomBrokerByCustomBrokerName(String customBrokerName) {

		Session session = null;
		List<CustomBrokerResponse> customBrokerResponseList = new ArrayList<CustomBrokerResponse>();
		List<CustomBroker> customBrokerList =null;
		try {
			session = sessionFactory.openSession();
			if(customBrokerName != null && customBrokerName.length() > 0) {
				customBrokerList = customBrokerDao.findAll(customBrokerName, session);
			}
			
			if (customBrokerList != null && !customBrokerList.isEmpty()) {
				for (CustomBroker customBroker : customBrokerList) {
					CustomBrokerResponse customBrokerResponseObj = new CustomBrokerResponse();
					customBrokerResponseObj.setCustomBrokerId(customBroker.getCustomBrokerId());
					customBrokerResponseObj.setCustomBrokerName(customBroker.getCustomBrokerName());
					customBrokerResponseObj.setContactName(customBroker.getContactName());
					customBrokerResponseObj.setPhone(customBroker.getPhone());
					customBrokerResponseObj.setExtention(customBroker.getExtention());
					customBrokerResponseObj.setFaxNumber(customBroker.getFaxNumber());
					//customBrokerResponseObj.setStatus(customBroker.getStatus().getStatus());
					customBrokerResponseObj.setEmail(customBroker.getEmail());
					customBrokerResponseObj.setWebsite(customBroker.getWebsite());
					customBrokerResponseList.add(customBrokerResponseObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return customBrokerResponseList;
	}

}
