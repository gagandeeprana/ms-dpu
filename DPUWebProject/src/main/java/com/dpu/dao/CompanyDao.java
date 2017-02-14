package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Company;
import com.dpu.model.CompanyResponse;

public interface CompanyDao extends GenericDao<Company>{

	/*boolean add(Company company);
	
	boolean update(int id, Company company);
	
	boolean delete(int id);
	
	List<Company> getAll(String name);
	
	Company get(int id);*/
	
	List<Object[]> getCompanyData();

	void updateData(Company company, CompanyResponse companyResponse, Session session);

	List<Object[]> getBillingLocations(Long companyId, Session session);

	List<Object[]> getAdditionalContacts(Long companyId, Session session);
	
}
