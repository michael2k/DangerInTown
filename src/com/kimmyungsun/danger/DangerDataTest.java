package com.kimmyungsun.danger;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v4.view.MenuItemCompat;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.widget.SearchView;
//import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
//import android.widget.SearchView;
//import android.widget.SearchView.OnQueryTextListener;






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
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kimmyungsun.danger.constanst.IDangerConstants;
import com.kimmyungsun.danger.datamng.YPYNetUtils;
import com.kimmyungsun.danger.geocode.GeoCodeCalc;
import com.kimmyungsun.danger.object.PlaceObject;
import com.kimmyungsun.danger.object.ResponsePlaceResult;
import com.kimmyungsun.danger.provider.DangersDataSource;

public class DangerDataTest extends Activity implements OnMapReadyCallback, LocationListener, IDangerConstants,
		ConnectionCallbacks, OnConnectionFailedListener, CancelableCallback, OnCameraChangeListener,
		OnMyLocationButtonClickListener, OnMapClickListener, OnInfoWindowClickListener,
		OnMarkerClickListener,
		OnMapLongClickListener {
	
	private final static String TAG = DangerDataTest.class.getName();

	private GoogleMap googleMap;
	private DangersDataSource dangerDataSource;
	private GoogleApiClient googleApiClient;
	public static final int BUTTON_DISABLED = 0;
	public static final int BUTTON_ENABLED = 1;
	private int buttonStatus = BUTTON_ENABLED;
//	private int buttonStatus = BUTTON_DISABLED;
	
	private Handler handler = new Handler();
	
	private LatLng mLatLng;
	
	private EditText txtSearch;
	private Button btnSearch;
	
	private Marker markerClicked;
	
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
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
//		RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativeLayout1);
//		
//		Fragment fMap = getSupportFragmentManager().findFragmentById(R.id.map);
//		fMap.getView().getLayoutParams().height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
//		Fragment fMatter = getSupportFragmentManager().findFragmentById(R.id.fragment1);
		
//		Fragment fragment = 
		
		dangerDataSource = new DangersDataSource(this);

		setUpGoogleApiClient();
		
	}

	private void setUpMap() {
		
		googleMap.getUiSettings().setMapToolbarEnabled(false);
		googleMap.setMyLocationEnabled(true);
		googleMap.setOnMyLocationButtonClickListener(this);
		googleMap.setOnMapClickListener(this);
		googleMap.setOnMapLongClickListener(this);
		
        // Setting a custom info window adapter for the google map
        googleMap.setInfoWindowAdapter(new CompanyInfoWindowAdapter(this, dangerDataSource));
		googleMap.setOnInfoWindowClickListener(this);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnCameraChangeListener(this);
 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.d(TAG, "onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.danger_data_test, menu);
		
	    // Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    searchView.setMaxWidth(400);
	    
	    // Assumes current activity is the searchable activity
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//	    searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
	    
		searchView.setOnQueryTextListener(onQueryTextHandler);

		return true;
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
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
		Log.d(TAG, "onResume");
		super.onResume();
//		dangerDataSource.open();
//		googleApiClient.connect();
		setUpGoogleApiClient();
	}
	
	

	private synchronized void setUpGoogleApiClient() {
		Log.d(TAG, "setUpGoogleApiClient");
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
		Log.d(TAG, "onConnected");

		locationClientUpdates();

		MapFragment mf = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
		mf.getMapAsync(this);
		
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
		Log.d(TAG, "onConnectionFailed");
	}

//	@Override
	public void onDisconnected() {
		Log.d(TAG, "onDisconnected");
	}

	public void setButtonStatus(int buttonStatus) {
		this.buttonStatus = buttonStatus;

		Log.d(TAG, "buttonStatus : " + buttonStatus);

		if (buttonStatus == BUTTON_ENABLED) {
			Log.d(TAG,
					"buttonStatus : BUTTON_ENABLED");
			if (googleApiClient != null ) {
				googleApiClient.connect();
			}
		} else {
			Log.d(TAG,
					"buttonStatus : BUTTON_DISABLED");
			if (googleApiClient != null && googleApiClient.isConnected()) {
				LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
				googleApiClient.disconnect();
			}
		}
	}

	@Override
	public boolean onMyLocationButtonClick() {
		Log.d(TAG, "onMyLocationButtonClick");

		buttonStatus = (buttonStatus + 1) % 2;

		setButtonStatus(buttonStatus);

		return false;
	}

	@Override
	public void onMapClick(LatLng point) {
		Log.d(TAG, "onMapClick");

//		SearchView searchView = (SearchView) findViewById(R.id.searchView);
//		searchView.setIconified(true);
//		searchView.setBackgroundColor(Color.WHITE);
//		searchView.setVisibility(View.VISIBLE);

		setButtonStatus(BUTTON_DISABLED);

	}

	@Override
	public void onMapLongClick(LatLng point) {
		Log.d(TAG, "onMapLongClick");

		setButtonStatus(BUTTON_DISABLED);
		
		mLatLng = point;

		googleMap.clear();
		
		processLocationChanged(point.latitude, point.longitude);

		googleMap.addMarker(new MarkerOptions()
		.position(point)
		.title("현재위치"))
		.showInfoWindow();
		

	}
	
	

//	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		Log.d(TAG, "onMapReady");
		
		this.googleMap = googleMap;
		setUpMap();
		
	}

	@Override
	public void onConnectionSuspended(int cause) {
		Log.d(TAG, "onConnectionSuspended");
		
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(TAG, "onLocationChanged:" + location);
		
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        
        Log.d(TAG, "lat:" + lat + ", lng:" + lng);
        
        mLatLng = new LatLng(lat, lng);
        
		googleMap.clear();
		
        processLocationChanged(lat, lng);
        
        
//      if ( buttonStatus == BUTTON_ENABLED ) {
      googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng),12), this);
