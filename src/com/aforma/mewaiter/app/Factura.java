package com.aforma.mewaiter.app;



import java.util.ArrayList;
import org.json.JSONObject;
import com.aforma.mewaiter.app.R;
import com.aforma.mewaiter.utils.ActionBar;
import com.aforma.mewaiter.utils.DBHelper;
import com.aforma.mewaiter.utils.JSONCreate;
import com.aforma.mewaiter.utils.JSONSendPOST;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 
 * La activity Factura muestra la pantalla impresión/ cobrar factura, con los payments y descuentos asociados.
 * 
 * Envia la factura de una mesa concreta recogida del parámentro Main.m_Text que contiene el nombre de la mesa.
 * 
 */
public class Factura extends Activity{
	
	 private Context contextForDialog = null;
	String numero = "";
	String zona = "";
	String sid_table = "";
	String name = "";
	String nota = "";
	public static String total = "000.00";
	JSONObject jsonFactura = new JSONObject();
	ArrayList<Payments> mediospago= new ArrayList<Payments>();
	ArrayList <Discount> descuentos =  new ArrayList<Discount>();
	ArrayList <Payments> payments =  new ArrayList<Payments>();
  
		@Override
		  public void onCreate(Bundle savedInstances) {
		    super.onCreate(savedInstances);
		   this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.factura);
		    
		    contextForDialog = this;
		   
		 final EditText edammount =  (EditText)  findViewById(R.id.edammount); 
		
		 // Draw ActionBar
		   
		ActionBar actionbar = (ActionBar) this.findViewById(R.id.actionBar1);
		actionbar.setTitle("Factura");	
		
		// Boton volver
		Button btnVolver = (Button) findViewById(R.id.btnBack);
		btnVolver.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
		
