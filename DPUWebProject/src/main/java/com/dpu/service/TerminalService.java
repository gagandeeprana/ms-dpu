package com.dpu.service;

import java.util.List;

import com.dpu.entity.Terminal;

public interface TerminalService {

	Terminal add(Terminal terminal);
	
	Terminal update(Terminal terminal);
	
	boolean delete(Terminal terminal);
	
	List<Terminal> getAll();
	
	Terminal get(int id);
	
}
