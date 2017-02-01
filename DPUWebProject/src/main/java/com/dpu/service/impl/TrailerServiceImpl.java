package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.TrailerDao;
import com.dpu.entity.Status;
import com.dpu.entity.Trailer;
import com.dpu.model.CategoryReq;
import com.dpu.model.DivisionReq;
import com.dpu.model.DriverReq;
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
	
	@Override
	public Trailer add(Trailer trailer) {
//		trailer.setReadingTakenDate(new Date());
//		trailer.setCreatedOn(new Date());
		return trailerdao.save(trailer);
	}

	@Override
	public Trailer update(Trailer trailer) {
		return trailerdao.update(trailer);
	}

	@Override
	public boolean delete(Trailer trailer) {
		boolean result = false;
		try {
			trailerdao.delete(trailer);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<TrailerRequest> getAll() {
		
		List<Trailer> trailerList = trailerdao.findAll();
		List<TrailerRequest> returnResponse = new ArrayList<TrailerRequest>();
		
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
		 return returnResponse;
	}

	@Override
	public Trailer get(int id) {
		return trailerdao.findById(id);
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
}
