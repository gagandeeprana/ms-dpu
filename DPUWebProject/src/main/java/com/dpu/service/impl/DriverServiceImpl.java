package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CategoryDao;
import com.dpu.dao.DivisionDao;
import com.dpu.dao.DriverDao;
import com.dpu.dao.TerminalDao;
import com.dpu.entity.Driver;
import com.dpu.entity.Status;
import com.dpu.model.CategoryReq;
import com.dpu.model.DivisionReq;
import com.dpu.model.DriverReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TerminalResponse;
import com.dpu.model.TypeResponse;
import com.dpu.service.CategoryService;
import com.dpu.service.DivisionService;
import com.dpu.service.DriverService;
import com.dpu.service.StatusService;
import com.dpu.service.TerminalService;
import com.dpu.service.TypeService;

@Component
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverDao driverDao;

	@Autowired
	StatusService statusService;

	@Autowired
	TypeService typeService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	DivisionService divisionService;

	@Autowired
	TerminalService terminalService;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	DivisionDao divisionDao;

	@Autowired
	TerminalDao terminalDao;

	@Autowired
	SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(DriverServiceImpl.class);

	@Override
	public Object addDriver(DriverReq driverReq) {

		logger.info("Inside DriverServiceImpl addDriver() starts");
		Object obj = null;
		String message = "Record Added Successfully";
		try {
			// boolean isDriverExist = isDriverExist(driverReq.getDriverCode());
			// if(!isDriverExist){
			Driver driver = new Driver();
			BeanUtils.copyProperties(driverReq, driver);
			driver.setCategory(categoryDao.findById(driverReq.getCategoryId()));
			driver.setDivision(divisionDao.findById(driverReq.getDivisionId()));
			driver.setTerminal(terminalDao.findById(driverReq.getTerminalId()));
			driver.setRole(typeService.get(driverReq.getRoleId()));
			driver.setDriverClass(typeService.get(driverReq.getDriverClassId()));
			driver.setStatus(statusService.get(driverReq.getStatusId()));
			driverDao.save(driver);
			// return getAllDriver();
			obj = createSuccessObject(message);
			// } else{
			// String errorString = "This driver code is already exist";
			// return errorString;
			// }
		} catch (Exception e) {
			logger.error("Exception inside DriverServiceImpl addDriver() :"
					+ e.getMessage());
			message = "Error while inserting record";
			obj = createFailedObject(message);
		}

		logger.info("Inside DriverServiceImpl addDriver() Ends");
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
		success.setResultList(getAllDriver());
		return success;
	}

	@SuppressWarnings("unused")
	private Driver setDriverValues(DriverReq driverReq) {
		Driver driver = new Driver();
		driver.setDriverCode(driverReq.getDriverCode());
		driver.setFirstName(driverReq.getFirstName());
		driver.setLastName(driverReq.getLastName());
		driver.setAddress(driverReq.getAddress());
		driver.setUnit(driverReq.getUnit());
		driver.setCity(driverReq.getCity());
		driver.setPostalCode(driverReq.getPostalCode());
		driver.setEmail(driverReq.getEmail());
		driver.setHome(driverReq.getHome());
		driver.setFaxNo(driverReq.getFaxNo());
		driver.setCellular(driverReq.getCellular());
		driver.setPager(driverReq.getPager());
		/*
		 * driver.setDivision(driverReq.getDivision());
		 * driver.setTerminalId(driverReq.getTerminalId());
		 * driver.setCatogoryId(driverReq.getCatogoryId());
		 * driver.setRoleId(driverReq.getRoleId());
		 * driver.setStatusId(driverReq.getStatusId());
		 * driver.setDriverClassId(driverReq.getDriverClassId());
		 */
		driver.setCreatedOn(new Date());
		return driver;
	}

	@Override
	public Object updateDriver(Long driverId, DriverReq driverReq) {

		logger.info("Inside DriverServiceImpl updateDriver() Starts, driverId :"
				+ driverId);
		Object obj = null;
		String message = "Record Updated Successfully";

		try {
			Driver driver = driverDao.findById(driverId);

			if (driver != null) {
				String[] ignoreProp = new String[1];
				ignoreProp[0] = "driverId";
				BeanUtils.copyProperties(driverReq, driver, ignoreProp);
				driver.setCategory(categoryDao.findById(driverReq
						.getCategoryId()));
				driver.setDivision(divisionDao.findById(driverReq
						.getDivisionId()));
				driver.setTerminal(terminalDao.findById(driverReq
						.getTerminalId()));
				driver.setRole(typeService.get(driverReq.getRoleId()));
				driver.setDriverClass(typeService.get(driverReq
						.getDriverClassId()));
				driver.setStatus(statusService.get(driverReq.getStatusId()));
				driverDao.update(driver);
				obj = createSuccessObject(message);
			} else {
				message = "Error while updating record";
				obj = createFailedObject(message);
			}

		} catch (Exception e) {
			logger.error("Exception inside DriverServiceImpl updateDriver() :"
					+ e.getMessage());
			message = "Error while updating record";
			obj = createFailedObject(message);
		}

		logger.info("Inside DriverServiceImpl updateDriver() ends, driverId :"
				+ driverId);
		return obj;
	}

	@Override
	public Object deleteDriver(Long driverId) {

		logger.info("Inside DriverServiceImpl deleteDriver() starts, driverId :"
				+ driverId);
		Object obj = null;
		String message = "Record Deleted Successfully";

		try {
			Driver driver = driverDao.findById(driverId);

			if (driver != null) {
				driverDao.delete(driver);
				obj = createSuccessObject(message);
			} else {
				message = "Error while Deleting Record";
				obj = createFailedObject(message);
			}
		} catch (Exception e) {
			logger.error("Exceptiom inside DriverServiceImpl deleteDriver() :"
					+ e.getMessage());
			message = "Error while Deleting Record";
			obj = createFailedObject(message);
		}

		logger.info("Inside DriverServiceImpl deleteDriver() ends, driverId :"
				+ driverId);
		return obj;

	}

	@Override
	public List<DriverReq> getAllDriver() {

		logger.info("Inside DriverServiceImpl getAllDriver() starts ");
		Session session = null;

		List<Driver> listOfDriver = null;

		List<DriverReq> drivers = null;

		try {
			session = sessionFactory.openSession();

			logger.info("[getAllDrivers]:  Service : Enter");

			listOfDriver = driverDao.findAll();
			logger.info("[getAllDrivers]: Service: listOfDriver : "
					+ listOfDriver);

			listOfDriver = driverDao.findAll(session);
			logger.info("[getAllDrivers]: Service: listOfDriver : "
					+ listOfDriver);

			List<Driver> listOfDriver = driverDao.findAll(session);

			drivers = setDriverData(listOfDriver);

		} catch (Exception e) {
			logger.error("Exception inside DriverServiceImpl getAllDriver():"
					+ e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside DriverServiceImpl getAllDriver() ends ");
		return drivers;
	}

	private List<DriverReq> setDriverData(List<Driver> listOfDriver) {

		List<DriverReq> drivers = new ArrayList<DriverReq>();
		if (listOfDriver != null && !listOfDriver.isEmpty()) {
			for (Driver driver : listOfDriver) {
				DriverReq driverReq = new DriverReq();
				BeanUtils.copyProperties(driver, driverReq);
				driverReq.setCategoryName(driver.getCategory().getName());
				driverReq.setTerminalName(driver.getTerminal()
						.getTerminalName());
				driverReq.setStatusName(driver.getStatus().getStatus());
				driverReq.setDivisionName(driver.getDivision()
						.getDivisionName());
				driverReq.setDriverClassName(driver.getDriverClass()
						.getTypeName());
				driverReq.setRoleName(driver.getRole().getTypeName());
				drivers.add(driverReq);
			}
		}

		return drivers;
	}

	@Override
	public Object getDriverByDriverId(Long driverId) {

		logger.info("Inside DriverServiceImpl getDriverByDriverId() starts, driverId :"
				+ driverId);
		Session session = null;
		Object obj = null;
		String message = "Record data get Successfully";

		try {
			session = sessionFactory.openSession();
			Driver driver = driverDao.findById(driverId, session);
			DriverReq response = new DriverReq();

			if (driver != null) {
				BeanUtils.copyProperties(driver, response);
				response.setCategoryId(driver.getCategory().getCategoryId());
				response.setStatusId(driver.getStatus().getId());
				response.setDivisionId(driver.getDivision().getDivisionId());
				response.setDriverClassId(driver.getDriverClass().getTypeId());
				response.setRoleId(driver.getRole().getTypeId());
				response.setTerminalId(driver.getTerminal().getTerminalId());
				List<Status> statusList = statusService.getAll();
				response.setStatusList(statusList);

				List<TypeResponse> roleList = typeService.getAll(6l);
				response.setRoleList(roleList);

				List<TypeResponse> driverClassList = typeService.getAll(5l);
				response.setDriverClassList(driverClassList);

				List<CategoryReq> categoryList = categoryService.getAll();
				response.setCategoryList(categoryList);

				List<DivisionReq> divisionList = divisionService.getAll("");
				response.setDivisionList(divisionList);

				List<TerminalResponse> terminalList = terminalService
						.getAllTerminals();
				response.setTerminalList(terminalList);
				obj = createSuccessObjectForParRecord(message, response);
			} else {
				message = "Error while getting record";
				obj = createFailedObject(message);
			}
		} catch (Exception e) {
			message = "Error while getting record";
			obj = createFailedObject(message);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return obj;
	}

	private Object createSuccessObjectForParRecord(String message,
			DriverReq response) {
		Success success = new Success();
		success.setMessage(message);
		success.setResultList(response);
		return success;
	}

	public boolean isDriverExist(String driverCode) {
		boolean isDriverExist = false;

		Criterion driverCriteria = Restrictions.eqOrIsNull("driverCode",
				driverCode);
		List<Driver> drivers = driverDao.find(driverCriteria);

		if (drivers.size() == 0) {

			return isDriverExist;
		}

		isDriverExist = true;
		return isDriverExist;

	}

	@Override
	public DriverReq getOpenAdd() {

		DriverReq driver = new DriverReq();

		List<Status> statusList = statusService.getAll();
		driver.setStatusList(statusList);

		List<TypeResponse> roleList = typeService.getAll(6l);
		driver.setRoleList(roleList);

		List<TypeResponse> driverClassList = typeService.getAll(5l);
		driver.setDriverClassList(driverClassList);

		List<CategoryReq> categoryList = categoryService.getAll();
		driver.setCategoryList(categoryList);

		List<DivisionReq> divisionList = divisionService.getAll("");
		driver.setDivisionList(divisionList);

		List<TerminalResponse> terminalList = terminalService.getAllTerminals();
		driver.setTerminalList(terminalList);

		return driver;
	}

	@Override
	public List<DriverReq> getDriverByDriverCodeOrName(String driverCodeOrName) {

		List<DriverReq> driverReqList = new ArrayList<DriverReq>();
		List<Driver> driverList = driverDao
				.searchDriverByDriverCodeOrName(driverCodeOrName);
		driverReqList = setDriverData(driverList);
		return driverReqList;
	}

}
