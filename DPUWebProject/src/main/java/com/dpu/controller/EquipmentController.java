/**
 * 
 */
package com.dpu.controller;

import java.util.ArrayList;
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

import com.dpu.constants.Iconstants;
import com.dpu.entity.Category;
import com.dpu.entity.Equipment;
import com.dpu.model.CategoryReq;
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



	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllEquipment() {
		logger.info("[getAll]: Enter");
		String json = null;
		try {
			List<Equipment> lstequipments = equipmentService.getAll();
			json = mapper.writeValueAsString(lstequipments);
			List<EquipmentReq> responses = new ArrayList<EquipmentReq>();
			for (Equipment equipment : lstequipments) {
				EquipmentReq response = new EquipmentReq();
				BeanUtils.copyProperties(response, equipment);
				responses.add(response);
			}
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
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
		logger.info("[add]: Enter");
		Object obj = null;
		try {

			System.out.println(new ObjectMapper().writeValueAsString(equipmentReq));
			Equipment equipment = setCategoryValues(equipmentReq);
			boolean result = equipmentService.add(equipment);
			System.out.println("result value : " + result);
			
			if (result) {
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
		logger.info("[getAll] :Exit");
		return obj;
	}
	
	private Equipment setCategoryValues(EquipmentReq equipmentReq) {
		Equipment equipment = new Equipment();
		equipment.setEquipmentName(equipmentReq.getEquipmentName());
		equipment.setDescription(equipmentReq.getDescription());
		 

		return equipment;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {
		logger.info("[delete] :Enter : Id : "+id);
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
		logger.info("[delete] :Exit ");
		return obj;

	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Equipment equipment) {
		logger.info("[update] :Enter :ID:  "+id );
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
		logger.info("[update] :Exit   " );
		return obj;
	}

	@RequestMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("equipmentId") int id) {
		logger.info("[get] :Enter :Id:   "+id );
		String json = null;
		try {
			Equipment equipment = equipmentService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			
			CategoryReq response = new CategoryReq();
			BeanUtils.copyProperties(response, equipment);

			if (response != null) {
				json = mapper.writeValueAsString(equipment);
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.error("EquipmentController : get " + e);
		}
		logger.info("[get] :Exit    " );
		return json;
	}

}
