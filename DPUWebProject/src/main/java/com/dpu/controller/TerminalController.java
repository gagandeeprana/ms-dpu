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
			logger.error("DivisionController : getAll " + e);
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
//	
//
//	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
//	public Object delete(@PathVariable("id") int id) {
//		logger.info("[delete] : Enter : Id : "+id);
//		Object obj = null;
//		boolean result = false;
//		
//		try {
//			
//			Terminal terminal = terminalService.get(id);
//			if(terminal != null) {
//				result = terminalService.delete(terminal);
//			}
//			if(result) {
//				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(terminalDeletedCode), terminalDeletedMessage, Iconstants.SUCCESS), HttpStatus.OK);
//			} else {
//				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(terminalUnableToDeleteCode), terminalUnableToDeleteMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		logger.info("[delete] : Exit");
//		return obj;
//	}
//
//	@RequestMapping(value = "/{terminalid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
//	public Object update(@PathVariable("terminalid") BigInteger id, @RequestBody Terminal terminal) {
//		logger.info("[update] : Enter : Id : "+id);
//		Object obj = null;
//		Calendar cal = Calendar.getInstance();
//		try {
//			terminal.setTerminalId(id);
////			terminal.setCreatedOn(cal.getTime());
//			terminal.setModifiedOn(cal.getTime());
//			Terminal response = terminalService.update(terminal);
//			if(response != null) {
//				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(terminalUpdateCode), terminalUpdateMessage, Iconstants.SUCCESS), HttpStatus.OK);
//			} else {
//				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(terminalUnableToUpdateCode), terminalUnableToUpdateMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
//			}
//		} catch (Exception e) {	
//			System.out.println(e);
//		}
//		logger.info("[update] : Exit");
//		return obj;
//	}
//
//	@RequestMapping(value = "/{terminalId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//	public Object get(@PathVariable("terminalId") int id) {
//		logger.info("[get] : Enter");
//		String json = new String();
//		try {
//			Terminal terminal = terminalService.get(id);
//			if(terminal != null) {
//				TerminalResponse response = new TerminalResponse();
//				BeanUtils.copyProperties(response, response);
//			
//			if(response != null) {
//				json = mapper.writeValueAsString(response);
//			}
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		logger.info("[get] : Exit");
//		return json;
//	}

}
