package com.kimmyungsun.danger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CompanyInfoFragment extends Fragment {
	
	private static final String TAG = CompanyInfoFragment.class.getName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.info_window, container, false);
	}
	
	
	

}
