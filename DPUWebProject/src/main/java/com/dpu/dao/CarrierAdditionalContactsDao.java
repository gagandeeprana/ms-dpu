package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CarrierAdditionalContact;

public interface CarrierAdditionalContactsDao extends GenericDao<CarrierAdditionalContact>{

	void deleteAdditionalContact(CarrierAdditionalContact companyAdditionalContacts, Session session);
	List<CarrierAdditionalContact> getAdditionalContactsByCarrierId(Long carrierId,Session session);
	void insertAdditionalContacts(CarrierAdditionalContact comAdditionalContact, Session session);

}
