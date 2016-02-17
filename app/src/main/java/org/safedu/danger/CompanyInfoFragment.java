package org.safedu.danger;

import java.util.List;

import org.safedu.danger.provider.DangersDataSource;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.safedu.danger.R;

public class CompanyInfoFragment extends Fragment
implements OnItemClickListener
{
	
	private static final String TAG = CompanyInfoFragment.class.getName();
	
	public static final String COMPANY_ID = "company_id";
	private String companyId;

	private Company company;

	private DangersDataSource ds;

	private ListView listMatters;

	private List<Matter> matters;

	private TextView txtCompanyName;
	private ImageView imgCompanyInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_company_info, container, false);
 
		// If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            companyId = savedInstanceState.getString(COMPANY_ID);
        }
        
		return view;
	}
	
	public void updateCompanyInfo(final String companyId ) {
		Log.d(TAG, "updateCompanyInfo[" + companyId + "]");
		
		txtCompanyName = (TextView) getView().findViewById(R.id.txtCompanyName);
		imgCompanyInfo = (ImageView) getView().findViewById(R.id.imgCompnayInfo);
		
		OnClickListener ocl = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), CompanyDetailsActivity.class);
				intent.putExtra(COMPANY_ID, companyId);
				startActivity(intent);
			}
		};
		
		txtCompanyName.setOnClickListener(ocl);
		imgCompanyInfo.setOnClickListener(ocl);
		
		listMatters = (ListView) getView().findViewById(R.id.listMatters);
		
		ds = new DangersDataSource(getActivity());
		
		Log.d(TAG, COMPANY_ID + ":" + companyId);
		
		if ( companyId != null && !companyId.isEmpty() ) {
			
			company = ds.getCompany(Long.parseLong(companyId));
			matters = ds.searchMatters(company);
		}

		if ( company != null ) {
			txtCompanyName.setText(company.getCompanyName());
			listMatters.setAdapter(new MatterArrayAdapter(getActivity(), R.layout.matter_item_in_info, matters));
//			listMatters.setOnItemClickListener(this);
		}
		
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	
	    // Save the current article selection in case we need to recreate the fragment
	    outState.putString(COMPANY_ID, companyId);
	}

	@Override
	public void onStart() {
	    super.onStart();
	
	    // During startup, check if there are arguments passed to the fragment.
	    // onStart is a good place to do this because the layout has already been
	    // applied to the fragment at this point so we can safely call the method
	    // below that sets the article text.
	    Bundle args = getArguments();
	    if (args != null) {
	        // Set article based on argument passed in
	        updateCompanyInfo(args.getString(COMPANY_ID));
	    } else if (companyId != null) {
	        // Set article based on saved instance state defined during onCreateView
	        updateCompanyInfo(companyId);
	    }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Matter m = matters.get(position);
		
		Intent intent = new Intent(getActivity(), MatterDetailActivity.class);
		
		intent.putExtra(MatterDetailActivity.MATTER_ID, m.getId());
		
		startActivity(intent);

	}
	

}
