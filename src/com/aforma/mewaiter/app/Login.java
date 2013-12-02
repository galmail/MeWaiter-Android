package com.aforma.mewaiter.app;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.aforma.mewaiter.utils.CheckConnect;
import com.aforma.mewaiter.utils.DBHelper;
import com.aforma.mewaiter.utils.JSONParser;
import com.aforma.mewaiter.app.R;

/**
 * 
 * Clase para la Activity del Login
 * Se realiza el login y se guardan los datos del user.
 */
public class Login extends Activity implements OnClickListener {

	private static final int DONE = 0;
	
	public static int mState;
	
    public static DBHelper BD; 
	public static String nameR; 
	public static String logo; 
	public static String i18nbg; 
	public static String pos_ip_address; 
	public static String access_key;
	public static String nameApp;
	public static String version;
	public static String os;
	public static String term_of_use;
	public static String whats_new; 
	public static String link_to_store; 
	public static String privacy; 
	public static String username;
	public static String email;
	public static String first_name;
	public static String middle_name;
	public static String last_name;
	public static String birthday;
	public static String mobile_number;
	public static String device_id;
	
	public static HashMap<String, String> restaurante = new HashMap<String, String>();
	public static ArrayList<HashMap<String, String>> menuList = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> sectionsList = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> subsectionsList = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> dishesList = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> modifiersList = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> modifiersListSets = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> modifiers = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> dishmods = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> pays = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> discountsList = new ArrayList<HashMap<String, String>>();
	public static Context LoginContext=null;
	public static String sugerencias="";
	
   
    public static String mwkey ="";

	public static JSONObject jsonRestaurante = null;
	public static JSONObject jsonModifiers = null;
	public static JSONObject jsonSugerencias = null;
	public static JSONObject jsonPayments = null;
	public static JSONObject jsonAppinfo = null;
	public static JSONObject jsonUserinfo = null;
	public static JSONObject jsonDiscounts = null;
	public static JSONObject jsonSegundos = null;
	
	public static Activity activityL = null;
	Button btnStartProgress;
	ProgressDialog progressBar;
	
	private ProgressDialog dialog;
	public static User userdatos;
 
	
	public static Button btbAceptar;
	public static ProgressDialog pd;
	public Long result2;
	public static AsyncTask<Void, Void, Void> task;
	public static AsyncTask<Void, Void, Void> task1;
	public static AsyncTask<Void, Void, Void> task2;
	public static int tarea = 0;

	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
			
			
			
			BD=new DBHelper(getApplicationContext());
			BD.open();
			long result=0;
			result = BD.getUserInfo();
			BD.close();
			
			task=null;
			
			if (result == 1)
			{
			
			setContentView(R.layout.lander);
			Button btnComenzar = (Button) findViewById(R.id.btbComenzar);
			
			btnComenzar.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					
						
						setContentView(R.layout.login);
						task=null;
						activityL = (Activity) Login.this;
						LoginContext=Login.this;
						btbAceptar = (Button) findViewById(R.id.btbaceptar);
						btbAceptar.setOnClickListener(Login.this);
					
					}
			});
	}
	else
	{
		//tarea=0;
		finish();
		Intent main_intent = new Intent(getApplicationContext(), Main.class);
		startActivity(main_intent);
		//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	
	}
}	
	
	
	/**
	 * 
	 * No utilizado en esta version esta funci—n se realiza el la activity Main.
	 * 
	 */
	public void restaurante() {
					
					task1 = new AsyncTask<Void, Void, Void>() {
						
						@Override
						protected void onPreExecute() {
							pd = new ProgressDialog(LoginContext);
							pd.setTitle("Sincronizando");
							pd.setMessage("Por favor espere...");
							pd.setCancelable(false);
							pd.setIndeterminate(true);
							pd.show();
							tarea=2;
						}
							
						@Override
						protected Void doInBackground(Void... arg0) {
							
							
							try {
								jsonRestaurante = getRestauranteInfo();
								jsonModifiers = getModifiers();
								jsonSugerencias = getSugerencias();
								Thread.sleep(1);
							} catch (InterruptedException e) {
								
								e.printStackTrace();
							}
							
							
							return null;
						}
						
						
						@Override
						protected void onPostExecute(Void result) {
							if (pd!=null) {
								
								tarea=0;
								pd.dismiss();
								btbAceptar.setEnabled(true);
								if ( jsonRestaurante == null  || jsonModifiers == null)
								{
									final AlertDialog alertDialog = new AlertDialog.Builder(LoginContext).create();
									alertDialog.setTitle("Sincronizaci—n");
									alertDialog.setMessage("Fallo en la sincronizaci—n ÀDeseas reintentar?");
									alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
										SystemClock.sleep(1);
										alertDialog.dismiss();
										restaurante();
										
										
									}
									});
									alertDialog.setButton2("NO", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											
											SystemClock.sleep(1);
											alertDialog.dismiss();
											
											finish();
											overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
											android.os.Process.killProcess(android.os.Process.myPid());
											
										}
										});
									alertDialog.setIcon(R.drawable.icon);
									alertDialog.show();
									
								}
								else
								{
									tarea=0;
									tratarCarta();
									Intent i = (Intent) new Intent(getApplicationContext(), Main.class);
									startActivity(i);
									//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
									overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
								}
								
								
							}
						}

							
					};
					task1.execute((Void[])null);
		
	}
