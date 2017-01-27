package com.dpu.controller;

import java.util.ArrayList;
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

import com.dpu.common.CommonProperties;
import com.dpu.constants.Iconstants;
import com.dpu.entity.Company;
import com.dpu.entity.Truck;
import com.dpu.model.CompanyResponse;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TruckResponse;
import com.dpu.service.TruckService;

@RestController
@RequestMapping(value = "truck")
public class TruckController {

	Logger logger = Logger.getLogger(TruckController.class);

	@Autowired
	TruckService truckService;

	ObjectMapper mapper = new ObjectMapper();

	// get List Of All Truck
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllTrucks() {
		logger.info("[getAllTrucks]:Controller:  Enter");
		String json = null;
		try {
			List<TruckResponse> listOfTrucks = truckService.getAllTrucks("");
			if (listOfTrucks != null && listOfTrucks.size() > 0) {
				json = mapper.writeValueAsString(listOfTrucks);
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("[getAllTrucks]:Controller " + e);
		}
		logger.info("[getAllTrucks]:Controller: Exit");
		return json;
	}

	// Add new Truck
	// @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method =
	// RequestMethod.POST)
	// public Object addTruck(@RequestBody TruckResponse truckResponse) {
	// logger.info("[addTruck]:Controller:  Enter");
	// Object obj = null;
	// try {
	//
	// System.out.println(new ObjectMapper().writeValueAsString(truckResponse));
	// Truck truck = setTruckValues(truckResponse);
	// boolean result = truckService.addTruck(truck);
	//
	// if(result){
	// obj = new ResponseEntity<Object>(new
	// Success(Integer.parseInt(CommonProperties.Truck_added_code),
	// CommonProperties.Truck_added_message, Iconstants.SUCCESS),
	// HttpStatus.OK);
	// }else{
	// obj = new ResponseEntity<Object>(new
	// Failed(Integer.parseInt(CommonProperties.Truck_unable_to_add_code),
	// CommonProperties.Truck_unable_to_add_message, Iconstants.ERROR),
	// HttpStatus.BAD_REQUEST);
	// }
	// } catch (Exception e) {
	// logger.error("[addTruck]:" + e);
	// obj = new ResponseEntity<Object>(new
	// Failed(Integer.parseInt(CommonProperties.Truck_unable_to_add_code),
	// CommonProperties.Truck_unable_to_add_message, Iconstants.ERROR),
	// HttpStatus.BAD_REQUEST);
	// }
	// logger.info("[addTruck]:Controller:  Exit");
	// return obj;
	// }
	//
	// private Truck setTruckValues(TruckResponse truckResponse) {
	// Truck truck = new Truck();
	// truck.setUnitNo(truckResponse.getUnitNo());
	// truck.setOwner(truckResponse.getOwner());
	// truck.setoOName(truckResponse.getoOName());
	// truck.setCategory(truckResponse.getCategory());
	// truck.setStatus(truckResponse.getStatus());
	// truck.setUsage(truckResponse.getUsage());
	// truck.setDivision(truckResponse.getDivision());
	// truck.setTerminal(truckResponse.getTerminal());
	// truck.setTruckType(truckResponse.getTruckType());
	// truck.setFinance(truckResponse.getFinance());
	// return truck;
	// }
	//
	// // delete Truck
	// @RequestMapping(value = "/{id}", produces =
	// MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	// public Object deleteTruck(@PathVariable("id") Long id) {
	// logger.info("[deleteTruck] : controller : Enter : driverCode "
	// + id);
	// Object obj = null;
	// try {
	// boolean result = truckService.deleteTruck(id);
	//
	// if(result) {
	// obj = new ResponseEntity<Object>(new
	// Success(Integer.parseInt(CommonProperties.Truck_deleted_code),
	// CommonProperties.Truck_deleted_message, Iconstants.SUCCESS),
	// HttpStatus.OK);
	// } else {
	// obj = new ResponseEntity<Object>(new
	// Failed(Integer.parseInt(CommonProperties.Truck_unable_to_delete_code),
	// CommonProperties.Truck_unable_to_delete_message, Iconstants.ERROR),
	// HttpStatus.BAD_REQUEST);
	// }
	// } catch (Exception e) {
	// logger.error("[deleteTruck] : controller : Exception" + e);
	// obj = new ResponseEntity<Object>(new
	// Failed(Integer.parseInt(CommonProperties.Truck_unable_to_delete_code),
	// CommonProperties.Truck_unable_to_delete_message, Iconstants.ERROR),
	// HttpStatus.BAD_REQUEST);
	// }
	// return obj;
	// }
	//
	// // Update Truck
	// @RequestMapping(value = "/{id}", produces =
	// MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	// public Object updateTruck(@PathVariable("id") Long id, @RequestBody Truck
	// truck) {
	//
	// logger.info("[updateTruck]:Enter Controller: driverCode : "+ id);
	// Object obj = null;
	// try {
	// truck.setTruckId(id);
	// boolean result = truckService.updateTruck(id, truck);
	//
	// if(result) {
	// obj = new ResponseEntity<Object>(new
	// Success(Integer.parseInt(CommonProperties.Truck_updated_code),
	// CommonProperties.Truck_updated_message, Iconstants.SUCCESS),
	// HttpStatus.OK);
	// } else {
	// obj = new ResponseEntity<Object>(new
	// Failed(Integer.parseInt(CommonProperties.Truck_unable_to_update_code),
	// CommonProperties.Truck_unable_to_update_message, Iconstants.ERROR),
	// HttpStatus.BAD_REQUEST);
	// }
	// } catch (Exception e) {
	// logger.error("[updateTruck]: Exception "+e);
	// obj = new ResponseEntity<Object>(new
	// Failed(Integer.parseInt(CommonProperties.Truck_unable_to_update_code),
	// CommonProperties.Truck_unable_to_update_message, Iconstants.ERROR),
	// HttpStatus.BAD_REQUEST);
	// }
	// return obj;
	// }
	//
	// // get Truck by Id
	// @RequestMapping(value = "/{id}", produces =
	// MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	// public Object getTruckById(@PathVariable("id") Long id) {
	// logger.info("[getTruckById] : Controller : Enter");
	// String json = null;
	// try {
	// Truck truck = truckService.getTruckById(id);
	// ObjectMapper mapper = new ObjectMapper();
	// TruckResponse response = new TruckResponse();
	// BeanUtils.copyProperties(response, truck);
	//
	// if(response != null) {
	// json = mapper.writeValueAsString(response);
	// }
	// } catch (Exception e) {
	// logger.error("[getTruckById]:" + e);
	// }
	// return json;
	// }
}
