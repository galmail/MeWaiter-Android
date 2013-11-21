package com.aforma.mewaiter.app;

/**
* 
* La Clase Restaurante, se utiliza para recoger todos los datos del Restaurante y realizar las operaci—nes necesarias.
*
* 
*/
public class Restaurante {
	
private String name;
private String logo;
private String il8nbg;
private String pos_ip_address;
private String access_key;
 
	public Restaurante(String name, String logo, String il8nbg, String pos_ip_address, String access_key) {
		super();
		this.name = name;
		this.logo = logo;
		this.il8nbg = il8nbg;
		this.pos_ip_address = pos_ip_address;
		this.access_key = access_key;
	
	}
	
	public String getName()
	{
		return this.name;
	}
	public String getAccesKEY()
	{
		return this.access_key;
	}
	
	public String getIP() {
		return this.pos_ip_address;
	}
	
	
	 
}