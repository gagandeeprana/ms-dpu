/**
 * 
 */
package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Category;
import com.dpu.entity.Handling;

public interface HandlingDao extends GenericDao<Handling> {

	List<Handling> findAll(Session session);

	Category findById(Long id, Session session);

	List<Category> getCategoryByCategoryName(Session session, String categoryName);
}
