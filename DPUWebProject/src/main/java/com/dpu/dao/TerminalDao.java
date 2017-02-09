package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Terminal;

/**
 * @author gagan
 *
 */

public interface TerminalDao extends GenericDao<Terminal> {

	List<Terminal> findAll(Session session);

}
