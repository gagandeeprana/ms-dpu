package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CarrierAdditionalContactsDao;
import com.dpu.entity.CarrierAdditionalContact;

@Repository
public class CarrierAdditionalContactsDaoImpl extends GenericDaoImpl<CarrierAdditionalContact>
		implements CarrierAdditionalContactsDao {

	@Override
	public void deleteAdditionalContact(CarrierAdditionalContact carrierAdditionalContact, Session session) {
		
		session.delete(carrierAdditionalContact);

	}

	@Override
	public List<CarrierAdditionalContact> getAdditionalContactsByCarrierId(Long carrierId, Session session) {
		
		StringBuilder sb = new StringBuilder(" select cac from CarrierAdditionalContact cac  where cac.carrier.carrierId =:carrierId " );
		Query query = session.createQuery(sb.toString());
		query.setParameter("carrierId", carrierId);
		return query.list();
		
	}

	@Override
	public void insertAdditionalContacts(CarrierAdditionalContact comAdditionalContact, Session session) {
		
		session.save(comAdditionalContact);
		
	}

}
