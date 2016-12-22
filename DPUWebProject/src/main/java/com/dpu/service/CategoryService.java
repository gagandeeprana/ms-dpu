/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Category;
import com.dpu.entity.Division;

/**
 * @author jagvir
 *
 */
public interface CategoryService {
	boolean add(Category category);

	boolean update(int id, Category category);

	boolean delete(int id);

	List<Category> getAll(String name);

	Category get(int id);


}
