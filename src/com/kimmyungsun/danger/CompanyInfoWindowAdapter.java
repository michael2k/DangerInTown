package com.kimmyungsun.danger;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.kimmyungsun.danger.provider.DangersDataSource;

public class CompanyInfoWindowAdapter implements InfoWindowAdapter {
	
	private Activity activity;
	private DangersDataSource dangerDataSource;
	
	public CompanyInfoWindowAdapter(Activity activity, DangersDataSource dangerDataSource) {
		this.activity = activity;
		this.dangerDataSource = dangerDataSource;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoContents(Marker marker) {
		
		// Getting view from the layout file info_window_layout
		View v = activity.getLayoutInflater().inflate(R.layout.info_window, null);
		
		TextView txtCompany = (TextView) v.findViewById(R.id.txtCompanyName);
		TextView txtAddress = (TextView) v.findViewById(R.id.txtCompanyAddress);
		
		// Getting reference to the TextView to set longitude
//		ListView matters = (ListView) v.findViewById(R.id.listView1);
		
		String snippet = marker.getSnippet();
		if ( snippet != null && !snippet.trim().isEmpty() ) {
			
			
			Company company = dangerDataSource.getCompany(Long.valueOf(marker.getSnippet()));
			
			txtCompany.setText(company.getCompanyName());
			txtAddress.setText(company.getAddress());
			
			List<Matter> matters = dangerDataSource.searchMatters(company);
			
			ListView listMatters = (ListView) v.findViewById(R.id.listMatters);
			
			listMatters.setAdapter(new MatterArrayAdapter(activity, R.layout.matter_info_row_item, matters));
			
	
		} else {
			txtCompany.setText("정보없음");
			txtAddress.setText("");
		}
		
	    
	    // Returning the view containing InfoWindow contents
	    return v;
	}

}
