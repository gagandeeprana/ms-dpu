package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.TerminalDao;
import com.dpu.entity.Service;
import com.dpu.entity.Status;
import com.dpu.entity.Terminal;
import com.dpu.entity.Type;
import com.dpu.model.DPUService;
import com.dpu.model.TerminalResponse;
import com.dpu.model.TypeResponse;
import com.dpu.service.StatusService;
import com.dpu.service.TerminalService;

@Component
public class TerminalServiceImpl implements TerminalService {

	Logger logger = Logger.getLogger(TerminalServiceImpl.class);
	
	@Autowired
	TerminalDao terminalDao;

	@Autowired
	StatusService statusService;

	@Override
	public List<TerminalResponse> getAllTerminals() {
		
		List<Terminal> terminalList = terminalDao.findAll();
		List<TerminalResponse> terminalRespList = new ArrayList<TerminalResponse>();
		
		if(terminalList != null && !terminalList.isEmpty()){
			for (Terminal terminal : terminalList) {
				TerminalResponse terminalResponseObj = new TerminalResponse();
				terminalResponseObj.setTerminalName(terminal.getTerminalName());
				terminalResponseObj.setFacility(terminal.getFacility());
				terminalResponseObj.setLocation(terminal.getLocation());
				terminalResponseObj.setTerminalId(terminal.getTerminalId());
				terminalRespList.add(terminalResponseObj);
			}
		}
		
		return terminalRespList;
	}

	@Override
	public List<TerminalResponse> addTerminal(TerminalResponse terminalResponse) {
		Terminal terminal= setServiceValues(terminalResponse);
		terminalDao.save(terminal);
		return getAllTerminals();
	}

	private Terminal setServiceValues(TerminalResponse terminalResponse) {
		Terminal terminal  = new Terminal();
		terminal.setTerminalName(terminalResponse.getTerminalName());
		Status status = statusService.get(terminalResponse.getStatusId());
		terminal.setLocation(terminalResponse.getLocation());
		terminal.setFacility(terminalResponse.getFacility());
		terminal.setAvailableServices(terminalResponse.getAvailableServices());
		terminal.setCreatedBy("Anuj Nayyar");
		terminal.setCreatedOn(new Date());
		terminal.setModifiedBy("Anuj Nayyar");
		terminal.setModifiedOn(new Date());
		terminal.setStatus(status);
		return terminal;
	}

	@Override
	public List<TerminalResponse> deleteTerminal(Long id) {
		List<TerminalResponse> terminalResp = null;
		
		Terminal terminal= terminalDao.findById(id);
		try {
			if(terminal != null){
				terminalDao.delete(terminal);
			}
			terminalResp = getAllTerminals();
		} catch (Exception e) {
			terminalResp = null;
		}
		return terminalResp;
	}

	@Override
	public TerminalResponse getTerminal(Long id) {
		TerminalResponse terminalResp = new TerminalResponse();
		Terminal terminal = terminalDao.findById(id);
		
		if(terminal != null){
			terminalResp.setTerminalId(terminal.getTerminalId());
			terminalResp.setStatusId(terminal.getStatus().getId());
			terminalResp.setTerminalName(terminal.getTerminalName());
			terminalResp.setLocation(terminal.getLocation());
			terminalResp.setFacility(terminal.getFacility());
			
		}		
		return terminalResp;
	}

	@Override
	public TerminalResponse getOpenAdd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TerminalResponse> updateTerminal(Long id,
			TerminalResponse terminalResponse) {
			Terminal terminal= terminalDao.findById(id);
			terminal.setTerminalName(terminalResponse.getTerminalName());
			Status status = statusService.get(terminalResponse.getStatusId());
			terminal.setFacility(terminalResponse.getFacility());
			terminal.setAvailableServices(terminalResponse.getAvailableServices());
			terminal.setModifiedOn(new Date());
			terminal.setStatus(status);
			terminalDao.update(terminal);
			return getAllTerminals();
	}

	@Override
	public List<TerminalResponse> getTerminalByTerminalName(String serviceName) {
		// TODO Auto-generated method stub
		return null;
	}


}
