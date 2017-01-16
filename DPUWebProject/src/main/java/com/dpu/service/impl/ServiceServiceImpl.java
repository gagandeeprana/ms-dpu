/**
 * 
 */
package com.dpu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.ServiceDao;
import com.dpu.entity.Service;
import com.dpu.service.ServiceService;

/**
 * @author jagvir
 *
 */
@Component
public class ServiceServiceImpl implements ServiceService {
	@Autowired
	ServiceDao serviceDao;

	@Override
	public Service add(Service service) {
		return serviceDao.save(service);
	}

	@Override
	public Service update(int id, Service service) {
		return serviceDao.update(service);
	}

	@Override
	public boolean delete(Service service) {
		boolean result = false;
		try {
			serviceDao.delete(service);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<Service> getAll() {
		return serviceDao.findAll();
	}

	@Override
	public Service get(int id) {
		return serviceDao.findById(id);
	}

}