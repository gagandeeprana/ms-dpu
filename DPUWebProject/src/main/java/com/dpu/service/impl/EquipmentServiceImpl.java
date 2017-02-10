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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.common.CommonProperties;
import com.dpu.constants.Iconstants;
import com.dpu.dao.EquipmentDao;
import com.dpu.entity.Equipment;
import com.dpu.entity.Type;
import com.dpu.model.EquipmentReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
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

	private Object createSuccessObject(String msg, long code) {
		Success success = new Success();
		success.setCode(code);
		success.setMessage(msg);
		success.setResultList(getAll(""));
		return success;
	}

	private Object createFailedObject(String msg, long code) {
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		failed.setResultList(getAll(""));
		return failed;
	}

	@Override
	public Object add(EquipmentReq equipmentReq) {

		logger.info("EquipmentServiceImpl: add():  STARTS");

		Session session = null;
		Transaction tx = null;
		Equipment equipment = null;
		// String msg = "Equipment Added Successfully";

		try {

			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			equipment = equipmentDao.add(session, equipmentReq);
			if (equipment == null) {
				return createFailedObject(
						CommonProperties.Equipment_unable_to_add_message,
						Long.parseLong(CommonProperties.Equipment_unable_to_add_code));
			}
			if (tx != null) {
				tx.commit();
			}

		} catch (Exception e) {
			logger.fatal("EquipmentServiceImpl: add(): Exception: "
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			return createFailedObject(
					CommonProperties.Equipment_unable_to_add_message,
					Long.parseLong(CommonProperties.Equipment_unable_to_add_code));
		} finally {
			logger.info("EquipmentServiceImpl: add():  finally block");
			if (session != null) {
				session.close();
			}
		}

		logger.info("EquipmentServiceImpl: add():  ENDS");

		return createSuccessObject(CommonProperties.Equipment_added_message,
				Long.parseLong(CommonProperties.Equipment_added_code));
	}

	@Override
	public Object update(Long id, EquipmentReq equipmentReq) {
		logger.info("EquipmentServiceImpl: update():  Enter");
		String msg = "Equipment Updated Successfully";
		Equipment equipmentObj = equipmentDao.findById(id);
		Equipment equipment = null;
		if (equipmentObj != null) {
			equipmentObj.setEquipmentName(equipmentReq.getEquipmentName());
			equipmentObj.setDescription(equipmentReq.getDescription());
			equipmentObj.setModifiedBy("gagan");
			equipmentObj.setModifiedOn(new Date());
			Type type = typeService.get(equipmentReq.getTypeId());
			equipmentObj.setType(type);
			equipment = equipmentDao.update(equipmentObj);
			if (equipment == null) {
				return null;
			}
			return createSuccessObject(
					CommonProperties.Equipment_updated_message,
					Long.parseLong(CommonProperties.Equipment_updated_code));
		}
		logger.info("EquipmentServiceImpl: update():  Exit");
		return null;
	}

	@Override
	public Object delete(Long id) {
		logger.info("EquipmentServiceImpl: delete():  Enter");
		String msg = "Equipment Deleted Successfully";
		Equipment equipment = equipmentDao.findById(id);
		if (equipment != null) {
			try {
				equipmentDao.delete(equipment);
				return createSuccessObject(
						CommonProperties.Equipment_deleted_message,
						Long.parseLong(CommonProperties.Equipment_deleted_code));
			} catch (Exception e) {
				logger.error("EquipmentServiceImpl: delete(): Exception  : ", e);
				return null;
			}
		}
		logger.info("EquipmentServiceImpl: delete():  Exit");
		return null;
	}

	@Override
	public List<EquipmentReq> getAll(String equipmentName) {
		List<Equipment> equipments = null;
		List<EquipmentReq> equipmentResponse = new ArrayList<EquipmentReq>();
		if (equipmentName != null && equipmentName.length() > 0) {
			Criterion criterion = Restrictions.like("equipmentName",
					equipmentName, MatchMode.ANYWHERE);
			equipments = equipmentDao.find(criterion);
		} else {
			equipments = equipmentDao.findAll();
		}
		if (equipments != null && equipments.size() > 0) {
			for (Equipment equipment : equipments) {
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
		if (equipment != null) {
			response = new EquipmentReq();
			response.setEquipmentId(equipment.getEquipmentId());
			response.setTypeId(equipment.getType().getTypeId());
			response.setEquipmentName(equipment.getEquipmentName());
			response.setDescription(equipment.getDescription());

			List<TypeResponse> typeList = typeService.getAll(1l);

			if (typeList != null && !typeList.isEmpty()) {
				response.setTypeList(typeList);
			}

		}

		return response;
	}

}
