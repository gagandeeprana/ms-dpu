package com.dpu.service;

import java.util.List;

import com.dpu.entity.CompanyBillingLocation;

public interface CompanyBillingLocationService {

	CompanyBillingLocation add(CompanyBillingLocation companyBillingLocation);
	
	CompanyBillingLocation update(CompanyBillingLocation companyBillingLocation);
	
	boolean delete(Long id);
	
	List<CompanyBillingLocation> getAll(Long companyId);
	
	CompanyBillingLocation get(Long id);
	
}
