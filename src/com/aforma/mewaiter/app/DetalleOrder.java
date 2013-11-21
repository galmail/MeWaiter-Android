package com.aforma.mewaiter.app;

import java.util.ArrayList;
import java.util.HashMap;
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
 * Esta activity muestra la pantalla del detalle del plato ya a–adido al pedido.
 * Permite la modificaci—n del mismo antes de ser enviado al POS.
 *
 */
public class DetalleOrder extends Activity {
	public static Activity parentDP= null;
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
	public int id_menu;
	public int id_table=0;
	public Order order= null;
	public String name="";
	public String category_sid = null;
	public String sid_modifier = null;
	public String sid_mls = null;
	public String sid_ml =  null;
	public int id_order = 0;
	public  String idOrder = null;
	
	
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
	
 	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detalle_plato);
		Button btnaddplato = (Button) findViewById(R.id.btnAddPlato);
		btnaddplato.setVisibility(Button.INVISIBLE);
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		
		BD=new DBHelper(getApplicationContext());
		
		id_order = (Integer) b.get("id_order");
		idOrder = String.valueOf(id_order);
		BD.open();
			order = BD.getOrder(id_order); 
			sid_table = order.getSidTable();
			sid=order.getSid();
			name = order.getProductName();
			note = order.getNote();
			cantidad = order.getQuantity();
			id_table = order.getIdTable();
			price = order.getPrice();
			category_sid = order.getCategory();
		
			
			
			ordermods = BD.getOrdermodsByID(id_order);
			discountsels = BD.getDiscountSelByIdOrder(id_order);
		BD.close();
		if ( Main.m_Text != "")
		{
			String[] separated = (Main.m_Text).split(" ");
			String zona = separated[0]; // Contiene el nœmero de mesa
			String mesa = separated[2]; // Contiene el nœmero de mesa
			id_table = Integer.parseInt(mesa);
		}

		parentDP=(Activity) DetalleOrder.this;
		
		setContentView(R.layout.detalle_plato);
		DetalleContext = this;
		final int black = getResources().getColor(android.R.color.black);
		final TextView txtCantidad = (TextView) this.findViewById(R.id.txtCantidad);
		String quantity = String.valueOf(order.getQuantity());		
		txtCantidad.setText(quantity);
		txtCantidad.setClickable(false);
		txtCantidad.setEnabled(false);
		final EditText editTxtNote = (EditText) this.findViewById(R.id.editText1);
		note =  order.getNote();
		editTxtNote.setText(note);
		
		name = order.getProductName();
		
		final LinearLayout ll = (LinearLayout) findViewById(R.id.LayoutMod);
		final LinearLayout ll2 = (LinearLayout) findViewById(R.id.LayoutDisc);
		
		
		 
		sid = order.getSid(); // Sid del plato

		BD.open();
		ArrayList<DishMod> dishesmod = BD.getDishesMods(sid);
		BD.close();
			 		
			 	
					for(int j=0; j< dishesmod.size() ;j++)
					{
				 		int id = dishesmod.get(j).getId();
				 		String sidDish = dishesmod.get(j).getSid();
				 		int idMLS = dishesmod.get(j).getIdMLS();
				 		
				 		
				 		
				 		BD.open();
				 		ArrayList<ListMod> listmod = BD.getListMod(idMLS);
				 		ListModSet listmodsets = BD.getListModSet(idMLS);
				 		BD.close();
				 		
				 		
				 		
				 		
				 		for (int i=0; i< listmod.size(); i++)
				 		{
					 		String titulo = listmod.get(i).getName();
					 		String isMand = listmod.get(i).getMandatory();
					 		String isMulti = listmod.get(i).getMultioption();
					 		sid_ml = listmod.get(j).getSid();
					 		int idList = listmod.get(i).getIdList();
					 		BD.open();
					 		ArrayList<Mod> mods = BD.getMods(idList);
					 		BD.close();
	
					 		TextView title = new TextView(this);				 		
				            title.setText(titulo);
				            title.setTextColor(black);
				            title.setTextSize(18);
				            title.setTypeface(null, Typeface.BOLD);
				     
				            ll.addView(title);
					 		if ( isMulti.contains("false") )
				 			{
						 		final RadioButton[] rb = new RadioButton[mods.size()];
						 		RadioGroup rg = new RadioGroup(this); //create the RadioGroup
						 		rg.setClickable(true);
						 		rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL					 		
						 		rg.setId(idList);
						 		for (int z=0; z<mods.size();z++)
						 		{	
						 				String nombre = mods.get(z).getName();
						 				int idm = mods.get(z).getIdModifier();
						 				
						 			    rb[z]  = new RadioButton(this);
						 			    rb[z].setText(nombre);
						 			    rb[z].setTextColor(black);
						 			   
						 			    rg.addView(rb[z]); //the RadioButtons are added to the radioGroup instead of the layout
						 		}
						 				ll.addView(rg);
				
					 			}
					 		else
					 		{
					 			if ( isMand.contains("true"))
					 			{
					 				for (int z=0; z<mods.size();z++)
							 		{
							 			String nombre = mods.get(z).getName();	
							 			int idm = mods.get(z).getIdModifier();
							 			
								 		final CheckBox cb = new CheckBox(this);
						                cb.setText(nombre);
						                cb.setId(idm+z);
						                
						                cb.setTextColor(black);
							 			ll.addView(cb);
							 		}
					 			}else
					 			{
	
					 				for (int z=0; z<mods.size();z++)
							 		{
							 			String nombre = mods.get(z).getName();
							 			int idm = mods.get(z).getIdModifier();
							 			
							 			ToggleButton tb = new ToggleButton(this);
								 		tb.setText(nombre);
								 		tb.setId(idm+z);
								 		
								 		tb.setTextColor(black);
							 			ll.addView(tb);
							 		}
					 			}
					 				
					 		}
				 		
				 		}			            
					 				
					}
			 		
		for (int m=0; m<ordermods.size(); m++)
		{
			
			String Value = ordermods.get(m).getValue();
			showAnswer(Value);
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
	            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	            }
			
			});
			
		
			//btnaddplato.setVisibility(Button.INVISIBLE);
			BD.open();
			final ArrayList<Discount> discounts = BD.getDiscounts();
			Dish dish = BD.getDishByName(name);
			id_menu = dish.getIdMenu();
			id_dish = dish.getId();
			id_section = dish.getIdSection();
			
			BD.close();
			
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
				//String idm = String.valueOf(id_menu);
				String name = discounts.get(d).getName();
				String sid_discount = discounts.get(d).getSid();
				
				String ids = String.valueOf(id_section);
				String idd = String.valueOf(id_dish);
				String idm = String.valueOf(id_menu);
				
				//if (menu_id.contains(idm) || section_id.contains(ids) || dish_id.contains(idd) || !(restaurant_id.isEmpty()))
				if ( menu_id.contains(idm) || section_id.contains(ids) || dish_id.contains(idd) )
				{
					
					
						String titulo2 =  "Descuentos";
				 		TextView title2 = new TextView(this);				 		
			            title2.setText(titulo2);
			            title2.setTextColor(black);
			            title2.setTextSize(18);
			            title2.setTypeface(null, Typeface.BOLD);
						ll2.addView(title2);
						title2.setVisibility(View.VISIBLE);
						final CheckBox cb = new CheckBox(this);
		                cb.setText(name);
		                cb.setId(d);
		                cb.setTextColor(black);
		                cb.setButtonDrawable(R.drawable.btn_check_holo_light);
		               
		                for (int i=0;i< discountsels.size();i++)
						{
							String sid_discountsel = discountsels.get(i).getSid();				 			
					 	    
				 			if (sid_discount.contains(sid_discountsel))
				 				cb.setChecked(true);
			               
							
						}
		                ll2.addView(cb);
		               
					}
				
				
				}
				
			
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
			
			 //Modificar Plato
			Button btnAdd2 = (Button) this.findViewById(R.id.btnAddPlato);
			btnAdd2.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	
	            	cantidad = Integer.valueOf((String) txtCantidad.getText());
	            	note = editTxtNote.getText().toString();
	            	
	            	ordermods.clear();
	            	
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
	            	
	            	BD.removeOrderModsById(id_order);
	            	BD.removeDiscountSelByID(id_order);
	            	
	            	int id_update = BD.updateOrders(id_order, order.getIdTable(), order.getSidTable(), order.getSid(), order.getCategory(), order.getPrice(), order.getNote(), 1, order.getProductName(), order.getQuantity());
	            	
	            	for (int i=0; i<discountsels.size();i++)
	            	{
	            		BD.insertDiscountSel(discountsels.get(i).getSid(), discountsels.get(i).getIdTable(), id_order, "0", "0", "0", discountsels.get(i).getDish());
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
		            		
		            			final AlertDialog alertDialog = new AlertDialog.Builder(DetalleContext).create();
		    					
		    					alertDialog.setMessage("Plato Agregado al pedido");
		    					alertDialog.setCancelable(false);
		    					alertDialog.setButton("Aceptar",
		    				                new DialogInterface.OnClickListener() {
		    				                    public void onClick(DialogInterface dialog, int id) {
		    				                        dialog.cancel();
		    				                        editTxtNote.setVisibility(View.INVISIBLE);
		    				                        finish();
		    				                        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		    				                        Main.agregado=true;
		    				                    }
		    				                });
		    					alertDialog.show();
		            		}
	            		
	            		}
	            		
	            		
	            	}else if ( id_order != 0)
	            	{
	            			final AlertDialog alertDialog = new AlertDialog.Builder(DetalleContext).create();
	    					
	    					alertDialog.setMessage("Plato Agregado al pedido");
	    					alertDialog.setCancelable(false);
	    					alertDialog.setButton("Aceptar",
	    				                new DialogInterface.OnClickListener() {
	    				                    public void onClick(DialogInterface dialog, int id) {
	    				                        dialog.cancel();
	    				                        editTxtNote.setVisibility(View.INVISIBLE);
	    				                        finish();
	    				                        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
	    				                        Main.agregado=true;
	    				                    }
	    				                });
	    					alertDialog.show();
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
			
	}


  public void showAnswer( String Value ) {
	   
	  	
	  	 LinearLayout root = (LinearLayout) findViewById(R.id.LayoutMod);   
	  	 int black = getResources().getColor(android.R.color.black);
	       if (root.getChildCount() > 0)
	        {
	    	
	    	for(int i = 0; i < root.getChildCount(); i++) {
	            View child = root.getChildAt(i);
	            if(child instanceof RadioGroup ) {
	                //Support for RadioGroups
	                RadioGroup radio = (RadioGroup)child;
	                RadioButton rb = null;
	                for (int t=0; t< radio.getChildCount();t++)
	                {
	                	rb = (RadioButton) radio.getChildAt(t);
	                	String nombre = rb.getText().toString();
	                	String value = rb.getText().toString();
	                	if ( value.contains(Value))
	                	{
	                		rb.setTextColor(black);
				 			
				 			rb.setChecked(true);
	                	}
	                }
	             }
	            else if(child instanceof CheckBox) {
	                //Support for Checkboxes
	                CheckBox cb = (CheckBox)child;
	                             	
	               	String nombre = cb.getText().toString();
	                String value = cb.getText().toString();
	                if ( value.contains(Value) )
                	{
                		
                		cb.setTextColor(black);
			 			
			 			cb.setChecked(true);
                	}	
	               
	            }
	            else if(child instanceof ToggleButton) {
	                //Support for ToggleButton
	                ToggleButton tb = (ToggleButton)child;
	                
	                String nombre =tb.getText().toString();
	                String value=tb.getTextOn().toString();
	                if (value.contains(Value))
	                {
	                	
	                	tb.setTextColor(black);
			 			
			 			tb.setChecked(true);
	                }	
	                }
	            else {
	                //Support for other controls
	            	
	            }
	    	}
	    
	    }    
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
		                if (mandatories.contains(nombre))
		                {
			                map = new HashMap<String,String>();
			                map.put(nombre, "true");
			                if (!mandaSels.contains(map))
			                	mandaSels.add(map);
		                }  
		               
		                BD.open();
		                mod = BD.getModsValue(value);
		                idList = mod.getIdList();
		                sid_modifier = mod.getSid();    
		                modlist = BD.getListModId(idList);
		                idListSet = modlist.getIdMLS();
		                sid_ml = modlist.getSid();
		                
		                listmodset = BD.getListModSet(idListSet);
		                sid_mls =listmodset.getSid();
		                
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
	                	
	                	
	                	if (mandatories.contains(nombre))
		                {
			                map = new HashMap<String,String>();
			                map.put(nombre, "true");
			                if (!mandaSels.contains(map))
			                	mandaSels.add(map);
		                }   
			                
	                	
	                	BD.open();
		                mod = BD.getModsValue(value);
		                idList = mod.getIdList();
		                sid_modifier = mod.getSid();    
		                modlist = BD.getListModId(idList);
		                idListSet = modlist.getIdMLS();
		                sid_ml = modlist.getSid();
		                
		                listmodset = BD.getListModSet(idListSet);
		                sid_mls =listmodset.getSid();
		                
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
	                	
	                	if (mandatories.contains(nombre))
		                {
			                map = new HashMap<String,String>();
			                map.put(nombre, "true");
			                if (!mandaSels.contains(map))
			                	mandaSels.add(map);
		                }   
	                	
	                	
	                	BD.open();
		                mod = BD.getModsValue(value);
		                idList = mod.getIdList();
		                sid_modifier = mod.getSid();    
		                modlist = BD.getListModId(idList);
		                idListSet = modlist.getIdMLS();
		                sid_ml = modlist.getSid();
		                
		                listmodset = BD.getListModSet(idListSet);
		                sid_mls =listmodset.getSid();
		                
		                BD.close();
	                	ordermods_item = new Ordermods(id,sid_mls, sid_ml, sid_modifier, value);
		                ordermods.add(ordermods_item);
		                order = new Order(id, id_table, sid_table, sid, category_sid, price, note, 1, name, cantidad, ordermods);
	                }
	                }
	            else {
	                //Support for other controls
	            	
	            }
	 
	            if(child instanceof ViewGroup) {
	                
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
    	
    	discountsels.clear();
	
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
