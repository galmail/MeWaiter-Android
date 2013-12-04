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
* La Activity Subsecciones muestra las secciones de un menu en una ListView y permite seleccionar una para ver sus subsecciones o platos.
*
* 
*/
public class Subsecciones extends Activity {
	public static Activity parentC=null;
	public SubSection subsectionSel = null;
	public String type;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		parentC = (Activity) Subsecciones.this;
		
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
        
      	  
	 	final int id_section=(Integer) b.get("id_section");
	 	final int id_menu=(Integer) b.get("id_menu");
	 	type=(String) b.get("type");
	 	
	 		DBHelper BD = new DBHelper(getApplicationContext());	 		
	 		BD.open();
	 			ArrayList<SubSection> subsections = BD.getSubSectionDatos(id_section, id_menu);
	 		BD.close();
	 		categorias = new Categorias[subsections.size()];	
			for(int j=0; j< subsections.size() ;j++)
			{
		 		int id = subsections.get(j).getId();
		 		String name =  subsections.get(j).getName();
		 	
		 		Categorias cat = new Categorias (id,name,res.getDrawable(R.drawable.recomendaciones));
		 		categorias[j] =  cat;
			
			}
	 	
			if (subsections.size() == 0)
			{
				if (type.contains("no"))
            	{

	            	Intent i1 = new Intent(getApplicationContext(), Platos.class);
	                // passing array index
	                i1.putExtra("id_section", id_section);
	                i1.putExtra("id_subsection", 0);
	                i1.putExtra("id_menu",id_menu);
	                
	                startActivity(i1);
	                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
            	}
                else
                {
                	Intent i1 = new Intent(getApplicationContext(), CartaVinos.class);
	                // passing array index
	                i1.putExtra("id_section", id_section);
	                i1.putExtra("id_subsection", 0);
	                i1.putExtra("id_menu",id_menu);
	                startActivity(i1);
	                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
                }
				
			}else
			{
	 
			listview2 = (ListView) findViewById(R.id.menus);
			
			ArrayAdapter<Categorias> adapter = new ArrayAdapter<Categorias>(this,android.R.layout.simple_list_item_1, categorias);
			listview2.setAdapter(adapter);
			 
			listview2.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position,
	                    long id) {
	            	
	            	DBHelper BD=new DBHelper(getApplicationContext());
	                BD.open();
	                
	                String name=listview2.getItemAtPosition(position).toString();
	                subsectionSel = BD.getSubSectionDatosByName(name);
	               
	                int id_subsection = subsectionSel.getId();
	                BD.close();
	                
	                if (type.contains("no"))
	            	{
		            	Intent i = new Intent(getApplicationContext(), Platos.class);
		                // passing array index
		                i.putExtra("id_section", id_section);
		                i.putExtra("id_subsection", id_subsection);
		                i.putExtra("id_menu",id_menu);
		                startActivity(i);
		                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            	}
		            else
		            {
		            	Intent i = new Intent(getApplicationContext(), CartaVinos.class);
		                // passing array index
		                i.putExtra("id_section", id_section);
		                i.putExtra("id_subsection", id_subsection);
		                i.putExtra("id_menu",id_menu);
		                startActivity(i);
		                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		            }
	                
	            }
					
			}); 
			}
			 
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
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);         	        	
	            	Secciones.parentB.finish();
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            	Carta.parentA.finish();
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            	Subsecciones.parentC.finish();
	            	//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            }
			
			}); 

	}

}
