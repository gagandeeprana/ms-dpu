package com.dpu.service;

import java.util.List;

import com.dpu.entity.Trailer;
import com.dpu.model.TrailerRequest;

public interface TrailerService {

	Object add(TrailerRequest trailerRequest);
	
	boolean delete(Trailer trailer);
	
	Trailer update(Trailer trailer);
	
	List<TrailerRequest> getAll();
	
	Trailer get(int id);

	TrailerRequest getOpenAdd();
}
