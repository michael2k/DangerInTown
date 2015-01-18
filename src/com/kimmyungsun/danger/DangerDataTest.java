package com.kimmyungsun.danger;

import java.util.ArrayList;
import java.util.List;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

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
import com.kimmyungsun.danger.constanst.IDangerConstants;
import com.kimmyungsun.danger.datamng.YPYNetUtils;
import com.kimmyungsun.danger.geocode.GeoCodeCalc;
import com.kimmyungsun.danger.object.PlaceObject;
import com.kimmyungsun.danger.object.ResponsePlaceResult;
import com.kimmyungsun.danger.provider.DangerSuggestionProvider;
import com.kimmyungsun.danger.provider.DangersDataSource;
import com.kimmyungsun.danger.provider.MySuggestionProvider;

public class DangerDataTest extends DBFragmentActivity implements OnMapReadyCallback, LocationListener, IDangerConstants,
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
	
	private Handler handler = new Handler();
	
	private LatLng mLatLng;
	
	private EditText txtSearch;
	private Button btnSearch;
	
	private List<Company> nearCompanys = new ArrayList<Company>();
	
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
		
		
	    Intent intent  = getIntent();

	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	        String query = intent.getStringExtra(SearchManager.QUERY);
	        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
	                DangerSuggestionProvider.AUTHORITY, DangerSuggestionProvider.MODE);
	        suggestions.saveRecentQuery(query, null);
	    }


		((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
		
//		SearchView searchView = (SearchView) findViewById(R.id.searchView);
//		searchView.setSubmitButtonEnabled(true);
//		searchView.setBackgroundColor(Color.WHITE);
		
//		txtSearch = ((EditText) findViewById(R.id.txtSearch));
//		btnSearch = ((Button) findViewById(R.id.btnSearch));

		dangerDataSource = new DangersDataSource(this);

		setUpGoogleApiClient();
		
//		alertShow();
		
		setupSearchView();
	}

	private void setUpMap() {
		googleMap.setMyLocationEnabled(true);
		googleMap.setOnMyLocationButtonClickListener(this);
		googleMap.setOnMapClickListener(this);
		googleMap.setOnMapLongClickListener(this);

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
//		dangerDataSource.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
//		dangerDataSource.open();
//		googleApiClient.connect();
		setUpGoogleApiClient();
	}
	
	

	private synchronized void setUpGoogleApiClient() {
		Log.i(TAG, "setUpGoogleApiClient");
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				if ( googleApiClient == null ) {
					googleApiClient = new GoogleApiClient.Builder(DangerDataTest.this)
					.addConnectionCallbacks(DangerDataTest.this)
					.addOnConnectionFailedListener(DangerDataTest.this)
					.addApi(LocationServices.API)
					.build();
					googleApiClient.connect();
				}
				
			}
		});
	
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(TAG, "onConnected");

		locationClientUpdates();

	}

	private void locationClientUpdates() {

		handler.post(new Runnable() {
			
			@Override
			public void run() {
				if (buttonStatus == BUTTON_ENABLED) {
					LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, REQUEST, DangerDataTest.this); // LocationListener
				} 
				
			}
		});

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.i(TAG, "onConnectionFailed");
	}

