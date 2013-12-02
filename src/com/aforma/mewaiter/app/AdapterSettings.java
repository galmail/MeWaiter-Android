package com.aforma.mewaiter.app;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.aforma.mewaiter.app.R;
import com.aforma.mewaiter.app.Settings;


/**
 * 
 * Esta clase maneja la Listview de lineas del setting hecha a medida, 
 * muestra las opciones de Pedido, Mi Cuenta, Carta, Acerca de la App, Sugerencias y Salir
 * con un icono asociado
 */
public class AdapterSettings extends BaseAdapter {
	  protected Activity activity;
	  protected ArrayList<Settings> items;
	         
	  public AdapterSettings(Activity activity, ArrayList<Settings> items) {
	    this.activity = activity;
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
	  public long getItemId(int position) {
	    return items.get(position).getId();
	  }
	   
	  	 
	  @Override
	  public View getView(int position, View contentView, ViewGroup parent) {
	    View vi=contentView;
	         
	    if(contentView == null) {
	      LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      vi = inflater.inflate(R.layout.elemento_lista, null);
	    }
	             
	    Settings item = items.get(position);
	         
	    ImageView image = (ImageView) vi.findViewById(R.id.icono);
	    int imageResource = activity.getResources().getIdentifier(item.icono, null, activity.getPackageName());
	    image.setImageDrawable(activity.getResources().getDrawable(imageResource));
	    
	    //image.setImageResource(item.icono);   
	    TextView descripcion = (TextView) vi.findViewById(R.id.subtitulo);
	    descripcion.setText(item.descripcion);     
	    	 
	    return vi;
	  }
	}