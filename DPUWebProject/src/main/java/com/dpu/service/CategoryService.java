/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Category;
import com.dpu.model.CategoryReq;

/**
 * @author jagvir
 *
 */
public interface CategoryService {
	boolean addCategory(Category category);

	Category update(int id, Category category);

	boolean delete(Category category);

	List<CategoryReq> getAll();

	CategoryReq getOpenAdd();

	CategoryReq get(Long id);

}
