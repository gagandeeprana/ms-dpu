package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.constants.Iconstants;
import com.dpu.model.CarrierContractModel;
import com.dpu.model.DriverReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CarrierContractService;

@RestController
@RequestMapping(value = "carrierContract")
public class CarrierContractController {

	Logger logger = Logger.getLogger(CarrierContractController.class);
	ObjectMapper mapper = new ObjectMapper();

	@Value("${Driver_unable_to_add_message}")
	private String Driver_unable_to_add_message;

	@Value("${Driver_unable_to_delete_message}")
	private String Driver_unable_to_delete_message;

	@Value("${Driver_unable_to_update_message}")
	private String Driver_unable_to_update_message;

	@Autowired
	CarrierContractService carrierContractService;

	
	/**
	 * this method is used to get all the carrierContract
	 * @return List<carrierContracts>
	 * @author sumit
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllCarrierContract() {
		System.out.println("hiiii CarrierContract");
		logger.info("CarrierContractController getAllCarrierContract() starts");
		String json = new String();

		try {
			List<CarrierContractModel> carrierResponse = carrierContractService.getAllCarrierContract();

			if (carrierResponse != null) {
				json = mapper.writeValueAsString(carrierResponse);
			}

		} catch (Exception e) {
			logger.info("Exception inside CarrierContractController getAllCarrierContract() :" + e.getMessage());
		}

		logger.info("CarrierContractController getAllCarrierContract() ends");
		return json;
	}
	
	/**
	 * this method is used to add new driver
	 * @param driverReq
	 * @return List<drivers>
	 * @author lakhvir
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Object> addCarrierContract(@RequestBody CarrierContractModel carrierContract) {
		
		logger.info("Inside CarrierContractController addCarrierContract Starts. ");
		ResponseEntity<Object> obj =  null;
		  
		try {
			Object result = carrierContractService.addCarrierContract(carrierContract);
			if(result instanceof Success){ 
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else{
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception Inside CarrierContractController addCarrierContract :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, Driver_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside CarrierContractController addCarrierContract Ends. ");
		return obj;
	}


}
