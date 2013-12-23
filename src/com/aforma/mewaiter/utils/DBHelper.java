package com.aforma.mewaiter.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import com.aforma.mewaiter.app.Discount;
import com.aforma.mewaiter.app.Discountsel;
import com.aforma.mewaiter.app.Dish;
import com.aforma.mewaiter.app.DishMod;
import com.aforma.mewaiter.app.DishType;
import com.aforma.mewaiter.app.ListMod;
import com.aforma.mewaiter.app.ListModSet;
import com.aforma.mewaiter.app.Login;
import com.aforma.mewaiter.app.Menu;
import com.aforma.mewaiter.app.Mesa;
import com.aforma.mewaiter.app.Mod;
import com.aforma.mewaiter.app.Order;
import com.aforma.mewaiter.app.Ordermods;
import com.aforma.mewaiter.app.Payments;
import com.aforma.mewaiter.app.Restaurante;
import com.aforma.mewaiter.app.Section;
import com.aforma.mewaiter.app.SubSection;
import com.aforma.mewaiter.app.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;

/**
 * 
 * Esta es la clase que se utiliza para realizar todos los accesos a la BBDD SQLITE
 *
 */
public class DBHelper extends SQLiteOpenHelper{
	//Establecemos los nombres de las columnas de las tablas
	public static final String KEY_ID = "id";
	public final static String KEY_COL1 = "name";
	public final static String KEY_COL2 = "logo";
	public final static String KEY_COL3 = "il8nbg";
	public final static String KEY_COL4 = "bg";
	public final static String KEY_COL5 = "menu_type";
	public final static String KEY_COL6 = "price";
	public final static String KEY_COL7 = "mini";
	public final static String KEY_COL8 = "thumbnail";
	public final static String KEY_COL9 = "hasBigSubsections";
	public final static String KEY_COL10 = "dishes_per_page";
	public final static String KEY_COL11 = "short_title";
	public final static String KEY_COL12 = "description";
	public final static String KEY_COL13 = "small_icon";
	public final static String KEY_ID1 = "id_menu";
	public final static String KEY_ID2 = "id_section";
	public final static String KEY_ID3 = "id_dish";
	public final static String KEY_ID4 = "id_subsection";
	public final static String KEY_COL14 = "mwkey";
	public final static String KEY_COL15 = "api_key";
	public final static String KEY_COL16 = "location_id";
	public final static String KEY_COL17 = "device_id";
	public final static String KEY_COL18 = "user_id";
	public final static String KEY_COL19 = "employee_id";
	public final static String KEY_COL20 = "sd_dish_id";
	public final static String KEY_COL21  = "access_key";
	public final static String KEY_ID5  = "id_list";
	public final static String KEY_ID6  = "id_modifier";
	public final static String KEY_COL22  = "is_mandatory";
	public final static String KEY_COL23  = "is_multioption";
	public final static String KEY_COL24  = "sd_modifierid";
	public final static String KEY_ID7 = "id_table";
	public final static String KEY_COL25 = "value";
	public final static String KEY_COL26 = "status";
	public final static String KEY_COL27 = "quantity";
	public final static String KEY_COL28 = "note";
	public final static String KEY_COL29 = "sid_floor";
	public final static String KEY_COL30 = "number";
	public final static String KEY_COL31 = "pax";
	public final static String KEY_COL32 = "sid";
	public final static String KEY_ID8 = "id_mls";
	public final static String KEY_COL33 = "pos_ip_address";
	public final static String KEY_COL34 = "product_name";
	public final static String KEY_COL35 = "category_sid";
	public final static String KEY_ID9 = "id_order";
	public final static String KEY_COL36 = "sid_mls";
	public final static String KEY_COL37 = "sid_ml";
	public final static String KEY_COL38 = "sid_modifier";
	public final static String KEY_COL39 = "sid_table";
	public final static String KEY_ID10 = "id_floor";
	public final static String KEY_ID11 = "id_table";
	public final static String KEY_COL40= "version";
	public final static String KEY_COL41= "os";
	public final static String KEY_COL42= "term_of_use";
	public final static String KEY_COL43= "privacy_policy";
	public final static String KEY_COL44= "whats_new";
	public final static String KEY_COL45= "link_to_store";
	public final static String KEY_COL46= "username";
	public final static String KEY_COL47= "email";
	public final static String KEY_COL48= "first_name";
	public final static String KEY_COL49= "middle_name";
	public final static String KEY_COL50= "last_name";
	public final static String KEY_COL51= "birthday";
	public final static String KEY_COL52= "mobile_number";
	public final static String KEY_COL53= "dtype";
	public final static String KEY_COL54= "amount";
	public final static String KEY_COL55= "section_id";
	public final static String KEY_COL56= "dish_id";
	public final static String KEY_COL57= "restaurant_id";
	public final static String KEY_COL58= "menu_id";
	public final static String KEY_COL59= "general";
	public final static String KEY_COL60= "sid_menu";
	public final static String KEY_COL61= "sid_section";
	public final static String KEY_COL62= "sid_dish";
	public final static String KEY_COL63= "key";
	public final static String KEY_COL64= "selected";
	
	
	
	//Array de strings para su uso en los diferentes métodos
	private static final String[] restaurante = new String[] {  KEY_COL1, KEY_COL2,KEY_COL3, KEY_COL33, KEY_COL21 };
	private static final String[] menus  = new String[] {  KEY_ID, KEY_COL32, KEY_COL1,KEY_COL5,KEY_COL6 };
	private static final String[] section = new String[] {  KEY_ID,  KEY_ID1,  KEY_COL32, KEY_COL1, KEY_COL7,KEY_COL8, KEY_COL9, KEY_COL10 };
	private static final String[] subsection = new String[] { KEY_ID,  KEY_COL32, KEY_COL1, KEY_COL11, KEY_ID2, KEY_ID1 };
	private static final String[] dishes = new String[] {  KEY_ID, KEY_ID1, KEY_ID2, KEY_ID4,  KEY_COL32,  KEY_COL32, KEY_COL1, KEY_COL11, KEY_COL6, KEY_COL12, KEY_COL20 };
	private static final String[] dishtypes = new String[] { KEY_ID, KEY_ID3, KEY_ID1, KEY_COL1, KEY_COL12, KEY_COL13 };
	private static final String[] user = new String[] { KEY_COL14, KEY_COL15, KEY_COL16, KEY_COL17, KEY_COL18, KEY_COL19 };
	private static final String[] modifier_list = new String[] { KEY_ID5, KEY_ID8, KEY_COL32, KEY_COL1, KEY_COL22, KEY_COL23, KEY_COL64};
	private static final String[] modifier_list_set = new String[] { KEY_ID, KEY_COL32, KEY_COL1};
	private static final String[] modifiers = new String[] { KEY_ID5, KEY_ID6, KEY_COL1, KEY_COL32, KEY_COL24, KEY_COL12, KEY_COL6};
	private static final String[] dishesmods = new String[] { KEY_ID, KEY_ID8, KEY_COL32};
	private static final String[] order =  new String[] { KEY_ID, KEY_ID7, KEY_COL39, KEY_COL32, KEY_COL35, KEY_COL6, KEY_COL28, KEY_COL26, KEY_COL34, KEY_COL27 };
	private static final String[] tables =  new String[] { KEY_ID10, KEY_ID11, KEY_COL29, KEY_COL39, KEY_COL1, KEY_COL30, KEY_COL26, KEY_COL31 };
	private static final String[] ordermod = new String[] { KEY_ID9, KEY_COL36, KEY_COL37, KEY_COL38, KEY_COL25};
	private static final String[] payments = new String[] { KEY_ID, KEY_COL32, KEY_COL1, KEY_COL63};
	private static final String[] appinfo = new String[] { KEY_COL1, KEY_COL40, KEY_COL41, KEY_COL42, KEY_COL43, KEY_COL44, KEY_COL45};
	private static final String[] userinfo = new String[] { KEY_COL46, KEY_COL47, KEY_COL48, KEY_COL49, KEY_COL50, KEY_COL51, KEY_COL52, KEY_COL17};
	private static final String[] discounts = new String[] { KEY_COL32, KEY_COL1, KEY_COL53, KEY_COL54, KEY_COL28, KEY_COL55, KEY_COL56, KEY_COL57, KEY_COL58};
	private static final String[] discountsel = new String[] { KEY_COL32, KEY_ID11, KEY_ID9, KEY_COL59, KEY_COL60, KEY_COL61, KEY_COL62};
	
	// Nombres de las tablas
	private static final String DATABASE_TABLE ="restaurante";
	private static final String DATABASE_TABLE2 = "menus";
	private static final String DATABASE_TABLE3 ="sections";
	private static final String DATABASE_TABLE4 = "subsections";
	private static final String DATABASE_TABLE5 ="dishes";
	private static final String DATABASE_TABLE6 = "dishtypes";
	private static final String DATABASE_TABLE7 = "user";
	private static final String DATABASE_TABLE8 = "modifier_list";
	private static final String DATABASE_TABLE9 = "modifier";
	private static final String DATABASE_TABLE10 = "dish_mod";
	private static final String DATABASE_TABLE11 = "orders";
	private static final String DATABASE_TABLE12 = "tables";
	private static final String DATABASE_TABLE13 = "modifier_list_set";
	private static final String DATABASE_TABLE14 = "order_mods";
	private static final String DATABASE_TABLE15 = "payments";
	private static final String DATABASE_TABLE16 = "appinfo";
	private static final String DATABASE_TABLE17 = "userinfo";
	private static final String DATABASE_TABLE18 = "discounts";
	private static final String DATABASE_TABLE19 = "discountsel";
	
	private static String DB_PATH = null;
	private final Context myContext;
	//Ruta por defecto de las bases de datos en el sistema Android
	//private static String DB_PATH = "/data/data/com.aforma.meWaiter/databases/";
	
	private static String DB_NAME = "mewaiterdb";
	private SQLiteDatabase mewaiter;

	 
	
	 
	/**
	* Constructor
	* Toma referencia hacia el contexto de la aplicación que lo invoca para poder acceder a los 'assets' y 'resources' de la aplicación.
	* Crea un objeto DBOpenHelper que nos permitirá controlar la apertura de la base de datos.
	* @param context
	*/
	public DBHelper(Context context) {
	 
	super(context, DB_NAME, null, 1);

	if(android.os.Build.VERSION.SDK_INT >= 4.2)
	{
        DB_PATH =context.getApplicationInfo().dataDir + "/databases/";         
    } else 
    	DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    
	this.myContext = context;
	 
	}
	 
