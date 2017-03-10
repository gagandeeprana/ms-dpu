package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CategoryDao;
import com.dpu.dao.DivisionDao;
import com.dpu.dao.TerminalDao;
import com.dpu.dao.TrailerDao;
import com.dpu.entity.Status;
import com.dpu.entity.Trailer;
import com.dpu.model.CategoryReq;
import com.dpu.model.DivisionReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TerminalResponse;
import com.dpu.model.TrailerRequest;
import com.dpu.model.TypeResponse;
import com.dpu.service.CategoryService;
import com.dpu.service.DivisionService;
import com.dpu.service.StatusService;
import com.dpu.service.TerminalService;
import com.dpu.service.TrailerService;
import com.dpu.service.TypeService;

@Component
public class TrailerServiceImpl implements TrailerService{
	
	Logger logger = Logger.getLogger(TrailerServiceImpl.class);
	
	@Autowired
	TrailerDao trailerdao;
	
	@Autowired
	StatusService statusService;
	
	@Autowired
	TypeService typeService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	DivisionService divisionService;
	
	@Autowired
	TerminalService terminalService;
	
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired
	DivisionDao divisionDao;
	
	@Autowired
	TerminalDao terminalDao;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Object add(TrailerRequest trailerRequest) {
		
		logger.info("Inside TrailerServiceImpl add() starts");
		Object obj = null;
		String message = "Trailer added successfully";
		
		try{
			Trailer trailer = new Trailer();
			BeanUtils.copyProperties(trailerRequest, trailer);
			trailer.setCategory(categoryDao.findById(trailerRequest.getCategoryId()));
			trailer.setDivision(divisionDao.findById(trailerRequest.getDivisionId()));
			trailer.setTerminal(terminalDao.findById(trailerRequest.getTerminalId()));
			trailer.setType(typeService.get(trailerRequest.getTrailerTypeId()));
			trailer.setStatus(statusService.get(trailerRequest.getStatusId()));
			trailerdao.save(trailer);
			obj = createSuccessObject(message);
		} catch (Exception e) {
			logger.error("Exception inside TrailerServiceImpl add() :"+e.getMessage());
			message = "Error while inserting trailer";
			obj = createFailedObject(message);
		} 

		return obj;
	}

