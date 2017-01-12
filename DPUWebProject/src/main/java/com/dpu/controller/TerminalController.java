/**
 * 
 */
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
public class TerminalController {
	
	Logger logger = Logger.getLogger(TerminalController.class);

	@Value("${terminal_added_code}")
	public String terminalAddedCode;

	@Value("${terminal_added_message}")
	public String terminalAddedMessage;

	@Value("${terminal_unable_to_add_code}")
	public String terminalUnableToAddCode;

	@Value("${terminal_unable_to_add_message}")
	public String terminalUnableToAddMessage;

	@Value("${terminal_deleted_code}")
	public String terminalDeletedCode;

	@Value("${terminal_deleted_message}")
	public String terminalDeletedMessage;

	@Value("${terminal_unable_to_delete_code}")
	public String terminalUnableToDeleteCode;

	@Value("${terminal_unable_to_delete_message}")
	public String terminalUnableToDeleteMessage;

	@Value("${terminal_updated_code}")
	public String terminalUpdateCode;

	@Value("${terminal_updated_message}")
	public String terminalUpdateMessage;

	@Value("${terminal_unable_to_update_code}")
	public String terminalUnableToUpdateCode;

	@Value("${terminal_unable_to_update_message}")
	public String terminalUnableToUpdateMessage;
	
	@Autowired
	TerminalService terminalService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("[getAll] : Enter");
		String json = null;
		try {

			List<Terminal> lstTerminals = terminalService.getAll();
			json = mapper.writeValueAsString(lstTerminals);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[getAll] : Exit");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Terminal terminal) {
		logger.info("[add] : Enter");
		Object obj = null;
		try {
			Terminal response = terminalService.add(terminal);
			if(response != null) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(terminalAddedCode), terminalAddedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(terminalUnableToAddCode), terminalUnableToAddMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[add] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {
		logger.info("[delete] : Enter : Id : "+id);
		Object obj = null;
		boolean result = false;

		try {
			
			Terminal terminal = terminalService.get(id);
			if(terminal != null) {
				result = terminalService.delete(terminal);
			}
			if(result) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(terminalDeletedCode), terminalDeletedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(terminalUnableToDeleteCode), terminalUnableToDeleteMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[delete] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{terminalid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("terminalid") int id, @RequestBody Terminal terminal) {
		logger.info("[update] : Enter : Id : "+id);
		Object obj = null;
		try {
			terminal.setTerminalId(id);
			Terminal response = terminalService.update(terminal);
			if(response != null) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(terminalUpdateCode), terminalUpdateMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(terminalUnableToUpdateCode), terminalUnableToUpdateMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {	
			System.out.println(e);
		}
		logger.info("[update] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{terminalId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("terminalId") int id) {
		logger.info("[get] : Enter");
		String json = new String();
		try {
			Terminal terminal = terminalService.get(id);
			if(terminal != null) {
				json = mapper.writeValueAsString(terminal);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[get] : Exit");
		return json;
	}

}
