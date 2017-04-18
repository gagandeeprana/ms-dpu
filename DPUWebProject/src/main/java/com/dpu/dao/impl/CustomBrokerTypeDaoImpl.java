package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CustomBrokerTypeDao;
import com.dpu.entity.CustomBrokerType;

@Repository
public class CustomBrokerTypeDaoImpl extends GenericDaoImpl<CustomBrokerType>
		implements CustomBrokerTypeDao {

	@Override
	public List<CustomBrokerType> getAll(Session session, Long customerBrokerId) {

		Query query=session.createQuery("from CustomBrokerType where customBroker = " + customerBrokerId);  
		List<CustomBrokerType> customBrokerTypeList = query.list();
		return customBrokerTypeList;
	}

}
