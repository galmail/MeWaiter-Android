package com.aforma.mewaiter.app;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ToggleButton;
import com.aforma.mewaiter.utils.ActionBar;
import com.aforma.mewaiter.utils.CheckConnect;
import com.aforma.mewaiter.utils.DBHelper;
import com.aforma.mewaiter.utils.JSONParser;


/**
 * Esta activity muestra el layout abrirm.xml e implementa 
 * toda la funcionalidad de abrir la mesa en el POS 
 * o sólo seleccionarla.
 * 
 * No tiene parámetros de entrada. 
 * Usa el valor de Main.m_Text (nombre de mesa)
 *  
 */
public class AbrirMesa extends Activity {

	
	
public static Context contextForDialog = null;
//public String m_Text = null;
public String result= null;
public String result2 = null;
public String reservation = null;
public String visit = null;
public String pax = "0";
public static Restaurante restaurante = null;
public static String ip=null;
public static String mwkey = null;
public static ArrayList<HashMap<String, String>> mesaList = new ArrayList<HashMap<String, String>>();
public static ArrayList<HashMap<String, String>> floorList = new ArrayList<HashMap<String, String>>();
public static ArrayList<Mesa> mesaslist = new ArrayList<Mesa>();
public static DBHelper DB = null;
public static List<String> list = new ArrayList<String>();
public static List<Integer> list2 = new ArrayList<Integer>();
public static Context ctx =null; 
public String temporal = "";

boolean abrir = false;
/**
 * Metodo onCreate se ejecuta cuando aparece la pantalla abrir mesa
 */
@Override
public void onCreate(Bundle savedInstanceState) {
	
	
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.abrirm);
	contextForDialog=this;
	final RelativeLayout abrirLay = (RelativeLayout) findViewById(R.id.abrirLay);
	
	
		//abrirLay.setVisibility(RelativeLayout.INVISIBLE);
			// Draw ActionBar
			ActionBar actionbar = (ActionBar) findViewById(R.id.actionBar5);
			actionbar.setTitle("Abrir Mesa");		
			ctx = getApplicationContext();
			DB = new DBHelper(getApplicationContext());
		    DB.open();
		    restaurante = DB.getRestaurante(Login.nameR);
		    DB.close();
		   
		    ip=restaurante.getIP();
		    
		    mwkey=restaurante.getAccesKEY();
			
			
			CheckMenu2();
			
			
			
			
			//Volver
			Button btnVolver = (Button) this.findViewById(R.id.btnBack2);
			btnVolver.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
			