	/**
	* Crea una base de datos vacía en el sistema y la reescribe con nuestro fichero de base de datos.
	* */
	public void createDataBase() throws IOException{
	 
	//boolean dbExist = checkDataBase();

		if(android.os.Build.VERSION.SDK_INT >= 4.2)
		{
	        DB_PATH = myContext.getApplicationInfo().dataDir + "/databases/";         
	    } else 
	    	DB_PATH = "/data/data/" + myContext.getPackageName() + "/databases/";
	    
	
		boolean dbExist = checkDatabase();
		
	if(dbExist){
	//la base de datos existe y no hacemos nada.
	}else{
	//Llamando a este método se crea la base de datos vacía en la ruta por defecto del sistema
	//de nuestra aplicación por lo que podremos sobreescribirla con nuestra base de datos.
	this.getReadableDatabase();
	 
	try {
	 
	copyDataBase();
	 
	} catch (IOException e) {
	throw new Error("Error copiando Base de Datos");
	}
	}
	 
	}
	 

	 
	/**
	* Copia nuestra base de datos desde la carpeta assets a la recien creada
	* base de datos en la carpeta de sistema, desde dónde podremos acceder a ella.
	* Esto se hace con bytestream.
	* */
	private void copyDataBase() throws IOException{

		if(android.os.Build.VERSION.SDK_INT >= 4.2)
		{
	        DB_PATH = myContext.getApplicationInfo().dataDir + "/databases/";         
	    } else 
	    	DB_PATH = "/data/data/" + myContext.getPackageName() + "/databases/";
	    
	//Abrimos el fichero de base de datos como entrada
	InputStream myInput = myContext.getAssets().open(DB_NAME);
	 
	//Ruta a la base de datos vacía recien creada
	String outFileName = DB_PATH + DB_NAME;
	 
	//Abrimos la base de datos vacía como salida
	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	//Transferimos los bytes desde el fichero de entrada al de salida
	byte[] buffer = new byte[1024];
	int length;
	while ((length = myInput.read(buffer))>0){
	myOutput.write(buffer, 0, length);
	}
	 
	//Liberamos los streams
	myOutput.flush();
	myOutput.close();
	myInput.close();
	 
	}
	
	 
	public void open() throws SQLException{
	 

		if(android.os.Build.VERSION.SDK_INT >= 4.2)
		{
	        DB_PATH = myContext.getApplicationInfo().dataDir + "/databases/";         
	    } else 
	    	DB_PATH = "/data/data/" + myContext.getPackageName() + "/databases/";
	    
	//Abre la base de datos
	try {
	createDataBase();
	} catch (IOException e) {
	throw new Error("Ha sido imposible crear la Base de Datos");
	}
	 
	//String myPath = DB_PATH + DB_NAME;
	String absolutePath = getDatabaseAbsolutePath();
	mewaiter = SQLiteDatabase.openDatabase(absolutePath, null, SQLiteDatabase.OPEN_READWRITE);
	//mewaiter = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
	}
	 
	@Override
	public synchronized void close() {
	

		if(android.os.Build.VERSION.SDK_INT >= 4.2)
		{
	        DB_PATH = myContext.getApplicationInfo().dataDir + "/databases/";         
	    } else 
	    	DB_PATH = "/data/data/" + myContext.getPackageName() + "/databases/";
	    
	if(mewaiter != null)
	mewaiter.close();
	super.close();
	}
	 
	@Override
	public void onCreate(SQLiteDatabase db) {
	 
	}
	 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	 
	}

	/**
	* A continuación se crearán los métodos de lectura, inserción, actualización
	* y borrado de la base de datos.
	* */
	/**
	* INSERTAR nuevo restaurante
	* */
	public long insertRestaurante(String name, String logo, String il8nbg, String pos_ip_address, String access_key) {
	ContentValues newValues = new ContentValues();
	newValues.put(KEY_COL1, name);
	newValues.put(KEY_COL2, logo);
	newValues.put(KEY_COL3, il8nbg);
	newValues.put(KEY_COL33, pos_ip_address);
	newValues.put(KEY_COL21, access_key);

	return mewaiter.insert(DATABASE_TABLE, null, newValues);
	}
	 
	/**
	* BORRAR TABLA RESTAURANTE 
	* */
	public int removeRestaurante() {
		return mewaiter.delete(DATABASE_TABLE, null, null);
	}
	
	public int removeUser() {
		return mewaiter.delete(DATABASE_TABLE7, null, null);
	}
	
	public int removePayments() {
		return mewaiter.delete(DATABASE_TABLE15, null, null);
	}
	 
	/**
	* ACTUALIZAR ALARMA _id = _rowIndex
	* */
	/*public boolean updateAlarma(Integer _rowIndex, Integer alarma, Integer evento) {
	ContentValues newValues = new ContentValues();
	newValues.put(KEY_COL1,alarma);
	newValues.put(KEY_COL2, evento);
	return mewaiter.update(DATABASE_TABLE, newValues, KEY_ID + "=" + _rowIndex, null) > 0;
	}*/
	
	public long insertUser(String mwkey, String api_key, String location_id, String device_id, String user_id, String employee_id) {
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_COL14, mwkey);
		newValues.put(KEY_COL15, api_key);
		newValues.put(KEY_COL16, location_id);
		newValues.put(KEY_COL17, device_id);
		newValues.put(KEY_COL18, user_id);
		newValues.put(KEY_COL19, employee_id);

		return mewaiter.insert(DATABASE_TABLE7, null, newValues);
		}
	
	public User getUser() {
		User userdatos = new User(null,null,null,null, null, null);
		Cursor result = mewaiter.query(DATABASE_TABLE7,
		user,  null, null, null, null, KEY_COL14 );
		if ((result.getCount() == 0) || !result.moveToFirst()) {
		//Si la alarma no existe, devuelve una alarma con valores -1 y -1
		userdatos = new User("-1","-1","-1","-1","-1","-1");
		 
		} else {
		if (result.moveToFirst()) {
		userdatos = new User(
		result.getString(result.getColumnIndex(KEY_COL14)),
		result.getString(result.getColumnIndex(KEY_COL15)),
		result.getString(result.getColumnIndex(KEY_COL16)),
		result.getString(result.getColumnIndex(KEY_COL17)),
		result.getString(result.getColumnIndex(KEY_COL18)),
		result.getString(result.getColumnIndex(KEY_COL19))
		
		);
		}
		}
		return userdatos;
		}
	
	public Restaurante getRestaurante(String name) {
		Restaurante restaurant = new Restaurante(null,null,null,null, null);
		
		
		
		Cursor result = mewaiter.query(true, DATABASE_TABLE,
		restaurante,
		KEY_COL1 + "='" + name + "'", null, null, null,
		null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
		//Si la alarma no existe, devuelve una alarma con valores -1 y -1
		restaurant = new Restaurante("-1","-1","-1","-1", "-1");
		 
		} else {
		if (result.moveToFirst()) {
		restaurant = new Restaurante(
		result.getString(result.getColumnIndex(KEY_COL1)),
		result.getString(result.getColumnIndex(KEY_COL2)),
		result.getString(result.getColumnIndex(KEY_COL3)),
		result.getString(result.getColumnIndex(KEY_COL33)),
		result.getString(result.getColumnIndex(KEY_COL21))
		);
		}
		}
		return restaurant;
		}
		 
	public ArrayList<Payments> getPayments(){
		ArrayList<Payments> payment = new ArrayList<Payments>();
		Cursor result = mewaiter.query(true, DATABASE_TABLE15, payments, null, null, null, null,null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
		//Si la alarma no existe, devuelve una alarma con valores -1 y -1
		
		 
		} else {
			
			if (result.moveToFirst()) {
				do {
					payment.add(new Payments(
					result.getInt(result.getColumnIndex(KEY_ID)),
					result.getString(result.getColumnIndex(KEY_COL32)),
					result.getString(result.getColumnIndex(KEY_COL1)),
					result.getString(result.getColumnIndex(KEY_COL63)),
					""
					));
				} while(result.moveToNext());
				}
		}
		return payment;
		}
	
	public Payments getPaymentsByName(String name){
		Payments payment = new Payments(0, name, name, name, name);
		
		Cursor result = mewaiter.query(true, DATABASE_TABLE15, payments,KEY_COL1 + "='" + name + "'", null, null, null,null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
		} else {
			
			if (result.moveToFirst()) {
				
					payment = new Payments(
					result.getInt(result.getColumnIndex(KEY_ID)),
					result.getString(result.getColumnIndex(KEY_COL32)),
					result.getString(result.getColumnIndex(KEY_COL1)),
					result.getString(result.getColumnIndex(KEY_COL63)), 
					""
					);
				
				}
		}
		return payment;
		}
	
	public Menu getMenu(int id) {
		Menu menu = new Menu(0,null,null,null, null);
	
		
		
		Cursor result = mewaiter.query(true, DATABASE_TABLE2,
		menus,
		"id" + "=" + id, null, null, null,
		null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
		//Si la alarma no existe, devuelve una alarma con valores -1 y -1
		menu = new Menu( -1,"-1","-1","-1", "-1");
		 
		} else {
		if (result.moveToFirst()) {
		menu = new Menu(
		result.getInt(result.getColumnIndex("id")),
		result.getString(result.getColumnIndex("sid")),
		result.getString(result.getColumnIndex("name")),
		result.getString(result.getColumnIndex("menu_type")),
		result.getString(result.getColumnIndex("price"))
		);
		}
		}
		return menu;
		}
	
		public List<Restaurante> getRestaurants() {
		
		ArrayList<Restaurante> restaurant = new ArrayList<Restaurante>();
		//String query="select name,logo,il8nbg,bg from restaurante;";
		
		//Strings result2 = mewaiter.execSQL(query);
		
		
		Cursor result = mewaiter.query(DATABASE_TABLE, restaurante, null, null, null, null, KEY_COL1);
		
		if (result.moveToFirst())
		do {
		restaurant.add(new Restaurante(
		result.getString(result.getColumnIndex(KEY_COL1)),
		result.getString(result.getColumnIndex(KEY_COL2)),
		result.getString(result.getColumnIndex(KEY_COL3)),
		result.getString(result.getColumnIndex(KEY_COL33)),
		result.getString(result.getColumnIndex(KEY_COL21))
		)
		);
		} while(result.moveToNext());
		return restaurant;
		}

		public ArrayList<Menu> getMenus() {
			
			ArrayList<Menu> menu = new ArrayList<Menu>();
	
			Cursor result = mewaiter.query(DATABASE_TABLE2, menus, null, null, null, null, KEY_ID);
			
			if (result.moveToFirst())
			do {
			menu.add(new Menu(
					result.getInt(result.getColumnIndex("id")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("menu_type")),
					result.getString(result.getColumnIndex("price"))
			)
			);
			} while(result.moveToNext());
			return menu;
			}
		
		protected String getDatabaseAbsolutePath() {

			return myContext.getDatabasePath(DB_NAME).getAbsolutePath();

			}
		
		private boolean checkDatabase() {
		 

			SQLiteDatabase checkDB = null;
			try {
			String absolutePath = getDatabaseAbsolutePath();
			checkDB = SQLiteDatabase.openDatabase(absolutePath, null, SQLiteDatabase.OPEN_READWRITE);

			} catch (SQLiteException e){
			//database does't exist yet.
			}

			if (checkDB != null){
			checkDB.close();
			}

			return checkDB != null ? true : false;
			}

		public long insertMenu(String id, String sid, String name, String menu_type, String price) {
			ContentValues newValues = new ContentValues();
			int entero = Integer.parseInt(id);
				newValues.put("id", entero);
				newValues.put("sid", sid);
				newValues.put("name", name);
				newValues.put("menu_type", menu_type);
				newValues.put("price", price);
			
				return mewaiter.insert(DATABASE_TABLE2, null, newValues);
			
			
		}
		public long insertSection(String id, String id_menu, String sid, String name, String mini, String thumbnail, String hasBigSubsections, String dishes_per_page ) {
			ContentValues newValues = new ContentValues();
			int i = Integer.parseInt(id);
			int i2 = Integer.parseInt(id_menu);
				newValues.put("id", i);
				newValues.put("id_menu", i2);
				newValues.put("sid", sid);
				newValues.put("name", name);
				newValues.put("mini", mini);
				newValues.put("thumbnail", thumbnail);
				newValues.put("hasBigSubsections", 	hasBigSubsections);
				newValues.put("dishes_per_page", 	dishes_per_page);
				
				return mewaiter.insert(DATABASE_TABLE3, null, newValues);
			
		}
		
		
		public long insertSubSection(String id, String sid, String id_menu, String id_section, String name, String short_title ) {
			ContentValues newValues = new ContentValues();
			int i = Integer.parseInt(id);
			int i2 = Integer.parseInt(id_menu);
			int i3 = Integer.parseInt(id_section);
				newValues.put("id", i);
				newValues.put("id_menu", i2);
				newValues.put("id_section", i3);
				newValues.put("sid", sid);
				newValues.put("name", name);
				newValues.put("short_title", short_title);
				//newValues.put("mini", mini);
				
				return mewaiter.insert(DATABASE_TABLE4, null, newValues);
			
		}
		
		public long insertDishes(String id, String id_menu, String id_section, String id_subsection, String sid, String name, String short_title, String price, String description, String sd_dish_id ) {
			ContentValues newValues = new ContentValues();
			int i = Integer.parseInt(id);
			int i2 = Integer.parseInt(id_menu);
			int i3 = Integer.parseInt(id_section);
			int i4 = Integer.parseInt(id_subsection);
				newValues.put("id", i);
				newValues.put("id_menu", i2);
				newValues.put("id_section", i3);
				newValues.put("id_subsection", i4);
				newValues.put("sid", sid);
				newValues.put("name", name);
				newValues.put("short_title", short_title);
				newValues.put("price", price);
				newValues.put("description", description);
				newValues.put("sd_dish_id", sd_dish_id);
				
				return mewaiter.insert(DATABASE_TABLE5, null, newValues);
			
		}
		

		public long insertDishTypes(String id, String id_menu, String id_dish, String sid, String name, String small_icon, String description ) {
			ContentValues newValues = new ContentValues();
			int i = Integer.parseInt(id);
			int i2 = Integer.parseInt(id_menu);
			int i3 = Integer.parseInt(id_dish);
		
				newValues.put("id", i);
				newValues.put("id_menu", i2);
				newValues.put("id_dish", i3);
				newValues.put("sid", sid);
				newValues.put("name", name);
				newValues.put("small_icon", small_icon);
				newValues.put("description", description);
				
				return mewaiter.insert(DATABASE_TABLE6, null, newValues);
			
		}
		
		public ArrayList<Section> getSection() {
			
			ArrayList<Section> sections = new ArrayList<Section>();
		
			Cursor result = mewaiter.query(DATABASE_TABLE3, section, null, null, null, null, KEY_ID);
			
			if (result.moveToFirst())
			do {
			sections.add(new Section(
					result.getInt(result.getColumnIndex("id")),
					result.getInt(result.getColumnIndex("id_menu")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("mini")),
					result.getString(result.getColumnIndex("thumbnail")),
					result.getString(result.getColumnIndex("dishes_per_page")),
					result.getString(result.getColumnIndex("hasBigSubSections"))
					
			)
			);
			} while(result.moveToNext());
			return sections;
			}

