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
import com.dpu.entity.Company;
import com.dpu.model.CompanyResponse;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CompanyService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "company")
public class CompanyController extends MessageProperties {

	Logger logger = Logger.getLogger(CompanyController.class);
	
	@Autowired
	CompanyService companyService;
	
	

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		logger.info("[getAll] : Enter");
		String json = new String();
		try {
			
			List<CompanyResponse> companyResponses = companyService.getAll();
			
			if(companyResponses != null) {
				json = mapper.writeValueAsString(companyResponses);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[getAll] : Exit");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CompanyResponse companyResponse) {
		logger.info("[add] : Enter");
		Object obj = null;
		try {
			//System.out.println(new ObjectMapper().writeValueAsString(companyResponse));
			
			Company response = companyService.addCompanyData(companyResponse);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(companyAddedCode),
						companyAddedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(companyUnableToAddCode),
						companyUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[add] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long companyId) {
		logger.info("[delete] : Enter : ID : ");
		Object obj = null;

		try {
			
			obj = companyService.delete(companyId);
			if (obj instanceof List<?>) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(companyUnableToDeleteCode),
						companyUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[delete] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody CompanyResponse companyResponse) {
		logger.info("[update] : Enter : ID : "+id);
		Object obj = null;
		try {
			//company.setCompanyId(id);
			
			obj = companyService.update(id, companyResponse);
			if (obj instanceof List<?>) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(companyUnableToUpdateCode),
						companyUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[update] : Exit : ID : ");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("id") Long id) {
		
		logger.info("[get] : Enter : ID : "+id);
		String json = new String();
		try {
			CompanyResponse companyResponse = companyService.get(id);
			if(companyResponse != null) {
				json = mapper.writeValueAsString(companyResponse);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[get] : Exit ");
		return json;
	}
	
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info(" Inside CompanyController openAdd() Starts ");
		String json = null;
		
		try {
			CompanyResponse companyResponse = companyService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(companyResponse);
		} catch (Exception e) {
			logger.error(" Exception inside CompanyController openAdd() :"+e.getMessage());
		}
		
		logger.info(" Inside CompanyController openAdd() Ends ");
		return json;
	}
}
