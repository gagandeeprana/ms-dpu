package com.dpu.service;

import java.util.List;

import com.dpu.entity.Company;

public interface CompanyService {

	boolean add(Company company);
	
	boolean update(int id, Company company);
	
	boolean delete(int id);
	
	List<Company> getAll(String name);
	
	Company get(int id);
	
}