public ArrayList<SubSection> getSubSection() {
			
			ArrayList<SubSection> subsections = new ArrayList<SubSection>();
	
			Cursor result = mewaiter.query(DATABASE_TABLE4, subsection, null, null, null, null, KEY_ID);
			
			if (result.moveToFirst())
			do {
			subsections.add(new SubSection(
					result.getInt(result.getColumnIndex("id")),
					result.getInt(result.getColumnIndex("id_menu")),
					result.getInt(result.getColumnIndex("id_section")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("short_title"))
			
			)
			);
			} while(result.moveToNext());
			return subsections;
			}

public ArrayList<Dish> getDishes() {
	
	ArrayList<Dish> dish = new ArrayList<Dish>();
	
	Cursor result = mewaiter.query(DATABASE_TABLE5, dishes, null, null, null, null, KEY_ID);
	
	if (result.moveToFirst())
	do {
	dish.add(new Dish(
			result.getInt(result.getColumnIndex("id")),
			result.getInt(result.getColumnIndex("id_menu")),
			result.getInt(result.getColumnIndex("id_section")),
			result.getInt(result.getColumnIndex("id_subsection")),
			result.getString(result.getColumnIndex("sid")),
			result.getString(result.getColumnIndex("name")),
			result.getString(result.getColumnIndex("price")),
			result.getString(result.getColumnIndex("description")),
			result.getString(result.getColumnIndex("short_title")),
			result.getString(result.getColumnIndex("sd_dish_id"))
	
	)
	);
	} while(result.moveToNext());
	return dish;
	}

