package com.aforma.mewaiter.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.aforma.mewaiter.app.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.aforma.mewaiter.app.Settings;
import com.aforma.mewaiter.app.EnvioAllPedidos;
import com.aforma.mewaiter.app.AdapterSettings;
import com.aforma.mewaiter.utils.ActionBar;
import com.aforma.mewaiter.utils.CheckConnect;
import com.aforma.mewaiter.utils.JSONParser;
import com.aforma.mewaiter.utils.SeparatedListAdapter;
import com.aforma.mewaiter.utils.ActionBar.AbstractAction;
import com.aforma.mewaiter.utils.DBHelper;


/**
 * 
 * Es la activity principal de la App, donde se maneja todo lo demás.
 * 
 *
 */
public class Main extends Activity {
	
	public static Context contextForDialog = null;
	public static String m_Text = "";
	public static Context cntxofParent= null;
	public ActionBar actionbar;
	public String result="";
	public String result2="";
	public Context mContexto =null; 
	public static boolean agregado =false;
	public static Activity activity;
	public static String login = "no";
	public static DBHelper DB = null;
	public static int id_table = 0; 
	public static String sid_table;
	public static List<String> list = new ArrayList<String>();
	public static List<String> list2 = new ArrayList<String>();
	public String fecha = "Última Actualización: ";
	public static SeparatedListAdapter adapter;
	public static ListView journalListView;
	public ArrayList<Order> pendientes = new ArrayList<Order>();
	
			
	@Override
	protected void onResume(){
		super.onResume();
		
			activity = (Activity) Main.this;
			contextForDialog = this;
			//Realizar login
			
			if ( m_Text != "")
			{
				
				String[] separated = (Main.m_Text).split(" Mesa ");
				String zona = separated[0]; // Contiene el número de mesa
				String mesa = separated[1]; // Contiene el número de mesa
						
				if (mesa != null)
					id_table = Integer.parseInt(mesa);
				
			}
			DB = new DBHelper(getApplicationContext());	
			
		
			activity = (Activity) this;							
			setContentView(R.layout.main);
				
			
				//Draw Slider 
				showSlider();
			
					
				// Draw ActionBar
				actionbar = (ActionBar) findViewById(R.id.actionbar);
				actionbar.setTitle("Pedido");				
				
				
				
				actionbar.setHomeAction(new ActionSettings());
				actionbar.setDisplayHomeAsUpEnabled(true);
				int a= actionbar.getActionCount();
				
				if (a<1)
				  actionbar.addAction(new EnvioPedido());
				
				
				contextForDialog = this;
				
				if ( !agregado )
				{
						pedidos();
				}
				else
					pedidosRellenos();
	
				DB.open();
				Login.userdatos = DB.getUser();
				
				List<Restaurante> restaurant = new ArrayList<Restaurante>();
				restaurant = DB.getRestaurants();
				DB.close();
				if (restaurant.size() != 0)
				{
					Login.nameR = restaurant.get(0).getName();
					Login.pos_ip_address = restaurant.get(0).getIP();
					Login.access_key = restaurant.get(0).getAccesKEY();
				
				}
			
				
	}
		
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_actions, menu);
	    
	   
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_factura:
        	if (Main.m_Text != null)
        	{
        		startActivity(new Intent(this, Factura.class));
        		//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        		overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
        	}
        return true;
        case R.id.action_mesa:
	        startActivity(new Intent(this, AbrirMesa.class));
	        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	        return true;
        case R.id.action_segundos:
       
        	checkSegundos();
        	return true;
        	
        
        
        case R.id.imprimir:
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
	
	
	/** OnCreate
	 * Función que se ejecuta en la creación de la activity: main_activity 
	 * 
	 */
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		// Es necesario para que permita utilizar servicios REST de tipo POST.
		if (android.os.Build.VERSION.SDK_INT > 9){
    	StrictMode.ThreadPolicy policy =new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
    	}
  
       
       
        super.onCreate(savedInstanceState);
		
		
		activity = (Activity) Main.this;
		contextForDialog = this;
		//Realizar login
		DB = new DBHelper(getApplicationContext());
		
		
			setContentView(R.layout.main);
			activity = (Activity) Main.this;
					
			//Draw Slider 
			showSlider();
	
				
			// Draw ActionBar
			actionbar = (ActionBar) findViewById(R.id.actionbar);
			actionbar.setTitle("Pedido");				
			
			
			actionbar.setHomeAction(new ActionSettings());
			actionbar.setDisplayHomeAsUpEnabled(true);
			int a= actionbar.getActionCount();
			
			
			if (a<1)
			  actionbar.addAction(new EnvioPedido());
			
			
			contextForDialog = this;
			
			DB.open();
			
			long result = 0;
			
			result=DB.getUserInfo();
			
			DB.close();
			if (result == 1)
			{
				restaurante();
			}
			pedidos();		
				
		}		
		
	
		
	

	public void miCuenta() {
		
		View searchView;
		int searchViewResId=R.layout.micuenta;
		
		ScrollView scroll1 = (ScrollView) findViewById(R.id.scrollView1);
		int numero=0;
		numero=scroll1.getChildCount();
		if (numero != 0)
		{
			scroll1.removeAllViews();
		}
		searchView = getLayoutInflater().inflate(searchViewResId, null);
	
	    scroll1.addView(searchView);
	    
	   
	    actionbar.setTitle("Mi Cuenta");
	  
		actionbar.setDisplayHomeAsUpEnabled(true);
		int a= actionbar.getActionCount();
		
		if (a==1)
		{
			actionbar.removeAllActions();
					
		}
		actionbar.addAction(new GuardarCuenta());
		actionbar.refreshDrawableState();
		
		TextView txtNombre = (TextView) findViewById(R.id.txtNombre);
		txtNombre.setText(Login.first_name);
		
		TextView txtApellidos = (TextView) findViewById(R.id.txtApellidos);
		String apellidos = Login.middle_name + "  " + Login.last_name;
		txtApellidos.setText(apellidos);
		
		
		Button btnsincro = (Button) findViewById(R.id.btnSincro);
		
		btnsincro.setOnClickListener(new  View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				borradoDatos();				
				restaurante();
				
			}
			
			
				
			
		
		});

}
public void pedidos() {
	
	
	View searchView;
	int searchViewResId=R.layout.pedidos;
	ScrollView scroll1 = (ScrollView) findViewById(R.id.scrollView1);
	int numero=0;
	numero=scroll1.getChildCount();
	if (numero != 0)
	{
		scroll1.removeAllViews();
	}
	
	searchView = getLayoutInflater().inflate(searchViewResId, null);
    scroll1.addView(searchView);
    
    
    actionbar.setTitle("Pedido");	
   	  
	actionbar.setDisplayHomeAsUpEnabled(true);
	int a = actionbar.getActionCount();
	
	if (a==1)
	{
		actionbar.removeAllActions();
				
	}
	
	
	ListView my_list = (ListView) findViewById(R.id.lstpedidos);
	my_list.setVisibility(View.VISIBLE);
	//RelativeLayout pedidos = (RelativeLayout) findViewById(R.id.pedidos);
	TextView txtempty =  (TextView) findViewById(R.id.textEmpty);
	Button btnAdd = (Button)  findViewById(R.id.btnAgrega);
	
	if (!Main.m_Text.isEmpty() || Main.m_Text != null)
	{
		btnAdd.setVisibility(Button.VISIBLE);
	}
	else
	{
		btnAdd.setVisibility(Button.INVISIBLE);
	}
	if (my_list.getCount() == 0 )
	{
		actionbar.removeAllActions();
		actionbar.refreshDrawableState();
		
		//my_list.setVisibility(ListView.GONE);
		txtempty.setVisibility(txtempty.VISIBLE);
		
		
	}else
	{
		//my_list.setVisibility(View.VISIBLE);
		txtempty.setVisibility(txtempty.INVISIBLE);
		
		actionbar.addAction(new EnvioPedido());
		actionbar.refreshDrawableState();
	}
	checkMenu();
	cntxofParent= this;
	checkMesa();	
	checkCarta();
			
}

