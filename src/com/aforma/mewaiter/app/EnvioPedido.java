package com.aforma.mewaiter.app;



import java.util.ArrayList;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;
import com.aforma.mewaiter.utils.ActionBar.AbstractAction;
import com.aforma.mewaiter.utils.CheckConnect;
import com.aforma.mewaiter.utils.DBHelper;
import com.aforma.mewaiter.utils.JSONCreate;
import com.aforma.mewaiter.utils.JSONSendPOST;

	
/**
 * 
 * AbstractAction EnvioPedido, asociado a un icono de la barra de estado superior.
 * 
 * Enviar el pedido.
 *
 */
public class EnvioPedido extends AbstractAction {
		
		public EnvioPedido() {
			super(android.R.drawable.ic_menu_send);
		}
		
		@Override
		public void performAction(View view){
			AlertDialog.Builder builder = new AlertDialog.Builder(Main.contextForDialog);
        	builder.setMessage("¿Deseas enviar el pedido?")        	        	
        	.setTitle("Enviar Pedido")
        	.setIcon(android.R.drawable.ic_menu_send);
        	
        	
        	//Add the buttons
        	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            	DBHelper DB = new DBHelper(Main.contextForDialog);
            	ArrayList<Ordermods> ordermods = new ArrayList<Ordermods>();
            	ArrayList <Discountsel> discountsels = new ArrayList<Discountsel>();
            	ArrayList <Discount> discounts = new ArrayList<Discount>();
            	int idOrder = 0;
            	DB.open();
            
            	final ArrayList<Order>  order = DB.getOrderByTable(Main.sid_table);
            	for( int i = 0; i< order.size();i++)
            	{
            		idOrder = order.get(i).getId();
            		ordermods = DB.getOrdermodsByID(idOrder);
            		order.get(i).setOrderMods(ordermods);
            	
	            	discountsels = DB.getDiscountSelByIdOrder(idOrder);
	            	
            	}
            	DB.close();
            	
            	
            	JSONCreate json = new JSONCreate();
            	JSONObject jsonOrder = json.createJSONOrder(order, ordermods, discountsels);
            	
            	JSONSendPOST jsonPost = new JSONSendPOST();
            	
            	String ip = Login.pos_ip_address;
            	
            	String url=ip+"/order";
            	int port=8080;
            	int timeout=10000;
            	String[] cadena = ip.split(":");
				
				if (cadena.length > 1)
				{
					ip=cadena[1].toString().substring(2);
					port= Integer.valueOf(cadena[2].toString());
				}
            	if (CheckConnect.serverIsAlive(ip,port,timeout))
            	{
            		boolean status = jsonPost.postData(url, jsonOrder);
            		
            		if (status)
            		{
            		AlertDialog.Builder builder2 = new AlertDialog.Builder(Main.contextForDialog);
                	builder2.setMessage("Pedido Enviado")        	        	
                	.setTitle("Enviar Pedido")
                	.setIcon(android.R.drawable.ic_menu_send);
                	
                	builder2.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
    					
    					@Override
    					public void onClick(DialogInterface dialog, int which) {
    						DBHelper DB = new DBHelper(Main.contextForDialog);
    						DB.open();
    						long OK = DB.removeOrderByTable(Main.sid_table);
    						for( int i = 0; i< order.size();i++)
    		            	{
    		            		int idOrder = order.get(i).getId();
    		            		long OK1 = DB.removeOrderModsById(idOrder);
    			            	
    		            	}     						
    						long OK2 = DB.removeDiscountsSelByTable(Main.id_table);
    						DB.close();
    						Main.m_Text = "";
    						Main.sid_table = "";
    						//Main.agregado = true;
    						Main.vaciarPedido();
    						
    					
    						
    					}
    				});
                	builder2.create();
                	builder2.show();
            		}else
            		{
            			Toast.makeText(Main.contextForDialog, "ERROR. El pedido no se ha podido enviar", Toast.LENGTH_LONG).show();
            		}
            		
            	}else
            	{
            		//Toast.makeText(Main.contextForDialog, "ERROR de conexion", Toast.LENGTH_LONG).show();
            		AlertDialog.Builder builder2 = new AlertDialog.Builder(Main.contextForDialog);
                	builder2.setMessage("ERROR CONEXION. Pedido No Enviado")        	        	
                	.setTitle("Enviar Pedido")
                	.setIcon(android.R.drawable.ic_menu_send);
                	
                	builder2.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
    					
    					@Override
    					public void onClick(DialogInterface dialog, int which) {
    		
    					}
    				});
                	builder2.create();
                	builder2.show();
            		
            	}
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
	




