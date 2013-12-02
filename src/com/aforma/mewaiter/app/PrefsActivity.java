package com.aforma.mewaiter.app;

import com.aforma.mewaiter.app.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * 
 * Actualmente no se usa
 *
 */
public class PrefsActivity extends PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}
}
