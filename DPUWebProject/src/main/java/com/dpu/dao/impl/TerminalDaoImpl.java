package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.TerminalDao;
import com.dpu.entity.Terminal;

/**
 * @author gagan
 *
 */

@Repository
@Transactional
public class TerminalDaoImpl extends GenericDaoImpl<Terminal> implements TerminalDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Terminal> findAll(Session session) {
		
		List<Terminal> terminalList = session.createQuery("select t from Terminal t join fetch t.shipper shipper").list();
		return terminalList;
	}

}
