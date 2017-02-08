package com.dpu.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
 
public class CommonProperties {

	
	static {
		loadProperties();
	}
	static Properties prop;

	private static void loadProperties() {
		prop = new Properties();
		InputStream in = CommonProperties.class
				.getResourceAsStream("/message.properties");
		try {
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	// messages for driver module
	public static final String Driver_added_message = prop.getProperty("Driver_added_message");
	public static final String Driver_added_code = prop.getProperty("Driver_added_code");
	public static final String Driver_unable_to_add_code = prop.getProperty("Driver_unable_to_add_code");
	public static final String Driver_unable_to_add_message = prop.getProperty("Driver_unable_to_add_message");
	public static final String Driver_deleted_code = prop.getProperty("Driver_deleted_code");
	public static final String Driver_deleted_message = prop.getProperty("Driver_deleted_message");
	public static final String Driver_unable_to_delete_code = prop.getProperty("Driver_unable_to_delete_code");
	public static final String Driver_unable_to_delete_message = prop.getProperty("Driver_unable_to_delete_message");
	public static final String Driver_updated_code = prop.getProperty("Driver_updated_code");
	public static final String Driver_updated_message = prop.getProperty("Driver_updated_message");
	public static final String Driver_unable_to_update_code = prop.getProperty("Driver_unable_to_update_code");
	public static final String Driver_unable_to_update_message = prop.getProperty("Driver_unable_to_update_message");
	
	// messages for truck module
		public static final String Truck_added_message = prop.getProperty("Truck_added_message");
		public static final String Truck_added_code = prop.getProperty("Truck_added_code");
		public static final String Truck_unable_to_add_code = prop.getProperty("Truck_unable_to_add_code");
		public static final String Truck_unable_to_add_message = prop.getProperty("Truck_unable_to_add_message");
		public static final String Truck_deleted_code = prop.getProperty("Truck_deleted_code");
		public static final String Truck_deleted_message = prop.getProperty("Truck_deleted_message");
		public static final String Truck_unable_to_delete_code = prop.getProperty("Truck_unable_to_delete_code");
		public static final String Truck_unable_to_delete_message = prop.getProperty("Truck_unable_to_delete_message");
		public static final String Truck_updated_code = prop.getProperty("Truck_updated_code");
		public static final String Truck_updated_message = prop.getProperty("Truck_updated_message");
		public static final String Truck_unable_to_update_code = prop.getProperty("Truck_unable_to_update_code");
		public static final String Truck_unable_to_update_message = prop.getProperty("Truck_unable_to_update_message");
	 

		// messages for division module
		public static final String Division_added_message = prop.getProperty("division_added_message");
		public static final String Division_added_code = prop.getProperty("division_added_code");
		public static final String Division_unable_to_add_code = prop.getProperty("division_unable_to_add_code");
		public static final String Division_unable_to_add_message = prop.getProperty("division_unable_to_add_message");
		public static final String Division_deleted_code = prop.getProperty("division_deleted_code");
		public static final String Division_deleted_message = prop.getProperty("division_deleted_message");
		public static final String Division_unable_to_delete_code = prop.getProperty("division_unable_to_delete_code");
		public static final String Division_unable_to_delete_message = prop.getProperty("division_unable_to_delete_message");
		public static final String Division_updated_code = prop.getProperty("division_updated_code");
		public static final String Division_updated_message = prop.getProperty("division_updated_message");
		public static final String Division_unable_to_update_code = prop.getProperty("division_unable_to_update_code");
		public static final String Division_unable_to_update_message = prop.getProperty("division_unable_to_update_message");

		// messages for equipment module
		public static final String Equipment_added_message = prop.getProperty("equipment_added_message");
		public static final String Equipment_added_code = prop.getProperty("equipment_added_code");
		public static final String Equipment_unable_to_add_code = prop.getProperty("equipment_unable_to_add_code");
		public static final String Equipment_unable_to_add_message = prop.getProperty("equipment_unable_to_add_message");
		public static final String Equipment_deleted_code = prop.getProperty("equipment_deleted_code");
		public static final String Equipment_deleted_message = prop.getProperty("equipment_deleted_message");
		public static final String Equipment_unable_to_delete_code = prop.getProperty("equipment_unable_to_delete_code");
		public static final String Equipment_unable_to_delete_message = prop.getProperty("equipment_unable_to_delete_message");
		public static final String Equipment_updated_code = prop.getProperty("equipment_updated_code");
		public static final String Equipment_updated_message = prop.getProperty("equipment_updated_message");
		public static final String Equipment_unable_to_update_code = prop.getProperty("equipment_unable_to_update_code");
		public static final String Equipment_unable_to_update_message = prop.getProperty("equipment_unable_to_update_message");

}