public ArrayList<DishType> getDishTypes() {
	
	ArrayList<DishType> dishtype = new ArrayList<DishType>();
	
	Cursor result = mewaiter.query(DATABASE_TABLE6, dishtypes, null, null, null, null, KEY_ID);
	
	if (result.moveToFirst())
	do {
	dishtype.add(new DishType(
			result.getInt(result.getColumnIndex("id")),
			result.getInt(result.getColumnIndex("id_menu")),
			result.getInt(result.getColumnIndex("id_dish")),
			
			result.getString(result.getColumnIndex("name")),
			result.getString(result.getColumnIndex("small_icon")),
			result.getString(result.getColumnIndex("description"))
			
	
	)
	);
	} while(result.moveToNext());
	return dishtype;
	}

		public int removeMenu() {
			return mewaiter.delete(DATABASE_TABLE2, null, null);
			
		}
		public int removeSections() {
			return mewaiter.delete(DATABASE_TABLE3, null, null);
		}
		public int removeSubsections() {
			return mewaiter.delete(DATABASE_TABLE4, null, null);
			
		}
		public int removeDishes() {
			return mewaiter.delete(DATABASE_TABLE5, null, null);
		}
		public int removeDishTypes() {
			return mewaiter.delete(DATABASE_TABLE6, null, null);
		}
		
		public int removeDishmods() {
			return mewaiter.delete(DATABASE_TABLE10, null, null);
		}

		public int removeModifierList() {
			return mewaiter.delete(DATABASE_TABLE8, null, null);
		}
		public int removeModifierListSets() {
			return mewaiter.delete(DATABASE_TABLE13, null, null);
		}

		public int removeModifiers() {
			return mewaiter.delete(DATABASE_TABLE9, null, null);
		}


		public Section getSectionDatos(String name) {
			
			Section sections2 = new Section(0, 0, name, name, name, name, name, name);
			
			
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE3,
			section,
			KEY_COL1 + "='" + name + "'", null, null, null,
			null, null);
			if ((result.getCount() == 0) || !result.moveToFirst()) {
			//Si la alarma no existe, devuelve una alarma con valores -1 y -1
			sections2 = new Section(0,0, "-1","-1","-1","-1", "-1", "-1");
			 
			} else {
			if (result.moveToFirst()) {
			sections2 = new Section(
					result.getInt(result.getColumnIndex("id")),
					result.getInt(result.getColumnIndex("id_menu")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("mini")),
					result.getString(result.getColumnIndex("thumbnail")),
					result.getString(result.getColumnIndex("dishes_per_page")),
					result.getString(result.getColumnIndex("hasBigSubSections"))
			);
			}
			}
			return sections2;
			
		}

		public ArrayList<SubSection> getSubSectionDatos(int id_section, int id_menu) {
				ArrayList<SubSection> subsections = new ArrayList<SubSection>();
			
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE4, subsection, KEY_ID2 + "=" + id_section + " AND " + KEY_ID1 + "=" + id_menu ,null, null, null, null, null);
			
			if (result.moveToFirst())
			do {
					subsections.add(new SubSection(
							result.getInt(result.getColumnIndex("id")),
							result.getInt(result.getColumnIndex("id_menu")),
							result.getInt(result.getColumnIndex("id_section")),
							result.getString(result.getColumnIndex("sid")),
							result.getString(result.getColumnIndex("name")),
							result.getString(result.getColumnIndex("short_title"))
			
			)
			);
			} while(result.moveToNext());
			return subsections;
		}

		public ArrayList<Dish> getDishesDatos(int id_section, int id_subsection, int id_menu) {
			
			ArrayList<Dish> dish = new ArrayList<Dish>();
			
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE5, dishes, KEY_ID2 + "=" + id_section + " AND " + KEY_ID4 + "=" + id_subsection + " AND " + KEY_ID1 + "=" + id_menu,
					null, null, null, null, null);
			//Cursor result = mewaiter.query(DATABASE_TABLE5, dishes, null, null, null, null, null);
			
			if (result.moveToFirst())
			do {
			dish.add(new Dish(
					result.getInt(result.getColumnIndex("id")),
					result.getInt(result.getColumnIndex("id_menu")),
					result.getInt(result.getColumnIndex("id_section")),
					result.getInt(result.getColumnIndex("id_subsection")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("price")),
					result.getString(result.getColumnIndex("description")),
					result.getString(result.getColumnIndex("short_title")),
					result.getString(result.getColumnIndex("sd_dish_id"))
					
			
			)
			);
			} while(result.moveToNext());
			return dish;
			
		}

		public Menu getMenuByname(String name) {
			
			Menu menu = new Menu(0,null,null,null, name);
						
			Cursor result = mewaiter.query(true, DATABASE_TABLE2,
			menus,
			"name" + "='" + name + "'", null, null, null,
			null, null);
			if ((result.getCount() == 0) || !result.moveToFirst()) {
			//Si la alarma no existe, devuelve una alarma con valores -1 y -1
			menu = new Menu( -1,"-1","-1","-1", "-1");
			 
			} else {
			if (result.moveToFirst()) {
				menu = new Menu(
				result.getInt(result.getColumnIndex("id")),
				result.getString(result.getColumnIndex("sid")),
				result.getString(result.getColumnIndex("name")),
				result.getString(result.getColumnIndex("menu_type")),
				result.getString(result.getColumnIndex("price"))
				);
			}
			}
			return menu;
			}

		
		public ArrayList<Section> getSectionByID(String id_menu) {
			ArrayList<Section> sections2 = new ArrayList<Section>();	
				
				int id = Integer.parseInt(id_menu);
				
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE3,
			section,
			"id_menu" + "=" + id, null, null, null,
			null, null);
		
			
			if ((result.getCount() == 0) || !result.moveToFirst()) {
				sections2.add(new Section(0,0, "-1","-1","-1","-1","-1", "-1"));
			
			 
			} else {
			
				
				if (result.moveToFirst())
						do {
								sections2.add(new Section(
										result.getInt(result.getColumnIndex("id")),
										result.getInt(result.getColumnIndex("id_menu")),
										result.getString(result.getColumnIndex("sid")),
										result.getString(result.getColumnIndex("name")),
										result.getString(result.getColumnIndex("mini")),
										result.getString(result.getColumnIndex("thumbnail")),
										result.getString(result.getColumnIndex("dishes_per_page")),
										result.getString(result.getColumnIndex("hasBigSubSections"))
										));
						
						
						
						} while(result.moveToNext());
			}
			
			return sections2;
			
		}
		public Section getSectionByIdSection(String id_section) {
			Section sections2 = new Section(0, 0, id_section, id_section, id_section, id_section, id_section, id_section);	
				
				int id = Integer.parseInt(id_section);
				
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE3,
			section,
			"id" + "=" + id, null, null, null,
			null, null);
		
			
			if ((result.getCount() == 0) || !result.moveToFirst()) {
				sections2 = new Section(0,0, "-1","-1","-1","-1","-1", "-1");
			
			 
			} else {
			
				
				if (result.moveToFirst())
						do {
								sections2 = new Section(
										result.getInt(result.getColumnIndex("id")),
										result.getInt(result.getColumnIndex("id_menu")),
										result.getString(result.getColumnIndex("sid")),
										result.getString(result.getColumnIndex("name")),
										result.getString(result.getColumnIndex("mini")),
										result.getString(result.getColumnIndex("thumbnail")),
										result.getString(result.getColumnIndex("dishes_per_page")),
										result.getString(result.getColumnIndex("hasBigSubSections"))
										);
						
						
						
						} while(result.moveToNext());
			}
			
			return sections2;
			
		}
		public SubSection getSubSectionByIdSubSection(String id_subsection) {
			SubSection subsections2 = new SubSection(0, 0, 0, id_subsection, id_subsection, id_subsection);	
				
				int id = Integer.parseInt(id_subsection);
				
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE4,
			subsection,
			"id" + "=" + id, null, null, null,
			null, null);
		
			
			if ((result.getCount() == 0) || !result.moveToFirst()) {
				subsections2 = new SubSection(id, id, id, id_subsection, id_subsection, id_subsection);
			
			 
			} else {
			
				
				if (result.moveToFirst())
						do {
								subsections2 = new SubSection(
										result.getInt(result.getColumnIndex("id")),
										result.getInt(result.getColumnIndex("id_menu")),
										result.getInt(result.getColumnIndex("id_section")),
										result.getString(result.getColumnIndex("sid")),
										result.getString(result.getColumnIndex("name")),
										result.getString(result.getColumnIndex("short_title"))
										);
						
						
						
						} while(result.moveToNext());
			}
			
			return subsections2;
			
		}

		public SubSection getSubSectionDatosByName(String name) {
			
			SubSection subsection2 = new SubSection(0, 0, 0, name, name, name);
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE4, subsection, KEY_COL1 + "='" + name + "'" ,null, null, null, null, null);
			
			if (result.moveToFirst())
			do {
					subsection2 = new SubSection(
							result.getInt(result.getColumnIndex("id")),
							result.getInt(result.getColumnIndex("id_menu")),
							result.getInt(result.getColumnIndex("id_section")),
							result.getString(result.getColumnIndex("sid")),
							result.getString(result.getColumnIndex("name")),
							result.getString(result.getColumnIndex("short_title"))
							);
			} while(result.moveToNext());
			return subsection2;
		}

		public Long insertModifierList(String id_list, String id_mls, String sid, String name,
				String is_mandatory, String is_multioption, String selected_modifier_sid) {
			ContentValues newValues = new ContentValues();
			int i = Integer.parseInt(id_list);
			int i2 = Integer.parseInt(id_mls);
			
				newValues.put("id_list", i);
				newValues.put("id_mls", i2);
				newValues.put("sid", sid);
				newValues.put("name", name);
				newValues.put("is_mandatory", is_mandatory);
				newValues.put("is_multioption", is_multioption);
				newValues.put("selected", selected_modifier_sid);
				
				return mewaiter.insert(DATABASE_TABLE8, null, newValues);
		}

		public Long insertModifier(String id_list, String id_modifier,
				String name, String sid, String sd_modifierid, String description, String price) {
			ContentValues newValues = new ContentValues();
			int i = Integer.parseInt(id_list);
			int i2 = Integer.parseInt(id_modifier);
				newValues.put("id_list", i);
				newValues.put("id_modifier", i2);
				newValues.put("name", name);
				newValues.put("sid", sid);
				newValues.put("sd_modifierid", sd_modifierid);
				newValues.put("description", description);
				newValues.put("price", 	price);
				
				
				
				return mewaiter.insert(DATABASE_TABLE9, null, newValues);
			
		}

		public Long insertDishersMods(String id, String id_mls,
				String sid) {
			ContentValues newValues = new ContentValues();
			int i = Integer.parseInt(id);
			int i2 = Integer.parseInt(id_mls);
			
				newValues.put("id", i);
				newValues.put("id_mls", i2);
				newValues.put("sid", sid);
				
				return mewaiter.insert(DATABASE_TABLE10, null, newValues);
		
		}

		public Dish getDishesByID(int id_section, int id_subsection,
				int id_menu, String name) {
			Dish dish =  new Dish(1, 1, 1, 1, name, name, name, name, name, name);
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE5, dishes,
					KEY_ID2 + "=" + id_section + " AND " + KEY_ID4 + "=" + id_subsection + " AND " + KEY_ID1 + "=" + id_menu + " AND " + KEY_COL1 + " LIKE '" + name + "'",
					null, null, null, null, null);
			//Cursor result = mewaiter.query(DATABASE_TABLE5, dishes, null, null, null, null, null);
			
			if (result.moveToFirst())
			do {
			dish = new Dish(
					result.getInt(result.getColumnIndex("id")),
					result.getInt(result.getColumnIndex("id_menu")),
					result.getInt(result.getColumnIndex("id_section")),
					result.getInt(result.getColumnIndex("id_subsection")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("price")),
					result.getString(result.getColumnIndex("description")),
					result.getString(result.getColumnIndex("short_title")),
					result.getString(result.getColumnIndex("sd_dish_id"))
			
			);
			} while(result.moveToNext());
			
			
			return dish;
		}

		public ArrayList<DishMod> getDishesMods(String sid) {
		
				ContentValues newValues = new ContentValues();
				ArrayList<DishMod> dishmods = new ArrayList<DishMod>();	
				
				
				Cursor result = mewaiter.query(true, DATABASE_TABLE10, dishesmods,
					KEY_COL32 +	"='" + sid + "'", null, null, null, null, null);
				//Cursor result2 = mewaiter.query(DATABASE_TABLE10, dishesmods, null, null, null, null, null);
				//Cursor result = mewaiter.query(DATABASE_TABLE5, dishes, null, null, null, null, null);
				
				if (result.moveToFirst())
				do {
					dishmods.add(new DishMod(
						result.getInt(result.getColumnIndex("id")),
						result.getInt(result.getColumnIndex("id_mls")),
						result.getString(result.getColumnIndex("sid"))
				
				));
				} while(result.moveToNext());
				
				
				return dishmods;
			
				
				
		}
		public ArrayList<Mod> getMods(int id_list) {
			
			//ContentValues newValues = new ContentValues();
			ArrayList<Mod> mods = new ArrayList<Mod>();	
			
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE9, modifiers,
					KEY_ID5 + "=" + id_list, null, null, null, null, null);
			
			//Cursor result = mewaiter.query(DATABASE_TABLE9, modifiers, null, null, null, null, null);
			if (result.moveToFirst())
			do {
				mods.add(new Mod(
					result.getInt(result.getColumnIndex("id_list")),
					result.getInt(result.getColumnIndex("id_modifier")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("sd_modifierid")),
					result.getString(result.getColumnIndex("description")),
					result.getString(result.getColumnIndex("price"))						
			
			));
			} while(result.moveToNext());
			
			
			return mods;
		
			
			
	}

		
