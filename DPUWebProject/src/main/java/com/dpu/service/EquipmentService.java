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

	List<EquipmentReq> update(Long id, EquipmentReq equipmentReq);

	boolean delete(Long id);

	EquipmentReq get(Long id);

	List<EquipmentReq> getAll(String equipmentName);

	Equipment add(EquipmentReq equipmentReq);

}
