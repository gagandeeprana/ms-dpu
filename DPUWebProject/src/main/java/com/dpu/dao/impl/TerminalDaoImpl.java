package com.dpu.dao.impl;

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

}
