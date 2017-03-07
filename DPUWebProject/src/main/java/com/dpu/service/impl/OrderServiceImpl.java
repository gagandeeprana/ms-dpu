package com.dpu.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CategoryDao;
import com.dpu.dao.OrderDao;
import com.dpu.entity.Category;
import com.dpu.entity.Company;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.entity.Order;
import com.dpu.entity.OrderPickupDropNo;
import com.dpu.entity.Probil;
import com.dpu.entity.Shipper;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.CategoryReq;
import com.dpu.model.CompanyResponse;
import com.dpu.model.Failed;
import com.dpu.model.OrderModel;
import com.dpu.model.OrderPickUpDeliveryModel;
import com.dpu.model.ProbilModel;
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
	
	private Order addOrder(OrderModel orderModel, Session session) {
		Company company = (Company) session.get(Company.class, orderModel.getCompanyId());
		CompanyBillingLocation billingLocation = (CompanyBillingLocation) session.get(CompanyBillingLocation.class, orderModel.getBillingLocationId());
		CompanyAdditionalContacts additionalContacts = (CompanyAdditionalContacts) session.get(CompanyAdditionalContacts.class, orderModel.getContactId());
		Type temp = (Type) session.get(Type.class, orderModel.getTemperatureId());
		Type tempType = (Type) session.get(Type.class, orderModel.getTemperatureTypeId());
		Type currency = (Type) session.get(Type.class, orderModel.getCurrencyId());
		
		Order order = new Order();
		BeanUtils.copyProperties(orderModel, order);
		order.setCompany(company);
		order.setBillingLocation(billingLocation);
		order.setContact(additionalContacts);
		order.setTemperature(temp);
		order.setTemperatureType(tempType);
		order.setCurrency(currency);
		orderDao.saveOrder(session, order);
		return order;
		
	}
	
	private void addProbil(ProbilModel probilModel, Order order, Session session) {
		
		Probil probil = new Probil();
		Long maxProbilNo = orderDao.getMaxProbilNo(session);
		
		BeanUtils.copyProperties(probilModel, probil);
		Shipper shipper = (Shipper) session.get(Shipper.class, probilModel.getShipperId());
		Shipper consinee = (Shipper) session.get(Shipper.class, probilModel.getConsineeId());
		Type pickUp = (Type) session.get(Type.class, probilModel.getPickupId());
		Type delivery = (Type) session.get(Type.class, probilModel.getDeliveryId());
		
		probil.setConsine(consinee);
		probil.setShipper(shipper);
		probil.setPickUp(pickUp);
		probil.setDelivery(delivery);
		probil.setOrder(order);
		probil.setProbilNo(maxProbilNo+1);
		
		String pickUpScheduledDate = probilModel.getPickupScheduledDate();
		String pickUpMabDate = probilModel.getPickupMABDate();
		String deliveryScheduledDate = probilModel.getDeliverScheduledDate();
		String deliveryMabData = probilModel.getDeliveryMABDate();
		
		probil.setPickupScheduledDate(changeStringToDate(pickUpScheduledDate));
		probil.setPickupMABDate(changeStringToDate(pickUpMabDate));
		probil.setDeliverScheduledDate(changeStringToDate(deliveryScheduledDate));
		probil.setDeliveryMABDate(changeStringToDate(deliveryMabData));
		
		String pickUpScheduledTime = probilModel.getPickupScheduledTime();
		String pickUpMabTime = probilModel.getPickupMABTime();
		String deliveryScheduledTime = probilModel.getDeliverScheduledTime();
		String deliveryMabTime = probilModel.getDeliveryMABTime();
		
		probil.setDeliverScheduledTime(changeStringToTime(deliveryScheduledTime));
		probil.setDeliveryMABTime(changeStringToTime(deliveryMabTime));
		probil.setPickupScheduledTime(changeStringToTime(pickUpScheduledTime));
		probil.setPickupMABTime(changeStringToTime(pickUpMabTime));
		
		orderDao.saveProbil(session, probil);
		
		addPickUpDelivery(probilModel, probil, session);
	}
	
	private void addPickUpDelivery(ProbilModel probilModel, Probil probil, Session session) {
		List<OrderPickUpDeliveryModel> orderPickUpDeliveryList = probilModel.getOrderPickUpDeliveryList();
		
		if(orderPickUpDeliveryList != null && !orderPickUpDeliveryList.isEmpty()){
			
			for (OrderPickUpDeliveryModel orderPickUpDeliveryModel : orderPickUpDeliveryList) {
				OrderPickupDropNo pickUpDropNo = new OrderPickupDropNo();
				BeanUtils.copyProperties(orderPickUpDeliveryModel, pickUpDropNo);
				pickUpDropNo.setProbil(probil);
				
				orderDao.savePickUpDrop(session, pickUpDropNo);
			}
		}
		
	}

	@Override
	public Object addOrder(OrderModel orderModel) {
		
		logger.info("Inside OrderServiceImpl addOrder() starts ");
		String message = "Record Added Successfully";
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			Order order = addOrder(orderModel, session);
			
			List<ProbilModel> probils = orderModel.getProbilList();
			
			if(probils != null && !probils.isEmpty()){
				for (ProbilModel probilModel : probils) {
					addProbil(probilModel, order, session);
				}
			}
			
		} catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
			message="error while inserting record";
			return createFailedObject(message);
		} finally{
			if(tx != null){
				tx.commit();
			} 
			if(session != null){
				session.close();
			}
		}
		return createSuccessObject(message);
	}
	
	

	private Date changeStringToTime(String timeVal) {
		String[] stArr = timeVal.split(":");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(stArr[0]));
		cal.set(Calendar.MINUTE,Integer.parseInt(stArr[1]));
		cal.set(Calendar.SECOND,Integer.parseInt(stArr[2]));
		Date date = cal.getTime();
		return date;
	}

	private Date changeStringToDate(String dateVal) {
		String[] stArr = dateVal.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(stArr[0]), Integer.parseInt(stArr[1]), Integer.parseInt(stArr[2]));
		Date date = cal.getTime();
		return date;
}

	private Object createSuccessObject(String message) {
		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAllOrders());
		return success;
	}
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
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
		return null;//getAll();
	}

	@Override
	public Object deleteProbil(Long probilId) {

		logger.info("Inside OrderServiceImpl deleteProbil() starts, probilId :"+ probilId);
		String message = "Record Deleted Successfully";
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Probil probil = (Probil) session.get(Probil.class, probilId);
			if(probil != null){
				List<OrderPickupDropNo> orderPickUpDropNos = probil.getOrderPickupDropNos();
				if(orderPickUpDropNos != null && !orderPickUpDropNos.isEmpty()){
					for (OrderPickupDropNo orderPickupDropNo : orderPickUpDropNos) {
						session.delete(orderPickupDropNo);
					}
				}
				
				session.delete(probil);
			} else{
				message = "Error while deleting record.";
				return createFailedObject(message);
			}
		}catch(Exception e){
			logger.error("Exception Inside OrderServiceImpl deleteProbil() :"+ e.getMessage());
			if(tx != null){
				tx.rollback();
			}
			message = "Error while deleting record.";
			return createFailedObject(message);
		} finally{
			if(tx != null){
				tx.commit();
			}
			if(session != null){
				session.close();
			}
		}
		logger.info("Inside OrderServiceImpl deleteProbil() ends, probilId :"+ probilId);
		return createSuccessObject(message);
	}
	@Override
	public List<OrderModel> getAllOrders() {

		Session session = null;
		List<OrderModel> allOrders = new ArrayList<OrderModel>();
		
		try{
			
			session = sessionFactory.openSession();
			List<Order> orders = orderDao.findAll(session);
			
			if(orders != null && ! orders.isEmpty()){
				for (Order order : orders) {
					OrderModel orderModel = new OrderModel();
					BeanUtils.copyProperties(order, orderModel);
					orderModel.setCompanyName(order.getCompany().getName());
					orderModel.setBillingLocationName(order.getBillingLocation().getName());
					orderModel.setContactName(order.getContact().getCustomerName());
					orderModel.setTemperatureName(order.getTemperature().getTypeName());
					orderModel.setTemperatureTypeName(order.getTemperatureType().getTypeName());
					orderModel.setCurrencyName(order.getCurrency().getTypeName());
					
					/*List<Probil> probilList = order.getProbils();
					List<ProbilModel> probils = new ArrayList<ProbilModel>();
					for (Probil probil : probilList) {
						
						Date d = probil.getPickupScheduledDate();
								System.out.println("the date is "+d);
						ProbilModel probilModel = new ProbilModel();
						BeanUtils.copyProperties(probil, probilModel);
						probilModel.setConsineeName(probil.getConsine().getLocationName());
						probilModel.setShipperName(probil.getShipper().getLocationName());
						probilModel.setPickupName(probil.getPickUp().getTypeName());
						probilModel.setDeliveryName(probil.getDelivery().getTypeName());
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String pickUpScheduledDate = sdf.format(probil.getPickupScheduledDate());
						String deliverScheduledDate = sdf.format(probil.getDeliverScheduledDate());
						String pickUpMABDate = sdf.format(probil.getPickupMABDate());
						String deliverMABDate = sdf.format(probil.getDeliveryMABDate());
						
						probilModel.setPickupScheduledDate(pickUpScheduledDate);
						probilModel.setPickupMABDate(pickUpMABDate);
						probilModel.setDeliverScheduledDate(deliverScheduledDate);
						probilModel.setDeliveryMABDate(deliverMABDate);
						
						SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
						String pickUpScheduledTime = localDateFormat.format(probil.getPickupScheduledTime());
						String pickUpMABTime = localDateFormat.format(probil.getPickupMABTime());
						String deliverScheduledTime = localDateFormat.format(probil.getDeliverScheduledTime());
						String deliverMABTime =  localDateFormat.format(probil.getDeliveryMABTime());
						
						probilModel.setPickupScheduledTime(pickUpScheduledTime);
						probilModel.setPickupMABTime(pickUpMABTime);
						probilModel.setDeliverScheduledTime(deliverScheduledTime);
						probilModel.setDeliveryMABTime(deliverMABTime);
						probils.add(probilModel);
					}
					
					orderModel.setProbilList(probils);*/
					
					allOrders.add(orderModel);
				}
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return allOrders;
	}

/*	@Override
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
	}*/

	@Override
	public ProbilModel getProbilByProbilId(Long probilId) {
		
		ProbilModel probilModel = new ProbilModel();
		Session session = null;
		try{
			session = sessionFactory.openSession();
			Probil probil = (Probil) session.get(Probil.class, probilId);
			if(probil != null){
				List<OrderPickupDropNo> orderPickUpDropNos = probil.getOrderPickupDropNos();
				if(orderPickUpDropNos != null && !orderPickUpDropNos.isEmpty()){
					for (OrderPickupDropNo orderPickupDropNo : orderPickUpDropNos) {
						//session.delete(orderPickupDropNo);
					}
				}
				
				//session.delete(probil);
			}
		} finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("Inside OrderServiceImpl deleteProbil() ends, probilId :"+ probilId);
		return probilModel;
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
