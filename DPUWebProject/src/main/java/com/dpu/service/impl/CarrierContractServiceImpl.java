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

import com.dpu.dao.CarrierContractDao;
import com.dpu.dao.CarrierDao;
import com.dpu.entity.CarrierContract;
import com.dpu.model.CarrierContractModel;
import com.dpu.model.CarrierModel;
import com.dpu.model.CategoryReq;
import com.dpu.model.DivisionReq;
import com.dpu.model.DriverReq;
import com.dpu.model.EquipmentReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.CarrierContractService;
import com.dpu.service.CarrierService;
import com.dpu.service.CategoryService;
import com.dpu.service.DivisionService;
import com.dpu.service.DriverService;
import com.dpu.service.EquipmentService;
import com.dpu.service.TypeService;

@Component
public class CarrierContractServiceImpl implements CarrierContractService {

	Logger logger = Logger.getLogger(CarrierContractServiceImpl.class);

	@Autowired
	CarrierContractDao carrierContractDao;

	@Autowired
	CarrierDao carrierDao;

	@Autowired
	CarrierService carrierService;

	@Autowired
	DriverService driverService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	TypeService typeService;

	@Autowired
	EquipmentService equipmentService;

	@Autowired
	DivisionService divisionService;

	// @Autowired
	// ArrangedWithDao arrangedWithDao;

	@Autowired
	SessionFactory sessionFactory;

	@Value("${CarrierContract_added_message}")
	private String CarrierContract_added_message;

	@Value("${CarrierContract_unable_to_add_message}")
	private String CarrierContract_unable_to_add_message;

	@Value("${CarrierContract_unable_to_delete_message}")
	private String CarrierContract_unable_to_delete_message;

	@Value("${CarrierContract_dependent_message}")
	private String CarrierContract_dependent_message;

	@Value("${CarrierContract_deleted_message}")
	private String CarrierContract_deleted_message;

