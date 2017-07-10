package com.dpu.service;

import java.util.List;

import com.dpu.model.HandlingModel;
import com.dpu.model.VehicleMaintainanceCategoryModel;

public interface VehicleMaintainanceCategoryService {
	/*Object update(Long id, HandlingModel handlingModel);

	Object delete(Long id);

	List<HandlingModel> getAll();

	HandlingModel getOpenAdd();

	HandlingModel get(Long id);
	
	List<HandlingModel> getSpecificData();

	List<HandlingModel> getHandlingByHandlingName(String handlingName);*/

	Object addVMC(VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel);

}
