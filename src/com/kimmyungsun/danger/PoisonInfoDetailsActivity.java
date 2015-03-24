package com.kimmyungsun.danger;

import android.os.Bundle;
import android.view.View;

public class PoisonInfoDetailsActivity extends DangerActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_poisoninfo_details);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

	public void goAccidentManual(View view) {
		goToUrl ( "http://safedu.org/pds1/76052" );
	}
	

}
