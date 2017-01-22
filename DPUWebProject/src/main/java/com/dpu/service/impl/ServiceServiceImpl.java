
package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.ServiceDao;
import com.dpu.entity.Equipment;
import com.dpu.entity.Service;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
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
	public boolean delete(Service service) {
		boolean result = false;
		try {
			serviceDao.delete(service);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<DPUService> getAll() {
		
		List<Service> serviceList = serviceDao.findAll();
		List<DPUService> servicesList = new ArrayList<DPUService>();
		
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
	public DPUService get(Long id) {
		
		DPUService dpuService = new DPUService();
		Service service = serviceDao.findById(id);
		
		if(service != null){
			dpuService.setServiceId(service.getServiceId());
			dpuService.setTextFieldId(service.getTextField().getTypeId());
			dpuService.setStatusId(service.getStatus().getId());
			dpuService.setAssociationWithId(service.getAssociationWith().getTypeId());
			
			List<Status> statusList = statusService.getAll();
			dpuService.setStatusList(statusList);
			
			List<TypeResponse> textFieldList = typeService.getAll(2l);
			dpuService.setTextFieldList(textFieldList);
			
			List<TypeResponse> associatedWithList = typeService.getAll(3l);
			dpuService.setAssociatedWithList(associatedWithList);
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

}
