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

import com.dpu.entity.Division;
import com.dpu.service.DivisionService;

/**
 * @author jagvir
 *
 */
@RestController
@RequestMapping(value = "shipper")
public class DivisionController {

	@Autowired
	DivisionService divisionService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		String json = null;
		try {

			List<Division> lstDivisions = divisionService.getAll("");
			json = mapper.writeValueAsString(lstDivisions);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Division division) {
		String json = null;
		try {
			boolean result = divisionService.add(division);
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
			boolean result = divisionService.delete(id);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Division division) {

		String json = null;
		try {
			boolean result = divisionService.update(id, division);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(value = "/{divisionId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("divisionId") int id) {
		String json = null;
		try {
			Division division = divisionService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(division);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

}
