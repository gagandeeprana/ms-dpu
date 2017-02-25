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

import com.dpu.model.CustomBrokerResponse;
import com.dpu.model.DPUService;
import com.dpu.model.Success;
import com.dpu.service.CustomBrokerService;
import com.dpu.util.MessageProperties;

/**
 * @author anuj
 *
 */
@RestController
@RequestMapping(value = "custombroker")
public class CustomBrokerController extends MessageProperties {

	Logger logger = Logger.getLogger(CustomBrokerController.class);

	@Autowired
	CustomBrokerService customBrokerService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		logger.info("[getAll] : Enter");
		String json = null;
		try {
			List<CustomBrokerResponse> lstcustomBrokerResp = customBrokerService.getAll();
			if (lstcustomBrokerResp != null && lstcustomBrokerResp.size() > 0) {
				json = mapper.writeValueAsString(lstcustomBrokerResp);
			}
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController getAll() :"+ e.getMessage());
		}
		logger.info("[getAll] : Exit");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CustomBrokerResponse customBrokerResp) {
		logger.info("Inside CustomBrokerController add() starts");
		Object obj = null;
		try {

			Object result = customBrokerService.add(customBrokerResp);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController add() :"+ e.getMessage());
		}

		logger.info("Inside CustomBrokerController add() ends");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		logger.info("[delete] : Enter : Id : " + id);
		Object obj = null;
		try {
			Object result = null;
			result = customBrokerService.delete(id);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController delete() :"+ e.getMessage());
		}
		logger.info("[delete] : Exit:   ");
		return obj;

	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id,
			@RequestBody CustomBrokerResponse customBrokerResp) {
		logger.info("[update] : Enter : Id : " + id);
		Object obj = null;
		try {
			Object result = customBrokerService.update(id, customBrokerResp);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController update() :"+ e.getMessage());
		}
		logger.info("[update] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{customBrokerId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("customBrokerId") Long id) {
		logger.info("[get] : Enter : Id : " + id);
		String json = null;
		try {
			CustomBrokerResponse customBrokerResp = customBrokerService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(customBrokerResp);
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController getById() :"+ e.getMessage());
		}
		logger.info("[get] : Exit ");
		return json;
	}

	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		logger.info(" Inside CustomBrokerController openAdd() Starts ");
		String json = null;
		try {
			CustomBrokerResponse customBrokerResp = customBrokerService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(customBrokerResp);
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController openAdd() :"+ e.getMessage());
		}
		logger.info(" Inside CustomBrokerController openAdd() Ends ");
		return json;
	}

	@RequestMapping(value = "/{custombrokername}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchService(@PathVariable("custombrokername") String customBrokerName) {
		logger.info("Inside CustomBrokerController searchCustomBroker() Starts, customBrokerName :"	+ customBrokerName);
		String json = new String();
		try {
			List<CustomBrokerResponse> customBrokerRespList = customBrokerService.getCustomBrokerByCustomBrokerName(customBrokerName);
			if (customBrokerRespList != null && customBrokerRespList.size() > 0) {
				json = mapper.writeValueAsString(customBrokerRespList);
			}
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController searchService() is :"	+ e.getMessage());
		}
		logger.info(" Inside CustomBrokerController customBrokerName() Starts, customBrokerName :"+ customBrokerName);
		return json;
	}
}
