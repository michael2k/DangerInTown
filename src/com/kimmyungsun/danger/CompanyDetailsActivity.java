package com.kimmyungsun.danger;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
//import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kimmyungsun.danger.provider.DangersDataSource;

public class CompanyDetailsActivity extends DangerActivity 
implements OnItemClickListener
{
	
	private static final String TAG = CompanyDetailsActivity.class.getName();
	
	public static final String COMPANY_ID = "company_id";
	private int companyId;
	private TextView txtComapnyName;
	private TextView txtAddress;
	private ListView listMatters;
	private Company company;
	private List<Matter> matters;
	
	private DangersDataSource ds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_details);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		txtComapnyName = (TextView) findViewById(R.id.txtCompanyName);
		txtAddress = (TextView) findViewById(R.id.txtCompanyAddress);
		listMatters = (ListView) findViewById(R.id.listMatters);
		listMatters.setOnItemClickListener(this);
		
		ds = new DangersDataSource(this);
		
		String companyId = getIntent().getStringExtra(COMPANY_ID);
		Log.d(TAG, COMPANY_ID + ":" + companyId);
		
		if ( companyId != null && !companyId.isEmpty() ) {
			this.companyId = Integer.parseInt(companyId);
			
			company = ds.getCompany(this.companyId);
			matters = ds.searchMatters(company);
			
			if ( company != null ) {
				txtComapnyName.setText(company.getCompanyName());
				txtAddress.setText(company.getAddress());
				listMatters.setAdapter(new MatterDetailsAdapter(this, R.layout.matter_row_item, matters));
			}
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Matter m = matters.get(position);
		
		Intent intent = new Intent(this, MatterDetailActivity.class);
		
		intent.putExtra(MatterDetailActivity.MATTER_ID, m.getId());
		
		startActivity(intent);
		
	}
	
	
}
