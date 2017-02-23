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
import com.dpu.model.HandlingModel;
import com.dpu.model.Success;
import com.dpu.service.HandlingService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "handling")
public class HandlingController extends MessageProperties {

	Logger logger = Logger.getLogger(HandlingController.class);

	@Autowired
	HandlingService handlingService;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * this method is used to get all handlings
	 * @return List<Handlings>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside HandlingController getAll() Starts ");
		String json = null;

		try {
			List<HandlingModel> responses = handlingService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside HandlingController getAll() :"+ e.getMessage());
		}
		
		logger.info("Inside HandlingController getAll() Ends ");
		return json;
	}

	/**
	 * this method is used to add the Handling
	 * @param handlingModel
	 * @return List<Handling>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody HandlingModel handlingModel) {

		logger.info("Inside HandlingController add() starts ");
		Object obj = null;
		
		try {

			Object result = handlingService.addHandling(handlingModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside HandlingController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,"Error while inserting record.", Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside HandlingController add() ends ");
		return obj;
	}

	/**
	 * this method is used to delete the Handling based on handlingId
	 * @param id
	 * @return List<Handling>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long handlingId) {

		logger.info("Inside HandlingController delete() Starts, handlingId is :" + handlingId);
		Object obj = null;

		try {
			Object result = handlingService.delete(handlingId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside HandlingController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,"Error while deleting record.", Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		logger.info("Inside CategoryController delete() Ends, id is :" + handlingId);
		return obj;

	}

	/**
	 * this method is used to update the categoryData based on categoryID
	 * 
	 * @param id
	 * @param categoryReq
	 * @return List<Categories>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id,
			@RequestBody CategoryReq categoryReq) {
		logger.info("[CategoryController] [update] : Srvice: Enter");
		logger.info("Inside CategoryController update() Starts, id is :" + id);
		Object obj = null;
		try {
			Object result = handlingService.update(id, categoryReq);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController update() :"
					+ e.getMessage());
		}

		logger.info("Inside CategoryController update() Ends, id is :" + id);
		logger.info("[CategoryController] [update] : Srvice: Exit");
		return obj;
	}

	/**
	 * this method is used to get category data based on categoryId
	 * 
	 * @param id
	 * @return categorydata
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCategoryById(@PathVariable("categoryId") Long id) {
		logger.info("[CategoryController] [getCategoryById] : Srvice: Enter");
		logger.info("Inside CategoryController getCategoryById() Starts, Id:"
				+ id);
		String json = null;

		try {

			CategoryReq categoryReq = handlingService.get(id);

			if (categoryReq != null) {
				json = mapper.writeValueAsString(categoryReq);
			}
		} catch (Exception e) {
			logger.error("Exception inside CategoryController getCategoryById() :"
					+ e.getMessage());
		}

		logger.info("Inside CategoryController getCategoryById() Ends, Id:"
				+ id);
		logger.info("[CategoryController] [getCategoryById] : Srvice: Exit");
		return json;
	}

	/**
	 * this method is used when we click on add button on category screen to
	 * send master data
	 * 
	 * @return master data for add category
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info("Inside HandlingController openAdd() Starts ");
		String json = null;

		try {
			HandlingModel model = handlingService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside CategoryController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside HandlingController openAdd() ends ");
		return json;
	}

	/**
	 * this method is used to get category based on category name
	 * 
	 * @param categoryName
	 * @return List<category>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{categoryName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchCategory(
			@PathVariable("categoryName") String categoryName) {
		logger.info("[CategoryController] [searchCategory] : Srvice: Enter");
		logger.info("Inside CategoryController searchCategory() Starts, categoryName :"
				+ categoryName);
		String json = new String();

		try {
			List<CategoryReq> categoryList = handlingService
					.getCategoryByCategoryName(categoryName);
			if (categoryList != null && categoryList.size() > 0) {
				json = mapper.writeValueAsString(categoryList);
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Exception inside CategoryController searchCategory() is :"
					+ e.getMessage());
		}

		logger.info(" Inside CategoryController searchCategory() Ends, categoryName :"
				+ categoryName);
		logger.info("[CategoryController] [searchCategory] : Srvice: Exit");
		return json;
	}

	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("[CategoryController] [getSpecificData] : Srvice: Enter");
		String json = new String();

		try {
			List<CategoryReq> categoryList = handlingService.getSpecificData();
			if (categoryList != null && categoryList.size() > 0) {
				json = mapper.writeValueAsString(categoryList);
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Exception inside CategoryController searchCategory() is :"
					+ e.getMessage());
		}

		logger.info(" Inside CategoryController searchCategory() Ends, categoryName :");
		logger.info("[CategoryController] [getSpecificData] : Srvice: Exit");
		return json;
	}
}
