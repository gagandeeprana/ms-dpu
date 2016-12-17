/**
 * 
 */
package com.dpu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.ShipperDao;
import com.dpu.entity.Shipper;
import com.dpu.service.ShipperService;

/**
 * @author jagvir
 *
 */
@Component
public class ShipperServiceImpl implements ShipperService {

	@Autowired
	ShipperDao shipperDao;

	@Override
	public boolean add(Shipper shipper) {
		return shipperDao.add(shipper);
	}

	@Override
	public boolean update(int id, Shipper shipper) {
		return shipperDao.update(id, shipper);
	}

	@Override
	public boolean delete(int id) {
		return shipperDao.delete(id);
	}

	@Override
	public List<Shipper> getAll(String name) {
		return shipperDao.getAll(name);
	}

	@Override
	public Shipper get(int id) {
		return shipperDao.get(id);
	}

}
