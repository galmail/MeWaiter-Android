package com.aforma.mewaiter.app;
/**
* 
* La Clase Settings, se utiliza para recoger todos los datos de configuraci—n y realizar las operaci—nes necesarias.
*
* 
*/
public class Settings {
	
	protected String icono;
	protected String descripcion;
	protected int id;
	
	public Settings() {
	    this.icono = "";
	    this.descripcion= "";
	    this.id=0;
	    
	  }
	  /**
	   *    
	   * @param icono
	   * @param descripcion
	   * @param id
	   */
	  public Settings(String icono, String descripcion, int id) {
		  this.icono = icono;
		  this.descripcion= descripcion;
		  this.id=id;
	    
	  }
	  
	  public long getId() {
		    return id;
		  }
	     
	  public String[] ObtenerSettings()
	  {
		  
		  String[] settings = {this.icono, this.descripcion};
		  return settings;
	  }
	

}
