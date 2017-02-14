package com.dpu.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CompanyDao;
import com.dpu.entity.Company;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.model.AdditionalContacts;
import com.dpu.model.BillingLocation;
import com.dpu.model.CompanyResponse;

@Repository
public class CompanyDaoImpl extends GenericDaoImpl<Company> implements CompanyDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCompanyData() {
		Session session = null;
		List<Object[]> returnList = null;
		try {
			session = sessionFactory.openSession();
			
			Query query = session.createSQLQuery(" select company_id, name from companymaster ");
			returnList = query.list();

		} catch (Exception e) {
			logger.error("[find ]" + e.getCause());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.info("[DivisionDaoImpl] [find] : Exit ");
		return returnList;
	}

	@Override
	public void updateData(Company company, CompanyResponse companyResponse, Session session) {

		String[] ignoreProp = new String[1];
		ignoreProp[0] ="companyId";
		
		BeanUtils.copyProperties(companyResponse, company, ignoreProp);
		session.saveOrUpdate(company);
		
		List<BillingLocation> billingocationList = companyResponse.getBillingLocations();
		
		if(billingocationList != null && !billingocationList.isEmpty()){
			for (BillingLocation billingLocation : billingocationList) {
				CompanyBillingLocation companyBillingLocation = new CompanyBillingLocation();
				BeanUtils.copyProperties(billingLocation, companyBillingLocation);
				companyBillingLocation.setCompany(company);
				session.saveOrUpdate(companyBillingLocation);
			}
		}

		List<AdditionalContacts> additionalContactsList = companyResponse.getAdditionalContacts();
		
		if(additionalContactsList != null && !additionalContactsList.isEmpty()){
			for (AdditionalContacts additionalContacts : additionalContactsList) {
				CompanyAdditionalContacts comAdditionalContacts = new CompanyAdditionalContacts();
				BeanUtils.copyProperties(additionalContacts, comAdditionalContacts);
				comAdditionalContacts.setCompany(company);
				session.saveOrUpdate(comAdditionalContacts);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getBillingLocations(Long companyId, Session session) {
		Long maxVal = getMaxProbilNo(session);
		List<Object[]> returnList = null;
		Query query = session.createSQLQuery(" select billing_location_id,name from billinglocationmaster where company_id =:companyId ");
		query.setParameter("companyId", companyId);
		returnList = query.list();
		return returnList;
	}

	private Long getMaxProbilNo(Session session) {
		Long returnVal = 0l;
		Long maxVal = (Long) session.createQuery(" select max(probil.probilNo) from Probil probil ").uniqueResult();
		if(maxVal != null){
			returnVal = maxVal;
		}
		System.out.println("the max probil no is :"+maxVal);
		return returnVal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAdditionalContacts(Long companyId, Session session) {
		List<Object[]> returnList = null;
		Query query = session.createSQLQuery(" select add_contact_id,customer_name from additionalcontactmaster where company_id =:companyId ");
		query.setParameter("companyId", companyId);
		returnList = query.list();
		return returnList;
	}

}
