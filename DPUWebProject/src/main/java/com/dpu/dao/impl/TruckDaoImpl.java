package com.dpu.dao.impl;

import java.util.Date;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.TruckDao;
import com.dpu.entity.Category;
import com.dpu.entity.Division;
import com.dpu.entity.Status;
import com.dpu.entity.Terminal;
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

			truck = setTruckValues(truckResponse);
//			System.out.println("[1] " + truck.getFinance() + " "
//					+ truck.getoOName() + " " + truck.getOwner() + " "
//					+ truck.getTruckType() + " " + truck.getUsage() + " "
//					+ truck.getTruckId() + " " + truck.getUnitNo() + " catId "
//					+ truck.getCategory().getCategoryId() + " divId  " + truck.getDivision().getDivisionId() + " stId "
//					+ truck.getStatus().getId() + " termId  " + truck.getTerminal().getTerminalId());
			Status status = (Status) session.get(Status.class,
					truckResponse.getStatusId());
			truck.setStatus(status);
			System.out.println("[2] ");

			Division division = (Division) session.get(Division.class,
					truckResponse.getDivisionId());
			truck.setDivision(division);
			System.out.println("[3] ");
			Category category = (Category) session.get(Category.class,
					truckResponse.getCategoryId());
			truck.setCategory(category);
			System.out.println("[4] ");
			Terminal terminal = (Terminal) session.get(Terminal.class,
					truckResponse.getTerminalId());
			truck.setTerminal(terminal);
			System.out.println("[5] ");
			Long truckId = (Long) session.save(truck);
			System.out.println("new tId  " + truckId);
			truck.setTruckId(truckId);
//			System.out.println(truck.getFinance() + " " + truck.getoOName()
//					+ " " + truck.getOwner() + " " + truck.getTruckType() + " "
//					+ truck.getUsage() + " " + truck.getTruckId() + " "
//					+ truck.getUnitNo() + " " + truck.getCategory() + " "
//					+ truck.getDivision() + " " + truck.getStatus() + " "
//					+ truck.getTerminal());

		} catch (Exception e) {
			logger.fatal("TruckDaoImpl: add(): Exception: " + e.getMessage());
		}

		logger.info("TruckDaoImpl: add(): ENDS");

		return truck;

	}

	private Truck setTruckValues(TruckResponse truckResponse) {

		logger.info("TruckDaoImpl: setTruckValues(): STARTS");

		Truck truck = new Truck();
		truck.setUnitNo(truckResponse.getUnitNo());
		truck.setOwner(truckResponse.getOwner());
		truck.setoOName(truckResponse.getoOName());
		truck.setUsage(truckResponse.getTruchUsage());
		truck.setTruckType(truckResponse.getTruckType());
		truck.setFinance(truckResponse.getFinance());
		truck.setCreatedBy("jagvir");
		truck.setCreatedOn(new Date());
		truck.setModifiedBy("jagvir");
		truck.setModifiedOn(new Date());

		logger.info("TruckDaoImpl: setTruckValues(): ENDS");

		return truck;
	}
}
