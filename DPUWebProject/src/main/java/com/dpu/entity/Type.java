/**
 * 
 */
package com.dpu.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author jagvir
 *
 */

@Entity
@Table(name = "typemaster")
public class Type {

	@Id
	@Column(name = "type_id")
	@GeneratedValue
	private Long typeId;

	@Column(name = "type_name")
	private String typeName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
	private Set<Equipment> equipmentSet = new HashSet<Equipment>();

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}