/**
 * 
 */
package com.dpu.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CategoryDao;
import com.dpu.entity.Category;
import com.dpu.entity.Truck;
import com.dpu.service.CategoryService;

/**
 * @author jagvir
 *
 */
@Component
public class CategoryServiceImpl implements CategoryService {

	Logger logger = Logger.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryDao categoryDao;

	@Override
	public boolean addCategory(Category category) {
		logger.info("[addCategory]:Service:  Enter");

		boolean returnValue = false;
		try {

			// truck.setCreated("sumit");
			// truck.setCreatedOn(new Date());
			//
			// truck.setModifiedBy("sumit");
			// truck.setModifiedOn(new Date());

			Category categoryy = categoryDao.save(category);
			System.out.println("[addCategory]category Id :" + categoryy.getCategoryId());
			returnValue = true;
			return returnValue;

		} catch (Exception e) {
			logger.info("[addCategory]:Exception:    : ", e);
			System.out.println(e);
			return returnValue;
		} finally {
			logger.info("[addCategory]:Service:  returnValue : " + returnValue);
		}
	}

	@Override
	public Category update(int id, Category category) {
		return categoryDao.update(category);
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
	public List<Category> getAll() {
		return categoryDao.findAll();
	}

	@Override
	public Category get(int id) {
		return categoryDao.findById(id);
	}

}
