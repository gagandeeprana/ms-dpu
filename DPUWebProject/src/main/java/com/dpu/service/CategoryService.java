/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Category;

/**
 * @author jagvir
 *
 */
public interface CategoryService {
	boolean addCategory(Category category);

	Category update(int id, Category category);

	boolean delete(Category category);

	List<Category> getAll();

	Category get(int id);

}
