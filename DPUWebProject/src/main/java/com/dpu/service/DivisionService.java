/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Division;

/**
 * @author jagvir
 *
 */
public interface DivisionService {

	boolean add(Division division);

	Division update(int id, Division division);

	boolean delete(Division division);

	List<Division> getAll();

	Division get(int id);
}
