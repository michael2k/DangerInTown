package com.kimmyungsun.danger;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
//import android.widget.SearchView;
//import android.widget.SearchView.OnQueryTextListener;






import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;

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
		OnMarkerClickListener, // OnDrawerScrollListener, OnDrawerOpenListener, OnDrawerCloseListener,
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
	
	private Marker markerSelected;
	private Intent oldIntent;
	
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
//		Fragment fMatter = getFragmentManager().findFragmentById(R.id.fragment1);
		
//		Fragment fragment = 
		
//		SlidingDrawer slidingDrawer = ( SlidingDrawer ) findViewById(R.id.slidingCompanyDetails);
//		slidingDrawer.setOnDrawerScrollListener(this);
//		slidingDrawer.setOnDrawerOpenListener(this);
//		slidingDrawer.setOnDrawerCloseListener(this);
		
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
//        googleMap.setInfoWindowAdapter(new CompanyInfoWindowAdapter(this, dangerDataSource));
//		googleMap.setOnInfoWindowClickListener(this);
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
		
//		if ( googleMap != null && mLatLng != null ) {
//			processLocationChanged(mLatLng.latitude, mLatLng.longitude);
//			if ( markerSelected != null ) {
//				onMarkerClick(markerSelected);
//			}
//		}
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
		markerSelected = null;
		
		processLocationChanged(point.latitude, point.longitude);

//		googleMap.addMarker(new MarkerOptions()
//		.position(point)
//		.title("현재위치"))
//		.showInfoWindow();
		
		oldIntent = null;

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
		markerSelected = null;
		
        processLocationChanged(lat, lng);
        
        
//      if ( buttonStatus == BUTTON_ENABLED ) {
      googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng),12), this);
//      }

		View v = (View) findViewById(R.id.dangerMainLayout);
		if ( v.getVisibility() != View.VISIBLE) v.setVisibility(View.VISIBLE);
		
		oldIntent = null;
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
        
        co.radius(2000);
        co.fillColor(Color.argb(40, 100, 0, 0));
        
        googleMap.addCircle(co);
        
//        co.radius(1000);
//        co.fillColor(Color.argb(50, 200, 0, 0));
//        
//        googleMap.addCircle(co);
        
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
		
		List<Matter> matters = dangerDataSource.searchMatters(company);
		for( Matter matter : matters ) {
			company.addMatter(matter);
		}

		Marker m = googleMap.addMarker(new MarkerOptions()
		.position(new LatLng(company.getLatitude(), company.getLongitude()))
		.snippet(String.valueOf(company.getId()))
		.icon(BitmapDescriptorFactory.fromResource(Company.getIconType(matters, 0)))
		.title(company.getCompanyName())
				)
				;