public Mod getModsSid(String sid) {
			
			//ContentValues newValues = new ContentValues();
			Mod mods = new Mod(0, 0, sid, sid, sid, sid, sid);	
			
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE9, modifiers,
					KEY_COL32 + "='" + sid +"'", null, null, null, null, null);
			
			//Cursor result = mewaiter.query(DATABASE_TABLE9, modifiers, null, null, null, null, null);
			if (result.moveToFirst())
			do {
				mods = new Mod(
					result.getInt(result.getColumnIndex("id_list")),
					result.getInt(result.getColumnIndex("id_modifier")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("sd_modifierid")),
					result.getString(result.getColumnIndex("description")),
					result.getString(result.getColumnIndex("price"))						
			
			);
			} while(result.moveToNext());
			
			
			return mods;
		
			
			
	}

public ArrayList<Mod> getModsSidBySidML(String sid_ml) {
	
	//ContentValues newValues = new ContentValues();
	ArrayList<Mod> mods = new ArrayList<Mod>();	
	
	
	Cursor result = mewaiter.query(true, DATABASE_TABLE9, modifiers,
			KEY_COL32 + " like '%" + sid_ml +"%'", null, null, null, null, null);
	
	//Cursor result = mewaiter.query(DATABASE_TABLE9, modifiers, null, null, null, null, null);
	if (result.moveToFirst())
	do {
		mods.add( new Mod(result.getInt(result.getColumnIndex("id_list")),
			result.getInt(result.getColumnIndex("id_modifier")),
			result.getString(result.getColumnIndex("name")),
			result.getString(result.getColumnIndex("sid")),
			result.getString(result.getColumnIndex("sd_modifierid")),
			result.getString(result.getColumnIndex("description")),
			result.getString(result.getColumnIndex("price"))));						
	
	
	} while(result.moveToNext());
	
	
	return mods;

	
	
}
public Mod getModsValue(String value, String sid_mls) {
	
	//ContentValues newValues = new ContentValues();
	Mod mods = new Mod(0, 0, null,null,null,null,null);	
	
	
	Cursor result = mewaiter.query(true, DATABASE_TABLE9, modifiers,
			KEY_COL1 + "='" + value +"' AND " + KEY_COL32 + " like '%" + sid_mls + "%'", null, null, null, null, null);
	
	//Cursor result = mewaiter.query(DATABASE_TABLE9, modifiers, null, null, null, null, null);
	if (result.moveToFirst())
	do {
		mods = new Mod(
			result.getInt(result.getColumnIndex("id_list")),
			result.getInt(result.getColumnIndex("id_modifier")),
			result.getString(result.getColumnIndex("name")),
			result.getString(result.getColumnIndex("sid")),
			result.getString(result.getColumnIndex("sd_modifierid")),
			result.getString(result.getColumnIndex("description")),
			result.getString(result.getColumnIndex("price"))						
	
	);
	} while(result.moveToNext());
	
	
	return mods;

	
	
}

		public ArrayList<DishMod> getAllDishesMods() {
			ContentValues newValues = new ContentValues();
			ArrayList<DishMod> dishmods = new ArrayList<DishMod>();	
			
			
			Cursor result = mewaiter.query(DATABASE_TABLE10, dishesmods,
					null, null, null, null, KEY_ID);
			//Cursor result = mewaiter.query(DATABASE_TABLE5, dishes, null, null, null, null, null);
			
			if (result.moveToFirst())
			do {
				dishmods.add(new DishMod(
						result.getInt(result.getColumnIndex("id")),
						result.getInt(result.getColumnIndex("id_mls")),
						result.getString(result.getColumnIndex("sid"))
			));
			} while(result.moveToNext());
			
			
			return dishmods;
		
			
		}

		public ArrayList<ListMod> getListMod(int idMLS) {
			
	
			ArrayList<ListMod> listmod = new ArrayList<ListMod>();
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE8, modifier_list,
					KEY_ID8 + "=" + idMLS,	null, null, null, null, null);
			
			
			if (result.moveToFirst())
			do {
				listmod.add(new ListMod(
						result.getInt(result.getColumnIndex("id_list")),
						result.getInt(result.getColumnIndex("id_mls")),
						result.getString(result.getColumnIndex("sid")),
						result.getString(result.getColumnIndex("name")),
						result.getString(result.getColumnIndex("is_mandatory")),
						result.getString(result.getColumnIndex("is_multioption")),
						result.getString(result.getColumnIndex("selected"))
				));
			} while(result.moveToNext());
			
			return listmod;
		}
		public ListMod getListModId(int idMLS) {
			
			
			ListMod listmod = new ListMod (idMLS, idMLS, null, null, null, null, null);
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE8, modifier_list,
					KEY_ID5 + "=" + idMLS,	null, null, null, null, null);
			
			
			if (result.moveToFirst())
			do {
				listmod = new ListMod(
						result.getInt(result.getColumnIndex("id_list")),
						result.getInt(result.getColumnIndex("id_mls")),
						result.getString(result.getColumnIndex("sid")),
						result.getString(result.getColumnIndex("name")),
						result.getString(result.getColumnIndex("is_mandatory")),
						result.getString(result.getColumnIndex("is_multioption")),
						result.getString(result.getColumnIndex("selected"))
						
				);
			} while(result.moveToNext());
			
			return listmod;
		}
		
		public ListMod getListModSid(String sid) {
			
			
			ListMod listmod = new ListMod(0, 0, sid, sid, sid, sid, sid);
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE8, modifier_list,
					KEY_COL32 + "='" + sid + "'",	null, null, null, null, null);
			
			
			if (result.moveToFirst())
			do {
				listmod = new ListMod(
						result.getInt(result.getColumnIndex("id_list")),
						result.getInt(result.getColumnIndex("id_mls")),
						result.getString(result.getColumnIndex("sid")),
						result.getString(result.getColumnIndex("name")),
						result.getString(result.getColumnIndex("is_mandatory")),
						result.getString(result.getColumnIndex("is_multioption")),
						result.getString(result.getColumnIndex("selected"))
				);
			} while(result.moveToNext());
			
			return listmod;
		}
		
		public ListModSet getListModSet(int id) {
			
			
			ListModSet listmodset = new ListModSet(1, null, null);
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE13, modifier_list_set,
					KEY_ID + "=" + id,	null, null, null, null, null);
			
			
			if (result.moveToFirst())
			do {
			listmodset = new ListModSet(
					result.getInt(result.getColumnIndex("id")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name"))					
			);
			} while(result.moveToNext());
			
			return listmodset;
		}
		
		public ListModSet getListModSetSid(String sid) {
			
			
			ListModSet listmodset = new ListModSet(1, null, null);
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE13, modifier_list_set,
					KEY_COL32 + "='" + sid + "'",	null, null, null, null, null);
			
			
			if (result.moveToFirst())
			do {
			listmodset = new ListModSet(
					result.getInt(result.getColumnIndex("id")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name"))					
			);
			} while(result.moveToNext());
			
			return listmodset;
		}
		// Orders
		
		public ArrayList<Order> getListOrder(int id_table) {
			
			
			ContentValues newValues = new ContentValues();
			ArrayList<Order> orders = new ArrayList<Order>();	
			
			
			Cursor result = mewaiter.query(DATABASE_TABLE11, order,
					null, null, null, null, KEY_ID);
			
			if (result.moveToFirst())
			do {
				
				
				orders.add(new Order(
					result.getInt(result.getColumnIndex("id")),
					result.getInt(result.getColumnIndex("id_table")),
					result.getString(result.getColumnIndex("sid_table")),
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("category_sid")),
					result.getString(result.getColumnIndex("price")),
					result.getString(result.getColumnIndex("note")),			
					result.getInt(result.getColumnIndex("status")),
					result.getString(result.getColumnIndex("product_name")),
					result.getInt(result.getColumnIndex("quantity")),
					null
					
			));
			} while(result.moveToNext());
			
			
			return orders;
		}	
		
		public ArrayList<Order> getOrderByTable(String sid_table) {
			
			
			ContentValues newValues = new ContentValues();
			ArrayList<Order> orders = new ArrayList<Order>();	
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE11, order,
					KEY_COL39 + "='" + sid_table + "'",	null, null, null, null, null);
			//Cursor result2 = mewaiter.query(DATABASE_TABLE11, order, null, null, null, null, null);
			
			if (result.moveToFirst())
			do {
				orders.add(new Order(
						result.getInt(result.getColumnIndex("id")),
						result.getInt(result.getColumnIndex("id_table")),
						result.getString(result.getColumnIndex("sid_table")),
						result.getString(result.getColumnIndex("sid")),
						result.getString(result.getColumnIndex("category_sid")),
						result.getString(result.getColumnIndex("price")),
						result.getString(result.getColumnIndex("note")),			
						result.getInt(result.getColumnIndex("status")),
						result.getString(result.getColumnIndex("product_name")),
						result.getInt(result.getColumnIndex("quantity")),
						null
				));	
						
			} while(result.moveToNext());
			
			return orders;
		}
		
public ArrayList<Ordermods> getOrdermodsByID(int idOrder) {
			
			ArrayList<Ordermods> ordermods = new ArrayList<Ordermods>();	
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE14, ordermod,
					KEY_ID9 + "=" + idOrder,	null, null, null, null, null);
			
			
			
			if (result.moveToFirst())
			do {
				ordermods.add(new Ordermods(
						result.getInt(result.getColumnIndex("id_order")),
						result.getString(result.getColumnIndex("sid_mls")),
						result.getString(result.getColumnIndex("sid_ml")),
						result.getString(result.getColumnIndex("sid_modifier")),
						result.getString(result.getColumnIndex("value"))		
						
				));	
						
			} while(result.moveToNext());
			
			return ordermods;
		}

