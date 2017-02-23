package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.common.CommonProperties;
import com.dpu.dao.CategoryDao;
import com.dpu.dao.HandlingDao;
import com.dpu.entity.Category;
import com.dpu.entity.Handling;
import com.dpu.entity.Status;
import com.dpu.entity.Truck;
import com.dpu.entity.Type;
import com.dpu.model.CategoryReq;
import com.dpu.model.Failed;
import com.dpu.model.HandlingModel;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.CategoryService;
import com.dpu.service.HandlingService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Component
public class HandlingServiceImpl implements HandlingService {

	Logger logger = Logger.getLogger(HandlingServiceImpl.class);

	@Autowired
	HandlingDao handlingDao;

	@Autowired
	StatusService statusService;

	@Autowired
	TypeService typeService;

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public List<HandlingModel> getAll() {
		
		logger.info("HandlingServiceImpl getAll() starts ");
		Session session = null;
		List<HandlingModel> handlingList = new ArrayList<HandlingModel>();

		try {

			session = sessionFactory.openSession();
			List<Handling> handlings = handlingDao.findAll(session);

			if (handlings != null && !handlings.isEmpty()) {
				for (Handling handling : handlings) {
					HandlingModel handlingModel = new HandlingModel();
					handlingModel.setId(handling.getId());
					handlingModel.setName(handling.getName());
					handlingModel.setStatusName(handling.getStatus().getStatus());;
					handlingList.add(handlingModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	
		logger.info("HandlingServiceImpl getAll() ends ");
		return handlingList;
	}

	private Object createSuccessObject(String msg) {
		Success success = new Success();
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg) {
		Failed failed = new Failed();
		failed.setMessage(msg);
		failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object addHandling(HandlingModel handlingModel) {

		logger.info("HandlingServiceImpl addHandling() starts ");
		Handling handling = null;
		String message ="Record inserted successfully";
		try {
			handling = setHandlingValues(handlingModel);
			handlingDao.save(handling);

		} catch (Exception e) {
			logger.info("Exception inside HandlingServiceImpl addHandling() :"+ e.getMessage());
			message ="error while inserting record";
			return createFailedObject(message);

		}
		
		logger.info("HandlingServiceImpl addHandling() ends ");
		return createSuccessObject(message);
	}

	private Handling setHandlingValues(HandlingModel handlingModel) {
		
		Handling handling = new Handling();
		handling.setName(handlingModel.getName());
		Status status = statusService.get(handlingModel.getStatusId());
		handling.setStatus(status);
		return handling;
	}

	@Override
	public Object update(Long id, CategoryReq categoryReq) {
		logger.info("[CategoryServiceImpl] [update] : Srvice: Enter");
		Category category = null;
		try {
			//category = categoryDao.findById(id);
			// if (category != null) {

			category.setName(categoryReq.getName());

			Status status = statusService.get(categoryReq.getStatusId());
			category.setStatus(status);

			Type highlight = typeService.get(categoryReq.getHighlightId());
			category.setHighLight(highlight);

			Type type = typeService.get(categoryReq.getTypeId());
			category.setType(type);

			//category = categoryDao.update(category);
			// }

		} catch (Exception e) {
			logger.info("Exception inside CategoryServiceImpl updateCategory() :"
					+ e.getMessage());
			return createFailedObject(
					CommonProperties.category_unable_to_update_message);
		}
		logger.info("[CategoryServiceImpl] [update] : Srvice: Exit");
		return createSuccessObject(CommonProperties.category_updated_message);
	}

	@Override
	public Object delete(Long id) {
		
		logger.info("HandlingServiceImpl delete() starts.");
		Object obj = null;
		String message ="Record deleted successfully.";
		
		try {
			Handling handling = handlingDao.findById(id);
			if(handling != null){
				handlingDao.delete(handling);
				obj = createSuccessObject(message);
			} else{
				message ="Error while deleting record";
				return createFailedObject(message);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside HandlingServiceImpl delete() : " + e.getMessage());
			message ="Error while deleting record";
			return createFailedObject(message);
		}
		
		logger.info("HandlingServiceImpl delete() ends.");
		return obj;
	}



	@Override
	public CategoryReq get(Long id) {
		logger.info("[CategoryServiceImpl] [get] : Srvice: Enter");
		Session session = null;
		CategoryReq categoryReq = new CategoryReq();

		try {

			session = sessionFactory.openSession();
			Category category =null;// categoryDao.findById(id, session);

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
	public HandlingModel getOpenAdd() {
		logger.info("HandlingServiceImpl getOpenAdd() starts ");
		HandlingModel handlingModel = new HandlingModel();

		List<Status> statusList = statusService.getAll();
		handlingModel.setStatusList(statusList);
		
		logger.info("HandlingServiceImpl getOpenAdd() ends ");
		return handlingModel;
	}

	@Override
	public List<CategoryReq> getCategoryByCategoryName(String categoryName) {
		logger.info("[CategoryServiceImpl] [getCategoryByCategoryName] : Srvice: Enter");
		Session session = null;
		List<CategoryReq> categories = new ArrayList<CategoryReq>();

		try {
			session = sessionFactory.openSession();
			List<Category> categoryList = null;//categoryDao
					//.getCategoryByCategoryName(session, categoryName);
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
		Category category = null;//categoryDao.findById(categoryId);
		return category;
	}

	@Override
	public List<CategoryReq> getSpecificData() {
		List<Object[]> categoryData = null;//categoryDao.getSpecificData("Category",
				//"categoryId", "name");

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
