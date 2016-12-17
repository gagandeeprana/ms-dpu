/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Company;
import com.dpu.entity.Shipper;

/**
 * @author jagvir
 *
 */
public interface ShipperService {

	boolean add(Shipper company);

	boolean update(int id, Shipper shipper);

	boolean delete(int id);

	List<Shipper> getAll(String name);

	Shipper get(int id);
}
