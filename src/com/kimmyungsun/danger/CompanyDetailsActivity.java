package com.kimmyungsun.danger;

import java.util.List;

import com.kimmyungsun.danger.provider.DangersDataSource;

import android.app.Activity;
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

public class CompanyDetailsActivity extends Activity 
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
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.company_details, menu);
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

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		if ( company != null ) {
			txtComapnyName.setText(company.getCompanyName());
			txtAddress.setText(company.getAddress());
			listMatters.setAdapter(new MatterDetailsAdapter(this, R.layout.matter_row_item, matters));
		}
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
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
