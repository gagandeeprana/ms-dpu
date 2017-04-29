package com.dpu.service;

import java.util.List;

import com.dpu.model.HandlingModel;
import com.dpu.model.TaxCodeModel;

public interface TaxCodeService {
	Object update(Long id, TaxCodeModel taxCodeModel);

	Object delete(Long id);

	List<TaxCodeModel> getAll();

	HandlingModel getOpenAdd();

	TaxCodeModel get(Long id);
	
	List<HandlingModel> getSpecificData();

	Object addTaxCode(TaxCodeModel taxCodeModel);

	List<TaxCodeModel> getTaxCodeByTaxCodeName(String taxCodeName);

}
