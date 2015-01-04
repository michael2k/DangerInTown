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
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class DangerDataTest extends Activity implements DangerMap,
		ConnectionCallbacks, OnConnectionFailedListener,
		OnMyLocationButtonClickListener, OnMapClickListener,
		OnMapLongClickListener {

	private GoogleMap googleMap;
	private DangersDataSource dangerDataSource;
	private GoogleLocationHandler googleLocationHandler;
	private LocationClient locationClient;
	public static final int BUTTON_DISABLED = 0;
	public static final int BUTTON_ENABLED = 1;
	private int buttonStatus = BUTTON_ENABLED;

	// These settings are the same as the settings for the map. They will in
	// fact give you updates
	// at the maximal rates currently possible.
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(DangerDataTest.class.getName(), "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dangerDataSource = new DangersDataSource(this);
		dangerDataSource.open();

		List<DangerItem> dis = dangerDataSource.getAllDangerItems();
		if (dis.size() > 0) {
			Log.i(DangerDataTest.class.getName(), "dis.size() : " + dis.size());
		} else {
			readData();
		}

		// onMyLocationButtonClickHandler = new
		// OnMyLocationButtonClickHandler(this);

		setUpMapIfNeeded();
		setUpLocationClientIfNeeded();

		googleLocationHandler = new GoogleLocationHandler(this);
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instatiated the
		// map
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			if (googleMap != null) {
				setUpMap();
			}
		}
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
			DangerItem di = dangerDataSource.createDangerItem(danger);
			System.out.println(di);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.i(DangerDataTest.class.getName(), "onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.danger_data_test, menu);
		return true;
	}

	@Override
	protected void onPause() {
		Log.i(DangerDataTest.class.getName(), "onPause");
		dangerDataSource.close();
		if (locationClient != null) {
			locationClient.disconnect();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.i(DangerDataTest.class.getName(), "onResume");
		super.onResume();
		dangerDataSource.open();
		// setUpMapIfNeeded();
		// setUpLocationClientIfNeeded();
		locationClient.connect();
	}

	private void setUpLocationClientIfNeeded() {
		Log.i(DangerDataTest.class.getName(), "setUpLocationClientIfNeeded");
		if (locationClient == null) {
			locationClient = new LocationClient(getApplicationContext(), this,
					this); // ConnectionCallbacks, OnConnectionFailedListener
		}
		// locationClient.connect();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(DangerDataTest.class.getName(), "onConnected");

		locationClientUpdates();

	}

	private void locationClientUpdates() {

		if (buttonStatus == BUTTON_ENABLED) {
			locationClient.requestLocationUpdates(REQUEST,
					getGoogleLocationHandler()); // LocationListener
		}

	}

	private GoogleLocationHandler getGoogleLocationHandler() {
		return googleLocationHandler;
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.i(DangerDataTest.class.getName(), "onConnectionFailed");
	}

	@Override
	public void onDisconnected() {
		Log.i(DangerDataTest.class.getName(), "onDisconnected");
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

		Log.i(DangerDataTest.class.getName(), "buttonStatus : " + buttonStatus);

		if (buttonStatus == BUTTON_ENABLED) {
			Log.i(DangerDataTest.class.getName(),
					"buttonStatus : BUTTON_ENABLED");
			if (locationClient != null && locationClient.isConnected()) {
				locationClientUpdates();
			}
		} else {
			Log.i(DangerDataTest.class.getName(),
					"buttonStatus : BUTTON_DISABLED");
			if (locationClient != null && locationClient.isConnected()) {
				locationClient
						.removeLocationUpdates(getGoogleLocationHandler());
			}
		}
	}

	@Override
	public boolean onMyLocationButtonClick() {
		Log.i(DangerDataTest.class.getName(), "onMyLocationButtonClick");

		buttonStatus = (buttonStatus + 1) % 2;

		setButtonStatus(buttonStatus);

		return false;
	}

	@Override
	public void onMapClick(LatLng point) {
		Log.i(DangerDataTest.class.getName(), "onMapClick");

		setButtonStatus(BUTTON_DISABLED);

	}

	@Override
	public void onMapLongClick(LatLng point) {
		Log.i(DangerDataTest.class.getName(), "onMapLongClick");

		setButtonStatus(BUTTON_DISABLED);

		getGoogleLocationHandler().processLocationChanged(point.latitude,
				point.longitude);

	}

}
