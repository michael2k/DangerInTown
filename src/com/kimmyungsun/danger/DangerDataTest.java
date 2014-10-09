package com.kimmyungsun.danger;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kimmyungsun.geocode.GeoCodeCalc;


public class DangerDataTest extends Activity 
	implements
	ConnectionCallbacks, OnConnectionFailedListener, LocationListener, OnMyLocationButtonClickListener
{
	
	private GoogleMap mMap;
	private DangersDataSource ds;
    private LocationClient mLocationClient;
   
	// These settings are the same as the settings for the map. They will in fact give you updates
	// at the maximal rates currently possible.
	private static final LocationRequest REQUEST = LocationRequest.create()
	        .setInterval(5000)         // 5 seconds
	        .setFastestInterval(16)    // 16ms = 60fps
	        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ds = new DangersDataSource(this);
        ds.open();
        
        List<DangerItem> dis = ds.getAllDangerItems();
        if ( dis.size() > 0 ) {
        	System.out.println("dis.size() : " + dis.size());
        } else {
        	readData();
        }
        
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
    	// Do a null check to confirm that we have not already instatiated the map
    	if ( mMap == null ) {
    		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    		if ( mMap != null ) {
    			setUpMap();
    		}
    	}
	}


	private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
	}


	private void readData() {
    	System.out.println("read!!");
    	Resources res = getResources();
    	String[] dangers = res.getStringArray(R.array.dangers);
    	for(String danger : dangers) {
//    		DangerItem di = DangerItem.newInstance(danger);
    		DangerItem di = ds.createDangerItem(danger);
    		System.out.println(di);
    	}
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.danger_data_test, menu);
        return true;
    }

    
	@Override
	protected void onPause() {
		super.onPause();
		ds.close();
        if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
	}

	@Override
	protected void onResume() {
		super.onResume();
		ds.open();
//		readData();
        setUpMapIfNeeded();
        setUpLocationClientIfNeeded();
        mLocationClient.connect();
	}


	private void setUpLocationClientIfNeeded() {
		Log.i(DangerDataTest.class.getName(), "setUpLocationClientIfNeeded");
	    if (mLocationClient == null) {
	        mLocationClient = new LocationClient( getApplicationContext(),
	                this,  // ConnectionCallbacks
	                this); // OnConnectionFailedListener
	    }
	}


	@Override
	public void onConnected(Bundle arg0) {
        mLocationClient.requestLocationUpdates(
                REQUEST,
                this);  // LocationListener
	}


	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
	}


	@Override
	public void onLocationChanged(Location location) {
		System.out.println("onLocationChanded:" + location);
        List<DangerItem> dis = ds.getAllDangerItems();
        
        double lat1 = location.getLatitude();
        double lng1 = location.getLongitude();
       
        for ( DangerItem di : dis ) {
        	double lat2 = di.getLatitude();
        	double lng2 = di.getLongitude();
        	double distance = GeoCodeCalc.CalcDistance(lat1, lng1, lat2, lng2);
        	
//        	System.out.println("distance : " + distance);
        	
        	if ( distance < 5.0 ) {
        		mMap.addMarker(new MarkerOptions()
        			.position(new LatLng(lat2, lng2))
        			.title(di.getAddress())
        		);
        		
        	}
        }
	}


	@Override
	public boolean onMyLocationButtonClick() {
		System.out.println("onMyLocationButtonClick");
		return false;
	}


	@Override
	public void onDisconnected() {
		System.out.println("onDisconnected");
	}





    
    
}
