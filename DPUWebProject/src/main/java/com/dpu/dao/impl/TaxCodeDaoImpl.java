/**
 * 
 */
package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.HandlingDao;
import com.dpu.dao.TaxCodeDao;
import com.dpu.entity.Handling;
import com.dpu.entity.TaxCode;

@Repository
public class TaxCodeDaoImpl extends GenericDaoImpl<TaxCode> implements TaxCodeDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<TaxCode> findAll(Session session) {
		
		StringBuilder sb = new StringBuilder(" select h from TaxCode h ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public TaxCode findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder(" select h from TaxCode h where h.taxCodeId =:taxCodeId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("taxCodeId", id);
		return (TaxCode) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Handling> getHandlingByHandlingName(Session session, String handlingName) {
		StringBuilder sb = new StringBuilder(" select h from Handling h join fetch h.status where h.name like :handlingName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("handlingName", "%"+handlingName+"%");
		return query.list();
	}

}
