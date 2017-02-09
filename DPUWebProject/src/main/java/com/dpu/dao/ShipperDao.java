package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Shipper;

public interface ShipperDao extends GenericDao<Shipper>{

	List<Shipper> findByCompanyName(String companyName);

	List<Shipper> findAll(Session session);

	Shipper findById(Long id, Session session);

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
