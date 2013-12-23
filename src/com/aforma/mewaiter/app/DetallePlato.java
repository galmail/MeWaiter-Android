package com.aforma.mewaiter.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.aforma.mewaiter.utils.ActionBar;
import com.aforma.mewaiter.utils.DBHelper;

/**
* 
* Esta activity muestra la pantalla del detalle del plato cuando va ha ser añadido al pedido.
* Permite la seleccion de las características del mismo antes de ser añadido al pedido.
*
*/

public class DetallePlato extends Activity {
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
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		parentD=(Activity) DetallePlato.this;
		
		
		setContentView(R.layout.detalle_plato);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		
		
		
		sid = (String) b.get("sid");
		id_dish = (Integer)b.get("id_dish");
		final int id_menu =(Integer)b.get("id_menu");
		id_section = (Integer)b.get("id_section");
		final int id_subsection = (Integer)b.get("id_subsection");
		final String idr = null;
		name = (String)b.get("name");
		price = (String)b.getString("price");
		
		BD=new DBHelper(getApplicationContext());
		
		if ( Main.m_Text != "")
		{
			BD.open();
			String[] separated = (Main.m_Text).split(" Mesa ");
			String zona = separated[0]; // Contiene el número de mesa
			String mesa = separated[1]; // Contiene el número de mesa
			Mesa mesas = BD.getTableByZoneNum(zona, mesa);
			
			List<Restaurante> rest = BD.getRestaurants();
			
			sid_table = mesas.getSidTable();
			//mesas.getSidTable();
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
		
		
		
		final TextView txtCantidad = (TextView) findViewById(R.id.txtCantidad);
		final EditText editTxtNote = (EditText) findViewById(R.id.editText1);
		editTxtNote.setVisibility(View.INVISIBLE);
		final LinearLayout ll = (LinearLayout) findViewById(R.id.LayoutMod);
		final LinearLayout ll2 = (LinearLayout) findViewById(R.id.LayoutDisc);
		
		DetalleContext = this;
		//Sumar y Restar
		 
		
		// Nos quedamos con el sid del plato para la busqueda de los dishmods
		String[] cadena = sid.split("\\+");
		String sidD=cadena[0];
		

		BD.open();
		
		final ArrayList<DishMod> dishesmod = BD.getDishesMods(sidD);
		//ArrayList<DishMod> dishesmod2 = BD.getAllDishesMods();
		if (id_subsection != 0)
		{
			String idSubSection = Integer.toString(id_subsection);
			SubSection subsection = BD.getSubSectionByIdSubSection(idSubSection);
			category_sid = subsection.getSid();
		}
		else
		{
			String idSection = Integer.toString(id_section);
			Section section = BD.getSectionByIdSection(idSection);
			category_sid = section.getSid();
			if (category_sid == "-1")
			{
				Menu menu = BD.getMenu(id_menu);
				category_sid =  menu.getSid();
			}
		}
		
		final ArrayList<Discount> discounts = BD.getDiscounts();
		
		BD.close();
		
	
			 		
			 		//String[] products1 = new String[dishesmod.size()];	
					for(int j=0; j< dishesmod.size() ;j++)
					{
				 		int id = dishesmod.get(j).getId();
				 		String sidDish = dishesmod.get(j).getSid();
				 		int idMLS = dishesmod.get(j).getIdMLS();
				 		//int idModifier = dishesmod.get(j).getIdModifier();
				 		BD.open();
				 		listmod = BD.getListMod(idMLS);
				 		ListModSet listmodsets = BD.getListModSet(idMLS);
				 		BD.close();
				 		
				 		sid_mls = listmodsets.getSid();
				 		
				 		for (int i=0; i< listmod.size(); i++)
				 		{
					 		String titulo = listmod.get(i).getName();
					 		String isMand = listmod.get(i).getMandatory();
					 		String isMulti = listmod.get(i).getMultioption();
					 		String selected = listmod.get(i).getSelected();
					 		sid_ml = listmod.get(i).getSid();
					 		int idList = listmod.get(i).getIdList();
					 		BD.open();
					 		mods = BD.getMods(idList);
					 		BD.close();
	
					 		TextView title = new TextView(this);				 		
				            title.setText(titulo);
				            title.setTextColor(getResources().getColor(android.R.color.black));
				            title.setTextSize(18);
				            title.setTypeface(null, Typeface.BOLD);
				          
				            
				            ll.addView(title);
					 		if ( isMulti.contains("false") )
				 			{
						 		final RadioButton[] rb = new RadioButton[mods.size()];
						 		RadioGroup rg = new RadioGroup(this); //create the RadioGroup
						 		rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL					 		
						 		rg.setId(idList);
						 		if ( isMand.contains("true"))
					 			{
					 				is_mandatory =  true;
					 				
					 				totalMand++;
					 			} 
						 		
						 		for (int z=0; z<mods.size();z++)
						 		{	
						 				String nombre = mods.get(z).getName();
						 				int idm = mods.get(z).getIdModifier();
						 				sid_modifier = mods.get(z).getSid();
						 				if (sid_modifier.contains(sid_ml))
						 				{
						 				if(!mods.get(z).getprice().isEmpty())
						 				{
						 					if (!mods.get(z).getprice().contains("null"))
						 					{
						 					Double total = Double.valueOf(price) + Double.valueOf(mods.get(z).getprice());
						 					price = String.valueOf(total);
						 					}
						 				}
						 				// creating new HashMap								 		
						 				if ( isMand.contains("true"))
							 			{
						 					mandatories.add(nombre);
							 			}
						                
						 				rb[z]  = new RadioButton(this);
						 				
						 			    rb[z].setText(nombre);
						 			    rb[z].setId(idm);
						 			    rb[z].setTag(sid_modifier);
						 			    // seleccionando mandotory por defecto
						 				if ( selected.contains(sid_modifier))
						 				{
						 					rb[z].setChecked(true);
						 				}
						 				
						 			   
						 			    
						 			    rg.addView(rb[z]); //the RadioButtons are added to the radioGroup instead of the layout
						 				}
						 		}
						 				ll.addView(rg);
				
					 			}
					 		else
					 		{
					 			if ( isMand.contains("true"))
					 			{
					 					is_mandatory =  true;
					 					
						 				totalMand++;
						 			
					 				for (int z=0; z<mods.size();z++)
							 		{
							 			String nombre = mods.get(z).getName();	
							 			int idm = mods.get(z).getIdModifier();
							 			String sid_modifier = mods.get(z).getSid();
							 			if (sid_modifier.contains(sid_ml))
						 				{
							 			if(!mods.get(z).getprice().isEmpty())
						 				{
							 				if (!mods.get(z).getprice().contains("null"))
						 					{
						 					Double total = Double.valueOf(price) + Double.valueOf(mods.get(z).getprice());
						 					price = String.valueOf(total);
						 					}
						 				}
								 		final CheckBox cb = new CheckBox(this);
								 		
								 		
								 		if ( isMand.contains("true"))
							 			{
						 					mandatories.add(nombre);
							 			}
								 		
								 		// seleccionando mandotory por defecto
						 				if ( selected.contains(sid_modifier))
						 				{
						 					cb.setChecked(true);
						 				}
						                cb.setText(nombre);
						                cb.setId(idm+z);
						                cb.setTag(sid_modifier);
						                ll.addView(cb);
						 				}
						 			}
					 			}else
					 			{
	
					 				for (int z=0; z<mods.size();z++)
							 		{
							 			String nombre = mods.get(z).getName();
							 			int idm = mods.get(z).getIdModifier();
							 			String sid_modifier = mods.get(z).getSid();
							 			if (sid_modifier.contains(sid_ml))
						 				{
							 			if(!mods.get(z).getprice().isEmpty())
						 				{
							 				if (!mods.get(z).getprice().contains("null"))
						 					{
						 					Double total = Double.valueOf(price) + Double.valueOf(mods.get(z).getprice());
						 					price = String.valueOf(total);
						 					}
						 				}
							 			ToggleButton tb = new ToggleButton(this);
							 			
							 			// seleccionando mandotory por defecto
						 				if ( selected.contains(sid_modifier))
						 				{
						 					tb.setChecked(true);
						 					tb.setText(nombre);
						 				}
						 				/*else
						 				{
						 					tb.setText("Sin " + nombre);
						 				}*/
							 			tb.setText(nombre);
								 		tb.setTextOn(nombre);
								 		tb.setTextOff(nombre);
								 		tb.setTag(sid_modifier);
								 		tb.setId(idm+z);
								 		ll.addView(tb);
						 				}
						 			}
					 			}
					 				
					 		}
				 		
				 		}			            
					 				
					}
			 		
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
						if (section_id == null)
						{
							section_id="";
						}
						if (dish_id == null)
						{
							dish_id="";
						}
						String idm = String.valueOf(id_menu);
						String name = discounts.get(d).getName();
						String sid_discount = discounts.get(d).getSid();
						
						String ids = String.valueOf(id_section);
						String idd = String.valueOf(id_dish);
						
						
						if ( menu_id.contains(idm) || section_id.contains(ids) || dish_id.contains(idd) )
						{
							title2.setVisibility(View.VISIBLE);
							final CheckBox cb = new CheckBox(this);
			                cb.setText(name);
			                cb.setId(d);
			                ll2.addView(cb);
			                dodiscount=true;
						}
						
					}
		
