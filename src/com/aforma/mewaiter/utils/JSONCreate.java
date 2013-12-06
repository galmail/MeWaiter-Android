package com.aforma.mewaiter.utils;



import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.aforma.mewaiter.app.Discount;
import com.aforma.mewaiter.app.Discountsel;
import com.aforma.mewaiter.app.ListMod;
import com.aforma.mewaiter.app.ListModSet;
import com.aforma.mewaiter.app.Main;
import com.aforma.mewaiter.app.Mod;
import com.aforma.mewaiter.app.Order;
import com.aforma.mewaiter.app.Ordermods;
import com.aforma.mewaiter.app.Payments;
 
import android.util.Log;
 
/**
 * 
 * La clase JSONCreate se utiliza para crear los JSON que será luego enviados en el envío de pedido y factura.
 *
 */
public class JSONCreate {
 
	JSONObject json = new JSONObject();
	JSONObject json2 = new JSONObject();
	JSONObject json3 = new JSONObject();
	JSONObject jsonP = new JSONObject();
	JSONArray json5 = new JSONArray();
	JSONArray json6 = new JSONArray();
	JSONArray json4 = new JSONArray();
	JSONArray json7 = new JSONArray();
	JSONArray json8 = new JSONArray();
	JSONObject json9 = new JSONObject();
	JSONArray json10 = new JSONArray();
	JSONObject orderJson = new JSONObject();
	JSONObject paymentJson = new JSONObject();
	JSONObject linesJson = new JSONObject();
	JSONObject mlsJson = new JSONObject();
	JSONObject mlJson = new JSONObject();
	JSONObject modJson = new JSONObject();
	JSONObject discJson =  new JSONObject();
	
	ListModSet mls = null;
	ListMod ml = null;
	Mod m = null;
	
	String sid_mls = "";
	String sid_ml = ""; 
	String sid_m = "";
	
	String name_mls = "";
	String name_ml = ""; 
	String name_m = "";
	String product_sid= "";
	String category_sid= "";
	String product_name= "";
	String price= "";
	String note = "";
	int cantidad = 0;
	
	
	
	//DBHelper DB = new DBHelper();
	int idOrder = 0;
   
 
    // constructor
    public JSONCreate() {
 
    }
    
