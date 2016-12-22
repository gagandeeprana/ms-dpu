/**
 * 
 */
package com.dpu.dao;

import java.util.List;

import com.dpu.entity.Category;

/**
 * @author jagvir
 *
 */
public interface CategoryDao {

	boolean add(Category category);

	boolean update(int id, Category category);

	boolean delete(int id);

	List<Category> getAll(String name);

	Category get(int id);
}
