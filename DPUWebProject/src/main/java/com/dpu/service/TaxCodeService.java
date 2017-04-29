package com.dpu.service;

import java.util.List;

import com.dpu.model.HandlingModel;
import com.dpu.model.TaxCodeModel;

public interface TaxCodeService {
	Object update(Long id, HandlingModel handlingModel);

	Object delete(Long id);

	List<TaxCodeModel> getAll();

	HandlingModel getOpenAdd();

	HandlingModel get(Long id);
	
	List<HandlingModel> getSpecificData();

	Object addHandling(HandlingModel handlingModel);

	List<HandlingModel> getHandlingByHandlingName(String handlingName);

}
