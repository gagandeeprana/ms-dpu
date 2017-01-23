
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
import com.dpu.entity.Category;
import com.dpu.model.CategoryReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CategoryService;
import com.dpu.util.MessageProperties;

/**
 * @author jagvir
 *
 */

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
			System.out.println(e);
			logger.error("Exception inside CategoryController add() ");
		}
		logger.info("[add]: Exit : obj : " + obj);
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {
		logger.info("[delete]: Enter : ID : " + id);
		Object obj = null;
		boolean result = false;
		try {
			Category category = null;
			//categoryService.get(id);
			if (category != null) {
				result = categoryService.delete(category);
			}
			if (result) {
				obj = new ResponseEntity<Object>(
						new Success(Integer.parseInt(categoryDeletedCode), categoryDeletedMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(categoryUnableToDeleteCode),
						categoryUnableToDeleteMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[delete]: Exit ");
		return obj;

	}

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
		
		logger.info("Inside CategoryController update() Starts, id is :" + id);
		return obj;
	}

	// get Category by Id
	@RequestMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCategoryById(@PathVariable("categoryId") Long id) {
		logger.info("Inside CategoryController getCategoryById() Starts, Id:" + id);
		String json = null;
		try {
			CategoryReq categoryReq = categoryService.get(id);
			//ObjectMapper mapper = new ObjectMapper();

			//CategoryReq response = new CategoryReq();
			//BeanUtils.copyProperties(response, category);

			if (categoryReq != null) {
				json = mapper.writeValueAsString(categoryReq);
			}
		} catch (Exception e) {
			logger.error("[getCategoryById]:" + e);
			System.out.println(e);
		}
		logger.info("[getCategoryById]: Exit: ");
		return json;
	}


	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		logger.info(" Inside ServiceController openAdd() Starts ");
		String json = null;
		try {
			CategoryReq CategoryReq = categoryService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(CategoryReq);
		} catch (Exception e) {
			logger.error(" Exception inside ServiceController openAdd() :"+e);
		}
		logger.info(" Inside ServiceController openAdd() Ends ");
		return json;
	}
}
