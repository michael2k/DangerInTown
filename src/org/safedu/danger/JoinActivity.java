package org.safedu.danger;

import org.safedu.danger.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class JoinActivity extends DangerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join, menu);
		return true;
	}

	public void goRequest(View view) {
		goToUrl ( "http://bbs3.agora.media.daum.net/gaia/do/petition/read?bbsId=P001&articleId=165377" );
	}
	
	public void goSpeech(View view) {
		goToUrl ( "http://www.me.go.kr/home/web/index.do?menuId=10191" );
	}
	
	public void goSupport(View view) {
		goToUrl ( "http://safedu.org/support" );
	}
	
	public void goSearchPlants(View view) {
		goToUrl ( "https://kangmin.cartodb.com/viz/07769b6a-a5fa-11e4-ab27-0e0c41326911/public_map" );
	}
	
	public void goSafetyNews(View view) {
		goToUrl ( "http://www.safedu.org/news1" );
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
}