		// Draw ActionBar
		ActionBar actionbar = (ActionBar) findViewById(R.id.actionBarPlato);
		
		final String nombre=name;
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
			Button btnBack3 = (Button) this.findViewById(R.id.btnVolverDetalle);
			btnBack3.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
			
	            	finish();	
	            	overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	            	//overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            }
			
			});
			
			
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
			
			 //Agregar
			Button btnAdd2 = (Button) this.findViewById(R.id.btnAddPlato);
			btnAdd2.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	

	            	cantidad=Integer.valueOf((String) txtCantidad.getText());
	            	note = editTxtNote.getText().toString();
	              
	            	saveAnswers();
	            	boolean error =  false;
	            	if (is_mandatory)
	            	{
	            		if ( totalMand != mandaSels.size())
	            			error=true;
	            	}
	            	
	            	if (!error)
	            	{
	            	
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
	            	
	            	// Estado =  Al insertar siempre sin enviar = 1
	            	if ( (ordermods.size() != 0) && (id_order != 0) )
	            	{
	            		for ( int i=0; i<ordermods.size();i++)
	            		{
	            			BD.open();
	            			long result = BD.insertOrdersMods(id_order, ordermods.get(i).getSidmls(), ordermods.get(i).getSidml(), ordermods.get(i).getSid(), ordermods.get(i).getValue());
	            			BD.close();
	            		
		            		if (result > 0)
		            		{
		            		
		            			
		            								Toast.makeText(DetalleContext, "Plato Agregado al pedido", Toast.LENGTH_LONG).show();
		    				                        editTxtNote.setVisibility(View.INVISIBLE);
		    				                        finish();
		    				                        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		    				                        Main.agregado=true;
		    				  
		            		}
	            		
	            		}
	            		
	            		
	            	}else if ( id_order != 0)
	            	{
	            			
	            								Toast.makeText(DetalleContext, "Plato Agregado al pedido", Toast.LENGTH_LONG).show();
	    				                        editTxtNote.setVisibility(View.INVISIBLE);
	    				                        finish();
	    				                        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	    				               
	            	}else
	            	{
	            			final AlertDialog alertDialog = new AlertDialog.Builder(DetalleContext).create();
	    					
	    					alertDialog.setMessage("Plato no se ha podido agregar. ERROR.");
	    					alertDialog.setCancelable(false);
	    					alertDialog.setButton("Aceptar",
	    				                new DialogInterface.OnClickListener() {
	    				                    public void onClick(DialogInterface dialog, int id) {
	    				                    	editTxtNote.setVisibility(View.INVISIBLE);
	    				                    	dialog.cancel();
	    				                        finish();
	    				                        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	    				                        Main.agregado=false;
	    				                    }
	    				                });
	    					alertDialog.show();
	            	}
	            	
	            	}
	            	else
	            	{
	            		Toast.makeText(getApplicationContext(), "Debes Seleccionar los modificadores obligatorios", Toast.LENGTH_LONG).show();
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
	}

	/**
	 * Revisa los datos en todos los componentes dinamicos de payments y descuento, los valida y los guarda. 
	 */
	public void saveAnswers() {
	   
 	
	  	 LinearLayout modLay = (LinearLayout) findViewById(R.id.LayoutMod);   
 		 loopQuestions(modLay);
 		 if (order==null)
 				order = new Order(0, id_table, sid_table, sid, category_sid, price, note, 1, name, cantidad, ordermods);
 		 
 		LinearLayout discountsLay = (LinearLayout) findViewById(R.id.LayoutDisc);   
		loopQuestions2(discountsLay);
 		 //return order;
 		 
	    }
	
	/**
	  *  Revisa los datos seleccionadosde los payments
	  */
	 public void loopQuestions(ViewGroup parent) {
	   	 	
		 Ordermods ordermods_item = null;
	   	 Mod mod = null;
	   	 ListMod modlist = null;
	   	 ListModSet listmodset = null;
	   	 int idList= 0;
	   	 int idListSet= 0;
	     int id = 0;
	    
	        if (parent.getChildCount() > 0)
	        {
	    	
	    	for(int i = 0; i < parent.getChildCount(); i++) {
	            View child = parent.getChildAt(i);
	            if(child instanceof RadioGroup ) {
	                //Support for RadioGroups
	                RadioGroup radio = (RadioGroup)child;
	                RadioButton rb = (RadioButton) findViewById(radio.getCheckedRadioButtonId());
	                if ( rb != null )
	                {
		                String nombre = rb.getText().toString();
		                String value = rb.getText().toString();
		                id = rb.getId();
		                String sid = (String) rb.getTag();
		                if (mandatories.contains(nombre))
		                {
			                map = new HashMap<String,String>();
			                map.put(nombre, "true");
			                if (!mandaSels.contains(map))
			                	mandaSels.add(map);
		                }   
		                BD.open();
		                
		                mod = BD.getModsSid(sid);
		                idList = mod.getIdList();
		                sid_modifier = mod.getSid();    
		                String cadena[] = sid_modifier.split("\\+");
		                sid_ml = cadena[1]+"+"+cadena[2];
		                sid_mls =cadena[2];
		                
		                BD.close();
		                 
		                ordermods_item = new Ordermods(id,sid_mls, sid_ml, sid_modifier, value);
		                ordermods.add(ordermods_item);
	                }
	                order = new Order(id, id_table, sid_table, sid, category_sid, price, note, 1, name, cantidad, ordermods);
	                
	               
	            }
	            else if(child instanceof CheckBox) {
	                //Support for Checkboxes
	                CheckBox cb = (CheckBox)child;
	                int answer = cb.isChecked() ? 1 : 0;
	                if (answer == 1){
	                	
	                	id = cb.getId();
	                	String nombre = cb.getText().toString();
	                	String value = cb.getText().toString();
	                	String sidM = (String) cb.getTag();
	                	
	                	if (mandatories.contains(nombre))
		                {
			                map = new HashMap<String,String>();
			                map.put(nombre, "true");
			                if (!mandaSels.contains(map))
			                	mandaSels.add(map);
		                }   
			                
	                	
	                	BD.open();
	                	
		                mod = BD.getModsSid(sidM);
		                idList = mod.getIdList();
		                sid_modifier = mod.getSid();    
		                String cadena[] = sid_modifier.split("\\+");
		                sid_ml = cadena[1]+"+"+cadena[2];
		                sid_mls =cadena[2];
		                
		                BD.close();
	                	ordermods_item = new Ordermods(id, sid_mls, sid_ml, sid_modifier, value);
		                ordermods.add(ordermods_item);
		                order = new Order(id, id_table, sid_table, sid, category_sid, price, note, 1, name, cantidad, ordermods);
	              }
	              
	            }
	            else if(child instanceof ToggleButton) {
	                //Support for ToggleButton
	                ToggleButton tb = (ToggleButton)child;
	                
	                if (tb.isChecked())
	                {
	                	
	                	id = tb.getId();
	                	String nombre =tb.getText().toString();
	                	String value=tb.getTextOn().toString();
	                	String sidM = (String) tb.getTag();
	                	
	                	if (mandatories.contains(nombre))
		                {
			                map = new HashMap<String,String>();
			                map.put(nombre, "true");
			                if (!mandaSels.contains(map))
			                	mandaSels.add(map);
		                }   
	                	
	                	
	                	BD.open();
	                	mod = BD.getModsSid(sidM);
		                idList = mod.getIdList();
		                sid_modifier = mod.getSid();    
		                String cadena[] = sid_modifier.split("\\+");
		                sid_ml = cadena[1]+"+"+cadena[2];
		                sid_mls =cadena[2];
		                
		                BD.close();
	                	ordermods_item = new Ordermods(id,sid_mls, sid_ml, sid_modifier,value);
		                ordermods.add(ordermods_item);
		                order = new Order(id, id_table, sid_table, sid, category_sid, price, note, 1, name, cantidad, ordermods);
	                }
	                }
	            else {
	                //Support for other controls
	            	
	            }
	 
	            if(child instanceof ViewGroup) {
	                //Nested Q&A
	                ViewGroup group = (ViewGroup)child;
	                loopQuestions(group);
	            }
	        }
			//return order;
	    }
	    else
	    {
	    	
	    	order = new Order(id, id_table, sid_table, sid, category_sid, price, note, 1, name, cantidad, ordermods);
	    
	    }
	    
	    }    

	 /**
	  *  Revisa los datos seleccionadosde los descuentos
	  */
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