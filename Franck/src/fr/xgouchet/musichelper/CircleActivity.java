package fr.xgouchet.musichelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CircleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_circle, menu);
		return true;
	}

	
}
