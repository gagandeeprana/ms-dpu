package com.dpu.service;

import java.util.List;

import com.dpu.entity.Trailer;
import com.dpu.model.TrailerRequest;

public interface TrailerService {

	Trailer add(Trailer trailer);
	
	boolean delete(Trailer trailer);
	
	Trailer update(Trailer trailer);
	
	List<TrailerRequest> getAll();
	
	Trailer get(int id);
}
