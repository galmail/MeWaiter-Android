package com.aforma.mewaiter.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 
 * Se utiliza para comprobar la conectividad antes de ejecutar un acceso a un servicio REST.
 *
 */
public class CheckConnect {
	
	static String ip = null;
    static int port = 0;
    static int timeout = 65535; // Maximo valor
   
    
    // constructor
    public CheckConnect(String ip, int port, int timeout) {
    	this.ip = ip;
    	this.port = port;
    	this.timeout = timeout;
    }
    
    public static boolean serverIsAlive(String ip, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    
    public String connect(){
    		
    		Socket cSocket = null;
    		PrintWriter out = null;
    		BufferedReader in = null;
    		String OK = "OK";
    		String ERROR = "ERROR";

    		try{
    		   cSocket = new Socket(ip,port);
    		   out = new PrintWriter(cSocket.getOutputStream(), true);
    		   in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
    		   return OK;
    		   }
    		   catch (IOException ex) {
    		   
    		   return ERROR;
    		   }                
    		   }
    	
    		
}


