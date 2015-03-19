package com.kimmyungsun.danger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PoisonInfoDetailsActivity extends DangerActivity {
	
//	private ListView listPoisonInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_poisoninfo_details);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
//		listPoisonInfos = (ListView) findViewById(R.id.listPoisonInfos);
		
	}

	public void goAccidentManual(View view) {
		goToUrl ( "http://safedu.org/pds1/76052" );
	}
	
	private void goToUrl( String url ) {
		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}

	/* (non-Javadoc)
	 * @see com.kimmyungsun.danger.DangerActivity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	

}
