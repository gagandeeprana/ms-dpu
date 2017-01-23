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
import com.dpu.model.DPUService;
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
			List<DPUService> lstServices = serviceService.getAll();
			if(lstServices != null) {
				/*List<DPUService> responses = new ArrayList<DPUService>();
				for(Service service : lstServices) {
					DPUService response = new DPUService();
					BeanUtils.copyProperties(response, service);
					responses.add(response);
				}*/
				/*if(responses != null && !responses.isEmpty()) {*/
					json = mapper.writeValueAsString(lstServices);
				/*}*/
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[getAll] : Exit");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody DPUService dpuService) {
		logger.info("[add] : Enter");
		Object obj = null;
		try {
			
			//Service service = setServiceValues(dpuService);
			List<DPUService> result = serviceService.add(dpuService);
			if (result != null) {
				obj = mapper.writeValueAsString(result);
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
	public Object delete(@PathVariable("id") Long id) {
		logger.info("[delete] : Enter : Id : "+id);
		Object obj = null;
		try {
			List<DPUService> response = null;
			//serviceService.get(id);
			/*if (service != null) {*/
			response = serviceService.delete(id);
		/*	}*/
			if (response != null) {
				obj = response;
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
	public Object update(@PathVariable("id") Long id,
			@RequestBody DPUService dpuService) {
		logger.info("[update] : Enter : Id : "+id);
		Object obj = null;
		try {
			//service.setServiceId(id);
			List<DPUService> response = serviceService.update(id, dpuService);
			if (response != null) {
				obj = response;
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
	public Object get(@PathVariable("categoryId") Long id) {
		logger.info("[get] : Enter : Id : "+id);
		String json = null;
		try {
			DPUService dpuService = serviceService.get(id);
			//Service service = serviceService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(dpuService);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[get] : Exit " );
		return json;
	}
	
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		logger.info(" Inside ServiceController openAdd() Starts ");
		String json = null;
		try {
			DPUService dpuService = serviceService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(dpuService);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info(" Inside ServiceController openAdd() Ends ");
		return json;
	}

}
