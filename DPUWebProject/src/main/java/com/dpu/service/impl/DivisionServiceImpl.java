/**
 * 
 */
package com.dpu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.DivisionDao;
import com.dpu.entity.Division;
import com.dpu.service.DivisionService;

/**
 * @author jagvir
 *
 */
@Component
public class DivisionServiceImpl implements DivisionService {

	@Autowired
	DivisionDao divisionDao;

	@Override
	public Division add(Division division) {
		return divisionDao.save(division);
	}

	@Override
	public Division update(int id, Division division) {
		return divisionDao.update(division);
	}

	@Override
	public boolean delete(Division division) {
		boolean result = false;
		try {
			divisionDao.delete(division);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<Division> getAll() {
		return divisionDao.findAll();
	}

	@Override
	public Division get(int id) {
		return divisionDao.findById(id);
	}

}
