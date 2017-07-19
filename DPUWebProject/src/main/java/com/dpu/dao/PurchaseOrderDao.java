package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Issue;
import com.dpu.entity.PurchaseOrder;

public interface PurchaseOrderDao extends GenericDao<PurchaseOrder> {

	List<Issue> findAll(Session session);

	Issue findById(Long id, Session session);

	List<Object> getUnitNos(Long categoryId, Session session);

	List<Issue> getIssueByIssueName(Session session, String issueName);

	void saveIssue(Issue issue, Session session);

	void update(Issue issue, Session session);
}
