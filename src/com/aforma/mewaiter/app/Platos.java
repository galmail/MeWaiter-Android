package com.aforma.mewaiter.app;

import java.util.ArrayList;

import com.aforma.mewaiter.utils.ActionBar;
import com.aforma.mewaiter.utils.DBHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
* 
* Es la Activity que muestra los datos de la Carta en un ListView y permite navegar entre secciones, subsecciones, menus y platos.
*
* 
*/
public class Platos extends Activity {
	public static Activity parentC=null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		parentC = (Activity) Platos.this;
		
		setContentView(R.layout.carta);
		// Draw ActionBar
		ActionBar actionbar = (ActionBar) findViewById(R.id.actionBarCarta);
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
		
	
		final ListView listview2;
		Resources res = getResources();
		Intent i = getIntent();
		Bundle b = i.getExtras();
        Categorias[] categorias = new Categorias[1];
        
      
	 	final int id_subsection = (Integer) b.get("id_subsection");
	 	final int id_menu = (Integer) b.get("id_menu");
		final int id_section=(Integer) b.get("id_section");
	 	
	 		final DBHelper BD = new DBHelper(getApplicationContext());	 		
	 		BD.open();
	 		ArrayList<Dish> dishes = BD.getDishesDatos(id_section, id_subsection, id_menu);
	 		BD.close();
	 		categorias = new Categorias[dishes.size()];	
	 		for(int j=0; j< dishes.size() ;j++)
			{
		 		int id = dishes.get(j).getId();
		 		
		 		String name =  dishes.get(j).getName();
		 		Categorias cat = new Categorias (id,name,res.getDrawable(R.drawable.recomendaciones));
		 		categorias[j] =  cat;
			
			}
	 		
	 		
	
			listview2 = (ListView) findViewById(R.id.menus);
			
			ArrayAdapter<Categorias> adapter = new ArrayAdapter<Categorias>(this,android.R.layout.simple_list_item_1, categorias);
			listview2.setAdapter(adapter);
			 
			listview2.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position,
	                    long id) {
	                
	            	final String  seleccion = listview2.getItemAtPosition(position).toString();
	            	BD.open();
	            	Dish dishSel = BD.getDishesByID(id_section, id_subsection, id_menu, seleccion);
	            	BD.close();
	            	Intent i = new Intent(getApplicationContext(), DetallePlato.class);
	                // passing array index
	            	int id_dish=dishSel.getId();
	            	String sid=dishSel.getSid();
	            	String name = dishSel.getName();
	            	String price = dishSel.getPrice();
	            	i.putExtra("sid", sid);
	                i.putExtra("id_dish", id_dish);
	                i.putExtra("id_section", id_section);
	                i.putExtra("id_subsection", id_subsection);
	                i.putExtra("id_menu", id_menu);
	                i.putExtra("name", name);
	                i.putExtra("price", price);
	            	
	            	
	                startActivity(i);
	                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	               
	                
	            }
					
			}); 
			 
			// Boton volver
			Button btnVolver = (Button) this.findViewById(R.id.btnVolverCarta);
			btnVolver.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
			
	            	finish();
	            	
	            	overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	            	//overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            }
			
			}); 
			
			 //Pedido
			ImageButton btnPedido = (ImageButton) this.findViewById(R.id.btnPedidoCarta);
			btnPedido.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
			
	            	//Finalizo Aperitivos
	            	finish();
	            	         	        	
	            	Secciones.parentB.finish();
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            	Carta.parentA.finish();
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            	Platos.parentC.finish();
	            	//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            }
			
			}); 

	}

}
