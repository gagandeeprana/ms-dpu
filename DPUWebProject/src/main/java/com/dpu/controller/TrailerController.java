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

			if (lstTrailers != null) {
				json = mapper.writeValueAsString(lstTrailers);
			}
		} catch (Exception e) {
			logger.error("Inside getAll(): TrailerController: Exception is: " + e.getMessage());

			System.out.println(e);
		}
		return json;
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Trailer trailer) {
		Object obj = null;
		try {
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
			//trailer.setTrailerId(id);
			trailerrequest.setTrailerId(id);
			Trailer tr=setTrailerValue(trailerrequest);
			
			
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
				json = mapper.writeValueAsString(trailer);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}
	
	private Trailer setTrailerValue(TrailerRequest trailerrequest){
		Trailer trailer =new Trailer();
		 trailer  = trailerService.get(trailerrequest.getTrailerId());
		if(trailerrequest.getTrailerId()!= null){
			trailer.setTrailerId(trailerrequest.getTrailerId());
		}
		if(trailerrequest.getClassId()!= null){
			trailer.setClassId(trailerrequest.getClassId());
		}
		if(trailerrequest.getEquipmentId()!= null){
			trailer.setEquipmentId(trailerrequest.getEquipmentId());
		}
		if(trailerrequest.getLength()!= null){
			trailer.setLength(trailerrequest.getLength());
		}
		if(trailerrequest.getVIN()!= null){
			trailer.setVIN(trailerrequest.getVIN());
		}
		if(trailerrequest.getMake()!= null){
			trailer.setMake(trailerrequest.getMake());
		}
		if(trailerrequest.getModel()!= null){
			trailer.setModel(trailerrequest.getModel());
		}
		if(trailerrequest.getYear()!= null){
			trailer.setYear(trailerrequest.getYear());
		}
		if(trailerrequest.getPlateNo()!= null){
			trailer.setPlateNo(trailerrequest.getPlateNo());
		}
		if(trailerrequest.getJurisdiction()!= null){
			trailer.setJurisdiction(trailerrequest.getJurisdiction());
		}
		if(trailerrequest.getTareWeight()!= null){
			trailer.setTareWeight(trailerrequest.getTareWeight());
		}
		if(trailerrequest.getRgw()!= null){
			trailer.setRgw(trailerrequest.getRgw());
		}
		if(trailerrequest.getCurrentOdometer()!= null){
			trailer.setCurrentOdometer(trailerrequest.getCurrentOdometer());
		}
		if(trailerrequest.getReadingTakenDate()!= null){
			trailer.setReadingTakenDate(trailerrequest.getReadingTakenDate());
		}
		if(trailerrequest.getCreatedBy()!= null){
			trailer.setCreatedBy(trailerrequest.getCreatedBy());
		}
		if(trailerrequest.getCreatedOn()!= null){
			trailer.setCreatedOn(trailerrequest.getCreatedOn());
		}
		return trailer;
	}
}
