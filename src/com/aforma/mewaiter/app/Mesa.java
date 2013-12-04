package com.aforma.mewaiter.app;

/**
* 
* La Clase Mesa, se utiliza para recoger todos los datos de la mesa y realizar las operaciones necesarias.
*
* 
*/
public class Mesa {
    private int id_floor;
    private int id_table;
    private String number;
    private int status; // 1="Open" 2="Closed"
    private int pax;
    private String sid_floor;
    private String sid_table;
    private String name;
    
    public Mesa(){
        super();
    }
    /**
     * 
     * @param id_floor
     * @param id_table
     * @param sid_floor
     * @param sid_table
     * @param name
     * @param number
     * @param status
     * @param pax
     */
    public Mesa(int id_floor, int id_table, String sid_floor, String sid_table, String name, String number, int status, int pax) {
        super();
        this.id_floor = id_floor;
        this.id_table = id_table;
        this.sid_floor = sid_floor;
        this.sid_table = sid_table;
        this.name = name;
        this.number = number;
        this.status = status;
        this.pax = pax;
    }

    @Override
    public String toString() {
        return this.sid_table + ". " + this.name + " " + this.number + " Personas: [" + this.pax + "]";
    }
    
    public int getIdFloor() {
    	return this.id_floor;
    }
    public String getZone() {
    	return this.name;
    }
    public String getNumTable() {
    	return this.number;
    }
    public String getZoneNum() {
    	return this.name + " Mesa " + this.number;
    }
    public int getStatus() {
    	return this.status;
    }
    public int getPax() {
    	return this.pax;
    }
    public String getStatusMsg() {
    	switch (this.status)
		{
		case 1:
			return "abierta";
		case 2:
			return "cerrada";
		default:
			return "error: sin estado";
		}
    }
    
    public String getSidFloor() {
    	return this.sid_floor;
    }
    public String getSidTable() {
    	return this.sid_table;
    }
    
}