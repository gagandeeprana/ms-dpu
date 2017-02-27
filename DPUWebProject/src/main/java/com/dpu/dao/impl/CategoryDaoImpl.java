/**
 * 
 */
package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import com.dpu.common.CommonProperties;
import com.dpu.dao.CategoryDao;
import com.dpu.entity.Category;
import com.dpu.service.impl.CategoryServiceImpl;

@Repository
public class CategoryDaoImpl extends GenericDaoImpl<Category> implements
		CategoryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findAll(Session session) {

		StringBuilder sb = new StringBuilder(
				"select c from Category c join fetch c.status join fetch c.highLight join fetch c.type ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public Category findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder(
				"select c from Category c join fetch c.status join fetch c.highLight join fetch c.type where c.categoryId =:categoryId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("categoryId", id);
		return (Category) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryByCategoryName(Session session,
			String categoryName) {
		StringBuilder sb = new StringBuilder(
				"select c from Category c join fetch c.status join fetch c.highLight join fetch c.type where c.name like :categoryName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("categoryName", "%" + categoryName + "%");
		return query.list();
	}

	@Override
	public void deleteCategory(Category category) {
		logger.info("[CategoryDaoImpl] [deleteCategory] : Srvice: Enter");
		Session session = null;
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(category);
		tx.commit();
		if (session != null) {
			session.close();
		}
		logger.info("[CategoryDaoImpl] [deleteCategory] : Srvice: Exit");

	}

}