private void checkCarta() {
	
		final Button btnagregar= (Button) findViewById(R.id.btnAgrega);
		
		btnagregar.setOnClickListener(new  View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (m_Text.isEmpty())
				{
					Toast.makeText(getBaseContext(), "Selecciona mesa antes de añadir un pedido.", Toast.LENGTH_LONG).show(); 
				}
				else
				{	
				
				Intent i = new Intent(getApplicationContext(),Carta.class);
				startActivity(i);  
				//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); 
				overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
				}
			}
			
		});
		
		
		
	}




private void checkMenu() {
	// Mesa
	final TextView txtmesa = (TextView) findViewById(R.id.txtMesa);
	
	
	txtmesa.setOnClickListener(new  View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			startActivity(new Intent(Main.contextForDialog, AbrirMesa.class));
	        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		}

		
	}); 
	

	
	
}

/**
 * No se utiliza
 */
private void checkMesa() {
	
	// Agregar plato pinchando directamente en el listado de los platos
	ListView lstplatos = (ListView) findViewById(R.id.lstpedidos);
	lstplatos.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
        	
        	
			Intent i = new Intent(getApplicationContext(),Carta.class);
			startActivity(i);  
			//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
    
        }
    });
	
	
}

static void checkSegundos() {
	
	
	if (Main.m_Text.isEmpty() || m_Text == null)
	{
		
		Toast.makeText(Main.contextForDialog, "La mesa debe seleccionarse primero", Toast.LENGTH_LONG).show();
	}
	else
	{
		
		if (Main.sid_table==null || Main.sid_table.isEmpty())
		{
			String mesa[] = Main.m_Text.split(" Mesa ");
			String zona=mesa[0];
			String numero= mesa[1];
			Main.DB.open();
			Mesa mesasel = Main.DB.getTableByZoneNum(zona, numero);
			Main.sid_table = mesasel.getSidTable();
			Main.DB.close();
		}
				
		AlertDialog.Builder builder = new AlertDialog.Builder(Main.contextForDialog);

	
		builder.setMessage("Marchar Segundos mesa: " + Main.m_Text + " ?")
	       .setTitle("Marchar Segundos");

	
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               
	        	   Login.jsonSegundos=Login.getMarcharSegundos();
	        	   if (Login.jsonSegundos == null)
	        	   {
	        		   Toast.makeText(Main.contextForDialog, "ERROR. No se ha podido enviar (Marchar Segundos)", Toast.LENGTH_LONG).show();
	        		   
	        	   }
	        	   else
	        	   {
	        		   boolean  OK = Login.jsonSegundos.toString().contains("true");
	        		   if (OK)
	        		   {
	        			   Toast.makeText(Main.contextForDialog, "Se ha enviado (Marchar Segundos) a " + Main.m_Text , Toast.LENGTH_LONG).show();
	        		   }
	        		   else
	        		   {
	        			   Toast.makeText(Main.contextForDialog, "ERROR. No se ha podido enviar (Marchar Segundos)", Toast.LENGTH_LONG).show();
	        		   }
	        		   
	        	   }
	        	   
	           }
	       });
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               
	        	   dialog.cancel();
	           }
	       });


		AlertDialog dialog = builder.create();
		dialog.show();
  
}
			
	
}
public static int openTable(String zona, String mesa, String accion, String mwkey,
		String pax, String reservation, String visit, String ip) {
	
	// Json resultado
	JSONObject jsonTable = null;
				       
	// Creating JSON Parser instance
	JSONParser jParserTable = new JSONParser();
	int result = 0;
	
	String[] cadena =ip.split(":");
	
	int port = 8080;
	if (cadena.length > 1)
	{
		ip=cadena[1].toString().substring(2);
		port= Integer.valueOf(cadena[2].toString());
	}
	
	if ( CheckConnect.serverIsAlive(ip, port, 10000) )
	{
	String url="http://"+ip+":"+port+"/table?mwkey="+mwkey+"&table="+sid_table+"&method="+accion+"&pax="+pax+"&reservation="+reservation+"&first_time_visit="+visit;
	jsonTable = jParserTable.getJSONFromUrl(url);
    //return jsonRest;
	
	String resultado = null;
	if ( jsonTable == null )
	{
		return 3;
	}
	
	
	try {
		resultado = jsonTable.getString("success");
	} catch (JSONException e) {
		
		e.printStackTrace();
	}
	
	if ( resultado == "true" && accion == "open")
	{
		result = 1; 
	}else
	{
		if ( resultado == "true" && accion == "closed")
		{
			result = 2;
		}else
		{
			result = 3; 
		}
	}
	
	return result; // 1 Open, 2 Close, 3 error 4 no funciona la conexión
	}else
	{
		return 4;
	}
}


public static int checkopenTable(String zona, String mesa, String mwkey, String ip, int port) {
	
	// Json resultado
	JSONObject jsonTable = null;
				       
	// Creating JSON Parser instance
	JSONParser jParserTable = new JSONParser();
	int result = 0;
	
	
	int timeout=10000;
	String[] cadena = ip.split(":");
	
	
	if (cadena.length > 1)
	{
		ip=cadena[1].toString().substring(2);
		port= Integer.valueOf(cadena[2].toString());
	}
	
	if ( CheckConnect.serverIsAlive(ip, port, 10000) )
	{
		
		String url="http://"+ip+":"+port+"/table?mwkey="+mwkey+"&table="+sid_table;
		jsonTable = jParserTable.getJSONFromUrl(url);
    
	
		String resultado = null;
		if ( jsonTable == null )
		{
			return 3;
		}
	
	
		try {
			resultado = jsonTable.getString("success");
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		if ( resultado == "true")
		{
			result = 1; 
		}else
		{
				result = 2; 
		}
		return result; // 1 Open, 2 error 3 no funciona la conexión
	}else
	{
		return 3;
	}
}
public void pedidosPdtes() {
	
	View searchView;
	int searchViewResId=R.layout.pedidospend;
	ScrollView scroll1 = (ScrollView) findViewById(R.id.scrollView1);
	int numero=0;
	numero=scroll1.getChildCount();
	if (numero != 0)
	{
		scroll1.removeAllViews();
	}
	
	adapter = new SeparatedListAdapter(this);

	searchView = getLayoutInflater().inflate(searchViewResId, null);
    scroll1.addView(searchView);
    
    actionbar.setTitle("Pedidos Pendientes");	
    actionbar.setDisplayHomeAsUpEnabled(true);
	int a= actionbar.getActionCount();
	
	if (a==1)
	{
		actionbar.removeAllActions();
			
	}
	actionbar.refreshDrawableState();
	pendientes =  new ArrayList<Order>();
	adapter = new SeparatedListAdapter(this);
	DB.open();
	pendientes = DB.getAllOrders();
	DB.close();
	
	// Get a reference to the ListView holder
	journalListView = (ListView) this.findViewById(R.id.list_journal);
	
		refreshListView(pendientes, adapter);
		
		journalListView.setAdapter(adapter);
		Helper.getListViewSize2(journalListView);
		journalListView.refreshDrawableState();
		if (adapter.isEmpty())
		{
			journalListView.setVisibility(ListView.GONE);
		}
		else
		{
			journalListView.setVisibility(ListView.VISIBLE);
		}
						
		Button btnenviarall = (Button) this.findViewById(R.id.btnReenviar);
						 
		btnenviarall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
									
				
				if (adapter.isEmpty())
				{
					Toast.makeText(contextForDialog, "No hay nada para reenviar", Toast.LENGTH_LONG).show();
					
				}
				else
				{
				EnvioAllPedidos envioall = new EnvioAllPedidos();									
				int OK=-1;
				OK = envioall.performAction();
				}
			}

		});

		

}	
		
				
static void refreshListView(ArrayList<Order> pendientes, SeparatedListAdapter  adapter) {
	
	   String mesa_ant = new String();
	   String Nombre = "";
	   String sid_mesa = "";
	   String mesa = "";
	   ArrayAdapter<String> listadapter;
	   String[] cadena = null;
    
	int j =0;
	for(int i=0; i<pendientes.size();i++)
	{
		sid_mesa = pendientes.get(i).getSidTable();	
		DB.open();
		Mesa table = DB.getTableById(sid_mesa);
		ArrayList<Order> orders = DB.getOrderByTable(sid_mesa);
		DB.close();
		mesa_ant=mesa;
		mesa = table.getZoneNum();	
		
	
		cadena = new String[orders.size()];
		for (j=0;j<orders.size(); j++)
		{
	
			Nombre = orders.get(j).getProductName();
			sid_mesa = orders.get(j).getSidTable();				
			
			cadena[j] = Nombre;
		}
			
			listadapter = new ArrayAdapter<String>(Main.contextForDialog, R.layout.list_item2, cadena);
			cadena = new String[orders.size()];
			adapter.addSection(mesa, listadapter);
		}		
	if (pendientes.isEmpty())
	{
		adapter.headers.clear();
		adapter.sections.clear();
		
	}
}


