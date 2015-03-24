package com.kimmyungsun.danger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kimmyungsun.danger.constanst.IDangerConstants;

public class DangerActivity extends Activity
implements IDangerConstants{
	
	private static final String TAG = DangerActivity.class.getName();

	public void goHome() {
		Intent homeIntent = new Intent(this, DangerDataTest.class);
		homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
		finish();
	}
	
	public void goJoin() {
		Intent joinIntent = new Intent(this, JoinActivity.class);
		
		startActivity(joinIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.d(TAG, "onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.danger_default_menu, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_gohome ) {
			goHome();
			return true;
		} else if ( id == R.id.action_join) {
			goJoin();
			return true;
		} else if ( id == android.R.id.home ) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void goToUrl(String url) {
		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}

}
