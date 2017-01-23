/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Division;
import com.dpu.model.DivisionReq;
import com.dpu.model.EquipmentReq;

/**
 * @author jagvir
 *
 */
public interface DivisionService {
	
	List<DivisionReq> update(Long id, DivisionReq divisionReq);

	List<DivisionReq> delete(Long id);

	DivisionReq get(Long id);

	List<DivisionReq> getAll(String divisionName);

	List<DivisionReq> add(DivisionReq divisionReq);
}
