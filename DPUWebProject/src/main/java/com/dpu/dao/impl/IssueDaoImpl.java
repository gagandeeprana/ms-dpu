package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.IssueDao;
import com.dpu.entity.Category;
import com.dpu.entity.Issue;
import com.dpu.entity.Type;

@Repository
public class IssueDaoImpl extends GenericDaoImpl<Issue> implements IssueDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> findAll(Session session) {
		
		StringBuilder sb = new StringBuilder(" select i from Issue i join fetch i.vmc join fetch i.unitType join fetch i.reportedBy join fetch i.status join fetch i.category ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public Issue findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder(" select i from Issue i join fetch i.vmc join fetch i.unitType join fetch i.reportedBy join fetch i.status join fetch i.category where i.id = :issueId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("issueId", id);
		return (Issue) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getUnitNos(Long categoryId, Long unitTypeId, Session session) {
		
		Type unitType = (Type) session.get(Type.class, unitTypeId);
		StringBuilder sb = new StringBuilder(" ");
		if(unitType.getTypeName().equals("Truck")){
			sb.append(" SELECT unit_no FROM `newtruckmaster` truck WHERE category_id = :categoryId ");
		} else{
			sb.append("  SELECT unit_no FROM `trailer` WHERE category_id = :categoryId ");
		}
		Query query = session.createSQLQuery(sb.toString());
		query.setParameter("categoryId", categoryId);
		return query.list();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> getIssueByIssueName(Session session, String issueName) {
		StringBuilder sb = new StringBuilder(" select i from Issue i join fetch i.vmc join fetch i.unitType join fetch i.reportedBy join fetch i.status join fetch i.category where i.issueName like :issueName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("issueName", "%"+issueName+"%");
		return query.list();
	}

	@Override
	public void saveIssue(Issue issue, Session session) {
		session.save(issue);
	}

	@Override
	public void update(Issue issue, Session session) {
		session.update(issue);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> findAllActiveAndIncompleteIssues(Session session) {
		StringBuilder sb = new StringBuilder(" select i from Issue i join fetch i.vmc join fetch i.unitType join fetch i.reportedBy join fetch i.status where i.status.typeId = 103 or i.status.typeId = 105");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> issueforCategoryAndUnitType(Long categoryId, Long unitTypeId, Session session) {
		Type unitType = (Type) session.get(Type.class, unitTypeId);
		Category category = (Category) session.get(Category.class, categoryId);
		StringBuilder sb = new StringBuilder(" ");
		sb.append(" from Issue i join fetch i.vmc join fetch i.unitType join fetch i.reportedBy join fetch i.status ")
		.append(" join fetch i.category ")
		.append("  where i.category =:category and i.unitType =:unitType and i.status.typeId in (103, 105, 107) ");
		
		Query query = session.createQuery(sb.toString());
		query.setParameter("category", category);
		query.setParameter("unitType", unitType);
		
		return query.list();
	}

}
