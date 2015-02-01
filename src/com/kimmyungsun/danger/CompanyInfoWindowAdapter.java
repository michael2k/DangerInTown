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
		
		TextView txtCompany = (TextView) v.findViewById(R.id.textView1);
		TextView txtAddress = (TextView) v.findViewById(R.id.textView2);
		
		// Getting reference to the TextView to set longitude
//		ListView matters = (ListView) v.findViewById(R.id.listView1);
		
		String snippet = marker.getSnippet();
		if ( snippet != null && !snippet.trim().isEmpty() ) {
			
			
			Company company = dangerDataSource.getCompany(Long.valueOf(marker.getSnippet()));
			
			txtCompany.setText(company.getCompanyName());
			txtAddress.setText(company.getAddress());
			
			List<Matter> matters = dangerDataSource.searchMatters(company);
			
			TableLayout tblMatters = (TableLayout) v.findViewById(R.id.tblMatters);
			
			for ( Matter m : matters ) {

				TableRow tr = new TableRow(activity);
				tr.setBackgroundColor(Color.GREEN);
				
				TextView name = new TextView(activity);
				name.setText(m.getMatterName());
				name.setTextColor(Color.BLACK);
				tr.addView(name);
				
				TextView ri = new TextView(activity);
				ri.setText(m.getRiskInfo());
				ri.setTextColor(Color.RED);
				tr.addView(ri);
				
				tblMatters.addView(tr);
	//			matters.setAdapter(new ArrayAdapter<Matter>(activity, R.layout.list_view_row_item, R.id.textViewMatter,list.toArray(new Matter[0])));
			}
			
			
			
	
	
		} else {
			txtCompany.setText("정보없음");
			txtAddress.setText("");
		}
		
	    
	    // Returning the view containing InfoWindow contents
	    return v;
	}

}
