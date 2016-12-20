/**
 * 
 */

package com.dpu.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CompanyDao;
import com.dpu.dao.ShipperDao;
import com.dpu.entity.Company;
import com.dpu.entity.Shipper;

/**
 * @author jagvir
 *
 */
@Repository
public class ShipperDaoImpl extends GenericDaoImpl<Shipper> implements ShipperDao {

//	Logger logger = Logger.getLogger(ShipperDaoImpl.class);
//
//	@Autowired
//	SessionFactory sessionFactory;
//
//	@Override
//	public boolean add(Shipper shipper) {
//		Session session = null;
//		boolean result = false;
//		try {
//			session = sessionFactory.openSession();
//			session.save(shipper);
//			result = true;
//		} catch (Exception e) {
//			result = false;
//			logger.error("ShipperDaoImpl: Inside add: Exception is: "
//					+ e.getMessage());
//		} finally {
//			try {
//				if (session != null) {
//					session.close();
//				}
//			} catch (Exception e2) {
//				logger.error("ShipperDaoImpl: Inside addShipper: Inside Finally: Exception is: "
//						+ e2.getMessage());
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public boolean update(int id, Shipper shipper) {
//		Session session = null;
//		boolean result = false;
//		Transaction tx = null;
//		try {
//			session = sessionFactory.openSession();
//			tx = session.beginTransaction();
//			shipper.setShipperId(id);
//			session.saveOrUpdate(shipper);
//			tx.commit();
//			result = true;
//		} catch (Exception e) {
//			result = false;
//			if (tx != null) {
//				tx.rollback();
//			}
//			System.out.println(e.getMessage());
//			logger.error("ShipperDaoImpl: Inside update: Exception is: "
//					+ e.getMessage());
//		} finally {
//			try {
//				if (session != null) {
//					session.close();
//				}
//			} catch (Exception e2) {
//				logger.error("ShipperDaoImpl: Inside update: Inside Finally: Exception is: "
//						+ e2.getMessage());
//			}
//		}
//		return result;
//	}
//
//	@Transactional
//	public boolean delete(int id) {
//		Session session = null;
//		boolean result = false;
//		try {
//			session = sessionFactory.openSession();
//			Shipper shipper = (Shipper) session.get(Shipper.class, id);
//			session.delete(shipper);
//			result = true;
//		} catch (Exception e) {
//			result = false;
//			System.out.println(e);
//			logger.error("ShipperDaoImpl: Inside delete: Exception is: "
//					+ e.getMessage());
//		} finally {
//			try {
//				if (session != null) {
//					session.close();
//				}
//			} catch (Exception e2) {
//				logger.error("ShipperDaoImpl: Inside delete: Inside Finally: Exception is: "
//						+ e2.getMessage());
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public List<Shipper> getAll(String name) {
//		Session session = null;
//		List<Shipper> lstShippers = new ArrayList<Shipper>();
//		try {
//			session = sessionFactory.openSession();
//			Criteria criteria = session.createCriteria(Shipper.class);
//			if (!"".equals(name)) {
//				criteria.add(Restrictions
//						.like("name", name, MatchMode.ANYWHERE));
//			}
//			lstShippers = (List<Shipper>) criteria.list();
//		} catch (Exception e) {
//			logger.error("ShipperDaoImpl: Inside getAll: Exception is: "
//					+ e.getMessage());
//		} finally {
//			try {
//				if (session != null) {
//					session.close();
//				}
//			} catch (Exception e2) {
//				logger.error("ShipperDaoImpl: Inside getAll: Inside Finally: Exception is: "
//						+ e2.getMessage());
//			}
//		}
//		return lstShippers;
//	}
//
//	@Override
//	public Shipper get(int id) {
//		Session session = null;
//		Shipper shipper = null;
//		try {
//			session = sessionFactory.openSession();
//			shipper = (Shipper) session.get(Shipper.class, id);
//		} catch (Exception e) {
//			logger.error("ShipperDaoImpl: Inside get: Exception is: "
//					+ e.getMessage());
//		} finally {
//			try {
//				if (session != null) {
//					session.close();
//				}
//			} catch (Exception e2) {
//				logger.error("ShipperDaoImpl: Inside get: Inside Finally: Exception is: "
//						+ e2.getMessage());
//			}
//		}
//		return shipper;
//	}

}