/**
 * 
 * Este metodo hace el tratamiento del json recibido por el servicio REST get_restaurant y lo guarda en estructuras de datos 
 * posteriormente se llamar‡ a cargarBD para guardar los datos en la BBDD SQLite.
 * 
 */
	public static void tratarCarta() {
		task2 = new AsyncTask<Void, Void, Void>() {
					
						@Override
						protected void onPreExecute() {
							pd = new ProgressDialog(LoginContext);
							pd.setTitle("Cargando datos");
							pd.setMessage("Por favor espere...");
							pd.setCancelable(false);
							pd.setIndeterminate(true);
							pd.show();
							tarea=3;
						}
							
						@Override
						protected Void doInBackground(Void... arg0) {
							
							try {
								
								String estado = tratarRestaurantInfo(jsonRestaurante);
								String estado2 = tratarModifiers(jsonModifiers);
								sugerencias = tratarSugerencias(jsonSugerencias);
								AbrirMesa.obtenerMesas();
								if (estado.contains("OK") )
								{
									if  (estado2.contains("OK")){
						
										cargarBD();
									}
								}
								Thread.sleep(20);
							} catch (InterruptedException e) {
								
								e.printStackTrace();
							}
						
							return null;
						}
						
						

						@Override
						protected void onPostExecute(Void result) {
							if (pd!=null) {
								pd.dismiss();
								btbAceptar.setEnabled(true);
								tarea=0;
								if ( jsonRestaurante == null )
								{
									final AlertDialog alertDialog = new AlertDialog.Builder(LoginContext).create();
									alertDialog.setTitle("Sincronizaci—n");
									alertDialog.setMessage("Fallo en la sincronizaci—n ÀDeseas reintentar?");
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
											
											//Main.activity.finish();
											Login.activityL.finish();
											Login.activityL.overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
											android.os.Process.killProcess(android.os.Process.myPid());
											
										}
										});
									alertDialog.setIcon(R.drawable.icon);
									alertDialog.show();
									
								}
								
								
								
							}
						}

							
					};
					task2.execute((Void[])null);
					
		
				}

				@Override
				public void onClick(View v) {
					
					
						v.setEnabled(false);
						task = new AsyncTask<Void, Void, Void>() {
							
							@Override
							protected void onPreExecute() {
								pd = new ProgressDialog(LoginContext);
								pd.setTitle("Conectando");
								pd.setMessage("Por favor espere...");
								pd.setCancelable(false);
								pd.setIndeterminate(true);
								pd.show();
								tarea=1;
							}
								
							@Override
							protected Void doInBackground(Void... arg0) {
								
								
								try {
									result2 = Login();
									Thread.sleep(10);
								} catch (InterruptedException e) {
									
									e.printStackTrace();
								}
								return null;
							
								
							}
							
							
							@Override
							protected void onPostExecute(Void result) {
								if (pd!=null) {
									
									
									tarea=0;
									pd.dismiss();
									btbAceptar.setEnabled(true);
									if ( result2 == -1 )
									{
										final AlertDialog alertDialog = new AlertDialog.Builder(LoginContext).create();
										alertDialog.setTitle("Login.");
										alertDialog.setMessage("Login incorrecto.");
										alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											
											SystemClock.sleep(10);
											alertDialog.dismiss();
											
											
											
										}
										});
										/*alertDialog.setButton2("NO", new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
												
												SystemClock.sleep(10);
												alertDialog.dismiss();										
												
												finish();
												android.os.Process.killProcess(android.os.Process.myPid());
												
											}
											});*/
										
										alertDialog.setIcon(R.drawable.icon);
										alertDialog.show();
										
									}
									else
									{
										tarea=0;
										finish();
										Intent i = (Intent) new Intent(getApplicationContext(), Main.class);
										startActivity(i);
										//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
										overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
									}
									
									
								}
							}
								
						};
						task.execute((Void[])null);
						
						
					}
		

	protected long Login() {
		
		long result = -1 ;
		
		EditText txtmwkey = (EditText) findViewById(R.id.ednombre);
		String DeviceID = getDeviceID();
		mwkey = txtmwkey.getText().toString();
		
				// url to make request
				
				String url = "http://beta.tocarta.es/cli/mw/login.json?mwkey=" + mwkey + "&device_id=" + DeviceID;
				
			    Main.login="no";       
			     
				// JSON Node names
				
				final String TAG_APIKEY = "api_key";
				final String TAG_LOCID = "location_id";
				final String TAG_USERID = "user_id";
				final String TAG_DEVICEID = "device_id";
				final String TAG_EMPLOYEEID = "employee_id";
				final String TAG_MWKEY = "mwkey";
				
				JSONObject jsonLogin = null;
		        // Creating JSON Parser instance
		        JSONParser jParserLogin = new JSONParser();
		        
		        jsonLogin = jParserLogin.getJSONFromUrl(url);
		        
		        
				if (jsonLogin != null)
				{
		        
		        try {
		        	
		        	JSONObject c = jsonLogin.getJSONObject("result");
		            
		        	//User Login
		        	String api_key = c.getString(TAG_APIKEY);
					String mwkey2 = mwkey;
		        	String location_id = c.getString(TAG_LOCID);
		        	String user_id = c.getString(TAG_USERID);
		        	String device_id = c.getString(TAG_DEVICEID);
		        	String employee_id = c.getString(TAG_EMPLOYEEID);
		        	            
		            userdatos = new User(mwkey2, api_key, location_id, device_id, user_id, employee_id);
		            
		            //INSERTAR EN BBDD tabla user
		            
		            BD=new DBHelper(getApplicationContext());
		            BD.open();
		            BD.removeUser();
		            result = BD.insertUser(mwkey2, api_key, location_id, device_id, user_id, employee_id);
		            BD.close();
		            if ( result == 1)
		            {
		            	// Variable que controla si se ha realizado el Login correctamente o no
		            	Main.login = "yes";
		            }
		            	
		        } catch (JSONException e) {
		            e.printStackTrace();
		        	}
					
				}
				
				return result;	// Sino se ha a–adido en BBDD el login devuelve -1, sino 1.
					
	}
	


	static JSONObject getRestauranteInfo() {
		
		
		// Json resultado de GetRestaurant
		JSONObject jsonRest = null;
		       
        // Creating JSON Parser instance
        JSONParser jParserRest = new JSONParser();
        
        
    	// url to make request
		String api_key="demo1";
		//api_key="woga1";
		api_key=userdatos.getUserMWKey();
		if (CheckConnect.serverIsAlive("beta.tocarta.es", 80, 10000))
		{
			String urlRest = "http://beta.tocarta.es/cli/c/get_restaurant_info.json?mwkey=" + api_key;
	             
	        jsonRest = jParserRest.getJSONFromUrl(urlRest);
		}
        return jsonRest;
        
	}
