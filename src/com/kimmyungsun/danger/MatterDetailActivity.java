package com.kimmyungsun.danger;

import com.kimmyungsun.danger.provider.DangersDataSource;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MatterDetailActivity extends Activity {
	
	private static final String TAG = MatterDetailActivity.class.getName();
	
	public static final String MATTER_ID = "matter_id";
	
	private TextView txtMatterName;
	private TextView txtRiskInfo;
	private ImageView imgRiskInfo;
	private ImageView imgResultInfo;
	private TextView txtEtcInfo;
	private ResultInfo resultInfo;
	
	private int matterId;
	private Matter matter;

	private DangersDataSource ds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matter_datail);
		
		txtMatterName = (TextView) findViewById(R.id.txtMatterName);
		txtRiskInfo = (TextView) findViewById(R.id.txtRiskInfo);
		txtEtcInfo = (TextView) findViewById(R.id.txtEtcInfo);
		imgRiskInfo = (ImageView) findViewById(R.id.imgRiskInfo);
		imgResultInfo = (ImageView) findViewById(R.id.imgResultInfo);
		
		matterId = getIntent().getIntExtra(MATTER_ID, -1);
		
		ds = new DangersDataSource(this);
		if ( matterId > 0 ) {
			matter = ds.getMatter(matterId);
			resultInfo = new ResultInfo(matter.getResultPart());
			Log.d(TAG, resultInfo.toString());
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matter_datail, menu);
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
		} else if ( id == android.R.id.home ) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		if ( matter != null ) {
			txtMatterName.setText(matter.getMatterName());
			txtRiskInfo.setText(matter.getRiskInfo());
			txtEtcInfo.setText("CAS No:" + matter.getCasNo() + ", 배출량(kg):" + matter.getOutQty() + ", 이동량(kg):" + matter.getMoveQty());
			imgRiskInfo.setImageResource(Matter.getIconType(matter));
			
		}
		super.onResume();
	}
}