public Map<String, ?> createItem(String title, String caption)
{
	final String ITEM_TITLE = "title";
    final String ITEM_CAPTION = "caption";

    Map<String, String> item = new HashMap<String, String>();
    item.put(ITEM_TITLE, title);
    item.put(ITEM_CAPTION, caption);
    return item;
}

public void app_info() {
	View searchView;
	int searchViewResId=R.layout.app_info;
	ScrollView scroll1 = (ScrollView) findViewById(R.id.scrollView1);
	int numero=0;
	numero=scroll1.getChildCount();
	if (numero != 0)
	{
		scroll1.removeAllViews();
	}
	
	searchView = getLayoutInflater().inflate(searchViewResId, null);
    scroll1.addView(searchView);
    actionbar.setTitle("App Info");	
    actionbar.setDisplayHomeAsUpEnabled(true);
	int a= actionbar.getActionCount();
	
	if (a==1)
	{
		actionbar.removeAllActions();
		
	}
	actionbar.refreshDrawableState();
	
	
	DB.open();
	long result = DB.getAppInfo();
	DB.close();
	
	TextView txtname = (TextView) findViewById(R.id.txtName);
	txtname.setText(Login.nameApp);
	
	TextView txtversion = (TextView) this.findViewById(R.id.txtVersion);
	txtversion.setText(Login.version);
	
	TextView txtos = (TextView) findViewById(R.id.txtOS);
	txtos.setText(Login.os);
	
	TextView txtterm = (TextView) this.findViewById(R.id.txtTerm);
	txtterm.setText(Login.term_of_use);
	TextView txtprivacy = (TextView) findViewById(R.id.txtPolicy);
	txtprivacy.setText(Login.privacy);
	
	TextView txtnews = (TextView) this.findViewById(R.id.txtNews);
	txtnews.setText(Login.whats_new);
	
	TextView txtlink = (TextView) this.findViewById(R.id.txtLink);
	txtlink.setText(Login.link_to_store);
	
}

private ArrayList<Settings> obtenerItems() {
    ArrayList<Settings> items = new ArrayList<Settings>();
     
   items.add(new Settings("android:drawable/ic_menu_agenda","Pedido",1));
    
    items.add(new Settings("android:drawable/ic_input_get", "Carta",2));
    items.add(new Settings("android:drawable/ic_menu_cc", "Mi Cuenta",3));
    items.add(new Settings("android:drawable/ic_menu_star", "Sugerencias",4));
    items.add(new Settings("android:drawable/ic_menu_send", "Pedidos por Enviar",5));
    items.add(new Settings("android:drawable/ic_dialog_info", "Acerca de la App",6));
    items.add(new Settings("android:drawable/ic_lock_power_off", "Salir",7));
   
    return items;
  }

	
	
		
	
public class ActionSettings extends AbstractAction {
	
	
	
	public ActionSettings() {
		super(android.R.drawable.ic_menu_preferences);
		
		
	}
	
	@Override
	public void performAction(View view){
		
		
		SlidingPaneLayout mpanes = (android.support.v4.widget.SlidingPaneLayout) findViewById(R.id.slidingPane) ;		
		
		
		if (mpanes.isOpen() == true )
			mpanes.closePane();
		else
			mpanes.openPane();
	
		
	}
}

	

	
	
private void showMenu() {
    final Dialog dialog = new Dialog(this,
            android.R.style.Theme_Translucent_NoTitleBar);

    // Setting dialogview
    Window window = dialog.getWindow();
    window.setGravity(Gravity.BOTTOM);


    dialog.setTitle(null);
    dialog.setContentView(R.layout.menupedido);
    dialog.setCancelable(true);
    Button dialogButton1 = (Button) dialog.findViewById(R.id.btnCancel2);
	dialogButton1.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			dialog.cancel();
		}
	});
	Button dialogButton2 = (Button) dialog.findViewById(R.id.btnAbrirMesa);
	dialogButton2.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			//setContentView(R.layout.activity_main);
			Intent i = new Intent(getApplicationContext(),AbrirMesa.class);
			startActivity(i); 
			//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		}
	});
	Button dialogButton3 = (Button) dialog.findViewById(R.id.btnFactura);
	dialogButton3.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			
			Intent i = new Intent(getApplicationContext(),Factura.class);
			startActivity(i); 
			//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		}
	});

    dialog.show();
}


public void showSlider() {
	
	// Draw Sliding 
	final SlidingPaneLayout mpanes = (android.support.v4.widget.SlidingPaneLayout) findViewById(R.id.slidingPane) ;
	mpanes.setParallaxDistance(100);
	
	
			ListView lv = (ListView)findViewById(R.id.optionList);
    
    ArrayList<Settings> itemsSettings = obtenerItems();
         
    AdapterSettings adapter;
    adapter= new AdapterSettings(this, itemsSettings);
         
    lv.setAdapter(adapter);  
    
    
    lv.setOnItemClickListener(new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            
          
        	
        	Settings item = (Settings) parent.getItemAtPosition(position);
        	
            
        	switch ((int) id)
        	{
        	case 1:
        		if ( !agregado )
				{
						pedidos();
				}
				else
					pedidosRellenos();
				
        		mpanes.closePane();        		
        		break; 
        		
        	case 2:
        		
        		if (m_Text.isEmpty())
				{
					Toast.makeText(getBaseContext(), "Selecciona mesa antes de añadir un pedido.", Toast.LENGTH_LONG).show(); 
				}
				else
				{	
	        		Intent i = new Intent(getApplicationContext(),Carta.class);
					startActivity(i);  
					//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
				}
				break;
				
        	case 3:
        		
        		miCuenta();
        		mpanes.closePane();
        		    		    
				break;
        	case 4:
        		
        		sugerencias();
        		mpanes.closePane();
        		
        		break;
				
        	case 7:            	            	
            	Main.m_Text="";
        		//Toast.makeText(getBaseContext(), "Adios !!", Toast.LENGTH_LONG).show();
            	
            	borradoDatos();
            	finish();
            	Intent i = new Intent(getApplicationContext(),Login.class);
				startActivity(i);  
				//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
            	break;
            	
        	case 5:
        		pedidosPdtes();
        		mpanes.closePane();
        		break;
        	case 6:
        		app_info();
        		mpanes.closePane();
        		break;
        		
            default:  
            	mpanes.closePane();
            	Toast.makeText(getBaseContext(), item.descripcion, Toast.LENGTH_LONG).show();
        		break;
        	}  
        }

	

		
  
        
   });		
}

