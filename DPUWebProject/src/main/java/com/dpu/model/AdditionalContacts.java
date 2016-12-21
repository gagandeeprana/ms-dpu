package com.dpu.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class AdditionalContacts {

	@JsonProperty(value = "add_contact_id")
	private int additionalContactId;

	@JsonProperty(value = "customer_name")
	private String customerName;

	@JsonProperty(value = "position")
	private String position;

	@JsonProperty(value = "phone")
	private String phone;

	@JsonProperty(value = "ext")
	private String ext;

	@JsonProperty(value = "fax")
	private String fax;

	@JsonProperty(value = "additional_contact_prefix")
	private String prefix;

	@JsonProperty(value = "cellular")
	private String cellular;

	@JsonProperty(value = "status")
	private int status;

	@JsonProperty(value = "email")
	private String email;

}
