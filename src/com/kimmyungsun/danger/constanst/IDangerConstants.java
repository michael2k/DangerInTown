package com.kimmyungsun.danger.constanst;

import android.graphics.Color;
import android.net.Uri;


public interface IDangerConstants {
	
	public static final String MAP_KEY = "AIzaSyBHSu7Gakue-Jgb86x4hOG62aXMWct4X0o"; // 13
//	public static final String MAP_KEY = "AIzaSyAa0VCzUiaKafw-sNERQX_nG2E0dlnK7vw"; // 15
	
	public static final String API_KEY = "AIzaSyDYzJufVULJ1Xl2kbaUbhTh9WxAlvuPZgQ";
	
	public static final boolean DEBUG=true; 
	
	public static final boolean SHOW_ADVERTISEMENT=true; 
	public static final String ADMOB_ID = "ADMOB_ID"; 
	
	public static final String EMAIL_CONTACT = "mskim2k@gmail.com";
	public static final String URL_YOUR_WEBSITE = "URL_YOUR_WEBSITE";
	public static final String URL_YOUR_GOOGLE_PLUS = "URL_YOUR_GOOGLE_PLUS";
	public static final String URL_YOUR_FACE_BOOK = "URL_YOUR_FACE_BOOK";
	public static final String URL_MORE_APP = "URL_MORE_APP"; // for examples :https://play.google.com/store/search?q=ypyproductions

	public static final String FORMAT_URL_PHOTO_REF = "https://maps.googleapis.com/maps/api/place/photo?photoreference=%1$s&sensor=false&maxwidth=%2$s&maxheight=%3$s&key=%4$s";
	public static final String FORMAT_DETAIL_LOCATION_REF = "https://maps.googleapis.com/maps/api/place/details/json?reference=%1$s&sensor=%2$s&key=%3$s";
	public static final String FORMAT_URL_TYPE_SEARCH = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "location=%1$s,%2$s&radius=%3$s&types=%4$s&sensor=%5$s&key=%6$s";
	public static final String FORMAT_URL_TEXT_SEARCH = "https://maps.googleapis.com/maps/api/place/textsearch/json?"
			+ "location=%1$s,%2$s&radius=%3$s&query=%4$s&sensor=%5$s&key=%6$s";

	public static final String FORMAT_URL_PHOTO = "https://maps.googleapis.com/maps/api/place/photo?photoreference=%1$s&maxheight=320&maxwidth=320&sensor=false&key=%2$s";

	public static final String FORMAT_DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json?origin=%1$s&destination=%2$s&sensor=%3$s&key=%4$s&mode=%5$s";
	public static final String FORMAT_NEXTPAGE_TYPE_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%1$s,%2$s&radius=%3$s&types=%4$s&sensor=%5$s&key=%6$s&pagetoken=%7$s";
	public static final String FORMAT_NEXTPAGE_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?location=%1$s,%2$s&radius=%3$s&types=%4$s&sensor=%5$s&key=%6$s&pagetoken=%7$s";

	public static final int TYPE_SEARCH_BY_TYPES = 1;
	public static final int TYPE_SEARCH_BY_TEXT = 2;
	public static final String URL_RATE_APP = "https://play.google.com/store/apps/details?id=%1$s";
	
	public static final String TAG_HOME = "TAG_HOME";
	public static final String TAG_ABOUT = "TAG_ABOUT";
	public static final String TAG_MY_LOCATION = "TAG_LOCATION";
	public static final String TAG_FAVORITE = "TAG_FAVORITE";
	public static final String TAG_SETTINGS = "TAG_SETTINGS";
	
	public static final double INVALID_VALUE = -1000;

	public static final String KEY_START_FROM = "KEY_START_FROM";
	public static final String START_FROM_SPLASH = "splash";
	public static final String START_FROM_SEARCH = "search";
	public static final String START_FROM_TOTAL_PLACE = "totalPlace";
	public static final String START_FROM_MAIN = "main";
	public static final String START_FROM_DETAIL = "detail";

	public static final String PIORITY_DISTANCE = "distance";
	public static final String PIORITY_RATING = "prominence";

	public static final String TRAVEL_MODE_DRIVING = "driving";
	public static final String DRIVING = "Driving";

