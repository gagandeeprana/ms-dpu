package com.dpu.service;

import java.util.List;

import com.dpu.entity.Category;
import com.dpu.model.CategoryReq;
import com.dpu.model.CompanyResponse;
import com.dpu.model.OrderModel;
import com.dpu.model.ProbilModel;
public interface OrderService {
	/*Object addCategory(CategoryReq categoryReq);*/

	List<CategoryReq> update(Long id, CategoryReq categoryReq);

	List<OrderModel> getAllOrders();

	OrderModel getOpenAdd();

	List<CategoryReq> getCategoryByCategoryName(String categoryName);
	
	Category getCategory(Long categoryId);

	List<CategoryReq> getSpecificData();

	CompanyResponse getCompanyData(Long companyId);

	Object addOrder(OrderModel orderModel);

	ProbilModel getProbilByProbilId(Long probilId);

	OrderModel getOrderByOrderId(Long orderId);

	Object deleteOrder(Long orderId);

}
