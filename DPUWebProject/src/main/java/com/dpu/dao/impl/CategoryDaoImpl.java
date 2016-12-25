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

import com.dpu.dao.CategoryDao;
import com.dpu.entity.Category;

/**
 * @author jagvir
 *
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {

	Logger logger = Logger.getLogger(CategoryDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean add(Category category) {
		Session session = null;
		boolean result = false;
		try {
			session = sessionFactory.openSession();
			session.save(category);
			result = true;
		} catch (Exception e) {
			result = false;
			logger.error("CategoryDaoImpl: Inside add: Exception is: "
					+ e.getMessage());
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e2) {
				logger.error("CategoryDaoImpl: Inside add: Inside Finally: Exception is: "
						+ e2.getMessage());
			}
		}
		return result;
	}

	@Override
	public boolean update(int id, Category category) {
		Session session = null;
		boolean result = false;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			category.setCategoryId(id);
			session.saveOrUpdate(category);
			tx.commit();
			result = true;
		} catch (Exception e) {
			result = false;
			if (tx != null) {
				tx.rollback();
			}
			System.out.println(e.getMessage());
			logger.error("CategoryDaoImpl: Inside update: Exception is: "
					+ e.getMessage());
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e2) {
				logger.error("CategoryDaoImpl: Inside update: Inside Finally: Exception is: "
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
			Category category = (Category) session.get(Category.class, id);
			session.delete(category);
			result = true;
		} catch (Exception e) {
			result = false;
			System.out.println(e);
			logger.error("CategoryDaoImpl: Inside delete: Exception is: "
					+ e.getMessage());
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e2) {
				logger.error("CategoryDaoImpl: Inside delete: Inside Finally: Exception is: "
						+ e2.getMessage());
			}
		}
		return result;

	}

	@Override
	public List<Category> getAll(String name) {
		Session session = null;
		List<Category> lstCategories = new ArrayList<Category>();
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Category.class);
			if (!"".equals(name)) {
				criteria.add(Restrictions
						.like("name", name, MatchMode.ANYWHERE));
			}
			lstCategories = (List<Category>) criteria.list();
		} catch (Exception e) {
			logger.error("CategoryDaoImpl: Inside getAll: Exception is: "
					+ e.getMessage());
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e2) {
				logger.error("CategoryDaoImpl: Inside getAll: Inside Finally: Exception is: "
						+ e2.getMessage());
			}
		}
		return lstCategories;
	}

	@Override
	public Category get(int id) {
		Session session = null;
		Category category = null;
		try {
			session = sessionFactory.openSession();
			category = (Category) session.get(Category.class, id);
		} catch (Exception e) {
			logger.error("CategoryDaoImpl: Inside get: Exception is: "
					+ e.getMessage());
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e2) {
				logger.error("CategoryDaoImpl: Inside get: Inside Finally: Exception is: "
						+ e2.getMessage());
			}
		}
		return category;
	}

}
