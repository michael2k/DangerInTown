package com.kimmyungsun.danger.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.kimmyungsun.danger.constanst.IDangerConstants;
import com.ypyproductions.utils.DBLog;


public class DangerContentProvider extends ContentProvider implements IDangerConstants {

	public static final String TAG = DangerContentProvider.class.getName();

	private SQLiteDatabase mDangerDB;
	private DangerDBHelper mDangerDBHelper;
	public  UriMatcher uriMatcher = buildUriMatcher();

	@Override
	public boolean onCreate() {
		Log.i(TAG, "onCreate");
		Context context = getContext();
		mDangerDBHelper = DangerDBHelper.getInstance(context);
		mDangerDB = mDangerDBHelper.getWritableDatabase();
		return (mDangerDB == null) ? false : true;

	}
	
   private UriMatcher buildUriMatcher(){
	   	Log.i(TAG, "buildUriMatcher");
	   
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	 
        // Suggestion items of Search Dialog is provided by this uri
//        uriMatcher.addURI(CONTENT_URI.getAuthority(), SearchManager.SUGGEST_URI_PATH_QUERY,SUGGESTION_KEYWORD);
//        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SUGGESTION_KEYWORD);
	 
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
        uriMatcher.addURI(DANGER_AUTHORITY, "companys", SEARCH_COMPANY_KEYWORD);
        uriMatcher.addURI(DANGER_AUTHORITY, "companys/#", GET_COMPANY_KEYWORD);
        uriMatcher.addURI(DANGER_AUTHORITY, "matters", SEARCH_MATTER_KEYWORD);
        
	 
        return uriMatcher;
    }

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.i(TAG, "insert");
		
		if(mDangerDB!=null){
			long rowID=mDangerDB.insert(DATABASE_TABLE, null, values);
			if (rowID > 0) {
				Uri mUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
				getContext().getContentResolver().notifyChange(mUri, null);
				return mUri;

			}
		}
		throw new UnsupportedOperationException();
	}


	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.i(TAG, "query: uri[" + uri.toString() + "]");
		Cursor c=null;
		switch (uriMatcher.match(uri)) {
        case SEARCH_COMPANY_KEYWORD:
        	if ( selectionArgs != null && selectionArgs.length > 0) {
        		DBLog.d(TAG, "================>aaaaa="+selectionArgs[0]);
        		
        	} else {
        		// getAllCompanys
        		c = mDangerDBHelper.getAllCompanys();
        	}
//        	c=getSuggestions(selectionArgs[0]);
            break;
        case SEARCH_MATTER_KEYWORD:
        	DBLog.d(TAG, "================>bbb="+selectionArgs[0]);
        	c = mDangerDBHelper.searchMatters(Long.valueOf(selectionArgs[0]));
        	break;
        case GET_COMPANY_KEYWORD:
//             c = getRecord(uri);
        	break;
		}
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

}
