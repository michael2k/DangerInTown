package com.kimmyungsun.danger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class JoinActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void goRequest(View view) {
		goToUrl ( "" );
	}
	
	public void goSpeech(View view) {
		goToUrl ( "http://www.me.go.kr/home/web/index.do?menuId=10191" );
	}
	
	public void goSupport(View view) {
		goToUrl ( "http://safedu.org/support" );
	}
	
	public void goFacebook(View view) {
		goToUrl ( "https://facebook.com/profile.php?id=1565798033654421" );
	}
	
	public void goAskDetails(View view) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "safedu.org@hanmail.net" });
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[우리동네 위험지도] 상세정보 문의");
		emailIntent.setType("message/rfc822");
		
		startActivity(Intent.createChooser(emailIntent, "이메일 앱을 선택하세요 :"));
	}
	
	public void goRecommend(View view) {
		goToUrl ( "" );
	}
	
	public void goHome(View view) {
		goToUrl ( "http://safedu.org" );
	}
	
	private void goToUrl( String url ) {
		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}
}
