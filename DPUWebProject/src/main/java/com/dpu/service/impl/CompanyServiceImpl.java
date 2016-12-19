package com.dpu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CompanyDao;
import com.dpu.entity.Company;
import com.dpu.service.CompanyService;

@Component
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	CompanyDao companyDao;
	
	@Override
	public Company add(Company company) {
		return companyDao.save(company);
	}

	@Override
	public Company update(Company company) {
		return companyDao.update(company);
	}

	@Override
	public boolean delete(Company company) {
		boolean result = false;
		try {
			companyDao.delete(company);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<Company> getAll() {
		return companyDao.findAll();
	}

	@Override
	public Company get(int id) {
		return companyDao.findById(id);
	}
}
