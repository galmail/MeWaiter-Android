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
 * Esta clase muestra la activity de la carta, valida para menu, seccion y subsección
 * Desde esta Activity, se habren el resto de los tipos de carta. 
 */
public class Carta extends Activity{
	
	//public Context mContexto = null;
	public static Activity parentA;
	
	public static Menu menuSel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		
		setContentView(R.layout.carta);
		parentA = (Activity) Carta.this;
	   
		
		// Draw ActionBar
		ActionBar actionbar = (ActionBar) findViewById(R.id.actionBarCarta);
		actionbar.setTitle("Menus");		
				
	
		//GridView gridView;
		Resources res = getResources();
		final ListView listview;
		
		String mesa = Main.m_Text; 
	 	
	 	final DBHelper BD=new DBHelper(getApplicationContext());
		
	 	BD.open();
		ArrayList<Menu> menulist= BD.getMenus();		
		BD.close();
		Categorias[] menu = new Categorias[menulist.size()];	
		for(int i=0; i< menulist.size() ;i++)
		{
	 		int id = menulist.get(i).getId();
	 		String name =  menulist.get(i).getName();
	 		Categorias cat = new Categorias (id,name,res.getDrawable(R.drawable.recomendaciones));
	 		menu[i] =  cat;
		
		}
		 
			listview = (ListView) findViewById(R.id.menus);
			
			ArrayAdapter<Categorias> adapter = new ArrayAdapter<Categorias>(this,android.R.layout.simple_list_item_1, menu);
			listview.setAdapter(adapter);
			 
			listview.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position,
	                    long id) {
	              
	                BD.open();
	                
	                String name=listview.getItemAtPosition(position).toString();
	                menuSel = BD.getMenuByname(name);
	                String id_menu=String.valueOf(menuSel.getId());
	              
	                BD.close();
	                String type = menuSel.getType();
	                
	                if ((type.toLowerCase().compareTo("wines") == 0) || (type.toLowerCase().compareTo("beverages") == 0))
	                {
	                	Intent i = new Intent(getApplicationContext(), Secciones.class);
	                	
	                	i.putExtra("type", "wines");
		                i.putExtra("id_menu", id_menu);
		                startActivity(i);
		                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	                
	                }
	                else
	                {
	                	
	                	if ( type.toLowerCase().compareTo("daily") == 0 )
	                	{
	                		Intent i2 = new Intent(getApplicationContext(), MenuDia.class);	                		
	                		i2.putExtra("id_menu", id_menu);
	 		                startActivity(i2);
			               
			                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	 		               overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	                	}
	                	else // El resto
	                	{
	                		Intent i3 = new Intent(getApplicationContext(), Secciones.class);
	                		i3.putExtra("type", "no");
			                i3.putExtra("id_menu", id_menu);
			                startActivity(i3);
			                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			                overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	                	}
	                }
	            }
			}); 
			 
			 //Volver
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
			
	            	finish();	
	            	//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            	
	            }
			
			});  

	}
	
	
	
	public void onResume(Bundle savedInstanceState) {
		//super.onResume();
		
	super.onCreate(savedInstanceState);
		setContentView(R.layout.carta);
	
		//GridView gridView;
		Resources res = getResources();
		ListView listview;
		
		
	  
	 	Categorias[] menu = null;		
	 
	 	
	 	DBHelper BD=new DBHelper(getApplicationContext());
		
	 	BD.open();
		ArrayList<Menu> menulist= BD.getMenus();
		BD.close();
		
		for(int i=0; i> menulist.size() ;i++)
		{
	 		int id = menulist.get(i).getId();
	 		String name =  menulist.get(i).getName();
	 		
			menu[i] =  new Categorias(id,name,res.getDrawable(R.drawable.recomendaciones));
		}
		 
		 
			listview = (ListView) findViewById(R.id.menus);
			
			ArrayAdapter<Categorias> adapter = new ArrayAdapter<Categorias>(this,android.R.layout.simple_list_item_1, menu);
			listview.setAdapter(adapter);
}

}
	