public ArrayList<Ordermods> getOrdermodsByIDMLS(int idOrder, String sid_mls) {
	
	ArrayList<Ordermods> ordermods = new ArrayList<Ordermods>();	
	Cursor result =null;
	if ( sid_mls.contains("%"))
	{
		result = mewaiter.query(true, DATABASE_TABLE14, ordermod,
				KEY_ID9 + "=" + idOrder, null, KEY_COL36, null, null, null);
	}
	else
	{
		result = mewaiter.query(true, DATABASE_TABLE14, ordermod,
				KEY_ID9 + "=" + idOrder + " AND " + KEY_COL36 + "='" + sid_mls + "'",	null, KEY_COL36, null, null, null);
	}

	if (result.moveToFirst())
	do {
		ordermods.add(new Ordermods(
				result.getInt(result.getColumnIndex("id_order")),
				result.getString(result.getColumnIndex("sid_mls")),
				result.getString(result.getColumnIndex("sid_ml")),
				result.getString(result.getColumnIndex("sid_modifier")),
				result.getString(result.getColumnIndex("value"))		
				
		));	
				
	} while(result.moveToNext());
	
	return ordermods;
}

public ArrayList<Ordermods> getOrdermodsByIDMLSML(int idOrder, String sid_mls, String sid_ml) {
	
	ArrayList<Ordermods> ordermods = new ArrayList<Ordermods>();
	Cursor result= null;
	
	if ( sid_ml.contains("%"))
	{
		result = mewaiter.query(true, DATABASE_TABLE14, ordermod,
				KEY_ID9 + "=" + idOrder + " AND " + KEY_COL36 + "='" + sid_mls + "'", null, null, null, null, null);
	}
	else
	{
		result = mewaiter.query(true, DATABASE_TABLE14, ordermod,
				KEY_ID9 + "=" + idOrder + " AND " + KEY_COL36 + "='" + sid_mls + "'" + " AND " +
					 KEY_COL37 + "='" + sid_ml + "'",	null, null, null, null, null);
	}
	
	
	
	
	if (result.moveToFirst())
	do {
		ordermods.add(new Ordermods(
				result.getInt(result.getColumnIndex("id_order")),
				result.getString(result.getColumnIndex("sid_mls")),
				result.getString(result.getColumnIndex("sid_ml")),
				result.getString(result.getColumnIndex("sid_modifier")),
				result.getString(result.getColumnIndex("value"))		
				
		));	
				
	} while(result.moveToNext());
	
	return ordermods;
}

public Ordermods getOrdermodsByNameIdOrder(int idOrder, String sid_table, String value) {
	
	Ordermods ordermods = new Ordermods(idOrder, value, value, value, value);	
	
	Cursor result = mewaiter.query(true, DATABASE_TABLE14, ordermod,
			KEY_ID9 + "=" + idOrder + " AND " + KEY_COL1 + "='" + value + "' AND " + KEY_COL32 + "='" + sid_table + "'",	null, null, null, null, null);
	
	
	if (result.moveToFirst())
	do {
		ordermods = new Ordermods(
				result.getInt(result.getColumnIndex("id_order")),
				result.getString(result.getColumnIndex("sid_mls")),
				result.getString(result.getColumnIndex("sid_ml")),
				result.getString(result.getColumnIndex("sid_modifier")),
				result.getString(result.getColumnIndex("value"))		
		);	
				
	} while(result.moveToNext());
	
	return ordermods;
}

public ArrayList<Ordermods> getOrdermodsByIDMLSM(int idOrder, String sid_mls, String sid_ml, String sid_m) {
	
	ArrayList<Ordermods> ordermods = new ArrayList<Ordermods>();	
	Cursor result = null;
	
	if ( sid_m.contains("%"))
	{
		result = mewaiter.query(true, DATABASE_TABLE14, ordermod,
				KEY_ID9 + "=" + idOrder + " AND " + KEY_COL36 + "='" + sid_mls + "'" + " AND " + KEY_COL37 + "='" + sid_ml + "'", null, null, null, null, null);
	}
	else
	{
		result = mewaiter.query(true, DATABASE_TABLE14, ordermod,
			KEY_ID9 + "=" + idOrder + " AND " + KEY_COL36 + "='" + sid_mls + "'" + " AND " + KEY_COL37 + "='" + sid_ml + "'" + 
					" AND " + KEY_COL38 + "='" + sid_m + "'", null, null, null, null, null);
	
	}
	
	if (result.moveToFirst())
	do {
		ordermods.add(new Ordermods(
				result.getInt(result.getColumnIndex("id_order")),
				result.getString(result.getColumnIndex("sid_mls")),
				result.getString(result.getColumnIndex("sid_ml")),
				result.getString(result.getColumnIndex("sid_modifier")),
				result.getString(result.getColumnIndex("value"))		
				
		));	
				
	} while(result.moveToNext());
	
	return ordermods;
}
public Order getOrder(int id) {

			Order orders = new Order(id, id, null, null, null, null, null, id, null, id, null);	
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE11, order,
					KEY_ID + "=" + id,	null, null, null, null, null);
			if (result.moveToFirst())
			do {
				orders = new Order(
						result.getInt(result.getColumnIndex("id")),
						result.getInt(result.getColumnIndex("id_table")),
						result.getString(result.getColumnIndex("sid_table")),
						result.getString(result.getColumnIndex("sid")),
						result.getString(result.getColumnIndex("category_sid")),
						result.getString(result.getColumnIndex("price")),
						result.getString(result.getColumnIndex("note")),			
						result.getInt(result.getColumnIndex("status")),
						result.getString(result.getColumnIndex("product_name")),
						result.getInt(result.getColumnIndex("quantity")),
						null
				);	
						
			} while(result.moveToNext());
			
			return orders;
		}
		
		public int removeOrders() {
			return mewaiter.delete(DATABASE_TABLE11, null, null);
		}
		public int removeOrderMods() {
			return mewaiter.delete(DATABASE_TABLE14, null, null);
		}
		public int removeOrderModsByID(int id_order) {
			return mewaiter.delete(DATABASE_TABLE14, KEY_ID9 + "=" + id_order, null);
		}
		public int removeDiscountSelByID(int id_order) {
			return mewaiter.delete(DATABASE_TABLE19, KEY_ID9 + "=" + id_order, null);
		}
		public int removeOrderByTable(String sid_table) {
			//int id_table = Integer.parseInt(idTable);
			
			int OK = mewaiter.delete(DATABASE_TABLE11, KEY_COL39 + " = '" + sid_table + "'" , null);
			Cursor cursor = mewaiter.query(DATABASE_TABLE11, order, KEY_COL39 + " = '" + sid_table + "'", null, null, null, null);
			while ( cursor.getCount() != 0)
			{
				cursor = mewaiter.query(DATABASE_TABLE11, order, KEY_COL39 + " = '" + sid_table + "'", null, null, null, null);
				SystemClock.sleep(10);
			}
			
			return OK;
		}
		
		public int removeOrderById(String id) {
			int i = Integer.parseInt(id);
			
			return mewaiter.delete(DATABASE_TABLE11, KEY_ID + " = " + i, null);
		}
		
		public int updateOrders( int id_order, int id_table, String sid_table, String sid, String category_sid, String price, 
				 String note, int status, String product_name, int cantidad ) {
				
			
			Order orders = new Order(status, status, product_name, product_name, product_name, product_name, product_name, status, product_name, status, null);	
			ContentValues newValues = new ContentValues();
						
				//newValues.put("id", id); (Es automático)
				newValues.put("id_table", id_table);
				newValues.put("sid_table", sid_table);			
				newValues.put("sid", sid);
				newValues.put("category_sid", category_sid);
				newValues.put("price", price);	
				newValues.put("note", note);
				newValues.put("status", status);			
				newValues.put("product_name", product_name);
				newValues.put("quantity", cantidad);
			
				//long result2=mewaiter.insert(DATABASE_TABLE11, null, newValues);
				long result2= mewaiter.update(DATABASE_TABLE11, newValues, KEY_ID + " =" + id_order, null);
				if (result2 == -1)
				{
					return -1;
				}
				else
				{
						Cursor result= mewaiter.query(true, DATABASE_TABLE11, order, KEY_ID + " =" + id_order ,null, null, null, null, null);
					
						result.moveToFirst();
						orders = new Order(
								result.getInt(result.getColumnIndex("id")),
								result.getInt(result.getColumnIndex("id_table")),
								result.getString(result.getColumnIndex("sid_table")),
								result.getString(result.getColumnIndex("sid")),
								result.getString(result.getColumnIndex("category_sid")),
								result.getString(result.getColumnIndex("price")),
								result.getString(result.getColumnIndex("note")),			
								result.getInt(result.getColumnIndex("status")),
								result.getString(result.getColumnIndex("product_name")),
								result.getInt(result.getColumnIndex("quantity")),
								null
								);
					
					}
						return orders.getId();
				
		
		}
		
		public int insertOrders( int id_table, String sid_table, String sid, String category_sid, String price, 
				 String note, int status, String product_name, int cantidad ) {
				
			
			Order orders = new Order(status, status, product_name, product_name, product_name, product_name, product_name, status, product_name, status, null);	
			ContentValues newValues = new ContentValues();
						
				//newValues.put("id", id); (Es automático)
				newValues.put("id_table", id_table);
				newValues.put("sid_table", sid_table);			
				newValues.put("sid", sid);
				newValues.put("category_sid", category_sid);
				newValues.put("price", price);	
				newValues.put("note", note);
				newValues.put("status", status);			
				newValues.put("product_name", product_name);
				newValues.put("quantity", cantidad);
			
				long result2=mewaiter.insert(DATABASE_TABLE11, null, newValues);
				
				if (result2 == -1)
				{
					return -1;
				}
				else
				{
						Cursor result= mewaiter.query(DATABASE_TABLE11, order, null, null, null, null, KEY_ID);
					
						result.moveToPosition(result.getCount() - 1);
						orders = new Order(
								result.getInt(result.getColumnIndex("id")),
								result.getInt(result.getColumnIndex("id_table")),
								result.getString(result.getColumnIndex("sid_table")),
								result.getString(result.getColumnIndex("sid")),
								result.getString(result.getColumnIndex("category_sid")),
								result.getString(result.getColumnIndex("price")),
								result.getString(result.getColumnIndex("note")),			
								result.getInt(result.getColumnIndex("status")),
								result.getString(result.getColumnIndex("product_name")),
								result.getInt(result.getColumnIndex("quantity")),
								null
								);
					
					}
						return orders.getId();
				
		
		}
		
		public boolean updateOrderStatus(String sid, int status) {
			ContentValues newValues = new ContentValues();
			newValues.put(KEY_COL26, status);
			return mewaiter.update(DATABASE_TABLE11, newValues, KEY_COL39 + "='" + sid + "'", null) > 0;
			
		}
		
		// Mesas y Zonas
		
		public ArrayList<Mesa> getListMesas() {
			
			ContentValues newValues = new ContentValues();
			ArrayList<Mesa> mesas = new ArrayList<Mesa>();	
			
			
			Cursor result = mewaiter.query(DATABASE_TABLE12, tables,
					null, null, null, null, KEY_COL30);
			
			if (result.moveToFirst())
			do {
				mesas.add(new Mesa(
						result.getInt(result.getColumnIndex("id_floor")),
						result.getInt(result.getColumnIndex("id_table")),
						result.getString(result.getColumnIndex("sid_floor")),
						result.getString(result.getColumnIndex("sid_table")),
						result.getString(result.getColumnIndex("name")),
						result.getString(result.getColumnIndex("number")),
						result.getInt(result.getColumnIndex("status")),
						result.getInt(result.getColumnIndex("pax"))
					
			));
			} while(result.moveToNext());
			
			
			return mesas;
		}	
		
		public Mesa getTableById(String sid_table) {
			
			Mesa mesa = new Mesa();
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE12, tables,
					KEY_COL39 + "='" + sid_table + "'",	null, null, null, null, null);
			
			
			if (result.moveToFirst())
			do {
				mesa = new Mesa(
						result.getInt(result.getColumnIndex("id_floor")),
						result.getInt(result.getColumnIndex("id_table")),
						result.getString(result.getColumnIndex("sid_floor")),
						result.getString(result.getColumnIndex("sid_table")),
						result.getString(result.getColumnIndex("name")),
						result.getString(result.getColumnIndex("number")),
						result.getInt(result.getColumnIndex("status")),
						result.getInt(result.getColumnIndex("pax"))
				);	
						
			} while(result.moveToNext());
			
			return mesa;
		}
		public Mesa getTableByZoneNum(String zona, String number) {
			
			Mesa mesa = new Mesa();
			
			Cursor result = mewaiter.query(true, DATABASE_TABLE12, tables,
					KEY_COL1 + "='" + zona + "' AND " + KEY_COL30 + "='" + number + "'",null, null, null, null, null);
			
			
			if (result.moveToFirst())
			do {
				mesa = new Mesa(
						result.getInt(result.getColumnIndex("id_floor")),
						result.getInt(result.getColumnIndex("id_table")),
						result.getString(result.getColumnIndex("sid_floor")),
						result.getString(result.getColumnIndex("sid_table")),
						result.getString(result.getColumnIndex("name")),
						result.getString(result.getColumnIndex("number")),
						result.getInt(result.getColumnIndex("status")),
						result.getInt(result.getColumnIndex("pax"))
				);	
						
			} while(result.moveToNext());
			
			return mesa;
		}
		
		public int removeMesas() {
			return mewaiter.delete(DATABASE_TABLE12, null, null);
		}
		
		public int removeMesaById(String sid_table) {
			//int id_table = Integer.parseInt(idTable);
			return mewaiter.delete(DATABASE_TABLE12, KEY_COL39 + " = '" + sid_table + "'", null);
		}
		
		public int removeMesaByZoneNum(String sid_floor, String number) {
					
			return mewaiter.delete(DATABASE_TABLE11, KEY_COL29 + " = '" + sid_floor + "' AND " + KEY_COL30 + "='" +  number + "'" , null);
		}
		
		
		public String insertMesas(int id_floor, int id_table, String sid_floor, String sid_table, String name, String number, int status, int pax)
		{
			
			Mesa mesa = new Mesa();	
			ContentValues newValues = new ContentValues();
			
				newValues.put("id_floor", id_floor);
				newValues.put("id_table", id_table);
				newValues.put("sid_floor", sid_floor);
				newValues.put("sid_table", sid_table);
				newValues.put("name", name);
				newValues.put("number", number);
				newValues.put("status", status);
				newValues.put("pax", pax);
							
				
				long result2=mewaiter.insert(DATABASE_TABLE12, null, newValues);
				
				if (result2 == -1)
				{
					return "-1";
				}
				else
				{
					Cursor result= mewaiter.query(DATABASE_TABLE12, tables, null, null, null, null, KEY_ID11);
					
					result.moveToPosition(result.getCount() - 1);
					if (result.moveToFirst())
						do {
							mesa = new Mesa(
									result.getInt(result.getColumnIndex("id_floor")),
									result.getInt(result.getColumnIndex("id_table")),
									result.getString(result.getColumnIndex("sid_floor")),
									result.getString(result.getColumnIndex("sid_table")),
									result.getString(result.getColumnIndex("name")),
									result.getString(result.getColumnIndex("number")),
									result.getInt(result.getColumnIndex("status")),
									result.getInt(result.getColumnIndex("pax"))
							);	
									
						} while(result.moveToNext());
						
						return mesa.getSidTable();
				}
		
		}
		
		public boolean updateMesaStatus(String sid_table, int status) {
			ContentValues newValues = new ContentValues();
			newValues.put(KEY_COL26, status);
			return mewaiter.update(DATABASE_TABLE12, newValues, KEY_COL39 + "='" + sid_table + "'", null) > 0;
			
		}

		public Long insertModifierListSets(String id, String sid,
				String name) {
			
			Integer i = Integer.parseInt(id);
			ContentValues newValues = new ContentValues();
			newValues.put("id", id);
			newValues.put("sid", sid);		
			newValues.put("name", name);			
			long result=mewaiter.insert(DATABASE_TABLE13, null, newValues);
			return result;
		}

		public Long insertOrdersMods(int idOrder, String sidmls, String sidml,
				String sid, String value) {
			
			ContentValues newValues = new ContentValues();
			newValues.put("id_order", idOrder);
			newValues.put("sid_mls", sidmls);		
			newValues.put("sid_ml", sidml);	
			newValues.put("sid_modifier", sid);	
			newValues.put("value", value);	
			
			long result=mewaiter.insert(DATABASE_TABLE14, null, newValues);
			return result;
		}

		public ArrayList<Order> getAllOrders() {
			
			ArrayList<Order> orders = new ArrayList<Order>();	
			
			
			Cursor result = mewaiter.query(DATABASE_TABLE11, order, null, null, KEY_COL39, null, KEY_COL39 , null);
			
			if (result.moveToFirst())
			do {
				orders.add(new Order(
						result.getInt(result.getColumnIndex("id")),
						result.getInt(result.getColumnIndex("id_table")),
						result.getString(result.getColumnIndex("sid_table")),
						result.getString(result.getColumnIndex("sid")),
						result.getString(result.getColumnIndex("category_sid")),
						result.getString(result.getColumnIndex("price")),
						result.getString(result.getColumnIndex("note")),			
						result.getInt(result.getColumnIndex("status")),
						result.getString(result.getColumnIndex("product_name")),
						result.getInt(result.getColumnIndex("quantity")),
						null
				));	
						
			} while(result.moveToNext());
			
			return orders;
		}
		
		
