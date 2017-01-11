package com.dpu.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class AdditionalContacts {

	@JsonProperty("add_contact_id")
	private int additionalContactId;

	@JsonProperty("customer_name")
	private String customerName;

	@JsonProperty("position")
	private String position;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("ext")
	private String ext;

	@JsonProperty("fax")
	private String fax;

	@JsonProperty("additional_contact_prefix")
	private String prefix;

	@JsonProperty("cellular")
	private String cellular;

	@JsonProperty("status")
	private int status;

	@JsonProperty("email")
	private String email;

}