	            	finish();	
	            	overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	            	
	            	//overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            }
			
			});  
			
			//Botón OK
			Button btnOK = (Button) this.findViewById(R.id.btnOK);
			btnOK.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	
	            	
	            	DBHelper DB = new DBHelper(getApplicationContext());
				    DB.open();
				    Restaurante restaurante = DB.getRestaurante(Login.nameR);
				    DB.close();
				    
				    String ip=restaurante.getIP();
				    
				    String mwkey=restaurante.getAccesKEY();				    
				    
					
					DB.open();
					//Obetener sid_table con la zona y el numero
					Mesa mesa = DB.getTableByZoneNum(result, result2);
					DB.close();
					Main.sid_table = mesa.getSidTable();
					
						if ( result != null  && result2 != null )
						{
							Main.m_Text=result + " Mesa " + result2;
							Main.sid_table = mesa.getSidTable();
							
								String[] cadena = ip.split(":");
								Integer port=8080;
								if (cadena.length > 1)
								{
									ip=cadena[1].toString().substring(2);
									port= Integer.valueOf(cadena[2].toString());
								}
								
							    if (CheckConnect.serverIsAlive(ip, port, 10000))
							    {
								    int OK2 = Main.checkopenTable(result,result2, mwkey, ip, port);
								    if ( OK2 != 1 )
								    {
								    int OK = Main.openTable(result, result2, "open", mwkey, pax, reservation, visit, ip);
								    switch(OK) {
									    case 1:
									    	Toast.makeText(contextForDialog, "Mesa "+ Main.m_Text + " abierta OK. ", Toast.LENGTH_LONG).show();
									    	break;
									    case 2:
									    	Toast.makeText(contextForDialog, "Mesa "+ Main.m_Text + " cerrada OK. ", Toast.LENGTH_LONG).show();
									    	break;
									    case 3:
									    	Toast.makeText(contextForDialog, "Mesa "+ Main.m_Text + " La mesa esta abierta OK. ", Toast.LENGTH_LONG).show();
									    	break;
									    case 4:
									    	Toast.makeText(contextForDialog, "Mesa "+ Main.m_Text + " ERROR Conexión al POS. " + OK, Toast.LENGTH_LONG).show();
									    	break;
							    	}
							    	Main.agregado = true;	
							    	finish();
							    	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
								    }
								    else
								    {
								    	Toast.makeText(contextForDialog, "Mesa "+ Main.m_Text + " Ya está abierta. ", Toast.LENGTH_LONG).show();
								    	Main.agregado = true;	
								    	finish();
								    	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
								    }
							    }else
							    {
							    	Toast.makeText(contextForDialog, "Mesa "+ Main.m_Text + " ERROR Conexión al POS. ", Toast.LENGTH_LONG).show();	
							    	//finish();
							    	//overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
							    }
							}
							else
							{
								
								Toast.makeText(contextForDialog, "Sellecciona mesa y zona.", Toast.LENGTH_LONG).show();	
								//Main.agregado = true;	
						    	//finish();
						    	//overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
							}
						    
			            	
					}
	            
			
			});  
			
}		
			
private void CheckMenu() {
	//Mesa
	final TextView txtmesa = (TextView) findViewById(R.id.txtSelect);
	
	
	txtmesa.setOnClickListener(new  View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
							
			// custom dialog
			final Dialog dialog = new Dialog(contextForDialog);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.mesas);	
			
			
			//Main.m_Text="";
			final ListView lstZona =  (ListView) dialog.findViewById(R.id.lstZona);
			lstZona.setBackgroundColor( getResources().getColor(R.color.blanco));
			CheckBox ckreserva = (CheckBox) findViewById(R.id.ckReserva);
		    CheckBox ckvisita = (CheckBox) findViewById(R.id.ckVisita);
		    visit="false";
		    reservation="false";
		    
		    if (ckreserva.isChecked() )
		    	reservation = "true";
		    
		    if (ckvisita.isChecked())		    
		    	visit = "true";
		    
		    EditText txtpersonas = (EditText) findViewById(R.id.txtPersona);
		    pax = (String) txtpersonas.getText().toString();
		    
		    obtenerMesas();
			
			
			ArrayAdapter <String> dataAdapter = new ArrayAdapter(contextForDialog, android.R.layout.simple_list_item_checked, list);			
					
			
			lstZona.setAdapter(dataAdapter);
			Helper.getListViewSize(lstZona);
			lstZona.refreshDrawableState();
			
			lstZona.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					CheckedTextView textview = (CheckedTextView)view;
				    textview.setChecked(!textview.isChecked());
					result = textview.getText().toString();
					if (result == null)
					{
						temporal = "<zona>" + " Mesa " + result2;
					}
					
					if (result2 == null)
					{
						temporal = result + " Mesa " + "<número>";
					}
					
					if (result != null && result2 != null)
					{
						Main.m_Text=result + " Mesa " + result2;
					}
					
					
					
				}
			
			});
			
			final ListView lstNum =  (ListView) dialog.findViewById(R.id.lstNumero);
			lstNum.setBackgroundColor(getResources().getColor(R.color.blanco));
			
			
			ArrayAdapter <String> dataAdapter2 = new ArrayAdapter(contextForDialog, android.R.layout.simple_list_item_checked, list2);			
					
			
			lstNum.setAdapter(dataAdapter2);
			Helper.getListViewSize(lstNum);
			lstNum.refreshDrawableState();
			lstNum.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					CheckedTextView textview2 = (CheckedTextView)view;
				    textview2.setChecked(!textview2.isChecked());
					result2 = textview2.getText().toString();
					if (result == null)
					{
						temporal = "<zona>" + " Mesa " + result2;
					}
					
					if (result2 == null)
					{
						temporal = result + " Mesa " + "<número>";
					}
					
					if (result != null && result2 != null)
					{
						Main.m_Text=result + " Mesa " + result2;
					}
				}
			});
			Button dialogButton = (Button) dialog.findViewById(R.id.btnOK);
			
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if ( result != null && result2 != null )
					{
					
						Main.m_Text=result + " Mesa " + result2;
						txtmesa.setText(Main.m_Text);
						DB.open();
						//Obetener sid_table con la zona y el numero
						Mesa mesa = DB.getTableByZoneNum(result, result2);
						DB.close();
						Main.sid_table = mesa.getSidTable();
						
					}
					else
					{
						
						Toast.makeText(getApplicationContext(), "Mesa no seleccionada", Toast.LENGTH_LONG).show();
					}
					dialog.dismiss();	
			
				}
				
			});
			Button dialogButton2 = (Button) dialog.findViewById(R.id.btnCancel);
			dialogButton2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					//if  (!temporal.contains("null") || !temporal.contains("<zona>") || !temporal.contains("<número>"))
						//Main.m_Text = temporal;
					
					//txtmesa2.setText(Main.m_Text);
					dialog.cancel();
				}
			});
			dialog.show();			
			
		}

		
	});
}


