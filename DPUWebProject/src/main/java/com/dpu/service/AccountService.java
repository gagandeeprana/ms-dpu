package com.dpu.service;

import java.util.List;

import com.dpu.model.AccountModel;
import com.dpu.model.TaxCodeModel;

public interface AccountService {
	Object update(Long id, TaxCodeModel taxCodeModel);

	Object delete(Long id);

	List<AccountModel> getAll();

	//HandlingModel getOpenAdd();

	TaxCodeModel get(Long id);
	
	List<TaxCodeModel> getSpecificData();

	Object addTaxCode(TaxCodeModel taxCodeModel);

	List<TaxCodeModel> getTaxCodeByTaxCodeName(String taxCodeName);

}
