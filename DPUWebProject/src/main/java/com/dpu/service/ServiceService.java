
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Service;
import com.dpu.model.DPUService;

/**
 * @author jagvir
 *
 */
public interface ServiceService {
	List<DPUService> add(DPUService dpuService);

	boolean delete(Service service);

	List<DPUService> getAll();

	DPUService get(Long id);

	DPUService getOpenAdd();

	List<DPUService> update(Long id, DPUService dpuService);
}
