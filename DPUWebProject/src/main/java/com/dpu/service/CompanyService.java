package com.dpu.service;

import java.util.List;

import com.dpu.entity.Company;
import com.dpu.model.CompanyResponse;

public interface CompanyService {

	Company addCompanyData(CompanyResponse companyResponse);
	
	Company update(Company company);
	
	boolean delete(Company company);
	
	List<CompanyResponse> getAll();
	
	CompanyResponse get(int id);
	
}