/**
 *  Hace una llamada de servicio REST y obtiene la lista de las mesas y zonas.
 */
public static void obtenerMesas() {
		
	DB = new DBHelper(Main.contextForDialog);
	DB.open();
		mesaslist = DB.getListMesas();
		User user = DB.getUser();
		Login.mwkey = user.getUserMWKey();
	DB.close();
	
	if (mesaslist.isEmpty() )
	{
	
	String url="http://beta.tocarta.es/cli/mw/tables.json?mwkey="+Login.mwkey;
	int port=80;
	int timeout=10000;
	
	// Json resultado de tables
	JSONObject jsonMesa = null;			       
	// Creating JSON Parser instance
	JSONParser jParserMesa = new JSONParser();
	
	if (CheckConnect.serverIsAlive("beta.tocarta.es", port, timeout))
	{
		jsonMesa = jParserMesa.getJSONFromUrl(url);
		
		
		if ( jsonMesa != null )
		{
			tratarMesas(jsonMesa);
			
		}
	}
	}
			
			DB.open();
			mesaslist = DB.getListMesas();
			DB.close();
			if (mesaslist != null)
			{
				String zona = null;
				String numero = null;
				for(int i=0; i<mesaslist.size();i++)
				{
					
					zona = mesaslist.get(i).getZone();
					numero = mesaslist.get(i).getNumTable();
					int num = Integer.valueOf(numero);
					if (!list.contains(zona) || !Main.list.contains(zona))
					{
						Main.list.add(zona);
						list.add(zona);
					}
					if (!list2.contains(num) || !Main.list2.contains(numero))
					{
						list2.add(num);
						Main.list2.add(numero);
						
						
					}
				}
			}else
			{
				Toast.makeText(Main.contextForDialog,"Error de conexión", Toast.LENGTH_LONG).show();
			}
			
	}
	
	


