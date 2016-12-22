package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.entity.Driver;
import com.dpu.service.DriverService;


/**
 * @author sumit
 *
 */
@RestController
@RequestMapping(value = "driver")
public class DriverController {

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
	public Object addDriver(@RequestBody Driver driver) {
		logger.info("[addDriver]:Controller:  Enter");
		String json = null;
		try {
			boolean result = driverService.addDriver(driver);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {
			logger.error("[addDriver]:" + e);
		}
		logger.info("[addDriver]:Controller:  Exit");
		return json;
	}

	// delete Driver
	@RequestMapping(value = "/{driverCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteDriver(@PathVariable("driverCode") String driverCode) {
		logger.info("[deleteDriver] : controller : Enter : driverCode "
				+ driverCode);
		String json = null;
		try {
			boolean result = driverService.deleteDriver(driverCode);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {
			logger.error("[deleteDriver] : controller : Exception" + e);
		}
		return json;
	}

	// Update Driver
	@RequestMapping(value = "/{driverCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object updateDriver(@PathVariable("driverCode") String driverCode, @RequestBody Driver driver) {

		logger.info("[updateDriver]:Enter Controller: driverCode : "+ driverCode);
		String json = null;
		try {
			boolean result = driverService.updateDriver(driverCode, driver);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {
			logger.error("[updateDriver]: Exception "+e);
		}
		return json;
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
