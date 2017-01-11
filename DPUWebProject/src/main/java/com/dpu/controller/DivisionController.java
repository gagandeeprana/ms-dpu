/**
 * 
 */
package com.dpu.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.dpu.entity.Division;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.DivisionService;
import com.dpu.util.MessageProperties;

/**
 * @author jagvir
 *
 */
@RestController
@RequestMapping(value = "division")
public class DivisionController extends MessageProperties {

	Logger logger = Logger.getLogger(DivisionController.class);
	
	@Autowired
	DivisionService divisionService;
	
	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		String json = null;
		try {

			List<Division> lstDivisions = divisionService.getAll();
			json = mapper.writeValueAsString(lstDivisions);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Division division) {
		logger.info("DivisionController: add");
		Calendar cal = Calendar.getInstance();
		Object obj = null;
		try {
			division.setCreatedOn(cal.getTime());
			Division result = divisionService.add(division);
			if (result != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(divisionAddedCode),
						divisionAddedMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(divisionUnableToAddCode),
						divisionUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error("DivisionController: add " + e);
		}
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {
		Object obj = null;
		boolean result = false;
		try {
			Division division = divisionService.get(id);
			if (division != null) {
				result = divisionService.delete(division);
			}
			if (result) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(divisionDeletedCode),
						divisionDeletedMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(divisionUnableToDeleteCode),
						divisionUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error("DivisionController: delete " + e);
		}
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Division division) {
		Object obj = null;
		try {
			division.setDivisionId(id);
			Division response = divisionService.update(id, division);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(divisionUpdateCode),
						divisionUpdateMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(divisionUnableToUpdateCode),
						divisionUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error("DivisionController: update " + e);
		}
		return obj;
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