    public JSONObject createJSONFactura( ArrayList<Payments> payments, String sid_table, String name, ArrayList<Discount> discounts) {
    	 
  	  try {
  		
  		paymentJson.put("table_sid", sid_table);
  		paymentJson.put("table_name", name);
	    	
  		Main.DB.open();
  		
  		if (payments.size() != 0)
		{
	  		for (int i=0;i< payments.size(); i++)
	  		{
	  			String nameP = payments.get(i).getName();
	  			String key = payments.get(i).getKey();
	  			String sid = payments.get(i).getSid();
	  			String amount = String.valueOf(payments.get(i).getAmount());
	  			String notas = payments.get(i).getNotas();
	
		    	jsonP.put("name", key);
		    	jsonP.put("sid", sid);
		    	jsonP.put("amount", amount);
		    	if ( !key.contains("cash"))
		    	{
		    		jsonP.put("note", notas);
		    	}
		    	json8.put(jsonP);
		    	jsonP = new JSONObject();
	  			
	  		}
		}
	  		
	  		paymentJson.put("payment_lines", json8);
	  		json8 = new JSONArray();
	  		
	  		if (discounts.size() != 0)
    		{
    			for(int disc=0; disc<discounts.size();disc++)
    			{
	    			String sid_discount =  discounts.get(disc).getSid();
	    			String namedec = discounts.get(disc).getName();
	    			String notedec = discounts.get(disc).getNote();
	    			String dtype = discounts.get(disc).getDtype();
	    			String amount = discounts.get(disc).getAmount();
	    			
	    			discJson.put("sid", sid_discount);
	    			discJson.put("name", namedec);
	    			discJson.put("note", notedec);
	    			discJson.put("dtype", dtype );
	    			discJson.put("amount", amount);
	    			json10.put(discJson);
	    			discJson =  new JSONObject();
	    			
    			}
    		
	    		paymentJson.put("discounts", json10);
	    		json10 = new JSONArray();
    		}
  		
  		
  		} catch (Exception e) {
        	Main.DB.close();
            Log.e("Buffer Error", "Error creando json payment." + e.toString());
        }
       Main.DB.close();
       
       return paymentJson;
 
    }

 
    public JSONObject createJSONOrder( ArrayList<Order> order, ArrayList<Ordermods> ordermods, ArrayList<Discountsel> discountssels) {
 
    	  try {
	    	
    		
    		orderJson.put("table_sid", order.get(0).getSidTable());
    		
    		orderJson.put("table_name", Main.m_Text);
	    	Main.DB.open();
    		for (int i=0;i< order.size(); i++)
    		{
    			
    			
    			product_sid = order.get(i).getSid();
    			category_sid = order.get(i).getCategory();
    			product_name = order.get(i).getProductName();
    			cantidad =  order.get(i).getQuantity();
    			price = order.get(i).getPrice();
    			
		    	json.put("product_sid", product_sid);
		    	json.put("category_sid", category_sid);
		    	json.put("product_name", product_name);
		    	json.put("multiply", cantidad);
		    	json.put("price", price);
		    	
		    	idOrder = order.get(i).getId();
		    	
		    	//ordermods = order.get(i).getOrderMods();
		    	ordermods = Main.DB.getOrdermodsByIDMLS(idOrder, "%");
		    	discountssels = Main.DB.getDiscountSelByIdOrder(idOrder);
		    	//ordermods = Main.DB.getOrdermodsByID(idOrder, sid_mls);
		    	if (!ordermods.isEmpty())
		    	{
		    	for (int z=0; z< ordermods.size();z++)
		    	{

    						    		
		    		ArrayList<Ordermods> ordermods_mls = new ArrayList<Ordermods>();		    		
		    		sid_mls = ordermods.get(z).getSidmls();
		    		ordermods_mls = Main.DB.getOrdermodsByIDMLS(idOrder, sid_mls);
		    		
		    		
		    		for (int i1=0; i1<ordermods_mls.size();i1++) //for select distinct mls where id=id get sid and name
		    		{
		    		
		    			// Obtener nombre de mls
		    			mls = Main.DB.getListModSetSid(sid_mls);
		    			name_mls = mls.getName();
		    			sid_mls = mls.getSid();
		    			String sid = "";
		    			if ( !mlsJson.isNull("sid"))
		    			{ 
		    				sid = mlsJson.get("sid").toString();
		    			}
		    			
		    			if ( !sid.contains(sid_mls))
		    			{
		    				mlsJson = new JSONObject();
		    				//Modifier list Set (OBjeto)
		    				mlsJson.put("name", name_mls);
		    				mlsJson.put("sid", sid_mls);
	    				}
		    			
		    			ArrayList<Ordermods> ordermods_ml = new ArrayList<Ordermods>();
		    			
		    			ordermods_ml = Main.DB.getOrdermodsByIDMLSML(idOrder, sid_mls, "%");
		    			sid_ml = ordermods_ml.get(i1).getSidml();
			    		for (int i11=0; i11<ordermods_ml.size();i11++) //for select distinct ml where id=id and mls=mls get sid and name
			    		{	
			    			sid_ml = ordermods_ml.get(i11).getSidml();
			    			ml = Main.DB.getListModSid(sid_ml);
			    			name_ml = ml.getName();
		    				//Modifier list
		    				json2.put("name", name_ml);
		    				json2.put("sid", sid_ml);
		    		
		    				ArrayList<Ordermods> ordermods_m = new ArrayList<Ordermods>();
				    		sid_m = ordermods_ml.get(i11).getSid();
				    		ordermods_m = Main.DB.getOrdermodsByIDMLSM(idOrder, sid_mls, sid_ml, "%");
				    		
				    			
					    		for (int i2=0; i2<ordermods_m.size();i2++)//for select distinct m where id=id and mls=mls and ml=ml get sid and name
					    		{		
					    			
					    			sid_m = ordermods_m.get(i2).getSid();
					    			m = Main.DB.getModsSid(sid_m);
				    				name_m = m.getName();
				    				
				    				
				    				//Modificadores		    				    				
				    				json3.put("sid", sid_m);
				    				json3.put("name", name_m);
				    				if (!json5.toString().contains(json3.toString()))
				    				{
				    					json4.put(i2, json3);
				    				}
				    				json3 = new JSONObject();
				    				
					    		}
					    		json2.put("selected_modifiers", json4);
			    				json5.put(i11, json2);
			    				json4 = new JSONArray();
			    				json2 = new JSONObject();
			    				
				    		}
			    			//json5.put(i1, json2);			    			
			    			//json2 = new JSONObject();
			    		
			    			mlsJson.put("modifier_lists", json5);
			    			//json6.put(i1, mlsJson);
			    			json5 = new JSONArray();
		    				
		    			
		    		}
		    				
		    				
		    				//json5 = new JSONArray();
		    				json.put("modifier_list_set", mlsJson);
		    				//json6 = new JSONArray();
		    				mlsJson = new JSONObject();
				    		note=order.get(i).getNote();
				    		json.put("note", note);
				    		
				    		// meter aqui los descuentos 
				    		//Main.DB.open();
				    		if (discountssels.size() != 0)
				    		{
					    		for(int disc=0; disc<discountssels.size();disc++)
					    		{
					    			String sid_discount =  discountssels.get(disc).getSid();
					    			Discount discount= Main.DB.getDiscountsBySid(sid_discount);
					    			String namedec = discount.getName();
					    			String notedec = discount.getNote();
					    			String dtype = discount.getDtype();
					    			String amount = discount.getAmount();
					    			
					    			json9.put("sid", sid_discount);
					    			json9.put("name", namedec);
					    			json9.put("note", notedec);
					    			json9.put("dtype", dtype );
					    			json9.put("amount", amount);
					    			
					    		}
					    		
					    		json.put("discount", json9);
					    		json9 = new JSONObject();
		    				}
				    		json7.put(i, json);
				    		json = new JSONObject();
			 	}			
		    		    				
		    	
		    	
		    	}else
		    	{
		    		note=order.get(i).getNote();
		    		json.put("note", note);
		    		
		    		
		    		if (discountssels.size() != 0)
		    		{
		    			
		    		for(int disc=0; disc<discountssels.size();disc++)
		    		{
		    			String sid_discount =  discountssels.get(disc).getSid();
		    			Discount discount= Main.DB.getDiscountsBySid(sid_discount);
		    			String namedec = discount.getName();
		    			String notedec = discount.getNote();
		    			String dtype = discount.getDtype();
		    			String amount = discount.getAmount();
		    			
		    			json9.put("sid", sid_discount);
		    			json9.put("name", namedec);
		    			json9.put("note", notedec);
		    			json9.put("dtype", dtype );
		    			json9.put("amount", amount);
		    			
		    		}
		    		
			    		json.put("discount", json9);
			    		json9 = new JSONObject();
		    		}
		    		json7.put(i, json);
		    		
		    		
		    		json = new JSONObject();
		    	
		    	}
		    	
 		}
    		orderJson.put("ticket_lines", json7);
	    	json7 = new JSONArray();
    		
        } catch (Exception e) {
        	Main.DB.close();
            Log.e("Buffer Error", "Error creando json order." + e.toString());
        }
       Main.DB.close();
       return orderJson;
 
    }
}