public ArrayList<Order> getOrders() {
			
			ArrayList<Order> orders = new ArrayList<Order>();	
			
			
			Cursor result = mewaiter.query(DATABASE_TABLE11, order, null, null, null, KEY_COL39 , null);
			
			if (result.moveToFirst())
			do {
				orders.add(new Order(
						result.getInt(result.getColumnIndex("id")),
						result.getInt(result.getColumnIndex("id_table")),
						result.getString(result.getColumnIndex("sid_table")),
						result.getString(result.getColumnIndex("sid")),
						result.getString(result.getColumnIndex("category_sid")),
						result.getString(result.getColumnIndex("price")),
						result.getString(result.getColumnIndex("note")),			
						result.getInt(result.getColumnIndex("status")),
						result.getString(result.getColumnIndex("product_name")),
						result.getInt(result.getColumnIndex("quantity")),
						null
				));	
						
			} while(result.moveToNext());
			
			return orders;
		}	

public long insertPayments(String id, String sid, String name, String key) {
	ContentValues newValues = new ContentValues();
	int i = Integer.parseInt(id);
	
	newValues.put(KEY_ID, i);
	newValues.put(KEY_COL32, sid);
	newValues.put(KEY_COL1, name);
	newValues.put(KEY_COL63, key);
	
	return mewaiter.insert(DATABASE_TABLE15, null, newValues);
	}

public int removeAppInfo() {
			return mewaiter.delete(DATABASE_TABLE16, null, null);

}
		
public long insertAppInfo(String nameApp, String version, String os, String term_of_use, String privacy, String news, String link) {
	ContentValues newValues = new ContentValues();
	newValues.put(KEY_COL1, nameApp);
	newValues.put(KEY_COL41, os);
	newValues.put(KEY_COL40, version);
	newValues.put(KEY_COL42, term_of_use);
	newValues.put(KEY_COL43, privacy);
	newValues.put(KEY_COL44, news);
	newValues.put(KEY_COL45, link);
	return mewaiter.insert(DATABASE_TABLE16, null, newValues);
	}

public long getAppInfo() {
	
	Cursor result = mewaiter.query(DATABASE_TABLE16, appinfo,
			null, null, null, null, null);
	
	long resultado = 1;
	
	if (result.moveToFirst())
	do {
		
				Login.nameApp = result.getString(result.getColumnIndex("name"));
				Login.version = result.getString(result.getColumnIndex("version"));
				Login.os = result.getString(result.getColumnIndex("os"));
				Login.term_of_use = result.getString(result.getColumnIndex("term_of_use"));
				Login.whats_new = result.getString(result.getColumnIndex("whats_new"));
				Login.privacy = result.getString(result.getColumnIndex("privacy_policy"));
				Login.link_to_store = result.getString(result.getColumnIndex("link_to_store"));
				resultado=0;
	} while(result.moveToNext());
	
	return resultado;
}

public int removeUserInfo() {
	return mewaiter.delete(DATABASE_TABLE17, null, null);

}

