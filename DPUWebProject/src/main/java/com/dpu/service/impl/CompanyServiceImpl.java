package com.dpu.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CompanyDao;
import com.dpu.entity.Company;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.model.BillingLocation;
import com.dpu.model.CompanyResponse;
import com.dpu.service.CompanyBillingLocationService;
import com.dpu.service.CompanyService;

@Component
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	CompanyBillingLocationService companyBillingLocationService;
	
	@Override
	public Company add(Company company) {
		return companyDao.save(company);
	}

	@Override
	public Company update(Company company) {
		return companyDao.update(company);
	}

	@Override
	public boolean delete(Company company) {
		boolean result = false;
		try {
			companyDao.delete(company);
			
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<CompanyResponse> getAll() {
		
		List<Company> companies = companyDao.findAll();
		List<CompanyResponse> returnResponse = new ArrayList<CompanyResponse>();
		
		if(companies != null && ! companies.isEmpty()){
			for (Company company : companies) {
				CompanyResponse response = new CompanyResponse();
				SetCompanyData(company,response);
				
				List<CompanyBillingLocation> listCompanyBillingLocations = companyBillingLocationService.getAll(company.getCompanyId());
				
				if(listCompanyBillingLocations != null && !listCompanyBillingLocations.isEmpty()){
					List<BillingLocation> billingLocations = new ArrayList<BillingLocation>();
					for (CompanyBillingLocation companyBillingLocation : listCompanyBillingLocations) {
						BillingLocation location = new BillingLocation();
						try {
							BeanUtils.copyProperties(location, companyBillingLocation);
						} catch (IllegalAccessException | InvocationTargetException e) {
							e.printStackTrace();
						}
						billingLocations.add(location);
					}
					response.setBillingLocations(billingLocations);
				}
				
				returnResponse.add(response);
			}
			
		}
		
		return returnResponse;
	}

	@Override
	public CompanyResponse get(int id) {
		Company company = companyDao.findById(id);
		CompanyResponse response = new CompanyResponse();
		if (company != null) {
			SetCompanyData(company,response);
			//BeanUtils.copyProperties(response, company);
			List<CompanyBillingLocation> listCompanyBillingLocations = companyBillingLocationService.getAll(id);
			
			if(listCompanyBillingLocations != null && !listCompanyBillingLocations.isEmpty()){
				List<BillingLocation> billingLocations = new ArrayList<BillingLocation>();
				for (CompanyBillingLocation companyBillingLocation : listCompanyBillingLocations) {
					BillingLocation location = new BillingLocation();
					try {
						BeanUtils.copyProperties(location, companyBillingLocation);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
					billingLocations.add(location);
				}
				response.setBillingLocations(billingLocations);
			}
		}
		
		return response;
	}

	private void SetCompanyData(Company companyObj, CompanyResponse response) {
		
		response.setAddress(companyObj.getAddress());
		response.setAfterHours(companyObj.getAfterHours());
		response.setCellular(companyObj.getCellular());
		response.setCity(companyObj.getCity());
		response.setCompanyPrefix(companyObj.getCompanyPrefix());
		response.setContact(companyObj.getContact());
		response.setCustomerNotes(companyObj.getCustomerNotes());
		response.setEmail(companyObj.getEmail());
		response.setExt(companyObj.getExt());
		response.setFax(companyObj.getFax());
		response.setName(companyObj.getName());
		response.setPager(companyObj.getPager());
		response.setPhone(companyObj.getPhone());
		response.setPosition(companyObj.getPosition());
		response.setProvinceState(companyObj.getProvinceState());
		response.setTollfree(companyObj.getTollfree());
		response.setZip(companyObj.getZip());
		response.setUnitNo(companyObj.getUnitNo());
		response.setWebsite(companyObj.getWebsite());
		
	}
}
