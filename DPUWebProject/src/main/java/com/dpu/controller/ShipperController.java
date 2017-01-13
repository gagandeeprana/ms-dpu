/**
 * 
 */
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

import com.dpu.constants.Iconstants;
import com.dpu.entity.Shipper;
import com.dpu.entity.Trailer;
import com.dpu.model.Failed;
import com.dpu.model.ShipperResponse;
import com.dpu.model.Success;
import com.dpu.model.TrailerRequest;
import com.dpu.service.ShipperService;
import com.dpu.util.MessageProperties;

/**
 * @author jagvir
 *
 */
@RestController
@RequestMapping(value = "shipper")
public class ShipperController extends MessageProperties {
	
	Logger logger = Logger.getLogger(ShipperController.class);

	@Autowired
	ShipperService shipperService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		logger.info("[getAll] : Enter");
		String json = null;
		try {
			List<Shipper> lstShippers = shipperService.getAll();
			if (lstShippers != null && lstShippers.size() > 0) {
				List<ShipperResponse> responses = new ArrayList<ShipperResponse>();
				for(Shipper shipper : lstShippers) {
					ShipperResponse response = new ShipperResponse();
					BeanUtils.copyProperties(response, shipper);
					responses.add(response);
				}
				if(responses != null && !responses.isEmpty()) {
					json = mapper.writeValueAsString(responses);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[getAll] : Exit");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Shipper shipper) {
		logger.info("[add] : Enter");
		Object obj = null;
		try {
			Shipper response = shipperService.add(shipper);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(shipperAddedCode),
						shipperAddedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(shipperUnableToAddCode),
						shipperUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[add] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {
		logger.info("[delete] : Enter : Id: "+id);
		Object obj = null;
		boolean result = false;

		try {

			Shipper shipper = shipperService.get(id);
			if (shipper != null) {
				result = shipperService.delete(shipper);
			}
			if (result) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(shipperDeletedCode),
						shipperDeletedMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(shipperUnableToDeleteCode),
						shipperUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[delete] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Shipper shipper) {
		logger.info("[update] : Enter : Id : "+id);
		Object obj = null;
		try {
			shipper.setShipperId(id);
			Shipper response = shipperService.update(shipper);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(shipperUpdateCode),
						shipperUpdateMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(shipperUnableToUpdateCode),
						shipperUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[update] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{shipperId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("shipperId") int id) {
		logger.info("[get ] : Enter: Id : "+id);
		String json = new String();
		try {
			Shipper shipper = shipperService.get(id);
			if (shipper != null) {
				json = mapper.writeValueAsString(shipper);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[get] : Exit");
		return json;
	}

}
