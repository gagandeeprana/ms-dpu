package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.TrailerDao;
import com.dpu.entity.Trailer;
import com.dpu.model.TrailerRequest;
import com.dpu.service.TrailerService;

@Component
public class TrailerServiceImpl implements TrailerService{
	@Autowired
	TrailerDao trailerdao;
	
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
			}
		}
		 return returnResponse;
	}

	@Override
	public Trailer get(int id) {
		return trailerdao.findById(id);
	}
}
