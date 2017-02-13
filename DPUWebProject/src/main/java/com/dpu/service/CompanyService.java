package com.dpu.service;

import java.util.List;

import com.dpu.entity.Company;
import com.dpu.model.CompanyResponse;

public interface CompanyService {

	Company addCompanyData(CompanyResponse companyResponse);
	
	Company update(Company company);
	
	Object delete(Long companyId);
	
	List<CompanyResponse> getAll();
	
	List<CompanyResponse> getCompanyData();

	CompanyResponse get(Long id);

	Object update(Long id, CompanyResponse companyResponse);

	CompanyResponse getOpenAdd();
	
}
