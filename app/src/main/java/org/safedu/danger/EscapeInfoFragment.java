package org.safedu.danger;

import java.util.HashMap;
import java.util.Map;

import org.safedu.danger.provider.DangersDataSource;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.safedu.danger.R;

public class EscapeInfoFragment extends Fragment
{
	
	private static final String TAG = EscapeInfoFragment.class.getName();
	
	private DangersDataSource ds;
	private Matter matter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_escapeinfo, container, false);
 
		return view;
	}
	
	

}
