package com.kimmyungsun.danger;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DangersDataSource {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID
			, MySQLiteHelper.COLUMN_ADDRESS
			, MySQLiteHelper.COLUMN_GADDRESS
			, MySQLiteHelper.COLUMN_LATITUDE
			, MySQLiteHelper.COLUMN_LONGITUDE
			, MySQLiteHelper.COLUMN_ACCURACY
			, MySQLiteHelper.COLUMN_STATUS
		};
	
	public DangersDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getReadableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public DangerItem createDangerItem(String dangerData) {
		
		DangerItem di = DangerItem.newInstance(dangerData);
		
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_ADDRESS, di.getAddress());
		values.put(MySQLiteHelper.COLUMN_GADDRESS, di.getgAddress());
		values.put(MySQLiteHelper.COLUMN_LATITUDE, di.getLatitude());
		values.put(MySQLiteHelper.COLUMN_LONGITUDE, di.getLongitude());
		values.put(MySQLiteHelper.COLUMN_ACCURACY, di.getAccuracy());
		values.put(MySQLiteHelper.COLUMN_STATUS, di.getStatus());
		
		long insertId = database.insert(MySQLiteHelper.TABLE_DANGER, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_DANGER, allColumns, 
				MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		di = cursorToDangerItem(cursor);
		cursor.close();
		
		return di;
	}
	
	public void deleteDangerItem(DangerItem di) {
		long id = di.getId();
		System.out.println("DangerItem deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_DANGER, MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<DangerItem> getAllDangerItems() {
		List<DangerItem> dis = new ArrayList<DangerItem>();
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_DANGER, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while( !cursor.isAfterLast()) {
			DangerItem di = cursorToDangerItem(cursor);
			dis.add(di);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return dis;
	}

	public DangerItem cursorToDangerItem(Cursor cursor) {
		DangerItem di = new DangerItem();
		di.setId(cursor.getLong(0));
		di.setAddress(cursor.getString(1));
		di.setgAddress(cursor.getString(2));
		di.setLatitude(cursor.getFloat(3));
		di.setLongitude(cursor.getFloat(4));
		di.setAccuracy(cursor.getInt(5));
		di.setStatus(cursor.getInt(6));
		return di;
	}

	public List<DangerItem> searchDangerItems(String searchString) {
		List<DangerItem> dis = new ArrayList<DangerItem>();
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_DANGER, allColumns, 
				MySQLiteHelper.COLUMN_ADDRESS + " like '%" + searchString + "%'", 
				null, null, null, null);
		
		cursor.moveToFirst();
		while( !cursor.isAfterLast()) {
			DangerItem di = cursorToDangerItem(cursor);
			dis.add(di);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return dis;
	}
}
