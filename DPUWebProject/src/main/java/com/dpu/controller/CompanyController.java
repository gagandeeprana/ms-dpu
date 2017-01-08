package com.dpu.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
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
import com.dpu.model.CompanyResponse;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CompanyService;
import com.dpu.util.MessageProperties;

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
				List<CompanyResponse> responses = new ArrayList<CompanyResponse>();
				for(Company company : lstCompanies) {
					CompanyResponse response = new CompanyResponse();
					BeanUtils.copyProperties(response, company);
					responses.add(response);
				}
				if(responses != null && !responses.isEmpty()) {
					json = mapper.writeValueAsString(responses);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CompanyResponse companyResponse) {
		Object obj = null;
		try {
			System.out.println(companyResponse + "         " + new ObjectMapper().writeValueAsString(companyResponse));
			Company company = setCompanyValues(companyResponse);
			System.out.println(new ObjectMapper().writeValueAsString(company));
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

	private Company setCompanyValues(CompanyResponse companyResponse) {
		Company company = new Company();
		company.setName(companyResponse.getName());
		company.setContact(companyResponse.getContact());
		company.setAddress(companyResponse.getAddress());
		company.setPosition(companyResponse.getPosition());
		company.setUnitNo(companyResponse.getUnitNo());
		company.setPhone(companyResponse.getPhone());
		company.setExt(companyResponse.getExt());
		company.setCity(companyResponse.getCity());
		company.setFax(companyResponse.getFax());
		company.setCompanyPrefix(companyResponse.getCompanyPrefix());
		company.setProvinceState(companyResponse.getProvinceState());
		company.setZip(companyResponse.getZip());
		company.setAfterHours(companyResponse.getAfterHours());
		company.setEmail(companyResponse.getEmail());
		company.setTollfree(companyResponse.getTollfree());
		company.setWebsite(companyResponse.getWebsite());
		company.setCellular(companyResponse.getCellular());
		company.setPager(companyResponse.getPager());
		return company;
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
				CompanyResponse response = new CompanyResponse();
				BeanUtils.copyProperties(response, company);
				
				if(response != null) {
					json = mapper.writeValueAsString(response);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}
}
