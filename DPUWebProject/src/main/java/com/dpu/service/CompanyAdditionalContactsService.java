package com.dpu.service;

import java.util.List;

import com.dpu.entity.CompanyAdditionalContacts;

public interface CompanyAdditionalContactsService {

	CompanyAdditionalContacts add(CompanyAdditionalContacts companyAdditionalContacts);
	
	CompanyAdditionalContacts update(CompanyAdditionalContacts companyAdditionalContacts);
	
	boolean delete(CompanyAdditionalContacts companyAdditionalContacts);
	
	List<CompanyAdditionalContacts> getAll(Long companyId);
	
	CompanyAdditionalContacts get(Long id);
	
}