static JSONObject getSugerencias() {
	
			// Json resultado de GetRestaurant
			JSONObject jsonSuger = null;
			       
	        // Creating JSON Parser instance
	        JSONParser jParserSug = new JSONParser();
	        
	        
	    	// url to make request
			String api_key="demo1";
			//api_key="woga1";
			
			
			BD.open();
			User user = BD.getUser();
			userdatos=BD.getUser();
			BD.close();
			api_key=userdatos.getUserMWKey();
			
			String deviceId = user.getUserDeviceID();
			if (CheckConnect.serverIsAlive("beta.tocarta.es", 80, 10000))
			{
				String urlRest = "http://beta.tocarta.es/cli/mw/suggestions.json?mwkey="+ api_key +"&device_id=" + deviceId;
		             
		        jsonSuger = jParserSug.getJSONFromUrl(urlRest);
			}
	        return jsonSuger;
	}

static JSONObject getMarcharSegundos() {
	
	// Json resultado de GetRestaurant
	JSONObject jsonSeg = null;
	       
    // Creating JSON Parser instance
    JSONParser jParserSeg = new JSONParser();
    
    
	// url to make request
	String api_key="demo1";
	//api_key="woga1";
	
	
	BD.open();
	User user = BD.getUser();
	userdatos=BD.getUser();
	BD.close();
	api_key=userdatos.getUserMWKey();
	
	String deviceId = user.getUserDeviceID();
	String[] cadena = Login.pos_ip_address.split(":");
	String ip="";
	Integer port=8080;
	
	if (cadena.length > 1)
	{
		ip=cadena[1].toString().substring(2);
		port= Integer.valueOf(cadena[2].toString());
	}
	if (CheckConnect.serverIsAlive(ip, port, 10000))
	{
		
		String mesa = Main.m_Text.replaceAll(" ", "%20");
		String urlRest = "http://" + ip + ":"+port+"/actions/bring_second_courses?mwkey="+ api_key +"&table_sid=" + Main.sid_table + "&table_name=" + mesa;
		
             
        jsonSeg = jParserSeg.getJSONFromUrl(urlRest);
	}
    return jsonSeg;
}


