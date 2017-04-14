package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.constants.Iconstants;
import com.dpu.model.CompanyResponse;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.VendorModel;
import com.dpu.service.VendorService;

@RestController
@RequestMapping(value = "vendor")
public class VendorController{

	Logger logger = Logger.getLogger(VendorController.class);
	
	@Autowired
	VendorService vendorService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${company_unable_to_add_message}")
	private String company_unable_to_add_message;

	@Value("${company_unable_to_delete_message}")
	private String company_unable_to_delete_message;
	
	@Value("${company_unable_to_update_message}")
	private String company_unable_to_update_message;

	/**
	 * this method is used to get all the vendors
	 * @return all vendors
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("VendorController getAll() starts");
		String json = new String();
		
		try {
			
			List<VendorModel> companyResponses = vendorService.getAll();
			
			if(companyResponses != null) {
				json = mapper.writeValueAsString(companyResponses);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside VendorController getAll() :"+e.getMessage());
		}
		
		logger.info("VendorController getAll() ends");
		return json;
	}

	/**
	 * this method is used to add vendor
	 * @param companyResponse
	 * @return all companies data
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody VendorModel vendorModel) {
		
		logger.info("VendorController add() starts");
		Object obj = null;
		try {
			obj = vendorService.addVendorData(vendorModel);
			
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside VendorController add() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,company_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("VendorController add() Ends");
		return obj;
	}

	/**
	 * this method is used to delete the company
	 * @param companyId
	 * @return List<Company>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long companyId) {

		logger.info(" CompanyController delete() starts ");
		Object obj = null;

		try {
			
			//obj = companyService.delete(companyId);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CompanyController delete() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,company_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info(" CompanyController delete() ends ");
		return obj;
	}

	/**
	 * this method is used to update the record
	 * @param id
	 * @param companyResponse
	 * @return List<Company>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody CompanyResponse companyResponse) {
		
		logger.info(" CompanyController delete() starts, companyId :"+id);
		Object obj = null;
		try {
			//obj = companyService.update(id, companyResponse);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj,HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CompanyController update() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,company_unable_to_update_message, Iconstants.ERROR),HttpStatus.BAD_REQUEST);
		}
		logger.info(" CompanyController delete() ends, companyId :"+id);
		return obj;
	}

	/**
	 * this method is used to get the particular company Data
	 * @param id
	 * @return particular company details
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("id") Long id) {
		
		logger.info(" CompanyController get() starts, companyId :"+id);
		String json = new String();
		try {
			VendorModel vendorResponse = vendorService.get(id);
			if(vendorResponse != null) {
				json = mapper.writeValueAsString(vendorResponse);
			}
		} catch (Exception e) {
			logger.info("Exception inside CompanyController get() :"+e.getMessage());
		}

		logger.info(" CompanyController get() ends, companyId :"+id);
		return json;
	}
	
	/**
	 * this method is used when we click on add button on company screen
	 * @return master data for company
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info(" Inside CompanyController openAdd() Starts ");
		String json = null;
		
		try {
			VendorModel vendorModel = vendorService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(vendorModel);
		} catch (Exception e) {
			logger.error(" Exception inside CompanyController openAdd() :"+e.getMessage());
		}
		
		logger.info(" Inside CompanyController openAdd() Ends ");
		return json;
	}
	
	/**
	 * this method is used to searchCompany based on company name
	 * @param companyName
	 * @return List<Companies>
	 */
	@RequestMapping(value = "/{vendorName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchVendor(@PathVariable("vendorName") String vendorName) {
		
		logger.info("Inside CompanyController searchCompany() Starts, companyName :"+vendorName);
		String json = new String();
		
		try {
			List<VendorModel> vendorList = vendorService.getVendorByVendorName(vendorName);
			if(vendorList != null && vendorList.size() > 0) {
				json = mapper.writeValueAsString(vendorList);
			}
		} catch (Exception e) {
			logger.error("Exception inside CompanyController searchCompany() is :" + e.getMessage());
		}
		
		logger.info(" Inside CompanyController searchCompany() Ends, companyName :"+vendorName);
		return json;
	}
	
	@RequestMapping(value = "/{id}/additionalContacts", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getVendorAdditionalContacts(@PathVariable("id") Long id) {
		
		logger.info(" CompanyController get() starts, companyId :"+id);
		String json = new String();
		try {
			VendorModel vendorResponse = vendorService.getVendorContacts(id);
			if(vendorResponse != null) {
				json = mapper.writeValueAsString(vendorResponse);
			}
		} catch (Exception e) {
			logger.info("Exception inside CompanyController get() :"+e.getMessage());
		}

		logger.info(" CompanyController get() ends, companyId :"+id);
		return json;
	}
}
