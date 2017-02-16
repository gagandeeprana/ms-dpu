package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CompanyDao;
import com.dpu.dao.ShipperDao;
import com.dpu.entity.Shipper;
import com.dpu.entity.Status;
import com.dpu.model.Failed;
import com.dpu.model.ShipperResponse;
import com.dpu.model.Success;
import com.dpu.service.CompanyService;
import com.dpu.service.ShipperService;
import com.dpu.service.StatusService;

@Component
public class ShipperServiceImpl implements ShipperService {

	Logger logger = Logger.getLogger(ShipperServiceImpl.class);
	@Autowired
	ShipperDao shipperDao;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	StatusService statusService;
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Object add(ShipperResponse shipperResponse) {
		Object obj = null;
		try {
			Shipper shipper = new Shipper();
			BeanUtils.copyProperties(shipperResponse, shipper);
			shipper.setStatus(statusService.get(shipperResponse.getStatusId()));
			shipperDao.save(shipper);
			obj = createSuccessObject("Shipper Added Successfully");
		} catch (Exception e) {
			obj = createFailedObject("Error while adding shipper");
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

	@Override
	public Object update(Long id, ShipperResponse shipperResponse) {
		
		Object obj = null;
		try{
			Shipper shipper = shipperDao.findById(id);
			if(shipper != null){
				String[] ignoreProp = new String[1];
				ignoreProp[0] = "shipperId";
				BeanUtils.copyProperties(shipperResponse, shipper, ignoreProp);
				shipper.setStatus(statusService.get(shipperResponse.getStatusId()));
				shipperDao.update(shipper);
				obj = createSuccessObject("Shipper Updated Successfully");
			}
		} catch(Exception e){
			logger.error("Exception inside ShipperServiceImpl update() :"+e.getMessage());
			obj = createFailedObject("Error while updating shipper");
		}
		return obj;
	}

	@Override
	public Object delete(Long  shipperId) {
		
		Object obj = null;
		try {
			Shipper shipper = shipperDao.findById(shipperId);
			if(shipper != null){
				shipperDao.delete(shipper);
			}
			 obj = createSuccessObject("Shipper deleted successfully");
		} catch (Exception e) {
			obj = createFailedObject("Error while deleting Shipper");
		}
		return obj;
	}

	@Override
	public List<ShipperResponse> getAll() {
		
		Session session = null;
		List<ShipperResponse> responses = new ArrayList<ShipperResponse>();
		try{
			session = sessionFactory.openSession();
			List<Shipper> shipperlist = shipperDao.findAll(session);
			
			if(shipperlist != null && ! shipperlist.isEmpty()){
				for (Shipper shipper : shipperlist) {
					ShipperResponse shipperResponse = new ShipperResponse();
					BeanUtils.copyProperties(shipper, shipperResponse);
					/*shipperResponse.setCompany(shipper.getCompany().getName());*/
					shipperResponse.setStatus(shipper.getStatus().getStatus());
					responses.add(shipperResponse);
				}
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return responses;
	}

	@Override
	public ShipperResponse get(Long id) {
		
		Session session = null;
		ShipperResponse response = new ShipperResponse();
		
		try{
			session = sessionFactory.openSession();
			Shipper shipper = shipperDao.findById(id,session);
			if(shipper != null){
				BeanUtils.copyProperties(shipper, response);
				/*	response.setCompanyId(shipper.getCompany().getCompanyId());*/
				response.setStatusId(shipper.getStatus().getId());
				/*response.setCompanyList(companyService.getCompanyData());*/
				List<Status> statusList = statusService.getAll();
				response.setStatusList(statusList);
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return response;
	}

	@Override
	public ShipperResponse getMasterData() {
		
		ShipperResponse response = new ShipperResponse();
		/*response.setCompanyList(companyService.getCompanyData());*/
		List<Status> statusList = statusService.getAll();
		response.setStatusList(statusList);
		return response;
	}

	@Override
	public List<ShipperResponse> getShipperByCompanyName(String companyName) {
		
		Session session = null;
		List<Shipper> shipperList = null;
		List<ShipperResponse> responses = new ArrayList<ShipperResponse>();
		try {
			session = sessionFactory.openSession();
			if(companyName != null && companyName.length() > 0) {
				shipperList = shipperDao.findByLoactionName(companyName, session);
			}
			
			if(shipperList != null && ! shipperList.isEmpty()){
				for (Shipper shipper : shipperList) {
					ShipperResponse shipperResponse = new ShipperResponse();
					BeanUtils.copyProperties(shipper, shipperResponse);
					/*shipperResponse.setCompany(shipper.getCompany().getName());*/
					shipperResponse.setStatus(shipper.getStatus().getStatus());
					responses.add(shipperResponse);
				}
			}
		} catch (Exception e) {

		} finally {
			if(session != null) {
				session.close();
			}
		}
		return responses;
	}

	@Override
	public List<ShipperResponse> getSpecificData() {
		List<Object[]> shipperData = shipperDao.getSpecificData("Shipper","shipperId","locationName");
		
		List<ShipperResponse> categories = new ArrayList<ShipperResponse>();
		if(shipperData != null && !shipperData.isEmpty()){
			for (Object[] row : shipperData) {
				ShipperResponse shipper = new ShipperResponse();
				shipper.setShipperId((Long) row[0]);
				shipper.setLocationName(String.valueOf(row[1]));
				categories.add(shipper);
			}
		}
		
		return categories;
	}
	
	

}
