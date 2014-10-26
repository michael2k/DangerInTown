package com.kimmyungsun.danger;

import java.util.List;

import android.location.Location;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kimmyungsun.geocode.GeoCodeCalc;

public class GoogleLocationHandler implements LocationListener {
	
	private DangerMap dangerMap;
	private GoogleMap googleMap;
	private DangersDataSource ds;
	

	public GoogleLocationHandler(DangerMap dangerMap) {
		this.dangerMap = dangerMap;
		this.googleMap = dangerMap.getGoogleMap();
		this.ds = dangerMap.getDangerDataSource();
	}


	@Override
	public void onLocationChanged(Location location) {
		System.out.println("onLocationChanded:" + location);
        List<DangerItem> dis = ds.getAllDangerItems();
        
        double lat1 = location.getLatitude();
        double lng1 = location.getLongitude();
        
        if ( dangerMap.getButtonStatus() == OnMyLocationButtonClickHandler.BUTTON_ENABLED ) {
        	googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1, lng1),15));
        }
       
        for ( DangerItem di : dis ) {
        	double lat2 = di.getLatitude();
        	double lng2 = di.getLongitude();
        	double distance = GeoCodeCalc.CalcDistance(lat1, lng1, lat2, lng2);
        	
//        	System.out.println("distance : " + distance);
        	
        	if ( distance < 5.0 ) {
        		googleMap.addMarker(new MarkerOptions()
        			.position(new LatLng(lat2, lng2))
        			.title(di.getAddress())
        		);
        		
        	}
        }
	}

}
