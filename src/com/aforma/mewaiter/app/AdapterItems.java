package com.aforma.mewaiter.app;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


/**
 * 
 * Esta clase maneja la Listview de lineas del pedido hecha a medida, 
 * que muestrar el número de platos en un icono con un circulo.
 */
public class AdapterItems  extends BaseAdapter {

	/**
	 * 
	 * Define que hacer al presionar click sobre un elemento en la lista. 
	 * En este caso muestra el detalle del plato.
	 *
	 */
	public class OnItemClickListener {

	}
	
	  protected Activity activity;
	  protected ArrayList<Listado> items;
	  
	  	/**
		 * 
		 * Genera la vista de la ListView. 
		 *
		 */	    
	  public AdapterItems(Activity activity, ArrayList<Listado> items) {
	    this.activity= activity;
	    this.items = items;
	    
	    
	  }
		@Override
	  public int getCount() {
	    return items.size();
	  }
	 
		
		@Override
	  public Object getItem(int position) {
	    return items.get(position);
	  }

		
		@Override
	  public View getView(int position, View contentView, ViewGroup parent) {
	    View vi=contentView;
	         
	    if(contentView == null) {
	      LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      vi = inflater.inflate(R.layout.lstpedido_item, null);
	    }
	             
	    final Listado item = items.get(position);
	         
	    Button image = (Button) vi.findViewById(R.id.btnNumero);
	    Button price = (Button) vi.findViewById(R.id.btnPrice);
	    TextView notas = (TextView) vi.findViewById(R.id.txtnotas);
	   
	    image.setText(Integer.toString(item.numero));
	    notas.setText(item.comentarios); 
	    price.setText(item.price + " €");
	    TextView descripcion = (TextView) vi.findViewById(R.id.item_list);
	    descripcion.setText(item.name);   
	    
	    descripcion.setOnClickListener(new AdapterView.OnClickListener() {

			@Override
	    	public void onClick(View v) {
	        
				
				Intent i = new Intent(activity, DetalleOrder.class);
               
            	int ident = item.id;
            	i.putExtra("id_order", ident);
                activity.startActivity(i);
                //activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);	
                activity.overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
			}

			});	
	    return vi;
	  }

	@Override
	public long getItemId(int position) {
		
		return 0;
	}
}
