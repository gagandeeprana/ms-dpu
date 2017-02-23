package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.constants.Iconstants;
import com.dpu.model.CategoryReq;
import com.dpu.model.CompanyResponse;
import com.dpu.model.Failed;
import com.dpu.model.OrderModel;
import com.dpu.model.ProbilModel;
import com.dpu.model.Success;
import com.dpu.service.CategoryService;
import com.dpu.service.OrderService;
import com.dpu.util.MessageProperties;


@RestController
@RequestMapping(value = "order")
public class OrderController extends MessageProperties {

	Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	OrderService orderService;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * this method is used to get all probils data
	 * @return List<probils>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("Inside OrderController getAll() Starts ");
		String json = null;
		
		try {
			List<OrderModel> responses = orderService.getAllOrders();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside OrderController getAll()"+e.getMessage());
		}
		logger.info("Inside OrderController getAll() Ends, json :" + json);
		return json;
	}

	/**
	 * this method is used to add the Order
	 * @param orderModel
	 * @return List<probils>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody OrderModel orderModel) {

		logger.info("Inside OrderController add() starts ");
		Object obj = null;
		try {

			Object response = orderService.addOrder(orderModel);
			if (response instanceof Success) {
				obj = new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside OrderController add() :"+e.getMessage());
		}
		logger.info("Inside OrderController add() Ends");
		return obj;
	}

	/**
	 * this method is used to delete the particular probil
	 * @param id
	 * @return List<Order>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/probil/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteProbil(@PathVariable("id") Long probilId) {
		
		logger.info("Inside OrderController deleteProbil() Starts, probilId is :" + probilId);
		Object obj = null;
		
		try {
			Object result = orderService.deleteProbil(probilId);
			
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(categoryUnableToDeleteCode),
					categoryUnableToDeleteMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			logger.error("Exception inside OrderController deleteProbil() :"+e.getMessage());
		}
		logger.info("Inside OrderController deleteProbil() Ends, probilId is :" + probilId);
		return obj;

	}

	/**
	 * this method is used to update the categoryData based on categoryID
	 * @param id
	 * @param categoryReq
	 * @return List<Categories>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody CategoryReq categoryReq) {
		logger.info("Inside CategoryController update() Starts, id is :" + id);
		Object obj = null;
		try {
			List<CategoryReq> response = null;
					//categoryService.update(id, categoryReq);
			if (response != null) {
				obj = response;
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(categoryUnableToUpdateCode),
						categoryUnableToUpdateMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController update() :"+e.getMessage());
		}
		
		logger.info("Inside CategoryController update() Ends, id is :" + id);
		return obj;
	}

	/**
	 * this method is used to get probil data based on probilId
	 * @param probilid
	 * @return probilData
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getProbilById(@PathVariable("categoryId") Long probilId) {
		
		logger.info("Inside CategoryController getCategoryById() Starts, probilId:" + probilId);
		String json = null;
		
		try {
			
			ProbilModel probilModel = orderService.getProbilByProbilId(probilId);

			if (probilModel != null) {
				json = mapper.writeValueAsString(probilModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController getCategoryById() :" + e.getMessage());
		}
		
		logger.info("Inside CategoryController getCategoryById() Ends, probilId:" + probilId);
		return json;
	}

	/**
	 * this method is used when we click on add button on Order screen to send master data
	 * @return master data for add Order
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info(" Inside OrderController openAdd() Starts ");
		String json = null;
		
		try {
			OrderModel orderModel = orderService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(orderModel);
		} catch (Exception e) {
			logger.error(" Exception inside OrderController openAdd() :"+e.getMessage());
		}
		
		logger.info(" Inside OrderController openAdd() Ends ");
		return json;
	}
	
	/**
	 * this method is used to get the specific company billingLocations and contacts
	 * @param companyId
	 * @return company related billingaccounts and contacts
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{companyId}/getData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCompanyData(@PathVariable("companyId") Long companyId) {
		
		logger.info(" Inside OrderController getCompanyData() Starts ");
		String json = null;
		
		try {
			CompanyResponse companyResponse = orderService.getCompanyData(companyId);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(companyResponse);
		} catch (Exception e) {
			logger.error(" Exception inside OrderController getCompanyData() :"+e.getMessage());
		}
		
		logger.info(" Inside OrderController getCompanyData() Ends ");
		return json;
	}
	
	/**
	 * this method is used to get category based on category name
	 * @param categoryName
	 * @return List<category>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{categoryName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchCategory(@PathVariable("categoryName") String categoryName) {
		
		logger.info("Inside CategoryController searchCategory() Starts, categoryName :"+categoryName);
		String json = new String();
		
		try {
			List<CategoryReq> categoryList = categoryService.getCategoryByCategoryName(categoryName);
			if(categoryList != null && categoryList.size() > 0) {
				json = mapper.writeValueAsString(categoryList);
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Exception inside CategoryController searchCategory() is :" + e.getMessage());
		}
		
		logger.info(" Inside CategoryController searchCategory() Ends, categoryName :"+categoryName);
		return json;
	}
	
	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {
		
		logger.info("Inside CategoryController searchCategory() Starts");
		String json = new String();
		
		try {
			List<CategoryReq> categoryList = categoryService.getSpecificData();
			if(categoryList != null && categoryList.size() > 0) {
				json = mapper.writeValueAsString(categoryList);
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Exception inside CategoryController searchCategory() is :" + e.getMessage());
		}
		
		logger.info(" Inside CategoryController searchCategory() Ends, categoryName :");
		return json;
	}
}
