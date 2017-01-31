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

import com.dpu.dao.TerminalDao;
import com.dpu.entity.Service;
import com.dpu.entity.Status;
import com.dpu.entity.Terminal;
import com.dpu.model.DPUService;
import com.dpu.model.TerminalResponse;
import com.dpu.model.TypeResponse;
import com.dpu.service.ServiceService;
import com.dpu.service.StatusService;
import com.dpu.service.TerminalService;

@Component
public class TerminalServiceImpl implements TerminalService {

	Logger logger = Logger.getLogger(TerminalServiceImpl.class);
	
	@Autowired
	TerminalDao terminalDao;

	@Autowired
	StatusService statusService;
	
	@Autowired
	ServiceService serviceService;

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
		TerminalResponse tresponse = new TerminalResponse();
		List<DPUService> serviceList = serviceService.getAll();
		tresponse.setServiceList(serviceList);
		return tresponse;
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
	public List<TerminalResponse> getTerminalByTerminalName(String terminalName) {
		List<Terminal> terminalList = null;
		if(terminalName != null && terminalName.length() > 0) {
			Criterion criterion = Restrictions.like("terminalName", terminalName, MatchMode.ANYWHERE);
			terminalList = terminalDao.find(criterion);
		}
		List<TerminalResponse> termList = new ArrayList<TerminalResponse>();
		
		if(terminalList != null && !terminalList.isEmpty()){
			for (Terminal terminal: terminalList) {
				TerminalResponse terminalObj = new TerminalResponse();
				terminalObj.setTerminalId(terminal.getTerminalId());
				terminalObj.setStatusId(terminal.getStatus().getId());
				terminalObj.setTerminalName(terminal.getTerminalName());
				terminalObj.setLocation(terminal.getLocation());
				terminalObj.setFacility(terminal.getFacility());
				terminalObj.setStatus(terminal.getStatus().getStatus());
				termList.add(terminalObj);
			}
		}
		
		return termList;
	}


}
