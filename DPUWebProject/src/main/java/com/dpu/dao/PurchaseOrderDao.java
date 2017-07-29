package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.PurchaseOrder;
import com.dpu.entity.PurchaseOrderIssue;
import com.dpu.entity.Type;

public interface PurchaseOrderDao extends GenericDao<PurchaseOrder> {

	List<PurchaseOrder> findAll(Session session);

	PurchaseOrder findById(Long id, Session session);

	void addPurchaseOrder(PurchaseOrder po, List<PurchaseOrderIssue> poIssues, Type assignStatus, Session session);

	Long getMaxPoNO(Session session);

	void update(PurchaseOrder po, List<PurchaseOrderIssue> poIssues, Type assignStatus, Type openStatus, Session session);

	void updateStatus(PurchaseOrder po, Type status, Session session);

}