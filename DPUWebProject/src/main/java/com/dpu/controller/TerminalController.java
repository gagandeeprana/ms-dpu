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
import com.dpu.entity.Terminal;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.TerminalService;
import com.dpu.util.MessageProperties;

/**
 * @author gagan
 *
 */
@RestController
@RequestMapping(value = "terminal")
public class TerminalController extends MessageProperties{

	@Autowired
	TerminalService terminalService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		String json = null;
		try {

			List<Terminal> lstTerminals = terminalService.getAll();
			json = mapper.writeValueAsString(lstTerminals);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Terminal terminal) {
		Object obj = null;
		try {
			Terminal response = terminalService.add(terminal);
			if(response != null) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(shipperAddedCode), shipperAddedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(shipperUnableToAddCode), shipperUnableToAddMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
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
			
			Terminal terminal = terminalService.get(id);
			if(terminal != null) {
				result = terminalService.delete(terminal);
			}
			if(result) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(shipperDeletedCode), shipperDeletedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(shipperUnableToDeleteCode), shipperUnableToDeleteMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return obj;
	}

	@RequestMapping(value = "/{terminalid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("terminalid") int id, @RequestBody Terminal terminal) {

		Object obj = null;
		try {
			terminal.setTerminalId(id);
			Terminal response = terminalService.update(terminal);
			if(response != null) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(shipperUpdateCode), shipperUpdateMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(shipperUnableToUpdateCode), shipperUnableToUpdateMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {	
			System.out.println(e);
		}
		return obj;
	}

	@RequestMapping(value = "/{terminalId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("terminalId") int id) {
		String json = new String();
		try {
			Terminal terminal = terminalService.get(id);
			if(terminal != null) {
				json = mapper.writeValueAsString(terminal);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

}
