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
@Table(name = "equipmentmaster")
@JsonSerialize(include = Inclusion.NON_NULL)
public class Equipment {

	@Id
	@Column(name = "equipment_id")
	@GeneratedValue
	@JsonProperty(value = "equipment_id")
	private int equipmentId;

	@Column(name = "equipment_name")
	@JsonProperty(value = "equipment_name")
	private String equipmentName;

	@Column(name = "description")
	@JsonProperty(value = "description")
	private String description;

	public int getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
