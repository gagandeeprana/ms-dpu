/**
 * 
 */
package com.dpu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.EquipmentDao;
import com.dpu.entity.Equipment;
import com.dpu.service.EquipmentService;

/**
 * @author jagvir
 *
 */
@Component
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	EquipmentDao equipmentDao;

	@Override
	public Equipment add(Equipment equipment) {
		return equipmentDao.save(equipment);
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
