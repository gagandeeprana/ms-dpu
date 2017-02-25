package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.common.CommonProperties;
import com.dpu.dao.DivisionDao;
import com.dpu.dao.TerminalDao;
import com.dpu.dao.TruckDao;
import com.dpu.dao.TypeDao;
import com.dpu.entity.Status;
import com.dpu.entity.Truck;
import com.dpu.model.CategoryReq;
import com.dpu.model.DivisionReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TerminalResponse;
import com.dpu.model.TruckResponse;
import com.dpu.model.TypeResponse;
import com.dpu.service.CategoryService;
import com.dpu.service.DivisionService;
import com.dpu.service.StatusService;
import com.dpu.service.TerminalService;
import com.dpu.service.TruckService;
import com.dpu.service.TypeService;

@Component
public class TruckServiceImpl implements TruckService {

	@Autowired
	TruckDao truckDao;

	@Autowired
	StatusService statusService;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	CategoryService categoryService;

	@Autowired
	DivisionService divisionService;

	@Autowired
	TerminalService terminalService;

	@Autowired
	TypeService typeService;

	@Autowired
	DivisionDao divisionDao;

	@Autowired
	TerminalDao terminalDao;

	@Autowired
	TypeDao typeDao;

	Logger logger = Logger.getLogger(TruckServiceImpl.class);

	private Object createSuccessObject(String msg, long code) {
		Success success = new Success();
		success.setCode(code);
		success.setMessage(msg);
		success.setResultList(getAllTrucks(""));
		return success;
	}

	private Object createFailedObject(String msg, long code) {
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		failed.setResultList(getAllTrucks(""));
		return failed;
	}

	@Override
	public Object update(Long id, TruckResponse truckResponse) {
		logger.info("[TruckServiceImpl] [update] : Enter ");
		Truck truck = null;
		try {
			truck = truckDao.findById(id);
			// if (truck != null) {
			truck.setUnitNo(truckResponse.getUnitNo());
			truck.setOwner(truckResponse.getOwner());
			truck.setoOName(truckResponse.getoOName());
			truck.setCategory(categoryService.getCategory(truckResponse
					.getCategoryId()));
			truck.setDivision(divisionDao.findById(truckResponse
					.getDivisionId()));
			truck.setTerminal(terminalDao.findById(truckResponse
					.getTerminalId()));
			truck.setStatus(statusService.get(truckResponse.getStatusId()));
			truck.setUsage(truckResponse.getTruchUsage());

			truck.setType(typeService.get(truckResponse.getTruckTypeId()));

			truck.setType(typeDao.findById(truckResponse.getTruckTypeId()));

			truck.setFinance(truckResponse.getFinance());

			truckDao.update(truck);
			// }
		} catch (Exception e) {
			logger.info("Exception inside TruckServiceImpl update() :"
					+ e.getMessage());
			return createFailedObject(
					CommonProperties.Truck_unable_to_update_message,
					Long.parseLong(CommonProperties.Truck_unable_to_update_code));
		}

		logger.info("[TruckServiceImpl] [get] : Exit ");
		return createSuccessObject(CommonProperties.Truck_updated_message,
				Long.parseLong(CommonProperties.Equipment_updated_code));
	}

	@Override
	public Object delete(Long id) {
		logger.info("[TruckServiceImpl] [delete] : Enter ");
		Truck truck = null;
		try {
			truck = truckDao.findById(id);
			truckDao.delete(truck);
		} catch (Exception e) {
			logger.error("[TruckServiceImpl] [delete] : ", e);
			return createFailedObject(
					CommonProperties.Truck_unable_to_delete_message,
					Long.parseLong(CommonProperties.Truck_unable_to_delete_code));
		}
		logger.info("[TruckServiceImpl] [get] : Exit ");
		return createSuccessObject(CommonProperties.Truck_deleted_message,
				Long.parseLong(CommonProperties.Truck_deleted_code));
	}

