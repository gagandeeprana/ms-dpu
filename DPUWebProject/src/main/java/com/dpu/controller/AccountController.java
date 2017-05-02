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
import com.dpu.model.AccountModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TaxCodeModel;
import com.dpu.service.AccountService;
import com.dpu.service.TaxCodeService;

@RestController
@RequestMapping(value = "account")
public class AccountController {

	Logger logger = Logger.getLogger(AccountController.class);

	@Autowired
	AccountService accountService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${taxcode_unable_to_add_message}")
	private String taxcode_unable_to_add_message;

	@Value("${taxcode_unable_to_delete_message}")
	private String taxcode_unable_to_delete_message;
	
	@Value("${taxcode_unable_to_update_message}")
	private String taxcode_unable_to_update_message;

	/**
	 * this method is used to get all taxCodes
	 * @return List<taxCode>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside TaxCodeController getAll() Starts ");
		String json = null;

		try {
			List<AccountModel> responses = accountService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController getAll() :"+ e.getMessage());
		}
		
		logger.info("Inside TaxCodeController getAll() Ends ");
		return json;
	}

	/**
	 * this method is used to add the Tax code
	 * @param accountModel
	 * @return List<Tax code>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody AccountModel accountModel) {

		logger.info("Inside TaxCodeController add() starts ");
		Object obj = null;
		
		try {

			Object result = accountService.addAccount(accountModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,taxcode_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside TaxCodeController add() ends ");
		return obj;
	}

	/**
	 * this method is used to delete the taxCode based on taxcodeId
	 * @param id
	 * @return List<Tax codes>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long accountId) {

		logger.info("Inside TaxCodeController delete() Starts, handlingId is :" + accountId);
		Object obj = null;

		try {
			Object result = accountService.delete(accountId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,taxcode_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside TaxCodeController delete() Ends, id is :" + accountId);
		return obj;

	}

	/**
	 * this method is used to update the taxCode based on taxCodeId
	 * @param accountId
	 * @param accountModel
	 * @return List<TaxCode>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long accountId, @RequestBody AccountModel accountModel) {

		logger.info("Inside TaxCodeController update() Starts, taxCodeId is :" + accountId);
		Object obj = null;
		try {
			Object result = accountService.update(accountId, accountModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,taxcode_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside TaxCodeController update() Ends, taxCodeId is :" + accountId);
		return obj;
	}

	/**
	 * this method is used to get taxcode data based on taxcodeId
	 * @param accountId
	 * @return taxCodeData
	 */
	@RequestMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAccountById(@PathVariable("accountId") Long accountId) {
		
		logger.info("Inside TaxCodeController getTaxcodeById() Starts, Id:"+ accountId);
		String json = null;

		try {

			AccountModel accountModel = accountService.get(accountId);

			if (accountModel != null) {
				json = mapper.writeValueAsString(accountModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController getTaxcodeById() :"+ e.getMessage());
		}

		logger.info("Inside TaxCodeController getTaxcodeById() Ends, Id:"+ accountId);
		return json;
	}

	/**
	 * this method is used when we click on add button on handling screen
	 * send master data
	 * @return master data for add handling
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info("Inside HandlingController openAdd() Starts ");
		String json = null;

		try {
			AccountModel model = accountService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside HandlingController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside HandlingController openAdd() ends ");
		return json;
	}

	/**
	 * this method is used to get taxcode data based on taxcode name
	 * @param accountName
	 * @return List<TaxCode>
	 */
	@RequestMapping(value = "/{accountName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchTaxCode(@PathVariable("accountName") String accountName) {

		logger.info("Inside TaxCodeController searchTaxCode() Starts, taxCodeName :"+ accountName);
		String json = new String();

		try {
			List<AccountModel> accountList = accountService.getAccountByAccountName(accountName);
			if (accountList != null && accountList.size() > 0) {
				json = mapper.writeValueAsString(accountList);
			}
		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController searchTaxCode() is :"+ e.getMessage());
		}

		logger.info(" Inside TaxCodeController searchTaxCode() Ends, taxCodeName :"+ accountName);
		return json;
	}

	/**
	 * this method is used to get specific taxcode data (id and name)
	 * @return taxcode Id and name
	 */
	
	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("Inside TaxCodeController getSpecificData() Starts ");
		String json = new String();

		try {
			List<TaxCodeModel> taxCodeList = null;//taxCodeService.getSpecificData();
			if (taxCodeList != null && taxCodeList.size() > 0) {
				json = mapper.writeValueAsString(taxCodeList);
			}
		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController getSpecificData() is :"+ e.getMessage());
		}

		logger.info("Inside TaxCodeController getSpecificData() Ends ");
		return json;
	}
}
