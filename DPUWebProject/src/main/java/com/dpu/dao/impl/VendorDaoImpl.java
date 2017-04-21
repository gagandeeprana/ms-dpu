package com.dpu.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dpu.dao.VendorDao;
import com.dpu.entity.Vendor;
import com.dpu.entity.VendorBillingLocation;
import com.dpu.entity.VendorContacts;
import com.dpu.service.StatusService;

@Transactional
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

			Query query = session
					.createSQLQuery(" select vendor_id, name from vendor ");
			returnList = query.list();

		} catch (Exception e) {
			logger.error("Exception inside VendorDaoImpl getVendorData() :"
					+ e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("VendorDaoImpl getVendorData() Ends");
		return returnList;
	}

	@Override
	public void updateData(Vendor vendor, Session session) {

		session.saveOrUpdate(vendor);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getVendorBillingLocations(Long vendorId,
			Session session) {

		List<Object[]> returnList = null;
		Query query = session
				.createSQLQuery(" select vendor_billing_location_id,name from vendor_billing_locations where vendor_id =:vendorId ");
		query.setParameter("vendorId", vendorId);
		returnList = query.list();
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getVendorAdditionalContacts(Long vendorId,
			Session session) {

		List<Object[]> returnList = null;
		Query query = session
				.createSQLQuery(" select vendor_additional_contact_id,vendor_name from vendor_additional_contacts where vendor_id =:vendorId ");
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
	public List<Vendor> getVendorsByVendorName(String vendorName,
			Session session) {

		StringBuilder builder = new StringBuilder();
		builder.append("select v from Vendor v where v.name like :vendorName ");
		Query query = session.createQuery(builder.toString());
		query.setParameter("vendorName", "%" + vendorName + "%");
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

	@Override
	public void updateDataAdditionalContact(VendorContacts comAdditionalContacts, Session session) {

		session.saveOrUpdate(comAdditionalContacts);

	}

	@Override
	public void updateVendorBillingLocation(VendorBillingLocation vendorBillingLocation, Session session) {

		session.saveOrUpdate(vendorBillingLocation);
		
	}

	@Override
	public boolean deleteAdditionalContact(Long vendorId,
			Long additionalContactId) {
		// TODO Auto-generated method stub
		return false;
	}

}
