package com.aforma.mewaiter.app;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.aforma.mewaiter.app.R;
import com.aforma.mewaiter.utils.ActionBar;
import com.aforma.mewaiter.utils.DBHelper;
import com.aforma.mewaiter.utils.SeparatedListAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
* 
* Activity MenuDia es la que se utiliza para mostrar la pantalla "Menœ Cerrado del d’a" y realizar las operaci—nes necesarias.
*
* 
*/
public class MenuDia extends Activity{
	
	public static Activity parentD= null;
	public Context DetalleContext;
	public int cantidad;
	public String note;
	public int id_mod;
	public String description;
	public String price;
	public String valor;
	public int id_dish;
	public String sid;
	public int id_section;
	public int id_subsection;
	public int id_table=0;
	public Order order= null;
	public String name="";
	public String category_sid = null;
	public String sid_modifier = null;
	public String sid_mls = null;
	public String sid_ml =  null;
	public String sid_table =null;
	ArrayList<Ordermods> ordermods = new ArrayList<Ordermods>();
	public ArrayList<Mod> mods = new ArrayList<Mod>();
	public ArrayList<ListMod> listmod = new ArrayList<ListMod>();
	public ArrayList<ListModSet> listmodsets = new ArrayList<ListModSet>();
	public ArrayList <Discountsel> discountsels = new ArrayList<Discountsel>();
	public DBHelper BD = null;
	public Boolean dodiscount =  false;
	public Boolean is_mandatory =  false;
	public ArrayList<HashMap<String, String>> mandaSels = new ArrayList<HashMap<String, String>>();
	public ArrayList<String> mandatories = new ArrayList<String>();
	public HashMap<String, String> map = new HashMap<String, String>();
    public int totalMand = 0;
	public Menu menu;
	
	
	public final static String ITEM_TITLE = "title";
    public final static String ITEM_CAPTION = "caption";

    public String id_menuS;
    public int id_menu;
    
    
	
    // Adapter for ListView Contents
    private SeparatedListAdapter adapter;

    // ListView Contents
    private ListView journalListView;

