/**
 * 
 */
package com.dpu.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.common.CommonProperties;
import com.dpu.dao.EmployeeDao;
import com.dpu.dao.EquipmentDao;
import com.dpu.entity.Employee;
import com.dpu.model.EmployeeModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.EmployeeService;
import com.dpu.service.TypeService;
import com.dpu.util.DateUtil;

/**
 * @author gagan
 *
 */
@Component
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	EquipmentDao equipmentDao;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	TypeService typeService;

	private Object createSuccessObject(String msg, long code) {
		Success success = new Success();
		success.setCode(code);
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg, long code) {
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object add(EmployeeModel employeeModel) {

		logger.info("EmployeeServiceImpl: add():  STARTS");
		Session session = null;
		Transaction tx = null;

		try {

			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = new Employee();
			setEmployeeValues(employeeModel, employee);
			employeeDao.add(session, employee);
			if (tx != null) {
				tx.commit();
			}

		} catch (Exception e) {
			logger.fatal("EmployeeServiceImpl: add(): Exception: " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			return createFailedObject( CommonProperties.employee_unable_to_add_message, Long.parseLong(CommonProperties.Equipment_unable_to_add_code));
		} finally {
			logger.info("EmployeeServiceImpl: add():  finally block");
			if (session != null) {
				session.close();
			}
		}

		logger.info("EmployeeServiceImpl: add():  ENDS");

		return createSuccessObject(CommonProperties.employee_added_message,
				Long.parseLong(CommonProperties.Equipment_added_code));
	}

private void setEmployeeValues(EmployeeModel employeeModel, Employee employee) {
		
		logger.info("EmployeeDaoImpl: setEmployeeValues(): STARTS");
		employee.setFirstName(employeeModel.getFirstName());
		employee.setLastName(employeeModel.getLastName());
		employee.setJobTitle(employeeModel.getJobTitle());
		employee.setUsername(employeeModel.getUsername());
		employee.setPassword(employeeModel.getPassword());
		employee.setEmail(employeeModel.getEmail());
		employee.setPhone(employeeModel.getPhone());
		String hiringDate = employeeModel.getHiringdate();
		String terminationDate = employeeModel.getTerminationdate();
		employee.setHiringDate(DateUtil.changeStringToDate(hiringDate));
		employee.setTerminationDate(DateUtil.changeStringToDate(terminationDate));
		employee.setCreatedBy(employeeModel.getCreatedBy());
		employee.setModifiedBy(employeeModel.getModifiedBy());
		logger.info("EmployeeDaoImpl: setEmployeeValues(): ENDS");

	}

	
	@Override
	public List<EmployeeModel> getAll() {
		List<Employee> employees = null;
		List<EmployeeModel> employeeResponse = new ArrayList<EmployeeModel>();
		employees = employeeDao.findAll();
		if (employees != null && employees.size() > 0) {
			for (Employee employee : employees) {
				EmployeeModel employeeModel = new EmployeeModel();
				employeeModel.setEmployeeId(employee.getEmployeeId());
				employeeModel.setFirstName(employee.getFirstName());
				employeeModel.setLastName(employee.getLastName());
				employeeModel.setUsername(employee.getUsername());
				employeeModel.setJobTitle(employee.getJobTitle());
				employeeModel.setEmail(employee.getEmail());
				employeeModel.setPhone(employee.getPhone());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				employeeModel.setHiringdate(sdf.format(employee.getHiringDate()));
				employeeModel.setTerminationdate(sdf.format(employee.getTerminationDate()));
				employeeResponse.add(employeeModel);
			}
		}
		return employeeResponse;
	}
	
	@Override
	public Object getUserById(Long userId) {

		logger.info("Inside EmployeeServiceImpl getUserById() starts, userId :"+ userId);
		Session session = null;
		Object obj = null;
		String message = "User data get Successfully";

		EmployeeModel employeeModel = null;
		try {
			session = sessionFactory.openSession();
			Employee employee = employeeDao.findById(userId);
			
			if (employee != null) {
				employeeModel = new EmployeeModel();
				BeanUtils.copyProperties(employee, employeeModel);
				employeeModel.setEmployeeId(employee.getEmployeeId());
				
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
		return employeeModel;
	}
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@Override
	public Object delete(Long userId) {
		logger.info("VehicleMaintainanceCategoryServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, userId);
			if (employee != null) {
				session.delete(employee);
				tx.commit();
			} else {
				return createFailedObject("");
			}

		} catch (Exception e) {
			logger.info("Exception inside VehicleMaintainanceCategoryServiceImpl delete() : " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject("");
			}
			return createFailedObject("");
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("VehicleMaintainanceCategoryServiceImpl delete() ends.");
		return createSuccessObject("");
	}

	private Object createSuccessObject(String msg) {

		Success success = new Success();
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	@Override
	public List<EmployeeModel> getUserByUserName(String userName) {
		
		logger.info("VehicleMaintainanceCategoryServiceImpl getVmcByVmcName() starts ");
		Session session = null;
		List<EmployeeModel> employeeResponse = new ArrayList<EmployeeModel>();

		try {
			session = sessionFactory.openSession();
			List<Employee> employeeList = employeeDao.getUserByUserName(session, userName);
			if (employeeList != null && !employeeList.isEmpty()) {
				for (Employee employee : employeeList) {
					EmployeeModel employeeModel = new EmployeeModel();
					employeeModel.setEmployeeId(employee.getEmployeeId());
					employeeModel.setFirstName(employee.getFirstName());
					employeeModel.setLastName(employee.getLastName());
					employeeModel.setUsername(employee.getUsername());
					employeeModel.setJobTitle(employee.getJobTitle());
					employeeModel.setEmail(employee.getEmail());
					employeeModel.setPhone(employee.getPhone());
					/*employeeModel.setHiringDate(employee.getHiringDate());
					employeeModel.setTerminationDate(employee.getTerminationDate());*/
					employeeResponse.add(employeeModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("VehicleMaintainanceCategoryServiceImpl getVmcByVmcName() ends ");
		return employeeResponse;
	}

	@Override
	public Object update(Long id, EmployeeModel employeeModel) {

		logger.info("VehicleMaintainanceCategoryServiceImpl update() starts.");
		try {
			Employee employee = employeeDao.findById(id);

			if (employee != null) {
				setEmployeeValues(employeeModel, employee);
			} else {
				return createFailedObject("");
			}

		} catch (Exception e) {
			logger.info("Exception inside VehicleMaintainanceCategoryServiceImpl update() :"+ e.getMessage());
			return createFailedObject("");
		}

		logger.info("VehicleMaintainanceCategoryServiceImpl update() ends.");
		return createSuccessObject("");
	}
	/*@Override
	public EquipmentReq get(Long id) {
		Equipment equipment = equipmentDao.findById(id);
		EquipmentReq response = null;
		if (equipment != null) {
			response = new EquipmentReq();
			response.setEquipmentId(equipment.getEquipmentId());
			response.setTypeId(equipment.getType().getTypeId());
			response.setEquipmentName(equipment.getEquipmentName());
			response.setDescription(equipment.getDescription());

			List<TypeResponse> typeList = typeService.getAll(1l);

			if (typeList != null && !typeList.isEmpty()) {
				response.setTypeList(typeList);
			}

		}

		return response;
	}
*/
}
