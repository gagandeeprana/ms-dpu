/**
 * 
 */
package com.dpu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dpu.dao.DivisionDao;
import com.dpu.dao.ShipperDao;
import com.dpu.entity.Division;

/**
 * @author jagvir
 *
 */
public class DivisionServiceImpl implements DivisionDao {

	@Autowired
	DivisionDao divisionDao;

	@Override
	public boolean add(Division division) {
		// TODO Auto-generated method stub
		return divisionDao.add(division);
	}

	@Override
	public boolean update(int id, Division division) {
		// TODO Auto-generated method stub
		return divisionDao.update(id, division);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return divisionDao.delete(id);
	}

	@Override
	public List<Division> getAll(String name) {
		// TODO Auto-generated method stub
		return divisionDao.getAll(name);
	}

	@Override
	public Division get(int id) {
		// TODO Auto-generated method stub
		return divisionDao.get(id);
	}

}
