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
import com.dpu.model.EmployeeModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.VehicleMaintainanceCategoryModel;
import com.dpu.service.EmployeeService;
import com.dpu.service.VehicleMaintainanceCategoryService;

@RestController
@RequestMapping(value = "employee")
public class EmployeeController {

	Logger logger = Logger.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${vmc_unable_to_add_message}")
	private String vmc_unable_to_add_message;

	@Value("${vmc_unable_to_delete_message}")
	private String vmc_unable_to_delete_message;
	
	@Value("${vmc_unable_to_update_message}")
	private String vmc_unable_to_update_message;

	/**
	 * this method is used to get all vmc's
	 * @return List<vmc>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside EmployeeController getAll() Starts ");
		String json = null;

		try {
			List<EmployeeModel> responses = employeeService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside EmployeeController getAll() :"+ e.getMessage());
		}
		
		logger.info("Inside EmployeeController getAll() Ends ");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody EmployeeModel employeeModel) {

		logger.info("Inside EmployeeController add() starts ");
		Object obj = null;
		
		try {

			Object result = employeeService.add(employeeModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside EmployeeController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,vmc_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside EmployeeController add() ends ");
		return obj;
	}

	/**
	 * this method is used to delete particular vmc
	 * @param vmcId
	 * @return List<vmc>
	 */
	/*@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long vmcId) {

		logger.info("Inside VehicleMaintainanceCategoryController delete() Starts, vmcId is :" + vmcId);
		Object obj = null;

		try {
			Object result = vehicleMaintainanceCategoryService.delete(vmcId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside VehicleMaintainanceCategoryController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,vmc_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		logger.info("Inside VehicleMaintainanceCategoryController delete() Ends, vmcId is :" + vmcId);
		return obj;

	}*/


	/**
	 * this method is used to update vmc based on vmcId
	 * @param vmcId
	 * @param vehicleMaintainanceCategoryModel
	 * @return List<vmc>
	 */
	/*@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long vmcId, @RequestBody VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel) {

		logger.info("Inside VehicleMaintainanceCategoryController update() Starts, vmcId is :" + vmcId);
		Object obj = null;
		try {
			Object result = vehicleMaintainanceCategoryService.update(vmcId, vehicleMaintainanceCategoryModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside VehicleMaintainanceCategoryController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,vmc_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside VehicleMaintainanceCategoryController update() Ends, vmcId is :" + vmcId);
		return obj;
	}*/

	@RequestMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getvmcById(@PathVariable("userId") Long userId) {
		
		logger.info("Inside EmployeeController getvmcById() Starts, vmcId:"+ userId);
		String json = null;

		try {

			EmployeeModel employeeModel = (EmployeeModel) employeeService.getUserById(userId);

			if (employeeModel != null) {
				json = mapper.writeValueAsString(employeeModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside EmployeeController getvmcById() :"+ e.getMessage());
		}

		logger.info("Inside EmployeeController getvmcById() Ends, vmcId:"+ userId);
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
	 * this method is used to search vmc based on vmc name
	 * @param vmcName
	 * @return List<vmc>
	 */
	/*@RequestMapping(value = "/{vmcName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchVmc(@PathVariable("vmcName") String vmcName) {

		logger.info("Inside VehicleMaintainanceCategoryController searchVmc() Starts, vmcName :"+ vmcName);
		String json = new String();

		try {
			List<VehicleMaintainanceCategoryModel> vmcList = vehicleMaintainanceCategoryService.getVmcByVmcName(vmcName);
			if (vmcList != null && vmcList.size() > 0) {
				json = mapper.writeValueAsString(vmcList);
			}
		} catch (Exception e) {
			logger.error("Exception inside VehicleMaintainanceCategoryController searchVmc() is :"+ e.getMessage());
		}

		logger.info(" Inside VehicleMaintainanceCategoryController searchVmc() Ends, vmcName :"+ vmcName);
		return json;
	}*/

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