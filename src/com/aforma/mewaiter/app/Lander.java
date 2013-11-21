package com.aforma.mewaiter.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import com.aforma.mewaiter.app.R;
import com.aforma.mewaiter.utils.DBHelper;



/**
 * 
 * Activity Lander muestra la primera pantalla de la App, con el bot—n "Comienzo"
 *
 */
public class Lander extends Activity {
	public static Activity activityLander = null;
	public DBHelper DB = null;
	public User resul = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lander);
		DB = new DBHelper(getApplicationContext());
		DB.open();
		final User result = DB.getUser();
		DB.close();
		
		activityLander = (Activity) this;
		final Button btbComenzar;
		btbComenzar = (Button) findViewById(R.id.btbComenzar);
		btbComenzar.setOnClickListener (new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
						
				if (result == null)
				{
					Intent login_intent = new Intent(getApplicationContext(),Login.class);
					startActivity(login_intent);
					//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
				}
				else
				{
					Intent main_intent = new Intent(getApplicationContext(), Main.class);
					startActivity(main_intent);
					//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
				}
				
			}
		
		
	});
	

	}	

	@Override
	public void onResume() {
		super.onResume();
		
		setContentView(R.layout.lander);
		final Button btbComenzar;
		
		DB = new DBHelper(getApplicationContext());
		DB.open();
		final User result = DB.getUser();
		DB.close();
		
		
		btbComenzar = (Button) findViewById(R.id.btbComenzar);
		btbComenzar.setOnClickListener (new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (result == null)
				{
					Intent login_intent = new Intent(getApplicationContext(),Login.class);
					startActivity(login_intent);
					//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
				}
				else
				{
					Intent main_intent = new Intent(getApplicationContext(), Main.class);
					startActivity(main_intent);
					//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
				}
				
						
			}
		});	
	}	
}