package com.kimmyungsun.danger.provider;

import com.kimmyungsun.danger.constanst.IDangerConstants;

import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class DangerSuggestionProvider extends SearchRecentSuggestionsProvider implements IDangerConstants 

{
	
	private static final String TAG = DangerSuggestionProvider.class.getName();
	
	public final static String AUTHORITY = "com.kimmyungsun.danger.provider.DangerSuggestionProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;
	
	public DangerSuggestionProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}

	/* (non-Javadoc)
	 * @see android.content.SearchRecentSuggestionsProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		Log.i(TAG, "selectionArgs(" + selectionArgs[0] + ")");
		
		getContext().getContentResolver().query(Uri.parse("content://" + DANGER_AUTHORITY + "/companys"), projection, selection, selectionArgs, sortOrder);
		
		return super.query(uri, projection, selection, selectionArgs, sortOrder);
	}
	
	

}
