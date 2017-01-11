/**
 * 
 */
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
import com.dpu.entity.Company;
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

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		logger.info("[getAll]: Enter");
		String json = null;
		try {
			List<Category> lstCategories = categoryService.getAll();
			json = mapper.writeValueAsString(lstCategories);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[getAll]: Exit: json :" + json);
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Category category) {
		logger.info("[add]: Enter");
		Object obj = null;
		try {
			Category result = categoryService.add(category);
			if (result != null) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(categoryAddedCode),categoryAddedMessage, Iconstants.SUCCESS),HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(categoryUnableToAddCode),categoryUnableToAddMessage, Iconstants.ERROR),HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[add]: Exit : obj : "+ obj);
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {
		logger.info("[delete]: Enter : ID : "+ id);
		Object obj = null;
		boolean result = false;
		try {
			Category category = categoryService.get(id);
			if (category != null) {
				result = categoryService.delete(category);
			}
			if (result) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(categoryDeletedCode),categoryDeletedMessage, Iconstants.SUCCESS),HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(categoryUnableToDeleteCode),categoryUnableToDeleteMessage, Iconstants.ERROR),HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[delete]: Exit ");
		return obj;

	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Category category) {
		logger.info("[update]: Enter: Id:  " +id);
		Object obj = null;
		try {
			category.setCategoryId(id);
			Category response = categoryService.update(id, category);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(categoryUpdateCode),categoryUpdateMessage, Iconstants.SUCCESS),HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(categoryUnableToUpdateCode),categoryUnableToUpdateMessage, Iconstants.ERROR),HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[update]: Exit");
		return obj;
	}

	@RequestMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("categoryId") int id) {
		logger.info("[get]: Enter: Id:  " +id);
		String json = null;
		try {
			Category category = categoryService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(category);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[get]: Exit: ");
		return json;
	}

}