	private Object createSuccessObject(String message) {
		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAll());
		return success;
	}
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}
	
	@Override
	public Object update(Long trailerId, TrailerRequest trailerRequest) {
		
		logger.info("Inside TrailerServiceImpl update() Starts, trailerId :"+ trailerId);
		Object obj = null;
		String message = "Trailer updated successfully";
		try{
			Trailer trailer = trailerdao.findById(trailerId);
			
			if(trailer != null){
				String[] ignoreProp = new String[1];
				ignoreProp[0] = "trailerId";
				BeanUtils.copyProperties(trailerRequest, trailer, ignoreProp);
				trailer.setCategory(categoryDao.findById(trailerRequest.getCategoryId()));
				trailer.setDivision(divisionDao.findById(trailerRequest.getDivisionId()));
				trailer.setTerminal(terminalDao.findById(trailerRequest.getTerminalId()));
				trailer.setType(typeService.get(trailerRequest.getTrailerTypeId()));
				trailer.setStatus(statusService.get(trailerRequest.getStatusId()));
				trailerdao.update(trailer);
				obj = createSuccessObject(message);
			} else{
				message = "Error while updating trailer";
				obj = createFailedObject(message);
			}
			 
		} catch (Exception e) {
			logger.error("Exception inside TrailerServiceImpl update() :"+ e.getMessage());
			message = "Error while updating record";
			obj = createFailedObject(message);
		}
		
		logger.info("Inside TrailerServiceImpl update() Ends, trailerId :"+ trailerId);
		return obj;
	}

	@Override
	public Object delete(Long trailerId) {
		
		logger.info("Inside TrailerServiceImpl delete() starts, trailerId :"+ trailerId);
		Object obj = null;
		String message = "Trailer deleted successfully";
		try {
			Trailer trailer = trailerdao.findById(trailerId);
			if(trailer != null){
				trailerdao.delete(trailer);
				obj = createSuccessObject(message);
			} else{
				message = "Error while deleting trailer";
				obj = createFailedObject(message);
			}
			
		}catch (Exception e) {
			logger.error("Exceptiom inside TrailerServiceImpl delete() :"+ e.getMessage());
			message = "Error while Deleting Record";
			obj = createFailedObject(message);
		}
		
		logger.info("Inside TrailerServiceImpl delete() ends, trailerId :"+ trailerId);
		return obj;
	}

	@Override
	public List<TrailerRequest> getAll() {
		
		logger.info("Inside TrailerServiceImpl getAll() starts "); 
		Session session = null;
		List<TrailerRequest> returnResponse = new ArrayList<TrailerRequest>();
		
		try{
			session = sessionFactory.openSession();
			List<Trailer> trailerList = trailerdao.findAll(session);
			if(trailerList !=  null && !trailerList.isEmpty()){
				
				for (Trailer trailer : trailerList) {
					TrailerRequest response = new TrailerRequest();
					BeanUtils.copyProperties(trailer, response);
					response.setCategory(trailer.getCategory().getName());
					response.setDivision(trailer.getDivision().getDivisionName());
					response.setTerminal(trailer.getTerminal().getTerminalName());
					response.setStatus(trailer.getStatus().getStatus());
					response.setTrailerType(trailer.getType().getTypeName());
					
					returnResponse.add(response);
				}
			}
		} catch (Exception e) {
			logger.error("Exception inside TrailerServiceImpl getAll():"+ e.getMessage());
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		logger.info("Inside TrailerServiceImpl getAll() ends "); 
		return returnResponse;
	}

	@Override
	public TrailerRequest get(Long trailerId) {
		
		logger.info("Inside TrailerServiceImpl get() starts "); 
		Session session = null;
		TrailerRequest response = new TrailerRequest();
		
		try{
			session = sessionFactory.openSession();
			Trailer trailer = trailerdao.findById(trailerId, session);
			if(trailer != null){
				BeanUtils.copyProperties(trailer, response);
				response.setCategoryId(trailer.getCategory().getCategoryId());
				response.setDivisionId(trailer.getDivision().getDivisionId());
				response.setTerminalId(trailer.getTerminal().getTerminalId());
				response.setStatusId(trailer.getStatus().getId());
				response.setTrailerTypeId(trailer.getType().getTypeId());
				
				List<Status> statusList = statusService.getAll();
				response.setStatusList(statusList);
				
				List<TypeResponse> trailerTypeList = typeService.getAll(7l);
				response.setTrailerTypeList(trailerTypeList);
				
				List<CategoryReq> categoryList = categoryService.getAll();
				response.setCategoryList(categoryList);
				
				List<DivisionReq> divisionList = divisionService.getAll("");
				response.setDivisionList(divisionList);
				
				List<TerminalResponse> terminalList = terminalService.getAllTerminals();
				response.setTerminalList(terminalList);
			}
			
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		logger.info("Inside TrailerServiceImpl get() ends "); 
		return response;
	}

	@Override
	public TrailerRequest getOpenAdd() {

		TrailerRequest trailer = new TrailerRequest();
		
		List<Status> statusList = statusService.getAll();
		trailer.setStatusList(statusList);
		
		List<TypeResponse> trailerTypeList = typeService.getAll(7l);
		trailer.setTrailerTypeList(trailerTypeList);
				
		List<CategoryReq> categoryList = categoryService.getAll();
		trailer.setCategoryList(categoryList);
		
		List<DivisionReq> divisionList = divisionService.getAll("");
		trailer.setDivisionList(divisionList);
		
		List<TerminalResponse> terminalList = terminalService.getAllTerminals();
		trailer.setTerminalList(terminalList);
		
		
		return trailer;
	}

	@Override
	public List<TrailerRequest> getSpecificData() {
		
		List<Object[]> trailerIdAndNameList = trailerdao.getSpecificData("Trailer", "trailerId", "owner");
		List<TrailerRequest> trailerData = new ArrayList<TrailerRequest>();
		
		if(trailerIdAndNameList != null && !trailerIdAndNameList.isEmpty()){
			for (Object[] row : trailerIdAndNameList) {
				TrailerRequest trailerRequest =  new TrailerRequest();
				trailerRequest.setTrailerId(Long.parseLong(String.valueOf(row[0])));
				trailerRequest.setOwner(String.valueOf(row[1]));
				trailerData.add(trailerRequest);
			}
			
		}
		
		return trailerData;
	}
}
