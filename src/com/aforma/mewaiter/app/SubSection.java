package com.aforma.mewaiter.app;


/**
* 
* La Clase SubSection, se utiliza para recoger todos los datos de una subsección y realizar las operaciones necesarias.
*
* 
*/
public class SubSection {
	private int id;
	private int id_menu;
	private int id_section;
	private String name;
	private String sid;
	private String short_title;
	
	
	 /**
	  * 
	  * @param id
	  * @param id_menu
	  * @param id_section
	  * @param sid
	  * @param name
	  * @param short_title
	  */
		public SubSection(int id, int id_menu, int id_section, String sid, String name, String short_title) {
			super();
			this.id=id;
			this.sid=sid;
			this.id_menu = id_menu;
			this.id_section = id_section;
			this.name = name;
			this.short_title= short_title;
			this.id_menu = id_menu;
			
			
		}
		
		public String getName()
		{
			return this.name;
		}
		public String getSid()
		{
			return this.sid;
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

}