public void borradoDatos() {
	
	DB.open();
	DB.removeDishes();
	DB.removeDishmods();
	DB.removeDishTypes();
	DB.removeMenu();
	DB.removeMesas();
	DB.removeMesas();
	DB.removeModifierList();
	DB.removeModifierListSets();
	DB.removeModifiers();
	DB.removeOrderMods();
	DB.removeOrders();
	DB.removeRestaurante();
	DB.removeSections();
	DB.removeSubsections();	
	DB.removeMesas();
	DB.removeDiscounts();
	DB.removeDiscountSel();
	DB.removeAppInfo();
	DB.removePayments();
	DB.removeUserInfo();
	
	DB.close();
}

public void pedidosRellenos() {
	
	
	View searchView;
	int searchViewResId=R.layout.pedidos;
	ScrollView scroll1 = (ScrollView) findViewById(R.id.scrollView1);
	int numero=0;
	numero=scroll1.getChildCount();
	if (numero != 0)
	{
		scroll1.removeAllViews();
	}
	
	searchView = getLayoutInflater().inflate(searchViewResId, null);
    scroll1.addView(searchView);
    scroll1.setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
    actionbar.setTitle("Pedido");	
   	  
	actionbar.setDisplayHomeAsUpEnabled(true);
	int a= actionbar.getActionCount();
	
	if (a==1)
	{
		actionbar.removeAllActions();
				
	}
	
	
	checkMenu();
	cntxofParent= this;
	checkMesa();	
	checkCarta();
	
			// Mesa 
			TextView txtmesa = (TextView) findViewById(R.id.txtMesa);
			
			txtmesa.setText(m_Text);
			
		    mContexto = this;
			relleno();
			
	}

private ArrayList<Listado> obtenerItems1() {
    ArrayList<Listado> items2 = new ArrayList<Listado>();
    
    
    DB.open();
    ArrayList<Order> orders = DB.getOrderByTable(sid_table);
    DB.close();
    
    for (int i=0; i<orders.size();i++)
    {
     	int idOrder = orders.get(i).getId();
    	DB.open();
        ArrayList<Ordermods> ordersmods = DB.getOrdermodsByID(idOrder);
        DB.close();
        String comentarios = orders.get(i).getNote();
        for (int j=0;j < ordersmods.size();j++)
        {
        	comentarios = comentarios + " | " + ordersmods.get(j).getValue();
        }
        String price = "0";
        if (orders.get(i).getPrice() != null)
        {
        	price=orders.get(i).getPrice();
        }
        	
        items2.add (new Listado(orders.get(i).getId(), orders.get(i).getProductName(), orders.get(i).getQuantity(), comentarios, price));
    		
    }
    		return items2;
}

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	  private final Context context;
	  private final String[] values;

	  public MySimpleArrayAdapter(Context context, String[] values) {
	    super(context, R.layout.list1, values);
	    this.context = context;
	    this.values = values;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.list1, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.item_list);
	    Button badge= (Button) rowView.findViewById(R.id.btnNumero);
	    textView.setText(values[position]);
	    badge.setText(values[position]);
	   

	    return rowView;
	  }
	} 

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
if (keyCode == KeyEvent.KEYCODE_BACK) {
   onBackPressed();

}

return super.onKeyDown(keyCode, event);
}

public void onBackPressed() {

return;
}

public void restaurante() {
	
	Login.task1 = new AsyncTask<Void, Void, Void>() {
		
		@Override
		protected void onPreExecute() {
			Login.pd = new ProgressDialog(contextForDialog);
			Login.pd.setTitle("Sincronizando");
			Login.pd.setMessage("Por favor espere...");
			Login.pd.setCancelable(false);
			Login.pd.setIndeterminate(true);
			Login.pd.show();
			Login.tarea=2;
		}
			
		@Override
		protected Void doInBackground(Void... arg0) {
			
			
			try {
				Login.jsonRestaurante = Login.getRestauranteInfo();
				Login.jsonModifiers = Login.getModifiers();
				Login.jsonSugerencias = Login.getSugerencias(); 
				Login.jsonPayments = Login.getPayments();
				Login.jsonAppinfo = Login.getAppInfo();
				Login.jsonUserinfo = Login.getUserInfo();
				Login.jsonDiscounts = Login.getDiscounts();
				Thread.sleep(10);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			if (Login.pd!=null) {
				
				Login.tarea=0;
				Login.pd.dismiss();
				
				if ( Login.jsonRestaurante == null  || Login.jsonModifiers == null )
				{
					final AlertDialog alertDialog = new AlertDialog.Builder(contextForDialog).create();
					alertDialog.setTitle("Sincronización");
					alertDialog.setMessage("Fallo en la sincronización ¿Deseas reintentar?");
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						SystemClock.sleep(10);
						alertDialog.dismiss();
						restaurante();
						
						
					}
					});
					alertDialog.setButton2("NO", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
							SystemClock.sleep(10);
							alertDialog.dismiss();							
							
							finish();
							android.os.Process.killProcess(android.os.Process.myPid());
							
						}
						});
					alertDialog.setIcon(R.drawable.icon);
					alertDialog.show();
					
				}
				else
				{
					Login.tarea=0;
					tratarCarta();
					
				}
				
				
			}
		}

			
	};
	Login.task1.execute((Void[])null);

}

public static void tratarCarta() {
	Login.task2 = new AsyncTask<Void, Void, Void>() {
				
					@Override
					protected void onPreExecute() {
						Login.pd = new ProgressDialog(contextForDialog);
						Login.pd.setTitle("Cargando datos");
						Login.pd.setMessage("Por favor espere...");
						Login.pd.setCancelable(false);
						Login.pd.setIndeterminate(true);
						Login.pd.show();
						Login.tarea=3;
					}
						
					@Override
					protected Void doInBackground(Void... arg0) {
						
						try {
							String estado = tratarRestaurantInfo(Login.jsonRestaurante);
							String estado2 = tratarModifiers(Login.jsonModifiers);
							String estado3 = tratarPayments(Login.jsonPayments);
							Login.sugerencias = tratarSugerencias(Login.jsonSugerencias);
							String estado4 = tratarAppInfo(Login.jsonAppinfo);
							String estado5 = tratarUserInfo(Login.jsonUserinfo);
							String estado6 = tratarDiscounts(Login.jsonDiscounts);
							AbrirMesa.obtenerMesas();
							if (estado.contains("OK") )
							{
								if  (estado2.contains("OK") ){
									
									cargarDB();
									
									
								}
									
							}
							Thread.sleep(1);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
					
						return null;
					}
					
					

					@Override
					protected void onPostExecute(Void result) {
						if (Login.pd!=null) {
							Login.pd.dismiss();
							
							Login.tarea=0;
							if ( Login.jsonRestaurante == null )
							{
								final AlertDialog alertDialog = new AlertDialog.Builder(contextForDialog).create();
								alertDialog.setTitle("Sincronización");
								alertDialog.setMessage("Fallo en la sincronización ¿Deseas reintentar?");
								alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									
									SystemClock.sleep(1);
									alertDialog.dismiss();
									tratarCarta();
									
									
								}
								});
								alertDialog.setButton2("NO", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
										SystemClock.sleep(1);
										alertDialog.dismiss();										
										
										Login.activityL.finish();
										android.os.Process.killProcess(android.os.Process.myPid());
										
									}
									});
								alertDialog.setIcon(R.drawable.icon);
								alertDialog.show();
								
							}
							
							
							
						}
					}

						
				};
				Login.task2.execute((Void[])null);
				
	
			}

