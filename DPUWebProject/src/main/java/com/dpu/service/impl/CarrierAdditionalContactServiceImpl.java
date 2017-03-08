package com.dpu.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CarrierAdditionalContactsDao;
import com.dpu.entity.CarrierAdditionalContact;
import com.dpu.service.CarrierAdditionalContactService;

@Component
public class CarrierAdditionalContactServiceImpl implements CarrierAdditionalContactService {
	
	@Autowired
	CarrierAdditionalContactsDao carrierAdditionalContactsDao;

	@Override
	public List<CarrierAdditionalContact> getAll(Long carrierId, Session session) {
		List<CarrierAdditionalContact> additionalContacts = carrierAdditionalContactsDao.getAdditionalContactsByCarrierId(carrierId,session);
		return additionalContacts;
	}

}