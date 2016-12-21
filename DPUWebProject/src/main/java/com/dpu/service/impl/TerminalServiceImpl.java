package com.dpu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.TerminalDao;
import com.dpu.entity.Terminal;
import com.dpu.service.TerminalService;

@Component
public class TerminalServiceImpl implements TerminalService {

	@Autowired
	TerminalDao terminalDao;
	
	@Override
	public Terminal add(Terminal terminal) {
		return terminalDao.save(terminal);
	}

	@Override
	public Terminal update(Terminal terminal) {
		return terminalDao.update(terminal);
	}

	@Override
	public boolean delete(Terminal terminal) {
		boolean result = false;
		try {
			terminalDao.delete(terminal);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<Terminal> getAll() {
		return terminalDao.findAll();
	}

	@Override
	public Terminal get(int id) {
		return terminalDao.findById(id);
	}
}
