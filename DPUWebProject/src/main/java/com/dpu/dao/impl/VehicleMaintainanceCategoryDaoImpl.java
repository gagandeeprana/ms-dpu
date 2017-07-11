package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.VehicleMaintainanceCategoryDao;
import com.dpu.entity.VehicleMaintainanceCategory;

@Repository
public class VehicleMaintainanceCategoryDaoImpl extends GenericDaoImpl<VehicleMaintainanceCategory> implements VehicleMaintainanceCategoryDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleMaintainanceCategory> findAll(Session session) {
		
		StringBuilder sb = new StringBuilder(" from VehicleMaintainanceCategory ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public VehicleMaintainanceCategory findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder(" from VehicleMaintainanceCategory where id =:vmcId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("vmcId", id);
		return (VehicleMaintainanceCategory) query.uniqueResult();
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<Handling> getHandlingByHandlingName(Session session, String handlingName) {
		StringBuilder sb = new StringBuilder(" select h from Handling h join fetch h.status where h.name like :handlingName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("handlingName", "%"+handlingName+"%");
		return query.list();
	}*/

}
