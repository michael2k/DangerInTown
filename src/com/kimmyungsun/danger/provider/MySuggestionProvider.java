package com.kimmyungsun.danger.provider;

import java.util.HashMap;
import java.util.Locale;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.kimmyungsun.danger.constanst.IDangerConstants;
import com.ypyproductions.utils.DBLog;


public class MySuggestionProvider extends SearchRecentSuggestionsProvider implements IDangerConstants {

	public static final String TAG = MySuggestionProvider.class.getName();
	
	private DangerDBHelper mDangerDBHelper;
	public  UriMatcher uriMatcher = buildUriMatcher();


	private SQLiteDatabase mDangerDB;
	
	
	public MySuggestionProvider() {
		setupSuggestions(AUTHORITY, DATABASE_MODE_QUERIES);
	}
	

	
   private UriMatcher buildUriMatcher(){
	   	Log.d(TAG, "buildUriMatcher");
	   
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	 
        // Suggestion items of Search Dialog is provided by this uri
        uriMatcher.addURI(CONTENT_URI.getAuthority(), SearchManager.SUGGEST_URI_PATH_QUERY,SUGGESTION_KEYWORD);
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SUGGESTION_KEYWORD);
	 
        // This URI is invoked, when user presses "Go" in the Keyboard of Search Dialog
        // Listview items of SearchableActivity is provided by this uri
        // See android:searchSuggestIntentData="content://in.wptrafficanalyzer.searchdialogdemo.provider/countries" of searchable.xml
//        uriMatcher.addURI(AUTHORITY, "records", SEARCH_KEYWORD);
	 
        // This URI is invoked, when user selects a suggestion from search dialog or an item from the listview
        // Country details for CountryActivity is provided by this uri
        // See, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID in CountryDB.java
//        uriMatcher.addURI(AUTHORITY, "records/#", GET_KEYWORD);
//        uriMatcher.addURI(AUTHORITY, "records/@", SAVE_KEYWORD);
	 
        // find Company
        uriMatcher.addURI(AUTHORITY, "companys", SEARCH_COMPANY_KEYWORD);
        uriMatcher.addURI(AUTHORITY, "company/#", GET_COMPANY_KEYWORD);
        uriMatcher.addURI(AUTHORITY, "matters", SEARCH_MATTER_KEYWORD);
        
       return uriMatcher;
    }

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "query : uri[" + uri.toString() + "], selectionArgs[0] : [" + selectionArgs[0] + "]");
		Cursor c=null;
		switch (uriMatcher.match(uri)) {
        case SUGGESTION_KEYWORD:
        	DBLog.d(TAG, "================>SUGGESTION_KEYWORD="+selectionArgs[0]);
    		c = mDangerDBHelper.query(selection, selectionArgs, null);
    		Log.d(TAG, "result : " + c.getCount());
            break;
        case SEARCH_COMPANY_KEYWORD:
        	DBLog.d(TAG, "================>SEARCH_COMPANY_KEYWORD="+selectionArgs[0]);
        	if ( selectionArgs != null && selectionArgs.length > 0) {
        		DBLog.d(TAG, "================>aaaaa="+selectionArgs[0]);
        		c = getContext().getContentResolver()
        				.query(Uri.parse("content://" + DANGER_AUTHORITY + "/companys/" + selectionArgs[0]), DangerDBHelper.COMPANY_ALL_COLUMNS, null, selectionArgs, null);
        	} else {
        		// getAllCompanys
//        		c = getContext().getContentResolver()
//        				.query(Uri.parse("content://" + DANGER_AUTHORITY + "/companys/" + selectionArgs[0]), DangerDBHelper.COMPANY_ALL_COLUMNS, null, selectionArgs, null);
       	}
            break;
        case SEARCH_MATTER_KEYWORD:
        	DBLog.d(TAG, "================>SEARCH_MATTER_KEYWORD="+selectionArgs[0]);
    		c = getContext().getContentResolver()
    				.query(Uri.parse("content://" + DANGER_AUTHORITY + "/matters/" + selectionArgs[0]), DangerDBHelper.MATTER_ALL_COLUMNS, null, selectionArgs, null);
        	break;
        case GET_COMPANY_KEYWORD:
        	DBLog.d(TAG, "================>GET_COMPANY_KEYWORD="+selectionArgs[0]);
        	c = getContext().getContentResolver()
        			.query(Uri.parse("content://" + DANGER_AUTHORITY + "/companys/" + selectionArgs[0]), DangerDBHelper.COMPANY_ALL_COLUMNS, null, selectionArgs, null);
		}
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}



	@Override
	public boolean onCreate() {
		Log.d(TAG, "onCreate");
		Context context = getContext();
		mDangerDBHelper = DangerDBHelper.getInstance(context);
		mDangerDB = mDangerDBHelper.getWritableDatabase();
		return (mDangerDB == null) ? false : true;
	
	}
	

}
