package com.kimmyungsun.danger;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.kimmyungsun.danger.provider.DangersDataSource;

public class MatterDetailActivity extends DangerActivity {
	
	private static final String TAG = MatterDetailActivity.class.getName();
	
	public static final String MATTER_ID = "matter_id";
	
	private TextView txtMatterName;
	private TextView txtCasNo;
	private TextView txtRiskInfo;
//	private ImageView imgRiskInfo;
//	private ImageView imgResultInfo;
//	private TextView txtResultInfo;
	private TextView txtEtcInfo;
//	private ResultInfo resultInfo;
	
	private int matterId;
	private Matter matter;

	private DangersDataSource ds;
	
	private Object[][] hbMapDatas = new Object[][] {
			new Object[] {"000050-00-0",R.drawable.hb_000050_00_0},
			new Object[] {"000056-23-5",R.drawable.hb_000056_23_5},
			new Object[] {"000062-53-3",R.drawable.hb_000062_53_3},
			new Object[] {"000067-66-3",R.drawable.hb_000067_66_3},
			new Object[] {"000071-43-2",R.drawable.hb_000071_43_2},
			new Object[] {"000075-01-4",R.drawable.hb_000075_01_4},
			new Object[] {"000075-07-0",R.drawable.hb_000075_07_0},
			new Object[] {"000075-09-2",R.drawable.hb_000075_09_2},
			new Object[] {"000075-21-8",R.drawable.hb_000075_21_8},
			new Object[] {"000075-56-9",R.drawable.hb_000075_56_9},
			new Object[] {"000077-78-1",R.drawable.hb_000077_78_1},
			new Object[] {"000078-79-5",R.drawable.hb_000078_79_5},
			new Object[] {"000079-01-6",R.drawable.hb_000079_01_6},
			new Object[] {"000079-06-1",R.drawable.hb_000079_06_1},
			new Object[] {"000095-53-4",R.drawable.hb_000095_53_4},
			new Object[] {"000101-14-4",R.drawable.hb_000101_14_4},
			new Object[] {"000101-77-9",R.drawable.hb_000101_77_9},
			new Object[] {"000106-89-8",R.drawable.hb_000106_89_8},
			new Object[] {"000106-97-8",R.drawable.hb_000106_97_8},
			new Object[] {"000106-98-9",R.drawable.hb_000106_98_9},
			new Object[] {"000106-99-0",R.drawable.hb_000106_99_0},
			new Object[] {"000107-06-2",R.drawable.hb_000107_06_2},
			new Object[] {"000107-13-1",R.drawable.hb_000107_13_1},
			new Object[] {"000107-30-2",R.drawable.hb_000107_30_2},
			new Object[] {"000108-88-3",R.drawable.hb_000108_88_3},
			new Object[] {"000108-95-2",R.drawable.hb_000108_95_2},
			new Object[] {"000115-07-1",R.drawable.hb_000115_07_1},
			new Object[] {"000117-81-7",R.drawable.hb_000117_81_7},
			new Object[] {"000123-91-1",R.drawable.hb_000123_91_1},
			new Object[] {"000127-18-4",R.drawable.hb_000127_18_4},
			new Object[] {"000141-78-6",R.drawable.hb_000141_78_6},
			new Object[] {"001330-20-7",R.drawable.hb_001330_20_7},
			new Object[] {"007664-93-9",R.drawable.hb_007664_93_9},
			new Object[] {"008030-30-6",R.drawable.hb_008030_30_6},

	};
	
	private Map<String, Integer> hbMap = new HashMap<String, Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matter_datail);
		
		txtMatterName = (TextView) findViewById(R.id.txtMatterName);
		txtCasNo = (TextView) findViewById(R.id.txtCasNo);
		txtRiskInfo = (TextView) findViewById(R.id.txtRiskInfo);
		txtEtcInfo = (TextView) findViewById(R.id.txtEtcInfo);
//		imgRiskInfo = (ImageView) findViewById(R.id.imgRiskInfo);
//		imgResultInfo = (ImageView) findViewById(R.id.imgResultInfo);
//		txtResultInfo = (TextView) findViewById(R.id.txtResultInfo);
		
		matterId = getIntent().getIntExtra(MATTER_ID, -1);
		
		ds = new DangersDataSource(this);
		if ( matterId > 0 ) {
			matter = ds.getMatter(matterId);
//			resultInfo = new ResultInfo(matter.getResultPart());
//			Log.d(TAG, resultInfo.toString());
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		hbMap.clear();
		for ( Object[] data : hbMapDatas) {
			hbMap.put((String)data[0], (Integer)data[1]);
		}
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matter_datail, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		if ( matter != null ) {
			txtMatterName.setText(matter.getMatterName());
//			if ( matter.getCasNo() != null && !matter.getCasNo().isEmpty() ) {
				txtCasNo.setText("Cas NO : " + matter.getCasNo());
//			}
			txtRiskInfo.setText(matter.getRiskInfo());
			txtEtcInfo.setText("배출량(kg):" + matter.getOutQty() + ", 이동량(kg):" + matter.getMoveQty());
//			txtResultInfo.setText(matter.getResultPart());

//			int resourceId = R.drawable.human_body_empty;
//			
//			if ( matter.getResultPart() != null && !matter.getResultPart().isEmpty() ) {
//				
//				if ( matter.getCasNo() != null && !matter.getCasNo().isEmpty() ) {
//					String casNo = matter.getCasNo().trim();
//					if ( hbMap.containsKey(casNo)) {
//						resourceId = hbMap.get(casNo);
//					}
//				}
//			}
//			imgResultInfo.setImageResource(resourceId);
			
		}
		super.onResume();
	}
	
	public void showRiskInfoDetails(View view) {
		Log.d(TAG, "showRiskInfoDetails");
		
		Intent riskInfoIntent = new Intent(this, RiskInfoDetailsActivity.class);
		riskInfoIntent.putExtra(MATTER_ID, matter.getId());
		startActivity(riskInfoIntent);
		
	}
	
	public void showPoisonInfoDetails(View view) {
		Log.d(TAG, "showPoisonInfoDetails");
		
		Intent poisonInfoIntent = new Intent(this, PoisonInfoDetailsActivity.class);
		startActivity(poisonInfoIntent);
		
	}
}
