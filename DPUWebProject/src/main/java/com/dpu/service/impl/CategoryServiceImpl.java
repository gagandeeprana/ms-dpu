/**
 * 
 */
package com.dpu.service.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CategoryDao;
import com.dpu.entity.Category;
import com.dpu.service.CategoryService;

/**
 * @author jagvir
 *
 */
@Component
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryDao categoryDao;

	@Override
	public Category add(Category category) {
		return categoryDao.save(category);
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
