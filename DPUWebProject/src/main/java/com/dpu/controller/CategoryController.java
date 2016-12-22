/**
 * 
 */
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

import com.dpu.entity.Category;
import com.dpu.entity.Company;
import com.dpu.service.CategoryService;

/**
 * @author jagvir
 *
 */

@RestController
@RequestMapping(value = "category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		String json = null;
		try {
			List<Category> lstCategories = categoryService.getAll("");
			json = mapper.writeValueAsString(lstCategories);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody Category category) {
		String json = null;
		try {
			boolean result = categoryService.add(category);
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
			boolean result = categoryService.delete(id);
			json = mapper.writeValueAsString(result);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id,
			@RequestBody Category category) {

		String json = null;
		try {
			boolean result = categoryService.update(id, category);
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
			Category category = categoryService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(category);
		} catch (Exception e) {
			System.out.println(e);
		}
		return json;
	}

}
