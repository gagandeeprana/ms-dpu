
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
import com.dpu.model.Failed;
import com.dpu.service.CategoryService;
import com.dpu.util.MessageProperties;


@RestController
@RequestMapping(value = "category")
public class CategoryController extends MessageProperties {

	Logger logger = Logger.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * this method is used to get all categories
	 * @return List<Categories>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("Inside CategoryController getAll() Starts ");
		String json = null;
		
		try {
			List<CategoryReq> responses = categoryService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside CategoryController getAll()"+e.getMessage());
		}
		logger.info("Inside CategoryController getAll() Ends, json :" + json);
		return json;
	}

	/**
	 * this method is used to add the category
	 * @param categoryReq
	 * @return List<category>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CategoryReq categoryReq) {

		logger.info("Inside CategoryController add() starts ");
		Object obj = null;
		try {

			List<CategoryReq> categoryList = categoryService.addCategory(categoryReq);

			if (categoryList != null) {
				obj = categoryList;
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(categoryUnableToAddCode),
						categoryUnableToAddMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside CategoryController add() :"+e.getMessage());
		}
		logger.info("Inside CategoryController add() Ends");
		return obj;
	}

	/**
	 * this method is used to delete the category based on categoryId
	 * @param id
	 * @return List<category>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		
		logger.info("Inside CategoryController delete() Starts, id is :" + id);
		Object obj = null;
		
		try {
			List<CategoryReq> categoryReq = null;
			categoryReq = categoryService.delete(id);
			
			if (categoryReq != null && categoryReq.size() > 0) {
				obj = categoryReq;
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(categoryUnableToDeleteCode),
						categoryUnableToDeleteMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController delete() :"+e.getMessage());
		}
		logger.info("Inside CategoryController delete() Ends, id is :" + id);
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
			List<CategoryReq> response = categoryService.update(id, categoryReq);
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
	 * this method is used to get category data based on categoryId
	 * @param id
	 * @return categorydata
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCategoryById(@PathVariable("categoryId") Long id) {
		
		logger.info("Inside CategoryController getCategoryById() Starts, Id:" + id);
		String json = null;
		
		try {
			
			CategoryReq categoryReq = categoryService.get(id);

			if (categoryReq != null) {
				json = mapper.writeValueAsString(categoryReq);
			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController getCategoryById() :" + e.getMessage());
		}
		
		logger.info("Inside CategoryController getCategoryById() Ends, Id:" + id);
		return json;
	}

	/**
	 * this method is used when we click on add button on category screen to send master data
	 * @return master data for add category
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info(" Inside CategoryController openAdd() Starts ");
		String json = null;
		
		try {
			CategoryReq CategoryReq = categoryService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(CategoryReq);
		} catch (Exception e) {
			logger.error(" Exception inside CategoryController openAdd() :"+e.getMessage());
		}
		
		logger.info(" Inside CategoryController openAdd() Ends ");
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
}
