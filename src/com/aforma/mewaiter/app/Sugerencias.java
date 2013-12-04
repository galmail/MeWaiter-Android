package com.aforma.mewaiter.app;

import android.app.Activity;

import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.aforma.mewaiter.utils.ActionBar;


/**
* 
* La Activity Sugerencias, se utiliza para mostrar las sugerencias del día en una pantalla, la cual es cargada al hacer Login o cuando se presiona un botón de actualizar.
*
* 
*/
public class Sugerencias extends Activity {

	private Context contextForDialog = null;
	  
	@Override
	  public void onCreate(Bundle savedInstances) {
	    super.onCreate(savedInstances);
	   this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.sugerencias);
	    
	    contextForDialog = this;
	   
	    
	    
	 // Draw ActionBar
	   
	ActionBar actionbar = (ActionBar) this.findViewById(R.id.actionBar5);
	actionbar.setTitle("Sugerencias del Día");	
	
	// Boton volver
	Button btnVolver = (Button) this.findViewById(R.id.btnBack);
	btnVolver.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
	
        	finish();
        	//overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
        	overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
	
	});  
	
	
	
	
	}  

}
