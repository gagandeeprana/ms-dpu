
package com.dpu.controller;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
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

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		logger.info("Inside CategoryController getAll() Starts ");
		String json = null;
		try {
			List<CategoryReq> responses = categoryService.getAll();
			//List<CategoryReq> responses = new ArrayList<CategoryReq>();
			/*for (Category category : lstCategories) {
				CategoryReq response = new CategoryReq();
				BeanUtils.copyProperties(response, category);
				responses.add(response);
			}*/
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside CategoryController getAll()", e);
		}
		logger.info("Inside CategoryController getAll() Ends, json :" + json);
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CategoryReq categoryReq) {

		logger.info("[add]: Enter");
		Object obj = null;
		try {

			System.out.println(new ObjectMapper().writeValueAsString(categoryReq));
			Category category = setCategoryValues(categoryReq);
			boolean result = categoryService.addCategory(category);
			System.out.println("result value : " + result);

			if (result) {
				obj = new ResponseEntity<Object>(
						new Success(Integer.parseInt(categoryAddedCode), categoryAddedMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(categoryUnableToAddCode),
						categoryUnableToAddMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[add]: Exit : obj : " + obj);
		return obj;
	}

	private Category setCategoryValues(CategoryReq categoryReq) {
		Category category = new Category();
		category.setName(categoryReq.getName());
		//category.setStatus(categoryReq.getStatus());
		//category.setTypeId(categoryReq.getTypeId());
		//category.setHighlight(categoryReq.getHighlight());
		return category;
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
	public Object update(@PathVariable("id") int id, @RequestBody Category category) {
		logger.info("[update]: Enter: Id:  " + id);
		Object obj = null;
		try {
			//category.setCategoryId(id);
			Category response = categoryService.update(id, category);
			if (response != null) {
				obj = new ResponseEntity<Object>(
						new Success(Integer.parseInt(categoryUpdateCode), categoryUpdateMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(categoryUnableToUpdateCode),
						categoryUnableToUpdateMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[update]: Exit");
		return obj;
	}

	// get Category by Id
	@RequestMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCategoryById(@PathVariable("categoryId") Long id) {
		logger.info("[getCategoryById]: Enter: Id:  " + id);
		String json = null;
		try {
			CategoryReq categoryReq = categoryService.get(id);
			ObjectMapper mapper = new ObjectMapper();

			CategoryReq response = new CategoryReq();
			//BeanUtils.copyProperties(response, category);

			if (response != null) {
				json = mapper.writeValueAsString(response);
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
