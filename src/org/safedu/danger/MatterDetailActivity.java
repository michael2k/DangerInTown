package org.safedu.danger;

import java.util.List;

import org.safedu.danger.provider.DangersDataSource;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.safedu.danger.R;

public class MatterDetailActivity extends DangerActivity {
	
	private static final String TAG = MatterDetailActivity.class.getName();
	
	public static final String MATTER_ID = "matter_id";
	
	private TextView txtMatterName;
	private TextView txtCasNo;
	private TextView txtRiskInfo;
	private TextView txtEtcInfo;
	
	private int matterId;
	private Matter matter;

	private DangersDataSource ds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matter_datail);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		txtMatterName = (TextView) findViewById(R.id.txtMatterName);
		txtCasNo = (TextView) findViewById(R.id.txtCasNo);
		txtRiskInfo = (TextView) findViewById(R.id.txtRiskInfo);
		txtEtcInfo = (TextView) findViewById(R.id.txtEtcInfo);
		
		matterId = getIntent().getIntExtra(MATTER_ID, -1);
		
		ds = new DangersDataSource(this);
		if ( matterId > 0 ) {
			matter = ds.getMatter(matterId);
			
			if ( matter != null ) {
				txtMatterName.setText(matter.getMatterName());
				txtCasNo.setText("Cas NO : " + matter.getCasNo());
				List<String> riskInfos = Matter.getRiskInfos(matter);
				StringBuilder sb = new StringBuilder();
				for( int i = 0; i < riskInfos.size(); i++ ) {
					if (sb.length() > 0 ) sb.append(",");
					sb.append(riskInfos.get(i));
				}
				txtRiskInfo.setText(sb.toString());
				txtEtcInfo.setText("배출량(kg):" + matter.getOutQty() + ", 이동량(kg):" + matter.getMoveQty());
			}
		}
		
	}

	public void showRiskInfoDetails(View view) {
		Log.d(TAG, "showRiskInfoDetails");
		
		Intent riskInfoIntent = new Intent(this, RiskInfoDetailsActivity.class);
		riskInfoIntent.putExtra(MATTER_ID, matter.getId());
		startActivity(riskInfoIntent);
		
	}
}
