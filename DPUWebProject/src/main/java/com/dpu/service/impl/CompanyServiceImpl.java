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
	public boolean add(Company company) {
		return companyDao.add(company);
	}

	@Override
	public boolean update(int id, Company company) {
		return companyDao.update(id, company);
	}

	@Override
	public boolean delete(int id) {
		return companyDao.delete(id);
	}

	@Override
	public List<Company> getAll(String name) {
		return companyDao.getAll(name);
	}

	@Override
	public Company get(int id) {
		return companyDao.get(id);
	}
}
