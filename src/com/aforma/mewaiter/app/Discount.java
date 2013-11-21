package com.aforma.mewaiter.app;

/**
 * 
 * Clase descuento: maneja cada uno de los descuentos, sus propiedades y eventos.
 * 
 */

public class Discount {
	
	private String sid;
	private String name;
	private String dtype;
	private String note;
	private String amount;
	private String section_id;
	private String dish_id;
	private String restaurant_id;
	private String menu_id;
	
	
	 /**
	  * 
	  * @param sid
	  * @param name
	  * @param dtype
	  * @param amount
	  * @param note
	  * @param section_id
	  * @param dish_id
	  * @param restaurant_id
	  * @param menu_id
	  * 
	  */
		public Discount (String sid, String name, String dtype, String amount, String note, String section_id, String dish_id, String restaurant_id, String menu_id) {
			super();
			
			this.sid = sid;
			this.name = name;
			this.dtype= dtype;
			this.amount = amount;
			this.note = note;
			this.section_id = section_id;
			this.dish_id = dish_id;
			this.restaurant_id =  restaurant_id;
			this.menu_id =  menu_id;
			
		}
		
		public String getName()
		{
			return this.name;
		}
		public String getSid()
		{
			return this.sid;
		}
		public String getDtype()
		{
			return this.dtype;
		}
		public String getAmount()
		{
			return this.amount;
		}
		public String getNote()
		{
			return this.note;
		}
		public String getSectionId()
		{
			return this.section_id;
		}
		public String getDishId()
		{
			return this.dish_id;
		}
		public String getRestId()
		{
			return this.restaurant_id;
		}
		public String getMenuId()
		{
			return this.menu_id;
		}

}
