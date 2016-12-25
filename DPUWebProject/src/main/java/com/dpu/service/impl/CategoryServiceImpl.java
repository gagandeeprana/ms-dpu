/**
 * 
 */
package com.dpu.service.impl;

import java.util.List;

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
	public boolean add(Category category) {
		// TODO Auto-generated method stub
		return categoryDao.add(category);
	}

	@Override
	public boolean update(int id, Category category) {
		// TODO Auto-generated method stub
		return categoryDao.update(id, category);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return categoryDao.delete(id);
	}

	@Override
	public List<Category> getAll(String name) {
		// TODO Auto-generated method stub
		return categoryDao.getAll(name);
	}

	@Override
	public Category get(int id) {
		// TODO Auto-generated method stub
		return categoryDao.get(id);
	}

}
