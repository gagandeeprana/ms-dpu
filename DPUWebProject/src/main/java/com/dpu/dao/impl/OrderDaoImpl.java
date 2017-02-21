package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.OrderDao;
import com.dpu.entity.Category;
import com.dpu.entity.Order;
import com.dpu.entity.Probil;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Category> implements OrderDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findAll(Session session) {
		
		StringBuilder sb = new StringBuilder("select o from Order o join fetch o.company join fetch o.billingLocation join fetch o.contact ")
		.append(" join fetch o.temperature join fetch o.temperatureType join fetch o.currency ");
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

	@Override
	public void saveOrder(Session session, Order order) {
		session.save(order);
		
	}

	@Override
	public void saveProbil(Session session, Probil probil) {
		session.save(probil);
		
	}
	
	@SuppressWarnings("unused")
	private Long getMaxProbilNo(Session session) {
		Long returnVal = 0l;
		Long maxVal = (Long) session.createQuery(" select max(probil.probilNo) from Probil probil ").uniqueResult();
		if(maxVal != null){
			returnVal = maxVal;
		}
		System.out.println("the max probil no is :"+maxVal);
		return returnVal;
	}

}
