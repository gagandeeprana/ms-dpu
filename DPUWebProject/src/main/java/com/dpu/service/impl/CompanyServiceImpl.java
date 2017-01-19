package com.dpu.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CompanyAdditionalContactsDao;
import com.dpu.dao.CompanyBillingLocationDao;
import com.dpu.dao.CompanyDao;
import com.dpu.entity.Company;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.model.AdditionalContacts;
import com.dpu.model.BillingLocation;
import com.dpu.model.CompanyResponse;
import com.dpu.service.CompanyAdditionalContactsService;
import com.dpu.service.CompanyBillingLocationService;
import com.dpu.service.CompanyService;

@Component
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	CompanyBillingLocationDao companyBillingLocationDao;
	
	@Autowired
	CompanyAdditionalContactsDao companyAdditionalContactsDao;
	
	@Autowired
	CompanyBillingLocationService companyBillingLocationService;
	
	@Autowired
	CompanyAdditionalContactsService companyAdditionalContactsService;
	
	@Override
	public Company addCompanyData(CompanyResponse companyResponse) {
		
		Company company = setCompanyValues(companyResponse);
		company = companyDao.save(company);
		List<BillingLocation> billingLocations = companyResponse.getBillingLocations();
		
		if(billingLocations != null && !billingLocations.isEmpty()){
			for (BillingLocation billingLocation : billingLocations) {
				CompanyBillingLocation comBillingLocation = setBillingData(billingLocation, company);
				companyBillingLocationDao.save(comBillingLocation);
			}
		}
		
		List<AdditionalContacts> additionalContacts = companyResponse.getAdditionalContacts();
		
		if(additionalContacts != null && !additionalContacts.isEmpty()){
			for (AdditionalContacts additionalContact : additionalContacts) {
				CompanyAdditionalContacts comAdditionalContact = setAdditionalContactData(additionalContact, company);
				companyAdditionalContactsDao.save(comAdditionalContact);
			}
		}
		return company;
	}
	
	private CompanyAdditionalContacts setAdditionalContactData( AdditionalContacts additionalContact, Company company) {

		CompanyAdditionalContacts companyAdditionalContact = new CompanyAdditionalContacts();
		companyAdditionalContact.setCellular(additionalContact.getCellular());
		companyAdditionalContact.setCompany(company);
		companyAdditionalContact.setCustomerName(additionalContact.getCustomerName());
		companyAdditionalContact.setEmail(additionalContact.getEmail());
		companyAdditionalContact.setExt(additionalContact.getExt());
		companyAdditionalContact.setFax(additionalContact.getFax());
		companyAdditionalContact.setPhone(additionalContact.getPhone());
		companyAdditionalContact.setPosition(additionalContact.getPosition());
		companyAdditionalContact.setPrefix(additionalContact.getPrefix());
		companyAdditionalContact.setStatus(additionalContact.getStatus());
		return companyAdditionalContact;
	}

	private CompanyBillingLocation setBillingData(BillingLocation billingLocation, Company company) {

		CompanyBillingLocation comBillingLocation = new CompanyBillingLocation();
		comBillingLocation.setAddress(billingLocation.getAddress());
		comBillingLocation.setArCDN(billingLocation.getArCDN());
		comBillingLocation.setArUS(billingLocation.getArUS());
		comBillingLocation.setCellular(billingLocation.getCellular());
		comBillingLocation.setCity(billingLocation.getCity());
		comBillingLocation.setCompany(company);
		comBillingLocation.setContact(billingLocation.getContact());
		comBillingLocation.setEmail(billingLocation.getEmail());
		comBillingLocation.setExt(billingLocation.getExt());
		comBillingLocation.setFax(billingLocation.getFax());
		comBillingLocation.setName(billingLocation.getName());
		comBillingLocation.setPhone(billingLocation.getPhone());
		comBillingLocation.setPosition(billingLocation.getPosition());
		comBillingLocation.setPrefix(billingLocation.getPrefix());
		comBillingLocation.setProvinceState(billingLocation.getProvinceState());
		comBillingLocation.setStatus(billingLocation.getStatus());
		comBillingLocation.setTollfree(billingLocation.getTollfree());
		comBillingLocation.setUnitNo(billingLocation.getUnitNo());
		comBillingLocation.setZip(billingLocation.getZip());
		return comBillingLocation;
	}

	private Company setCompanyValues(CompanyResponse companyResponse) {
		//logger.info("[setCompanyValues] : Enter");
		Company company = new Company();
		company.setName(companyResponse.getName());
		company.setContact(companyResponse.getContact());
		company.setAddress(companyResponse.getAddress());
		company.setPosition(companyResponse.getPosition());
		company.setUnitNo(companyResponse.getUnitNo());
		company.setPhone(companyResponse.getPhone());
		company.setExt(companyResponse.getExt());
		company.setCity(companyResponse.getCity());
		company.setFax(companyResponse.getFax());
		company.setCompanyPrefix(companyResponse.getCompanyPrefix());
		company.setProvinceState(companyResponse.getProvinceState());
		company.setZip(companyResponse.getZip());
		company.setAfterHours(companyResponse.getAfterHours());
		company.setEmail(companyResponse.getEmail());
		company.setTollfree(companyResponse.getTollfree());
		company.setWebsite(companyResponse.getWebsite());
		company.setCellular(companyResponse.getCellular());
		company.setPager(companyResponse.getPager());
		//logger.info("[setCompanyValues] : Exit");
		return company;
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
				setCompanyData(company,response);
				
			/*	List<CompanyBillingLocation> listCompanyBillingLocations = companyBillingLocationService.getAll(company.getCompanyId());
				
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
				}*/
				
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
			setCompanyData(company,response);
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
			
			List<CompanyAdditionalContacts> comAddContacts = companyAdditionalContactsService.getAll(id);
			
			if(comAddContacts != null && !comAddContacts.isEmpty()){
				List<AdditionalContacts> addContacts = new ArrayList<AdditionalContacts>();
				for (CompanyAdditionalContacts companyAdditionalContacts : comAddContacts) {
					AdditionalContacts addContact = new AdditionalContacts();
					try {
						BeanUtils.copyProperties(addContact, companyAdditionalContacts);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
					
					addContacts.add(addContact);
				}
				
				response.setAdditionalContacts(addContacts);
			}
		}
		
		return response;
	}

	private void setCompanyData(Company companyObj, CompanyResponse response) {
		
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