static  void cargarDB() {
	
	//Creamos y abrimos la base de datos
	
		
		DB.open();	

		// Cargamos Restaurante
		ArrayList<Restaurante> rest = new ArrayList<Restaurante>();
		int resul1 = DB.removeRestaurante();
	
		Long resultado = DB.insertRestaurante(Login.nameR, Login.logo, Login.i18nbg, Login.pos_ip_address, Login.access_key);
		resultado = null;
		rest =(ArrayList<Restaurante>) DB.getRestaurants();
		
		//Cargamos Menu
		resul1 = DB.removeMenu();
		
		
		for(int i=0;i<Login.menuList.size();i++)
		{		
				resultado = DB.insertMenu( Login.menuList.get(i).get("id"), Login.menuList.get(i).get("sid"), Login.menuList.get(i).get("name"), Login.menuList.get(i).get("menu_type"), Login.menuList.get(i).get("price"));
		}
	
		
		//Cargamos Sections
		resul1 = DB.removeSections();			
		
		for(int i=0;i<Login.sectionsList.size();i++)
		{		
				resultado = DB.insertSection( Login.sectionsList.get(i).get("id"), Login.sectionsList.get(i).get("id_menu"), Login.sectionsList.get(i).get("sid"),Login.sectionsList.get(i).get("name"),
						Login.sectionsList.get(i).get("mini"), Login.sectionsList.get(i).get("thumbnail"), Login.sectionsList.get(i).get("hasBigsubSections"), Login.sectionsList.get(i).get("dishes_per_page") );
				
		}
		
		
		
		
		// Cargamos Subsections
		resul1 = DB.removeSubsections();
		
		for(int i=0;i<Login.subsectionsList.size();i++)
		{		
				resultado = DB.insertSubSection( Login.subsectionsList.get(i).get("id"), Login.subsectionsList.get(i).get("sid"), Login.subsectionsList.get(i).get("id_menu"), Login.subsectionsList.get(i).get("id_section"), 
						Login.subsectionsList.get(i).get("name"), Login.sectionsList.get(i).get("short_title") );
				
		}
		
		
		// Cargamos Dishes
		resul1 = DB.removeDishes();
		
		for(int i=0;i<Login.dishesList.size();i++)
		{		
				resultado = DB.insertDishes( Login.dishesList.get(i).get("id"), Login.dishesList.get(i).get("id_menu"), Login.dishesList.get(i).get("id_section"), 
						Login.dishesList.get(i).get("id_subsection"), Login.dishesList.get(i).get("sid"), Login.dishesList.get(i).get("name"),  Login.dishesList.get(i).get("price"), Login.dishesList.get(i).get("short_title"), Login.dishesList.get(i).get("description"), Login.dishesList.get(i).get("sd_dish_id") );
		}	
		
		//Cargamos modiferslist
		resul1= DB.removeModifierList();
		for(int i=0;i<Login.modifiersList.size();i++)
		{		
				resultado = DB.insertModifierList( Login.modifiersList.get(i).get("id"),Login.modifiersList.get(i).get("id_mls"),Login.modifiersList.get(i).get("sid"), Login.modifiersList.get(i).get("name"),  Login.modifiersList.get(i).get("is_mandatory"), Login.modifiersList.get(i).get("is_multioption"));
		}	
		
		//Cargamos modiferslistSets
		resul1= DB.removeModifierListSets();
		for(int i=0;i<Login.modifiersListSets.size();i++)
		{		
				resultado = DB.insertModifierListSets( Login.modifiersListSets.get(i).get("id"), Login.modifiersListSets.get(i).get("sid"), Login.modifiersListSets.get(i).get("name"));
		}	
		
		//Cargamos modifers
		resul1= DB.removeModifiers();
		
		for(int i=0;i<Login.modifiers.size();i++)
		{		
				resultado = DB.insertModifier( Login.modifiers.get(i).get("id_list"), Login.modifiers.get(i).get("id"), Login.modifiers.get(i).get("name"), Login.modifiers.get(i).get("sid"), Login.modifiers.get(i).get("sd_modifierid"),Login.modifiers.get(i).get("description"), Login.modifiers.get(i).get("price"));
		}	
	
		
		//Cargamos dishmods 
		resul1= DB.removeDishmods();
		for(int i=0;i<Login.dishmods.size();i++)
		{		
				resultado = DB.insertDishersMods( Login.dishmods.get(i).get("id"), Login.dishmods.get(i).get("id_mls"),  Login.dishmods.get(i).get("sid"));
		}
		
		resul1= DB.removePayments();
		for(int i=0;i<Login.pays.size();i++)
		{		
				resultado = DB.insertPayments( Login.pays.get(i).get("id"), Login.pays.get(i).get("sid"),  Login.pays.get(i).get("name"), Login.pays.get(i).get("key"));
		}
		
		resul1= DB.removeAppInfo();
		resultado = DB.insertAppInfo(Login.nameApp, Login.version, Login.os, Login.term_of_use, Login.privacy, Login.whats_new, Login.link_to_store);
		
		resul1= DB.removeUserInfo();
		resultado = DB.insertUserInfo(Login.username, Login.email, Login.first_name, Login.middle_name, Login.last_name, Login.birthday, Login.mobile_number, Login.device_id);
		
		resul1= DB.removeDiscounts();
		for(int i=0;i< Login.discountsList.size();i++)
		{		
				resultado = DB.insertDiscounts( Login.discountsList.get(i).get("sid"), Login.discountsList.get(i).get("name"), Login.discountsList.get(i).get("dtype"), 
						Login.discountsList.get(i).get("amount"), Login.discountsList.get(i).get("note"), Login.discountsList.get(i).get("section_id"), Login.discountsList.get(i).get("dish_id"), 
						Login.discountsList.get(i).get("restaurant_id"), Login.discountsList.get(i).get("menu_id"));
		}
		
		
		resul1= DB.removeOrders();
		resul1= DB.removeOrderMods();
		resul1= DB.removeDiscountSel();
		resul1= DB.removeMesas();
		
		
		DB.close();
	
	
}


private static String tratarPayments(JSONObject json) {
	
	// JSON Node names
	
	final String TAG_ID = "id";
	final String TAG_NAME = "name";		
	final String TAG_SID="sid";
	final String TAG_KEY="key";
	
	Login.pays = new ArrayList <HashMap<String,String>>();
	
    // Menu JSONArray
 		JSONArray jsonPayments = null;
 		

 	try {
        //Lista de Modificadores Sets
 		
 		jsonPayments = json.getJSONArray("payments");
 		
 		for (int y=0; y < jsonPayments.length(); y++)
 		{
 			JSONObject payment = jsonPayments.getJSONObject(y);
 			
    		String id  = payment.getString("id");
    		String sid=payment.getString("sid");
            String name = payment.getString("name");
            String key = payment.getString("key");
            
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
             
            // adding each child node to HashMap key => value
            
            map.put(TAG_ID, id);
            map.put(TAG_SID, sid);
            map.put(TAG_NAME, name);
            map.put(TAG_KEY, key);
            

            // adding HashList to ArrayList
            Login.pays.add(map);
 		}   
 		}catch (JSONException e) {
              
           	e.printStackTrace();
           	
        }
        return "OK";
 }
 	
private static String tratarAppInfo(JSONObject jsonAppInfo) {
	
	final String TAG_APP="waiter_app";
	JSONObject appinfo = null;
	
	try {
		appinfo = jsonAppInfo.getJSONObject(TAG_APP);
		Login.nameApp = appinfo.getString("name");
		Login.version = appinfo.getString("version");
		Login.os = appinfo.getString("os");
		Login.term_of_use = appinfo.getString("terms_of_use");
		Login.whats_new = appinfo.getString("whats_new");
		Login.link_to_store = appinfo.getString("link_to_store");
		Login.privacy = appinfo.getString("privacy_policy");
		
	} catch (JSONException e) {
		e.printStackTrace();
    }
	return "OK";
}
private static String tratarUserInfo(JSONObject jsonUserInfo) {
	
	final String TAG_USER="waiter";
	JSONObject userinfo = null;
	
	try {
		userinfo = jsonUserInfo.getJSONObject(TAG_USER);
		Login.username = userinfo.getString("username");
		Login.email = userinfo.getString("email");
		Login.first_name = userinfo.getString("first_name");
		Login.middle_name = userinfo.getString("middle_name");
		Login.last_name = userinfo.getString("last_name");
		Login.birthday = userinfo.getString("birthday");
		Login.mobile_number = userinfo.getString("mobile_number");
		Login.device_id = userinfo.getString("device_id");
		
	} catch (JSONException e) {
		e.printStackTrace();
    }
	return "OK";
}

