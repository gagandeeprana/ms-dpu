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

import com.dpu.dao.CompanyAdditionalContactsDao;
import com.dpu.dao.CompanyBillingLocationDao;
import com.dpu.dao.CompanyDao;
import com.dpu.dao.VendorDao;
import com.dpu.entity.Company;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.entity.Status;
import com.dpu.entity.Vendor;
import com.dpu.entity.VendorBillingLocation;
import com.dpu.entity.VendorContacts;
import com.dpu.model.AdditionalContacts;
import com.dpu.model.BillingLocation;
import com.dpu.model.CompanyResponse;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.VendorAdditionalContactsModel;
import com.dpu.model.VendorBillingLocationModel;
import com.dpu.model.VendorModel;
import com.dpu.service.CompanyAdditionalContactsService;
import com.dpu.service.CompanyBillingLocationService;
import com.dpu.service.CompanyService;
import com.dpu.service.StatusService;
import com.dpu.service.VendorService;

@Component
public class VendorServiceImpl implements VendorService{

	@Autowired
	VendorDao vendorDao;
	
	@Autowired
	CompanyBillingLocationDao companyBillingLocationDao;
	
	@Autowired
	CompanyAdditionalContactsDao companyAdditionalContactsDao;
	
	@Autowired
	CompanyBillingLocationService companyBillingLocationService;
	
	@Autowired
	CompanyAdditionalContactsService companyAdditionalContactsService;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	StatusService statusService;
	
	Logger logger = Logger.getLogger(VendorServiceImpl.class);
	
	@Value("${company_added_message}")
	private String company_added_message;
	
	@Value("${company_unable_to_add_message}")
	private String company_unable_to_add_message;
	
	@Value("${company_deleted_message}")
	private String company_deleted_message;
	
	@Value("${company_unable_to_delete_message}")
	private String company_unable_to_delete_message;
	
	@Value("${company_updated_message}")
	private String company_updated_message;
	
	@Value("${company_unable_to_update_message}")
	private String company_unable_to_update_message;
	
	@Value("${company_dependent_message}")
	private String company_dependent_message;
	
	@Override
	public Object addVendorData(VendorModel vendorModel) {
		
		logger.info("Inside VendorServiceImpl addVendorData() starts");
		Session session = null;
		Transaction tx = null;
		
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Vendor vendor = setVendorValues(vendorModel);
			vendor = vendorDao.insertVendorData(vendor,session);
			
			List<VendorBillingLocationModel> billingLocations = vendorModel.getBillingLocations();
			if(billingLocations != null && !billingLocations.isEmpty()){
				for (VendorBillingLocationModel billingLocation : billingLocations) {
					VendorBillingLocation comBillingLocation = setBillingData(billingLocation, vendor);
					vendorDao.insertBillingLocation(comBillingLocation, session);
				}
			}
			
			List<VendorAdditionalContactsModel> additionalContacts = vendorModel.getAdditionalContacts();
			if(additionalContacts != null && !additionalContacts.isEmpty()){
				for (VendorAdditionalContactsModel additionalContact : additionalContacts) {
					VendorContacts comAdditionalContact = setAdditionalContactData(additionalContact, vendor);
					vendorDao.insertAdditionalContacts(comAdditionalContact, session);
				}
			}
			
		} catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
			
