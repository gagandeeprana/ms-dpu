package com.dpu.service;

import java.util.List;

import com.dpu.model.IssueModel;
import com.dpu.model.PurchaseOrderModel;

public interface PurchaseOrderService {
	Object update(Long id, IssueModel issueModel);

	Object delete(Long id);

	List<IssueModel> getAll();

	PurchaseOrderModel getOpenAdd();

	IssueModel get(Long id);
	
	List<IssueModel> getSpecificData();

	IssueModel getUnitNo(Long categoryId);

	Object addIssue(IssueModel issueModel);

	List<IssueModel> getIssueByIssueName(String issueName);

	Object addPO(PurchaseOrderModel poModel);

}