private static String tratarDiscounts(JSONObject jsonDiscounts) {
	
	final String TAG_DISCOUNT="discounts";
	final String TAG_SID="sid";
	final String TAG_NAME="name";
	final String TAG_DTYPE="dtype";
	final String TAG_AMOUNT="amount";
	final String TAG_NOTE="note";
	final String TAG_REST="restaurant";
	final String TAG_SECTIONID="section_id";
	final String TAG_DISHID="dish_id";
	final String TAG_RESTID="restaurant_id";
	final String TAG_MENUID="menu_id";
	JSONArray disclist = null;
	
	try {
		
		Login.discountsList = new ArrayList <HashMap<String,String>>();
		
		disclist = jsonDiscounts.getJSONArray(TAG_DISCOUNT);
 		
 		for (int i=0; i < disclist.length(); i++)
 		{
 			JSONObject discounts = disclist.getJSONObject(i);
 			
    		
    		String sid= discounts.getString("sid");
            String name = discounts.getString("name");
            String dtype= discounts.getString("dtype");
    		String amount= discounts.getString("amount");
            String note = discounts.getString("note");
           
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            
            if (discounts.has("section_id"))
            {
	    		String section_id = discounts.getString("section_id");
	    		map.put(TAG_SECTIONID, section_id);
            }
            if (discounts.has("dish_id"))
            {
	    		String dish_id = discounts.getString("dish_id");
	    		map.put(TAG_DISHID, dish_id);
            }
            if (discounts.has("restaurant_id"))
            {
	    		String restaurant_id = discounts.getString("restaurant_id");
	    		map.put(TAG_RESTID, restaurant_id);
            }
            if (discounts.has("menu_id"))
            {
	    		String menu_id = discounts.getString("menu_id");
	    		map.put(TAG_MENUID, menu_id);
            }
           
             
            // adding each child node to HashMap key => value
           
            map.put(TAG_SID, sid);
            map.put(TAG_NAME, name);
            map.put(TAG_DTYPE, dtype);
            map.put(TAG_AMOUNT, amount);
            map.put(TAG_NOTE, note);
            
            Login.discountsList.add(map);
            
 		}
		
	} catch (JSONException e) {
		e.printStackTrace();
    }
	return "OK";
}
private static String tratarModifiers(JSONObject json) {
	
	// JSON Node names
	
	final String TAG_ID = "id";
	final String TAG_ID2 = "id_list";
	final String TAG_PRICE = "price";
	final String TAG_DESC = "description";
	final String TAG_ISMAND = "is_mandatory";
	final String TAG_ISMULTI = "is_multioption";		
	final String TAG_NAME = "name";		
	final String TAG_SDMOD ="sd_modifierid";	
	final String TAG_SID="sid";
	final String TAG_ID3="id_mls";
	Login.modifiersList = new ArrayList <HashMap<String,String>>();
	Login.modifiers = new ArrayList <HashMap<String,String>>();
	Login.modifiersListSets = new ArrayList <HashMap<String,String>>();
	Login.dishmods = new ArrayList <HashMap<String,String>>();
		
	
    // Menu JSONArray
 		JSONArray modifiers_list = null;
 		JSONArray modifier_list_sets = null;
 		JSONArray modifier = null;
 		JSONArray dishesmod = null;

 	try {
        //Lista de Modificadores Sets
 		
 		modifier_list_sets = json.getJSONArray("modifier_list_sets");
 		
 		for (int y=0; y < modifier_list_sets.length(); y++)
 		{
 			JSONObject mls = modifier_list_sets.getJSONObject(y);
 			
    		String id= mls.getString("id");
    		String sid=mls.getString("sid");
            String name = mls.getString("name");
            
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
             
            // adding each child node to HashMap key => value
            map.put(TAG_ID, id);
            map.put(TAG_SID, sid);
            map.put(TAG_NAME, name);
            

            // adding HashList to ArrayList
            Login.modifiersListSets.add(map);
            
    	
            modifiers_list = mls.getJSONArray("modifier_lists");            
        
            for(int i = 0; i < modifiers_list.length(); i++){
    		
    		JSONObject c = modifiers_list.getJSONObject(i);
    		String idML= c.getString("id");
            String nameML = c.getString("name");
            String sidML = c.getString("sid");
            String is_mandatory = c.getString("is_mandatory");
            String is_multioption = c.getString("is_multioption");
            
            // creating new HashMap
            HashMap<String, String> map1 = new HashMap<String, String>();
             
            // adding each child node to HashMap key => value
            map1.put(TAG_ID, idML);
            map1.put(TAG_ID3, id);
            map1.put(TAG_SID, sidML);
            map1.put(TAG_NAME, nameML);
            map1.put(TAG_ISMAND, is_mandatory);
            map1.put(TAG_ISMULTI, is_multioption);

            // adding HashList to ArrayList
           
            Login.modifiersList.add(map1);
            
           // Modificadores
            

        	modifier = c.getJSONArray("modifiers");            
            
        	for(int j = 0; j < modifier.length(); j++){
        		
        		JSONObject c2 = modifier.getJSONObject(j);
        		String idM= c2.getString("id");
                String nameM = c2.getString("name");
                String sidM = c2.getString("sid");
                String sd_modifierid = c2.getString("sd_modifierid");
                String description = c2.getString("description");
                String price = c2.getString("price");
                // creating new HashMap
                HashMap<String, String> map2 = new HashMap<String, String>();
                 
                // adding each child node to HashMap key => value
                map2.put(TAG_ID, idM);
                map2.put(TAG_ID2, idML);
                map2.put(TAG_SID, sidM);
                map2.put(TAG_NAME, nameM);
                map2.put(TAG_SDMOD, sd_modifierid);
                map2.put(TAG_DESC, description);
                map2.put(TAG_PRICE, price);
 
                // adding HashList to ArrayList
                Login.modifiers.add(map2);
                
            
        	}
        	
        	// DISHES
            

        	dishesmod = mls.getJSONArray("dishes");            
            
        	for(int k = 0; k < dishesmod.length(); k++){
        		
        		JSONObject c3 = dishesmod.getJSONObject(k);
        		String idD= c3.getString("id");
                String sidD = c3.getString("sid");
                
                // creating new HashMap
                HashMap<String, String> map3 = new HashMap<String, String>();
                 
                // adding each child node to HashMap key => value
                map3.put(TAG_ID, idD);  // id (id dish_mod)
                map3.put(TAG_ID3, id);  // id_mls
                map3.put(TAG_SID, sidD); // sid dish
                
                // adding HashList to ArrayList
                Login.dishmods.add(map3);
        		
        	}
            
            
    	}
 	}
   } catch (JSONException e) {
       
    	e.printStackTrace();
    	
    }
    return "OK";
   }