private static void tratarMesas(JSONObject json) {
	
	// JSON Node names
	
	final String TAG_ID = "id";
	final String TAG_ID1 = "id_floor";
	final String TAG_ID2 = "id_table";
	final String TAG_NAME = "name";		
	final String TAG_SID="sid";
	final String TAG_SIDF="sid_floor";
	final String TAG_SIDT="sid_table";
	final String TAG_NUMBER="number";
	
    // JSONArray
 		JSONArray floors = null;
 		JSONArray tables = null;
 		
 	try {
        
 		
 		floors = json.getJSONArray("floors");
 		
 		for (int y=0; y < floors.length(); y++)
 		{
 			JSONObject floor = floors.getJSONObject(y);
 			
    		String id= floor.getString("id");
    		String sid=floor.getString("sid");
            String name = floor.getString("name");
            
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
             
            // adding each child node to HashMap key => value
            map.put(TAG_ID, id);
            map.put(TAG_SID, sid);
            map.put(TAG_NAME, name);
            

            // adding HashList to ArrayList
            floorList.add(map);
            
    	
            tables = floor.getJSONArray("tables");            
        
            for(int i = 0; i < tables.length(); i++){
    		
    		JSONObject c = tables.getJSONObject(i);
    		String idT= c.getString("id");
            String numberT = c.getString("number");
            String sidT = c.getString("sid");
         
            // creating new HashMap
            HashMap<String, String> map1 = new HashMap<String, String>();
             
            // adding each child node to HashMap key => value
            map1.put(TAG_ID1, id);
            map1.put(TAG_ID2, idT);
            map1.put(TAG_SIDF, sid);
            map1.put(TAG_SIDT, sidT);
            map1.put(TAG_NAME, name);
            map1.put(TAG_NUMBER, numberT);
            

            // adding HashList to ArrayList
            mesaList.add(map1);
            

            int id_floor = Integer.valueOf(id);
            int id_table = Integer.valueOf(idT);
            mesaslist.add(new Mesa(id_floor, id_table, sid, sidT, name, numberT, 2, 0)); //mesa cerrada = 2 y pax=0
            
            
            //INSERTAR EN BBDD tabla tables
            DB = new DBHelper(Main.contextForDialog);
            
            DB.open();
            //DB.removeUser();
            String result = DB.insertMesas(id_floor, id_table, sid, sidT, name, numberT, 2, 0);
            DB.close();
            
            }
            }
 		
 		
 	} catch (JSONException e) {
		
		e.printStackTrace();
	}
}
 	

