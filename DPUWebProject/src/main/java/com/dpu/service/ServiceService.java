/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Category;
import com.dpu.entity.Service;

/**
 * @author jagvir
 *
 */
public interface ServiceService {
	Service add(Service service);

	Service update(int id, Service service);

	boolean delete(Service service);

	List<Service> getAll();

	Service get(int id);
}
