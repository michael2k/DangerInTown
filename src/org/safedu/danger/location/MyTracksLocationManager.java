package org.safedu.danger.location;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
//import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
//import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * My Tracks Location Manager. Applies Google location settings before allowing
 * access to {@link LocationManager}.
 * 
 * @author DoBao
 */
public class MyTracksLocationManager {

	public static final String TAG = MyTracksLocationManager.class.getSimpleName();
	
	private final Handler handler;
//	private final LocationClient locationClient;
	private final GoogleApiClient googleApiClient;

	private LocationListener requestLastLocation;
	private LocationListener requestLocationUpdates;
	private float requestLocationUpdatesDistance;
	private long requestLocationUpdatesTime;
	
	
	public MyTracksLocationManager(Context context, Looper looper, boolean enableLocaitonClient) {
		this.handler = new Handler(looper);
		if (enableLocaitonClient) {
//			locationClient = new LocationClient(context, connectionCallbacks, onConnectionFailedListener);
			googleApiClient = new GoogleApiClient.Builder(context)
			.addConnectionCallbacks(connectionCallbacks)
			.addOnConnectionFailedListener(onConnectionFailedListener)
			.addApi(LocationServices.API)
			.build();
			googleApiClient.connect();
		}
		else {
			googleApiClient = null;
		}
	}
	
	private final ConnectionCallbacks connectionCallbacks = new ConnectionCallbacks() {

		@Override
		public void onConnected(Bundle bunlde) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (requestLastLocation != null && googleApiClient.isConnected()) {
						requestLastLocation.onLocationChanged(LocationServices.FusedLocationApi.getLastLocation(googleApiClient));
						requestLastLocation = null;
					}
					if (requestLocationUpdates != null && googleApiClient.isConnected()) {
						LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(requestLocationUpdatesTime)
								.setFastestInterval(requestLocationUpdatesTime).setSmallestDisplacement(requestLocationUpdatesDistance);
						LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, requestLocationUpdates, handler.getLooper());
					}
				}
			});
		}

		@Override
		public void onConnectionSuspended(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};

	private final OnConnectionFailedListener onConnectionFailedListener = new OnConnectionFailedListener() {
		@Override
		public void onConnectionFailed(ConnectionResult connectionResult) {
			
		}
	};
	

	/**
	 * Closes the {@link MyTracksLocationManager}.
	 */
	public void close() {
		if (googleApiClient != null) {
			googleApiClient.disconnect();
		}
	}

	/**
	 * Request last location.
	 * 
	 * @param locationListener
	 *            location listener
	 */
	public void requestLastLocation(final LocationListener locationListener) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				requestLastLocation = locationListener;
				connectionCallbacks.onConnected(null);
			}
		});
	}

	/**
	 * Requests location updates. This is an ongoing request, thus the caller
	 * needs to check the status of {@link #isAllowed}.
	 * 
	 * @param minTime
	 *            the minimal time
	 * @param minDistance
	 *            the minimal distance
	 * @param locationListener
	 *            the location listener
	 */
	public void requestLocationUpdates(final long minTime, final float minDistance, final LocationListener locationListener) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				requestLocationUpdatesTime = minTime;
				requestLocationUpdatesDistance = minDistance;
				requestLocationUpdates = locationListener;
				connectionCallbacks.onConnected(null);
			}
		});
	}

	/**
	 * Removes location updates.
	 * 
	 * @param locationListener
	 *            the location listener
	 */
	public void removeLocationUpdates(final LocationListener locationListener) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				requestLocationUpdates = null;
				if (googleApiClient != null && googleApiClient.isConnected()) {
					LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, locationListener);
				}
			}
		});
	}
}
