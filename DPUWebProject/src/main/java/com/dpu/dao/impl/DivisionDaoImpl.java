/**
 * 
 */
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

import com.dpu.dao.DivisionDao;
import com.dpu.entity.Division;

/**
 * @author jagvir
 *
 */
@Repository
public class DivisionDaoImpl implements DivisionDao{

	Logger logger = Logger.getLogger(DivisionDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	
	@Override
	public boolean add(Division division) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionFactory.openSession();
			session.save(division);
			result = true;
		} catch (Exception e) {
			result = false;
			logger.error("DivisionDaoImpl: Inside add: Exception is: "
					+ e.getMessage());
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e2) {
				logger.error("DivisionDaoImpl: Inside add: Inside Finally: Exception is: "
						+ e2.getMessage());
			}
		}
		return result;
	}

	@Override
	public boolean update(int id, Division division) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			division.setDivisionId(id);
			session.saveOrUpdate(division);
			tx.commit();
			result = true;
		} catch (Exception e) {
			result = false;
			if (tx != null) {
				tx.rollback();
			}
			System.out.println(e.getMessage());
			logger.error("DivisionDaoImpl: Inside update: Exception is: "
					+ e.getMessage());
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e2) {
				logger.error("DivisionDaoImpl: Inside update: Inside Finally: Exception is: "
						+ e2.getMessage());
			}
		}
		return result;
	}

	@Override
	public boolean delete(int id) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionFactory.openSession();
			Division division = (Division) session.get(Division.class, id);
			session.delete(division);
			result = true;
		} catch (Exception e) {
			result = false;
			System.out.println(e);
			logger.error("DivisionDaoImpl: Inside delete: Exception is: "
					+ e.getMessage());
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e2) {
				logger.error("DivisionDaoImpl: Inside delete: Inside Finally: Exception is: "
						+ e2.getMessage());
			}
		}
		return result;
	}

	@Override
	public List<Division> getAll(String name) {
		Session session = null;
		List<Division> lstShippers = new ArrayList<Division>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Division.class);
			if (!"".equals(name)) {
				criteria.add(Restrictions
						.like("name", name, MatchMode.ANYWHERE));
			}
			lstShippers = (List<Division>) criteria.list();
		} catch (Exception e) {
			logger.error("DivisionDaoImpl: Inside getAll: Exception is: "
					+ e.getMessage());
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e2) {
				logger.error("DivisionDaoImpl: Inside getAll: Inside Finally: Exception is: "
						+ e2.getMessage());
			}
		}
		return lstShippers;
	}

	@Override
	public Division get(int id) {
		Session session = null;
		Division division = null;
		try {
			session = sessionFactory.openSession();
			division = (Division) session.get(Division.class, id);
		} catch (Exception e) {
			logger.error("DivisionDaoImpl: Inside get: Exception is: "
					+ e.getMessage());
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e2) {
				logger.error("DivisionDaoImpl: Inside get: Inside Finally: Exception is: "
						+ e2.getMessage());
			}
		}
		return division;
	}

}
