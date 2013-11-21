package com.aforma.mewaiter.app;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import com.aforma.mewaiter.utils.ActionBar.AbstractAction;

	
/**
 * 
 *  Clase Guardar Cuenta: Guardar’a los datos modificados en la pantalla Mi Cuenta. 
 *  
 *  TODO en version2 de meWaiter.
 *  
 *
 */
public class GuardarCuenta extends AbstractAction {
		
		public GuardarCuenta() {
			super(android.R.drawable.ic_menu_save);
			
			
		}
		
		@Override
		public void performAction(View view){
			
				            	
	            	AlertDialog.Builder builder = new AlertDialog.Builder(Main.contextForDialog);
	            	builder.setMessage("ÀDeseas guardar la configuraci—n?")
	            	.setTitle("Mi Cuenta"); 
	            
	            	//Add the buttons
	            	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    // User clicked OK button
	                	
	                }
	            	});
	            	builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    // User cancelled the dialog
	                }
	            	});

	            	builder.create();  
	            	builder.show();
	            
	            	}	
	           
	      
					
	}
	