//		m.showInfoWindow();
		
		return m;
		
	}
	
	private void addCompanyMarker(double lat, double lng, List<Company> companys ) {
		
		nearCompanys.clear();
		
		Marker shortMarker = null;
		double shortDistance = 5.0;
		
		for ( Company company : companys ) {
			
			double lat2 = company.getLatitude();
			double lng2 = company.getLongitude();
			double distance = GeoCodeCalc.CalcDistance(lat, lng, lat2, lng2);
			
//        	System.out.println("distance : " + distance);
			
			if ( distance < 5.0 ) {
				
				Marker marker = addCompanyMarker(company);
				nearCompanys.add(company);
				
				if ( distance < shortDistance ) {
					shortDistance = distance;
					shortMarker = marker;
				}
				
//        		Log.i(TAG, nearCompanys.toString());
				
			}
		}
		
//		googleMap.moveCamera(CameraUpdateFactory.newLatLng(shortMarker.getPosition()));
		
		onMarkerClick(shortMarker);
		
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
		
		if ( googleMap != null ) {
			googleMap.clear();
		}
		
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			//handles suggestion clicked query
			Log.d(TAG, "onNewIntent ACTION_VIEW intent[ " + intent.toString() + " ]");
        	
			handleSuggestionQuery(intent);
			
        	
        	oldIntent = intent;
			
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// handles a search query
			Log.d(TAG, "onNewIntent ACTION_SEARCH intent[ " + intent.toString() + " ]");
			
			handleSearchQuery(intent);
			
			oldIntent = intent;
			
		} else {
//			Log.d(TAG, nearCompanys.toString());
			Log.d(TAG, markerSelected.toString());
			Log.d(TAG, mLatLng.toString());
			
			String companyId = markerSelected.getSnippet();
			markerSelected = null;
			Company c = dangerDataSource.getCompany(Long.parseLong(companyId));
			
			Marker m  = addCompanyMarker(c);
			
			if ( oldIntent != null ) {
				if (Intent.ACTION_VIEW.equals(oldIntent.getAction())) {
					handleSuggestionQuery(oldIntent);
				} else if (Intent.ACTION_SEARCH.equals(oldIntent.getAction())) {
					handleSearchQuery(oldIntent);
				}
			} else {
				processLocationChanged(mLatLng.latitude, mLatLng.longitude);
			}
			
			onMarkerClick(m);
		}
	}
	
	protected void handleSuggestionQuery(Intent intent) {
		
		Cursor cursor = getContentResolver().query(intent.getData(), null, null, new String[] {intent.getData().getLastPathSegment()}, null);
		cursor.moveToFirst();
		Company company = Company.cursorToCompany(cursor);
		Log.d(TAG, "c :" + company.toString());
		
    	googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(company.getLatitude(), company.getLongitude()),12));
    	
    	Marker m = addCompanyMarker(company);
		
    	markerSelected = null;
    	onMarkerClick(m);
    	
	}
	
	protected void handleSearchQuery(Intent intent ) {
		Log.d(TAG, "handleSearchQuery");
		
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
				
				Marker m = markers.get(0);
				
				markerSelected = null;
				onMarkerClick(m);
				
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

		
		if ( markerSelected != null ) {
			List<Matter> matters = dangerDataSource.searchMatters(markerSelected.getSnippet());
			markerSelected.setIcon(BitmapDescriptorFactory.fromResource(Company.getIconType(matters, 0)));
		}
		
//		CompanyInfoFragment cif = new CompanyInfoFragment();
//		Bundle bundle = new Bundle();
//		bundle.putString(CompanyInfoFragment.COMPANY_ID, marker.getSnippet());
//		cif.setArguments(bundle);
		
		CompanyInfoFragment cif = ( CompanyInfoFragment ) getFragmentManager().findFragmentById(R.id.companyInfoFragment);
//		RelativeLayout dangerMainLayout = ( RelativeLayout ) findViewById(R.id.dangerMainLayout);
//		FragmentManager fm = getFragmentManager();
//		FragmentTransaction ft = fm.beginTransaction();
//		ft.add(R.id.dangerMainLayout, cif);
//		ft.commit();
		
		cif.updateCompanyInfo(marker.getSnippet());
		
//		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams ) cif.getView().getLayoutParams();
//		params.height = 300;
//		
//		cif.getView().setLayoutParams(params);
		
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
		
		List<Matter> matters = dangerDataSource.searchMatters(marker.getSnippet());
		marker.setIcon(BitmapDescriptorFactory.fromResource(Company.getIconType(matters, 1)));
		markerSelected = marker;
		
		return true;
	}
	
	public RelativeLayout.LayoutParams fetchLayoutParams() {
	     RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                 LayoutParams.MATCH_PARENT, 200);

	     // We can add any rule available for RelativeLayout and hence can position accordingly
	     params.addRule(RelativeLayout.BELOW, R.id.map);
	     params.addRule(RelativeLayout.ALIGN_BOTTOM);
	     return params;
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
		
		View v = (View) findViewById(R.id.dangerMainLayout);
		if ( v.getVisibility() != View.VISIBLE) v.setVisibility(View.VISIBLE);
	      
		
	}

	@Override
	public void onCameraChange(CameraPosition cameraPosition) {
		Log.d(TAG, "onCameraChange");
		
		View v = (View) findViewById(R.id.dangerMainLayout);
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

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d(TAG, "onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
		mLatLng = savedInstanceState.getParcelable("mLatLng");
		buttonStatus = savedInstanceState.getInt("button_status");
		
		String companyIdSelected = savedInstanceState.getString("companyId");
		
		long[] companyIds = savedInstanceState.getLongArray("companyIds");
		if ( companyIds != null ) {
			for ( int i = 0; i < companyIds.length; i++ ) {
				Company c = dangerDataSource.getCompany(companyIds[i]);
				Marker m = addCompanyMarker(c);
				if ( companyIdSelected != null && companyIdSelected.isEmpty() ) {
					long companyId = Long.parseLong(companyIdSelected);
					if ( c.getId() == companyId) {
						onMarkerClick(m);
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);

		long[] companyIds = new long[nearCompanys.size()];
		for( int i = 0; i < companyIds.length; i++) {
			companyIds[i] = nearCompanys.get(i).getId();
		}
		outState.putLongArray("companyIds", companyIds);

		if ( markerSelected != null ) {
			outState.putString("companyId", markerSelected.getSnippet());
		}
		
		outState.putInt("button_status", buttonStatus);
		outState.putParcelable("mLatLng", mLatLng);
		
	}

//	@Override
//	public void onScrollEnded() {
//		SlidingDrawer slidingDrwawer = ( SlidingDrawer ) findViewById(R.id.slidingCompanyDetails);
//		int height = slidingDrwawer.getHeight();
//		Fragment fMap = getFragmentManager().findFragmentById(R.id.map);
//		int mapHeight = fMap.getView().getHeight();
//		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fMap.getView().getLayoutParams();
//		params.height = mapHeight - height;
//		fMap.getView().setLayoutParams(params);
//
//	}
//
//	@Override
//	public void onScrollStarted() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onDrawerClosed() {
//		Fragment fMap = getFragmentManager().findFragmentById(R.id.map);
//		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fMap.getView().getLayoutParams();
//		params.height = android.widget.FrameLayout.LayoutParams.MATCH_PARENT;
//		fMap.getView().setLayoutParams(params);
//		
//	}
//
//	@Override
//	public void onDrawerOpened() {
//		SlidingDrawer slidingDrwawer = ( SlidingDrawer ) findViewById(R.id.slidingCompanyDetails);
//		Button handle = ( Button ) findViewById(R.id.handle);
//		int height = slidingDrwawer.getHeight() - handle.getHeight();
//		Fragment fMap = getFragmentManager().findFragmentById(R.id.map);
//		int mapHeight = fMap.getView().getHeight();
//		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fMap.getView().getLayoutParams();
//		params.height = mapHeight - height;
//		fMap.getView().setLayoutParams(params);
//		
//	}

}
