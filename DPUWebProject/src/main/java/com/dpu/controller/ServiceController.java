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
import com.dpu.entity.Service;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.ServiceService;
import com.dpu.util.MessageProperties;

/**
 * @author jagvir
 *
 */
@RestController
@RequestMapping(value = "service")
public class ServiceController extends MessageProperties {
	
	Logger logger = Logger.getLogger(ServiceController.class);
	
	@Autowired
	ServiceService serviceService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		logger.info("[getAll] : Enter");
		String json = null;
		try {
			List<Service> lstServices = serviceService.getAll();
			json = mapper.writeValueAsString(lstServices);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[getAll] : Exit");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Service service) {
		logger.info("[add] : Enter");
		Object obj = null;
		try {
			Service result = serviceService.add(service);
			if (result != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(serviceAddedCode),
						serviceAddedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(serviceUnableToAddCode),
						serviceUnableToAddMessage, Iconstants.ERROR),
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
		logger.info("[delete] : Enter : Id : "+id);
		Object obj = null;
		boolean result = false;
		try {
			Service service = serviceService.get(id);
			if (service != null) {
				result = serviceService.delete(service);
			}
			if (result) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(serviceDeletedCode),
						serviceDeletedMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(serviceUnableToDeleteCode),
						serviceUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[delete] : Exit:   ");
		return obj;

	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Service service) {
		logger.info("[update] : Enter : Id : "+id);
		Object obj = null;
		try {
			service.setServiceId(id);
			Service response = serviceService.update(id, service);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(serviceUpdateCode),
						serviceUpdateMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(serviceUnableToUpdateCode),
						serviceUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[update] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("categoryId") int id) {
		logger.info("[get] : Enter : Id : "+id);
		String json = null;
		try {
			Service service = serviceService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(service);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[get] : Exit " );
		return json;
	}

}
