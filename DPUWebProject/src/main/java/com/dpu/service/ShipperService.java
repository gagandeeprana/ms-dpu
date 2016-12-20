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

	Shipper add(Shipper shipper);

	Shipper update(Shipper shipper);

	boolean delete(Shipper shipper);

	List<Shipper> getAll();

	Shipper get(int id);
}