    public Map<String, ?> createItem(String title, String caption)
        {
            Map<String, String> item = new HashMap<String, String>();
            item.put(ITEM_TITLE, title);
            item.put(ITEM_CAPTION, caption);
            return item;
        }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detalle_menu);
		
	
		Intent i = getIntent();
		Bundle b = i.getExtras();
		id_menuS = (String)b.get("id_menu");
		id_menu = Integer.valueOf(id_menuS);
		//id_section = (Integer)b.get("id_section");
		//id_subsection = (Integer)b.get("id_subsection");
		String idr = null;
		menu = new Menu(cantidad, idr, idr, idr, idr);
		
		
		BD=new DBHelper(getApplicationContext());
		
		BD.open();
		menu = BD.getMenu(id_menu);
		category_sid = menu.getSid();
		BD.close();
		
		sid = menu.getSid();
		name = menu.getName();
		price = menu.getPrice();
		
		DetalleContext = this;
		
		if ( Main.m_Text != "")
		{
			BD.open();
			String[] separated = (Main.m_Text).split(" ");
			String mesa = separated[2]; // Contiene el numero de mesa
			String zona = separated[0];
			Mesa mesas = BD.getTableByZoneNum(zona, mesa);
			
			List<Restaurante> rest = BD.getRestaurants();
			
			sid_table = mesas.getSidTable();
			id_table = Integer.parseInt(mesa);
			BD.close();
			if (sid_table == null)
			{
				sid_table = Main.sid_table;
			}else
				if (sid_table.isEmpty())
				{
					sid_table = Main.sid_table;
				}
			
		}
		
		
		
		// Draw ActionBar
		ActionBar actionbar = (ActionBar) findViewById(R.id.actionBarPlato);
		String nombre= Carta.menuSel.getName();
		String s = "";
		if (nombre.length() > 15)
		{
			s = nombre.substring(0,12);
			s= s + "...";
		}
		else 
			s=nombre;
		actionbar.setTitle(s);
		
		 

		
			// Boton volver
			Button btnVolver = (Button) this.findViewById(R.id.btnVolverDetalle);
			btnVolver.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
			
	            	finish();
	            	//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            }
			
			});  

			// Boton volver
						Button btnAddPlate = (Button) this.findViewById(R.id.btnAddPlate);
						btnAddPlate.setOnClickListener(new View.OnClickListener() {
				            public void onClick(View v) {
				            	
				            	addPlatos();
						
				            	
				            }
						
						});  
	
	
		final TextView txtCantidad = (TextView) findViewById(R.id.txtCantidad);
		cantidad=Integer.valueOf((String) txtCantidad.getText());
		final EditText editTxtNote = (EditText) findViewById(R.id.editText1);
		editTxtNote.setVisibility(View.INVISIBLE);
	
					// Agregar nota
					Button btnNota = (Button) this.findViewById(R.id.btNota);
					btnNota.setOnClickListener(new View.OnClickListener() {
			            public void onClick(View v) {
			            	
			            	if (editTxtNote.isShown() )
			            	{
			            		editTxtNote.setVisibility(View.INVISIBLE);
			            	}
			            	else
			            	{
			            		editTxtNote.setVisibility(View.VISIBLE);
			            	}
			    			
			            	
			            	
	
			            }
			            });
				Button btnSuma = (Button) this.findViewById(R.id.btnSuma);
				btnSuma.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View v) {
		            	
		            	Integer cantidad;
		            	cantidad=Integer.valueOf((String) txtCantidad.getText());
		            	cantidad=cantidad+1;
		            	txtCantidad.setText(String.valueOf(cantidad));            	
		            	
		            }
				
				}); 
				
				Button btnResta = (Button) this.findViewById(R.id.btnResta);
				btnResta.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View v) {
		            	
		            	Integer cantidad;
		            	cantidad=Integer.valueOf((String) txtCantidad.getText());
		            	if (cantidad != 1)
		            	{
		            		cantidad=cantidad-1;
		            	}
		            	txtCantidad.setText(String.valueOf(cantidad));            	
		            	
		            }
				
				}); 
	
	
				//Descuentos
				
				BD.open();
								
				final ArrayList<Discount> discounts = BD.getDiscounts();
				
				BD.close();
				
				final LinearLayout ll2 = (LinearLayout) findViewById(R.id.LayoutDisc);
				// Add Discounts
				TextView title2 = new TextView(this);				 		
	            title2.setText("Descuentos");
	            title2.setVisibility(View.INVISIBLE);
	            title2.setTextColor(getResources().getColor(android.R.color.black));
	            title2.setTextSize(18);
	            title2.setTypeface(null, Typeface.BOLD);
	    
	            
	            ll2.addView(title2);
				
				for (int d=0; d<discounts.size();d++)
				{
					String menu_id = discounts.get(d).getMenuId();
					String section_id = discounts.get(d).getSectionId();
					String dish_id = discounts.get(d).getDishId();
					String restaurant_id = discounts.get(d).getRestId();
					if (menu_id == null)
					{
						menu_id="";
					}
					if (restaurant_id == null)
					{
						restaurant_id="";
					}
					
					
					String idm = String.valueOf(id_menu);
					String name = discounts.get(d).getName();
					String sid_discount = discounts.get(d).getSid();
					
					String ids = String.valueOf(id_section);
					String idd = String.valueOf(id_dish);
					
					
					if ( menu_id.contains(idm))
					{
						title2.setVisibility(View.VISIBLE);
						final CheckBox cb = new CheckBox(this);
		                cb.setText(name);
		                cb.setId(d);
		                ll2.addView(cb);
		                dodiscount=true;
					}
					
				}
				 //Agregar
				Button btnAdd2 = (Button) this.findViewById(R.id.btnAdd);
				btnAdd2.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View v) {
		            
		            	
		            	
		            	cantidad=Integer.valueOf((String) txtCantidad.getText());
		            	note = editTxtNote.getText().toString();
		              
		            	saveAnswers();
		            	
		            	BD.open();
		            	int id_order = 0;
		            	id_order = BD.insertOrders(order.getIdTable(), order.getSidTable(), order.getSid(), order.getCategory(), order.getPrice(), order.getNote(), 1, order.getProductName(), order.getQuantity());
		            	if (dodiscount==true)
		            	{
		            		for (int i=0; i<discountsels.size();i++)
		            		{
		            			BD.insertDiscountSel(discountsels.get(i).getSid(), discountsels.get(i).getIdTable(), id_order, "0", "0", "0", discountsels.get(i).getDish());
		            		}
		            	}
		            	BD.close();
		            	
		            	
		            	if ( id_order != 0)
		            	{
			            		
			            		Toast.makeText(DetalleContext, "Menœ agregado al pedido", Toast.LENGTH_LONG).show();
				            	editTxtNote.setVisibility(View.INVISIBLE);
					    		//finish();
					    		Main.agregado=true;
			    				            
		            	}
		            	else
		            	{
		            			Toast.makeText(DetalleContext,"Menœ no se ha podido agregar. ERROR.", Toast.LENGTH_LONG).show();
		    					editTxtNote.setVisibility(View.INVISIBLE);
		    				    //finish();
		    				    Main.agregado=false;
		            	}
		            }
				}); 
	
	
	
	}
	
	
	
	public void addPlatos()	{
	
	
	setContentView(R.layout.menudia);
	final ListView listview3;
	Resources res = getResources();
  

 
 	final DBHelper BD=new DBHelper(getApplicationContext());
	
 	
    
    BD.open();
	ArrayList<Section> sectionlist= BD.getSectionByID(id_menuS);		
	
	
	
	// Create the ListView Adapter
    adapter = new SeparatedListAdapter(this);
    ArrayAdapter<String> listadapter;
    String[] cadena = null;
    
    // Add Sections
    

    for (int i2 = 0; i2 < sectionlist.size(); i2++)
    {
    		String name =  sectionlist.get(i2).getName();
    		int id_section = sectionlist.get(i2).getId();
    		ArrayList <Dish> dishes = BD.getDishesDatos(id_section, 0, id_menu);
    		cadena = new String[dishes.size()];	
    		
    		for (int z=0;z<dishes.size();z++)
    		{
    			cadena[z] = dishes.get(z).getName();
    			
    		}	
    		listadapter = new ArrayAdapter<String>(this, R.layout.list_item2, cadena);
    		adapter.addSection(name, listadapter);
    }
    BD.close();
    // Get a reference to the ListView holder
    journalListView = (ListView) this.findViewById(R.id.list_journal);

    // Set the adapter on the ListView holder
    journalListView.setAdapter(adapter);

    // Listen for Click events
    journalListView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
                {
                    //String item = (String) adapter.getItem(position);
                	Intent i = new Intent(getApplicationContext(), DetallePlato.class);
                	final String  seleccion = journalListView.getItemAtPosition(position).toString();
	            	BD.open();
	            	Section section = BD.getSectionDatos(seleccion);
	            	int id_section = section.getId();
	            	Dish dishSel = BD.getDishesByID(id_section, 0, id_menu, seleccion);
	            	BD.close();
	            	
	                // passing array index
	            	int id_dish=dishSel.getId();
	            	String sid = dishSel.getSid();
	            	String name = dishSel.getName();
	            	id_section = dishSel.getIdSection();
	            	
	            	
	                i.putExtra("id_dish", id_dish);
	                i.putExtra("id_section", id_section);
	                i.putExtra("id_subsection", 0);
	                i.putExtra("id_menu", id_menu);
	                i.putExtra("name", name);
	                i.putExtra("sid", sid);
	                i.putExtra("id", position);
	                
	                startActivity(i);
	                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
                    //Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                }
        });




	
	// Draw ActionBar
	ActionBar actionbar = (ActionBar) findViewById(R.id.actionBarMenu);
	String nombre= Carta.menuSel.getName();
	String s = "";
	if (nombre.length() > 15)
	{
		s = nombre.substring(0,12);
		s= s + "...";
	}
	else 
		s=nombre;
	actionbar.setTitle(s);
	
	 

	
		// Boton volver
		Button btnVolver = (Button) this.findViewById(R.id.btnVolverMenu);
		btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
		
            	finish();
            	//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
            }
		
		});  

		
		
		 //Pedido
		ImageButton btnPedido = (ImageButton) this.findViewById(R.id.btnPedidoMenu);
		btnPedido.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
		
            	finish();
            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
            	Carta.parentA.finish();
            	//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
            }
		
		}); 
		
		



	}

	
	
	
	 public void saveAnswers() {
		   
		 	
		
 		order = new Order(0, id_table, sid_table, sid, category_sid, price, note, 1, name, cantidad, ordermods);
 		 
 		LinearLayout discountsLay = (LinearLayout) findViewById(R.id.LayoutDisc);   
		loopQuestions2(discountsLay);
 		 //return order;
 		 
	    }
	 
    


	 public void loopQuestions2(ViewGroup parent) {
	 	
	
  	 
  	 Discountsel discountsel_item =  null;
  	 Discount discounts = null;
  	 
  	 
  	 	int id;
   	
       if (parent.getChildCount() > 0)
       {
   	
    	   for(int i = 0; i < parent.getChildCount(); i++) {
           View child = parent.getChildAt(i);
           
           if(child instanceof CheckBox) {
               //Support for Checkboxes
               CheckBox cb = (CheckBox)child;
               int answer = cb.isChecked() ? 1 : 0;
               if (answer == 1){
               	id = cb.getId();
               	String nombre = cb.getText().toString();
               	String value = cb.getText().toString();
               	
               
               		BD.open();
	                discounts = BD.getDiscountsByName(nombre);
	                BD.close();
	                String sidD = discounts.getSid();
	                            
	               
	                discountsel_item = new Discountsel(sidD, id_table, 0, "0","0","0", sid);
	                discountsels.add(discountsel_item);
	                
               }
             
           }   
   
    	   }
       }
	 }
}
	

	