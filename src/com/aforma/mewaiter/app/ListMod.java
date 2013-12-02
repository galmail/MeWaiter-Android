package com.aforma.mewaiter.app;



/**
 * 
 * Listado de Modificadores.
 *
 */
public class ListMod {
	private int id_list;
	private int id_mls;
	private String name;
	private String is_mandatory;
	private String is_multioption;
	private String sid;
	
	 
		public ListMod(int id_list, int id_mls, String sid, String name, String is_mandatory, String is_multioption ) {
			super();
			
			this.id_list=id_list;
			this.name=name;
			this.id_mls=id_mls;
			this.sid=sid;
			this.is_mandatory=is_mandatory;
			this.is_multioption=is_multioption;
			
		}
		
		
		public int getIdList()
		{
			return this.id_list;
		}
		public int getIdMLS()
		{
			return this.id_mls;
		}
		public String getSid()
		{
			return this.sid;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public String getMandatory()
		{
			return this.is_mandatory;
		}
		public String getMultioption()
		{
			return this.is_multioption;
		}

}