//      }

		View v = (View) findViewById(R.id.RelativeLayout1);
		if ( v.getVisibility() != View.VISIBLE) v.setVisibility(View.VISIBLE);
	}

	public void processLocationChanged(double lat, double lng) {
		Log.d(TAG, "processLocationChanged");
		
        List<Company> companys = dangerDataSource.getAllCompanys();
//        List<Company> companys = getContentResolver().
        
        
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
        
        addCompanyMarker(lat, lng, companys);
       
	}
	
	private List<Marker> addCompanyMarker(List<Company> companys ) {
		
		List<Marker> markers = new ArrayList<Marker>();
		
		nearCompanys.clear();
		
		for ( Company company : companys ) {
			
			markers.add( addCompanyMarker(company) );
			
			nearCompanys.add(company);
			
		}
		
		return markers;
		
	}
	
	private Marker addCompanyMarker ( Company company ) {
		
		Marker m = googleMap.addMarker(new MarkerOptions()
		.position(new LatLng(company.getLatitude(), company.getLongitude()))
		.snippet(String.valueOf(company.getId()))
//		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
		.title(company.getCompanyName())
				)
				;
//		m.showInfoWindow();
		
		List<Matter> matters = dangerDataSource.searchMatters(company);
		for( Matter matter : matters ) {
			company.addMatter(matter);
		}
		
		return m;
		
	}
	
	private void addCompanyMarker(double lat, double lng, List<Company> companys ) {
		
		nearCompanys.clear();
		
		for ( Company company : companys ) {
			
			double lat2 = company.getLatitude();
			double lng2 = company.getLongitude();
			double distance = GeoCodeCalc.CalcDistance(lat, lng, lat2, lng2);
			
//        	System.out.println("distance : " + distance);
			
			if ( distance < 5.0 ) {
				
				addCompanyMarker(company);
				nearCompanys.add(company);
				
//        		Log.i(TAG, nearCompanys.toString());
				
			}
		}
		
	}
	
	
	/*
	 * called when the user clicks the btnSearch button
	 */
	public void searchLocation(View view) {
		Log.d(TAG, "searchLocation");
		String searchString = txtSearch.getText().toString();
		Log.d(TAG, "searchString:" + searchString);
		
		List<Company> companys = dangerDataSource.searchCompanys(searchString);
		Log.d(TAG, "search result list size:" + companys.size());
		for ( Company company : companys) {
			Log.d(TAG, "company:" + company.toString());
			
		}
		
		AsyncTask.execute(new  Runnable() {
			
			@Override
			public void run() {
				ResponsePlaceResult rpr = YPYNetUtils.getListPlacesBaseOnText(DangerDataTest.this, mLatLng.longitude, mLatLng.latitude, txtSearch.getText().toString());
				
				Log.d(TAG, rpr.toString());
				
				List<PlaceObject> places = rpr.getListPlaceObjects();
				for ( PlaceObject po : places ) {
					Log.d(TAG, po.getName());
				}
				
			}
		});
		
	}


	@Override
	public boolean onSearchRequested() {
		Log.d(TAG, "onSearchRequested");
		return super.onSearchRequested();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.d(TAG, "onNewIntent");
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			//handles suggestion clicked query
			Log.d(TAG, "onNewIntent ACTION_VIEW intent[ " + intent.toString() + " ]");
			Cursor cursor = getContentResolver().query(intent.getData(), null, null, new String[] {intent.getData().getLastPathSegment()}, null);
			cursor.moveToFirst();
			Company company = Company.cursorToCompany(cursor);
			Log.d(TAG, "c :" + company.toString());
			
        	googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(company.getLatitude(), company.getLongitude()),12));
        	
        	addCompanyMarker(company);

			
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// handles a search query
			Log.d(TAG, "onNewIntent ACTION_SEARCH intent[ " + intent.toString() + " ]");
			String query = intent.getStringExtra(SearchManager.QUERY);
			Log.d(TAG, "onNewIntent ACTION_SEARCH query[ " + query + " ]");
			
			if ( query != null && !query.trim().isEmpty() ) {
				
				List<Company> companys = dangerDataSource.searchCompanys(query);
				
				if ( companys != null && companys.size() > 0 ) {
					
					Log.d(TAG, "onNewIntent ACTION_SEARCH : " + companys.toString());
					
					List<Marker> markers = addCompanyMarker(companys);
					
					LatLngBounds.Builder builder = LatLngBounds.builder();
					for(Marker m : markers){
						builder.include(m.getPosition());
					}
					LatLngBounds bounds = builder.build();
					googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));			
					
				}

			}
		}
	}

	public static OnQueryTextListener onQueryTextHandler = new OnQueryTextListener() {
		
		@Override
		public boolean onQueryTextSubmit(String query) {
			Log.d(TAG, "onQueryTextSubmit( " + query + " )");
			return false;
		}
		
		@Override
		public boolean onQueryTextChange(String newText) {
			Log.d(TAG, "onQueryTextChange( " + newText + " )");
			return false;
		}
	};

	@Override
	public boolean onMarkerClick(Marker marker) {
		Log.d(TAG, "onMarkerClick");
		
		setButtonStatus(BUTTON_DISABLED);
		
//		MapFragment mf = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
//		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(mf.getView().getLayoutParams());
//
//		if ( markerClicked != null && markerClicked.equals(marker) ) {
//			rlp.removeRule(RelativeLayout.ABOVE);
//			markerClicked = null;
//		} else {
//			if ( markerClicked == null ) {
//				CompanyInfoFragment fragment = new CompanyInfoFragment();
//				
////				rlp.addRule(RelativeLayout.ABOVE, R.id.fragment1);
//			}
//			markerClicked = marker;
//		}
//		mf.getView().setLayoutParams(rlp);
		
		
		return false;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Log.d(TAG, "onInfoWindowClick");
		
		Intent intent = new Intent(this, CompanyDetailsActivity.class);
		
		intent.putExtra(CompanyDetailsActivity.COMPANY_ID, marker.getSnippet());
		
		startActivity(intent);
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish() {
		Log.d(TAG, "onFinish");
		
		View v = (View) findViewById(R.id.RelativeLayout1);
		if ( v.getVisibility() != View.VISIBLE) v.setVisibility(View.VISIBLE);
	      
		
	}

	@Override
	public void onCameraChange(CameraPosition cameraPosition) {
		Log.d(TAG, "onCameraChange");
		
		View v = (View) findViewById(R.id.RelativeLayout1);
		if ( v.getVisibility() != View.VISIBLE) v.setVisibility(View.VISIBLE);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selected
		
		Intent intent = null;
		switch( item.getItemId() ) {
		case R.id.action_join:
			intent = new Intent(this, JoinActivity.class);
			
			startActivity(intent);
			
			return true;
		case R.id.action_settings:
			intent = new Intent(this, SettingsActivity.class);
			
			startActivity(intent);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
				
		}
	}

}
