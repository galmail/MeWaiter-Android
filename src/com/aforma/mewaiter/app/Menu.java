package com.aforma.mewaiter.app;

/**
* 
* La Clase Menu, se utiliza para recoger todos los datos del menœ y realizar las operaci—nes necesarias.
*
* 
*/
public class Menu {
	
private int id;
private String name;
private String menu_type;
private String price;
private String sid;
 /**
  * 
  * @param id
  * @param sid
  * @param name
  * @param menu_type
  * @param price
  */
	public Menu(int id, String sid, String name, String menu_type, String price) {
		super();
		this.id=id;
		this.sid=sid;
		this.name = name;
		this.menu_type = menu_type;
		this.price = price;
		
	
	}
	
	public String getName()
	{
		return this.name;
	}
	public int getId()
	{
		return this.id;
	}

	public String getType() {
		
		return this.menu_type;
	}
	public String getSid()
	{
		return this.sid;
	}
	 
	public String getPrice()
	{
		return this.price;
	}
}