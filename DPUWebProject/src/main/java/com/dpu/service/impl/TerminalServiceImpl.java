package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.ServiceDao;
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
import com.dpu.service.TypeService;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TerminalResponse getTerminal(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TerminalResponse getOpenAdd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TerminalResponse> updateTerminal(Long id,
			TerminalResponse terminalResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TerminalResponse> getTerminalByTerminalName(String serviceName) {
		// TODO Auto-generated method stub
		return null;
	}


}
