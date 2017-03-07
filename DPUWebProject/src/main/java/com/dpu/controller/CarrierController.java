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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.constants.Iconstants;
import com.dpu.model.CarrierModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CarrierService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "carrier")
public class CarrierController extends MessageProperties {

	Logger logger = Logger.getLogger(CarrierController.class);
	
	@Autowired
	CarrierService carrierService;

	
	@Value("${carrier_unable_to_delete_message}")
	private String carrier_unable_to_delete_message;
	
	 

	ObjectMapper mapper = new ObjectMapper();
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllCarrier() {
		logger.info("CarrierController getAllCarrier() starts");
		String json = new String();
		try {
			
			List<CarrierModel> carrierResponse = carrierService.getAll();
			if(carrierResponse != null) {
				json = mapper.writeValueAsString(carrierResponse);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception inside CarrierController getAllCarrier() :"+e.getMessage());
		}
		logger.info("CarrierController getAllCarrier() ends");
		return json;
	}
	
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long companyId) {

		logger.info(" CarrierController delete() starts ");
		Object obj = null;

		try {
			
			obj = carrierService.delete(companyId);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController delete() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,carrier_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info(" CarrierController delete() ends ");
		return obj;
	}
}
