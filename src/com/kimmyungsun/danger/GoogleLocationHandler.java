package com.kimmyungsun.danger;

import java.util.List;

import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.CircleOptionsCreator;
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
		Log.i(GoogleLocationHandler.class.getName(), "onLocationChanded:" + location);
		
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        
        processLocationChanged(lat, lng);
        
	}


	public void processLocationChanged(double lat, double lng) {
		Log.i(GoogleLocationHandler.class.getName(), "processLocationChanged");
		
		googleMap.clear();
		
        List<DangerItem> dis = ds.getAllDangerItems();
        
        
        if ( dangerMap.getButtonStatus() == DangerDataTest.BUTTON_ENABLED ) {
        	googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng),12));
        }
        
        CircleOptions co = new CircleOptions();
        co.center(new LatLng(lat, lng));
        co.strokeWidth(1.0f);
        co.radius(5000);
        co.fillColor(Color.argb(30, 50, 0, 0));
        
        googleMap.addCircle(co);
        
        co.radius(3000);
        co.fillColor(Color.argb(40, 100, 0, 0));
        
        googleMap.addCircle(co);
        
        co.radius(1000);
        co.fillColor(Color.argb(50, 200, 0, 0));
        
        googleMap.addCircle(co);
        
        co.radius(500);
        
        googleMap.addCircle(co);
        
       
        for ( DangerItem di : dis ) {
        	double lat2 = di.getLatitude();
        	double lng2 = di.getLongitude();
        	double distance = GeoCodeCalc.CalcDistance(lat, lng, lat2, lng2);
        	
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
