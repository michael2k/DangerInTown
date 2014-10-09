package com.kimmyungsun.danger;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kimmyungsun.geocode.GeoCodeCalc;


public class DangerDataTest extends FragmentActivity 
	implements
	ConnectionCallbacks, OnConnectionFailedListener, LocationListener, com.google.android.gms.location.LocationListener, OnMyLocationButtonClickListener
	,LocationSource, LocationSource.OnLocationChangedListener
	, OnMapLongClickListener
{
	
	private GoogleMap mMap;
	private DangersDataSource ds;
    private LocationClient mLocationClient;
    private LocationManager locationManager;
    private final static long MIN_TIME = 400;
    private final static float MIN_DISTANCE = 1000;
   
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
    	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
	}


	private void setUpMap() {
		mMap.setOnMapLongClickListener(this);
		mMap.setLocationSource(this);
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
	    mLocationClient.getLastLocation();
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
       
        LatLng latLng = new LatLng(lat1, lng1);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
        
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


	@Override
	public void onMapLongClick(LatLng point) {
		System.out.println("onMapLongClick");
        Location location = new Location("LongPressLocationProvider");
        location.setLatitude(point.latitude);
        location.setLongitude(point.longitude);
        location.setAccuracy(100);
        onLocationChanged(location);
	}


//	@Override
	public void activate(OnLocationChangedListener listener) {
		System.out.println("activate");
	}


//	@Override
	public void deactivate() {
		System.out.println("deactivate");
	}


@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	// TODO Auto-generated method stub
	
}


@Override
public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
	
}


@Override
public void onProviderDisabled(String provider) {
	// TODO Auto-generated method stub
	
}


    
    
}
