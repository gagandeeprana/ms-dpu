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

import com.dpu.constants.Iconstants;
import com.dpu.entity.Equipment;
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
	@Autowired
	EquipmentService equipmentService;

	ObjectMapper mapper = new ObjectMapper();

	Logger logger = Logger.getLogger(EquipmentController.class);

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		String json = null;
		try {
			List<Equipment> lstEquipments = equipmentService.getAll();
			json = mapper.writeValueAsString(lstEquipments);
		} catch (Exception e) {
			System.out.println(e);
			logger.error(e);
			logger.error("EquipmentController : getAll " + e);
		}
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Equipment equipment) {
		Object obj = null;
		try {
			Equipment result = equipmentService.add(equipment);
			if (result != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(equipmentAddedCode),
						equipmentAddedMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(equipmentUnableToAddCode),
						equipmentUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error("EquipmentController : add: " + e);
		}
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {
		Object obj = null;
		boolean result = false;
		try {
			Equipment equipment = equipmentService.get(id);
			if (equipment != null) {
				result = equipmentService.delete(equipment);
			}
			if (result) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(equipmentDeletedCode),
						equipmentDeletedMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(equipmentUnableToDeleteCode),
						equipmentUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error("EquipmentController : delete " + e);
		}
		return obj;

	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Equipment equipment) {
		Object obj = null;
		try {
			equipment.setEquipmentId(id);
			Equipment response = equipmentService.update(id, equipment);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(equipmentUpdateCode),
						equipmentUpdateMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(equipmentUnableToUpdateCode),
						equipmentUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error("EquipmentController : update " + e);
		}
		return obj;
	}

	@RequestMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("equipmentId") int id) {
		String json = null;
		try {
			Equipment equipment = equipmentService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(equipment);
		} catch (Exception e) {
			System.out.println(e);
			logger.error("EquipmentController : get " + e);
		}
		return json;
	}

}
