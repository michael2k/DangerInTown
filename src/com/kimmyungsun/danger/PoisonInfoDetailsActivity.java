package com.kimmyungsun.danger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class PoisonInfoDetailsActivity extends Activity {
	
//	private ListView listPoisonInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_poisoninfo_details);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
//		listPoisonInfos = (ListView) findViewById(R.id.listPoisonInfos);
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
//		listPoisonInfos.setAdapter(new RiskInfoArrayAdapter(this, R.layout.riskinfo_row));
		super.onResume();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if ( id == android.R.id.home ) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void goAccidentManual(View view) {
		goToUrl ( "http://safedu.org/pds1/76052" );
	}
	
	private void goToUrl( String url ) {
		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}
	

}
