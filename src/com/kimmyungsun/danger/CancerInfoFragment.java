package com.kimmyungsun.danger;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.kimmyungsun.danger.provider.DangersDataSource;

public class CancerInfoFragment extends Fragment
implements OnItemClickListener
{
	
	private static final String TAG = CancerInfoFragment.class.getName();
	
	private DangersDataSource ds;
	private Matter matter;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_cancerinfo, container, false);
 
		return view;
	}
	

	@Override
	public void onStart() {
	    super.onStart();
	    
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume() {
		
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
//		Matter m = matters.get(position);
//		
//		Intent intent = new Intent(getActivity(), MatterDetailActivity.class);
//		
//		intent.putExtra(MatterDetailActivity.MATTER_ID, m.getId());
//		
//		startActivity(intent);

	}


	public void updateMatterInfo(final Matter matter ) {
			Log.d(TAG, "updateMatterInfo[" + matter.toString() + "]");
			
			this.matter = matter;
			
	}
	

}
