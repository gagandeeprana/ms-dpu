package com.dpu.dao.impl;

import java.util.Date;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.TruckDao;
import com.dpu.entity.Division;
import com.dpu.entity.Status;
import com.dpu.entity.Truck;
import com.dpu.model.DivisionReq;
import com.dpu.model.TruckResponse;
import com.dpu.service.StatusService;

/**
 * @author sumit
 *
 */

@Repository
@Transactional
public class TruckDaoImpl extends GenericDaoImpl<Truck> implements TruckDao {

	@Autowired
	StatusService statusService;

	@Override
	public Truck add(Session session, TruckResponse truckResponse) {
		logger.info("TruckDaoImpl: add(): STARTS");
		Truck truck = null;
		try {

			truck = setDivisionValues(truckResponse);
			// Status status = (Status) session.get(Status.class,
			// divisionReq.getStatusId());
			// division.setStatus(status);
			//
			// Long divisionId = (Long) session.save(division);
			// division.setDivisionId(divisionId);
		} catch (Exception e) {
			logger.fatal("DivisionDaoImpl: add(): Exception: " + e.getMessage());
		}

		logger.info("DivisionDaoImpl: add(): ENDS");

		return truck;

	}

	private Truck setDivisionValues(TruckResponse truckResponse) {

		logger.info("TruckDaoImpl: setDivisionValues(): STARTS");

		Truck truck = new Truck();
		// division.setDivisionCode(divisionReq.getDivisionCode());
		// division.setDivisionName(divisionReq.getDivisionName());
		// division.setFedral(divisionReq.getFedral());
		// division.setProvincial(divisionReq.getProvincial());
		// division.setSCAC(divisionReq.getSCAC());
		// division.setCarrierCode(divisionReq.getCarrierCode());
		// division.setContractPrefix(divisionReq.getContractPrefix());
		// division.setInvoicePrefix(divisionReq.getInvoicePrefix());
		// // Status status = statusService.get(divisionReq.getStatusId());
		// // division.setStatus(status);
		// division.setCreatedBy("jagvir");
		// division.setCreatedOn(new Date());
		// division.setModifiedBy("jagvir");
		// division.setModifiedOn(new Date());
		//
		// logger.info("DivisionDaoImpl: setDivisionValues(): ENDS");
		//
		return truck;
	}

}
