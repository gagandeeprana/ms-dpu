package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Account;
import com.dpu.entity.TaxCode;

public interface AccountDao extends GenericDao<TaxCode> {

	List<Account> findAll(Session session);

	TaxCode findById(Long id, Session session);

	List<TaxCode> getTaxCodesByTaxCodeNames(Session session, String taxCodeName);
}
