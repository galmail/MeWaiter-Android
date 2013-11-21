package com.aforma.mewaiter.app;


/**
 * 
 * Clase DishType: maneja el tipo de platos, propiedades y eventos.
 *
 */
public class DishType {
	
	private int id;
	private int id_menu;
	private int id_dish;
	private String name;
	private String description;
	private String small_icon;
	
	
	/**
	 * 
	 * @param id
	 * @param id_menu
	 * @param id_dish
	 * @param name
	 * @param description
	 * @param small_icon
	 */
	 
		public DishType(int id, int id_menu, int id_dish, String name, String description, String small_icon) {
			super();
			this.id=id;
			this.id_menu = id_menu;
			this.id_dish = id_dish;
			this.small_icon = small_icon;
			this.name = name;
			this.id_menu = id_menu;
			this.description = description;
			
		}
		
		public String getName()
		{
			return this.name;
		}
		public String getDescription()
		{
			return this.description;
		}
		
		public int getId()
		{
			return this.id;
		}
		public int getIdMenu()
		{
			return this.id_menu;
		}
		public int getIdDish()
		{
			return this.id_dish;
		}
		


}
