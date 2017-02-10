package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.ServiceDao;
import com.dpu.entity.Service;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.ServiceService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Component
public class ServiceServiceImpl implements ServiceService {
	@Autowired
	ServiceDao serviceDao;

	@Autowired
	StatusService statusService;
	
	@Autowired
	TypeService typeService;
	
	@Autowired
	SessionFactory sessionFactory;
	
	Logger logger = Logger.getLogger(ServiceServiceImpl.class);
	@Override
	public Object add(DPUService dpuService) {
		
		Object obj = null;
		String message = "Record Added Successfully";
		try {
			Service service = setServiceValues(dpuService);
			serviceDao.save(service);
			obj = createSuccessObject(message);
		} catch(Exception e){
			logger.error("Exception inside DriverServiceImpl addDriver() :"+e.getMessage());
			message = "Error while inserting record";
			obj = createFailedObject(message);
		}
		
		return obj;
		
	}

	private Object createSuccessObject(String message) {
		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAll());
		return success;
	}
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}
	private Service setServiceValues(DPUService dpuService) {
		Service service  = new Service();
		service.setServiceName(dpuService.getServiceName());
		Status status = statusService.get(dpuService.getStatusId());
		Type textField = typeService.get(dpuService.getTextFieldId());
		service.setTextField(textField);
		Type associateWith = typeService.get(dpuService.getAssociationWithId());
		service.setAssociationWith(associateWith);
		service.setStatus(status);
		return service;
	}
	
	@Override
	public List<DPUService> update(Long id, DPUService dpuService) {
		//Service service  = new Service();
		Service service = serviceDao.findById(id);
		//service.setServiceId(id);
		service.setServiceName(dpuService.getServiceName());
		Status status = statusService.get(dpuService.getStatusId());
		Type textField = typeService.get(dpuService.getTextFieldId());
		service.setTextField(textField);
		Type associateWith = typeService.get(dpuService.getAssociationWithId());
		service.setAssociationWith(associateWith);
		service.setStatus(status);
		serviceDao.update(service);
		return getAll();
	}

	@Override
	public List<DPUService> delete(Long id) {
		List<DPUService> response = null;
		
		Service service = serviceDao.findById(id);
		try {
			if(service != null){
				serviceDao.delete(service);
			}
			response = getAll();
		} catch (Exception e) {
			response = null;
		}
		return response;
	}

	@Override
	public List<DPUService> getAll() {
		
		Session session = null;
		List<DPUService> servicesList = new ArrayList<DPUService>();
		
		try{
			
			session = sessionFactory.openSession();
			List<Service> serviceList = serviceDao.findAll(session);
			
			if(serviceList != null && !serviceList.isEmpty()){
				for (Service service : serviceList) {
					DPUService serviceObj = new DPUService();
					serviceObj.setAssociationWith(service.getAssociationWith().getTypeName());
					serviceObj.setServiceName(service.getServiceName());
					serviceObj.setServiceId(service.getServiceId());
					serviceObj.setStatus(service.getStatus().getStatus());
					serviceObj.setTextField(service.getTextField().getTypeName());
					servicesList.add(serviceObj);
				}
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return servicesList;
	}

	@Override
	public DPUService get(Long id) {
		
		Session session = null; 
		DPUService dpuService = new DPUService();
		
		try{
			
			session = sessionFactory.openSession();
			Service service = serviceDao.findById(id, session);
			
			if(service != null){
				dpuService.setServiceId(service.getServiceId());
				dpuService.setTextFieldId(service.getTextField().getTypeId());
				dpuService.setStatusId(service.getStatus().getId());
				dpuService.setAssociationWithId(service.getAssociationWith().getTypeId());
				dpuService.setServiceName(service.getServiceName());
				List<Status> statusList = statusService.getAll();
				dpuService.setStatusList(statusList);
				
				List<TypeResponse> textFieldList = typeService.getAll(2l);
				dpuService.setTextFieldList(textFieldList);
				
				List<TypeResponse> associatedWithList = typeService.getAll(3l);
				dpuService.setAssociatedWithList(associatedWithList);
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return dpuService;
	}

	@Override
	public DPUService getOpenAdd() {

		DPUService service = new DPUService();
		
		List<Status> statusList = statusService.getAll();
		service.setStatusList(statusList);
		
		List<TypeResponse> textFieldList = typeService.getAll(2l);
		service.setTextFieldList(textFieldList);
		
		List<TypeResponse> associatedWithList = typeService.getAll(3l);
		service.setAssociatedWithList(associatedWithList);
		
		return service;
	}

	@Override
	public List<DPUService> getServiceByServiceName(String serviceName) {
		
		Session session = null;
		List<DPUService> servicesList = new ArrayList<DPUService>();
		
		try{
			session = sessionFactory.openSession();
			List<Service> serviceList = serviceDao.getServiceByServiceName(session, serviceName);
			if(serviceList != null && !serviceList.isEmpty()){
				for (Service service : serviceList) {
					DPUService serviceObj = new DPUService();
					serviceObj.setAssociationWith(service.getAssociationWith().getTypeName());
					serviceObj.setServiceName(service.getServiceName());
					serviceObj.setServiceId(service.getServiceId());
					serviceObj.setStatus(service.getStatus().getStatus());
					serviceObj.setTextField(service.getTextField().getTypeName());
					servicesList.add(serviceObj);
				}
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return servicesList;
	}

	@Override
	public List<DPUService> getServiceData() {
		
		List<Object[]> serviceData = serviceDao.getServiceData();
		List<DPUService> returnServ = new ArrayList<DPUService>();
		
		if(serviceData != null && !serviceData.isEmpty()){
			for (Object[] row : serviceData) {
				DPUService res = new DPUService();
				res.setServiceId(Long.valueOf(String.valueOf(row[0])));
				res.setServiceName(String.valueOf(row[1]));
				returnServ.add(res);
			}
		}
		
		return returnServ;
	}
}
