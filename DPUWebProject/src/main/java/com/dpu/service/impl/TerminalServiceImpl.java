package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.ServiceDao;
import com.dpu.dao.ShipperDao;
import com.dpu.dao.TerminalDao;
import com.dpu.entity.Service;
import com.dpu.entity.Status;
import com.dpu.entity.Terminal;
import com.dpu.model.DPUService;
import com.dpu.model.ShipperResponse;
import com.dpu.model.TerminalResponse;
import com.dpu.model.TypeResponse;
import com.dpu.service.ServiceService;
import com.dpu.service.ShipperService;
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
	
	@Autowired
	ShipperService shipperService;

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ShipperDao shipperDao;
	
	@Autowired
	ServiceDao serviceDao;
	
	@Override
	public List<TerminalResponse> getAllTerminals() {
		
		Session session = null;
		List<TerminalResponse> terminalRespList = new ArrayList<TerminalResponse>();
		try{
			session = sessionFactory.openSession();
			List<Terminal> terminalList = terminalDao.findAll(null, session);
			
			if(terminalList != null && !terminalList.isEmpty()){
				for (Terminal terminal : terminalList) {
					TerminalResponse terminalResponseObj = new TerminalResponse();
					terminalResponseObj.setTerminalName(terminal.getTerminalName());
					terminalResponseObj.setTerminalId(terminal.getTerminalId());
					terminalResponseObj.setShipperName(terminal.getShipper().getLocationName());
					terminalRespList.add(terminalResponseObj);
				}
			}
		} finally{
			session.close();
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
		Set<Service> services = new HashSet<Service>();
		if(terminalResponse.getServiceIds() != null && terminalResponse.getServiceIds().size() > 0) {
			for(int i=0;i<terminalResponse.getServiceIds().size();i++) {
				Long serviceId = terminalResponse.getServiceIds().get(i);
				Service service = serviceDao.findById(serviceId);
				if(service != null) {
					services.add(service);
				}
			}
		}
		terminal.setServices(services);
		terminal.setShipper(shipperDao.findById(terminalResponse.getShipperId()));
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
		
		Session session = null;
		TerminalResponse terminalResp = new TerminalResponse();
		Terminal terminal = null;
		try {
			session = sessionFactory.openSession();
			terminal = terminalDao.findById(session, id);
			if(terminal != null){
				terminalResp.setTerminalId(terminal.getTerminalId());
				terminalResp.setTerminalName(terminal.getTerminalName());
				terminalResp.setShipperId(terminal.getShipper().getShipperId());
				terminalResp.setShipperList(shipperService.getAll());
				Set<Service> services = terminal.getServices();
				terminalResp.setServiceList(serviceService.getAll());
				List<Long> serviceIds = new ArrayList<Long>();
				for(Service service : services) {
					serviceIds.add(service.getServiceId());
				}
				terminalResp.setServiceIds(serviceIds);
			}		
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(session != null) {
				session.close();
			}
		}
		return terminalResp;
	}

	@Override
	public TerminalResponse getOpenAdd() {
		
		TerminalResponse tresponse = new TerminalResponse();
		tresponse.setServiceList(serviceService.getServiceData());
		tresponse.setShipperList(shipperService.getSpecificData());
		return tresponse;
	}

	@Override
	public List<TerminalResponse> updateTerminal(Long id,
			TerminalResponse terminalResponse) {
			Terminal terminal= terminalDao.findById(id);
			terminal.setTerminalName(terminalResponse.getTerminalName());
			Status status = statusService.get(terminalResponse.getStatusId());
//			terminal.setAvailableServices(terminalResponse.getAvailableServices());
			terminal.setModifiedOn(new Date());
			terminal.setStatus(status);
			terminalDao.update(terminal);
			return getAllTerminals();
	}

	@Override
	public List<TerminalResponse> getTerminalByTerminalName(String terminalName) {
		Session session = null;
		List<Terminal> terminalList = null;
		List<TerminalResponse> termList = new ArrayList<TerminalResponse>();
		try {
			session = sessionFactory.openSession();
			if(terminalName != null && terminalName.length() > 0) {
				terminalList = terminalDao.findAll(terminalName, session);
			}
			
			if(terminalList != null && !terminalList.isEmpty()){
				for (Terminal terminal: terminalList) {
					TerminalResponse terminalObj = new TerminalResponse();
					terminalObj.setTerminalId(terminal.getTerminalId());
					terminalObj.setStatusId(terminal.getStatus().getId());
					terminalObj.setTerminalName(terminal.getTerminalName());
					terminalObj.setStatus(terminal.getStatus().getStatus());
					termList.add(terminalObj);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(session != null) {
				session.close();
			}
		}
		
		return termList;
	}


}
