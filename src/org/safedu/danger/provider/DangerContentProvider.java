package org.safedu.danger.provider;

import org.safedu.danger.constanst.IDangerConstants;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.ypyproductions.utils.DBLog;


public class DangerContentProvider extends SearchRecentSuggestionsProvider implements IDangerConstants {

	public static final String TAG = DangerContentProvider.class.getName();

	private SQLiteDatabase mDangerDB;
	private DangerDBHelper mDangerDBHelper;
	public  UriMatcher uriMatcher = buildUriMatcher();

	public final static int MODE = DATABASE_MODE_QUERIES;

	
	public DangerContentProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}
	
	@Override
	public boolean onCreate() {
		Log.d(TAG, "onCreate");
		Context context = getContext();
		mDangerDBHelper = DangerDBHelper.getInstance(context);
		mDangerDB = mDangerDBHelper.getWritableDatabase();
		return (mDangerDB == null) ? false : true;

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
        uriMatcher.addURI(AUTHORITY, "companys/#", GET_COMPANY_KEYWORD);
        uriMatcher.addURI(AUTHORITY, "matters", SEARCH_MATTER_KEYWORD);
        uriMatcher.addURI(AUTHORITY, "matters/#", GET_MATTER_KEYWORD);
        
	 
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
		throw new UnsupportedOperationException();
	}


	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "query: uri[" + uri.toString() + "]");
		Cursor c=null;
		switch (uriMatcher.match(uri)) {
        case SUGGESTION_KEYWORD:
        	DBLog.d(TAG, "================>SUGGESTION_KEYWORD="+selectionArgs[0]);
    		c = mDangerDBHelper.query(selection, selectionArgs, null);
    		Log.d(TAG, "result : " + c.getCount());
            break;
        case SEARCH_COMPANY_KEYWORD:
        	if ( selectionArgs != null && selectionArgs.length > 0) {
        		DBLog.d(TAG, "================>SEARCH_COMPANY_KEYWORD="+selectionArgs[0]);
        		c = mDangerDBHelper.searchCompanys(selectionArgs[0]);
        	} else {
        		// getAllCompanys
        		c = mDangerDBHelper.getAllCompanys();
        	}
//        	c=getSuggestions(selectionArgs[0]);
            break;
        case SEARCH_MATTER_KEYWORD:
        	DBLog.d(TAG, "================>SEARCH_MATTER_KEYWORD="+selectionArgs[0]);
        	c = mDangerDBHelper.searchMatters(Long.valueOf(selectionArgs[0]));
        	break;
        case GET_COMPANY_KEYWORD:
        	DBLog.d(TAG, "================>GET_COMPANY_KEYWORD="+selectionArgs[0]);
//             c = getRecord(uri);
        	c = mDangerDBHelper.getCompany(Long.valueOf(selectionArgs[0]));
        	break;
        case GET_MATTER_KEYWORD:
        	DBLog.d(TAG, "================>GET_MATTER_KEYWORD="+selectionArgs[0]);
//             c = getRecord(uri);
        	c = mDangerDBHelper.getMatter(Long.valueOf(selectionArgs[0]));
        	break;
		}
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}

}