private static String tratarRestaurantInfo(JSONObject json) {
	
	// JSON Node names
	
	final String TAG_ID = "id";
	final String TAG_IP="pos_ip_address";
	final String TAG_TYPE = "menu_type";
	final String TAG_PRICE = "price";
	final String TAG_NAME = "name";
	final String TAG_SUB ="hasBigSubsections";
	final String TAG_DISH = "dishes_per_page";
	final String TAG_MINI = "mini";
	final String TAG_THUMBNAIL = "thumbnail";
	final String TAG_SHORTTITLE = "short_title";
	final String TAG_DESC = "description";
	final String TAG_LARGE = "large";
	final String TAG_LOGO = "logo";
	final String TAG_I18NBG = "i18nbg";
	final String TAG_SID = "sid";
	Login.restaurante = new HashMap<String, String>();
	Login.menuList = new ArrayList <HashMap<String,String>>();
	Login.sectionsList = new ArrayList <HashMap<String,String>>();
	Login.subsectionsList = new ArrayList <HashMap<String,String>>();
	Login.dishesList = new ArrayList <HashMap<String,String>>();
	
    // Menu JSONArray
 		JSONArray menu = null;
 		JSONArray sections = null;
 		JSONArray subsections = null;
 		JSONArray dishes = null;
 		JSONArray disktypes = null;
 		JSONObject settings = null;
    
    
    try {
        //Restaurante
    	
    	
    	
    	Login.nameR = json.getString("name");
    	Login.logo = json.getString("logo");
    	Login.i18nbg = json.getString("i18nbg");
    	Login.pos_ip_address = json.getString("pos_ip_address");
    	
    	
    	settings = json.getJSONObject("setting");            
        Login.access_key = settings.getString("access_key");
        
        // adding each child node to HashMap key => value
        Login.restaurante.put(TAG_LOGO, Login.logo);
        Login.restaurante.put(TAG_NAME, Login.nameR);
        Login.restaurante.put(TAG_I18NBG, Login.i18nbg);
        Login.restaurante.put("access_key", Login.access_key);
        Login.restaurante.put(TAG_IP, Login.pos_ip_address);
      
        
                	
    	// Getting Array of Menu
    	menu = json.getJSONArray("menus");
         
        // looping through Menus
        for(int i = 0; i < menu.length(); i++){
            
        	JSONObject c = menu.getJSONObject(i);
            String id_menu= c.getString("id");
         
            String idM = c.getString("id");
            String sidM = c.getString("sid");
            String nameM = c.getString("name");
            String menu_type = c.getString("menu_type");
            String price = c.getString("price");
            
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
             
            // adding each child node to HashMap key => value
            map.put(TAG_ID, idM);
            map.put(TAG_SID, sidM);
            map.put(TAG_NAME, nameM);
            map.put(TAG_TYPE, menu_type);
            map.put(TAG_PRICE, price);

            // adding HashList to ArrayList
            Login.menuList.add(map);
            
            
            
            
            //Looping through sections 
            sections = c.getJSONArray("sections");
            
            for(int j= 0; j < sections.length();j++)
            {
            	  JSONObject c2 = sections.getJSONObject(j);
            	  String id_section = c2.getString("id");
            	
            	// Storing each json item in variable
                String id = c2.getString("id");
                String sidS = c2.getString("sid");
                String name = c2.getString("name");
                String hasBigSubsections = c2.getString("hasBigSubsections");
                String dishes_per_page = c2.getString("dishes_per_page");
                String mini = c2.getString("mini");
                String thumbnail = c2.getString("thumbnail");
                // creating new HashMap
                HashMap<String, String> map_sections = new HashMap<String, String>();
                 
                // adding each child node to HashMap key => value
                map_sections.put(TAG_ID, id);
                map_sections.put("id_menu", id_menu);
                map_sections.put(TAG_SID, sidS);
                map_sections.put(TAG_NAME, name);
                map_sections.put(TAG_SUB, hasBigSubsections);
                map_sections.put(TAG_DISH, dishes_per_page);
                map_sections.put(TAG_MINI, mini);
                map_sections.put(TAG_THUMBNAIL, thumbnail);
 
                // adding HashList to ArrayList
                Login.sectionsList.add(map_sections);
                
                
            	subsections = c2.getJSONArray("subsections");
                
                if ( subsections.length() > 0)
                {
                	//subsections = c2.getJSONArray("subsections");
                    for(int z=0; z < subsections.length();z++)
                    {
                    	JSONObject c3 = subsections.getJSONObject(z);
                    	 
                    	String id_subsection= c3.getString("id");
                    	
                    	// Storing each json item in variable
                        String idSub = c3.getString("id");
                        String sidSub = c3.getString("sid");
                        String nameSub = c3.getString("name");
                        String short_title = c3.getString("short_title");                       
                        //String miniSub = c3.getString("mini");
                        
                        // creating new HashMap
                        HashMap<String, String> map_subsections = new HashMap<String, String>();
                         
                        // adding each child node to HashMap key => value
                        map_subsections.put(TAG_ID, idSub);
                        map_subsections.put("id_menu", id_menu);
                        map_subsections.put("id_section", id_section);
                        map_subsections.put("sid", sidSub);
                        map_subsections.put(TAG_NAME, nameSub);
                        map_subsections.put(TAG_SHORTTITLE, short_title);
                        //map_subsections.put(TAG_MINI, miniSub);
                                
                        // adding HashList to ArrayList
                        Login.subsectionsList.add(map_subsections);
                        
                        // Dishes
                        dishes = c3.getJSONArray("dishes");
                        for(int k= 0; k < dishes.length();k++)
                        {
                        	  JSONObject c4 = dishes.getJSONObject(k);
                        	  
                        	
                        	// Storing each json item in variable
                            String idD = c4.getString("id");
                            String nameD = c4.getString("name");
                            String priceD = c4.getString("price");
                            String sidD = c4.getString("sid");
                            String short_titleD = c4.getString("short_title");
                            String sd_dish_id = c4.getString("sd_dish_id");
                            String description = c4.getString("description");
                            
                            // creating new HashMap
                            HashMap<String, String> map_dishes = new HashMap<String, String>();
                             
                            // adding each child node to HashMap key => value
                            map_dishes.put(TAG_ID, idD);
                            map_dishes.put("id_menu", id_menu);
                            map_dishes.put("id_section", id_section);
                            map_dishes.put("id_subsection", id_subsection);
                            map_dishes.put(TAG_SID, sidD);
                            map_dishes.put(TAG_NAME, nameD);
                            map_dishes.put(TAG_PRICE, priceD);
                            map_dishes.put(TAG_SHORTTITLE, short_titleD);
                            map_dishes.put(TAG_DESC, description);
                            map_dishes.put("sd_dish_id", sd_dish_id);
                            //map_dishes.put(TAG_LARGE, large);
                            
             
                            // adding HashList to ArrayList
                            Login.dishesList.add(map_dishes);
                    
                        }
                    }
                }
                else
                {
                	
                	// Dishes
                    dishes = c2.getJSONArray("dishes");
                    for(int k= 0; k < dishes.length();k++)
                    {
                    	  JSONObject c4 = dishes.getJSONObject(k);
                    	
                    	// Storing each json item in variable
                        String idD = c4.getString("id");
                        String nameD = c4.getString("name");
                        String priceD = c4.getString("price");
                        String sidD = c4.getString("sid");
                        String short_titleD = c4.getString("short_title");
                        String sd_dish_id = c4.getString("sd_dish_id");
                        String description = c4.getString("description");
                       
                        // creating new HashMap
                        HashMap<String, String> map_dishes = new HashMap<String, String>();
                         
                        // adding each child node to HashMap key => value
                        map_dishes.put(TAG_ID, idD);
                        map_dishes.put("id_menu", id_menu);
                        map_dishes.put("id_section", id_section);
                        // No hay subsections
                        map_dishes.put("id_subsection", "0");
                        map_dishes.put(TAG_SID, sidD);
                        map_dishes.put(TAG_NAME, nameD);
                        map_dishes.put(TAG_PRICE, priceD);
                        map_dishes.put(TAG_DESC, description);
                        map_dishes.put("sd_dish_id", sd_dish_id);
                        //map_dishes.put(TAG_LARGE, large);
                        map_dishes.put(TAG_SHORTTITLE, short_titleD);
         
                        // adding HashList to ArrayList
                        Login.dishesList.add(map_dishes);
                	
                }
                         
            	
            }
            }
            
            
       
        }
    } catch (JSONException e) {
       
    	e.printStackTrace();
    	
    }
    return "OK";
   }
