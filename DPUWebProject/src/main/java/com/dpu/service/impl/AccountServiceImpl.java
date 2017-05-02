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

import com.dpu.dao.AccountDao;
import com.dpu.entity.Account;
import com.dpu.entity.TaxCode;
import com.dpu.model.AccountModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TaxCodeModel;
import com.dpu.service.AccountService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Component
public class AccountServiceImpl implements AccountService {

	Logger logger = Logger.getLogger(AccountServiceImpl.class);

	@Autowired
	AccountDao accountDao;

	@Autowired
	StatusService statusService;
	
	@Autowired
	TypeService typeService;

	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${taxcode_added_message}")
	private String taxcode_added_message;
	
	@Value("${taxcode_unable_to_add_message}")
	private String taxcode_unable_to_add_message;
	
	@Value("${taxcode_deleted_message}")
	private String taxcode_deleted_message;
	
	@Value("${taxcode_unable_to_delete_message}")
	private String taxcode_unable_to_delete_message;
	
	@Value("${taxcode_updated_message}")
	private String taxcode_updated_message;
	
	@Value("${taxcode_unable_to_update_message}")
	private String taxcode_unable_to_update_message;
	
	@Value("${taxcode_dependent_message}")
	private String taxcode_dependent_message;
	
	@Override
	public List<AccountModel> getAll() {
		
		logger.info("TaxCodeServiceImpl getAll() starts ");
		Session session = null;
		List<AccountModel> accountModelList = new ArrayList<AccountModel>();

		try {
			session = sessionFactory.openSession();
			List<Account> accounts = accountDao.findAll(session);

			if (accounts != null && !accounts.isEmpty()) {
				for (Account account : accounts) {
					AccountModel accountModel = new AccountModel();
					BeanUtils.copyProperties(account, accountModel);
					
					if(account.getAccountType() != null){
						accountModel.setAccountTypeName(account.getAccountType().getTypeName());
					}
					
					if(account.getCurrency() != null){
						accountModel.setCurrencyName(account.getCurrency().getTypeName());
					}
					
					if(account.getParentAccount() != null){
						accountModel.setParentAccountName(account.getParentAccount().getAccountName());
					}
					
					accountModelList.add(accountModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	
		logger.info("TaxCodeServiceImpl getAll() ends ");
		return accountModelList;
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
	public Object addAccount(AccountModel accountModel) {

		logger.info("TaxCodeServiceImpl addTaxCode() starts ");
		Account account = null;
		Session session = null;
		Transaction tx = null;
		try {
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			account = setAccountValues(accountModel);
			session.save(account);

		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside TaxCodeServiceImpl addTaxCode() :"+ e.getMessage());
			return createFailedObject(taxcode_unable_to_add_message);

		} finally{
			if(tx != null){
				tx.commit();
			} 
			if(session !=null){
				session.close();
			}
		}
		
		logger.info("TaxCodeServiceImpl addTaxCode() ends ");
		return createSuccessObject(taxcode_added_message);
	}

	private Account setAccountValues(AccountModel accountModel) {

		Account account = new Account();
		BeanUtils.copyProperties(accountModel, account);
		
		if(accountModel.getCurrencyId() != null){
			account.setCurrency(typeService.get(accountModel.getCurrencyId()));
		}
		
		if(accountModel.getAccountTypeId() != null){
			account.setAccountType(typeService.get(accountModel.getAccountTypeId()));
		}
		
		if(accountModel.getParentAccountId() != null){
			account.setParentAccount(getParentAccount(accountModel.getParentAccountId()));
		}
		
		return account;
	}

	private Account getParentAccount(Long parentAccountId) {

		Session session = null;
		Account account = null;
		try{
			session = sessionFactory.openSession();
			account = (Account) session.get(Account.class, parentAccountId);
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return account;
	}

	@Override
	public Object update(Long id, AccountModel accountModel) {

		logger.info("TaxCodeServiceImpl update() starts.");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Account account = (Account) session.get(Account.class, id);
			
			if (account != null) {
				String[] ignorePro ={"accountId"};
				BeanUtils.copyProperties(accountModel, account, ignorePro);
				if(accountModel.getCurrencyId() != null){
					account.setCurrency(typeService.get(accountModel.getCurrencyId()));
				}
				
				if(accountModel.getAccountTypeId() != null){
					account.setAccountType(typeService.get(accountModel.getAccountTypeId()));
				}
				
				if(accountModel.getParentAccountId() != null){
					account.setParentAccount(getParentAccount(accountModel.getParentAccountId()));
				}
				session.update(account);
				tx.commit();
			} else{
				return createFailedObject(taxcode_unable_to_update_message);
			}

		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside TaxCodeServiceImpl update() :"+ e.getMessage());
			return createFailedObject(taxcode_unable_to_update_message);
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		logger.info("TaxCodeServiceImpl update() ends.");
		return createSuccessObject(taxcode_updated_message);
	}

	@Override
	public Object delete(Long id) {
		
		logger.info("TaxCodeServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Account account = (Account) session.get(Account.class, id);
			if(account != null){
				session.delete(account);
				tx.commit();
			} else{
				return createFailedObject(taxcode_unable_to_delete_message);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside TaxCodeServiceImpl delete() : " + e.getMessage());
			if(tx != null){
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				return createFailedObject(taxcode_dependent_message);
			}
			return createFailedObject(taxcode_unable_to_delete_message);
		} finally{
			/*if(tx != null){
				tx.commit();
			}*/
			if(session != null){
				session.close();
			}
		}
		
		logger.info("TaxCodeServiceImpl delete() ends.");
		return createSuccessObject(taxcode_deleted_message);
	}



	@Override
	public AccountModel get(Long id) {
		
		logger.info("TaxCodeServiceImpl get() starts.");
		Session session = null;
		AccountModel accountModel = new AccountModel();

		try {

			session = sessionFactory.openSession();
			Account account = accountDao.findById(id, session);

			if (account != null) {
				BeanUtils.copyProperties(account, accountModel);
				if(account.getAccountType() != null){
					accountModel.setAccountTypeName(account.getAccountType().getTypeName());
					accountModel.setAccountTypeId(account.getAccountType().getTypeId());
				}
				
				if(account.getCurrency() != null){
					accountModel.setCurrencyName(account.getCurrency().getTypeName());
					accountModel.setCurrencyId(account.getCurrency().getTypeId());
				}
				
				if(account.getParentAccount() != null){
					accountModel.setParentAccountName(account.getParentAccount().getAccountName());
					accountModel.setParentAccountId(account.getParentAccount().getAccountId());
				}
				
				accountModel.setAccountTypeList(typeService.getAll(22l));
				accountModel.setCurrencyList(typeService.getAll(21l));
				//accountModel.set
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("TaxCodeServiceImpl get() ends.");
		return accountModel;
	}

	@Override
	public AccountModel getOpenAdd() {
		
		logger.info("HandlingServiceImpl getOpenAdd() starts ");
		AccountModel accountModel = new AccountModel();

		accountModel.setAccountTypeList(typeService.getAll(22l));
		accountModel.setCurrencyList(typeService.getAll(21l));
		
		accountModel.setParentAccountList(getSpecificData());
		logger.info("HandlingServiceImpl getOpenAdd() ends ");
		
		return accountModel;
	}

	@Override
	public List<AccountModel> getAccountByAccountName(String accountName) {
		
		logger.info("TaxCodeServiceImpl getTaxCodeByTaxCodeName() starts, taxCodeName :"+accountName);
		Session session = null;
		List<AccountModel> taxCodeList = new ArrayList<AccountModel>();

		try {
			session = sessionFactory.openSession();
			List<Account> accounts = accountDao.getAccountByAccountName(session, accountName);
			if (accounts != null && !accounts.isEmpty()) {
				for (Account account : accounts) {
					AccountModel accountModel = new AccountModel();
					BeanUtils.copyProperties(account, accountModel);
					if(account.getAccountType() != null){
						accountModel.setAccountTypeName(account.getAccountType().getTypeName());
					}
					
					if(account.getCurrency() != null){
						accountModel.setCurrencyName(account.getCurrency().getTypeName());
					}
					
					if(account.getParentAccount() != null){
						accountModel.setParentAccountName(account.getParentAccount().getAccountName());
					}
					taxCodeList.add(accountModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("TaxCodeServiceImpl getTaxCodeByTaxCodeName() ends, taxCodeName :"+accountName);
		return taxCodeList;
	}

	@Override
	public List<AccountModel> getSpecificData() {
		
		List<Object[]> accountData = accountDao.getSpecificData("Account","accountId", "accountName");

		List<AccountModel> accounts = new ArrayList<AccountModel>();
		if (accountData != null && !accountData.isEmpty()) {
			for (Object[] row : accountData) {
				AccountModel accountModel = new AccountModel();
				accountModel.setAccountId((Long) row[0]);
				accountModel.setAccountName(String.valueOf(row[1]));
				accounts.add(accountModel);
			}
		}

		return accounts;
	}

}
