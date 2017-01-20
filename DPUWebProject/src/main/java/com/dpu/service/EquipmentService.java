/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Equipment;
import com.dpu.model.EquipmentReq;

/**
 * @author jagvir
 *
 */
public interface EquipmentService {

	Equipment update(Long id, Equipment equipment);

	boolean delete(Equipment equipment);

	EquipmentReq get(Long id);

	List<EquipmentReq> getAll(String equipmentName);

	Equipment add(EquipmentReq equipmentReq);

}
