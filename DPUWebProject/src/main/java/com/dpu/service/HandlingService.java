/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Category;
import com.dpu.model.CategoryReq;
import com.dpu.model.HandlingModel;

/**
 * @author jagvir
 *
 */
public interface HandlingService {
	Object update(Long id, CategoryReq categoryReq);

	Object delete(Long id);

	List<HandlingModel> getAll();

	HandlingModel getOpenAdd();

	CategoryReq get(Long id);

	List<CategoryReq> getCategoryByCategoryName(String categoryName);
	
	Category getCategory(Long categoryId);

	List<CategoryReq> getSpecificData();

	Object addHandling(HandlingModel handlingModel);

}
