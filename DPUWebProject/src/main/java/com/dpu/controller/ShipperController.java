/**
 * 
 */
package com.dpu.controller;

import java.util.List;

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
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.ShipperService;
import com.dpu.util.MessageProperties;

/**
 * @author jagvir
 *
 */
@RestController
@RequestMapping(value = "shipper")
public class ShipperController extends MessageProperties {

	@Autowired
	ShipperService shipperService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		String json = null;
		try {
			List<Shipper> lstShippers = shipperService.getAll();
			json = mapper.writeValueAsString(lstShippers);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Shipper shipper) {
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
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {

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
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Shipper shipper) {

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
		return obj;
	}

	@RequestMapping(value = "/{shipperId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("shipperId") int id) {
		String json = new String();
		try {
			Shipper shipper = shipperService.get(id);
			if (shipper != null) {
				json = mapper.writeValueAsString(shipper);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

}
