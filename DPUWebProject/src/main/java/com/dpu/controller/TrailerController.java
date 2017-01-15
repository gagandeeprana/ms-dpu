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
	
	

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("Inside getAll(): TrailerController:");
		
		String json = new String();
		try {
			
			List<Trailer> lstTrailers = trailerService.getAll();
			logger.info("Inside getAll(): TrailerController: List Size: " + lstTrailers.size());

			if (lstTrailers != null && lstTrailers.size() > 0) {
				List<TrailerRequest> responses = new ArrayList<TrailerRequest>();
				for(Trailer trailer : lstTrailers) {
					TrailerRequest response = new TrailerRequest();
					BeanUtils.copyProperties(response, trailer);
					responses.add(response);
				}
				if(responses != null && !responses.isEmpty()) {
					json = mapper.writeValueAsString(responses);
				}
			}
		} catch (Exception e) {
			logger.error("Inside getAll(): TrailerController: Exception is: " + e.getMessage());

			System.out.println(e);
		}
		return json;
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody TrailerRequest trailerRequest) {
		Object obj = null;
		try {
			
			Trailer trailer = setTrailerValues(trailerRequest);
			Trailer response = trailerService.add(trailer);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(trailerAddedCode),
						trailerAddedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(trailerUnableToAddCode),
						trailerUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
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

			Trailer trailer = trailerService.get(id);
			if (trailer != null) {
				result = trailerService.delete(trailer);
			}
			if (result) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(trailerDeletedCode),
						trailerDeletedMessage, Iconstants.SUCCESS),
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
			trailerrequest.setTrailerId(id);
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
		trailer.setTrailerId(trailerRequest.getTrailerId());
		trailer.setUnitNo(trailerRequest.getUnitNo());
		trailer.setUsage(trailerRequest.getUsage());
		trailer.setOwner(trailerRequest.getOwner());
		trailer.setDivision(trailerRequest.getDivision());
		trailer.setoOName(trailerRequest.getoOName());
		trailer.setTerminal(trailerRequest.getTerminal());
		trailer.setCategory(trailerRequest.getCategory());
		trailer.setTrailerType(trailerRequest.getTrailerType());
		trailer.setStatus(trailerRequest.getStatus());
		trailer.setFinance(trailerRequest.getFinance());
		
		return trailer;
	}
}
