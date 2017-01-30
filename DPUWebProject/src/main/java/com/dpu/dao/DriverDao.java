package com.dpu.dao;

import java.util.List;

import com.dpu.entity.Driver;

/**
 * @author sumit
 *
 */

public interface DriverDao extends GenericDao<Driver> {

	List<Driver> searchDriverByDriverCodeOrName(String driverCodeOrName);

}
