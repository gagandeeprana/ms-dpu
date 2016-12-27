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

import com.dpu.common.CommonProperties;
import com.dpu.constants.Iconstants;
import com.dpu.entity.Driver;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.DriverService;
import com.dpu.util.MessageProperties;


/**
 * @author sumit
 *
 */
@RestController
@RequestMapping(value = "driver")
public class DriverController extends MessageProperties  {

	Logger logger = Logger.getLogger(DriverController.class);

	@Autowired
	DriverService driverService;

	ObjectMapper mapper = new ObjectMapper();

	// get List Of All Drivers
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllDrivers() {
		logger.info("[getAllDrivers]:Controller:  Enter");
		String json = null;
		try {
			List<Driver> listOfDriver = driverService.getAllDriver();
			json = mapper.writeValueAsString(listOfDriver);
		} catch (Exception e) {
			logger.error("[getAllDrivers]:Controller " + e);
		}
		logger.info("[getAllDrivers]:Controller: Exit");
		return json;
	}

	// Add new Driver
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Object> addDriver(@RequestBody Driver driver) {
		
		logger.info("[addDriver]:Controller:  Enter");
		ResponseEntity<Object> obj =  null;
		  
		try {
			boolean result = driverService.addDriver(driver);
			if(result){
				 obj = new ResponseEntity<Object>(new Success(Integer.parseInt(CommonProperties.Driver_added_code), CommonProperties.Driver_added_message, Iconstants.SUCCESS), HttpStatus.OK);
			}else{
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_add_code), CommonProperties.Driver_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("[addDriver]:" + e);
			obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_add_code), CommonProperties.Driver_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		logger.info("[addDriver]:Controller:  Exit");
		return obj;
	}

	// delete Driver
	@RequestMapping(value = "/{driverCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteDriver(@PathVariable("driverCode") String driverCode) {
		logger.info("[deleteDriver] : controller : Enter : driverCode "
				+ driverCode);
		Object  obj = null;
		try {
			boolean result = driverService.deleteDriver(driverCode);
			if(result) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(CommonProperties.Driver_deleted_code), CommonProperties.Driver_deleted_message, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_delete_code), CommonProperties.Driver_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("[deleteDriver] : controller : Exception" + e);
			obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_delete_code), CommonProperties.Driver_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		return obj;
	}

	// Update Driver
	@RequestMapping(value = "/{driverCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object updateDriver(@PathVariable("driverCode") String driverCode, @RequestBody Driver driver) {

		logger.info("[updateDriver]:Enter Controller: driverCode : "+ driverCode);
		Object obj = null;
		try {
			boolean result = driverService.updateDriver(driverCode, driver);

			if(result) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(CommonProperties.Driver_updated_code), CommonProperties.Driver_updated_message, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_update_code), CommonProperties.Driver_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("[updateDriver]: Exception "+e);
			obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_update_code), CommonProperties.Driver_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		return obj;
	}

	// get Driver by Id
	@RequestMapping(value = "/{driverCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getDriverByDriverCode(@PathVariable("driverCode") String driverCode) {
		logger.info("[getDriverByDriverCode] : Controller : Enter");
		String json = null;
		try {
			Driver driver = driverService.getDriverByDriverCode(driverCode);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(driver);
		} catch (Exception e) {
			logger.error("[getDriverByDriverCode]:" + e);
		}
		return json;
	}
}