static JSONObject getAppInfo() {
	
	// Json resultado de GetRestaurant
	JSONObject jsonAppInfo = null;
	       
    // Creating JSON Parser instance
    JSONParser jParserApp = new JSONParser();
    
    
	// url to make request
	String api_key="demo1";
	//api_key="woga1";
	api_key=userdatos.getUserMWKey();
	BD.open();
	User user = BD.getUser();
	BD.close();
	String deviceId = user.getUserDeviceID();
	if (CheckConnect.serverIsAlive("beta.tocarta.es", 80, 10000))
	{
		String urlRest = "http://beta.tocarta.es/cli/mw/app_info.json?mwkey="+ api_key +"&device_id=" + deviceId;
             
        jsonAppInfo = jParserApp.getJSONFromUrl(urlRest);
	}
    return jsonAppInfo;
}

static JSONObject getUserInfo() {
	
	// Json resultado de GetRestaurant
	JSONObject jsonUserInfo = null;
	       
    // Creating JSON Parser instance
    JSONParser jParserUser = new JSONParser();
    
    
	// url to make request
	String api_key="demo1";
	//api_key="woga1";
	api_key=userdatos.getUserMWKey();
	BD.open();
	User user = BD.getUser();
	BD.close();
	String deviceId = user.getUserDeviceID();
	if (CheckConnect.serverIsAlive("beta.tocarta.es", 80, 10000))
	{
		String urlRest = "http://beta.tocarta.es/cli/mw/user_info.json?mwkey="+ api_key +"&device_id=" + deviceId;
             
        jsonUserInfo = jParserUser.getJSONFromUrl(urlRest);
	}
    return jsonUserInfo;
}

static JSONObject getDiscounts() {
	
	// Json resultado de GetDiscounts
	JSONObject jsonDiscounts = null;
	       
    // Creating JSON Parser instance
    JSONParser jParserDiscounts = new JSONParser();
    
    
	// url to make request
	String api_key="demo1";
	//api_key="woga1";
	api_key=userdatos.getUserMWKey();
	BD.open();
	User user = BD.getUser();
	BD.close();
	String deviceId = user.getUserDeviceID();
	if (CheckConnect.serverIsAlive("beta.tocarta.es", 80, 10000))
	{
		String urlRest = "http://beta.tocarta.es/cli/mw/discounts.json?mwkey="+ api_key;
             
        jsonDiscounts = jParserDiscounts.getJSONFromUrl(urlRest);
	}
    return jsonDiscounts;
}

