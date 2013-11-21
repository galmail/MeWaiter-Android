package com.aforma.mewaiter.app;

import java.util.ArrayList;

/**
* 
* La Clase Order, se utiliza para recoger todos los datos del pedido y realizar las operaci—nes necesarias.
*
* 
*/
public class Order {
	private int id;
	private int id_table;
	private String sid_table;
	private String category_sid;
	private String sid;
	private int quantity;
	private String price;
	private String note;
	private String product_name;
	private int status; // Valores 1="Sin Enviar" 2="Enviado OK" 3="Enviado ERROR"
	private ArrayList<Ordermods>  ordermods = new ArrayList<Ordermods>();
	
	/**
	 * 
	 * @param id
	 * @param id_table
	 * @param sid_table
	 * @param sid
	 * @param category_sid
	 * @param price
	 * @param note
	 * @param status
	 * @param product_name
	 * @param quantity
	 * @param ordermods
	 */
	 
		public Order(int id, int id_table, String sid_table,  String sid, String category_sid, String price, String note, int status, String product_name, int quantity, ArrayList<Ordermods>ordermods) {
			super();
			this.id=id;
			this.category_sid = category_sid;
			this.id_table = id_table;
			this.quantity = quantity;
			this.status = status;			
			this.price = price;
			this.note =note;
			this.sid = sid;
			this.product_name=product_name;
			this.ordermods = ordermods;
			this.sid_table = sid_table;
			
			
		}
		
		public int getId()
		{
			return this.id;
		}
		public int getIdTable()
		{
			return this.id_table;
		}
		public String getSidTable()
		{
			return this.sid_table;
		}
		public String getPrice()
		{
			return this.price;
		}
		public String getSid()
		{
			return this.sid;
		}
		public String getCategory()
		{
			return this.category_sid;
		}
		public String getNote()
		{
			return this.note;
		}
		
		public int getStatus()
		{
			return this.status;
		}
		public String getStatusMsg()
		{
			switch (this.status)
			{
			case 1:
				return "sin enviar";
			case 2:
				return "enviado ok";
			case 3:
				return "enviado error";
			default:
				return "error: sin estado";
			}
		}
		
		public int getQuantity()
		{
			return this.quantity;
		}
		public String getProductName()
		{
			return this.product_name;
		}
		
		public ArrayList<Ordermods> getOrderMods()
		{
			return ordermods;
		}
		public void setOrderMods( ArrayList<Ordermods> ordermods)
		{
				this.ordermods = ordermods;
		}

}