//	@Override
	public void onDisconnected() {
		Log.i(TAG, "onDisconnected");
	}

	public void setButtonStatus(int buttonStatus) {
		this.buttonStatus = buttonStatus;

		Log.i(TAG, "buttonStatus : " + buttonStatus);

		if (buttonStatus == BUTTON_ENABLED) {
			Log.i(TAG,
					"buttonStatus : BUTTON_ENABLED");
			if (googleApiClient != null ) {
				googleApiClient.connect();
			}
		} else {
			Log.i(TAG,
					"buttonStatus : BUTTON_DISABLED");
			if (googleApiClient != null && googleApiClient.isConnected()) {
				LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
				googleApiClient.disconnect();
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

//		SearchView searchView = (SearchView) findViewById(R.id.searchView);
//		searchView.setIconified(true);
//		searchView.setBackgroundColor(Color.WHITE);
//		searchView.setVisibility(View.VISIBLE);

		setButtonStatus(BUTTON_DISABLED);

	}

	@Override
	public void onMapLongClick(LatLng point) {
		Log.i(TAG, "onMapLongClick");

		setButtonStatus(BUTTON_DISABLED);
		
		mLatLng = point;

		googleMap.clear();
		
		processLocationChanged(point.latitude, point.longitude);

		googleMap.addMarker(new MarkerOptions()
		.position(point)
		.title("현재위치")).showInfoWindow();;
		

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
		Log.i(TAG, "onConnectionSuspended");
		
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.i(TAG, "onLocationChanged:" + location);
		
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        
        Log.i(TAG, "lat:" + lat + ", lng:" + lng);
        
        mLatLng = new LatLng(lat, lng);
        
		googleMap.clear();
		
        processLocationChanged(lat, lng);
        
	}

	public void processLocationChanged(double lat, double lng) {
		Log.i(TAG, "processLocationChanged");
		
        List<Company> companys = dangerDataSource.getAllCompanys();
//        List<Company> companys = getContentResolver().
        
        
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
        
       
        nearCompanys.clear();
        
        for ( Company company : companys ) {
        	double lat2 = company.getLatitude();
        	double lng2 = company.getLongitude();
        	double distance = GeoCodeCalc.CalcDistance(lat, lng, lat2, lng2);
        	
//        	System.out.println("distance : " + distance);
        	
        	if ( distance < 5.0 ) {
        		Marker m = googleMap.addMarker(new MarkerOptions()
		        			.position(new LatLng(lat2, lng2))
		        			.snippet(company.getAddress())
		        			.title(company.getCompanyName())
        					);
        		m.showInfoWindow();
        		
        		List<Matter> matters = dangerDataSource.searchMatters(company);
        		for( Matter matter : matters ) {
        			company.addMatter(matter);
        		}
        		nearCompanys.add(company);
        		
//        		Log.i(TAG, nearCompanys.toString());
        		
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
		
		List<Company> companys = dangerDataSource.searchCompanys(searchString);
		Log.i(TAG, "search result list size:" + companys.size());
		for ( Company company : companys) {
			Log.i(TAG, "company:" + company.toString());
			
		}
		
		AsyncTask.execute(new  Runnable() {
			
			@Override
			public void run() {
				ResponsePlaceResult rpr = YPYNetUtils.getListPlacesBaseOnText(DangerDataTest.this, mLatLng.longitude, mLatLng.latitude, txtSearch.getText().toString());
				
				Log.i(TAG, rpr.toString());
				
				List<PlaceObject> places = rpr.getListPlaceObjects();
				for ( PlaceObject po : places ) {
					Log.i(TAG, po.getName());
				}
				
			}
		});
		
	}


	@Override
	public boolean onSearchRequested() {
		// TODO Auto-generated method stub
		return super.onSearchRequested();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.i(TAG, "onNewIntent");
		if (ContactsContract.Intents.SEARCH_SUGGESTION_CLICKED.equals(intent.getAction())) {
			//handles suggestion clicked query
//			String displayName = getDisplayNameForContact(intent);
//			resultText.setText(displayName);
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// handles a search query
			String query = intent.getStringExtra(SearchManager.QUERY);
//			resultText.setText("should search for query: '" + query + "'...");
		}
	}

	private void setupSearchView() {
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) findViewById(R.id.searchView);
		SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
		searchView.setSearchableInfo(searchableInfo);
		searchView.setOnQueryTextListener(onQueryTextHandler);
		
	}
	
	public static OnQueryTextListener onQueryTextHandler = new OnQueryTextListener() {
		
		@Override
		public boolean onQueryTextSubmit(String query) {
			Log.i(TAG, "onQueryTextSubmit( " + query + " )");
			return false;
		}
		
		@Override
		public boolean onQueryTextChange(String newText) {
			Log.i(TAG, "onQueryTextChange( " + newText + " )");
			return false;
		}
	};

}
