package com.dpu.controller;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.constants.Iconstants;
import com.dpu.entity.Company;
import com.dpu.model.Failed;
import com.dpu.service.CompanyService;
import com.dpu.util.MessageProperties;
import com.dpu.model.Success;

@RestController
@RequestMapping(value = "company")
public class CompanyController extends MessageProperties {

	@Autowired
	CompanyService companyService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		String json = new String();
		try {
			List<Company> lstCompanies = companyService.getAll();
			if (lstCompanies != null) {
				json = mapper.writeValueAsString(lstCompanies);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Company company) {
		Object obj = null;
		try {
			Company response = companyService.add(company);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(companyAddedCode),
						companyAddedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(companyUnableToAddCode),
						companyUnableToAddMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") int id) {

		Object obj = null;
		boolean result = false;

		try {

			Company company = companyService.get(id);
			if (company != null) {
				result = companyService.delete(company);
			}
			if (result) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(companyDeletedCode),
						companyDeletedMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(companyUnableToDeleteCode),
						companyUnableToDeleteMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Company company) {

		Object obj = null;
		try {
			company.setCompanyId(id);
			Company response = companyService.update(company);
			if (response != null) {
				obj = new ResponseEntity<Object>(new Success(
						Integer.parseInt(companyUpdateCode),
						companyUpdateMessage, Iconstants.SUCCESS),
						HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(
						Integer.parseInt(companyUnableToUpdateCode),
						companyUnableToUpdateMessage, Iconstants.ERROR),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("id") int id) {
		String json = new String();
		try {
			Company company = companyService.get(id);
			if (company != null) {
				json = mapper.writeValueAsString(company);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}
}
