package com.aforma.mewaiter.app;


/**
 * 
 * Clase Dish: maneja cada de los platos, sus propiedades y eventos.
 *
 */
public class Dish {
	private int id;
	private int id_menu;
	private int id_section;
	private int id_subsection;
	private String sid;
	private String name;
	private String description;
	private String short_title;
	private String price;
	private String sd_dish_id;
	
	
	 /**
	  * 
	  * @param id
	  * @param id_menu
	  * @param id_section
	  * @param id_subsection
	  * @param sid
	  * @param name
	  * @param description
	  * @param short_title
	  * @param price
	  * @param sd_dish_id
	  */
		public Dish(int id, int id_menu, int id_section, int id_subsection, String sid, String name, String description, String short_title, String price, String sd_dish_id) {
			super();
			this.id=id;
			this.id_menu = id_menu;
			this.id_section = id_section;
			this.id_subsection = id_subsection;
			this.sid = sid;
			this.name = name;
			this.short_title= short_title;
			this.id_menu = id_menu;
			this.description = description;
			this.price = price;
			this.sd_dish_id = sd_dish_id;
			
		}
		
		public String getName()
		{
			return this.name;
		}
		public String getDescription()
		{
			return this.description;
		}
		public String getPrice()
		{
			return this.price;
		}
		public String getShortTitle()
		{
			return this.short_title;
		}
		public int getId()
		{
			return this.id;
		}
		public int getIdMenu()
		{
			return this.id_menu;
		}
		public int getIdSection()
		{
			return this.id_section;
		}
		public int getIdSubsection()
		{
			return this.id_subsection;
		}
		public String getsdDishId()
		{
			return this.sd_dish_id;
		}
		public String getSid()
		{
			return this.sid;
		}

}
