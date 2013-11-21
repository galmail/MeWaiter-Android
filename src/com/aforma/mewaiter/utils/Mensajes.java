package com.aforma.mewaiter.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * 
 * Actualmente no se utiliza
 *
 */
public class Mensajes {
	
	public static void msgDisplay(Context context, String txtmensaje)
	  {
		// La llamada se debe realizar habien optenido antes el context Context context = getApplicationContext();  
		
	  CharSequence text = txtmensaje;
	  int duration = Toast.LENGTH_SHORT;
	  Toast toast = Toast.makeText(context, text, duration);
	  toast.show();	
	  }

}
