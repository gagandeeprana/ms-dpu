/**
 * 
 */
package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.EquipmentDao;
import com.dpu.entity.Equipment;
import com.dpu.model.EquipmentReq;
import com.dpu.service.EquipmentService;

/**
 * @author jagvir
 *
 */
@Component
public class EquipmentServiceImpl implements EquipmentService {
	
	Logger logger = Logger.getLogger(EquipmentServiceImpl.class);

	@Autowired
	EquipmentDao equipmentDao;

	@Override
	public boolean add(Equipment equipment) {
		logger.info("[add]:Service:  Enter");

		boolean returnValue = false;
		try {

			// truck.setCreated("sumit");
			// truck.setCreatedOn(new Date());
			//
			// truck.setModifiedBy("sumit");
			// truck.setModifiedOn(new Date());

			Equipment equipmentt = equipmentDao.save(equipment);
			System.out.println("[addCategory]category Id :" + equipmentt.getEquipmentId());
			returnValue = true;
			return returnValue;

		} catch (Exception e) {
			logger.info("[add]:Exception:    : ", e);
			System.out.println(e);
			return returnValue;
		} finally {
			logger.info("[add]:Service:  returnValue : " + returnValue);
		}
	}

	@Override
	public Equipment update(Long id, Equipment equipment) {
		return equipmentDao.update(equipment);
	}

	@Override
	public boolean delete(Equipment equipment) {
		boolean result = false;
		try {
			equipmentDao.delete(equipment);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<EquipmentReq> getAll(String equipmentName) {
		List<Equipment> equipments = null;
		List<EquipmentReq> equipmentResponse = new ArrayList<EquipmentReq>();
		if(equipmentName != null && equipmentName.length() > 0) {
			Criterion criterion = Restrictions.like("equipmentName", equipmentName);
			equipments = equipmentDao.find(criterion);
		} else {
			equipments = equipmentDao.findAll();
		}
		if(equipments != null  && equipments.size() > 0) {
			for(Equipment equipment : equipments) {
				EquipmentReq equipmentReq = new EquipmentReq();
				equipmentReq.setEquipmentName(equipment.getEquipmentName());
				equipmentReq.setType(equipment.getType().getTypeName());
				equipmentReq.setDescription(equipment.getDescription());
				equipmentResponse.add(equipmentReq);
			}
		}
		return equipmentResponse;
	}

	@Override
	public Equipment get(Long id) {
		return equipmentDao.findById(id);
	}

}