			logger.error("Exception inside VendorServiceImpl addVendorData() :"+ e.getMessage());
			return createFailedObject(company_unable_to_add_message);
		} finally{
			if(tx != null){
				tx.commit();
			}
			if(session != null){
				session.close();
			}
		}
		
		logger.info("Inside VendorServiceImpl addVendorData() ends");
		return createSuccessObject(company_added_message);
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
	
	private VendorContacts setAdditionalContactData( VendorAdditionalContactsModel additionalContact, Vendor vendor) {

		VendorContacts vendorContact = new VendorContacts();
		vendorContact.setCellular(additionalContact.getCellular());
		vendorContact.setVendor(vendor);
		vendorContact.setVendorName(additionalContact.getVendorName());
		vendorContact.setEmail(additionalContact.getEmail());
		vendorContact.setExt(additionalContact.getExt());
		vendorContact.setFax(additionalContact.getFax());
		vendorContact.setPhone(additionalContact.getPhone());
		vendorContact.setPosition(additionalContact.getPosition());
		vendorContact.setPrefix(additionalContact.getPrefix());
		vendorContact.setStatus(statusService.get(additionalContact.getStatusId()));
		return vendorContact;
	}

	private VendorBillingLocation setBillingData(VendorBillingLocationModel billingLocation, Vendor vendor) {

		VendorBillingLocation vendorBillingLocation = new VendorBillingLocation();
		vendorBillingLocation.setAddress(billingLocation.getAddress());
		vendorBillingLocation.setArCDN(billingLocation.getArCDN());
		vendorBillingLocation.setArUS(billingLocation.getArUS());
		vendorBillingLocation.setCellular(billingLocation.getCellular());
		vendorBillingLocation.setCity(billingLocation.getCity());
		vendorBillingLocation.setVendor(vendor);
		vendorBillingLocation.setContact(billingLocation.getContact());
		vendorBillingLocation.setEmail(billingLocation.getEmail());
		vendorBillingLocation.setExt(billingLocation.getExt());
		vendorBillingLocation.setFax(billingLocation.getFax());
		vendorBillingLocation.setName(billingLocation.getName());
		vendorBillingLocation.setPhone(billingLocation.getPhone());
		vendorBillingLocation.setPosition(billingLocation.getPosition());
		vendorBillingLocation.setPrefix(billingLocation.getPrefix());
		vendorBillingLocation.setProvinceState(billingLocation.getProvinceState());
		vendorBillingLocation.setStatus(statusService.get(billingLocation.getStatusId()));
		vendorBillingLocation.setTollfree(billingLocation.getTollfree());
		vendorBillingLocation.setUnitNo(billingLocation.getUnitNo());
		vendorBillingLocation.setZip(billingLocation.getZip());
		return vendorBillingLocation;
	}

	private Vendor setVendorValues(VendorModel vendorModel) {
		Vendor vendor = new Vendor();
		vendor.setName(vendorModel.getName());
		vendor.setContact(vendorModel.getContact());
		vendor.setAddress(vendorModel.getAddress());
		vendor.setPosition(vendorModel.getPosition());
		vendor.setUnitNo(vendorModel.getUnitNo());
		vendor.setPhone(vendorModel.getPhone());
		vendor.setExt(vendorModel.getExt());
		vendor.setCity(vendorModel.getCity());
		vendor.setFax(vendorModel.getFax());
		vendor.setVendorPrefix(vendorModel.getVendorPrefix());
		vendor.setProvinceState(vendorModel.getProvinceState());
		vendor.setZip(vendorModel.getZip());
		vendor.setAfterHours(vendorModel.getAfterHours());
		vendor.setEmail(vendorModel.getEmail());
		vendor.setTollfree(vendorModel.getTollfree());
		vendor.setWebsite(vendorModel.getWebsite());
		vendor.setCellular(vendorModel.getCellular());
		vendor.setPager(vendorModel.getPager());
		return vendor;
	}

	@Override
	public Vendor update(Vendor vendor) {
		return vendorDao.update(vendor);
	}

	@Override
	public Object delete(Long vendorId) {
			
		logger.info("Inside CompanyServiceImpl addCompanyData() starts");
		Session session = null;
		Transaction tx = null;
		
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Vendor vendor = vendorDao.findById(vendorId, session);
			
			if(vendor != null){
				
				List<CompanyBillingLocation> listCompanyBillingLocations = companyBillingLocationService.getAll(vendorId, session);
				if(listCompanyBillingLocations != null && !listCompanyBillingLocations.isEmpty()){
					for (CompanyBillingLocation companyBillingLocation : listCompanyBillingLocations) {
						companyBillingLocationDao.deleteBillingLocation(companyBillingLocation,session);
					}
				}
				
				List<CompanyAdditionalContacts> comAddContacts = companyAdditionalContactsService.getAll(vendorId, session);
				if(comAddContacts != null && !comAddContacts.isEmpty()){
					for (CompanyAdditionalContacts companyAdditionalContacts : comAddContacts) {
						companyAdditionalContactsDao.deleteAdditionalContact(companyAdditionalContacts, session);
					}
				}
				vendorDao.deleteVendor(vendor, session);
			} else{
				return createFailedObject(company_unable_to_delete_message);
			}
		} catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				return createFailedObject(company_dependent_message);
			}
			return createFailedObject(company_unable_to_delete_message);
		} finally{
			if(tx != null){
				tx.commit();
			}
			if(session != null){
				session.close();
			}
		}
		return createSuccessObject(company_deleted_message);
	}

	@Override
	public List<VendorModel> getAll() {
		
		List<Vendor> vendors = vendorDao.findAll();
		List<VendorModel> returnResponse = new ArrayList<VendorModel>();
		
		if(vendors != null && ! vendors.isEmpty()){
			for (Vendor vendor : vendors) {
				VendorModel response = new VendorModel();
				setVendorData(vendor,response);
				
		
				returnResponse.add(response);
			}
			
		}
		
		return returnResponse;
	}

	@Override
	public VendorModel get(Long id) {
		
		Session session = null;
		VendorModel response = new VendorModel();
		try{
			session = sessionFactory.openSession();
			Vendor vendor = vendorDao.findById(id, session);
			
			if (vendor != null) {
				setVendorData(vendor,response);
				//BeanUtils.copyProperties(response, company);
				List<VendorBillingLocation> listCompanyBillingLocations = vendor.getBillingLocations();
				
				if(listCompanyBillingLocations != null && !listCompanyBillingLocations.isEmpty()){
					List<VendorBillingLocationModel> billingLocations = new ArrayList<VendorBillingLocationModel>();
					for (VendorBillingLocation vendorBillingLocation : listCompanyBillingLocations) {
						VendorBillingLocationModel location = new VendorBillingLocationModel();
							org.springframework.beans.BeanUtils.copyProperties(vendorBillingLocation, location);
							//BeanUtils.copyProperties(location, vendorBillingLocation);
							location.setStatusId(vendorBillingLocation.getStatus().getId());
						billingLocations.add(location);
					}
					response.setBillingLocations(billingLocations);
				}
				
				List<VendorContacts> comAddContacts = vendor.getAdditionalContacts();
				
				if(comAddContacts != null && !comAddContacts.isEmpty()){
					List<VendorAdditionalContactsModel> addContacts = new ArrayList<VendorAdditionalContactsModel>();
					for (VendorContacts vendorContacts : comAddContacts) {
						VendorAdditionalContactsModel addContact = new VendorAdditionalContactsModel();
							org.springframework.beans.BeanUtils.copyProperties(vendorContacts, addContact);
							addContact.setStatusId(vendorContacts.getStatus().getId());
						
						addContacts.add(addContact);
					}
					
					response.setAdditionalContacts(addContacts);
				}
				
				List<Status> statusList = statusService.getAll();
				response.setStatusList(statusList);
			}
		}finally {
			if (session != null) {
				session.close();
			}
		}
		
		return response;
	}

	private void setVendorData(Vendor vendor, VendorModel response) {
		
		response.setVendorId(vendor.getVendorId());
		response.setAddress(vendor.getAddress());
		response.setAfterHours(vendor.getAfterHours());
		response.setCellular(vendor.getCellular());
		response.setCity(vendor.getCity());
		response.setVendorPrefix(vendor.getVendorPrefix());
		response.setContact(vendor.getContact());
		response.setVendorNotes(vendor.getVendorNotes());
		response.setEmail(vendor.getEmail());
		response.setExt(vendor.getExt());
		response.setFax(vendor.getFax());
		response.setName(vendor.getName());
		response.setPager(vendor.getPager());
		response.setPhone(vendor.getPhone());
		response.setPosition(vendor.getPosition());
		response.setProvinceState(vendor.getProvinceState());
		response.setTollfree(vendor.getTollfree());
		response.setZip(vendor.getZip());
		response.setUnitNo(vendor.getUnitNo());
		response.setWebsite(vendor.getWebsite());
		
	}

	@Override
	public List<VendorModel> getCompanyData() {
		
		List<Object[]> vendorData = vendorDao.getVendorData();
		List<VendorModel> returnRes = new ArrayList<VendorModel>();
		
		if(vendorData != null && !vendorData.isEmpty()){
			for (Object[] row : vendorData) {
				VendorModel res = new VendorModel();
				res.setVendorId(Long.valueOf(String.valueOf(row[0])));
				res.setName(String.valueOf(row[1]));
				returnRes.add(res);
			}
		}
		
		return returnRes;
	}

	@Override
	public Object update(Long id, CompanyResponse companyResponse) {

		/*Company company = companyDao.findById(id);
		Session session = null;
		Transaction tx = null;
		
		try{
			if(company != null){
				session = sessionFactory.openSession();
				tx = session.beginTransaction();
				
				companyDao.updateData(company, companyResponse,session);
			} else{
				return createFailedObject(company_unable_to_update_message);
			}
		} catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
			return createFailedObject(company_unable_to_update_message);
		} finally{
			if(tx != null){
				tx.commit();
			}
			if(session != null){
				session.close();
			}
		}*/
		
		return createSuccessObject(company_updated_message);
	}

	@Override
	public VendorModel getOpenAdd() {
		
		VendorModel vendorModel = new VendorModel();
		
		List<Status> statusList = statusService.getAll();
		vendorModel.setStatusList(statusList);
		
		return vendorModel;
	}

	@Override
	public CompanyResponse getCompanyBillingLocationAndContacts(Long companyId) {
		CompanyResponse companyResponse = new CompanyResponse();
		Session session = null;
		
	/*	try{
			Company company = companyDao.findById(companyId);
			if(company != null){
				session = sessionFactory.openSession();
				List<Object[]> billingLocationData = companyDao.getBillingLocations(company.getCompanyId(), session);
				if(billingLocationData != null && !billingLocationData.isEmpty()){
					List<BillingLocation> billingLocations = new ArrayList<BillingLocation>();
					for (Object[] row : billingLocationData) {
						BillingLocation billingLocation = new BillingLocation();
						billingLocation.setBillingLocationId(Long.parseLong(String.valueOf(row[0])));
						billingLocation.setName(String.valueOf(row[1]));
						billingLocations.add(billingLocation);
					}
					
					companyResponse.setBillingLocations(billingLocations);
				}
				
				List<Object[]> additionalContacts = companyDao.getAdditionalContacts(company.getCompanyId(), session);
				if(additionalContacts != null && !additionalContacts.isEmpty()){
					List<AdditionalContacts> additionalContactList = new ArrayList<AdditionalContacts>();
					for (Object[] row : additionalContacts) {
						AdditionalContacts additionalContact = new AdditionalContacts();
						additionalContact.setAdditionalContactId(Long.parseLong(String.valueOf(row[0])));
						additionalContact.setCustomerName(String.valueOf(row[1]));
						additionalContactList.add(additionalContact);
					}
					
					companyResponse.setAdditionalContacts(additionalContactList);
				}
			}
		} finally{
			if(session != null){
				session.close();
			}
		}*/
		
		
		return companyResponse;
	}

	@Override
	public List<VendorModel> getVendorByVendorName(String vendorName) {
		Session session = null;
		List<VendorModel> response = new ArrayList<VendorModel>();
		try{
			
			session = sessionFactory.openSession();
			List<Vendor> vendorList = vendorDao.getVendorsByVendorName(vendorName, session);
			if(vendorList != null && !vendorList.isEmpty()){
				for (Vendor vendor : vendorList) {
					VendorModel obj = new VendorModel();
					setVendorData(vendor,obj);
					response.add(obj);
				}
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		return response;
	}

	@Override
	public VendorModel getVendorContacts(Long vendorId) {
		Session session = null;
		VendorModel response = new VendorModel();
		try{
			
			session = sessionFactory.openSession();
			Vendor vendor = (Vendor) session.get(Vendor.class, vendorId);
			List<VendorContacts> contacts = vendor.getAdditionalContacts();
			if(contacts != null && !contacts.isEmpty()){
				List<VendorAdditionalContactsModel> vendorAdditionalContacts = new ArrayList<VendorAdditionalContactsModel>();
				for (VendorContacts vendorContacts : contacts) {
					VendorAdditionalContactsModel addContact = new VendorAdditionalContactsModel();
					org.springframework.beans.BeanUtils.copyProperties(vendorContacts, addContact);
					addContact.setStatusId(vendorContacts.getStatus().getId());
				
					vendorAdditionalContacts.add(addContact);
				}
				response.setAdditionalContacts(vendorAdditionalContacts);
				
				/*for (Vendor vendor : vendorList) {
					VendorModel obj = new VendorModel();
					setVendorData(vendor,obj);
					response.add(obj);
				}*/
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		return response;
	}
}
