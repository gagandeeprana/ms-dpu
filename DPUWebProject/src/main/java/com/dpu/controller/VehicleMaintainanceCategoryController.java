package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.constants.Iconstants;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.VehicleMaintainanceCategoryModel;
import com.dpu.service.VehicleMaintainanceCategoryService;

@RestController
@RequestMapping(value = "vmc")
public class VehicleMaintainanceCategoryController {

	Logger logger = Logger.getLogger(VehicleMaintainanceCategoryController.class);

	@Autowired
	VehicleMaintainanceCategoryService vehicleMaintainanceCategoryService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${handling_unable_to_add_message}")
	private String handling_unable_to_add_message;

	@Value("${handling_unable_to_delete_message}")
	private String handling_unable_to_delete_message;
	
	@Value("${handling_unable_to_update_message}")
	private String handling_unable_to_update_message;

	/**
	 * this method is used to get all handlings
	 * @return List<Handlings>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside VehicleMaintainanceCategoryController getAll() Starts ");
		String json = null;

		try {
			List<VehicleMaintainanceCategoryModel> responses = vehicleMaintainanceCategoryService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside HandlingController getAll() :"+ e.getMessage());
		}
		
		logger.info("Inside HandlingController getAll() Ends ");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel) {

		logger.info("Inside VehicleMaintainanceCategoryController add() starts ");
		Object obj = null;
		
		try {

			Object result = vehicleMaintainanceCategoryService.addVMC(vehicleMaintainanceCategoryModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside VehicleMaintainanceCategoryController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,handling_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside VehicleMaintainanceCategoryController add() ends ");
		return obj;
	}

	/**
	 * this method is used to delete the Handling based on handlingId
	 * @param id
	 * @return List<Handling>
	 * @author lakhvir.bansal
	 */
	/*@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
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
			obj = new ResponseEntity<Object>(new Failed(0,handling_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		logger.info("Inside CategoryController delete() Ends, id is :" + handlingId);
		return obj;

	}

	/**
	 * this method is used to update the handling based on handlingID
	 * @param handlingId
	 * @param handlingModel
	 * @return List<Categories>
	 */
/*	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long handlingId, @RequestBody HandlingModel handlingModel) {

		logger.info("Inside HandlingController update() Starts, handlingId is :" + handlingId);
		Object obj = null;
		try {
			Object result = handlingService.update(handlingId, handlingModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside HandlingController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,handling_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside HandlingController update() Ends, handlingId is :" + handlingId);
		return obj;
	}
*/
	/**
	 * this method is used to get Handling data based on handlingId
	 * @param handlingId
	 * @return handlingData
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{vmcId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getvmcById(@PathVariable("vmcId") Long vmcId) {
		
		logger.info("Inside HandlingController getHandlingById() Starts, Id:"+ vmcId);
		String json = null;

		try {

			VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel = vehicleMaintainanceCategoryService.get(vmcId);

			if (vehicleMaintainanceCategoryModel != null) {
				json = mapper.writeValueAsString(vehicleMaintainanceCategoryModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside HandlingController getHandlingById() :"+ e.getMessage());
		}

		logger.info("Inside HandlingController getHandlingById() Ends, Id:"+ vmcId);
		return json;
	}

	/**
	 * this method is used when we click on add button on handling screen
	 * send master data
	 * @return master data for add handling
	 * @author lakhvir.bansal
	 */
/*	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info("Inside HandlingController openAdd() Starts ");
		String json = null;

		try {
			HandlingModel model = handlingService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside HandlingController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside HandlingController openAdd() ends ");
		return json;
	}*/

	/**
	 * this method is used to get handling data based on handling name
	 * @param handlingName
	 * @return List<Handling>
	 * @author lakhvir.bansal
	 */
	/*@RequestMapping(value = "/{handlingName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchHandling(@PathVariable("handlingName") String handlingName) {

		logger.info("Inside HandlingController searchHandling() Starts, handlingName :"+ handlingName);
		String json = new String();

		try {
			List<HandlingModel> handlingList = handlingService.getHandlingByHandlingName(handlingName);
			if (handlingList != null && handlingList.size() > 0) {
				json = mapper.writeValueAsString(handlingList);
			}
		} catch (Exception e) {
			logger.error("Exception inside HandlingController searchHandling() is :"+ e.getMessage());
		}

		logger.info(" Inside HandlingController searchHandling() Ends, handlingName :"+ handlingName);
		return json;
	}
*/
	/**
	 * this method is used to get specific handling data (id and name)
	 * @return handlingId and name
	 * @author lakhvir.bansal
	 */
	
	/*@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("Inside HandlingController getSpecificData() Starts ");
		String json = new String();

		try {
			List<HandlingModel> handlingList = handlingService.getSpecificData();
			if (handlingList != null && handlingList.size() > 0) {
				json = mapper.writeValueAsString(handlingList);
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Exception inside HandlingController getSpecificData() is :"+ e.getMessage());
		}

		logger.info("Inside HandlingController getSpecificData() Ends ");
		return json;
	}*/
}
