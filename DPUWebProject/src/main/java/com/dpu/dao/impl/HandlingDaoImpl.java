/**
 * 
 */
package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CategoryDao;
import com.dpu.dao.HandlingDao;
import com.dpu.entity.Category;
import com.dpu.entity.Handling;

@Repository
public class HandlingDaoImpl extends GenericDaoImpl<Handling> implements HandlingDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Handling> findAll(Session session) {
		
		StringBuilder sb = new StringBuilder("select h from Handling h join fetch h.status ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public Category findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder("select c from Category c join fetch c.status join fetch c.highLight join fetch c.type where c.categoryId =:categoryId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("categoryId", id);
		return (Category) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryByCategoryName(Session session, String categoryName) {
		StringBuilder sb = new StringBuilder("select c from Category c join fetch c.status join fetch c.highLight join fetch c.type where c.name like :categoryName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("categoryName", "%"+categoryName+"%");
		return query.list();
	}

}
