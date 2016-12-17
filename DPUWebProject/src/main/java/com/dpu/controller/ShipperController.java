/**
 * 
 */
package com.dpu.controller;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.entity.Company;
import com.dpu.entity.Shipper;
import com.dpu.service.CompanyService;
import com.dpu.service.ShipperService;

/**
 * @author jagvir
 *
 */
@RestController
@RequestMapping(value = "shipper")
public class ShipperController {

	@Autowired
	ShipperService shipperService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		String json = null;
		try {
			
			List<Shipper> lstShippers = shipperService.getAll("");
			json = mapper.writeValueAsString(lstShippers);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Shipper shipper) {
		String json = null;
		try {
			boolean result = shipperService.add(shipper);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {

		String json = null;
		try {
			boolean result = shipperService.delete(id);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Shipper shipper) {

		String json = null;
		try {
			boolean result = shipperService.update(id, shipper);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(value = "/{shipperId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("shipperId") int id) {
		String json = null;
		try {
			Shipper shipper = shipperService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(shipper);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

}
