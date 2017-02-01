package com.dpu.service.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CompanyBillingLocationDao;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.service.CompanyBillingLocationService;

@Component
public class CompanyBillingLocationServiceImpl implements CompanyBillingLocationService{

	@Autowired
	CompanyBillingLocationDao companyBillingLocationDao;
	
	@Override
	public CompanyBillingLocation add(CompanyBillingLocation companyBillingLocation) {
		return companyBillingLocationDao.save(companyBillingLocation);
	}

	@Override
	public CompanyBillingLocation update(CompanyBillingLocation companyBillingLocation) {
		return companyBillingLocationDao.update(companyBillingLocation);
	}

	@Override
	public boolean delete(CompanyBillingLocation companyBillingLocation) {
		boolean result = false;
		try {
			companyBillingLocationDao.delete(companyBillingLocation);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<CompanyBillingLocation> getAll(Long companyId) {
		try {
			Criterion criterion = Restrictions.eq("company.companyId", companyId);
			return companyBillingLocationDao.find(criterion);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public CompanyBillingLocation get(Long id) {
		return companyBillingLocationDao.findById(id);
	}
}
