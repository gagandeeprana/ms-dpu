/**
 * 
 */
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

import com.dpu.common.CommonProperties;
import com.dpu.constants.Iconstants;
import com.dpu.model.EquipmentReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.EquipmentService;
import com.dpu.util.MessageProperties;

/**
 * @author jagvir
 *
 */
@RestController
@RequestMapping(value = "equipment")
public class EquipmentController extends MessageProperties {

	Logger logger = Logger.getLogger(EquipmentController.class);

	@Autowired
	EquipmentService equipmentService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/{equipmentname}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllEquipment(
			@PathVariable("equipmentname") String equipmentName) {
		logger.info("[getAll]: Enter");
		String json = new String();
		try {
			List<EquipmentReq> lstequipments = equipmentService
					.getAll(equipmentName);
			if (lstequipments != null && lstequipments.size() > 0) {
				json = mapper.writeValueAsString(lstequipments);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error(e);
			logger.error("EquipmentController : getAll " + e);
		}
		logger.info("[getAll] :Exit");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllEquipment() {
		logger.info("[getAll]: Enter");
		String json = new String();
		try {
			List<EquipmentReq> lstequipments = equipmentService.getAll("");
			if (lstequipments != null && lstequipments.size() > 0) {
				json = mapper.writeValueAsString(lstequipments);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error(e);
			logger.error("EquipmentController : getAll " + e);
		}
		logger.info("[getAll] :Exit");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody EquipmentReq equipmentReq) {

		logger.info("EquipmentController: add(): STARTS");
		Object obj = null;
		try {

			Object result = equipmentService.add(equipmentReq);

			if (result != null) {
				if (result instanceof Success) {
					obj = new ResponseEntity<Object>(result, HttpStatus.OK);
				} else {
					obj = new ResponseEntity<Object>(
							new Failed(
									Integer.parseInt(CommonProperties.Equipment_unable_to_add_code),
									CommonProperties.Equipment_unable_to_add_message,
									Iconstants.ERROR), HttpStatus.BAD_REQUEST);
				}
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(equipmentUnableToAddCode),
						equipmentUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			obj = new ResponseEntity<Object>(new Failed(
					Integer.parseInt(equipmentUnableToAddCode),
					equipmentUnableToAddMessage, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
			logger.fatal("EquipmentController: add(): Exception: "
					+ e.getMessage());
		}

		logger.info("EquipmentController: add(): ENDS");

		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		logger.info("[delete] :Enter : Id : " + id);
		Object obj = null;
		// boolean result = false;
		try {
			/*
			 * Equipment equipment = null;//equipmentService.get(id); if
			 * (equipment != null) {
			 */
			Object result = equipmentService.delete(id);
			/* } */
			if (result != null) {
				if (result instanceof Success) {
					obj = new ResponseEntity<Object>(result, HttpStatus.OK);
				} else {
					obj = new ResponseEntity<Object>(
							new Failed(
									Integer.parseInt(CommonProperties.Equipment_unable_to_delete_code),
									CommonProperties.Equipment_unable_to_delete_message,
									Iconstants.ERROR), HttpStatus.BAD_REQUEST);
				}
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(equipmentUnableToDeleteCode),
						equipmentUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			obj = new ResponseEntity<Object>(new Failed(
					Integer.parseInt(equipmentUnableToDeleteCode),
					equipmentUnableToDeleteMessage, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
			logger.error("EquipmentController : delete " + e);
		}
		logger.info("[delete] :Exit ");
		return obj;

	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id,
			@RequestBody EquipmentReq equipmentReq) {
		logger.info("[update] :Enter :ID:  " + id);
		Object obj = null;
		try {
			equipmentReq.setEquipmentId(id);
			Object result = equipmentService.update(id, equipmentReq);
			if (result != null) {
				if (result instanceof Success) {
					obj = new ResponseEntity<Object>(result, HttpStatus.OK);
				} else {
					obj = new ResponseEntity<Object>(
							new Failed(
									Integer.parseInt(CommonProperties.Equipment_unable_to_update_code),
									CommonProperties.Equipment_unable_to_update_message,
									Iconstants.ERROR), HttpStatus.BAD_REQUEST);
				}
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(equipmentUnableToUpdateCode),
						equipmentUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			obj = new ResponseEntity<Object>(new Failed(
					Integer.parseInt(equipmentUnableToUpdateCode),
					equipmentUnableToUpdateMessage, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
			logger.error("EquipmentController : update " + e);
		}
		logger.info("[update] :Exit   ");
		return obj;
	}

	@RequestMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("equipmentId") Long id) {

		logger.info("EquipmentController: get(): STARTS");

		String json = new String();
		try {

			EquipmentReq equipmentReq = equipmentService.get(id);
			if (equipmentReq != null) {
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(equipmentReq);
				System.out.println(json);
			}
		} catch (Exception e) {
			logger.info("EquipmentController: get(): Exception:  "
					+ e.getMessage());
		}

		logger.info("EquipmentController: get(): ENDS");

		return json;
	}

}