	@Override
	public List<CarrierContractModel> getAllCarrierContract() {

		logger.info("Inside CarrierContractServiceImpl getAllCarrierContract() starts ");
		Session session = null;
		List<CarrierContractModel> returnResponse = new ArrayList<CarrierContractModel>();

		try {
			session = sessionFactory.openSession();
			List<CarrierContract> listOfCarrierContract = carrierContractDao
					.findAllCarrierContract(session);

			if (listOfCarrierContract != null
					&& !listOfCarrierContract.isEmpty()) {
				for (CarrierContract carrierContract : listOfCarrierContract) {
					CarrierContractModel response = new CarrierContractModel();
					setCarrierContractData(carrierContract, response);
					returnResponse.add(response);
				}

			}
		} catch (Exception e) {
			logger.error("Exception inside CarrierContractServiceImpl getAllCarrierContract() :"
					+ e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		logger.info("Inside CarrierContractServiceImpl getAllCarrierContract() ends ");
		return returnResponse;
	}

	private void setCarrierContractData(CarrierContract carrierContract,
			CarrierContractModel response) {

		response.setContractNoId(carrierContract.getContractNoId());
		response.setArrangedWithName(carrierContract.getArrangedWith().getTypeName());
		response.setCargo(carrierContract.getCargo());
		response.setCarrierName(carrierContract.getCarrier().getName());
		response.setCarrierRat(carrierContract.getCarrierRat());
		response.setCategoryName(carrierContract.getCategory().getName());
		response.setCommodityName(carrierContract.getCommodity()
				.getTypeName());
		response.setContractNo(carrierContract.getContractNo());
		response.setContractNoId(carrierContract.getContractNoId());
		response.setContractRate(carrierContract.getContractRate());
		response.setCreatedBy(carrierContract.getCreatedBy());
		response.setCurrencyName(carrierContract.getCurrency()
				.getTypeName());
		response.setDispatched(carrierContract.getDispatched());
		response.setDispatcherName(carrierContract.getDispatcher()
				.getTypeName());
		response.setDivisionName(carrierContract.getDivision()
				.getDivisionName());
		response.setdOTno(carrierContract.getdOTno());
		response.setDriverName(carrierContract.getDriver().getFirstName());
		response.setEquipmentName(carrierContract.getEquipment()
				.getEquipmentName());
		response.setHours(carrierContract.getHours());
		response.setInsExpires(carrierContract.getInsExpires());
		response.setLiabity(carrierContract.getLiabity());
		response.setmCno(carrierContract.getmCno());
		response.setMiles(carrierContract.getMiles());
		response.setRoleName(carrierContract.getRole().getTypeName());
		response.setTransDoc(carrierContract.getTransDoc());

	}

	@Override
	public Object addCarrierContract(CarrierContractModel carrierContractModel) {

		logger.info("Inside CarrierContractServiceImpl addCarrierContract() starts");
		Object obj = null;
		try {

			CarrierContract carrierContract = new CarrierContract();
			BeanUtils.copyProperties(carrierContractModel, carrierContract);
			/*
			 * carrierContract.setCategory(categoryDao.findById(driverReq
			 * .getCategoryId()));
			 * carrierContract.setDivision(divisionDao.findById(driverReq
			 * .getDivisionId()));
			 * carrierContract.setTerminal(terminalDao.findById(driverReq
			 * .getTerminalId()));
			 * carrierContract.setRole(typeService.get(driverReq.getRoleId()));
			 * carrierContract.setDriverClass(typeService.get(driverReq
			 * .getDriverClassId())); carrierContract
			 * .setStatus(statusService.get(driverReq.getStatusId()));
			 */
			carrierContractDao.save(carrierContract);
			obj = createSuccessObject(CarrierContract_added_message);
		} catch (Exception e) {
			logger.error("Exception inside CarrierContractServiceImpl addCarrierContract() :"
					+ e.getMessage());
			obj = createFailedObject(CarrierContract_unable_to_add_message);
		}

		logger.info("Inside CarrierContractServiceImpl addCarrierContract() Ends");
		return obj;
	}

	private Object createFailedObject(String errorMessage) {

		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	private Object createSuccessObject(String message) {

		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAllCarrierContract());
		return success;
	}

	@Override
	public CarrierContractModel getOpenAdd() {

		CarrierContractModel carrierContractModel = new CarrierContractModel();

		List<CarrierModel> carrierList = carrierService.getAll();
		carrierContractModel.setCarrierList(carrierList);

		List<TypeResponse> arrangedWithList = typeService.getAll(18l);
		carrierContractModel.setArrangedWithList(arrangedWithList);

		List<DriverReq> driverList = driverService.getAllDriver();
		carrierContractModel.setDriverList(driverList);

		List<TypeResponse> currencyList = typeService.getAll(9l);
		carrierContractModel.setCurrencyList(currencyList);

		List<CategoryReq> categoryList = categoryService.getAll();
		carrierContractModel.setCategoryList(categoryList);

		List<TypeResponse> roleList = typeService.getAll(6l);
		carrierContractModel.setRoleList(roleList);

		List<EquipmentReq> equipmentList = equipmentService.getAll("");
		carrierContractModel.setEquipmentList(equipmentList);

		List<TypeResponse> commodityList = typeService.getAll(17l);
		carrierContractModel.setCommodityList(commodityList);

		List<DivisionReq> divisionList = divisionService.getAll("");
		carrierContractModel.setDivisionList(divisionList);

		List<TypeResponse> dispatcherList = typeService.getAll(19l);
		carrierContractModel.setDispatcherList(dispatcherList);

		return carrierContractModel;
	}

	@Override
	public Object deleteCarrierContract(Long carrierContractId) {

		logger.info("Inside CarrierContractServiceImpl deleteCarrierContract() starts, driverId :"
				+ carrierContractId);
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			CarrierContract carrierContract = carrierContractDao
					.findById(carrierContractId);

			if (carrierContract != null) {
				session.delete(carrierContract);
			} else {
				return createFailedObject(CarrierContract_unable_to_delete_message);
			}
		} catch (Exception e) {
			logger.error("Exceptiom inside CarrierContractServiceImpl deleteCarrierContract() :"
					+ e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(CarrierContract_dependent_message);
			}
			return createFailedObject(CarrierContract_unable_to_delete_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CarrierContractServiceImpl deleteCarrierContract() ends, driverId :"
				+ carrierContractId);
		return createSuccessObject(CarrierContract_deleted_message);

	}

}
