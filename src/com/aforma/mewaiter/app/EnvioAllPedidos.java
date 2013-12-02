package com.aforma.mewaiter.app;



import java.util.ArrayList;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.aforma.mewaiter.utils.CheckConnect;
import com.aforma.mewaiter.utils.DBHelper;
import com.aforma.mewaiter.utils.JSONCreate;
import com.aforma.mewaiter.utils.JSONSendPOST;

	
/**
 * 
 * Clase Enviar All Pedidos: Recoge todos los pedidos pendientes y los va enviando al POS uno a uno
 * 
 * Si no se consigue enviar aparece un mensaje (Toast) de error.
 * Si se consigue enviar aparece un mensaje (Toast) de env’o correcto por mesa.
 *
 */
public class EnvioAllPedidos  {
		
		public EnvioAllPedidos() {
			
		}
		int OK=0;
		public int performAction(){
			AlertDialog.Builder builder = new AlertDialog.Builder(Main.contextForDialog);
        	builder.setMessage("ÀDeseas reenviar ahora?")        	        	
        	.setTitle("Enviar Pedidos Pendientes")
        	.setIcon(android.R.drawable.ic_menu_send);
        	
        	
        	//Add the buttons
        	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            	DBHelper DB = new DBHelper(Main.contextForDialog);
            	ArrayList<Ordermods> ordermods = new ArrayList<Ordermods>();
            	ArrayList<Discountsel> discountsels = new ArrayList<Discountsel>();
            	int idOrder = 0;
            	DB.open();
            
            	
            	ArrayList<Order> order2 = DB.getAllOrders();
            	DB.close();
            	for (int j = 0; j< order2.size(); j++)
            	{	
            		DB.open();
            		String sid_table = order2.get(j).getSidTable();
            		Mesa mesaDB = DB.getTableById(sid_table);
            		String mesa =  mesaDB.getNumTable();
            		ArrayList<Order> order = DB.getOrderByTable(sid_table);
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
					port = Integer.valueOf(cadena[2].toString());
				}
            	if (CheckConnect.serverIsAlive(ip,port,timeout))
            	{
            		boolean status = jsonPost.postData(url, jsonOrder);
            		
            		if (status)
            		{
           				DB = new DBHelper(Main.contextForDialog);
    						DB.open();
    						OK = DB.removeOrderByTable(sid_table);
    						ArrayList<Order> order3 = DB.getAllOrders();
    						Main.adapter.sections.clear();
    						Main.adapter.headers.clear();
    						Main.journalListView.setAdapter(Main.adapter);
    						
    						Mesa mesas = new Mesa();
    						String mesa_name =  new String();
    						String Nombre = "";
    						ArrayAdapter<String> listadapter;
    						String[] cadena1 = null;
    						cadena1 = new String[order3.size()];
    						String sid_mesa = null;
    						
    						for (j=0;j<order3.size(); j++)
    						{
    							Nombre = order3.get(j).getProductName();
    							sid_mesa = order3.get(j).getSidTable();				
    							mesas =  DB.getTableById(sid_mesa);
    							mesa_name =  mesas.getZoneNum();
    							cadena1[j] = Nombre;
    						}
    						
    						if (mesa_name.contains("Mesa"))
    						{
	    						listadapter = new ArrayAdapter<String>(Main.contextForDialog, R.layout.list_item2, cadena1);
	    						Main.adapter.addSection(mesa_name, listadapter);    								
    						}
    						//Main.refreshListView(order3, Main.adapter);	
    						Helper.getListViewSize2(Main.journalListView);
    						Main.journalListView.refreshDrawableState();
    						
    						for( int i = 0; i< order.size();i++)
    		            	{
    		            		int id_Order = order.get(i).getId();
    		            		long OK1 = DB.removeOrderModsById(id_Order);
    			            	
    		            	}     						
    						
    						long OK2 = DB.removeDiscountsSelByTable(Main.id_table);
    						DB.close();
    						if (OK>0)
    						{
    							Toast.makeText(Main.contextForDialog, "Pedido mesa: " + mesa + " enviado", Toast.LENGTH_LONG).show();
    						}
    						else
    						{
    							Toast.makeText(Main.contextForDialog, "ERROR. El pedido se ha enviado, pero no se ha podido borrar.", Toast.LENGTH_LONG).show();
    						}
    							
    					
            		}else
            		{
            			Toast.makeText(Main.contextForDialog, "ERROR. El pedido no se ha podido enviar", Toast.LENGTH_LONG).show();
            		}
            		
            	}else
            	{
            		Toast.makeText(Main.contextForDialog, "ERROR de conexion", Toast.LENGTH_LONG).show();
             	}
            	
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
        
        	return OK;
		}
		
		
	}
	