public long insertUserInfo(String username, String email, String first_name, String middle_name, String last_name, String birthday, String mobile_number, String device_id) {
ContentValues newValues = new ContentValues();
newValues.put(KEY_COL46, username);
newValues.put(KEY_COL47, email);
newValues.put(KEY_COL48, first_name);
newValues.put(KEY_COL49, middle_name);
newValues.put(KEY_COL50, last_name);
newValues.put(KEY_COL51, birthday);
newValues.put(KEY_COL52, mobile_number);
newValues.put(KEY_COL17, device_id);

return mewaiter.insert(DATABASE_TABLE17, null, newValues);
}

public long getUserInfo() {

Cursor result = mewaiter.query(DATABASE_TABLE17, userinfo,
	null, null, null, null, null);

long resultado = 1;

if (result.moveToFirst())
do {

		Login.username = result.getString(result.getColumnIndex("username"));
		Login.email = result.getString(result.getColumnIndex("email"));
		Login.first_name = result.getString(result.getColumnIndex("first_name"));
		Login.middle_name = result.getString(result.getColumnIndex("middle_name"));
		Login.last_name = result.getString(result.getColumnIndex("last_name"));
		Login.birthday = result.getString(result.getColumnIndex("birthday"));
		Login.mobile_number = result.getString(result.getColumnIndex("mobile_number"));
		Login.device_id= result.getString(result.getColumnIndex("device_id"));
		resultado=0;
} while(result.moveToNext());

return resultado;
}


public int removeDiscounts() {
	return mewaiter.delete(DATABASE_TABLE18, null, null);

}

public long insertDiscounts(String sid, String name, String dtype, String amount, String note, String section_id, String dish_id, String restaurant_id, String menu_id) {
ContentValues newValues = new ContentValues();
newValues.put(KEY_COL32, sid);
newValues.put(KEY_COL1, name);
newValues.put(KEY_COL53, dtype);
newValues.put(KEY_COL54, amount);
newValues.put(KEY_COL28, note);
newValues.put(KEY_COL55, section_id);
newValues.put(KEY_COL56, dish_id);
newValues.put(KEY_COL57, restaurant_id);
newValues.put(KEY_COL58, menu_id);
return mewaiter.insert(DATABASE_TABLE18, null, newValues);
}

public ArrayList<Discount> getDiscounts() {
	
	ArrayList<Discount> discountsList = new ArrayList<Discount>();	

	Cursor result = mewaiter.query(DATABASE_TABLE18, discounts,
	null, null, null, null, null);

if (result.moveToFirst())
do {
			discountsList.add(new Discount(
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("dtype")),
					result.getString(result.getColumnIndex("amount")),
					result.getString(result.getColumnIndex("note")),
					result.getString(result.getColumnIndex("section_id")),
					result.getString(result.getColumnIndex("dish_id")),
					result.getString(result.getColumnIndex("restaurant_id")),
					result.getString(result.getColumnIndex("menu_id"))
			));	
	
} while(result.moveToNext());
	return discountsList;
}


public int removeDiscountSel() {
	return mewaiter.delete(DATABASE_TABLE19, null, null);

}

public long insertDiscountSel(String sid, int id_table, int id_order, String general, String sid_menu, String sid_section, String sid_dish) {
ContentValues newValues = new ContentValues();
newValues.put(KEY_COL32, sid);
newValues.put(KEY_ID11, id_table);
newValues.put(KEY_ID9, id_order );
newValues.put(KEY_COL59, general);
newValues.put(KEY_COL60, sid_menu);
newValues.put(KEY_COL61, sid_section);
newValues.put(KEY_COL62, sid_dish);

return mewaiter.insert(DATABASE_TABLE19, null, newValues);
}

public ArrayList<Discountsel> getDiscountSel() {
	
	ArrayList<Discountsel> discountSelList = new ArrayList<Discountsel>();	

	Cursor result = mewaiter.query(DATABASE_TABLE19, discountsel,
	null, null, null, null, null);

if (result.moveToFirst())
do {
			discountSelList.add(new Discountsel(
					result.getString(result.getColumnIndex("sid")),
					result.getInt(result.getColumnIndex("id_table")),
					result.getInt(result.getColumnIndex("id_order")),
					result.getString(result.getColumnIndex("general")),
					result.getString(result.getColumnIndex("sid_menu")),
					result.getString(result.getColumnIndex("sid_section")),
					result.getString(result.getColumnIndex("sid_dish"))
					
			));	
	
} while(result.moveToNext());
	return discountSelList;
}

public ArrayList<Discountsel> getDiscountSelByIdOrder( int id_order) {
	
	ArrayList<Discountsel> discountSelList = new ArrayList<Discountsel>();	

	Cursor result = mewaiter.query(true, DATABASE_TABLE19, discountsel,
			KEY_ID9 + "=" + id_order, null, null, null, null, null);
	

if (result.moveToFirst())
do {
			discountSelList.add(new Discountsel(
					result.getString(result.getColumnIndex("sid")),
					result.getInt(result.getColumnIndex("id_table")),
					result.getInt(result.getColumnIndex("id_order")),
					result.getString(result.getColumnIndex("general")),
					result.getString(result.getColumnIndex("sid_menu")),
					result.getString(result.getColumnIndex("sid_section")),
					result.getString(result.getColumnIndex("sid_dish"))
					
			));	
	
} while(result.moveToNext());
	return discountSelList;
}

public ArrayList<Discountsel> getDiscountSelByIdTable( int id_table) {
	
	ArrayList<Discountsel> discountSelList = new ArrayList<Discountsel>();	

	Cursor result = mewaiter.query(true, DATABASE_TABLE19, discountsel,
			KEY_ID11 + "=" + id_table, null, null, null, null, null);
	

if (result.moveToFirst())
do {
			discountSelList.add(new Discountsel(
					result.getString(result.getColumnIndex("sid")),
					result.getInt(result.getColumnIndex("id_table")),
					result.getInt(result.getColumnIndex("id_order")),
					result.getString(result.getColumnIndex("general")),
					result.getString(result.getColumnIndex("sid_menu")),
					result.getString(result.getColumnIndex("sid_section")),
					result.getString(result.getColumnIndex("sid_dish"))
					
			));	
	
} while(result.moveToNext());
	return discountSelList;
}

public Discount getDiscountsByName(String nombre) {
	Discount discount = null;	

	Cursor result = mewaiter.query(true, DATABASE_TABLE18, discounts,
	KEY_COL1 + "='" + nombre + "'", null, null, null, null, null);

if (result.moveToFirst())
do {
			discount = new Discount(
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("dtype")),
					result.getString(result.getColumnIndex("amount")),
					result.getString(result.getColumnIndex("note")),
					result.getString(result.getColumnIndex("section_id")),
					result.getString(result.getColumnIndex("dish_id")),
					result.getString(result.getColumnIndex("restaurant_id")),
					result.getString(result.getColumnIndex("menu_id"))
			);	
	
} while(result.moveToNext());
	return discount;
}

public Discount getDiscountsBySid(String sid_discount) {
	Discount discount = null;	

	Cursor result = mewaiter.query(true, DATABASE_TABLE18, discounts,
	KEY_COL32 + "='" + sid_discount + "'", null, null, null, null, null);

if (result.moveToFirst())
do {
			discount = new Discount(
					result.getString(result.getColumnIndex("sid")),
					result.getString(result.getColumnIndex("name")),
					result.getString(result.getColumnIndex("dtype")),
					result.getString(result.getColumnIndex("amount")),
					result.getString(result.getColumnIndex("note")),
					result.getString(result.getColumnIndex("section_id")),
					result.getString(result.getColumnIndex("dish_id")),
					result.getString(result.getColumnIndex("restaurant_id")),
					result.getString(result.getColumnIndex("menu_id"))
			);	
	
} while(result.moveToNext());
	return discount;
}

public long removeOrderModsById(int id_order) {
	
		//int id_table = Integer.parseInt(idTable);
		return mewaiter.delete(DATABASE_TABLE14, KEY_ID9 + " = " + id_order + "" , null);
	}
public long removeDiscountsSelByTable(int id_table) {
	
	//String  id = String.valueOf(id_table);
	return mewaiter.delete(DATABASE_TABLE19, KEY_ID11 + " = " + id_table , null);
}

public Dish getDishByName(String name) {
	Dish dish = new Dish(0, 0, 0, 0, name, name, name, name, name, name);
	
	
	Cursor result = mewaiter.query(true, DATABASE_TABLE5, dishes, KEY_COL1 + "='" + name + "'",
			null, null, null, null, null);
	//Cursor result = mewaiter.query(DATABASE_TABLE5, dishes, null, null, null, null, null);
	
	if (result.moveToFirst())
	do {
	dish = new Dish(
			result.getInt(result.getColumnIndex("id")),
			result.getInt(result.getColumnIndex("id_menu")),
			result.getInt(result.getColumnIndex("id_section")),
			result.getInt(result.getColumnIndex("id_subsection")),
			result.getString(result.getColumnIndex("sid")),
			result.getString(result.getColumnIndex("name")),
			result.getString(result.getColumnIndex("price")),
			result.getString(result.getColumnIndex("description")),
			result.getString(result.getColumnIndex("short_title")),
			result.getString(result.getColumnIndex("sd_dish_id")));
	} while(result.moveToNext());
	return dish;
	
	}

public Dish getDishByNameMenu(int id_menu, String seleccion) {

	Dish dish = new Dish(0, 0, 0, 0, null,null, seleccion, seleccion, seleccion, seleccion);
	
	
	Cursor result = mewaiter.query(true, DATABASE_TABLE5, dishes, KEY_COL1 + "='" + seleccion + "' AND " + KEY_ID1 + "=" + id_menu,
			null, null, null, null, null);
	//Cursor result = mewaiter.query(DATABASE_TABLE5, dishes, null, null, null, null, null);
	
	if (result.moveToFirst())
	do {
	dish = new Dish(
			result.getInt(result.getColumnIndex("id")),
			result.getInt(result.getColumnIndex("id_menu")),
			result.getInt(result.getColumnIndex("id_section")),
			result.getInt(result.getColumnIndex("id_subsection")),
			result.getString(result.getColumnIndex("sid")),
			result.getString(result.getColumnIndex("name")),
			result.getString(result.getColumnIndex("price")),
			result.getString(result.getColumnIndex("description")),
			result.getString(result.getColumnIndex("short_title")),
			result.getString(result.getColumnIndex("sd_dish_id")));
	} while(result.moveToNext());
	return dish;
}


}



		
