package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.dao.HandlingDao;
import com.dpu.dao.IssueDao;
import com.dpu.dao.PurchaseOrderDao;
import com.dpu.entity.Category;
import com.dpu.entity.Driver;
import com.dpu.entity.Issue;
import com.dpu.entity.PurchaseOrder;
import com.dpu.entity.Type;
import com.dpu.entity.VehicleMaintainanceCategory;
import com.dpu.entity.Vendor;
import com.dpu.model.CategoryReq;
import com.dpu.model.DriverReq;
import com.dpu.model.Failed;
import com.dpu.model.IssueModel;
import com.dpu.model.PurchaseOrderModel;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.model.VehicleMaintainanceCategoryModel;
import com.dpu.model.VendorModel;
import com.dpu.service.CategoryService;
import com.dpu.service.DriverService;
import com.dpu.service.IssueService;
import com.dpu.service.PurchaseOrderService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;
import com.dpu.service.VehicleMaintainanceCategoryService;
import com.dpu.service.VendorService;

@Component
public class PurchaseOrderServiceImpl implements PurchaseOrderService  {

	Logger logger = Logger.getLogger(PurchaseOrderServiceImpl.class);

	@Autowired
	HandlingDao handlingDao;
	
	@Autowired
	IssueDao issueDao;

	@Autowired
	StatusService statusService;
	
	@Autowired
	IssueService issueService;

	@Autowired
	VehicleMaintainanceCategoryService vehicleMaintainanceCategoryService;
	
	@Autowired
	DriverService driverService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	TypeService typeService;
	
	@Autowired
	PurchaseOrderDao poDao;

	@Value("${issue_added_message}")
	private String issue_added_message;

	@Value("${issue_unable_to_add_message}")
	private String issue_unable_to_add_message;

	@Value("${issue_deleted_message}")
	private String issue_deleted_message;

	@Value("${issue_unable_to_delete_message}")
	private String issue_unable_to_delete_message;

	@Value("${issue_updated_message}")
	private String issue_updated_message;

	@Value("${issue_unable_to_update_message}")
	private String issue_unable_to_update_message;

	@Value("${issue_already_used_message}")
	private String issue_already_used_message;

