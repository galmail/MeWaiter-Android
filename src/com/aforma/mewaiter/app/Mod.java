package com.aforma.mewaiter.app;


/**
* 
* La Clase Mod, se utiliza para recoger todos los datos de los modificadores y realizar las operaciones necesarias.
*
* 
*/
public class Mod {
	
	
	private int id_list;
	private int id_modifier;
	private String name;
	private String description;
	private String sid;
	private String sd_modifierid;
	private String price;
	
	
	 /**
	  * 
	  * @param id_list
	  * @param id_modifier
	  * @param name
	  * @param sid
	  * @param sd_modifierid
	  * @param description
	  * @param price
	  */
	  
	 
		public Mod(int id_list, int id_modifier, String name, String sid, String sd_modifierid, String description, String price) {
			super();
			
			this.id_list=id_list;
			this.id_modifier=id_modifier;
			this.name=name;
			this.sid=sid;
			this.description=description;
			this.price=price;
			this.sd_modifierid=sd_modifierid;
			
		}
		
		
		public int getIdList()
		{
			return this.id_list;
		}
		public int getIdModifier()
		{
			return this.id_modifier;
		}
		public String getSid()
		{
			return this.sid;
		}		
		public String getName()
		{
			return this.name;
		}
		public String getprice()
		{
			return this.price;
		}
		public String getDesc()
		{
			return this.description;
		}
		public String getSdModifierId()
		{
			return this.sd_modifierid;
		}
		
}
