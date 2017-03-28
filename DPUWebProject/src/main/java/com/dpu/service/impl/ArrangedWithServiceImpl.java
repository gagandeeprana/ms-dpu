package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.ArrangedWithDao;
import com.dpu.entity.ArrangedWith;
import com.dpu.service.ArrangedWithService;

@Component
public class ArrangedWithServiceImpl implements ArrangedWithService {

	@Autowired
	ArrangedWithDao arrangedWithDao;

	@Override
	public List<ArrangedWith> getAllArrangedWith() {

		try {
			List<ArrangedWith> listOfArrangeWith = arrangedWithDao.findAll();
			if (listOfArrangeWith != null) {
				return listOfArrangeWith;
			}
		} catch (Exception e) {
			
		}
		return new ArrayList<ArrangedWith>();
	}

}
