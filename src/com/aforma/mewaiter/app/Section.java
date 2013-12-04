package com.aforma.mewaiter.app;


/**
* 
* La Clase Section, se utiliza para recoger todos los datos de una sección y realizar las operaciones necesarias.
*
* 
*/
public class Section {
	private int id;
	private int id_menu;
	private String sid;
	private String name;
	private String mini;
	private String thumbnail;
	private String hasBigSubsections;
	private String dishes_per_pages;
	
	 /**
	  * 
	  * @param id
	  * @param id_menu
	  * @param sid
	  * @param name
	  * @param mini
	  * @param thumbnail
	  * @param hasBigSubsections
	  * @param dishes_per_pages
	  */
		public Section(int id, int id_menu, String sid, String name, String mini, String thumbnail, String hasBigSubsections, String dishes_per_pages) {
			super();
			this.id=id;
			this.id_menu = id_menu;
			this.name = name;
			this.thumbnail= thumbnail;
			this.mini = mini;
			this.hasBigSubsections = hasBigSubsections;
			this.dishes_per_pages = dishes_per_pages;
			this.sid =sid;
		}
		
		public String getName()
		{
			return this.name;
		}
		public String getSid()
		{
			return this.sid;
		}
		public int getId()
		{
			return this.id;
		}
		public int getIdMenu()
		{
			return this.id_menu;
		}
		public String gethasBigSubSections()
		{
			return this.hasBigSubsections;
		}
}
