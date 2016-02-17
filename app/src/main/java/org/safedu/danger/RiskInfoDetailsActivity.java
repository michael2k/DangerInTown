package org.safedu.danger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.safedu.danger.provider.DangersDataSource;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.safedu.danger.R;

public class RiskInfoDetailsActivity extends DangerActivity {

	private static final String TAG = RiskInfoDetailsActivity.class.getName();

	public static final String MATTER_ID = "matter_id";

	private static final String LINK_URL = "http://safedu.org/commun_pds/";

	private static String[][] links = new String[][] {
		new String[] {"아크롤레인","88684"},
		new String[] {"아크릴산","88740"},
		new String[] {"아크릴로니트릴","88745"},
		new String[] {"아크릴일클로라이드","88749"},
		new String[] {"알릴알코올","88756"},
		new String[] {"알릴클로라이드","88762"},
		new String[] {"암모니아","88766"},
		new String[] {"암모니아(수산화암모늄(CASNo.1366-21-6)포함)","88766"},
		new String[] {"질산암모늄","88770"},
		new String[] {"아르신","88775"},
		new String[] {"벤젠","88779"},
		new String[] {"염화벤질","88783"},
		new String[] {"노말-부틸아민","88787"},
		new String[] {"이황화탄소","88791"},
		new String[] {"일산화탄소","88795"},
		new String[] {"염소","88799"},
		new String[] {"이산화염소","88803"},
		new String[] {"클로로설폰산","88807"},
		new String[] {"염화시안","88811"},
		new String[] {"메타-크레졸","88815"},
		new String[] {"디보란","88819"},
		new String[] {"아세트산에틸","88823"},
		new String[] {"산화에틸렌","88827"},
		new String[] {"에틸렌디아민","88831"},
		new String[] {"에틸렌이민","88835"},
		new String[] {"플루오린","88839"},
		new String[] {"포름알데하이드","88843"},
		new String[] {"포름알데히드","88843"},
		new String[] {"폼산","88847"},
		new String[] {"헥사민","88851"},
		new String[] {"염화수소","88856"},
		new String[] {"시안화수소","88860"},
		new String[] {"플루오르화수소","88864"},
		new String[] {"과산화수소","88868"},
		new String[] {"황화수소","88873"},
		new String[] {"디이소시안산이소포론","88878"},
		new String[] {"사린","88882"},
		new String[] {"메탄올","88886"},
		new String[] {"메틸알코올","88886"},
		new String[] {"메틸아크릴레이트","88890"},
		new String[] {"염화메틸","88894"},
		new String[] {"메틸에틸케톤","88898"},
		new String[] {"메틸에틸케톤과산화물","88902"},
		new String[] {"메틸하이드라진","88906"},
		new String[] {"메틸비닐케톤","88910"},
		new String[] {"메틸아민","88914"},
		new String[] {"질산","88918"},
		new String[] {"산화질소","88922"},
		new String[] {"니트로벤젠","88926"},
		new String[] {"니트로메탄","88930"},
		new String[] {"파라-니트로톨루엔","88934"},
		new String[] {"페놀","88938"},
		new String[] {"포스겐","88942"},
		new String[] {"포스핀","88946"},
		new String[] {"옥시염화인","88950"},
		new String[] {"삼염화인","88954"},
		new String[] {"염소산칼륨","88958"},
		new String[] {"질산칼륨","88962"},
		new String[] {"과염소산칼륨","88966"},
		new String[] {"과망간산칼륨","88970"},
		new String[] {"산화프로필렌","88974"},
		new String[] {"나트륨","88978"},
		new String[] {"염소산나트륨","88982"},
		new String[] {"시안화나트륨","88986"},
		new String[] {"질산나트륨","88990"},
		new String[] {"황산","88994"},
		new String[] {"톨루엔","88998"},
		new String[] {"톨루엔-2,4-디이소시아네이트","89002"},
		new String[] {"트리에틸아민","89006"},
		new String[] {"트리메틸아민","89010"},
		new String[] {"염화비닐","89014"},
		new String[] {"인화아연","89018"}
	};

	private static Map<String, String> linkMap = new HashMap<String, String>();

	static {
		for(String[] link : links ) {
			linkMap.put(link[0], link[1]);
		}
	}

	private EscapeInfoFragment rif;
	private int matterId;
	private DangersDataSource ds;
	private Matter matter;
	private EscapeInfoFragment eif;
	private CancerInfoFragment cif;
	private List<String> riskInfos;

	private Animation animBlink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_riskinfo_details);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		matterId = getIntent().getIntExtra(MATTER_ID, -1);

		ds = new DangersDataSource(this);
		if ( matterId > 0 ) {
			matter = ds.getMatter(matterId);
			Log.d(TAG, matter.toString());

		}

		animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);

		FragmentManager fm = getFragmentManager();

		eif = (EscapeInfoFragment) getFragmentManager().findFragmentById(R.id.fragmentEscapeInfo);
        cif = (CancerInfoFragment) getFragmentManager().findFragmentById(R.id.fragmentCancerInfo);

		String riskInfo = matter.getRiskInfo();
		Log.d(TAG, "riskInfo:" + riskInfo);
		if ( riskInfo == null || riskInfo.isEmpty() || !riskInfo.contains("발암") ) {
			// hide eif
			fm.beginTransaction().hide(cif).commit();
		}
		if ( riskInfo == null || riskInfo.isEmpty() || !riskInfo.contains("사고대비") ) {
			// hide eif
			fm.beginTransaction().hide(eif).commit();
		}

		// blink layout
		if ( riskInfo != null && !riskInfo.isEmpty() ) {
			if ( riskInfo.contains("발암") ) {
				LinearLayout ll = (LinearLayout) findViewById(R.id.cancerLayout);
				ll.startAnimation(animBlink);
			}
			if ( riskInfo.contains("사고대비") ) {
				LinearLayout ll = (LinearLayout) findViewById(R.id.accidentLayout);
				ll.startAnimation(animBlink);
			}
			if ( riskInfo.contains("생식") ) {
				LinearLayout ll = (LinearLayout) findViewById(R.id.fertileLayout);
				ll.startAnimation(animBlink);
			}
			if ( riskInfo.contains("발달") ) {
				LinearLayout ll = (LinearLayout) findViewById(R.id.developLayout);
				ll.startAnimation(animBlink);
			}
			if ( riskInfo.contains("환경") ) {
				LinearLayout ll = (LinearLayout) findViewById(R.id.environLayout);
				ll.startAnimation(animBlink);
			}
			if ( riskInfo.contains("변이") ) {
				LinearLayout ll = (LinearLayout) findViewById(R.id.mutationLayout);
				ll.startAnimation(animBlink);
			}
			if ( riskInfo.contains("변이") ) {
				LinearLayout ll = (LinearLayout) findViewById(R.id.mutationLayout);
				ll.startAnimation(animBlink);
			}
		}



	}

	public void goEscapeInfo(View view ) {
		Log.d(TAG, "goEscapeInfo[" + matter.getMatterName() + "]");
		String matterName = matter.getMatterName().replace(" ", "").trim();
		if ( linkMap.containsKey( matterName ) ) {
			String url = LINK_URL + linkMap.get(matterName);
			goToUrl( url );
		}
	}


	public void goCancerInfo(View view) {
		Log.d(TAG, "goCancerInfo");

		Intent cancerInfoIntent = new Intent(this, CancerInfoDetailsActivity.class);
		cancerInfoIntent.putExtra(MATTER_ID, matter.getId());
		startActivity(cancerInfoIntent);
	}




}