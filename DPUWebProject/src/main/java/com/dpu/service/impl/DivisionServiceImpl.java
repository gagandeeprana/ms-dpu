/**
 * 
 */
package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.DivisionDao;
import com.dpu.entity.Division;
import com.dpu.entity.Equipment;
import com.dpu.entity.Status;
import com.dpu.model.DivisionReq;
import com.dpu.model.EquipmentReq;
import com.dpu.model.TypeResponse;
import com.dpu.service.DivisionService;
import com.dpu.service.StatusService;

/**
 * @author jagvir
 *
 */
@Component
public class DivisionServiceImpl implements DivisionService {

	Logger logger = Logger.getLogger(DivisionServiceImpl.class);

	@Autowired
	DivisionDao divisionDao;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	StatusService statusService;

	@Override
	public List<DivisionReq> update(Long id, DivisionReq divisionReq) {
		logger.info("[DivisionServiceImpl] [update] : Srvice: Enter");
		Division division = divisionDao.findById(id);
		if (division != null) {
			logger.info("[DivisionServiceImpl] [update] : 1111111111111111111");
			division.setDivisionCode(divisionReq.getDivisionCode());
			division.setDivisionName(divisionReq.getDivisionName());
			division.setFedral(divisionReq.getFedral());
			division.setProvincial(divisionReq.getProvincial());
			division.setSCAC(divisionReq.getSCAC());
			division.setCarrierCode(divisionReq.getCarrierCode());
			division.setContractPrefix(divisionReq.getContractPrefix());
			division.setInvoicePrefix(divisionReq.getInvoicePrefix());
			logger.info("[DivisionServiceImpl] [update] : 22222222222222");
			Status status = statusService.get(divisionReq.getStatusId());
			division.setStatus(status);
			logger.info("[DivisionServiceImpl] [update] : 333333333");
			division.setModifiedBy("jagvir");
			division.setModifiedOn(new Date());
			logger.info("[DivisionServiceImpl] [update] : 4444444444");
			divisionDao.update(division);
			logger.info("[DivisionServiceImpl] [update]: Division updated Successfully.");
			return getAll("");
		} else {
			logger.info("[DivisionServiceImpl] [update] : 55555555");
			return null;
		}
	}

	@Override
	public List<DivisionReq> delete(Long id) {
		Division division = divisionDao.findById(id);
		if (division != null) {
			try {
				divisionDao.delete(division);
				return getAll("");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return null;
	}

	@Override
	public DivisionReq get(Long id) {
		Division division = divisionDao.findById(id);
		DivisionReq response = null;
		if(division != null) {
			response = new DivisionReq();
			response.setDivisionCode(division.getDivisionCode());
			response.setDivisionName(division.getDivisionName());
			response.setFedral(division.getFedral());
			response.setProvincial(division.getProvincial());
			response.setSCAC(division.getSCAC());
			response.setCarrierCode(division.getCarrierCode());
			response.setContractPrefix(division.getContractPrefix());
			response.setInvoicePrefix(division.getInvoicePrefix());
			
			List<Status> statusList = statusService.getAll();
			
			if(statusList != null && !statusList.isEmpty()){
				response.setStatusList(statusList);
			}
			
		}
		
		return response;

	}

	@Override
	public List<DivisionReq> getAll(String divisionName) {
		logger.info("[DivisionServiceImpl] [getAllDivisions] : Enter ");
		List<Division> lstDivision = null;
		List<DivisionReq> divisionResponse = new ArrayList<DivisionReq>();
		if (divisionName != null && divisionName.length() > 0) {
			Criterion criterion = Restrictions.like("divisionName",
					divisionName, MatchMode.ANYWHERE);
			lstDivision = divisionDao.find(criterion);
		} else {
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			lstDivision = divisionDao.findAll();
		}
		if (lstDivision != null && lstDivision.size() > 0) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			for (Division division : lstDivision) {
				DivisionReq divisionReq = new DivisionReq();
				divisionReq.setDivisionCode(division.getDivisionCode());
				divisionReq.setProvincial(division.getProvincial());
				divisionReq.setFedral(division.getFedral());
				divisionReq.setDivisionName(division.getDivisionName());
				divisionReq.setStatus(division.getStatus().getStatus());
				divisionReq.setDivisionId(division.getDivisionId());
				divisionResponse.add(divisionReq);
			}
		}
		return divisionResponse;
	}

	@Override
	public List<DivisionReq> add(DivisionReq divisionReq) {
		logger.info("DivisionServiceImpl: add():  STARTS");

		Session session = null;
		Transaction tx = null;
		List<DivisionReq> divisionList = null;

		try {

			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Division division = divisionDao.add(session, divisionReq);
			divisionList = getAll("");
		} catch (Exception e) {
			logger.fatal("DivisionServiceImpl: add(): Exception: "
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			logger.info("DivisionServiceImpl: add():  finally block");
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		logger.info("DivisionServiceImpl: add():  ENDS");

		return divisionList;

	}

	// @Override
	// public boolean add(Division division) {
	// logger.info("[addCategory]:Service:  Enter");
	//
	// boolean returnValue = false;
	// try {
	//
	// // truck.setCreated("sumit");
	// // truck.setCreatedOn(new Date());
	// //
	// // truck.setModifiedBy("sumit");
	// // truck.setModifiedOn(new Date());
	//
	// Division divisionn = divisionDao.save(division);
	// division.setCreatedOn(new Date());
	// System.out.println("[addCategory]category Id :"
	// + divisionn.getDivisionId());
	// returnValue = true;
	// return returnValue;
	//
	// } catch (Exception e) {
	// logger.info("[addCategory]:Exception:    : ", e);
	// System.out.println(e);
	// return returnValue;
	// } finally {
	// logger.info("[addCategory]:Service:  returnValue : " + returnValue);
	// }
	// }
	//
	// @Override
	// public Division update(int id, Division division) {
	// division.setCreatedOn(new Date());
	// return divisionDao.update(division);
	// }
	//
	// @Override
	// public boolean delete(Division division) {
	// boolean result = false;
	// try {
	// divisionDao.delete(division);
	// result = true;
	// } catch (Exception e) {
	// result = false;
	// }
	// return result;
	// }
	//
	// @Override
	// public List<Division> getAll(String divisionName) {
	//
	// return divisionDao.findAll();
	// }
	//
	// @Override
	// public Division get(int id) {
	// return divisionDao.findById(id);
	// }

}
