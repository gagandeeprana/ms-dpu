/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Company;
import com.dpu.entity.Division;

/**
 * @author jagvir
 *
 */
public interface DivisionService {

	boolean add(Division division);

	boolean update(int id, Division division);

	boolean delete(int id);

	List<Division> getAll(String name);

	Division get(int id);

}
