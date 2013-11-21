package com.aforma.mewaiter.app;


/**
 * 
 * Clase DishMod: maneja cada de los modificadores asociados a un plato, sus propiedades y eventos.
 *
 */
public class DishMod {
	
	private int id;
	private int id_mls;
	private String sid;
	 
	/**
	 * 
	 * @param id
	 * @param id_mls
	 * @param sid
	 */
		public DishMod(int id, int id_mls, String sid) {
			super();
			this.id=id;
			this.id_mls=id_mls;
			this.sid=sid;
			
		}
		
		public int getId()
		{
			return this.id;
		}
		public int getIdMLS()
		{
			return this.id_mls;
		}
		public String getSid()
		{
			return this.sid;
		}
		
}
