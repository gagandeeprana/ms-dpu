package com.dpu.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.dao.CategoryDao;
import com.dpu.dao.CompanyAdditionalContactsDao;
import com.dpu.dao.CompanyBillingLocationDao;
import com.dpu.dao.CompanyDao;
import com.dpu.dao.DivisionDao;
import com.dpu.dao.SaleDao;
import com.dpu.entity.Company;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.entity.Status;
import com.dpu.model.AdditionalContacts;
import com.dpu.model.BillingLocation;
import com.dpu.model.CategoryReq;
import com.dpu.model.CompanyResponse;
import com.dpu.model.DivisionReq;
import com.dpu.model.Failed;
import com.dpu.model.SaleReq;
import com.dpu.model.Success;
import com.dpu.service.CategoryService;
import com.dpu.service.CompanyAdditionalContactsService;
import com.dpu.service.CompanyBillingLocationService;
import com.dpu.service.CompanyService;
import com.dpu.service.DivisionService;
import com.dpu.service.SaleService;
import com.dpu.service.StatusService;

@Component
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SaleService saleService;

	@Autowired
	private DivisionService divisionService;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private DivisionDao divisionDao;

	@Autowired
	private SaleDao saleDao;

	@Autowired
	CompanyBillingLocationDao companyBillingLocationDao;

	@Autowired
	CompanyAdditionalContactsDao companyAdditionalContactsDao;

	@Autowired
	CompanyBillingLocationService companyBillingLocationService;

	@Autowired
	CompanyAdditionalContactsService companyAdditionalContactsService;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	StatusService statusService;

	Logger logger = Logger.getLogger(CompanyServiceImpl.class);

	@Value("${company_added_message}")
	private String company_added_message;

	@Value("${company_unable_to_add_message}")
	private String company_unable_to_add_message;

	@Value("${company_deleted_message}")
	private String company_deleted_message;

	@Value("${company_unable_to_delete_message}")
	private String company_unable_to_delete_message;

	@Value("${company_updated_message}")
	private String company_updated_message;

	@Value("${company_unable_to_update_message}")
	private String company_unable_to_update_message;

	@Value("${company_dependent_message}")
	private String company_dependent_message;

	@Override
	public Object addCompanyData(CompanyResponse companyResponse) {

		logger.info("Inside CompanyServiceImpl addCompanyData() starts");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Company company = setCompanyValues(companyResponse);
			company = companyDao.insertCompanyData(company, session);

			List<BillingLocation> billingLocations = companyResponse
					.getBillingLocations();
			if (billingLocations != null && !billingLocations.isEmpty()) {
				for (BillingLocation billingLocation : billingLocations) {
					CompanyBillingLocation comBillingLocation = setBillingData(
							billingLocation, company);
					companyBillingLocationDao.insertBillingLocation(
							comBillingLocation, session);
				}
			}

			List<AdditionalContacts> additionalContacts = companyResponse
					.getAdditionalContacts();
			if (additionalContacts != null && !additionalContacts.isEmpty()) {
				for (AdditionalContacts additionalContact : additionalContacts) {
					CompanyAdditionalContacts comAdditionalContact = setAdditionalContactData(
							additionalContact, company);
					companyAdditionalContactsDao.insertAdditionalContacts(
							comAdditionalContact, session);
				}
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			logger.error("Exception inside CompanyServiceImpl addCompanyData() :"
					+ e.getMessage());
			return createFailedObject(company_unable_to_add_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CompanyServiceImpl addCompanyData() ends");
		return createSuccessObject(company_added_message);
	}

	private Object createFailedObject(String errorMessage) {

		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	private Object createSuccessObject(String message) {

		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAll());
		return success;
	}

	private CompanyAdditionalContacts setAdditionalContactData(
			AdditionalContacts additionalContact, Company company) {

		CompanyAdditionalContacts companyAdditionalContact = new CompanyAdditionalContacts();
		companyAdditionalContact.setCellular(additionalContact.getCellular());
		companyAdditionalContact.setCompany(company);
		companyAdditionalContact.setCustomerName(additionalContact
				.getCustomerName());
		companyAdditionalContact.setEmail(additionalContact.getEmail());
		companyAdditionalContact.setExt(additionalContact.getExt());
		companyAdditionalContact.setFax(additionalContact.getFax());
		companyAdditionalContact.setPhone(additionalContact.getPhone());
		companyAdditionalContact.setPosition(additionalContact.getPosition());
		companyAdditionalContact.setPrefix(additionalContact.getPrefix());
		companyAdditionalContact.setStatus(statusService.get(additionalContact
				.getStatusId()));
		return companyAdditionalContact;
	}

	private CompanyBillingLocation setBillingData(
			BillingLocation billingLocation, Company company) {

		CompanyBillingLocation comBillingLocation = new CompanyBillingLocation();
		comBillingLocation.setAddress(billingLocation.getAddress());
		comBillingLocation.setArCDN(billingLocation.getArCDN());
		comBillingLocation.setArUS(billingLocation.getArUS());
		comBillingLocation.setCellular(billingLocation.getCellular());
		comBillingLocation.setCity(billingLocation.getCity());
		comBillingLocation.setCompany(company);
		comBillingLocation.setContact(billingLocation.getContact());
		comBillingLocation.setEmail(billingLocation.getEmail());
		comBillingLocation.setExt(billingLocation.getExt());
		comBillingLocation.setFax(billingLocation.getFax());
		comBillingLocation.setName(billingLocation.getName());
		comBillingLocation.setPhone(billingLocation.getPhone());
		comBillingLocation.setPosition(billingLocation.getPosition());
		comBillingLocation.setPrefix(billingLocation.getPrefix());
		comBillingLocation.setProvinceState(billingLocation.getProvinceState());
		comBillingLocation.setStatus(statusService.get(billingLocation
				.getStatusId()));
		comBillingLocation.setTollfree(billingLocation.getTollfree());
		comBillingLocation.setUnitNo(billingLocation.getUnitNo());
		comBillingLocation.setZip(billingLocation.getZip());
		return comBillingLocation;
	}

	private Company setCompanyValues(CompanyResponse companyResponse) {

		Company company = new Company();
		company.setName(companyResponse.getName());
		company.setContact(companyResponse.getContact());
		company.setAddress(companyResponse.getAddress());
		company.setPosition(companyResponse.getPosition());
		company.setUnitNo(companyResponse.getUnitNo());
		company.setPhone(companyResponse.getPhone());
		company.setExt(companyResponse.getExt());
		company.setCity(companyResponse.getCity());
		company.setFax(companyResponse.getFax());
		company.setCompanyPrefix(companyResponse.getCompanyPrefix());
		company.setProvinceState(companyResponse.getProvinceState());
		company.setZip(companyResponse.getZip());
		company.setAfterHours(companyResponse.getAfterHours());
		company.setEmail(companyResponse.getEmail());
		company.setTollfree(companyResponse.getTollfree());
		company.setWebsite(companyResponse.getWebsite());
		company.setCellular(companyResponse.getCellular());
		company.setPager(companyResponse.getPager());
		company.setCategory(categoryDao.findById(companyResponse
				.getCategoryId()));
		company.setDivision(divisionDao.findById(companyResponse
				.getDivisionId()));
		company.setSale(saleDao.findById(companyResponse.getSaleId()));
		return company;
	}

	@Override
	public Company update(Company company) {

		return companyDao.update(company);
	}

	@Override
	public Object delete(Long companyId) {

		logger.info("Inside CompanyServiceImpl addCompanyData() starts");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Company company = companyDao.findById(companyId, session);

			if (company != null) {

				List<CompanyBillingLocation> listCompanyBillingLocations = companyBillingLocationService
						.getAll(companyId, session);
				if (listCompanyBillingLocations != null
						&& !listCompanyBillingLocations.isEmpty()) {
					for (CompanyBillingLocation companyBillingLocation : listCompanyBillingLocations) {
						companyBillingLocationDao.deleteBillingLocation(
								companyBillingLocation, session);
					}
				}

				List<CompanyAdditionalContacts> comAddContacts = companyAdditionalContactsService
						.getAll(companyId, session);
				if (comAddContacts != null && !comAddContacts.isEmpty()) {
					for (CompanyAdditionalContacts companyAdditionalContacts : comAddContacts) {
						companyAdditionalContactsDao.deleteAdditionalContact(
								companyAdditionalContacts, session);
					}
				}
				companyDao.deleteCompany(company, session);
			} else {
				return createFailedObject(company_unable_to_delete_message);
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(company_dependent_message);
			}
			return createFailedObject(company_unable_to_delete_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}
		return createSuccessObject(company_deleted_message);
	}

	@Override
	public List<CompanyResponse> getAll() {

		List<Company> companies = companyDao.findAll();
		List<CompanyResponse> returnResponse = new ArrayList<CompanyResponse>();

		if (companies != null && !companies.isEmpty()) {
			for (Company company : companies) {
				CompanyResponse response = new CompanyResponse();
				setCompanyData(company, response);
				returnResponse.add(response);
			}

		}

		return returnResponse;
	}

	@Override
	public CompanyResponse get(Long id) {

		Session session = null;
		CompanyResponse response = new CompanyResponse();
		try {
			session = sessionFactory.openSession();
			Company company = companyDao.findById(id, session);

			if (company != null) {
				setCompanyData(company, response);
				// BeanUtils.copyProperties(response, company);
				List<CompanyBillingLocation> listCompanyBillingLocations = companyBillingLocationService
						.getAll(id, session);

				if (listCompanyBillingLocations != null
						&& !listCompanyBillingLocations.isEmpty()) {
					List<BillingLocation> billingLocations = new ArrayList<BillingLocation>();
					for (CompanyBillingLocation companyBillingLocation : listCompanyBillingLocations) {
						BillingLocation location = new BillingLocation();
						try {
							BeanUtils.copyProperties(location,
									companyBillingLocation);
							location.setStatusId(companyBillingLocation
									.getStatus().getId());
						} catch (IllegalAccessException
								| InvocationTargetException e) {
							e.printStackTrace();
						}
						billingLocations.add(location);
					}
					response.setBillingLocations(billingLocations);
				}

				List<CompanyAdditionalContacts> comAddContacts = companyAdditionalContactsService
						.getAll(id, session);

				if (comAddContacts != null && !comAddContacts.isEmpty()) {
					List<AdditionalContacts> addContacts = new ArrayList<AdditionalContacts>();
					for (CompanyAdditionalContacts companyAdditionalContacts : comAddContacts) {
						AdditionalContacts addContact = new AdditionalContacts();
						try {
							BeanUtils.copyProperties(addContact,
									companyAdditionalContacts);
							addContact.setStatusId(companyAdditionalContacts
									.getStatus().getId());
						} catch (IllegalAccessException
								| InvocationTargetException e) {
							e.printStackTrace();
						}

						addContacts.add(addContact);
					}

					response.setAdditionalContacts(addContacts);
				}

				List<Status> statusList = statusService.getAll();
				response.setStatusList(statusList);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return response;
	}

	private void setCompanyData(Company companyObj, CompanyResponse response) {

		response.setCompanyId(companyObj.getCompanyId());
		response.setAddress(companyObj.getAddress());
		response.setAfterHours(companyObj.getAfterHours());
		response.setCellular(companyObj.getCellular());
		response.setCity(companyObj.getCity());
		response.setCompanyPrefix(companyObj.getCompanyPrefix());
		response.setContact(companyObj.getContact());
		response.setCustomerNotes(companyObj.getCustomerNotes());
		response.setEmail(companyObj.getEmail());
		response.setExt(companyObj.getExt());
		response.setFax(companyObj.getFax());
		response.setName(companyObj.getName());
		response.setPager(companyObj.getPager());
		response.setPhone(companyObj.getPhone());
		response.setPosition(companyObj.getPosition());
		response.setProvinceState(companyObj.getProvinceState());
		response.setTollfree(companyObj.getTollfree());
		response.setZip(companyObj.getZip());
		response.setUnitNo(companyObj.getUnitNo());
		response.setWebsite(companyObj.getWebsite());
		if (companyObj.getCategory().getName() != null)
			response.setCategoryName(companyObj.getCategory().getName());
		if (companyObj.getDivision().getDivisionName() != null)
			response.setDivisionName(companyObj.getDivision().getDivisionName());
		if (companyObj.getSale().getName() != null)
			response.setSaleName(companyObj.getSale().getName());

	}

	@Override
	public List<CompanyResponse> getCompanyData() {

		List<Object[]> companyData = companyDao.getCompanyData();
		List<CompanyResponse> returnRes = new ArrayList<CompanyResponse>();

		if (companyData != null && !companyData.isEmpty()) {
			for (Object[] row : companyData) {
				CompanyResponse res = new CompanyResponse();
				res.setCompanyId(Long.valueOf(String.valueOf(row[0])));
				res.setName(String.valueOf(row[1]));
				returnRes.add(res);
			}
		}

		return returnRes;
	}

	@Override
	public Object update(Long id, CompanyResponse companyResponse) {

		Company company = companyDao.findById(id);
		Session session = null;
		Transaction tx = null;

		try {
			if (company != null) {
				session = sessionFactory.openSession();
				tx = session.beginTransaction();

				companyDao.updateData(company, companyResponse, session);
			} else {
				return createFailedObject(company_unable_to_update_message);
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			return createFailedObject(company_unable_to_update_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		return createSuccessObject(company_updated_message);
	}

	@Override
	public CompanyResponse getOpenAdd() {

		CompanyResponse companyResponse = new CompanyResponse();

		List<Status> statusList = statusService.getAll();
		companyResponse.setStatusList(statusList);

		List<CategoryReq> categoryList = categoryService.getAll();
		companyResponse.setCategoryList(categoryList);

		List<DivisionReq> divisionList = divisionService.getAll("");
		companyResponse.setDivisionList(divisionList);

		List<SaleReq> saleList = saleService.getAll();
		companyResponse.setSaleList(saleList);

		return companyResponse;
	}

	@Override
	public CompanyResponse getCompanyBillingLocationAndContacts(Long companyId) {

		CompanyResponse companyResponse = new CompanyResponse();
		Session session = null;

		try {
			Company company = companyDao.findById(companyId);
			if (company != null) {
				session = sessionFactory.openSession();
				List<Object[]> billingLocationData = companyDao
						.getBillingLocations(company.getCompanyId(), session);
				if (billingLocationData != null
						&& !billingLocationData.isEmpty()) {
					List<BillingLocation> billingLocations = new ArrayList<BillingLocation>();
					for (Object[] row : billingLocationData) {
						BillingLocation billingLocation = new BillingLocation();
						billingLocation.setBillingLocationId(Long
								.parseLong(String.valueOf(row[0])));
						billingLocation.setName(String.valueOf(row[1]));
						billingLocations.add(billingLocation);
					}

					companyResponse.setBillingLocations(billingLocations);
				}

				List<Object[]> additionalContacts = companyDao
						.getAdditionalContacts(company.getCompanyId(), session);
				if (additionalContacts != null && !additionalContacts.isEmpty()) {
					List<AdditionalContacts> additionalContactList = new ArrayList<AdditionalContacts>();
					for (Object[] row : additionalContacts) {
						AdditionalContacts additionalContact = new AdditionalContacts();
						additionalContact.setAdditionalContactId(Long
								.parseLong(String.valueOf(row[0])));
						additionalContact.setCustomerName(String
								.valueOf(row[1]));
						additionalContactList.add(additionalContact);
					}

					companyResponse
							.setAdditionalContacts(additionalContactList);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return companyResponse;
	}

	@Override
	public List<CompanyResponse> getCompanyByCompanyName(String companyName) {

		Session session = null;
		List<CompanyResponse> response = new ArrayList<CompanyResponse>();
		try {

			session = sessionFactory.openSession();
			List<Company> companyList = companyDao.getCompaniesByCompanyName(
					companyName, session);
			if (companyList != null && !companyList.isEmpty()) {
				for (Company company : companyList) {
					CompanyResponse companyResponse = new CompanyResponse();
					org.springframework.beans.BeanUtils.copyProperties(company,
							companyResponse);
					response.add(companyResponse);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return response;
	}
}
