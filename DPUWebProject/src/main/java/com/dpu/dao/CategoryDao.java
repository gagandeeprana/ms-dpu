/**
 * 
 */
package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Category;

public interface CategoryDao extends GenericDao<Category> {

	List<Category> findAll(Session session);

	Category findById(Long id, Session session);

	List<Category> getCategoryByCategoryName(Session session, String categoryName);

	void deleteCategory(Category category);
}
