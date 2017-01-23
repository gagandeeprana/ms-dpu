/**
 * 
 */
package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CategoryDao;
import com.dpu.entity.Category;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.CategoryReq;
import com.dpu.model.TypeResponse;
import com.dpu.service.CategoryService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

/**
 * @author jagvir
 *
 */
@Component
public class CategoryServiceImpl implements CategoryService {

	Logger logger = Logger.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	StatusService statusService;
	
	@Autowired
	TypeService typeService;
	
	@Override
	public List<CategoryReq> addCategory(CategoryReq categoryReq) {
		logger.info("[addCategory]:Service:  Enter");

		List<CategoryReq> returnList = new ArrayList<CategoryReq>();
		try {

			Category category = setCategoryValues(categoryReq);
			categoryDao.save(category);
			returnList = getAll();

		} catch (Exception e) {
			logger.info("[addCategory]:Exception:    : ", e);
			System.out.println(e);
		} finally {
			logger.info("[addCategory]:Service:  returnValue : ");
		}
		
		return returnList;
	}

	private Category setCategoryValues(CategoryReq categoryReq) {
		Category category = new Category();
		category.setName(categoryReq.getName());
		Status status = statusService.get(categoryReq.getStatusId());
		Type highlight = typeService.get(categoryReq.getHighlightId());
		category.setHighLight(highlight);
		Type type = typeService.get(categoryReq.getTypeId());
		category.setType(type);
		category.setStatus(status);
		return category;
	}
	
	@Override
	public List<CategoryReq> update(Long id, CategoryReq categoryReq) {
		
		Category category = categoryDao.findById(id);
		category.setName(categoryReq.getName());
		
		Status status = statusService.get(categoryReq.getStatusId());
		category.setStatus(status);
		
		Type highlight = typeService.get(categoryReq.getHighlightId());
		category.setHighLight(highlight);
		
		Type type = typeService.get(categoryReq.getTypeId());
		category.setType(type);
		
		categoryDao.update(category);
		return getAll();
	}

	@Override
	public boolean delete(Category category) {
		boolean result = false;
		try {
			categoryDao.delete(category);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<CategoryReq> getAll() {

		List<CategoryReq> categoriesList = new ArrayList<CategoryReq>();
		List<Category> categories = categoryDao.findAll();
		
		if(categories != null && ! categories.isEmpty()){
			for (Category category : categories) {
				CategoryReq categoryReq = new CategoryReq();
				categoryReq.setCategoryId(category.getCategoryId());
				categoryReq.setName(category.getName());
				categoryReq.setHighlightName(category.getHighLight().getTypeName());
				categoryReq.setTypeName(category.getType().getTypeName());
				categoryReq.setStatusName(category.getStatus().getStatus());
				categoriesList.add(categoryReq);
			}
		}
		
		return categoriesList;
	}

	@Override
	public CategoryReq get(Long id) {
		
		Category category = categoryDao.findById(id);
		CategoryReq categoryReq = new CategoryReq();
		
		if(category != null){
			
			categoryReq.setCategoryId(category.getCategoryId());
			categoryReq.setName(category.getName());
			categoryReq.setStatusId(category.getStatus().getId());
			categoryReq.setTypeId(category.getType().getTypeId());
			categoryReq.setHighlightId(category.getHighLight().getTypeId());
			
			List<Status> statusList = statusService.getAll();
			categoryReq.setStatusList(statusList);
			
			List<TypeResponse> typeList = typeService.getAll(3l);
			categoryReq.setTypeList(typeList);
			
			List<TypeResponse> highlightList = typeService.getAll(4l);
			categoryReq.setHighlightList(highlightList);
		}
		
		return categoryReq;
	}

	@Override
	public CategoryReq getOpenAdd() {

		CategoryReq categoryReq = new CategoryReq();
		
		List<Status> statusList = statusService.getAll();
		categoryReq.setStatusList(statusList);
		
		List<TypeResponse> typeList = typeService.getAll(3l);
		categoryReq.setTypeList(typeList);
		
		List<TypeResponse> highlightList = typeService.getAll(4l);
		categoryReq.setHighlightList(highlightList);
		
		return categoryReq;
	}

}
