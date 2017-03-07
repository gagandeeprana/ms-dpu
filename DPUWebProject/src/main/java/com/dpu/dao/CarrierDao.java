package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Carrier;
import com.dpu.model.CarrierModel;

public interface CarrierDao extends GenericDao<Carrier> {

	List<CarrierModel> getAllCarrier(Session session);
	Carrier findById(Long carrierId, Session session);
	void deleteCarrier(Carrier carrier, Session session);
}
