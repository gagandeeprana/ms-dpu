/**
 * 
 */
package com.dpu.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.dpu.common.CommonProperties;
import com.dpu.constants.Iconstants;
import com.dpu.entity.Category;
import com.dpu.entity.Division;
import com.dpu.model.CategoryReq;
import com.dpu.model.DivisionReq;
import com.dpu.model.EquipmentReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TruckResponse;
import com.dpu.service.DivisionService;
import com.dpu.util.MessageProperties;

/**
 * @author jagvir
 *
 */
@RestController
@RequestMapping(value = "division")
public class DivisionController extends MessageProperties {

	Logger logger = Logger.getLogger(DivisionController.class);

	@Autowired
	DivisionService divisionService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllDivisions() {
		logger.info("[getAllDivisions] : Enter ");
		String json = null;
		try {
			List<DivisionReq> lstDivisionReqs = divisionService.getAll("");
			if (lstDivisionReqs != null && lstDivisionReqs.size() > 0) {
				json = mapper.writeValueAsString(lstDivisionReqs);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error(e);
			logger.error("DivisionController : getAll " + e);
		}
		logger.info("[getAll] :Exit");
		return json;
	}

	@RequestMapping(value = "/{divisionname}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllDivision(
			@PathVariable("divisionname") String divisionName) {
		logger.info("[getAllDivision]: Enter");
		String json = new String();
		try {
			List<DivisionReq> lstDivisions = divisionService
					.getAll(divisionName);
			if (lstDivisions != null && lstDivisions.size() > 0) {
				json = mapper.writeValueAsString(lstDivisions);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error(e);
			logger.error("DivisionController : getAllDivision " + e);
		}
		logger.info("[getAllDivision] :Exit");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object addDivision(@RequestBody DivisionReq divisionReq) {
		logger.info("[DivisionController] : addDivision");
		Object obj = null;
		try {

			Object result = divisionService.add(divisionReq);

			if (result != null) {
				if (result instanceof Success) {
					obj = new ResponseEntity<Object>(result, HttpStatus.OK);
				} else {
					obj = new ResponseEntity<Object>(
							new Failed(
									Integer.parseInt(CommonProperties.Division_unable_to_add_code),
									CommonProperties.Division_unable_to_add_message,
									Iconstants.ERROR), HttpStatus.BAD_REQUEST);
				}
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(divisionUnableToAddCode),
						divisionUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.fatal("DivisionController: add(): Exception: "
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(
					Integer.parseInt(divisionUnableToAddCode),
					divisionUnableToAddMessage, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("DivisionController: add(): ENDS");

		return obj;

	}

	//
	// private Division setDivivionValues(DivisionReq divisionReq) {
	// Division division = new Division();
	// division.setCarrierCode(divisionReq.getCarrierCode());
	// division.setContractPrefix(divisionReq.getContractPrefix());
	// division.setDivisionCode(divisionReq.getDivisionCode());
	// division.setDivisionName(divisionReq.getDivisionName());
	// division.setFedral(divisionReq.getFedral());
	// division.setInvoicePrefix(divisionReq.getInvoicePrefix());
	// division.setProvincial(divisionReq.getProvincial());
	// division.setSCAC(divisionReq.getSCAC());
	//
	// return division;
	// }
	//
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		logger.info("[delete] : Enter  : Id : " + id);
		Object obj = null;
		try {
			Object result = divisionService.delete(id);
			if (result != null) {
				if (result instanceof Success) {
					obj = new ResponseEntity<Object>(result, HttpStatus.OK);
				} else {
					obj = new ResponseEntity<Object>(
							new Failed(
									Integer.parseInt(CommonProperties.Division_unable_to_delete_code),
									CommonProperties.Division_unable_to_delete_message,
									Iconstants.ERROR), HttpStatus.BAD_REQUEST);
				}
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(divisionUnableToDeleteCode),
						divisionUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception in delete of DivisionController ", e);
			obj = new ResponseEntity<Object>(new Failed(
					Integer.parseInt(divisionUnableToDeleteCode),
					divisionUnableToDeleteMessage, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}
		logger.info("[delete] : Exit : ");
		return obj;

	}

	//
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id,
			@RequestBody DivisionReq divisionReq) {
		logger.info("[DivisionController] [update] :Enter :ID:  " + id);
		Object obj = null;
		try {
			divisionReq.setDivisionId(id);
			Object result = divisionService.update(id, divisionReq);
			if (result != null) {
				if (result instanceof Success) {
					obj = new ResponseEntity<Object>(result, HttpStatus.OK);
				} else {
					obj = new ResponseEntity<Object>(
							new Failed(
									Integer.parseInt(CommonProperties.Division_unable_to_update_code),
									CommonProperties.Division_unable_to_update_message,
									Iconstants.ERROR), HttpStatus.BAD_REQUEST);
				}
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(divisionUnableToUpdateCode),
						divisionUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			obj = new ResponseEntity<Object>(new Failed(
					Integer.parseInt(divisionUnableToUpdateCode),
					divisionUnableToUpdateMessage, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
			logger.error("EquipmentController : update " + e);
		}
		logger.info("[DivisionController] [update] :Exit   ");
		return obj;
	}

	@RequestMapping(value = "/{divisionId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("divisionId") Long id) {
		logger.info("[get] : Enter : ID : " + id);
		String json = null;
		try {
			DivisionReq divisionReq = divisionService.get(id);
			if (divisionReq != null) {
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(divisionReq);
				System.out.println(json);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[get] : Exit  ");
		return json;
	}

}
