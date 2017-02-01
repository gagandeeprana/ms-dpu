package com.dpu.dao;

import java.util.List;

import com.dpu.entity.Shipper;

public interface ShipperDao extends GenericDao<Shipper>{

	List<Shipper> findByCompanyName(String companyName);

//	boolean add(Shipper shipper);
//
//	boolean update(int id, Shipper shipper);
//
//	boolean delete(int id);
//
//	List<Shipper> getAll(String name);
//
//	Shipper get(int id);

}
