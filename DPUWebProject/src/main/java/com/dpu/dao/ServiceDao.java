
package com.dpu.dao;

import java.util.List;

import com.dpu.entity.Service;

public interface ServiceDao extends GenericDao<Service>{
	List<Object[]> getServiceData();
}
