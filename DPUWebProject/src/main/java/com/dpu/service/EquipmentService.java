/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Equipment;

/**
 * @author jagvir
 *
 */
public interface EquipmentService {

	Equipment add(Equipment equipment);

	Equipment update(int id, Equipment equipment);

	boolean delete(Equipment equipment);

	List<Equipment> getAll();

	Equipment get(int id);

}
