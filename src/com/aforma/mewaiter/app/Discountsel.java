package com.aforma.mewaiter.app;



/**
 * 
 * Clase descuento seleccionado: maneja cada uno de los descuentos seleccionados para a–adir al pedido o ticket, sus propiedades y eventos.
 *
 */
public class Discountsel {
	
	private int id_order;
	private int id_table;
	private String sid;  // Sid del descuento
	private String general;
	private String sid_menu;
	private String sid_section;
	private String sid_dish;

	
	/**
	 * Clase descuento seleccionado: maneja cada uno de los descuentos seleccionados para a–adir al pedido o ticket, sus propiedades y eventos.
	 * 
	 * @param sid
	 * @param id_table
	 * @param id_order
	 * @param general
	 * @param sid_menu
	 * @param sid_section
	 * @param sid_dish
	 */
	public Discountsel(String sid, int id_table, int id_order, String general, String sid_menu, String sid_section, String sid_dish) {
			super();
			this.id_table=id_table;
			this.id_order=id_order;
			this.sid=sid;
			this.general = general;
			this.sid_menu = sid_menu;
			this.sid_section = sid_section;
			this.sid_dish = sid_dish;
			
		}
		
		public int getIdOrder()
		{
			return this.id_order;
		}
		public int getIdTable()
		{
			return this.id_table;
		}
		public String getSid()
		{
			return this.sid;
		}
		public String getGeneral()
		{
			return this.general;
		}
		public String getGetMenu()
		{
			return this.sid_menu;
		}
		public String getGetSection()
		{
			return this.sid_section;
		}
		public String getDish()
		{
			return this.sid_dish;
		}
		
}
