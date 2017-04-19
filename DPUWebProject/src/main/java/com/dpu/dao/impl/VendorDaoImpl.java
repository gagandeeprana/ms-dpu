package com.dpu.dao.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dpu.dao.VendorDao;
import com.dpu.entity.Company;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.entity.Vendor;
import com.dpu.entity.VendorBillingLocation;
import com.dpu.entity.VendorContacts;
import com.dpu.model.AdditionalContacts;
import com.dpu.model.BillingLocation;
import com.dpu.model.CompanyResponse;
import com.dpu.model.VendorAdditionalContactsModel;
import com.dpu.model.VendorBillingLocationModel;
import com.dpu.model.VendorModel;
import com.dpu.service.StatusService;

@Repository
public class VendorDaoImpl extends GenericDaoImpl<Vendor> implements VendorDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	StatusService statusService;
	
	Logger logger = Logger.getLogger(VendorDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getVendorData() {
		
		logger.info("VendorDaoImpl getVendorData() starts");
		Session session = null;
		List<Object[]> returnList = null;
		try {
			session = sessionFactory.openSession();
			
			Query query = session.createSQLQuery(" select vendor_id, name from vendor ");
			returnList = query.list();

		} catch (Exception e) {
			logger.error("Exception inside VendorDaoImpl getVendorData() :" + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("VendorDaoImpl getVendorData() Ends");
		return returnList;
	}

	@Override
	public void updateData(Vendor vendor, VendorModel vendorModel, Session session) {

		String[] ignoreProp = new String[1];
		ignoreProp[0] ="vendorId";
		
		BeanUtils.copyProperties(vendorModel, vendor, ignoreProp);
		session.saveOrUpdate(vendor);
		
		List<VendorBillingLocationModel> billingLocationList = vendorModel.getBillingLocations();
		
		if(billingLocationList != null && !billingLocationList.isEmpty()){
			for (VendorBillingLocationModel billingLocation : billingLocationList) {
				VendorBillingLocation vendorBillingLocation = null;
				if(billingLocation.getVendorBillingLocationId() != null){
					vendorBillingLocation = (VendorBillingLocation) session.get(VendorBillingLocation.class, billingLocation.getVendorBillingLocationId());
				} else{
					vendorBillingLocation = new VendorBillingLocation();
				}
				
				BeanUtils.copyProperties(billingLocation, vendorBillingLocation);
				vendorBillingLocation.setVendor(vendor);
				vendorBillingLocation.setStatus(statusService.get(billingLocation.getStatusId()));
				session.saveOrUpdate(vendorBillingLocation);
			}
		}

	//	List<VendorAdditionalContactsModel> additionalContactsList = vendorModel.getAdditionalContacts();
		
		/*if(additionalContactsList != null && !additionalContactsList.isEmpty()){
			for (VendorAdditionalContactsModel additionalContacts : additionalContactsList) {
				VendorContacts comAdditionalContacts = new VendorContacts();
				BeanUtils.copyProperties(additionalContacts, comAdditionalContacts);
				comAdditionalContacts.setVendor(vendor);
				comAdditionalContacts.setStatus(statusService.get(additionalContacts.getStatusId()));
				session.saveOrUpdate(comAdditionalContacts);
			}
		}*/
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getVendorBillingLocations(Long vendorId, Session session) {
		List<Object[]> returnList = null;
		Query query = session.createSQLQuery(" select vendor_billing_location_id,name from vendor_billing_locations where vendor_id =:vendorId ");
		query.setParameter("vendorId", vendorId);
		returnList = query.list();
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getVendorAdditionalContacts(Long vendorId, Session session) {
		List<Object[]> returnList = null;
		Query query = session.createSQLQuery(" select vendor_additional_contact_id,vendor_name from vendor_additional_contacts where vendor_id =:vendorId ");
		query.setParameter("vendorId", vendorId);
		returnList = query.list();
		return returnList;
	}

	@Override
	public Vendor findById(Long vendorId, Session session) {
		Vendor vendor = (Vendor) session.get(Vendor.class, vendorId);
		return vendor;
	}

	@Override
	public Vendor insertVendorData(Vendor vendor, Session session) {
		session.save(vendor);
		return vendor;
	}

	@Override
	public void deleteVendor(Vendor vendor, Session session) {
		session.delete(vendor);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vendor> getVendorsByVendorName(String vendorName, Session session) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("select v from Vendor v where v.name like :vendorName ");
		Query query = session.createQuery(builder.toString());
		query.setParameter("vendorName", "%"+vendorName+"%");
		return query.list();
	}
	
	@Override
	public void insertBillingLocation(VendorBillingLocation vendorBillingLocation, Session session) {
		session.save(vendorBillingLocation);
	}
	
	@Override
	public void insertAdditionalContacts(VendorContacts vendorContacts, Session session) {
		session.save(vendorContacts);
	}

}
