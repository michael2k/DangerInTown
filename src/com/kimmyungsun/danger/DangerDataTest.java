package com.kimmyungsun.danger;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
//import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kimmyungsun.geocode.GeoCodeCalc;

public class DangerDataTest extends Activity implements DangerMap, OnMapReadyCallback, LocationListener,
		ConnectionCallbacks, OnConnectionFailedListener, 
		OnMyLocationButtonClickListener, OnMapClickListener,
		OnMapLongClickListener {
	
	private final static String TAG = DangerDataTest.class.getName();

	private GoogleMap googleMap;
	private DangersDataSource dangerDataSource;
	private GoogleApiClient googleApiClient;
	public static final int BUTTON_DISABLED = 0;
	public static final int BUTTON_ENABLED = 1;
	private int buttonStatus = BUTTON_ENABLED;
	
	private EditText txtSearch;
	private Button btnSearch;
	
	// These settings are the same as the settings for the map. They will in
	// fact give you updates
	// at the maximal rates currently possible.
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		
		setContentView(R.layout.activity_main);

		((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
		
		txtSearch = ((EditText) findViewById(R.id.txtSearch));
		btnSearch = ((Button) findViewById(R.id.btnSearch));

		dangerDataSource = new DangersDataSource(this);
		dangerDataSource.open();

		List<DangerItem> dis = dangerDataSource.getAllDangerItems();
		if (dis.size() > 0) {
			Log.i(TAG, "dis.size() : " + dis.size());
		} else {
			readData();
		}

		setUpGoogleApiClient();
		
	}

	private void setUpMap() {
		googleMap.setMyLocationEnabled(true);
		googleMap.setOnMyLocationButtonClickListener(this);
		googleMap.setOnMapClickListener(this);
		googleMap.setOnMapLongClickListener(this);

	}

	private void readData() {
		System.out.println("read!!");
		Resources res = getResources();
		String[] dangers = res.getStringArray(R.array.dangers);
		for (String danger : dangers) {
			// DangerItem di = DangerItem.newInstance(danger);
			Log.i(TAG,danger);
			DangerItem di = dangerDataSource.createDangerItem(danger);
			Log.i(TAG,di.toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.i(TAG, "onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.danger_data_test, menu);
		return true;
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "onPause");
		if (googleApiClient != null && googleApiClient.isConnected()) {
			LocationServices.FusedLocationApi
					.removeLocationUpdates(googleApiClient, this);
			googleApiClient.disconnect();
		}
		dangerDataSource.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
		dangerDataSource.open();
		googleApiClient.connect();
	}

	private synchronized void setUpGoogleApiClient() {
		Log.i(TAG, "setUpGoogleApiClient");
	
		if ( googleApiClient == null ) {
			googleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(TAG, "onConnected");

		locationClientUpdates();

	}

	private void locationClientUpdates() {

		if (buttonStatus == BUTTON_ENABLED) {
			LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, REQUEST,this); // LocationListener
		} 

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.i(TAG, "onConnectionFailed");
	}

//	@Override
	public void onDisconnected() {
		Log.i(TAG, "onDisconnected");
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

		Log.i(TAG, "buttonStatus : " + buttonStatus);

		if (buttonStatus == BUTTON_ENABLED) {
			Log.i(TAG,
					"buttonStatus : BUTTON_ENABLED");
			if (googleApiClient != null && googleApiClient.isConnected()) {
				locationClientUpdates();
			}
		} else {
			Log.i(TAG,
					"buttonStatus : BUTTON_DISABLED");
			if (googleApiClient != null && googleApiClient.isConnected()) {
				LocationServices.FusedLocationApi
						.removeLocationUpdates(googleApiClient, this);
			}
		}
	}

	@Override
	public boolean onMyLocationButtonClick() {
		Log.i(TAG, "onMyLocationButtonClick");

		buttonStatus = (buttonStatus + 1) % 2;

		setButtonStatus(buttonStatus);

		return false;
	}

	@Override
	public void onMapClick(LatLng point) {
		Log.i(TAG, "onMapClick");

		setButtonStatus(BUTTON_DISABLED);

	}

	@Override
	public void onMapLongClick(LatLng point) {
		Log.i(TAG, "onMapLongClick");

		setButtonStatus(BUTTON_DISABLED);

		googleMap.clear();
		
		googleMap.addMarker(new MarkerOptions()
		.position(point)
		.title("현재위치"));

		processLocationChanged(point.latitude, point.longitude);

	}
	
	

//	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		Log.i(TAG, "onMapReady");
		
		this.googleMap = googleMap;
		setUpMap();
		
	}

	@Override
	public void onConnectionSuspended(int cause) {
		Log.i(TAG, "onMapReady");
		
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.i(TAG, "onLocationChanded:" + location);
		
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        
        Log.i(TAG, "lat:" + lat + ", lng:" + lng);
        
		googleMap.clear();
		
        processLocationChanged(lat, lng);
        
	}

	public void processLocationChanged(double lat, double lng) {
		Log.i(TAG, "processLocationChanged");
		
        List<DangerItem> dis = dangerDataSource.getAllDangerItems();
        
        
        if ( buttonStatus == BUTTON_ENABLED ) {
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
        		Marker m = googleMap.addMarker(new MarkerOptions()
		        			.position(new LatLng(lat2, lng2))
		        			.snippet(di.getAddress())
		        			.title(di.getCompanyName())
        					);
        		m.showInfoWindow();
        		
        	}
        }
	}
	
	
	/*
	 * called when the user clicks the btnSearch button
	 */
	public void searchLocation(View view) {
		Log.i(TAG, "searchLocation");
		String searchString = txtSearch.getText().toString();
		Log.i(TAG, "searchString:" + searchString);
		
		List<DangerItem> list = dangerDataSource.searchDangerItems(searchString);
		Log.i(TAG, "search result list size:" + list.size());
		for ( DangerItem di : list) {
			Log.i(TAG, "di:" + di.toString());
			
		}
		
	}

}
