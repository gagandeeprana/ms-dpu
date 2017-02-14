package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CategoryDao;
import com.dpu.dao.OrderDao;
import com.dpu.entity.Category;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.CategoryReq;
import com.dpu.model.CompanyResponse;
import com.dpu.model.Failed;
import com.dpu.model.OrderModel;
import com.dpu.model.ShipperResponse;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.CompanyService;
import com.dpu.service.OrderService;
import com.dpu.service.ShipperService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Component
public class OrderServiceImpl implements OrderService {

	Logger logger = Logger.getLogger(OrderServiceImpl.class);

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	StatusService statusService;
	
	@Autowired
	TypeService typeService;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	ShipperService shipperService;
	
/*	@Override
	public Object addCategory(CategoryReq categoryReq) {
	
		logger.info("Inside CategoryServiceImpl addCategory() starts ");
		Object obj = null;
		String message = "Record Added Successfully";
		
		try {
			Category category = setCategoryValues(categoryReq);
			categoryDao.save(category);
			obj = createSuccessObject(message);

		} catch (Exception e) {
			logger.info("Exception inside CategoryServiceImpl addCategory() :"+e.getMessage());
			message = "Error while inserting record";
			obj = createFailedObject(message);
		}
		
		logger.info("Inside CategoryServiceImpl addCategory() ends ");
		return obj;
	}*/
	
	@Override
	public Object addOrder(OrderModel orderModel) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Object createSuccessObject(String message) {
		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAll());
		return success;
	}
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
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
	public List<CategoryReq> delete(Long id) {
		
		Category category = categoryDao.findById(id);
		List<CategoryReq> returnList = new ArrayList<CategoryReq>();
		if(category != null){
			categoryDao.delete(category);
			returnList = getAll();
		}
		return returnList;
	}

	@Override
	public List<CategoryReq> getAll() {

		Session session = null;
		List<CategoryReq> categoriesList = new ArrayList<CategoryReq>();
		
		try{
			
			session = sessionFactory.openSession();
			List<Category> categories = categoryDao.findAll(session);
			
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
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return categoriesList;
	}

	@Override
	public CategoryReq get(Long id) {
		
		Session session = null;
		CategoryReq categoryReq = new CategoryReq();
		
		try{
			
			session = sessionFactory.openSession();
			Category category = categoryDao.findById(id, session);
			
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
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return categoryReq;
	}

	@Override
	public OrderModel getOpenAdd() {

		OrderModel order = new OrderModel();
		
		@SuppressWarnings("unused")
		List<Status> statusList = statusService.getAll();
		
		List<TypeResponse> temperatureList = typeService.getAll(12l);
		order.setTemperatureList(temperatureList);
		
		List<TypeResponse> temperatureTypeList = typeService.getAll(13l);
		order.setTemperatureTypeList(temperatureTypeList);
		
		List<CompanyResponse> companyData = companyService.getCompanyData();
		order.setCompanyList(companyData);
		
		List<TypeResponse> currencyList = typeService.getAll(9l);
		order.setCurrencyList(currencyList);
		
		List<ShipperResponse> shipperConsineeList = shipperService.getSpecificData();
		order.setShipperConsineeList(shipperConsineeList);
		
		List<TypeResponse> pickUpTypes = typeService.getAll(10l);
		order.setPickupList(pickUpTypes);
		
		List<TypeResponse> deliveryTypes = typeService.getAll(11l);
		order.setDeliveryList(deliveryTypes);
		
		return order;
	}

	@Override
	public List<CategoryReq> getCategoryByCategoryName(String categoryName) {

		Session session = null;
		List<CategoryReq> categories = new ArrayList<CategoryReq>();
		
		try{
			session = sessionFactory.openSession();
			List<Category> categoryList = categoryDao.getCategoryByCategoryName(session, categoryName);
			if(categoryList != null && !categoryList.isEmpty()){
				for (Category category : categoryList) {
					CategoryReq categoryObj = new CategoryReq();
					categoryObj.setCategoryId(category.getCategoryId());
					categoryObj.setName(category.getName());
					categoryObj.setHighlightName(category.getHighLight().getTypeName());
					categoryObj.setTypeName(category.getType().getTypeName());
					categoryObj.setStatusName(category.getStatus().getStatus());
					categories.add(categoryObj);
				}
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return categories;
	}

	@Override
	public Category getCategory(Long categoryId) {
		Category category = categoryDao.findById(categoryId);
		return category;
	}

	@Override
	public List<CategoryReq> getSpecificData(){
		List<Object[]> categoryData = categoryDao.getSpecificData("Category","categoryId","name");
		
		List<CategoryReq> categories = new ArrayList<CategoryReq>();
		if(categoryData != null && !categoryData.isEmpty()){
			for (Object[] row : categoryData) {
				CategoryReq categoryObj = new CategoryReq();
				categoryObj.setCategoryId((Long) row[0]);
				categoryObj.setName(String.valueOf(row[1]));
				categories.add(categoryObj);
			}
		}
		
		return categories;
	}

	@Override
	public CompanyResponse getCompanyData(Long companyId) {
		CompanyResponse companyResponse = companyService.getCompanyBillingLocationAndContacts(companyId);
		return companyResponse;
	}

	

	
}
