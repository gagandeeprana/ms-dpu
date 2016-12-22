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
	public Shipper add(Shipper shipper) {
		return shipperDao.save(shipper);
	}

	@Override
	public Shipper update(Shipper shipper) {
		return shipperDao.update(shipper);
	}

	@Override
	public boolean delete(Shipper shipper) {
		boolean result = false;
		try {
			shipperDao.delete(shipper);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<Shipper> getAll() {
		return shipperDao.findAll();
	}

	@Override
	public Shipper get(int id) {
		return shipperDao.findById(id);
	}

}
