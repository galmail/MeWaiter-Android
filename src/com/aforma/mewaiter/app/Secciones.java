package com.aforma.mewaiter.app;

import java.util.ArrayList;

import com.aforma.mewaiter.app.R;
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
* La Activity Secciones muestra las secciones de un menu en una ListView y permite seleccionar una para ver sus subsecciones o platos.
*
* 
*/
public class Secciones extends Activity{
	public static Activity parentB;
	public static Section sectionSel;
	public String type=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		parentB = (Activity) Secciones.this;
		setContentView(R.layout.carta);
		
		// Draw ActionBar
		ActionBar actionbar = (ActionBar) findViewById(R.id.actionBarCarta);
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
	
		final ListView listview2;
		Resources res = getResources();
		Intent i = getIntent();
		Bundle b = i.getExtras();
		String id_menu = (String)b.get("id_menu");
		type = (String)b.get("type");
		
    
	 
	 	final DBHelper BD=new DBHelper(getApplicationContext());
		
	 	BD.open();
		ArrayList<Section> sectionlist= BD.getSectionByID(id_menu);		
		BD.close();
		Categorias[] section = new Categorias[sectionlist.size()];	
		for(int j=0; j< sectionlist.size() ;j++)
		{
	 		int id = sectionlist.get(j).getId();
	 		String name =  sectionlist.get(j).getName();
	 		Categorias cat = new Categorias (id,name,res.getDrawable(R.drawable.recomendaciones));
	 		section[j] =  cat;
		
		}
		 
		 
			listview2 = (ListView) findViewById(R.id.menus);
			
			ArrayAdapter<Categorias> adapter = new ArrayAdapter<Categorias>(this,android.R.layout.simple_list_item_1, section);
			listview2.setAdapter(adapter);
			 
			listview2.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position,
	                    long id) {
	             
	                BD.open();
	                
	                String name=listview2.getItemAtPosition(position).toString();
	                sectionSel = BD.getSectionDatos(name);
	                BD.close();
	               
	                int id_section = sectionSel.getId();
	                int id_menu = sectionSel.getIdMenu();
	                BD.open();
	                ArrayList<SubSection> Subsection = BD.getSubSectionDatos(id_section, id_menu);
	                
	                BD.close();
	                
	                if (Subsection.size() == 0)
	                {
	                	if (type.contains("no"))
	                	{
		                	Intent i = new Intent(getApplicationContext(), Platos.class);
			                i.putExtra("id_section", id_section);
			                i.putExtra("id_menu", id_menu);
			                i.putExtra("id_subsection", 0);
			                startActivity(i);
			                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	                	}
	                	else
	                	{
	                		Intent i = new Intent(getApplicationContext(), CartaVinos.class);
			                i.putExtra("id_section", id_section);
			                i.putExtra("id_menu", id_menu);
			                i.putExtra("id_subsection", 0);
			                startActivity(i);
			                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	                	}
		                
	                }else
	                {
	                	Intent i = new Intent(getApplicationContext(), Subsecciones.class);
		                i.putExtra("id_section", id_section);
		                i.putExtra("id_menu", id_menu);
		                i.putExtra("type", type);
		               
		                startActivity(i);
		                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	                }
	                
	            }
					
			}); 
			 
		
			// Boton volver
			Button btnVolver = (Button) this.findViewById(R.id.btnVolverCarta);
			btnVolver.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	
	            	//setResult(0);
	            	finish();
	            	//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);	
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            	
	            }
			
			});  
			 //Pedido
			ImageButton btnPedido = (ImageButton) this.findViewById(R.id.btnPedidoCarta);
			btnPedido.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
			
	            	//setResult(0);
	            	finish();
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            	Secciones.parentB.finish();
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            	Carta.parentA.finish();
	            	//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            	
	            	
	            }
			
			}); 
			 

	}
}
	


