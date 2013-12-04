package com.aforma.mewaiter.app;

import android.graphics.drawable.Drawable;

/**
 * 
 * Es la clase con la información a mostrar en la lista de Settings 
 * en el menu slider.
 *
 */
public class Categorias {
    @SuppressWarnings("unused")
	
   /**
    * @param
    * int id:  identificador categoria
    * String nombre: descripcion
    * Drawable icono: icono mostrado 
    * 
    */
    
    private int id;
	private String name;
	public Drawable icono;


	    public Categorias(){
	        super();
	    }
	    
	    public Categorias(int id, String name, Drawable icono) {
	        super();
	        this.id = id;
	        this.name = name;
	        this.icono = icono;
	    }

	    public Drawable toIcono() {
	    	return this.icono;
	    }
	    @Override
	    public String toString() {
	        return  this.name;
	    }
	}


