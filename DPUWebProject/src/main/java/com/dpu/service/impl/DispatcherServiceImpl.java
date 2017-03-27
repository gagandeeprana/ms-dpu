package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.DispatcherDao;
import com.dpu.entity.Dispatcher;
import com.dpu.service.DispatcherService;

@Component
public class DispatcherServiceImpl implements DispatcherService {

	@Autowired
	DispatcherDao dispatcherDao;

	@Override
	public List<Dispatcher> getAllDispatcher() {

		List<Dispatcher> listOfDispatcher = new ArrayList<Dispatcher>();
		listOfDispatcher = dispatcherDao.findAll();
		if (listOfDispatcher != null) {
			return listOfDispatcher;
		}
		return new ArrayList<Dispatcher>();
	}

}
