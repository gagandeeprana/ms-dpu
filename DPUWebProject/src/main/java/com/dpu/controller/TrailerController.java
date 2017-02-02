package com.dpu.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
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
import com.dpu.entity.Trailer;
import com.dpu.model.DriverReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TrailerRequest;
import com.dpu.service.TrailerService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "trailer")
public class TrailerController extends MessageProperties {
	
	Logger logger = Logger.getLogger(TrailerController.class);

	@Autowired
	TrailerService trailerService;

	ObjectMapper mapper = new ObjectMapper();
	
	
	/**
	 * this method is used to get all trailers
	 * @return List<trailers>
	 * @author lakhvir
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("Inside TrailerController getAll() Starts ");
		String json = new String();
		
		try {
			List<TrailerRequest> lstTrailers = trailerService.getAll();

			if(lstTrailers != null && !lstTrailers.isEmpty()) {
				json = mapper.writeValueAsString(lstTrailers);
			}
			
		} catch (Exception e) {
			logger.error("Exception inside  TrailerController getAll() :" + e.getMessage());
		}
		
		logger.info("Inside TrailerController getAll() Ends ");
		return json;
	}
	
	/**
	 * this method is used to add the trailer
	 * @param trailerRequest
	 * @return all trailers List
	 * @author lakhvir
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody TrailerRequest trailerRequest) {
		
		logger.info("Inside TrailerController add() starts");
		Object obj = null;
		try {
			Object response = trailerService.add(trailerRequest);
			if (response instanceof List<?>) {
				obj = new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(trailerUnableToAddCode),
						trailerUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside TrailerController add() :" + e.getMessage());
		}
		
		logger.info("Inside TrailerController add() Ends");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long trailerId) {

		Object obj = null;
		try {

			obj = trailerService.delete(trailerId);
			
			if (obj instanceof List<?>) {
				obj = new ResponseEntity<Object>(obj,
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(trailerUnableToDeleteCode),
						trailerUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody TrailerRequest trailerrequest) {

		Object obj = null;
		try {
			//trailerrequest.setTrailerId(id);
			Trailer tr = setTrailerValues(trailerrequest);
			
			
			Trailer response = trailerService.update(tr);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(trailerUpdateCode),
						trailerUpdateMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(trailerUnableToUpdateCode),
						trailerUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("id") int id) {
		String json = new String();
		try {
			Trailer trailer = trailerService.get(id);
			if (trailer != null) {
				TrailerRequest response = new TrailerRequest();
				BeanUtils.copyProperties(response, trailer);
				if(response != null) {
					json = mapper.writeValueAsString(response);
				}
				System.out.println(json);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}
	
	private Trailer setTrailerValues(TrailerRequest trailerRequest){
		Trailer trailer = new Trailer();
		/*trailer.setTrailerId(trailerRequest.getTrailerId());
		trailer.setUnitNo(trailerRequest.getUnitNo());
		trailer.setUsage(trailerRequest.getUsage());
		trailer.setOwner(trailerRequest.getOwner());
		trailer.setDivision(trailerRequest.getDivision());
		trailer.setoOName(trailerRequest.getoOName());
		trailer.setTerminal(trailerRequest.getTerminal());
		trailer.setCategory(trailerRequest.getCategory());
		trailer.setTrailerType(trailerRequest.getTrailerType());
		trailer.setStatus(trailerRequest.getStatus());
		trailer.setFinance(trailerRequest.getFinance());*/
		
		return trailer;
	}
	
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		logger.info(" Inside TrailerController openAdd() Starts ");
		String json = null;
		try {
			TrailerRequest trailerReq = trailerService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(trailerReq);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info(" Inside TrailerController openAdd() Ends ");
		return json;
	}
	
	
}
