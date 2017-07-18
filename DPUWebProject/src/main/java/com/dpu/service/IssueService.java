package com.dpu.service;

import java.util.List;

import com.dpu.model.IssueModel;

public interface IssueService {
	Object update(Long id, IssueModel issueModel);

	Object delete(Long id);

	List<IssueModel> getAll();

	IssueModel getOpenAdd();

	IssueModel get(Long id);
	
	List<IssueModel> getSpecificData();

	IssueModel getUnitNo(Long categoryId);

	Object addIssue(IssueModel issueModel);

	List<IssueModel> getIssueByIssueName(String issueName);

}
