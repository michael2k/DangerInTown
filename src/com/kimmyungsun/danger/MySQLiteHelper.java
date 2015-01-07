package com.kimmyungsun.danger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_DANGER = "tbldanger";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMPANY_NAME = "companyname";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_GADDRESS = "gaddress";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_ACCURACY = "accuracy";
	public static final String COLUMN_STATUS = "status";
	
	private static final String DATABASE_NAME = "tbldanger.db";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_DANGER + "(" 
			+ COLUMN_ID + " integer not null, " 
			+ COLUMN_COMPANY_NAME + " text not null, "
			+ COLUMN_ADDRESS + " text not null, "
			+ COLUMN_GADDRESS + " text, "
			+ COLUMN_LATITUDE + " float not null, "
			+ COLUMN_LONGITUDE + " float not null, "
			+ COLUMN_ACCURACY + " integer not null, "
			+ COLUMN_STATUS + " integer not null"
			+ ");";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
			"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANGER);
		onCreate(db);
	}

}
