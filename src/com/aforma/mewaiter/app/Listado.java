package com.aforma.mewaiter.app;


/**
 * 
 * Listado de Categorias
 *
 */
public class Listado {

    
	public String name;
    public int numero;
    public int id;
    public String comentarios;
    public String price;
    
    public Listado(){
        super();
    }
    
    public Listado(int id, String name, int numero, String comentarios, String price) {
        super();
        this.id = id;
        this.name = name;
        this.numero = numero;
        this.comentarios = comentarios;
        this.price = price;
    }

    public int toInteger() {
    	return this.numero;
    }
    @Override
    public String toString() {
        return  this.name;
    }
    
    
}

