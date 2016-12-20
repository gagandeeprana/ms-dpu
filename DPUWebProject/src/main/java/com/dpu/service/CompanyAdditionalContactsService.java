package com.dpu.service;

import java.util.List;

import com.dpu.entity.CompanyAdditionalContacts;

public interface CompanyAdditionalContactsService {

	CompanyAdditionalContacts add(CompanyAdditionalContacts companyAdditionalContacts);
	
	CompanyAdditionalContacts update(CompanyAdditionalContacts companyAdditionalContacts);
	
	boolean delete(CompanyAdditionalContacts companyAdditionalContacts);
	
	List<CompanyAdditionalContacts> getAll(int companyId);
	
	CompanyAdditionalContacts get(int id);
	
}
