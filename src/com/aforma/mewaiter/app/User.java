package com.aforma.mewaiter.app;



/**
* 
* La Clase User, se utiliza para recoger todos los datos de un usuario y realizar las operaciones necesarias.
*
* 
*/
public class User {

		
		private String mwkey;
		private String api_key;
		private String location_id;
		private String device_id;
		private String user_id;
		private String employee_id;
		 
		/**
		 * 
		 * @param mwkey
		 * @param api_key
		 * @param location_id
		 * @param device_id
		 * @param user_id
		 * @param employee_id
		 */
			public User(String mwkey, String api_key, String location_id, String device_id, String user_id, String employee_id) {
				super();
				this.mwkey = mwkey;
				this.api_key = api_key;
				this.location_id = location_id;
				this.device_id = device_id;
				this.user_id = user_id;
				this.employee_id = employee_id;
			
			}
			
			public String getUserMWKey()
			{
				return this.mwkey;
			}
			public String getUserAPIKey()
			{
				return this.api_key;
			}
			public String getUserUserID()
			{
				return this.user_id;
			}
			public String getUserlocationID()
			{
				return this.location_id;
			}
			public String getUserDeviceID()
			{
				return this.device_id;
			}
			public String getUserEmployeeID()
			{
				return this.employee_id;
			}
			
			
			 
}


