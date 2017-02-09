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
import com.dpu.model.DPUService;
import com.dpu.model.DriverReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.DriverService;
import com.dpu.util.MessageProperties;


@RestController
@RequestMapping(value = "driver")
public class DriverController extends MessageProperties  {

	Logger logger = Logger.getLogger(DriverController.class);

	@Autowired
	DriverService driverService;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * this method is used to get all the drivers
	 * @return List<drivers>
	 * @author lakhvir
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllDrivers() {
		logger.info("Inside DriverController getAllDrivers() Starts");
		String json = null;
		try {
			
			List<DriverReq> lstdrivers = driverService.getAllDriver();

			if (lstdrivers != null && !lstdrivers.isEmpty()) {
				json = mapper.writeValueAsString(lstdrivers);
			}
		} catch (Exception e) {
			logger.error("Exception inside DriverController getAllDrivers() :" + e.getMessage());
		}
		logger.info("Inside DriverController getAllDrivers() Ends");
		return json;
	}

	/**
	 * this method is used to add new driver
	 * @param driverReq
	 * @return List<drivers>
	 * @author lakhvir
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Object> addDriver(@RequestBody DriverReq driverReq) {
		
		logger.info("Inside DriverController addDriver Starts. ");
		ResponseEntity<Object> obj =  null;
		  
		try {
			Object result = driverService.addDriver(driverReq);
			if(result != null){
				if(result instanceof Success){ 
					obj = new ResponseEntity<Object>(result, HttpStatus.OK);
				} else{
					obj = new ResponseEntity<Object>(new Failed(Integer.parseInt("1234"), "this driver code is already present", Iconstants.ERROR), HttpStatus.BAD_REQUEST);
				}
			}else{
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_add_code), CommonProperties.Driver_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("[addDriver]:" + e);
			obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_add_code), CommonProperties.Driver_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		logger.info("Inside DriverController addDriver Ends. ");
		return obj;
	}

	/**
	 * this method is used to delete the driver based on driverId
	 * @param driverId
	 * @return List<driver>
	 * @author lakhvir
	 */
	@RequestMapping(value = "/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteDriver(@PathVariable("driverId") Long driverId) {
		logger.info("Inside DriverController deleteDriver() : driverCode " + driverId);
		Object  obj = null;
		try {
			Object result = driverService.deleteDriver(driverId);
			if(result instanceof List<?>) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_delete_code), CommonProperties.Driver_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside DriverController deleteDriver() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_delete_code), CommonProperties.Driver_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside DriverController deleteDriver() Ends, driverId :" + driverId);
		return obj;
	}

	/**
	 * this method is used to update the driver based on driverId
	 * @param driverCode
	 * @param driver
	 * @return List<driver>
	 * @author lakhvir
	 */
	@RequestMapping(value = "/{driverCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object updateDriver(@PathVariable("driverCode") Long driverId, @RequestBody DriverReq driverReq) {

		logger.info("Inside DriverController updateDriver, driverId : "+ driverId);
		Object obj = null;
		try {
			Object result = driverService.updateDriver(driverId, driverReq);

			if(result instanceof List<?>) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_update_code), CommonProperties.Driver_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("[updateDriver]: Exception "+e);
			obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(CommonProperties.Driver_unable_to_update_code), CommonProperties.Driver_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		return obj;
	}

	/**
	 * this method is used to get the driver details based on driverId
	 * @param driverId
	 * @return driver
	 * @author lakhvir
	 */
	@RequestMapping(value = "/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getDriverByDriverId(@PathVariable("driverId") Long driverId) {
		logger.info("[getDriverByDriverCode] : Controller : Enter");
		Object obj = null;
		try {
			obj = driverService.getDriverByDriverId(driverId);
		} catch (Exception e) {
			logger.error("[getDriverByDriverCode]:" + e);
		}
		return obj;
	}
	
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		logger.info(" Inside driverController openAdd() Starts ");
		String json = null;
		try {
			DriverReq driverReq = driverService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(driverReq);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info(" Inside driverController openAdd() Ends ");
		return json;
	}
	
	@RequestMapping(value = "/{driverCodeOrName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchDriver(@PathVariable("driverCodeOrName") String driverCodeOrName) {
		logger.info("Inside driverController searchDriver() Starts, driverCodeOrName :"+driverCodeOrName);
		String json = new String();
		try {
			List<DriverReq> serviceList = driverService.getDriverByDriverCodeOrName(driverCodeOrName);
			if(serviceList != null && serviceList.size() > 0) {
				json = mapper.writeValueAsString(serviceList);
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Exception inside ServiceController searchService() is :" + e);
		}
		logger.info(" Inside ServiceController searchService() Starts, serviceName :"+driverCodeOrName);
		return json;
	}
}
