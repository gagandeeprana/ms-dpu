/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Category;
import com.dpu.entity.Service;
import com.dpu.model.DPUService;

/**
 * @author jagvir
 *
 */
public interface ServiceService {
	Service add(Service service);

	Service update(int id, Service service);

	boolean delete(Service service);

	List<DPUService> getAll();

	Service get(int id);
}
