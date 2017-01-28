/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Shipper;
import com.dpu.model.ShipperResponse;

/**
 * @author jagvir
 *
 */
public interface ShipperService {

	Shipper add(Shipper shipper);

	Shipper update(Shipper shipper);

	boolean delete(Shipper shipper);

	List<ShipperResponse> getAll();

	Shipper get(int id);

	ShipperResponse getMasterData();
}