private static String tratarSugerencias(JSONObject jsonSugerencias) {
	
	final String TAG_SUG="suggestions";
	
	String sugerencia = "";
	try {
		sugerencia = jsonSugerencias.getString(TAG_SUG);
		
	} catch (JSONException e) {
		e.printStackTrace();
    }
	return sugerencia;
}



@Override
public void onPause() {
	super.onPause();
	
	
	if(Login.pd != null)
		Login.pd.dismiss();
	switch (Login.tarea)
	{
	case 1:
		Login.task.cancel(true);
		Login.pd.dismiss();
		break;
	case 2:
		Login.task1.cancel(true);
		Login.pd.dismiss();
		break;
	case 3:
		Login.task2.cancel(true);
		Login.pd.dismiss();
		break;
	}
	
}

public void sugerencias() {
View searchView;
int searchViewResId=R.layout.sugerencias;
ScrollView scroll1 = (ScrollView) findViewById(R.id.scrollView1);
int numero=0;
numero=scroll1.getChildCount();
if (numero != 0)
{
	scroll1.removeAllViews();
}

searchView = getLayoutInflater().inflate(searchViewResId, null);
scroll1.addView(searchView);
actionbar.setTitle("Sugerencias");	
actionbar.setDisplayHomeAsUpEnabled(true);
int a= actionbar.getActionCount();

if (a==1)
{
	actionbar.removeAllActions();
		
}
actionbar.refreshDrawableState();

String[] separated = Login.sugerencias.split("\n");

TextView title = (TextView) findViewById(R.id.txtTitulo);
title.setText(separated[0]);
final TextView txtFecha = (TextView) findViewById(R.id.txtFecha);
txtFecha.setText(fecha);
final LinearLayout ll = (LinearLayout) findViewById(R.id.laySug);

int total=separated.length-2;
for (int i=1;i<=total;i++)
{
	String salto="\r";
	String line=separated[i];
	int comp = line.compareTo(salto);
	if (comp > 0)
	{
		final CheckBox cb = new CheckBox(this);
        cb.setText(separated[i]);
        int black = getResources().getColor(android.R.color.black);
        cb.setTextColor(black);
        cb.setClickable(false);
        cb.setChecked(true);
        cb.setId(i);
        ll.addView(cb);
	}
}

TextView title2 = (TextView) this.findViewById(R.id.TextView06);
total++;
title2.setText(separated[total]);



Button btnSugerencias = (Button) findViewById(R.id.btnSugerencias);
btnSugerencias.setOnClickListener(new  View.OnClickListener() {

	@Override
	public void onClick(View v) {
		
		

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        
		
		Login.jsonSugerencias = Login.getSugerencias();
		Login.sugerencias = tratarSugerencias(Login.jsonSugerencias);
		
		fecha = "Última Actualización: " + formattedDate;
		txtFecha.setText(fecha);
		sugerencias();
		
	}
	});

}

public static void vaciarPedido() {
	
final ListView lv = (ListView) Main.activity.findViewById(R.id.lstpedidos);
    
    final ArrayList<Listado> list = new ArrayList<Listado>();
         
    final AdapterItems adapter;
    adapter= new AdapterItems(Main.activity, list);
    
    lv.setAdapter(adapter);  
    Helper.getListViewSize(lv);
    
    TextView txtempty =  (TextView) Main.activity.findViewById(R.id.textEmpty);
	RelativeLayout pedidos = (RelativeLayout) Main.activity.findViewById(R.id.pedidos);
	Button btnAdd = (Button)  Main.activity.findViewById(R.id.btnAgrega);
	
	txtempty.setVisibility(txtempty.VISIBLE);
	
	//lv.setVisibility(ListView.GONE);
	btnAdd.setVisibility(Button.INVISIBLE);
	
	final TextView txtmesa = (TextView) Main.activity.findViewById(R.id.txtMesa);
	
	txtmesa.setText(Main.m_Text);
  
}

private void relleno() {
	
	// Relleno pedido
	
	final ListView lv = (ListView)findViewById(R.id.lstpedidos);
    
    final ArrayList<Listado> list = obtenerItems1();
    
    
         
    final AdapterItems adapter;
    adapter= new AdapterItems(this, list);    
   
    lv.setAdapter(adapter); 
    if (lv.getCount() != 0)
    {
    	Helper.getListViewSize(lv); 
    	
    }
    
    final Button btnAdd = (Button) findViewById(R.id.btnAgrega);
    if ( Main.m_Text.isEmpty() || Main.m_Text == null)
    {
    	btnAdd.setVisibility(Button.INVISIBLE);
    }
    else
    {
    	btnAdd.setVisibility(Button.VISIBLE);
    }
    
    final TextView txtvacio =  (TextView) findViewById(R.id.textEmpty);
    final RelativeLayout pedidos = (RelativeLayout) findViewById(R.id.pedidos);
    if(adapter.getCount()!=0){
    	
    		actionbar.removeAllActions();
    		actionbar.addAction(new EnvioPedido());
    		actionbar.refreshDrawableState();
    		txtvacio.setVisibility(TextView.INVISIBLE);
    		lv.setVisibility(ListView.VISIBLE);
    		//pedidos.setVisibility(RelativeLayout.VISIBLE);
    		
    		
    		
    	
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
 	   @Override
		public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				
 		   		Intent i = new Intent(getBaseContext(), DetalleOrder.class);
                Listado selectedFromList = (Listado)(lv.getItemAtPosition(arg2));
            	int ident = selectedFromList.id;
            	i.putExtra("id_order", ident);
                startActivity(i);
                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
            };
           });
    
    
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            
        	public boolean onItemLongClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
             
        		
            	Listado selectedFromList = (Listado)(lv.getItemAtPosition(myItemInt));
            	final int I = myItemInt;
            	final int ident = selectedFromList.id;
            	
              
                AlertDialog.Builder builder = new AlertDialog.Builder(mContexto);
            	builder.setMessage("¿Desea eliminar el plato: " + selectedFromList + " ?")        	        	
            	.setTitle("Eliminar Plato")
            	.setIcon(android.R.drawable.ic_menu_send);
            	
            	
            	//Add the buttons
            	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	
                	DB.open();
                	String identificador=String.valueOf(ident);
                	long result = DB.removeOrderById(identificador);
                	ArrayList<Ordermods> ordermods = DB.getOrdermodsByID(ident);
                	long result2 = DB.removeOrderModsByID(ident);
                	long result3 = DB.removeDiscountSelByID(ident);
                	DB.close();
                	
                	if (result > 0 || (result2 > 0 && ordermods.size() != 0))
                	{
                		
	                    // User clicked OK button
	                	AlertDialog.Builder builder2 = new AlertDialog.Builder(mContexto);
	                	builder2.setMessage("Eliminado OK")        	        	
	                	.setTitle("Eliminar Plato")
	                	.setIcon(android.R.drawable.ic_menu_send);
	                	
	                	
	                	builder2.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
	    					
	    					@Override
	    					public void onClick(DialogInterface dialog, int which) {
	    						
	    						relleno();
	    						
	    					}
							
	    				});
	                	builder2.create();
	                	builder2.show();
                		
                	
                		}else
	                	{
	                		// User clicked OK button
		                	AlertDialog.Builder builder2 = new AlertDialog.Builder(mContexto);
		                	builder2.setMessage("Eliminado Error")        	        	
		                	.setTitle("Eliminar Plato")
		                	.setIcon(android.R.drawable.ic_menu_send);
		                	
		                	
		                	builder2.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
		    					
		    					@Override
		    					public void onClick(DialogInterface dialog, int which) {
		    						
		    						relleno();
		    						
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
               
				return true;
                
            }
        });
    
        
        
    }
else{
       
       txtvacio.setVisibility(TextView.VISIBLE);
	   lv.setVisibility(ListView.GONE);
	   
       actionbar.removeAllActions();
   	   actionbar.refreshDrawableState();
       
       
} 
   
}


			
}