static JSONObject getPayments() {
	
	// Json resultado de GetRestaurant
	JSONObject jsonPayment = null;
	       
    // Creating JSON Parser instance
    JSONParser jParserPay = new JSONParser();
    
    
	// url to make request
	String api_key="demo1";
	//api_key="woga1";
	api_key=userdatos.getUserMWKey();
	BD.open();
	User user = BD.getUser();
	BD.close();
	String deviceId = user.getUserDeviceID();
	if (CheckConnect.serverIsAlive("beta.tocarta.es", 80, 10000))
	{
		String urlRest = "http://beta.tocarta.es/cli/mw/payments.json?mwkey="+ api_key;
             
        jsonPayment = jParserPay.getJSONFromUrl(urlRest);
	}
    return jsonPayment;
}

	
static JSONObject getModifiers() {
		
		
		// Json resultado de GetRestaurant
		JSONObject jsonMod = null;
		       
        // Creating JSON Parser instance
        JSONParser jParserMod = new JSONParser();
        
        
    	// url to make request
		String api_key="demo1";
		//api_key="woga1";
		api_key=userdatos.getUserMWKey();
		
		if (CheckConnect.serverIsAlive("beta.tocarta.es", 80, 10000))
		{
			String urlRest = "http://beta.tocarta.es/cli/mw/modifiers.json?mwkey=" + api_key;
             
        	jsonMod = jParserMod.getJSONFromUrl(urlRest);
		}
        return jsonMod;
        
        
	}
	private static String tratarRestaurantInfo(JSONObject json) {
	
		// JSON Node names
		//public  static final String TAG_CONTACTS = "contacts";
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
		
		
        // Menu JSONArray
     		JSONArray menu = null;
     		JSONArray sections = null;
     		JSONArray subsections = null;
     		JSONArray dishes = null;
     		JSONArray disktypes = null;
     		JSONObject settings = null;
        
        
        try {
            //Restaurante
        	
        	
        	
        	nameR = json.getString("name");
        	logo = json.getString("logo");
        	i18nbg = json.getString("i18nbg");
        	pos_ip_address = json.getString("pos_ip_address");
        	
        	
        	settings = json.getJSONObject("setting");            
            access_key = settings.getString("access_key");
            
            // adding each child node to HashMap key => value
            restaurante.put(TAG_LOGO, logo);
            restaurante.put(TAG_NAME, nameR);
            restaurante.put(TAG_I18NBG, i18nbg);
            restaurante.put("access_key", access_key);
            restaurante.put(TAG_IP, pos_ip_address);
            //restaurante.put(TAG_BG, "bg");
            
            
                    	
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
                menuList.add(map);
                
                
                
                
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
                    sectionsList.add(map_sections);
                    
                    
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
	                        subsectionsList.add(map_subsections);
	                        
	                        // Dishes
	                        dishes = c3.getJSONArray("dishes");
	                        for(int k= 0; k < dishes.length();k++)
	                        {
	                        	  JSONObject c4 = dishes.getJSONObject(k);
	                        	  
	                        	//final ArrayList<HashMap<String, String>> dishesList = new ArrayList<HashMap<String, String>>();
	                        	// Storing each json item in variable
	                            String idD = c4.getString("id");
	                            String nameD = c4.getString("name");
	                            String priceD = c4.getString("price");
	                            String sidD = c4.getString("sid");
	                            String short_titleD = c4.getString("short_title");
	                            String sd_dish_id = c4.getString("sd_dish_id");
	                            String description = c4.getString("description");
	                            //String large = c4.getString("large");
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
	                            dishesList.add(map_dishes);
                        
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
                            
                            map_dishes.put(TAG_SHORTTITLE, short_titleD);
             
                            // adding HashList to ArrayList
                            dishesList.add(map_dishes);
                    	
                    }
                             
                	
                }
                }
                
                
           
            }
        } catch (JSONException e) {
           
        	e.printStackTrace();
        	
        }
        return "OK";
       }
	
	
	
  
	protected String getDeviceID() {
 
		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

	    final String tmDevice, tmSerial, androidId;
	    tmDevice = "" + tm.getDeviceId();
	    tmSerial = "" + tm.getSimSerialNumber();
	    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	    String deviceId = deviceUuid.toString();
	    
	    //Para realizar pruebas con otros dispositivos
	    //deviceId="00000000-1a68-7a50-af0f-c8490033c587";
	   
	    return deviceId;
	}
	
	static  void cargarBD() {
	
		//Creamos y abrimos la base de datos
		
			
			//BD=new BDHelper(Login.LoginContext);
			BD.open();	
	
			// Cargamos Restaurante
			ArrayList<Restaurante> rest = new ArrayList<Restaurante>();
			int resul1 = BD.removeRestaurante();
		
			Long resultado = BD.insertRestaurante(nameR, logo, i18nbg, pos_ip_address, access_key);
			resultado = null;
			rest =(ArrayList<Restaurante>) BD.getRestaurants();
			
			
			//Cargamos Menu
			resul1 = BD.removeMenu();
			
			
			for(int i=0;i<menuList.size();i++)
			{		
					resultado = BD.insertMenu( menuList.get(i).get("id"), menuList.get(i).get("sid"), menuList.get(i).get("name"), menuList.get(i).get("menu_type"), menuList.get(i).get("price"));
			}
		
			
			//Cargamos Sections
			resul1 = BD.removeSections();			
			
			for(int i=0;i<sectionsList.size();i++)
			{		
					resultado = BD.insertSection( sectionsList.get(i).get("id"), sectionsList.get(i).get("id_menu"), sectionsList.get(i).get("sid"),sectionsList.get(i).get("name"),
							sectionsList.get(i).get("mini"), sectionsList.get(i).get("thumbnail"), sectionsList.get(i).get("hasBigsubSections"), sectionsList.get(i).get("dishes_per_page") );
					
			}
			
			
			
			
			// Cargamos Subsections
			resul1 = BD.removeSubsections();
			
			for(int i=0;i<subsectionsList.size();i++)
			{		
					resultado = BD.insertSubSection( subsectionsList.get(i).get("id"), subsectionsList.get(i).get("sid"), subsectionsList.get(i).get("id_menu"), subsectionsList.get(i).get("id_section"), 
							 subsectionsList.get(i).get("name"), sectionsList.get(i).get("short_title") );
					
			}
			
			
			// Cargamos Dishes
			resul1 = BD.removeDishes();
			
			for(int i=0;i<dishesList.size();i++)
			{		
					resultado = BD.insertDishes( dishesList.get(i).get("id"), dishesList.get(i).get("id_menu"), dishesList.get(i).get("id_section"), 
							dishesList.get(i).get("id_subsection"), dishesList.get(i).get("sid"), dishesList.get(i).get("name"),  dishesList.get(i).get("price"), dishesList.get(i).get("short_title"), dishesList.get(i).get("description"), dishesList.get(i).get("sd_dish_id") );
			}	
			
			//Cargamos modiferslist
			resul1= BD.removeModifierList();
			for(int i=0;i<modifiersList.size();i++)
			{		
					resultado = BD.insertModifierList( modifiersList.get(i).get("id"),modifiersList.get(i).get("id_mls"),modifiersList.get(i).get("sid"), modifiersList.get(i).get("name"),  modifiersList.get(i).get("is_mandatory"), modifiersList.get(i).get("is_multioption"));
			}	
			
			//Cargamos modiferslistSets
			resul1= BD.removeModifierListSets();
			for(int i=0;i<modifiersListSets.size();i++)
			{		
					resultado = BD.insertModifierListSets( modifiersListSets.get(i).get("id"), modifiersListSets.get(i).get("sid"), modifiersListSets.get(i).get("name"));
			}	
			
			//Cargamos modifers
			resul1= BD.removeModifiers();
			
			for(int i=0;i<modifiers.size();i++)
			{		
					resultado = BD.insertModifier( modifiers.get(i).get("id_list"), modifiers.get(i).get("id"), modifiers.get(i).get("name"), modifiers.get(i).get("sid"), modifiers.get(i).get("sd_modifierid"),modifiers.get(i).get("description"), modifiers.get(i).get("price"));
			}	
		
			
			//Cargamos dishmods 
			resul1= BD.removeDishmods();
			for(int i=0;i<dishmods.size();i++)
			{		
					resultado = BD.insertDishersMods( dishmods.get(i).get("id"), dishmods.get(i).get("id_mls"),  dishmods.get(i).get("sid"));
			}
			resul1= BD.removePayments();
			for(int i=0;i<Login.pays.size();i++)
			{		
					resultado = BD.insertPayments( Login.pays.get(i).get("id"), Login.pays.get(i).get("sid"),  Login.pays.get(i).get("name"), Login.pays.get(i).get("key"));
			}
			
			resul1= BD.removeAppInfo();
			resultado = BD.insertAppInfo(Login.nameApp, Login.version, Login.os, Login.term_of_use, Login.privacy, Login.whats_new, Login.link_to_store);
			
			resul1= BD.removeUserInfo();
			resultado = BD.insertUserInfo(Login.username, Login.email, Login.first_name, Login.middle_name, Login.last_name, Login.birthday, Login.mobile_number, Login.device_id);
			
			resul1= BD.removeDiscounts();
			for(int i=0;i< Login.discountsList.size();i++)
			{		
					resultado = BD.insertDiscounts( Login.discountsList.get(i).get("sid"), Login.discountsList.get(i).get("name"), Login.discountsList.get(i).get("dtype"), 
							Login.discountsList.get(i).get("amount"), Login.discountsList.get(i).get("note"), Login.discountsList.get(i).get("section_id"), Login.discountsList.get(i).get("dish_id"), 
							Login.discountsList.get(i).get("restaurant_id"), Login.discountsList.get(i).get("menu_id"));
			}
			resul1= BD.removeOrders();
			resul1= BD.removeOrderMods();
			resul1= BD.removeMesas();
				
			BD.close();
		
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

	@Override
	public void onPause() {
		super.onPause();
		
		
		if(pd != null)
			pd.dismiss();
		switch (tarea)
		{
		case 1:
			task.cancel(true);
			pd.dismiss();
			break;
		case 2:
			task1.cancel(true);
			pd.dismiss();
			break;
		case 3:
			task2.cancel(true);
			pd.dismiss();
			break;
		}
		
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
	
private static String tratarAppInfo(JSONObject jsonAppInfo) {
		
		final String TAG_APP="waiter_app";
		JSONObject appinfo = null;
		
		try {
			appinfo = jsonAppInfo.getJSONObject(TAG_APP);
			nameApp = appinfo.getString("name");
			version = appinfo.getString("version");
			os = appinfo.getString("os");
			term_of_use = appinfo.getString("term_of_use");
			whats_new = appinfo.getString("whats_new");
			link_to_store = appinfo.getString("link_to_store");
			privacy = appinfo.getString("privacy_policy");
			
			
			
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
		username = userinfo.getString("username");
		email = userinfo.getString("email");
		first_name = userinfo.getString("first_name");
		middle_name = userinfo.getString("middle_name");
		last_name = userinfo.getString("last_name");
		birthday = userinfo.getString("birthday");
		mobile_number = userinfo.getString("mobile_number");
		device_id = userinfo.getString("device_id");
		
	} catch (JSONException e) {
		e.printStackTrace();
    }
	return "OK";
}
	private static String tratarModifiers(JSONObject json) {
		
		// JSON Node names
		
		final String TAG_ID = "id";
		final String TAG_ID2 = "id_list";
		//final String TAG_ID3 = "id_modifier";		
		final String TAG_PRICE = "price";
		final String TAG_DESC = "description";
		final String TAG_ISMAND = "is_mandatory";
		final String TAG_ISMULTI = "is_multioption";		
		final String TAG_NAME = "name";		
		final String TAG_SDMOD ="sd_modifierid";
		//final String TAG_DISHID = "dishes_id";
		final String TAG_SID="sid";
		final String TAG_ID3="id_mls";
			
		
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
                modifiersListSets.add(map);
                
        	
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
                modifiersList.add(map1);
                
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
                    modifiers.add(map2);
                    
                
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
                    dishmods.add(map3);
            		
            	}
                
	            
        	}
     	}
       } catch (JSONException e) {
           
        	e.printStackTrace();
        	
        }
        return "OK";
       }

}
   




