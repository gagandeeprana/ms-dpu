
package com.dpu.service;

import java.util.List;

import com.dpu.model.DPUService;

/**
 * @author jagvir
 *
 */
public interface ServiceService {
	List<DPUService> add(DPUService dpuService);

	List<DPUService> delete(Long id);

	List<DPUService> getAll();

	DPUService get(Long id);

	DPUService getOpenAdd();

	List<DPUService> update(Long id, DPUService dpuService);

	List<DPUService> getServiceByServiceName(String serviceName);
}
