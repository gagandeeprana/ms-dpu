/**
 * 
 */
package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Handling;
import com.dpu.entity.TaxCode;

public interface TaxCodeDao extends GenericDao<TaxCode> {

	List<TaxCode> findAll(Session session);

	Handling findById(Long id, Session session);

	List<Handling> getHandlingByHandlingName(Session session, String handlingName);
}
