package com.aforma.mewaiter.app;


/**
 * 
 * Listado de Modifier_sets
 *
 */
public class ListModSet {
	

	private int id;
	private String sid;
	private String name;
	 
		public ListModSet(int id, String sid, String name) {
			super();
			
			this.id=id;
			this.name=name;
			this.sid=sid;
			
		}
		
		
		public int getId()
		{
			return this.id;
		}
		
		public String getSid()
		{
			return this.sid;
		}
		
		public String getName()
		{
			return this.name;
		}
}
