package com.kimmyungsun.danger.provider;

import android.content.SearchRecentSuggestionsProvider;

public class DangerSuggestionProvider extends SearchRecentSuggestionsProvider {
	
	public final static String AUTHORITY = "com.kimmyungsun.danger.provider.DangerSuggestionProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;
	
	public DangerSuggestionProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}

}
