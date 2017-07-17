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
import com.dpu.model.HandlingModel;
import com.dpu.model.IssueModel;
import com.dpu.model.Success;
import com.dpu.service.IssueService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "issue")
public class IssueController extends MessageProperties {

	Logger logger = Logger.getLogger(IssueController.class);

	@Autowired
	IssueService issueService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${issue_unable_to_add_message}")
	private String issue_unable_to_add_message;

	@Value("${issue_unable_to_delete_message}")
	private String issue_unable_to_delete_message;
	
	@Value("${issue_unable_to_update_message}")
	private String issue_unable_to_update_message;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside IssueController getAll() Starts ");
		String json = null;

		try {
			List<IssueModel> responses = issueService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside IssueController getAll() :"+ e.getMessage());
		}
		
		logger.info("Inside IssueController getAll() Ends ");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody IssueModel issueModel) {

		logger.info("Inside IssueController add() starts ");
		Object obj = null;
		
		try {

			Object result = issueService.addIssue(issueModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside IssueController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,issue_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside IssueController add() ends ");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long issueId) {

		logger.info("Inside HandlingController delete() Starts, handlingId is :" + issueId);
		Object obj = null;

		try {
			Object result = issueService.delete(issueId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside HandlingController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,issue_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		logger.info("Inside CategoryController delete() Ends, id is :" + issueId);
		return obj;

	}

	/**
	 * this method is used to update the handling based on handlingID
	 * @param handlingId
	 * @param handlingModel
	 * @return List<Categories>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long handlingId, @RequestBody HandlingModel handlingModel) {

		logger.info("Inside HandlingController update() Starts, handlingId is :" + handlingId);
		Object obj = null;
		try {
			Object result = issueService.update(handlingId, handlingModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside HandlingController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,issue_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside HandlingController update() Ends, handlingId is :" + handlingId);
		return obj;
	}

	@RequestMapping(value = "/{issueId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getIssueByIssueId(@PathVariable("issueId") Long issueId) {
		
		logger.info("Inside IssueController getHandlingById() Starts, Id:"+ issueId);
		String json = null;

		try {

			IssueModel issueModel = issueService.get(issueId);

			if (issueModel != null) {
				json = mapper.writeValueAsString(issueModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside IssueController getHandlingById() :"+ e.getMessage());
		}

		logger.info("Inside IssueController getHandlingById() Ends, Id:"+ issueId);
		return json;
	}

	/**
	 * this method is used when we click on add button on issue screen
	 * send master data
	 * @return master data for add handling
	 * @author lakhvir
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info("Inside IssueController openAdd() Starts ");
		String json = null;

		try {
			IssueModel model = issueService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside IssueController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside IssueController openAdd() ends ");
		return json;
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
			IssueModel model = issueService.getUnitNo(categoryId);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
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
