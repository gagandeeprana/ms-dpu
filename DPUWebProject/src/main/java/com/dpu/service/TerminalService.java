package com.dpu.service;

import java.util.List;

import com.dpu.entity.Terminal;
import com.dpu.model.DPUService;
import com.dpu.model.TerminalResponse;

public interface TerminalService {

	
	List<TerminalResponse> addTerminal(TerminalResponse terminalResponse);

	List<TerminalResponse> deleteTerminal(Long id);

	List<TerminalResponse> getAllTerminals();

	TerminalResponse getTerminal(Long id);

	TerminalResponse getOpenAdd();

	List<TerminalResponse> updateTerminal(Long id, TerminalResponse terminalResponse);

	List<TerminalResponse> getTerminalByTerminalName(String serviceName);
	
}
