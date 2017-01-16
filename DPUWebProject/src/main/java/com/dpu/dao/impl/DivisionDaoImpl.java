/**
 * 
 */
package com.dpu.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CategoryDao;
import com.dpu.dao.DivisionDao;
import com.dpu.entity.Category;
import com.dpu.entity.Division;

/**
 * @author jagvir
 *
 */
@Repository
public class DivisionDaoImpl extends GenericDaoImpl<Division> implements DivisionDao{

}