package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.common.CommonProperties;
import com.dpu.dao.CategoryDao;
import com.dpu.entity.Category;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.CategoryReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.CategoryService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Component
public class CategoryServiceImpl implements CategoryService {

	Logger logger = Logger.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	StatusService statusService;

	@Autowired
	TypeService typeService;

	@Autowired
	SessionFactory sessionFactory;

	private Object createSuccessObject(String msg, long code) {
		Success success = new Success();
		success.setCode(code);
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg, long code) {
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		failed.setResultList(getAll());
		return failed;
	}

	public Object createAlreadyExistObject(String msg, long code) {
		System.out.println("wwwwwwwwwwwwww" + msg + " " + code);
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object addCategory(CategoryReq categoryReq) {

		logger.info("[CategoryServiceImpl] [addCategory] : Srvice: Enter");
		Category category = null;
		try {
			category = setCategoryValues(categoryReq);
			categoryDao.save(category);

		} catch (Exception e) {
			logger.info("Exception inside CategoryServiceImpl addCategory() :"
					+ e.getMessage());
			return createFailedObject(
					CommonProperties.category_unable_to_add_message,
					Long.parseLong(CommonProperties.category_unable_to_add_code));

		}
		logger.info("[CategoryServiceImpl] [addCategory] : Srvice: Exit");
		return createSuccessObject(CommonProperties.category_added_message,
				Long.parseLong(CommonProperties.category_added_code));
	}

	private Category setCategoryValues(CategoryReq categoryReq) {
		logger.info("[CategoryServiceImpl] [setCategoryValues] : Srvice: Enter");
		Category category = new Category();
		category.setName(categoryReq.getName());
		Status status = statusService.get(categoryReq.getStatusId());
		Type highlight = typeService.get(categoryReq.getHighlightId());
		category.setHighLight(highlight);
		Type type = typeService.get(categoryReq.getTypeId());
		category.setType(type);
		category.setStatus(status);
		logger.info("[CategoryServiceImpl] [setCategoryValues] : Srvice: Exit");
		return category;
	}

	@Override
	public Object update(Long id, CategoryReq categoryReq) {
		logger.info("[CategoryServiceImpl] [update] : Srvice: Enter");
		Category category = null;
		try {
			category = categoryDao.findById(id);
			// if (category != null) {

			category.setName(categoryReq.getName());

			Status status = statusService.get(categoryReq.getStatusId());
			category.setStatus(status);

			Type highlight = typeService.get(categoryReq.getHighlightId());
			category.setHighLight(highlight);

			Type type = typeService.get(categoryReq.getTypeId());
			category.setType(type);

			category = categoryDao.update(category);
			// }

		} catch (Exception e) {
			logger.info("Exception inside CategoryServiceImpl updateCategory() :"
					+ e.getMessage());
			return createFailedObject(
					CommonProperties.category_unable_to_update_message,
					Long.parseLong(CommonProperties.category_unable_to_update_code));
		}
		logger.info("[CategoryServiceImpl] [update] : Srvice: Exit");
		return createSuccessObject(CommonProperties.category_updated_message,
				Long.parseLong(CommonProperties.category_updated_code));
	}

	@Override
	public Object delete(Long id) {

		logger.info("[CategoryServiceImpl] [delete] : Srvice: Enter");

		Object obj = null;
		try {
			Category category = categoryDao.findById(id);
			categoryDao.deleteCategory(category);
			obj = createSuccessObject(
					CommonProperties.category_deleted_message,
					Long.parseLong(CommonProperties.category_unable_to_delete_code));
		} catch (ConstraintViolationException em) {
			logger.info("Exception inside CategoryServiceImpl delete() : "
					+ em.getMessage());
			obj = createFailedObject(
					CommonProperties.category_already_used_message,
					Long.parseLong(CommonProperties.category_already_used_code));

		} catch (Exception e) {
			logger.info("Exception inside CategoryServiceImpl delete() : "
					+ e.getMessage());
			obj = createFailedObject(
					CommonProperties.category_unable_to_delete_message,
					Long.parseLong(CommonProperties.category_deleted_code));
		}

		logger.info("[CategoryServiceImpl] [delete] : Service :  Exit");

		return obj;
	}

	@Override
	public List<CategoryReq> getAll() {
		logger.info("[CategoryServiceImpl] [getAll] : Srvice: Enter");
		Session session = null;
		List<CategoryReq> categoriesList = new ArrayList<CategoryReq>();

		try {

			session = sessionFactory.openSession();
			List<Category> categories = categoryDao.findAll(session);

			if (categories != null && !categories.isEmpty()) {
				for (Category category : categories) {
					CategoryReq categoryReq = new CategoryReq();
					categoryReq.setCategoryId(category.getCategoryId());
					categoryReq.setName(category.getName());
					categoryReq.setHighlightName(category.getHighLight()
							.getTypeName());
					categoryReq.setTypeName(category.getType().getTypeName());
					categoryReq.setStatusName(category.getStatus().getStatus());
					categoriesList.add(categoryReq);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.info("[CategoryServiceImpl] [getAll] : Srvice: Exit");
		return categoriesList;
	}

	@Override
	public CategoryReq get(Long id) {
		logger.info("[CategoryServiceImpl] [get] : Srvice: Enter");
		Session session = null;
		CategoryReq categoryReq = new CategoryReq();

		try {

			session = sessionFactory.openSession();
			Category category = categoryDao.findById(id, session);

			if (category != null) {

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
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.info("[CategoryServiceImpl] [get] : Srvice: Exit");
		return categoryReq;
	}

	@Override
	public CategoryReq getOpenAdd() {
		logger.info("[CategoryServiceImpl] [getOpenAdd] : Srvice: Enter");
		CategoryReq categoryReq = new CategoryReq();

		List<Status> statusList = statusService.getAll();
		categoryReq.setStatusList(statusList);

		List<TypeResponse> typeList = typeService.getAll(3l);
		categoryReq.setTypeList(typeList);

		List<TypeResponse> highlightList = typeService.getAll(4l);
		categoryReq.setHighlightList(highlightList);
		logger.info("[CategoryServiceImpl] [getOpenAdd] : Srvice: Exit");
		return categoryReq;
	}

	@Override
	public List<CategoryReq> getCategoryByCategoryName(String categoryName) {
		logger.info("[CategoryServiceImpl] [getCategoryByCategoryName] : Srvice: Enter");
		Session session = null;
		List<CategoryReq> categories = new ArrayList<CategoryReq>();

		try {
			session = sessionFactory.openSession();
			List<Category> categoryList = categoryDao
					.getCategoryByCategoryName(session, categoryName);
			if (categoryList != null && !categoryList.isEmpty()) {
				for (Category category : categoryList) {
					CategoryReq categoryObj = new CategoryReq();
					categoryObj.setCategoryId(category.getCategoryId());
					categoryObj.setName(category.getName());
					categoryObj.setHighlightName(category.getHighLight()
							.getTypeName());
					categoryObj.setTypeName(category.getType().getTypeName());
					categoryObj.setStatusName(category.getStatus().getStatus());
					categories.add(categoryObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.info("[CategoryServiceImpl] [getCategoryByCategoryName] : Srvice: Exit");
		return categories;
	}

	@Override
	public Category getCategory(Long categoryId) {
		Category category = categoryDao.findById(categoryId);
		return category;
	}

	@Override
	public List<CategoryReq> getSpecificData() {
		List<Object[]> categoryData = categoryDao.getSpecificData("Category",
				"categoryId", "name");

		List<CategoryReq> categories = new ArrayList<CategoryReq>();
		if (categoryData != null && !categoryData.isEmpty()) {
			for (Object[] row : categoryData) {
				CategoryReq categoryObj = new CategoryReq();
				categoryObj.setCategoryId((Long) row[0]);
				categoryObj.setName(String.valueOf(row[1]));
				categories.add(categoryObj);
			}
		}

		return categories;
	}

}
