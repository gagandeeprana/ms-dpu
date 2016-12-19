package com.dpu.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.CompanyDao;
import com.dpu.entity.Company;

@Repository
public class CompanyDaoImpl extends GenericDaoImpl<Company> implements CompanyDao {

//	Logger logger = Logger.getLogger(CompanyDaoImpl.class);
//
//	@Autowired
//	SessionFactory sessionFactory;
//
//	@Transactional
//	public boolean add(Company company) {
//		Session session = null;
//		boolean result = false;
//		try {
//			session = sessionFactory.openSession();
//			session.save(company);
//			result = true;
//		} catch (Exception e) {
//			 result = false;
//			logger.error("CompanyDaoImpl: Inside add: Exception is: " + e.getMessage());
//		} finally {
//			try {
//				if (session != null) {
//					session.close();
//				}
//			} catch (Exception e2) {
//				logger.error("CompanyDaoImpl: Inside addCompany: Inside Finally: Exception is: " + e2.getMessage());
//			}
//		}
//		return result;
//	}
//
//	public boolean update(int id, Company company) {
//		Session session = null;
//		boolean result= false;
//		Transaction tx = null;
//		try {
//			session = sessionFactory.openSession();
//			tx = session.beginTransaction();
//			company.setCompanyId(id);
//			session.saveOrUpdate(company);
//			tx.commit();
//			result = true;
//		} catch (Exception e) {
//			result = false;
//			if(tx != null) {
//				tx.rollback();
//			}
//			System.out.println(e.getMessage());
//			logger.error("CompanyDaoImpl: Inside update: Exception is: " + e.getMessage());
//		} finally {
//			try {
//				if (session != null) {
//					session.close();
//				}
//			} catch (Exception e2) {
//				logger.error("CompanyDaoImpl: Inside update: Inside Finally: Exception is: " + e2.getMessage());
//			}
//		}
//		return result;
//	}
//
//	public boolean delete(int id) {
//		Session session = null;
//		boolean result = false;
//		Transaction tx = null;
//
//		try {
//			session = sessionFactory.openSession();
//			Company company = (Company) session.get(Company.class, id);
//			session.delete(company);
//			result = true;
//		} catch (Exception e) {
//			result = false;
//			System.out.println(e);
//			logger.error("CompanyDaoImpl: Inside delete: Exception is: " + e.getMessage());
//		} finally {
//			try {
//				if (session != null) {
//					session.close();
//				}
//			} catch (Exception e2) {
//				logger.error("CompanyDaoImpl: Inside delete: Inside Finally: Exception is: " + e2.getMessage());
//			}
//		}
//		return result;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Company> getAll(String name) {
//		Session session = null;
//		List<Company> lstCompanies = new ArrayList<Company>();
//		try {
//			session = sessionFactory.openSession();
//			Criteria criteria = session.createCriteria(Company.class);
//			if(!"".equals(name)) {
//				criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
//			}
//			lstCompanies = (List<Company>) criteria.list();
//		} catch (Exception e) {
//			logger.error("CompanyDaoImpl: Inside getAll: Exception is: " + e.getMessage());
//		} finally {
//			try {
//				if (session != null) {
//					session.close();
//				}
//			} catch (Exception e2) {
//				logger.error("CompanyDaoImpl: Inside getAll: Inside Finally: Exception is: " + e2.getMessage());
//			}
//		}
//		return lstCompanies;
//	}
//	
//	public Company get(int id) {
//		Session session = null;
//		Company company = null;
//		try {
//			session = sessionFactory.openSession();
//			company = (Company)session.get(Company.class, id);
//		} catch (Exception e) {
//			logger.error("CompanyDaoImpl: Inside get: Exception is: " + e.getMessage());
//		} finally {
//			try {
//				if (session != null) {
//					session.close();
//				}
//			} catch (Exception e2) {
//				logger.error("CompanyDaoImpl: Inside getQuestionsCountByCategory: Inside Finally: Exception is: " + e2.getMessage());
//			}
//		}
//		return company;
//	}
}
