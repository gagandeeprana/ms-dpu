/**
 * 
 */
package com.dpu.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.EquipmentDao;
import com.dpu.entity.Category;
import com.dpu.entity.Equipment;
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
	public Equipment update(int id, Equipment equipment) {
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
	public List<Equipment> getAll() {
		return equipmentDao.findAll();
	}

	@Override
	public Equipment get(int id) {
		return equipmentDao.findById(id);
	}

}
