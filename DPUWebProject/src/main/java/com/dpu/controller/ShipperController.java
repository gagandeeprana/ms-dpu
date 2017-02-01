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

import com.dpu.constants.Iconstants;
import com.dpu.model.Failed;
import com.dpu.model.ShipperResponse;
import com.dpu.service.ShipperService;
import com.dpu.util.MessageProperties;


@RestController
@RequestMapping(value = "shipper")
public class ShipperController extends MessageProperties {
	
	Logger logger = Logger.getLogger(ShipperController.class);

	@Autowired
	ShipperService shipperService;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * this method is used to getAll shippers
	 * @return List<shipper>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		logger.info("Inside ShipperController getAll() starts");
		String json = null;
		try {
			List<ShipperResponse> lstShippers = shipperService.getAll();
			if(lstShippers != null && !lstShippers.isEmpty()) {
				json = mapper.writeValueAsString(lstShippers);
			}
		} catch (Exception e) {
			logger.error("Exception inside ShipperController getAll() :"+e.getMessage());
		}
		logger.info("Inside ShipperController getAll() ends");
		return json;
	}

	/**
	 * this method is used to add shipper
	 * @param shipperResponse
	 * @return List<Shipper>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody ShipperResponse shipperResponse) {
		
		logger.info("Inside ShipperController add() Starts ");
		Object obj = null;
		
		try {
			Object response = shipperService.add(shipperResponse);
			if (response instanceof List<?>) {
				obj = new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(shipperUnableToAddCode),
						shipperUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside ShipperController add() :"+e.getMessage());
		}
		
		logger.info("Inside ShipperController add() Ends ");
		return obj;
	}

	/**
	 * this method is used to delete the shipper based on shipperId
	 * @param id
	 * @return List<shipper>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		
		logger.info("Inside ShipperController delete() Starts, shipperId :"+id);
		Object obj = null;

		try {

			obj = shipperService.delete(id);
			
			if (obj instanceof List<?>) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(shipperUnableToDeleteCode),
						shipperUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside ShipperController delete() :"+e.getMessage());
		}
		logger.info("Inside ShipperController delete() Ends, shipperId :"+id);
		return obj;
	}

	/**
	 * this method is used to update the shipper based on shipperId
	 * @param id
	 * @param shipperResponse
	 * @return List<Shipper>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody ShipperResponse shipperResponse) {
		logger.info("Inside  ShipperController update() Starts, shipperId :"+id);
		Object obj = null;
		try {
			Object response = shipperService.update(id, shipperResponse);
			if (response != null) {
				obj = new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(shipperUnableToUpdateCode),
						shipperUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside ShipperController update :"+e.getMessage());
		}
		logger.info("Inside  ShipperController update() Ends, shipperId :"+id);
		return obj;
	}

	/**
	 * this method is used to get the particular shipper
	 * @param id
	 * @return particular shipper
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{shipperId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("shipperId") Long id) {
		logger.info("Inside  ShipperController get() Starts, shipperId : "+id);
		String json = new String();
		try {
			ShipperResponse response = shipperService.get(id);
			if(response != null) {
				json = mapper.writeValueAsString(response);
			}
		} catch (Exception e) {
			logger.error("Exception inside ShipperController get() :"+e.getMessage());
		}
		logger.info("Inside  ShipperController get() Ends, shipperId : "+id);
		return json;
	}

	/**
	 * this method is used when we click on add button in shipper screen
	 * @return masterData
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getOpenAdd() {
		logger.info("Inside ShipperController getOpenAdd() Starts. ");
		String json = new String();
		try {
			ShipperResponse response = shipperService.getMasterData();
			if (response != null) {
				json = mapper.writeValueAsString(response);
			}
		} catch (Exception e) {
			logger.error("Exception inside ShipperController getOpenAdd() :"+e.getMessage());
		}
		logger.info("Inside ShipperController getOpenAdd() Ends. ");
		return json;
	}
	
	/**
	 * this method is used to search the shipper based on companyName
	 * @param companyName
	 * @return List<Shipper>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{companyName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchShipper(@PathVariable("companyName") String companyName) {
		logger.info("Inside ShipperController searchShipper() Starts, companyName :"+companyName);
		String json = new String();
		try {
			List<ShipperResponse> shipperList = shipperService.getShipperByCompanyName(companyName);
			if(shipperList != null && shipperList.size() > 0) {
				json = mapper.writeValueAsString(shipperList);
			}
		} catch (Exception e) {
			logger.error("Exception inside ShipperController searchShipper() companyname is :" + e.getMessage());
		}
		logger.info(" Inside ShipperController searchShipper() Starts, companyName :"+companyName);
		return json;
	}
}
