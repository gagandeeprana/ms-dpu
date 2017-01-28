package com.dpu.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CompanyDao;
import com.dpu.entity.Company;

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

}
