package com.aforma.mewaiter.app;

/**
* 
* La Clase Payments, se utiliza para recoger todos los datos de los medios de pagos y realizar las operaci—nes necesarias.
*
* 
*/
public class Payments {
	
	private int id;
	private String sid;
	private String name;
	private Double amount;
	private String key;
	private String notas = "";
	
	 /**
	  * 
	  * @param id
	  * @param sid
	  * @param name
	  * @param key
	  * @param notas
	  */
		public Payments(int id,  String sid,  String name, String key, String notas) {
			super();
			this.id = id;
			this.sid = sid;
			this.name =name;
			this.key = key;
			if (!key.contains("cash"))
				this.notas = notas;
			
		}
		
		/**
		 * 
		 * @param id
		 * @param sid
		 * @param name
		 * @param amount
		 * @param key
		 * @param notas
		 */
		public Payments(int id,  String sid,  String name, Double amount, String key, String notas) {
			super();
			this.id = id;
			this.sid = sid;
			this.name =name;
			this.amount= amount;
			this.key=key;
			if (!key.contains("cash"))
				this.notas = notas;
			
		}
		
		/**
		 * 
		 * @param id
		 * @param sid
		 * @param name
		 * @param amount
		 * @param key
		 */
		public Payments(int id,  String sid,  String name, Double amount, String key) {
			super();
			this.id = id;
			this.sid = sid;
			this.name =name;
			this.amount= amount;
			this.key=key;
			
			
		}
		
		public int getId()
		{
			return this.id;
		}
		public String getSid()
		{
			return this.sid;
		}
		public String getName() {
			return this.name;
		}

		public Double getAmount() {
			
			return this.amount;
		}

		public String getKey() {
			
			return this.key;
		}
		
		public String getNotas() {
			
			return this.notas;
		}
		
}
