package com.dpu.util;

import org.springframework.beans.factory.annotation.Value;

public class MessageProperties {

	/**
	 * Company Module Messages
	 */
	
	@Value("${company_added_code}")
	public String companyAddedCode;
	
	@Value("${company_added_message}")
	public String companyAddedMessage;
	
	@Value("${company_unable_to_add_code}")
	public String companyUnableToAddCode;
	
	@Value("${company_unable_to_add_message}")
	public String companyUnableToAddMessage;
	
	@Value("${company_deleted_code}")
	public String companyDeletedCode;
	
	@Value("${company_deleted_message}")
	public String companyDeletedMessage;
	
	@Value("${company_unable_to_delete_code}")
	public String companyUnableToDeleteCode;
	
	@Value("${company_unable_to_delete_message}")
	public String companyUnableToDeleteMessage;
	
	@Value("${company_updated_code}")
	public String companyUpdateCode;
	
	@Value("${company_updated_message}")
	public String companyUpdateMessage;
	
	@Value("${company_unable_to_update_code}")
	public String companyUnableToUpdateCode;
	
	@Value("${company_unable_to_update_message}")
	public String companyUnableToUpdateMessage;
	
	/**
	 * Shipper Module Messages
	 */
	
	@Value("${shipper_added_code}")
	public String shipperAddedCode;
	
	@Value("${shipper_added_message}")
	public String shipperAddedMessage;
	
	@Value("${shipper_unable_to_add_code}")
	public String shipperUnableToAddCode;
	
	@Value("${shipper_unable_to_add_message}")
	public String shipperUnableToAddMessage;
	
	@Value("${shipper_deleted_code}")
	public String shipperDeletedCode;
	
	@Value("${shipper_deleted_message}")
	public String shipperDeletedMessage;
	
	@Value("${shipper_unable_to_delete_code}")
	public String shipperUnableToDeleteCode;
	
	@Value("${shipper_unable_to_delete_message}")
	public String shipperUnableToDeleteMessage;
	
	@Value("${shipper_updated_code}")
	public String shipperUpdateCode;
	
	@Value("${shipper_updated_message}")
	public String shipperUpdateMessage;
	
	@Value("${shipper_unable_to_update_code}")
	public String shipperUnableToUpdateCode;
	
	@Value("${shipper_unable_to_update_message}")
	public String shipperUnableToUpdateMessage;

}
