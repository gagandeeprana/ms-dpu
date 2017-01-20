/**
 * 
 */
package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.EquipmentDao;
import com.dpu.entity.Equipment;
import com.dpu.entity.Type;
import com.dpu.model.EquipmentReq;
import com.dpu.model.TypeResponse;
import com.dpu.service.EquipmentService;
import com.dpu.service.TypeService;

/**
 * @author jagvir
 *
 */
@Component
public class EquipmentServiceImpl implements EquipmentService {
	
	Logger logger = Logger.getLogger(EquipmentServiceImpl.class);

	@Autowired
	EquipmentDao equipmentDao;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	TypeService typeService;

	@Override
	public Equipment add(EquipmentReq equipmentReq) {

		logger.info("EquipmentServiceImpl: add():  STARTS");

		Session session = null;
		Transaction tx = null;
		Equipment equipment = null;
		
		try {
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			equipment = equipmentDao.add(session, equipmentReq);

		} catch (Exception e) {
			logger.fatal("EquipmentServiceImpl: add(): Exception: " + e.getMessage());
			if(tx != null) {
				tx.rollback();
			}
		} finally {
			logger.info("EquipmentServiceImpl: add():  finally block");
			if(tx != null) {
				tx.commit();
			}
			if(session != null) {
				session.close();
			}
		}
		
		logger.info("EquipmentServiceImpl: add():  ENDS");

		return equipment;
	}
	
	@Override
	public List<EquipmentReq> update(Long id, EquipmentReq equipmentReq) {
		
		Equipment equipmentObj = equipmentDao.findById(id);
		if(equipmentObj != null){
			equipmentObj.setEquipmentName(equipmentReq.getEquipmentName());
			equipmentObj.setDescription(equipmentReq.getDescription());
			equipmentObj.setModifiedBy("gagan");
			equipmentObj.setModifiedOn(new Date());
			Type type = typeService.get(equipmentReq.getTypeId());
			equipmentObj.setType(type);
			equipmentDao.update(equipmentObj);
			
			return getAll("");
		} else{
			return null;
		}
	}

	@Override
	public boolean delete(Long id) {
		
		boolean result = false;
		Equipment equipment = equipmentDao.findById(id);
		if(equipment != null){
			try {
				equipmentDao.delete(equipment);
				result = true;
			} catch (Exception e) {
				result = false;
			}
		}
		
		return result;
	}

	@Override
	public List<EquipmentReq> getAll(String equipmentName) {
		List<Equipment> equipments = null;
		List<EquipmentReq> equipmentResponse = new ArrayList<EquipmentReq>();
		if(equipmentName != null && equipmentName.length() > 0) {
//			Criterion criterion = Restrictions.like("equipmentName", equipmentName);
//			equipments = equipmentDao.find(criterion);
		} else {
			equipments = equipmentDao.findAll();
		}
		if(equipments != null  && equipments.size() > 0) {
			for(Equipment equipment : equipments) {
				EquipmentReq equipmentReq = new EquipmentReq();
				equipmentReq.setEquipmentId(equipment.getEquipmentId());
				equipmentReq.setEquipmentName(equipment.getEquipmentName());
				equipmentReq.setType(equipment.getType().getTypeName());
				equipmentReq.setTypeId(equipment.getType().getTypeId());
				equipmentReq.setDescription(equipment.getDescription());
				equipmentResponse.add(equipmentReq);
			}
		}
		return equipmentResponse;
	}

	@Override
	public EquipmentReq get(Long id) {
		Equipment equipment = equipmentDao.findById(id);
		EquipmentReq response = null;
		if(equipment != null) {
			response = new EquipmentReq();
			response.setEquipmentId(equipment.getEquipmentId());
			response.setTypeId(equipment.getType().getTypeId());
			response.setEquipmentName(equipment.getEquipmentName());
			response.setDescription(equipment.getDescription());
			
			List<TypeResponse> typeList = typeService.getAll(1l);
			
			if(typeList != null && !typeList.isEmpty()){
				response.setTypeList(typeList);
			}
			
		}
		
		return response;
	}

}
