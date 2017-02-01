package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CompanyDao;
import com.dpu.dao.ShipperDao;
import com.dpu.entity.Shipper;
import com.dpu.entity.Status;
import com.dpu.model.ShipperResponse;
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

	@Override
	public Object add(ShipperResponse shipperResponse) {
	
		Shipper shipper = new Shipper();
		BeanUtils.copyProperties(shipperResponse, shipper);
		shipper.setCompany(companyDao.findById(shipperResponse.getCompanyId()));
		shipper.setStatus(statusService.get(shipperResponse.getStatusId()));
		shipperDao.save(shipper);
		return getAll();
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
				shipper.setCompany(companyDao.findById(shipperResponse.getCompanyId()));
				shipper.setStatus(statusService.get(shipperResponse.getStatusId()));
				shipperDao.update(shipper);
				obj = getAll();
			}
		} catch(Exception e){
			logger.error("Exception inside ShipperServiceImpl update() :"+e.getMessage());
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
			 obj = getAll();
		} catch (Exception e) {
		}
		return obj;
	}

	@Override
	public List<ShipperResponse> getAll() {
		
		List<Shipper> shipperlist = shipperDao.findAll();
		List<ShipperResponse> responses = new ArrayList<ShipperResponse>();
		
		if(shipperlist != null && ! shipperlist.isEmpty()){
			for (Shipper shipper : shipperlist) {
				ShipperResponse shipperResponse = new ShipperResponse();
				BeanUtils.copyProperties(shipper, shipperResponse);
				shipperResponse.setCompany(shipper.getCompany().getName());
				shipperResponse.setStatus(shipper.getStatus().getStatus());
				responses.add(shipperResponse);
			}
		}
		
		return responses;
	}

	@Override
	public ShipperResponse get(Long id) {
		
		Shipper shipper = shipperDao.findById(id);
		ShipperResponse response = new ShipperResponse();
		if(shipper != null){
			BeanUtils.copyProperties(shipper, response);
			response.setCompanyId(shipper.getCompany().getCompanyId());
			response.setStatusId(shipper.getStatus().getId());
			response.setCompanyList(companyService.getCompanyData());
			List<Status> statusList = statusService.getAll();
			response.setStatusList(statusList);
		}
		
		return response;
	}

	@Override
	public ShipperResponse getMasterData() {
		
		ShipperResponse response = new ShipperResponse();
		response.setCompanyList(companyService.getCompanyData());
		List<Status> statusList = statusService.getAll();
		response.setStatusList(statusList);
		return response;
	}

	@Override
	public List<ShipperResponse> getShipperByCompanyName(String companyName) {
		
		List<Shipper> shipperList = null;
		List<ShipperResponse> responses = new ArrayList<ShipperResponse>();
		
		if(companyName != null && companyName.length() > 0) {
			shipperList = shipperDao.findByCompanyName(companyName);
		}
		
		if(shipperList != null && ! shipperList.isEmpty()){
			for (Shipper shipper : shipperList) {
				ShipperResponse shipperResponse = new ShipperResponse();
				BeanUtils.copyProperties(shipper, shipperResponse);
				shipperResponse.setCompany(shipper.getCompany().getName());
				shipperResponse.setStatus(shipper.getStatus().getStatus());
				responses.add(shipperResponse);
			}
		}
		
		return responses;
	}

}
