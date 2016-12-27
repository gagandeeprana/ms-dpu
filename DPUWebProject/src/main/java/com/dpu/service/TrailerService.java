package com.dpu.service;

import java.util.List;

import com.dpu.entity.Trailer;

public interface TrailerService {

	Trailer add(Trailer trailer);
	
	boolean delete(Trailer trailer);
	
	Trailer update(Trailer trailer);
	
	List<Trailer> getAll();
	
	Trailer get(int id);
}