	@Override
	public TruckResponse get(Long id) {
		logger.info("[TruckServiceImpl] [get] : Enter ");
		Truck truck = truckDao.findById(id);
		TruckResponse truckResponse = new TruckResponse();
		if (truck != null) {
			BeanUtils.copyProperties(truck, truckResponse);
			truckResponse.setUnitNo(truck.getUnitNo());
			truckResponse.setOwner(truck.getOwner());
			truckResponse.setoOName(truck.getoOName());
			truckResponse.setStatusName(truck.getStatus().getStatus());
			truckResponse.setTruchUsage(truck.getUsage());
			truckResponse.setDivisionId(truck.getDivision().getDivisionId());
			truckResponse.setCategoryId(truck.getCategory().getCategoryId());
			truckResponse.setTerminalId(truck.getTerminal().getTerminalId());
			truckResponse.setStatusId(truck.getStatus().getId());
			truckResponse.setTruckTypeId(truck.getType().getTypeId());

			truckResponse.setTypeName(truck.getType().getTypeName());

			truckResponse.setTruckType(truck.getType().getTypeName());

			truckResponse.setFinance(truck.getFinance());

			List<Status> lstStatus = statusService.getAll();
			truckResponse.setStatusList(lstStatus);

			List<CategoryReq> lstCategories = categoryService.getAll();
			truckResponse.setCategoryList(lstCategories);

			List<TerminalResponse> lstTerminalResponses = terminalService
					.getAllTerminals();
			truckResponse.setTerminalList(lstTerminalResponses);

			List<DivisionReq> lstDivision = divisionService.getAll("");
			truckResponse.setDivisionList(lstDivision);

			List<TypeResponse> truckTypeList = typeService.getAll(8l);
			truckResponse.setTruckTypeList(truckTypeList);

		}
		logger.info("[TruckServiceImpl] [get] : Exit ");
		return truckResponse;
	}

	@Override
	public List<TruckResponse> getAllTrucks(String owner) {
		logger.info("[TruckServiceImpl] [getAllTrucks] : Enter ");
		List<Truck> lstTruck = null;
		List<TruckResponse> lstTruckResponse = new ArrayList<TruckResponse>();
		try {

			if (owner != null && owner.length() > 0) {
				Criterion criterion = Restrictions.like("owner", owner,
						MatchMode.ANYWHERE);
				lstTruck = truckDao.find(criterion);
			} else {
				lstTruck = truckDao.findAll();
			}
			if (lstTruck != null && lstTruck.size() > 0) {
				for (Truck truck : lstTruck) {
					TruckResponse truckResponse = new TruckResponse();
					truckResponse.setTruckId(truck.getTruckId());
					truckResponse.setUnitNo(truck.getUnitNo());
					truckResponse.setOwner(truck.getOwner());
					truckResponse.setoOName(truck.getoOName());
					truckResponse
							.setCatogoryName(truck.getCategory().getName());
					truckResponse.setTruchUsage(truck.getUsage());

					truckResponse.setDivisionName(truck.getDivision()
							.getDivisionName());
					truckResponse.setTerminalName(truck.getTerminal()
							.getTerminalName());
					truckResponse.setTypeName(truck.getType().getTypeName());

					truckResponse.setDivisionName(truck.getDivision()
							.getDivisionName());
					truckResponse.setTerminalName(truck.getTerminal()
							.getTerminalName());
					truckResponse.setTruckType(truck.getType().getTypeName());

					truckResponse.setFinance(truck.getFinance());
					truckResponse.setStatusName(truck.getStatus().getStatus());
					lstTruckResponse.add(truckResponse);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[TruckServiceImpl] [getAllTrucks] : Exit ");
		return lstTruckResponse;
	}

	@Override
	public Object add(TruckResponse truckResponse) {
		logger.info("TruckServiceImpl: add():  STARTS");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			truckDao.add(session, truckResponse);
			if (tx != null) {
				tx.commit();
			}
		} catch (Exception e) {
			logger.error("TruckServiceImpl: add(): Exception: ", e);
			if (tx != null) {
				tx.rollback();
			}
			return createFailedObject(
					CommonProperties.Truck_unable_to_add_message,
					Long.parseLong(CommonProperties.Truck_unable_to_add_code));
		} finally {
			logger.info("TruckServiceImpl: add():  finally block");
			if (session != null) {
				session.close();
			}
		}

		logger.info("TruckServiceImpl: add():  ENDS");

		return createSuccessObject(CommonProperties.Truck_added_message,
				Long.parseLong(CommonProperties.Truck_added_code));

	}

	@Override
	public TruckResponse getOpenAdd() {
		TruckResponse truckResponse = new TruckResponse();

		List<Status> lstStatus = statusService.getAll();
		truckResponse.setStatusList(lstStatus);

		List<CategoryReq> categoryList = categoryService.getAll();
		truckResponse.setCategoryList(categoryList);

		List<DivisionReq> divisionList = divisionService.getAll("");
		truckResponse.setDivisionList(divisionList);

		List<TerminalResponse> terminalList = terminalService.getAllTerminals();
		truckResponse.setTerminalList(terminalList);

		List<TypeResponse> truckTypeList = typeService.getAll(8l);
		truckResponse.setTruckTypeList(truckTypeList);
		return truckResponse;

	}

}
