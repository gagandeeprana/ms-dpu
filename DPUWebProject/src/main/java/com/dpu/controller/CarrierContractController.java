package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.model.CarrierModel;
import com.dpu.service.CarrierContractService;

@RestController
@RequestMapping(value = "carrierContract")
public class CarrierContractController {
	
	Logger logger = Logger.getLogger(CarrierContractController.class);
	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	CarrierContractService carrierContractService;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllCarrierContract() {

		logger.info("CarrierContractController getAllCarrierContract() starts");
		String json = new String();

		try {
			List<CarrierModel> carrierResponse = carrierContractService.getAllCarrierContract();

			if (carrierResponse != null) {
				json = mapper.writeValueAsString(carrierResponse);
			}

		} catch (Exception e) {
			logger.info("Exception inside CarrierContractController getAllCarrierContract() :" + e.getMessage());
		}

		logger.info("CarrierContractController getAllCarrierContract() ends");
		return json;
	}

}
