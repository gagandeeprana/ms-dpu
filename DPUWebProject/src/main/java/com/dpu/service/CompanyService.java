package com.dpu.service;

import java.util.List;

import com.dpu.entity.Company;

public interface CompanyService {

	Company add(Company company);
	
	Company update(Company company);
	
	boolean delete(Company company);
	
	List<Company> getAll();
	
	Company get(int id);
	
}
