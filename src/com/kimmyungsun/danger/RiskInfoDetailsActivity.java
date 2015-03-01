package com.kimmyungsun.danger;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class RiskInfoDetailsActivity extends Activity {
	
	private ListView listRiskInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_riskinfo_details);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		listRiskInfos = (ListView) findViewById(R.id.listRiskInfos);
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		listRiskInfos.setAdapter(new RiskInfoArrayAdapter(this, R.layout.riskinfo_row));
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

}