            	finish();
            	//overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
            	overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
		
		});  
		final LinearLayout descLay = (LinearLayout) findViewById(R.id.descLay);
		// Boton volver
				Button btnImp = (Button) findViewById(R.id.btnImp);
				btnImp.setOnClickListener(new OnClickListener() {
		            public void onClick(View v) {
		            	JSONCreate jsoncreate =  new JSONCreate();
		            	
		            	ArrayList <Payments> paymetsVacio = new ArrayList <Payments>();
		            	
		            	descuentos.clear();
		            	for (int i=0;i<descLay.getChildCount();i++)
			     	       {
			     	    	   final int posicion2 = i;
			     	    	  
			     					final CheckBox cb = (CheckBox) descLay.getChildAt(posicion2);
			     					final String pago = (String) cb.getText();
			     					if (cb.isChecked())
			     					{
			     						Main.DB.open();
			     						Discount discount = Main.DB.getDiscountsByName(pago);
			     						Main.DB.close();
			     						
			     						descuentos.add(discount);
			     					}	
			     					
			     	       }
		            	
		            	
		        		jsonFactura = jsoncreate.createJSONFactura(paymetsVacio, sid_table, name, descuentos);
		        		if ( jsonFactura != null)
		        		{
		        			JSONSendPOST jsonsend = new JSONSendPOST();
		        			String[] cadena = Login.pos_ip_address.split(":");
		    				String ip="";
		    				int port = 8080;
		    				if (cadena.length > 1)
		    				{
		    					ip=cadena[1].toString().substring(2);
		    					port= Integer.valueOf(cadena[2].toString());
		    				}
		        			boolean OK = jsonsend.postData("http://"+ip+":"+port+ "/ticket?mwkey="+Login.mwkey+"&close_table=false", jsonFactura);
		        			if (OK)
		        			{
		        				edammount.setText(total);
		        				Toast.makeText(contextForDialog, "Se ha impreso la factura", Toast.LENGTH_LONG).show();
		        				
		        			}
		        			else
		        			{
		        				Toast.makeText(contextForDialog, "ERROR. No se ha podido imprimir la factura", Toast.LENGTH_LONG).show();
		        			}
		        		}
		            	//finish();	
		            }
				
				});  
		
				Button btnCobrar = (Button) findViewById(R.id.btnCobrar);
				btnCobrar.setOnClickListener(new OnClickListener() {
		            public void onClick(View v) {
		            	JSONCreate jsoncreate =  new JSONCreate();
		            	
		            	descuentos.clear();
		            	for (int i=0;i<descLay.getChildCount();i++)
			     	       {
			     	    	   final int posicion2 = i;
			     	    	  
			     					final CheckBox cb = (CheckBox) descLay.getChildAt(posicion2);
			     					final String pago = (String) cb.getText();
			     					if (cb.isChecked())
			     					{
			     						Main.DB.open();
			     						Discount discount = Main.DB.getDiscountsByName(pago);
			     						Main.DB.close();
			     						
			     						descuentos.add(discount);
			     					}	
			     					
			     	       }
		        		jsonFactura = jsoncreate.createJSONFactura(payments, sid_table, name, descuentos);
		        		if ( jsonFactura != null)
		        		{
		        			JSONSendPOST jsonsend = new JSONSendPOST();
		        			String[] cadena = Login.pos_ip_address.split(":");
		    				String ip="";
		    				int port = 8080;
		    				if (cadena.length > 1)
		    				{
		    					ip=cadena[1].toString().substring(2);
		    					port= Integer.valueOf(cadena[2].toString());
		    				}
		        			boolean OK = jsonsend.postData("http://"+ip+":"+port+"/ticket?mwkey="+Login.mwkey+"&close_table=true", jsonFactura);
		        			if (OK)
		        			{
		        				Toast.makeText(contextForDialog, "COBRAR: Factura generada", Toast.LENGTH_LONG).show();
		        			}
		        			else
		        			{
		        				Toast.makeText(contextForDialog, "COBRAR: ERROR. No se ha podido generar la factura", Toast.LENGTH_LONG).show();
		        			}
		        		}
		            	finish();	
		            	overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		            }
				
				});  
		
	
		TextView txtmesa = (TextView) this.findViewById(R.id.txttable);
	
		if (Main.m_Text != null && !Main.m_Text.isEmpty())
		{
			txtmesa.setText(Main.m_Text.toString());
			final String name = Main.m_Text.toString();
			String[] separated = (Main.m_Text).split(" Mesa ");
			String zona = separated[0]; // Contiene el número de mesa
			String mesa = separated[1]; // Contiene el número de mesa
		}else
		{
			Toast.makeText(this, "Debes seleccionar una mesa.", Toast.LENGTH_LONG).show();
			finish();
			overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
		}
		
		DBHelper DB =  new DBHelper(this);
		
		DB.open();
		
		Mesa mesa = DB.getTableByZoneNum(zona, numero);
		sid_table = mesa.getSidTable();
		name = mesa.getZoneNum();
		
		mediospago = DB.getPayments();
		DB.close();
		final LinearLayout pagoLay = (LinearLayout) findViewById(R.id.pagoLay);
		
		pagoLay.removeAllViews();
		if ( !Main.m_Text.isEmpty())
		{
				//payments = obtenerPayments();
			//final ArrayList<String> items4 = new ArrayList<String>();
			for(int i=0;i<mediospago.size();i++)
			{
				//items4.add (mediospago.get(i).getName() );
				CheckBox cb =  new CheckBox(this);
				cb.setId(i);
				cb.setText(mediospago.get(i).getName());
				cb.setTextColor(getResources().getColor(android.R.color.black));
				
				pagoLay.addView(cb);
				pagoLay.setVisibility(View.GONE);
				
			}
		    
			
			for (int i=0;i<descLay.getChildCount();i++)
  	       {
  	    	   final int posicion2 = i;
  	    	   descLay.getChildAt(i).setOnClickListener( new OnClickListener() {
  	    		@Override
  				public void onClick(View arg0) {
  					
  					final CheckBox cb = (CheckBox) descLay.getChildAt(posicion2);
  					final String pago = (String) cb.getText();
  					
  						Main.DB.open();
  						Discount discount = Main.DB.getDiscountsByName(pago);
  						Main.DB.close();
  						
  						descuentos.add(discount);
  						
  						
  				}
  				});
  	       }
       
       TextView txtpago = (TextView) findViewById(R.id.txtPago);
       txtpago.setOnClickListener (new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			if (pagoLay.isShown())
			{
				pagoLay.setVisibility(View.GONE);
			}else
			{
				pagoLay.setVisibility(View.VISIBLE);
			}
		}
    	  
    	  
       });
       
       for (int i=0;i<pagoLay.getChildCount();i++)
       {
    	   final int posicion = i;
    	   pagoLay.getChildAt(i).setOnClickListener( new OnClickListener() {
    		
    		   
    		   
			@Override
			public void onClick(View arg0) {
				
				final CheckBox cb = (CheckBox) pagoLay.getChildAt(posicion);
				final String forma = (String) cb.getText();
				
				
					String[] nombre = forma.split(" : ");
					if (nombre.length > 1)
					{
						cb.setText(nombre[0]);
						cb.setTextColor(getResources().getColor(android.R.color.black));
						cb.setChecked(false);
					
					
					String[] result = nombre[1].split(" ");
					
					Double amount = Double.parseDouble(result[0]);
					for(int j=0;j<mediospago.size();j++)
					{
						if (mediospago.get(j).getName().contains(nombre[0]))
						{
							Payments pay = new Payments (mediospago.get(j).getId(), mediospago.get(j).getSid(), mediospago.get(j).getName(), amount, mediospago.get(j).getKey());
							payments.remove(pay);
						}
					}
				}
				else
				{
				// custom dialog forma de pago
				final Dialog dialog = new Dialog(contextForDialog);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.formapago);
				
				
				final EditText edtmoney =  (EditText) dialog.findViewById(R.id.edMoney);
				final EditText ednotas =  (EditText) dialog.findViewById(R.id.edNot);
		
				Button dialogButton = (Button) dialog.findViewById(R.id.btnOK);
				
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						int OK1=0;
						int OK2=0;
						if ( edtmoney.getText().toString().isEmpty() || edtmoney.getText().toString().contains("0000.00") )
						{
							Toast.makeText(dialog.getContext(), "Debes introducir una cantidad.", Toast.LENGTH_LONG).show();
							
						}
						else 
						{
							OK1=1;
						}
						
						if (!forma.contains("Efectivo")){
							
							if ( ednotas.getText().toString().isEmpty() || ednotas.getText().toString().contains("Notas") ){
							
								Toast.makeText(dialog.getContext(), "Debes introducir una nota.", Toast.LENGTH_LONG).show();
							}
							else 
							{
								OK2=1;
							}
						}
						else
						{
							OK2=1;
						}
						
						
						if (OK1!=0 && OK2!=0)
						{
							
							String result = edtmoney.getText().toString();
							cb.setText(forma + " : " + result + " euros");
							cb.setTextColor(getResources().getColor(R.color.actionbar_background_end));
						
							for(int i=0;i<mediospago.size();i++)
							{
								if (mediospago.get(i).getName().contains(forma))
								{
									for (int j=0;j<payments.size();j++)
									{
										if (payments.get(j).getName().contains(forma))
										{
											payments.remove(j);
										}
									}
									
									Double amount = Double.parseDouble(result);
									if (forma.contains("Efectivo")){				
									
										Payments pay = new Payments (mediospago.get(i).getId(), mediospago.get(i).getSid(), 
												mediospago.get(i).getName(), amount, mediospago.get(i).getKey());
										payments.add(pay);
									}
									else
									{
										nota = ednotas.getText().toString();
										Payments pay = new Payments (mediospago.get(i).getId(), mediospago.get(i).getSid(), 
												mediospago.get(i).getName(), amount, mediospago.get(i).getKey(), nota);
										payments.add(pay);
										
									}
									
								
								}
							
							} // For
							dialog.dismiss();
						
						}
						
						
						
					}
					
				});
				Button dialogButton2 = (Button) dialog.findViewById(R.id.btnCancel);
				dialogButton2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						cb.setChecked(false);
						dialog.cancel();
					}
				});
				dialog.show();			
				
				
				
			}
    		  
    		 
			}	
           		
           		
           
				
    	   });
       }
       

		}  
	
		
		// Cargar Descuentos
		//final ListView lstDesc =  (ListView) findViewById(R.id.lstDesc);
		//List<String> listDesc = new ArrayList<String>();
		Main.DB.open();
		ArrayList<Discount> discounts = new ArrayList<Discount>();
		discounts = Main.DB.getDiscounts();
		Main.DB.close();
		descLay.removeAllViews();
		//listDesc.add("Descuentos");
		for (int d=0;d<discounts.size();d++)
		{
			
			String restId = discounts.get(d).getRestId();
			String menuId = discounts.get(d).getMenuId();
			String sectionId = discounts.get(d).getSectionId();
			String dishId = discounts.get(d).getDishId();
			String sid = discounts.get(d).getSid();
			String note = discounts.get(d).getNote();
			String dtype = discounts.get(d).getDtype();
			String name = discounts.get(d).getName();
			String amount = discounts.get(d).getAmount();
			
			if (menuId == null && sectionId == null && dishId == null)
			{
				//listDesc.add(name);
				
				CheckBox cb =  new CheckBox(this);
				cb.setId(d);
				cb.setText(name);
				cb.setTextColor(getResources().getColor(android.R.color.black));
				descLay.addView(cb);
				descLay.setVisibility(View.GONE);
				
			}
			
		}
		
		
		TextView txtDesc = (TextView) findViewById( R.id.txtDesc);
	       txtDesc.setOnClickListener (new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (descLay.isShown())
				{
					descLay.setVisibility(View.GONE);
				}else
				{
					descLay.setVisibility(View.VISIBLE);
				}
			}
	       });
	       
	   
        		
		
}	
		
	}	







