package com.aforma.mewaiter.app;

import java.util.ArrayList;
import java.util.HashMap;
import com.aforma.mewaiter.utils.ActionBar;
import com.aforma.mewaiter.utils.DBHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


/**
*
* Esta clase muestra la activity de la carta de vinos (type_menu=wines o beverages)
* Desde esta Activity, se habren el resto de los tipos de carta. 
*/

public class CartaVinos extends Activity{
	
   // List view
    private ListView lv;
     
    // Listview Adapter
    ArrayAdapter<String> adapter;
     
    // Search EditText
    EditText inputSearch;
     
     
    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;
    String products1[] = new String[1];
   
	
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.carta_vinos);
	
	// Draw ActionBar
	ActionBar actionbar = (ActionBar) findViewById(R.id.actionBarVinos);
	String nombre= Secciones.sectionSel.getName();
	String s = "";
	if (nombre.length() > 15)
	{
		s = nombre.substring(0,12);
		s= s + "...";
	}
	else 
		s=nombre;
		actionbar.setTitle(s);	
	
			Intent i = getIntent();
			Bundle b = i.getExtras();
			
			final int id_menu =(Integer)b.get("id_menu");
			final int id_section = (Integer)b.get("id_section");
			final int id_subsection = (Integer)b.get("id_subsection");
  
			final DBHelper BD=new DBHelper(getApplicationContext());

		 			 		
		 		BD.open();
		 		ArrayList<Dish> dishes = BD.getDishesDatos(id_section, id_subsection, id_menu);
		 		BD.close();
		 		products1 = new String[dishes.size()];	
				for(int j=0; j< dishes.size() ;j++)
				{
			 		int id = dishes.get(j).getId();
			 		String name = dishes.get(j).getName();
			 		products1[j] =  name;
				
				}
		 		
	        lv = (ListView) findViewById(R.id.list_view);
	        inputSearch = (EditText) findViewById(R.id.inputSearch);
		        // Adding items to listview Sections
		        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, products1);
		        lv.setAdapter(adapter); 
	       inputSearch.addTextChangedListener(new TextWatcher() {
	            
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	                CartaVinos.this.adapter.getFilter().filter(cs);   
	            }
	             
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                
	                 
	            }
	             
	            @Override
	            public void afterTextChanged(Editable arg0) {
	                                      
	            }
	        });
	    
	       lv.setOnItemClickListener( new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
					                
	            	final String  seleccion = lv.getItemAtPosition(position).toString();
	            	BD.open();
	            	Dish dishSel = BD.getDishesByID(id_section, id_subsection, id_menu, seleccion);
	            	BD.close();
	            	Intent i = new Intent(getApplicationContext(), DetallePlato.class);
	                // passing array index
	            	int id_dish=dishSel.getId();
	            	String name = dishSel.getName();
	            	String sid = dishSel.getSid();
	            	String price = dishSel.getPrice();
	                i.putExtra("id_dish", id_dish);
	                i.putExtra("id_section", id_section);
	                i.putExtra("id_subsection", id_subsection);
	                i.putExtra("id_menu", id_menu);
	                i.putExtra("name", name);
	                i.putExtra("sid", sid);
	                i.putExtra("price", price);
	                
	                startActivity(i);
	                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
			}
	    	  
	    	   
	       });
	

			
	
	
				// Boton volver
				Button btnVolver = (Button) this.findViewById(R.id.btnVolverVino);
				btnVolver.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View v) {
				
		            	finish();
		            	overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		            	
		            	//overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		            }
				
				});  
				 //Pedido
				ImageButton btnPedido = (ImageButton) this.findViewById(R.id.btnPedidoVino);
				btnPedido.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View v) {
				
		            	finish();
		            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		            	Carta.parentA.finish();
		            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		            	Secciones.parentB.finish();
		            	//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		            	
		            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		            }
				
				}); 
				 
	}
}


