package org.safedu.danger.datamng;

import java.io.IOException;
import java.io.InputStream;

import org.safedu.danger.constanst.IDangerConstants;
import org.safedu.danger.object.PlaceDetailObject;
import org.safedu.danger.object.ResponsePlaceResult;
import org.safedu.danger.object.RouteObject;
import org.safedu.danger.settings.SettingManager;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.ypyproductions.location.utils.LocationUtils;
import com.ypyproductions.utils.DownloadUtils;
import com.ypyproductions.utils.StringUtils;

/**
 * 
 * YPYNetUtils.java
 * 
 * @Author DoBao
 * @Email baodt@hanet.vn
 * @Phone +84983028786
 * @Skype baopfiev_k50
 * @Date Dec 23, 2013
 * @Project WhereMyLocation
 * @Package com.ypyproductions.wheremylocation.net
 * @Copyright � 2013 Softwares And Network Solutions HANET Co., Ltd
 */
public class YPYNetUtils implements IDangerConstants {

	private static final String TAG = YPYNetUtils.class.getSimpleName();

	public static ResponsePlaceResult getListPlacesBaseOnType(Context mContext, double lng, double lat, String types) {
		boolean sensor = LocationUtils.isEnableGPS(mContext);
		String url = String.format(FORMAT_URL_TYPE_SEARCH, String.valueOf(lat), String.valueOf(lng), String.valueOf(SettingManager.getRadius(mContext) * 1000), types, sensor,
				API_KEY);
		Log.d(TAG, "==================>getListPlacesBaseOnType url=" + url);
		if (!StringUtils.isStringEmpty(url)) {
			InputStream mInputStream = DownloadUtils.download(url);
			return JsonParsingUtils.parsingListPlaceObjects(mInputStream);
		}
		return null;
	}

	public static ResponsePlaceResult getListNextPlacesBaseOnType(Context mContext, double lng, double lat, String types,String pageToken) {
		boolean sensor = LocationUtils.isEnableGPS(mContext);
		String url = String.format(FORMAT_NEXTPAGE_TYPE_SEARCH_URL, String.valueOf(lat), String.valueOf(lng), String.valueOf(SettingManager.getRadius(mContext) * 1000), types, sensor,
				API_KEY,pageToken);
		Log.d(TAG, "==================>getListNextPlacesBaseOnType url=" + url);
		if (!StringUtils.isStringEmpty(url)) {
			InputStream mInputStream = DownloadUtils.download(url);
			return JsonParsingUtils.parsingListPlaceObjects(mInputStream);
		}
		return null;
	}

	public static ResponsePlaceResult getListPlacesBaseOnText(Context mContext, double lng, double lat, String textSearch) {
		boolean sensor = LocationUtils.isEnableGPS(mContext);
		
		String url = String.format(FORMAT_URL_TEXT_SEARCH, String.valueOf(lat), String.valueOf(lng), String.valueOf(SettingManager.getRadius(mContext) * 1000),
				StringUtils.urlEncodeString(textSearch), sensor,API_KEY);

		Log.d(TAG, "==================>getListPlacesBaseOnText url=" + url);
		if (!StringUtils.isStringEmpty(url)) {
			InputStream mInputStream = DownloadUtils.download(url);
			return JsonParsingUtils.parsingListPlaceObjects(mInputStream);
		}
		return null;
	}
	
	public static ResponsePlaceResult getListNextPlacesBaseOnText(Context mContext, double lng, double lat, String textSearch,String pageToken) {
		boolean sensor = LocationUtils.isEnableGPS(mContext);
		
		String url = String.format(FORMAT_NEXTPAGE_TEXT_SEARCH_URL, String.valueOf(lat), String.valueOf(lng), String.valueOf(SettingManager.getRadius(mContext) * 1000), 
				StringUtils.urlEncodeString(textSearch), sensor,API_KEY,pageToken);
		
		Log.d(TAG, "==================>getListNextPlacesBaseOnText url=" + url);
		if (!StringUtils.isStringEmpty(url)) {
			InputStream mInputStream = DownloadUtils.download(url);
			return JsonParsingUtils.parsingListPlaceObjects(mInputStream);
		}
		return null;
	}

	public static PlaceDetailObject getPlaceDetailObject(Context mContext, String ref) {
		boolean sensor = LocationUtils.isEnableGPS(mContext);
		String url = String.format(FORMAT_DETAIL_LOCATION_REF, ref, sensor, API_KEY);
		Log.d(TAG, "==================>getPlaceDetailObject url=" + url);
		if (!StringUtils.isStringEmpty(url)) {
			InputStream mInputStream = DownloadUtils.download(url);
			return JsonParsingUtils.parsingPlaceDetailObject(mInputStream);
		}
		return null;
	}

	public static RouteObject getRouteObject(Context mContext, Location mOriLocation, Location mDesLocation) {
		boolean sensor = LocationUtils.isEnableGPS(mContext);
		String origin = mOriLocation.getLatitude() + "," + mOriLocation.getLongitude();
		String destination = mDesLocation.getLatitude() + "," + mDesLocation.getLongitude();
		String formatDirections = String.format(FORMAT_DIRECTION_URL, origin, destination, sensor, API_KEY, SettingManager.getTravelMode(mContext));
		if (SettingManager.getMetric(mContext).equals(UNIT_MILE)) {
			formatDirections = formatDirections + "&units=imperial";
		}
		Log.d(TAG, "==================>getRouteObject url=" + formatDirections);
		if (!StringUtils.isStringEmpty(formatDirections)) {
			InputStream mInputStream = DownloadUtils.download(formatDirections);
			return JsonParsingUtils.parsingRouteObject(mInputStream);
		}
		return null;
	}
}
