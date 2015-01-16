package com.kimmyungsun.danger;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	private static final String TAG = MySQLiteHelper.class.getName();
	
	private static final String DATABASE_NAME = "DBMS_DANGER.db";
	private static final int DATABASE_VERSION = 5;

	public static final String TABLE_COMPANY = "tblcompany";
	public static final String TABLE_MATTER = "tblmatter";

	public static final String COLUMN_ID = "_id";
	
	public static final String COLUMN_COMPANY_NAME = "companyname";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_GADDRESS = "gaddress";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_ACCURACY = "accuracy";
	public static final String COLUMN_STATUS = "status";
	
	public static final String COLUMN_COMPANY_CODE = "companycode";
	public static final String COLUMN_MATTER_NAME = "mattername";
	public static final String COLUMN_CAS_NO = "casno";
	public static final String COLUMN_OUT_QTY = "outqty";
	public static final String COLUMN_MOVE_QTY = "moveqty";
	public static final String COLUMN_RISK_INFO = "riskinfo";
	public static final String COLUMN_RESULT_PART = "resultpart";
	
	// Database creation sql statement
	public static final String TABLE_COMPANY_CREATE = "create table "
			+ TABLE_COMPANY + "(" 
			+ COLUMN_ID + " integer primary key, " 
			+ COLUMN_COMPANY_NAME + " text not null, "
			+ COLUMN_ADDRESS + " text not null, "
			+ COLUMN_GADDRESS + " text, "
			+ COLUMN_LATITUDE + " float not null, "
			+ COLUMN_LONGITUDE + " float not null, "
			+ COLUMN_ACCURACY + " integer not null, "
			+ COLUMN_STATUS + " integer not null"
			+ ");";

	// Database creation sql statement
	public static final String TABLE_MATTER_CREATE = "create table "
			+ TABLE_MATTER + "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_COMPANY_CODE + " integer not null, "
			+ COLUMN_MATTER_NAME + " text not null, "
			+ COLUMN_CAS_NO + " text, "
			+ COLUMN_OUT_QTY + " float, "
			+ COLUMN_MOVE_QTY + " float, "
			+ COLUMN_RISK_INFO + " text, "
			+ COLUMN_RESULT_PART + " text"
			+ ");";
	
	private Context context;

	public String[] companyAllColumns = { COLUMN_ID
		, COLUMN_COMPANY_NAME
		, COLUMN_ADDRESS
		, COLUMN_GADDRESS
		, COLUMN_LATITUDE
		, COLUMN_LONGITUDE
		, COLUMN_ACCURACY
		, COLUMN_STATUS
	};

	public String[] matterAllColumns = { COLUMN_ID
			, COLUMN_COMPANY_CODE
			, COLUMN_MATTER_NAME
			, COLUMN_CAS_NO
			, COLUMN_OUT_QTY
			, COLUMN_MOVE_QTY
			, COLUMN_RISK_INFO
			, COLUMN_RESULT_PART
	};
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_MATTER_CREATE);
		insertMatters(database);
		
		database.execSQL(TABLE_COMPANY_CREATE);
		insertCompanys(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG,
			"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATTER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
		onCreate(db);
	}

	private void insertCompanys(SQLiteDatabase database) {
		System.out.println("insertCompanys!!");
		Resources res = context.getResources();
		
		// read the companys info
		String[] companys = res.getStringArray(R.array.companys);
		for (String company : companys) {
			Log.i(TAG,company);
			Company c = insertCompany(database, company);
			Log.i(TAG,c.toString());
		}
	}

	private void insertMatters(SQLiteDatabase database) {
		System.out.println("readMatters!!");
		Resources res = context.getResources();
		
		// read the matters info
		String[] matters = res.getStringArray(R.array.matters);
		for ( String m : matters ) {
			Log.i(TAG, m);
			Matter matter = insertMatter(database, m );
			Log.i(TAG, matter.toString());
		}
		
	}

	public Company insertCompany(SQLiteDatabase database, String companyData) {
		
		Company company = Company.newInstance(companyData);
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, company.getId());
		values.put(COLUMN_COMPANY_NAME, company.getCompanyName());
		values.put(COLUMN_ADDRESS, company.getAddress());
		values.put(COLUMN_GADDRESS, company.getgAddress());
		values.put(COLUMN_LATITUDE, company.getLatitude());
		values.put(COLUMN_LONGITUDE, company.getLongitude());
		values.put(COLUMN_ACCURACY, company.getAccuracy());
		values.put(COLUMN_STATUS, company.getStatus());
		
		long insertId = database.insert(TABLE_COMPANY, null, values);
		Cursor cursor = database.query(TABLE_COMPANY, companyAllColumns, 
				COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		company = cursorToCompany(cursor);
		cursor.close();
		
		return company;
	}

	public Matter insertMatter(SQLiteDatabase database, String matterData) {
		
		Matter matter = Matter.newInstance(matterData);
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_COMPANY_CODE, matter.getCompanyCode());
		values.put(COLUMN_MATTER_NAME, matter.getMatterName());
		values.put(COLUMN_CAS_NO, matter.getCasNo());
		values.put(COLUMN_OUT_QTY, matter.getOutQty());
		values.put(COLUMN_MOVE_QTY, matter.getMoveQty());
		values.put(COLUMN_RISK_INFO, matter.getRiskInfo());
		values.put(COLUMN_RESULT_PART, matter.getResultPart());
		
		long insertId = database.insert(TABLE_MATTER, null, values);
		Cursor cursor = database.query(TABLE_MATTER, matterAllColumns, 
				COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		matter = cursorToMatter(cursor);
		cursor.close();
		
		return matter;
	}

	public Company cursorToCompany(Cursor cursor) {
		Company company = new Company();
		company.setId(cursor.getLong(0));
		company.setCompanyName(cursor.getString(1));
		company.setAddress(cursor.getString(2));
		company.setgAddress(cursor.getString(3));
		company.setLatitude(cursor.getFloat(4));
		company.setLongitude(cursor.getFloat(5));
		company.setAccuracy(cursor.getInt(6));
		company.setStatus(cursor.getInt(7));
		return company;
	}

	public Matter cursorToMatter(Cursor cursor) {
		Matter matter = new Matter();
		matter.setId(cursor.getInt(0));
		matter.setCompanyCode(cursor.getInt(1));
		matter.setMatterName(cursor.getString(2));
		matter.setCasNo(cursor.getString(3));
		matter.setOutQty(cursor.getFloat(4));
		matter.setMoveQty(cursor.getFloat(5));
		matter.setRiskInfo(cursor.getString(6));
		matter.setResultPart(cursor.getString(7));
		return matter;
	}

	public List<Company> getAllCompanys() {
		List<Company> companys = new ArrayList<Company>();
		
		Cursor cursor = getReadableDatabase().query(MySQLiteHelper.TABLE_COMPANY, companyAllColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while( !cursor.isAfterLast()) {
			Company di = cursorToCompany(cursor);
			companys.add(di);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return companys;
	}

	public List<Company> searchCompanys(String searchString) {
		List<Company> companys = new ArrayList<Company>();
		
		Cursor cursor = getReadableDatabase().query(MySQLiteHelper.TABLE_COMPANY, companyAllColumns, 
				MySQLiteHelper.COLUMN_ADDRESS + " like '%" + searchString + "%' "
				+ " OR " + MySQLiteHelper.COLUMN_COMPANY_NAME + " like '%" + searchString + "%' ", 
				null, null, null, null);
		
		cursor.moveToFirst();
		while( !cursor.isAfterLast()) {
			Company company = cursorToCompany(cursor);
			companys.add(company);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return companys;
	}

	public List<Matter> searchMatters(Company company) {
		List<Matter> matters = new ArrayList<Matter>();
		
		Cursor cursor = getReadableDatabase().query(MySQLiteHelper.TABLE_MATTER, matterAllColumns, 
				MySQLiteHelper.COLUMN_COMPANY_CODE + " = " + company.getId(), 
				null, null, null, null);
		
		cursor.moveToFirst();
		while( !cursor.isAfterLast()) {
			Matter matter = cursorToMatter(cursor);
			matters.add(matter);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return matters;
	}
	
	public void deleteCompany(Company di) {
		long id = di.getId();
		System.out.println("DangerItem deleted with id: " + id);
		getWritableDatabase().delete(MySQLiteHelper.TABLE_COMPANY, MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}

}
