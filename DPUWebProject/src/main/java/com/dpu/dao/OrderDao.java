/**
 * 
 */
package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Category;
import com.dpu.entity.Order;
import com.dpu.entity.OrderPickupDropNo;
import com.dpu.entity.Probil;

public interface OrderDao extends GenericDao<Category> {

	List<Order> findAll(Session session);

	Category findById(Long id, Session session);

	List<Category> getCategoryByCategoryName(Session session, String categoryName);

	void saveOrder(Session session, Order order);

	void saveProbil(Session session, Probil probil);

	void savePickUpDrop(Session session, OrderPickupDropNo pickUpDropNo);

	Long getMaxProbilNo(Session session);

}
