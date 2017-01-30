/**
 * 
 */
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
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.model.TerminalResponse;
import com.dpu.service.TerminalService;
import com.dpu.util.MessageProperties;
/**
 * @author anuj
 *
 */
@RestController
@RequestMapping(value = "terminal")
public class TerminalController extends MessageProperties {
	
	Logger logger = Logger.getLogger(TerminalController.class);
	
	@Autowired
	TerminalService terminalService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllTerminals() {
		logger.info("[getAllTerminals] : Enter ");
		String json = null;
		try {
			List<TerminalResponse> lstTerminalRes = terminalService.getAllTerminals();
			if (lstTerminalRes != null && lstTerminalRes.size() > 0) {
				json = mapper.writeValueAsString(lstTerminalRes);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error(e);
			logger.error("TerminalController : getAll " + e);
		}
		logger.info("[getAllTerminals] :Exit");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody TerminalResponse terminalResp) {
		logger.info("[addTerminal] : Enter");
		Object obj = null;
		try {
			
			//Service service = setServiceValues(dpuService);
			List<TerminalResponse> result = terminalService.addTerminal(terminalResp);
			if (result != null) {
				obj = mapper.writeValueAsString(result);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(terminalUnableToAddCode),
						terminalUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[addTerminal] : Exit");
		return obj;
	}

	

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		logger.info("[delete] : Enter : Id : "+id);
		Object obj = null;
		try {
			List<TerminalResponse> terminalResponse = null;
			terminalResponse = terminalService.deleteTerminal(id);
		
			if (terminalResponse != null) {
				obj = terminalResponse;
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(terminalUnableToDeleteCode),
						terminalUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[delete] : Exit:   ");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id,
			@RequestBody TerminalResponse terminalRes) {
		logger.info("[update] : Enter : Id : "+id);
		Object obj = null;
		try {
			//service.setServiceId(id);
			List<TerminalResponse> terminalResponse = terminalService.updateTerminal(id, terminalRes);
			if (terminalResponse != null) {
				obj = terminalResponse;
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(terminalUnableToUpdateCode),
						terminalUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[update] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{terminalid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("terminalid") Long id) {
		logger.info("[get] : Enter : Id : "+id);
		String json = null;
		try {
			TerminalResponse terminalResponse = terminalService.getTerminal(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(terminalResponse);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[get] : Exit " );
		return json;
	}


}
