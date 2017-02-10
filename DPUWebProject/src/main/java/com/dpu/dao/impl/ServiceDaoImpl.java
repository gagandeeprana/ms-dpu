package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dpu.dao.ServiceDao;
import com.dpu.entity.Service;

@Repository
public class ServiceDaoImpl extends GenericDaoImpl<Service> implements ServiceDao {
	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getServiceData() {
		Session session = null;
		List<Object[]> returnList = null;
		try {
			session = sessionFactory.openSession();
			
			Query query = session.createSQLQuery(" select service_id, service_name from service ");
			returnList = query.list();

		} catch (Exception e) {
			logger.error("[find ]" + e.getCause());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.info("[ShipperDaoImpl] [find] : Exit ");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Service> findAll(Session session) {

		StringBuilder sb = new StringBuilder("select s from Service s join fetch s.status join fetch s.associationWith join fetch s.textField ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public Service findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder("select s from Service s join fetch s.status join fetch s.associationWith join fetch s.textField where s.serviceId =:serviceId");
		Query query = session.createQuery(sb.toString());
		query.setParameter("serviceId", id);
		return (Service) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Service> getServiceByServiceName(Session session, String serviceName) {
		StringBuilder sb = new StringBuilder("select s from Service s join fetch s.status join fetch s.associationWith join fetch s.textField where s.serviceName like :serviceName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("serviceName", "%"+serviceName+"%");
		return query.list();
	}

}
