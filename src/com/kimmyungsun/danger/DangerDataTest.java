package com.kimmyungsun.danger;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
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
import com.google.android.gms.maps.MapFragment;


public class DangerDataTest extends Activity 
	implements DangerMap, ConnectionCallbacks, OnConnectionFailedListener
{
	
	private GoogleMap googleMap;
	private DangersDataSource dangerDataSource;
	private GoogleLocationHandler googleLocationHandler;
	private OnMyLocationButtonClickHandler onMyLocationButtonClickHandler;
    private LocationClient locationClient;
    private int buttonStatus = OnMyLocationButtonClickHandler.BUTTON_ENABLED;
   
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
        
        
        dangerDataSource = new DangersDataSource(this);
        dangerDataSource.open();
        
        List<DangerItem> dis = dangerDataSource.getAllDangerItems();
        if ( dis.size() > 0 ) {
        	System.out.println("dis.size() : " + dis.size());
        } else {
        	readData();
        }
        
        setUpMapIfNeeded();
        
        googleLocationHandler = new GoogleLocationHandler(this);
        onMyLocationButtonClickHandler = new OnMyLocationButtonClickHandler(this);
    }


    private void setUpMapIfNeeded() {
    	// Do a null check to confirm that we have not already instatiated the map
    	if ( googleMap == null ) {
    		googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    		if ( googleMap != null ) {
    			setUpMap();
    		}
    	}
	}


	private void setUpMap() {
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickHandler);
	}


	private void readData() {
    	System.out.println("read!!");
    	Resources res = getResources();
    	String[] dangers = res.getStringArray(R.array.dangers);
    	for(String danger : dangers) {
//    		DangerItem di = DangerItem.newInstance(danger);
    		DangerItem di = dangerDataSource.createDangerItem(danger);
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
		dangerDataSource.close();
        if (locationClient != null) {
            locationClient.disconnect();
        }
	}

	@Override
	protected void onResume() {
		super.onResume();
		dangerDataSource.open();
        setUpMapIfNeeded();
        setUpLocationClientIfNeeded();
        locationClient.connect();
	}


	private void setUpLocationClientIfNeeded() {
		Log.i(DangerDataTest.class.getName(), "setUpLocationClientIfNeeded");
	    if (locationClient == null) {
	        locationClient = new LocationClient( getApplicationContext(),
	                this,  // ConnectionCallbacks
	                this); // OnConnectionFailedListener
	    }
	}


	@Override
	public void onConnected(Bundle arg0) {
        locationClient.requestLocationUpdates(
                REQUEST,
                getGoogleLocationHandler());  // LocationListener
	}


	private LocationListener getGoogleLocationHandler() {
		return googleLocationHandler;
	}


	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		
	}


	@Override
	public void onDisconnected() {
		System.out.println("onDisconnected");
	}


	@Override
	public GoogleMap getGoogleMap() {
		return googleMap;
	}


	@Override
	public DangersDataSource getDangerDataSource() {
		return dangerDataSource;
	}


	@Override
	public int getButtonStatus() {
		return buttonStatus;
	}


	@Override
	public void setButtonStatus(int buttonStatus) {
		this.buttonStatus = buttonStatus;
		
		if ( buttonStatus == OnMyLocationButtonClickHandler.BUTTON_ENABLED ) {
			locationClient.req
		}
	}





    
    
}