	public static final String TRAVEL_MODE_WALKING = "walking";
	public static final String WALKING = "Walking ";

	public static final String UNIT_KILOMETTER = "Kilometers";
	public static final String UNIT_MILE = "Miles";

	public static final float DEFAULT_ZOOM_LEVEL = 18f;
	public static final int TIME_OUT = 5;

	public static final int HOME_INDEX = 0;
	public static final int MY_LOCATION_INDEX = 1;
	public static final int FAVORITES_INDEX = 2;
	public static final int SETTINGS_INDEX = 3;
	public static final int ABOUT_US_INDEX = 4;

	public static final int MAP_INDEX = 1;
	public static final int INVALID_LOCATION = 1;

	public static final int MAX_RADIUS = 50;
	public static final int MIN_RADIUS = 0;
	public static final int DEFAULT_RADIUS_MARKER = 50;
	public static final int DEFAULT_STROKE_MARKER_WIDTH = 4;

	public static final String KEY_NAME_FRAGMENT = "name_fragment";
	public static final String KEY_ID_FRAGMENT = "id_fragment";
	public static final String KEY_NAME_KEYWORD = "keyword";
	public static final String KEY_URL = "url";

	public static final String FILE_FAVORITE_PLACES = "favorites.dat";
	public static final String DIR_DATA = "data";

	public static final int MAX_DISTANCE_TO_UPDATE = 500;

	public static final String STATUS_OK = "ok";
	public static final String STATUS_ZERO = "ZERO_RESULTS";

	public static final int STROKE_COLOR = Color.parseColor("#800099cc");
	public static final int FILL_COLOR = Color.parseColor("#3233b5e5");

	public static final float ONE_MILE = 1.60934f;


//	public static final int[] LIST_ICON_ABOUTS = { R.drawable.icon_email, R.drawable.icon_website, R.drawable.icon_facebook,
//		R.drawable.icon_gplus, R.drawable.icon_rate_me,
//			R.drawable.icon_more_app };
//
//	public static final int[] LIST_TITLE_ABOUTS = { R.string.title_contact_us, R.string.title_website, R.string.title_facebook,
//		R.string.title_google_plus, R.string.title_rate_us,
//			R.string.title_more_app };
//
//	public static final int[] LIST_CONTENT_ABOUTS = { R.string.info_contact_us, R.string.info_website, R.string.info_facebook,
//		R.string.info_google_plus, R.string.info_rate_us,
//			R.string.info_more_app };
	
	public static final String[] LIST_LINK_ABOUTS = { "Email", URL_YOUR_WEBSITE,URL_YOUR_FACE_BOOK, URL_YOUR_GOOGLE_PLUS,
			URL_RATE_APP,URL_MORE_APP};

//	public static final String DATABASE_NAME = "datas";
//	public static final String DATABASE_TABLE = "records";
//	public static final int DATABASE_VERSION = 3;

	public static final int SUGGESTION_KEYWORD = 1;
	public static final int SEARCH_KEYWORD = 2;
	public static final int GET_KEYWORD = 3;
	public static final int SAVE_KEYWORD = 4;
	
	public static final int GET_COMPANY_KEYWORD = 11;
	public static final int SEARCH_COMPANY_KEYWORD = 12;
	public static final int SEARCH_MATTER_KEYWORD = 13;

	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_KEYWORD = "keyword";
	
//	public static final String COLUMN_DISPLAY1 = "display1";
//	public static final String COLUMN_DATE = "date";
//	public static final String COLUMN_QUERY = "query";
	
	
//	public static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " ("+COLUMN_DISPLAY1+" TEXT  NOT NULL, " 
//	+ COLUMN_DATE+" text not null, "+COLUMN_QUERY+" text not null);";
	
//	public static final String DANGER_AUTHORITY = "com.kimmyungsun.danger.provider.DangerContentProvider";
//	public static final Uri DANGER_CONTENT_URI = Uri.parse("content://" + DANGER_AUTHORITY);
	
	public static final String AUTHORITY = "com.kimmyungsun.danger.provider.DangerContentProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/companys");
	public static final Uri COMPANY_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/companys");
	public static final Uri CONTENT_CHECK = Uri.parse("content://" + AUTHORITY + "/companys/@");

}
