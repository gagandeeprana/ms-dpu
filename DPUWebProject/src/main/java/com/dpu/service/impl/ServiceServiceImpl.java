
package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.ServiceDao;
import com.dpu.entity.Service;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.CompanyResponse;
import com.dpu.model.DPUService;
import com.dpu.model.TypeResponse;
import com.dpu.service.ServiceService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

/**
 * @author jagvir
 *
 */
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
	
	@Override
	public List<DPUService> add(DPUService dpuService) {
		
		Service service = setServiceValues(dpuService);
		serviceDao.save(service);
		
		return getAll();
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
		
		List<Service> serviceList = null;
		List<DPUService> servicesList = new ArrayList<DPUService>();
		if(serviceName != null && serviceName.length() > 0) {
			Criterion criterion = Restrictions.like("serviceName", serviceName, MatchMode.ANYWHERE);
			serviceList = serviceDao.find(criterion);
		}
		
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