private void CheckMenu2() {
	
	
	final TextView txtmesa2 = (TextView) findViewById(R.id.txtSelect);
	
	
			
			final String temporal = Main.m_Text;
			
			final ListView lstZone =  (ListView) this.findViewById(R.id.lstZone);
			lstZone.setBackgroundColor( getResources().getColor(R.color.blanco));
			
			CheckBox ckreserva = (CheckBox) findViewById(R.id.ckReserva);
			
		    CheckBox  ckvisita = (CheckBox) findViewById(R.id.ckVisita);
		    visit="false";
		    reservation="false";
		    
		    if (ckreserva.isChecked() )
		    	reservation = "true";
		    
		    if (ckvisita.isChecked())		    
		    	visit = "true";
		    
		    EditText txtpersonas = (EditText) findViewById(R.id.txtPersona);
		    pax = (String) txtpersonas.getText().toString();
		    
		    lstZone.setVisibility(ListView.VISIBLE);
		    final Button btnZona = (Button) this.findViewById(R.id.btnZona);
		    final Button btnNumero = (Button) this.findViewById(R.id.btnMesa);
		    //btnZona.setBackgroundResource(R.drawable.tab_selected_pressed_mewaiter);
			//btnNumero.setBackgroundResource(R.drawable.ab_solid_mewaiter);
		    
		    
		    
		    obtenerMesas();
			
			
			ArrayAdapter <String> dataAdapter = new ArrayAdapter(contextForDialog, android.R.layout.select_dialog_singlechoice, list);			
					
			
			lstZone.setAdapter(dataAdapter);
			lstZone.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					CheckedTextView textview = (CheckedTextView)view;
				    textview.setChecked(!textview.isChecked());
					result = textview.getText().toString();
					String temporal = null;
					if (result == null)
					{
						temporal = "<zona>" + " Mesa " + result2;
					}
					
					if (result2 == null)
					{
						temporal = result + " Mesa " + "<número>";
					}
					
					if (result != null && result2 != null)
					{
						temporal =result + " Mesa " + result2;
					}
					
					//if  (!temporal.contains("null") || !temporal.contains("<zona>") || !temporal.contains("<número>"))
						//Main.m_Text = temporal;
					
					txtmesa2.setText(temporal);
				}
			
			});
			
			final ListView lstNume =  (ListView) this.findViewById(R.id.lstNum);
			
			
			
			//ordenación de lista
			Collections.sort(list2);
			ArrayAdapter <String> dataAdapter2 = new ArrayAdapter(contextForDialog, android.R.layout.select_dialog_singlechoice, list2);			
					
			lstNume.setBackgroundColor( getResources().getColor(R.color.blanco));
			lstNume.setAdapter(dataAdapter2);
			
			lstNume.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					
					CheckedTextView textview2 = (CheckedTextView)view;
				    textview2.setChecked(!textview2.isChecked());
					result2 = textview2.getText().toString();
					String temporal = null;
					if (result == null)
					{
						temporal = "<zona>" + " Mesa " + result2;
					}
					
					if (result2 == null)
					{
						temporal = result + " Mesa " + "<número>";
					}
					
					if (result != null && result2 != null)
					{
						temporal =result + " Mesa " + result2;
					}
					
					
					txtmesa2.setText(temporal);
					
					}
				});
			
			// Boton Zona		
			
			btnZona.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
					
				 if (lstNume.getVisibility() == ListView.VISIBLE)
						lstNume.setVisibility(ListView.INVISIBLE);
					lstZone.setVisibility(ListView.VISIBLE);
					//btnZona.setBackgroundResource(R.drawable.tab_selected_pressed_mewaiter);
					//btnNumero.setBackgroundResource(R.drawable.ab_solid_mewaiter);
					btnZona.setBackgroundResource(R.drawable.tab_selected_mewaiter);
					btnNumero.setBackgroundResource(R.drawable.tab_unselected_mewaiter);	
			}
						
			});  	
			
			// Boton Numero
			
			btnNumero.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
								
				if (lstZone.getVisibility() == ListView.VISIBLE)
					lstZone.setVisibility(ListView.INVISIBLE);
				lstNume.setVisibility(ListView.VISIBLE);
				//btnNumero.setBackgroundResource(R.drawable.tab_selected_pressed_mewaiter);
				//btnZona.setBackgroundResource(R.drawable.ab_solid_mewaiter);
				btnNumero.setBackgroundResource(R.drawable.tab_selected_mewaiter);
				btnZona.setBackgroundResource(R.drawable.tab_unselected_mewaiter);				            	
			}
									
			});  
	
	}
 
	
/**
 * Muestra el menu option (presionando botón opciones android 
 */	
public boolean onCreateOptionsMenu(Menu menu) {
		
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mesa_actions, menu);
	    return true;
	}
	
	
	
	
/**
 * Define las acciones a tomar en cada caso del menu option
 */	
public boolean onOptionsItemSelected(MenuItem item) {
		
        switch (item.getItemId()) {
        case R.id.action_factura2:
        	if ( Main.m_Text != null && !Main.m_Text.isEmpty() )
        	{
        		startActivity(new Intent(this, Factura.class));
        		//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        		overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
        	}
        	else
        	{
        		if ( (result!=null && result2!=null) )
        		{
        			if ( !result.isEmpty() && !result2.isEmpty())
        			{
	        			Main.m_Text = result + " Mesa " + result2;
	        			startActivity(new Intent(this, Factura.class));
	            		//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	            		overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
        			}
        			else
        			{
        				Toast.makeText(contextForDialog, "Debes seleccionar una mesa.", Toast.LENGTH_LONG).show();
        			}
        		}else
        		{
        			Toast.makeText(contextForDialog, "Debes seleccionar una mesa.", Toast.LENGTH_LONG).show();
        		}
        	}
        	return true;
        case R.id.action_segundos2:
        
        	Main.checkSegundos();
        	return true;
        
        case R.id.imprimir2:
        	if (Main.m_Text != null)
        	{
        		startActivity(new Intent(this, Factura.class));
        		//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        		overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
        	}
            return true;
        default:
        return super.onOptionsItemSelected(item);
        }
    }
	
}
