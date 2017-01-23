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

	List<EquipmentReq> delete(Long id);

	EquipmentReq get(Long id);

	List<EquipmentReq> getAll(String equipmentName);

	List<EquipmentReq> add(EquipmentReq equipmentReq);

}
