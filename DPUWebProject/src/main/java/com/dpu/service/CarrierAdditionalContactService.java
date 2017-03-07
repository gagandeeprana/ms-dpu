package com.dpu.service;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CarrierAdditionalContact;

public interface CarrierAdditionalContactService {

	public List<CarrierAdditionalContact> getAll(Long carrierId, Session session);

}
