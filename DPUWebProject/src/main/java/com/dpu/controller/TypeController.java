package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.model.TypeResponse;
import com.dpu.service.TypeService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "type")
public class TypeController extends MessageProperties {

	Logger logger = Logger.getLogger(TypeController.class);
	
	@Autowired
	TypeService typeService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("TypeController: getAll(): STARTS");
		
		String json = new String();
		try {
			
			List<TypeResponse> typeResponses = typeService.getAll("");
			
			if(typeResponses != null) {
				json = mapper.writeValueAsString(typeResponses);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[getAll] : Exit");
		return json;
	}

}
