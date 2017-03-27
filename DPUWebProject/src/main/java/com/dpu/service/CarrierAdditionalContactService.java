package com.dpu.service;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CarrierAdditionalContacts;

public interface CarrierAdditionalContactService {

	public List<CarrierAdditionalContacts> getAll(Long carrierId, Session session);

}
