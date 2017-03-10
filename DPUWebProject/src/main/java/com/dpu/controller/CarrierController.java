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
import com.dpu.model.CarrierAdditionalContactModel;
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

	@Value("${carrier_unable_to_add_message}")
	private String carrier_unable_to_add_message;

	@Value("${carrier_unable_to_delete_message}")
	private String carrier_unable_to_delete_message;

	@Value("${carrier_unable_to_update_message}")
	private String carrier_unable_to_update_message;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllCarrier() {
		
		logger.info("CarrierController getAllCarrier() starts");
		String json = new String();
		
		try {
			List<CarrierModel> carrierResponse = carrierService.getAll();
			
			if (carrierResponse != null) {
				json = mapper.writeValueAsString(carrierResponse);
			}

		} catch (Exception e) {
			logger.info("Exception inside CarrierController getAllCarrier() :" + e.getMessage());
		}
		
		logger.info("CarrierController getAllCarrier() ends");
		return json;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long carrierId) {

		logger.info(" CarrierController delete() starts ");
		Object obj = null;

		try {
			obj = carrierService.delete(carrierId);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController delete() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, carrier_unable_to_delete_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info(" CarrierController delete() ends ");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("id") Long id) {

		logger.info(" CarrierController get() starts, carrierId :" + id);
		String json = new String();
		
		try {
			CarrierModel carrierResponse = carrierService.get(id);
			if (carrierResponse != null) {
				json = mapper.writeValueAsString(carrierResponse);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController get() :" + e.getMessage());
		}

		logger.info(" CarrierController get() ends, carrierId :" + id);
		return json;
	}

	/**
	 * this method is used to add carrier
	 * 
	 * @param carrierResponse
	 * @return all carries data
	 * @author sumit
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CarrierModel carrierResponse) {
		
		logger.info("CarrierController add() starts");
		Object obj = null;
		
		try {
			obj = carrierService.addCarrierData(carrierResponse);

			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.info("Exception inside carrierController add() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, carrier_unable_to_add_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("carrierController add() Ends");
		return obj;
	}
	
	@RequestMapping(value = "/contactId/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET  )
	public Object getContactById(@PathVariable("id") Long id) {

		logger.info(" CarrierController getContactById() starts, contactId :" + id);
		String json = new String();
		
		try {
			CarrierAdditionalContactModel additionalContact = carrierService.getContactById(id);
			if (additionalContact != null) {
				json = mapper.writeValueAsString(additionalContact);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController getContactById() :" + e.getMessage());
		}

		logger.info(" CarrierController getContactById() ends, contactId :" + id);
		return json;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody CarrierModel carrierResponse) {

		logger.info(" CarrierController update() starts, carrierId :" + id);
		Object obj = null;
		
		try {
			obj = carrierService.update(id, carrierResponse);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController update() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, carrier_unable_to_update_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}
		
		logger.info(" CarrierController update() ends, carrierId :" + id);
		return obj;
	}

}
