package com.dpu.service.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CompanyAdditionalContactsDao;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.service.CompanyAdditionalContactsService;

@Component
public class CompanyAdditionalContactsServiceImpl implements CompanyAdditionalContactsService{

	@Autowired
	CompanyAdditionalContactsDao companyAdditionalContactsDao;
	
	@Override
	public CompanyAdditionalContacts add(CompanyAdditionalContacts companyAdditionalContacts) {
		return companyAdditionalContactsDao.save(companyAdditionalContacts);
	}

	@Override
	public CompanyAdditionalContacts update(CompanyAdditionalContacts companyAdditionalContacts) {
		return companyAdditionalContactsDao.update(companyAdditionalContacts);
	}

	@Override
	public boolean delete(Long additionalContactId) {
		boolean result = false;
		try {
			CompanyAdditionalContacts companyAdditionalContacts = get(additionalContactId);
			if(companyAdditionalContacts != null){
				companyAdditionalContactsDao.delete(companyAdditionalContacts);
				result = true;
			}
			
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<CompanyAdditionalContacts> getAll(Long companyId) {
		try {
			Criterion criterion = Restrictions.eq("company.companyId", companyId);
			return companyAdditionalContactsDao.find(criterion);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public CompanyAdditionalContacts get(Long id) {
		return companyAdditionalContactsDao.findById(id);
	}
}
