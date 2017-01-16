/**
 * 
 */
package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author jagvir
 *
 */

@Entity
@Table(name = "category")
@JsonSerialize(include = Inclusion.NON_NULL)
public class Category {

	@Id
	@Column(name = "category_id")
	//@JsonProperty(value = "category_id")
	@GeneratedValue
	private int categoryId;

	@Column(name = "type_id")
	//@JsonProperty(value = "type_id")
	private int typeId;

	@Column(name = "name")
	//@JsonProperty(value = "name")
	private String name;

	@Column(name = "status")
	//@JsonProperty(value = "status")
	private int status;

	@Column(name = "created_on")
	//@JsonProperty(value = "created_on")
	private String createdOn;

	@Column(name = "created_by")
	//@JsonProperty(value = "created_by")
	private String createdBy;
	
	@Column(name = "highlight")
	private String highlight;
	
	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
