package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.dao.HandlingDao;
import com.dpu.dao.TaxCodeDao;
import com.dpu.entity.Handling;
import com.dpu.entity.Status;
import com.dpu.entity.TaxCode;
import com.dpu.model.Failed;
import com.dpu.model.HandlingModel;
import com.dpu.model.Success;
import com.dpu.model.TaxCodeModel;
import com.dpu.service.HandlingService;
import com.dpu.service.StatusService;
import com.dpu.service.TaxCodeService;

@Component
public class TaxCodeServiceImpl implements TaxCodeService {

	Logger logger = Logger.getLogger(TaxCodeServiceImpl.class);

	@Autowired
	TaxCodeDao taxCodeDao;

	@Autowired
	StatusService statusService;

	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${handling_added_message}")
	private String handling_added_message;
	
	@Value("${handling_unable_to_add_message}")
	private String handling_unable_to_add_message;
	
	@Value("${handling_deleted_message}")
	private String handling_deleted_message;
	
	@Value("${handling_unable_to_delete_message}")
	private String handling_unable_to_delete_message;
	
	@Value("${handling_updated_message}")
	private String handling_updated_message;
	
	@Value("${handling_unable_to_update_message}")
	private String handling_unable_to_update_message;
	
	@Value("${handling_already_used_message}")
	private String handling_already_used_message;
	
	@Override
	public List<TaxCodeModel> getAll() {
		
		logger.info("HandlingServiceImpl getAll() starts ");
		Session session = null;
		List<TaxCodeModel> taxCodeList = new ArrayList<TaxCodeModel>();

		try {
			session = sessionFactory.openSession();
			List<TaxCode> taxCodes = taxCodeDao.findAll(session);

			if (taxCodes != null && !taxCodes.isEmpty()) {
				for (TaxCode taxCode : taxCodes) {
					TaxCodeModel taxCodeModel = new TaxCodeModel();
					BeanUtils.copyProperties(taxCode, taxCodeModel);
					taxCodeList.add(taxCodeModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	
		logger.info("HandlingServiceImpl getAll() ends ");
		return taxCodeList;
	}

	private Object createSuccessObject(String msg) {
		Success success = new Success();
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg) {
		Failed failed = new Failed();
		failed.setMessage(msg);
		//failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object addTaxCode(TaxCodeModel taxCodeModel) {

		logger.info("HandlingServiceImpl addHandling() starts ");
		TaxCode taxCode = null;
		Session session = null;
		Transaction tx = null;
		try {
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			taxCode = setTaxCodeValues(taxCodeModel);
			session.save(taxCode);

		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside HandlingServiceImpl addHandling() :"+ e.getMessage());
			return createFailedObject(handling_unable_to_add_message);

		} finally{
			if(tx != null){
				tx.commit();
			} 
			if(session !=null){
				session.close();
			}
		}
		
		logger.info("HandlingServiceImpl addHandling() ends ");
		return createSuccessObject(handling_added_message);
	}

	private TaxCode setTaxCodeValues(TaxCodeModel taxCodeModel) {
		
		TaxCode taxCode = new TaxCode();
		BeanUtils.copyProperties(taxCodeModel, taxCode);
		return taxCode;
	}

	@Override
	public Object update(Long id, TaxCodeModel taxCodeModel) {

		logger.info("HandlingServiceImpl update() starts.");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			TaxCode taxCode = (TaxCode) session.get(TaxCode.class, id);
			
			if (taxCode != null) {
				String[] ignorePro ={"taxCodeId"};
				BeanUtils.copyProperties(taxCodeModel, taxCode, ignorePro);
				session.update(taxCode);
				tx.commit();
			} else{
				return createFailedObject(handling_unable_to_update_message);
			}

		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside HandlingServiceImpl update() :"+ e.getMessage());
			return createFailedObject(handling_unable_to_update_message);
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		logger.info("HandlingServiceImpl update() ends.");
		return createSuccessObject(handling_updated_message);
	}

	@Override
	public Object delete(Long id) {
		
		logger.info("HandlingServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			TaxCode taxCode = (TaxCode) session.get(TaxCode.class, id);
			if(taxCode != null){
				session.delete(taxCode);
				tx.commit();
			} else{
				return createFailedObject(handling_unable_to_delete_message);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside HandlingServiceImpl delete() : " + e.getMessage());
			if(tx != null){
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				return createFailedObject(handling_already_used_message);
			}
			return createFailedObject(handling_unable_to_delete_message);
		} finally{
			/*if(tx != null){
				tx.commit();
			}*/
			if(session != null){
				session.close();
			}
		}
		
		logger.info("HandlingServiceImpl delete() ends.");
		return createSuccessObject(handling_deleted_message);
	}



	@Override
	public TaxCodeModel get(Long id) {
		
		logger.info("HandlingServiceImpl get() starts.");
		Session session = null;
		TaxCodeModel taxCodeModel = new TaxCodeModel();

		try {

			session = sessionFactory.openSession();
			TaxCode taxCode = taxCodeDao.findById(id, session);

			if (taxCode != null) {
				BeanUtils.copyProperties(taxCode, taxCodeModel);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("HandlingServiceImpl get() ends.");
		return taxCodeModel;
	}

	@Override
	public HandlingModel getOpenAdd() {
		logger.info("HandlingServiceImpl getOpenAdd() starts ");
		HandlingModel handlingModel = new HandlingModel();

		List<Status> statusList = statusService.getAll();
		handlingModel.setStatusList(statusList);
		
		logger.info("HandlingServiceImpl getOpenAdd() ends ");
		return handlingModel;
	}

	@Override
	public List<TaxCodeModel> getTaxCodeByTaxCodeName(String taxCodeName) {
		
		logger.info("HandlingServiceImpl getHandlingByHandlingName() starts ");
		Session session = null;
		List<TaxCodeModel> taxCodeList = new ArrayList<TaxCodeModel>();

		try {
			session = sessionFactory.openSession();
			List<TaxCode> taxCodes = taxCodeDao.getTaxCodesByTaxCodeNames(session, taxCodeName);
			if (taxCodes != null && !taxCodes.isEmpty()) {
				for (TaxCode taxCode : taxCodes) {
					TaxCodeModel taxCodeModel = new TaxCodeModel();
					BeanUtils.copyProperties(taxCode, taxCodeModel);
					taxCodeList.add(taxCodeModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("HandlingServiceImpl getHandlingByHandlingName() ends ");
		return taxCodeList;
	}

	@Override
	public List<HandlingModel> getSpecificData() {
		List<Object[]> handlingData = null;//handlingDao.getSpecificData("Handling","id", "name");

		List<HandlingModel> handlings = new ArrayList<HandlingModel>();
		if (handlingData != null && !handlingData.isEmpty()) {
			for (Object[] row : handlingData) {
				HandlingModel handlingObj = new HandlingModel();
				handlingObj.setId((Long) row[0]);
				handlingObj.setName(String.valueOf(row[1]));
				handlings.add(handlingObj);
			}
		}

		return handlings;
	}

}
