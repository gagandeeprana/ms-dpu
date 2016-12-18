package com.dpu.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.DriverDao;
import com.dpu.entity.DriverEntity;

/**
 * @author sumit
 *
 */

@Repository
@Transactional
public class DriverDaoImpl extends GenericDaoImpl<DriverEntity> implements DriverDao {

}
