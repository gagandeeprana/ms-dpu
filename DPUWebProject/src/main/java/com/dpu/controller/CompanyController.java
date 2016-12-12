package com.dpu.controller;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.entity.Company;
import com.dpu.service.CompanyService;


@RestController
@RequestMapping(value = "company")
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		String json = null;
		try {
			List<Company> lstCompanies = companyService.getAll("");
			json = mapper.writeValueAsString(lstCompanies);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Company company) {
		String json = null;
		try {
			boolean result = companyService.add(company);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {	
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {

		String json = null;
		try {
			boolean result = companyService.delete(id);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id, @RequestBody Company company) {
		
		String json = null;
		try {
			boolean result = companyService.update(id, company);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {	
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("categoryId") int id) {
		String json = null;
		try {
			Company company= companyService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(company);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}
}
