package com.dpu.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.TruckDao;
import com.dpu.entity.Truck;

/**
 * @author sumit
 *
 */

@Repository
@Transactional
public class TruckDaoImpl extends GenericDaoImpl<Truck> implements TruckDao {

}
