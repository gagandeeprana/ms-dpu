package com.dpu.service;

import java.util.List;

import com.dpu.entity.CompanyBillingLocation;

public interface CompanyBillingLocationService {

	CompanyBillingLocation add(CompanyBillingLocation companyBillingLocation);
	
	CompanyBillingLocation update(CompanyBillingLocation companyBillingLocation);
	
	boolean delete(CompanyBillingLocation companyBillingLocation);
	
	List<CompanyBillingLocation> getAll(int companyId);
	
	CompanyBillingLocation get(int id);
	
}
