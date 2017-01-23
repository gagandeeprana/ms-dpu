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
	List<CategoryReq> addCategory(CategoryReq categoryReq);

	List<CategoryReq> update(Long id, CategoryReq categoryReq);

	boolean delete(Category category);

	List<CategoryReq> getAll();

	CategoryReq getOpenAdd();

	CategoryReq get(Long id);

}
