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
import com.dpu.model.Failed;
import com.dpu.model.IssueModel;
import com.dpu.model.PurchaseOrderModel;
import com.dpu.model.Success;
import com.dpu.service.IssueService;
import com.dpu.service.PurchaseOrderService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "po")
public class PurchaseOrderController extends MessageProperties {

	Logger logger = Logger.getLogger(PurchaseOrderController.class);

	@Autowired
	IssueService issueService;
	
	@Autowired
	PurchaseOrderService poService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${po_unable_to_add_message}")
	private String po_unable_to_add_message;

	@Value("${po_unable_to_delete_message}")
	private String po_unable_to_delete_message;
	
	@Value("${po_unable_to_update_message}")
	private String po_unable_to_update_message;

	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info("Inside PurchaseOrderController openAdd() Starts ");
		String json = null;

		try {
			PurchaseOrderModel model = poService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside PurchaseOrderController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside PurchaseOrderController openAdd() ends ");
		return json;
	}
	
	@RequestMapping(value = "/category/{categoryId}/unitType/{unitTypeId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCategoryAndUnitTypeIssues(@PathVariable("categoryId") Long categoryId, @PathVariable("unitTypeId") Long unitTypeId) {
		
		logger.info("Inside PurchaseOrderController getCategoryAndUnitTypeIssues() Starts ");
		String json = null;

		try {
			List<IssueModel> issueList = poService.getCategoryAndUnitTypeIssues(categoryId, unitTypeId);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(issueList);
		} catch (Exception e) {
			logger.error(" Exception inside PurchaseOrderController getCategoryAndUnitTypeIssues() :"+ e.getMessage());
		}

		logger.info("Inside PurchaseOrderController getCategoryAndUnitTypeIssues() ends ");
		return json;
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody PurchaseOrderModel poModel) {

		logger.info("Inside PurchaseOrderController add() starts ");
		Object obj = null;
		
		try {

			Object result = poService.addPO(poModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, po_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside PurchaseOrderController add() ends ");
		return obj;
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside PurchaseOrderController getAll() Starts ");
		String json = null;

		try {
			List<IssueModel> responses = issueService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController getAll() :"+ e.getMessage());
		}
		
		logger.info("Inside PurchaseOrderController getAll() Ends ");
		return json;
	}

	@RequestMapping(value = "/{purchaseOrderId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getPurchaseOrderById(@PathVariable("purchaseOrderId") Long purchaseOrderId) {
		
		logger.info("Inside PurchaseOrderController getIssueByIssueId() Starts, issueId:"+ purchaseOrderId);
		String json = null;

		try {

			IssueModel issueModel = issueService.get(purchaseOrderId);

			if (issueModel != null) {
				json = mapper.writeValueAsString(issueModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside PurchaseOrderController getIssueByIssueId() :"+ e.getMessage());
		}

		logger.info("Inside PurchaseOrderController getIssueByIssueId() Ends, issueId:"+ purchaseOrderId);
		return json;
	}

	@RequestMapping(value = "/{issueId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("issueId") Long issueId) {

		logger.info("Inside IssueController delete() Starts, issueId is :" + issueId);
		Object obj = null;

		try {
			Object result = issueService.delete(issueId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, po_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		logger.info("Inside IssueController delete() Ends, issueId is :" + issueId);
		return obj;

	}

	@RequestMapping(value = "/{issueId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("issueId") Long issueId, @RequestBody IssueModel issueModel) {

		logger.info("Inside IssueController update() Starts, issueId is :" + issueId);
		Object obj = null;
		try {
			Object result = issueService.update(issueId, issueModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, po_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside IssueController update() Ends, issueId is :" + issueId);
		return obj;
	}

	/**
	 * this method is used when we click on add button on issue screen
	 * send master data
	 * @return master data for add handling
	 * @author lakhvir
	 */
	@RequestMapping(value = "/category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getUnitNo(@PathVariable("categoryId") Long categoryId) {
		
		logger.info("Inside IssueController getUnitNo() Starts ");
		String json = null;

		try {
		//	IssueModel model = issueService.getUnitNo(categoryId);
			//ObjectMapper mapper = new ObjectMapper();
			//json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside IssueController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside IssueController openAdd() ends ");
		return json;
	}

	
	@RequestMapping(value = "/{issueName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchIssue(@PathVariable("issueName") String issueName) {

		logger.info("Inside IssueController searchIssue() Starts, issueName :"+ issueName);
		String json = new String();

		try {
			List<IssueModel> handlingList = issueService.getIssueByIssueName(issueName);
			if (handlingList != null && handlingList.size() > 0) {
				json = mapper.writeValueAsString(handlingList);
			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController searchIssue() is :"+ e.getMessage());
		}

		logger.info(" Inside IssueController searchIssue() Ends, issueName :"+ issueName);
		return json;
	}

	
	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("Inside IssueController getSpecificData() Starts ");
		String json = new String();

		try {
			List<IssueModel> issueList = issueService.getSpecificData();
			if (issueList != null && issueList.size() > 0) {
				json = mapper.writeValueAsString(issueList);
			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController getSpecificData() is :"+ e.getMessage());
		}

		logger.info("Inside IssueController getSpecificData() Ends ");
		return json;
	}
}
