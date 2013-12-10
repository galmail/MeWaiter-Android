package com.aforma.mewaiter.utils;



import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

import com.aforma.mewaiter.app.Factura;

import android.util.Log;
 

/**
 * 
 * La clase JSONSendPOST se utiliza para realizar el envío del JSON de pedidos o facturas.
 *
 */
public class JSONSendPOST {
 
    InputStream is = null;
    JSONObject jObj = null;
    String json = "";
 
    // constructor
    public JSONSendPOST() {
 
    }
 
    public boolean postData(String url,JSONObject obj) {
        // Create a new HttpClient and Post Header

        HttpParams myParams = new BasicHttpParams();
        
        
        HttpConnectionParams.setConnectionTimeout(myParams, 10000); //Timeout Limit
        HttpConnectionParams.setSoTimeout(myParams, 10000); //Timeout Limit
        
        HttpClient httpclient = new DefaultHttpClient(myParams);
       
        String[] cadena;
       
        try {

            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");
            
            String Json1 = new String (obj.toString());
            byte[] Json = Json1.getBytes("UTF-8");
            String Json2= new String(Json, "UTF-8");
            StringEntity se = new StringEntity(Json2); 
            
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json; charset=UTF-8"));
            httppost.setEntity(se); 

            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());
            Log.i("tag", temp);
            
            if (temp.toString().contains("true"))
            {
            	
            	if(temp.toString().contains("total"))
            	{
            		cadena = temp.toString().split(":");
            		String tempo = cadena[2].toString();
            		String[] temporal = tempo.split("\\}");
            		
            		Factura.total = (String) temporal[0].toString();
            		
            	}
            	return true;
            }else
            {
            	return false;
            }


        } catch (ClientProtocolException e) {
        	Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        	return false;

        } catch (IOException e) {
        	 Log.e("mewaiter", "No se ha podido parsear el JSON: \"" + json + "\"");
        	 return false;
        }
    }
    public String post(String url) {
        // Create a new HttpClient and Post Header

        HttpParams myParams = new BasicHttpParams();
        
        
        HttpConnectionParams.setConnectionTimeout(myParams, 10000); //Timeout Limit
        HttpConnectionParams.setSoTimeout(myParams, 10000); //Timeout Limit
        
        HttpClient httpclient = new DefaultHttpClient(myParams);
       
        String[] cadena;
       
        try {

            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");
            
            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());
            Log.i("tag", temp);
            
              	return temp;
            

        } catch (ClientProtocolException e) {
        	Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        	return "false";

        } catch (IOException e) {
        	 Log.e("mewaiter", "No se ha podido parsear el JSON: \"" + json + "\"");
        	 return "false";
        }
    }
}