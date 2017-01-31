/**
 * 
 */
package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CompanyDao;
import com.dpu.dao.ShipperDao;
import com.dpu.entity.Service;
import com.dpu.entity.Shipper;
import com.dpu.entity.Status;
import com.dpu.model.DPUService;
import com.dpu.model.ShipperResponse;
import com.dpu.service.CompanyService;
import com.dpu.service.ShipperService;
import com.dpu.service.StatusService;

/**
 * @author jagvir
 *
 */
@Component
public class ShipperServiceImpl implements ShipperService {

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
	public Shipper update(Shipper shipper) {
		return shipperDao.update(shipper);
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
	public Shipper get(int id) {
		return shipperDao.findById(id);
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
			//Criterion criterion = Restrictions.like("company.name", companyName, MatchMode.ANYWHERE);
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
