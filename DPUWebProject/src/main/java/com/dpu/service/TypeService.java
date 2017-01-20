package com.dpu.service;

import java.util.List;

import com.dpu.model.TypeResponse;

public interface TypeService {

	List<TypeResponse> getAll(Long typeValue);
	
}
