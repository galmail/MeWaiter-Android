package com.aforma.mewaiter.app;
/**
* 
* La Clase OrderMods, es la clase que recoge los datos y la asociaci—n entre el pedido y los modificadores seleccionados al mismo.
*
* 
*/
public class Ordermods {
	private int id_order;
	private String sid_mls;
	private String sid_ml;
	private String sid_modifier;
	private String value;
	
	
	 /**
	  * 
	  * @param id_order
	  * @param sid_mls
	  * @param sid_ml
	  * @param sid_modifier
	  * @param value
	  */
		public Ordermods(int id_order,  String sid_mls, String sid_ml, String sid_modifier, String value) {
			super();
			this.id_order=id_order;
			this.sid_mls = sid_mls;
			this.sid_ml = sid_ml;
			this.sid_modifier = sid_modifier;
			this.value=value;
			
		}
		
		public int getIdOrder()
		{
			return this.id_order;
		}
		public String getSidmls()
		{
			return this.sid_mls;
		}
		
		public String getSidml()
		{
			return this.sid_ml;
		}
		public String getSid()
		{
			return this.sid_modifier;
		}
		public String getValue()
		{
			return this.value;
		}
		
}
