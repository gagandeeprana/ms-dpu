/**
 * 
 */
package com.dpu.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.DivisionDao;
import com.dpu.entity.Category;
import com.dpu.entity.Division;
import com.dpu.service.DivisionService;

/**
 * @author jagvir
 *
 */
@Component
public class DivisionServiceImpl implements DivisionService {
	
	Logger logger = Logger.getLogger(DivisionServiceImpl.class);

	@Autowired
	DivisionDao divisionDao;

	@Override
	public boolean add(Division division) {
		logger.info("[addCategory]:Service:  Enter");

		boolean returnValue = false;
		try {

			// truck.setCreated("sumit");
			// truck.setCreatedOn(new Date());
			//
			// truck.setModifiedBy("sumit");
			// truck.setModifiedOn(new Date());

			Division divisionn = divisionDao.save(division);
			division.setCreatedOn(new Date());
			System.out.println("[addCategory]category Id :" + divisionn.getDivisionId());
			returnValue = true;
			return returnValue;

		} catch (Exception e) {
			logger.info("[addCategory]:Exception:    : ", e);
			System.out.println(e);
			return returnValue;
		} finally {
			logger.info("[addCategory]:Service:  returnValue : " + returnValue);
		}
	}

	@Override
	public Division update(int id, Division division) {
		return divisionDao.update(division);
	}

	@Override
	public boolean delete(Division division) {
		boolean result = false;
		try {
			divisionDao.delete(division);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<Division> getAll() {
		return divisionDao.findAll();
	}

	@Override
	public Division get(int id) {
		return divisionDao.findById(id);
	}

}
