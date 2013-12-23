package com.aforma.mewaiter.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HTTPSendGet {
	

	
    static JSONObject jObj = null;
    static String json = "";
    static boolean resultado=false;


	// constructor
	public HTTPSendGet() {
	
	}

public static JSONObject getData(String url) {
	
	InputStream result = null;

	HttpClient httpClient = new DefaultHttpClient();
	HttpGet get = new HttpGet(url); // For example
	HttpResponse response;
	try {
		response = httpClient.execute(get);
	
		if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		    BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
		    result = bufferedHttpEntity.getContent();
		} 
	
	} catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (ClientProtocolException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
     

	try {
       
    	BufferedReader reader = new BufferedReader(new InputStreamReader(
                        result, "utf-8"), 8);
                
    	StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        result.close();
        json = sb.toString();
    } catch (Exception e) {
        Log.e("Buffer Error", "Error converting result " + e.toString());
    }

    // try parse the string to a JSON object
    try {
        jObj = new JSONObject(json);
       
        
    } catch (JSONException e) {
        Log.e("JSON Parser", "Error parsing data " + e.toString());
    }

    return jObj;
   

	}
}
