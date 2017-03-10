package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CarrierDao;
import com.dpu.entity.Carrier;
import com.dpu.model.CarrierModel;

@Repository
public class CarrierDaoImpl extends GenericDaoImpl<Carrier> implements CarrierDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CarrierModel> getAllCarrier(Session session) {

		StringBuilder sb = new StringBuilder("from Carrier");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}
	
	@Override
	public Carrier findById(Long carrierId, Session session) {
		
		StringBuilder sb = new StringBuilder(" select c from Carrier c where c.carrierId =:carrierId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("carrierId", carrierId);
		return (Carrier) query.uniqueResult();
		
	}

	@Override
	public void deleteCarrier(Carrier carrier, Session session) {
		
		session.delete(carrier);
		
	}

	@Override
	public void updateData(Carrier carrier, CarrierModel carrierResponse, Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Carrier insertCarrierData(Carrier carrier, Session session) {
		
		session.save(carrier);
		return carrier;
		
	}

}