	@Override
	public List<IssueModel> getAll() {

		logger.info("IssueServiceImpl getAll() starts ");
		Session session = null;
		List<IssueModel> issueList = new ArrayList<IssueModel>();
		try {
			session = sessionFactory.openSession();
			List<Issue> issues = issueDao.findAll(session);
			issueList = setIssueData(issues, issueList);
			
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("IssueServiceImpl getAll() ends ");
		return issueList;
	}

	private List<IssueModel> setIssueData(List<Issue> issues, List<IssueModel> issueList) {
		if (issues != null && !issues.isEmpty()) {
			for (Issue issue : issues) {
				IssueModel issueObj = new IssueModel();
				issueObj.setId(issue.getId());
				issueObj.setTitle(issue.getIssueName());
				issueObj.setVmcName(issue.getVmc().getName());
				issueObj.setReportedByName(issue.getReportedBy().getFirstName());
				//issueObj.setUnitTypeName(issue.getUnitType().getName());
				issueObj.setUnitNo(issue.getUnitNo());
				issueObj.setStatusName(issue.getStatus().getTypeName());
				issueList.add(issueObj);
			}
		}
		
		return issueList;
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
		// failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object update(Long id, IssueModel issueModel) {

		logger.info("IssueServiceImpl update() starts.");
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Issue issue = issueDao.findById(id);

			if (issue != null) {
				setIssueValues(issueModel,session,issue);
				issueDao.update(issue, session);
				tx.commit();
			} else {
				return createFailedObject(issue_unable_to_update_message);
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.info("Exception inside IssueServiceImpl update() :"+ e.getMessage());
			return createFailedObject(issue_unable_to_update_message);
		} finally {
			if(session != null) {
				session.close();
			}
		}

		logger.info("IssueServiceImpl update() ends.");
		return createSuccessObject(issue_updated_message);
	}

	@Override
	public Object delete(Long id) {

		logger.info("IssueServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Issue issue = (Issue) session.get(Issue.class, id);
			if (issue != null) {
				session.delete(issue);
				tx.commit();
			} else {
				return createFailedObject(issue_unable_to_delete_message);
			}

		} catch (Exception e) {
			logger.info("Exception inside IssueServiceImpl delete() : " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(issue_already_used_message);
			}
			return createFailedObject(issue_unable_to_delete_message);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("IssueServiceImpl delete() ends.");
		return createSuccessObject(issue_deleted_message);
	}

	@Override
	public IssueModel get(Long id) {

		logger.info("IssueServiceImpl get() starts.");
		Session session = null;
		IssueModel issueModel = new IssueModel();

		try {

			session = sessionFactory.openSession();
			Issue issue = issueDao.findById(id, session);

			if (issue != null) {

				issueModel.setId(issue.getId());
				issueModel.setTitle(issue.getIssueName());
				
				issueModel.setVmcId(issue.getVmc().getId());
				issueModel.setReportedById(issue.getReportedBy().getDriverId());
				//issueModel.setUnitTypeId(issue.getUnitType().getCategoryId());
				issueModel.setUnitNo(issue.getUnitNo());
				issueModel.setStatusId(issue.getStatus().getTypeId());
				
				List<VehicleMaintainanceCategoryModel> vmcList = vehicleMaintainanceCategoryService.getSpecificData();
				issueModel.setVmcList(vmcList);
				
				List<DriverReq> driverList = driverService.getSpecificData();
				issueModel.setReportedByList(driverList);
				
				List<TypeResponse> statusList = typeService.getAll(23l);
				issueModel.setStatusList(statusList);
				
				/*List<CategoryReq> unitTypeList = categoryService.getSpecificData();
				issueModel.setUnitTypeList(unitTypeList);*/
				
				//List<String> unitNos = getUnitNosForCategory(issue.getUnitType().getCategoryId(), session);
				//issueModel.setUnitNos(unitNos);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("IssueServiceImpl get() ends.");
		return issueModel;
	}

	@Override
	public PurchaseOrderModel getOpenAdd() {

		logger.info("PurchaseOrderServiceImpl getOpenAdd() starts ");
		PurchaseOrderModel poModel = new PurchaseOrderModel();
		
		List<VendorModel> vendorList = vendorService.getSpecificData();
		poModel.setVendorList(vendorList);
		
		//List<IssueModel> allIssues = issueService.getActiveAndIncompleteIssues();
		//poModel.setIssueList(allIssues);
		
		List<CategoryReq> categoryList = categoryService.getSpecificData();
		poModel.setCategoryList(categoryList);
		
		List<TypeResponse> unitTypeList = typeService.getAll(25l);
		poModel.setUnitTypeList(unitTypeList);
		
		List<TypeResponse> statusList = typeService.getAll(24l);
		poModel.setStatusList(statusList);
		
		logger.info("PurchaseOrderServiceImpl getOpenAdd() ends ");
		return poModel;
	}

	@Override
	public List<IssueModel> getIssueByIssueName(String issueName) {

		logger.info("IssueServiceImpl getIssueByIssueName() starts ");
		Session session = null;
		List<IssueModel> issueList = new ArrayList<IssueModel>();

		try {
			session = sessionFactory.openSession();
			List<Issue> issues = issueDao.getIssueByIssueName(session, issueName);
			issueList = setIssueData(issues, issueList);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("IssueServiceImpl getHandlingByHandlingName() ends ");
		return issueList;
	}
	
	@Override
	public List<IssueModel> getSpecificData() {

		Session session = sessionFactory.openSession();
		List<IssueModel> issueList = new ArrayList<IssueModel>();
		
		try {
			List<Object[]> issueData = handlingDao.getSpecificData(session, "Issue", "id", "issueName");
			
			if (issueData != null && !issueData.isEmpty()) {
				for (Object[] row : issueData) {
					IssueModel issueObj = new IssueModel();
					issueObj.setId((Long) row[0]);
					issueObj.setTitle(String.valueOf(row[1]));
					issueList.add(issueObj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return issueList;
	}

	@Override
	public IssueModel getUnitNo(Long categoryId) {
		
		Session session = null;
		IssueModel issueModel = new IssueModel();
		try {
			session = sessionFactory.openSession();
			List<String> getUnitNos = getUnitNosForCategory(categoryId, session);
			issueModel.setUnitNos(getUnitNos);
		} finally {
			if(session != null){
				session.close();
			}
		}
		
		return issueModel;
	}

	private List<String> getUnitNosForCategory(Long categoryId, Session session) {
		List<String> unitNo = new ArrayList<String>();
		/*List<Object> unitNos = issueDao.getUnitNos(categoryId, session);
		if(unitNos != null){
			unitNo = iterateUnitNos(unitNos);
		}*/
		
		return unitNo;
	}

	private List<String> iterateUnitNos(List<Object> unitNos) {
		
		List<String> unitNoList = new ArrayList<String>();
		for (Object obj : unitNos) {
			String unitNo = String.valueOf(obj);
			unitNoList.add(unitNo);
		}
		
		return unitNoList;
	}

	@Override
	public Object addIssue(IssueModel issueModel) {
		
		logger.info("IssueServiceImpl addIssue() starts ");
		Issue issue = null;
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			issue = new Issue();
			issue = setIssueValues(issueModel, session, issue);
			issueDao.saveIssue(issue, session);
			tx.commit();
		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside IssueServiceImpl addIssue() :" + e.getMessage());
			return createFailedObject(issue_unable_to_add_message);

		} finally {
			if(session != null){
				session.close();
			}
		}

		logger.info("IssueServiceImpl addIssue() ends ");
		return createSuccessObject(issue_added_message);
	}

	private Issue setIssueValues(IssueModel issueModel, Session session, Issue issue) {

		Driver reportedBy = (Driver) session.get(Driver.class, issueModel.getReportedById());
		VehicleMaintainanceCategory vmc = (VehicleMaintainanceCategory) session.get(VehicleMaintainanceCategory.class, issueModel.getVmcId());
		Category unitType = (Category) session.get(Category.class, issueModel.getUnitTypeId());
		Type status = (Type) session.get(Type.class, issueModel.getStatusId());
		
		issue.setReportedBy(reportedBy);
		issue.setVmc(vmc);
		//issue.setUnitType(unitType);
		issue.setStatus(status);
		issue.setIssueName(issueModel.getTitle());
		issue.setUnitNo(issueModel.getUnitNo());
		return issue;
	}

	@Override
	public Object addPO(PurchaseOrderModel poModel) {

		logger.info("IssueServiceImpl addIssue() starts ");
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			List<PurchaseOrder> pos = setPoValues(poModel, session);
			poDao.addPurchaseOrder(pos, session);
			tx.commit();
		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside IssueServiceImpl addIssue() :" + e.getMessage());
			return createFailedObject(issue_unable_to_add_message);

		} finally {
			if(session != null){
				session.close();
			}
		}

		logger.info("IssueServiceImpl addIssue() ends ");
		return createSuccessObject(issue_added_message);
	}

	private List<PurchaseOrder> setPoValues(PurchaseOrderModel poModel, Session session) {
	
		List<PurchaseOrder> pos = null;
		List<Long> issueIds = poModel.getIssueIds();
		
		if(issueIds != null){
			
			pos = new ArrayList<PurchaseOrder>(); 
			Type status = (Type) session.get(Type.class, poModel.getStatusId());
			Type unitType = (Type) session.get(Type.class, poModel.getUnitTypeId());
			Category category = (Category) session.get(Category.class, poModel.getCategoryId());
			Vendor vendor = (Vendor) session.get(Vendor.class, poModel.getVendorId());
			
			for (Long issueId : issueIds) {
				PurchaseOrder po = new PurchaseOrder();
				Issue issue = (Issue) session.get(Issue.class, issueId);
				po.setCategory(category);
				po.setIssue(issue);
				po.setMessage(poModel.getMessage());
				po.setStatus(status);
				po.setUnitType(unitType);
				po.setVendor(vendor);
				
				if(status.getTypeName().equals("Invoiced")){
					po.setInvoiceNo(poModel.getInvoiceNo());
				}
				
				pos.add(po);
			}
		}
		
		return pos;
	}

	@Override
	public List<IssueModel> getCategoryAndUnitTypeIssues(Long categoryId, Long unitTypeId) {

		Session session = null;
		List<IssueModel> issues = new ArrayList<IssueModel>();
		try {
			session = sessionFactory.openSession();
			issues = issueService.getIssueforCategoryAndUnitType(categoryId, unitTypeId, session);
		} finally {
			if(session != null){
				session.close();
			}
		}
		
		return issues;
		
	}

	

}
