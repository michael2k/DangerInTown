package com.kimmyungsun.danger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class JoinActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		
//		GridView joinView = (GridView) findViewById(R.id.joinView);
//		joinView.setAdapter(new JoinViewAdapter(this));
//		
//		joinView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Toast.makeText(JoinActivity.this, "" + position, Toast.LENGTH_SHORT).show();
//			}
//		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join, menu);
		return true;
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
	
	public void goSearchCancer(View view) {
//		goToUrl ( "https://facebook.com/profile.php?id=1565798033654421" );
		goToUrl ( "http://nocancer.kr/carcinogen2/search.html" );
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
