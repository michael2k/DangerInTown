package org.safedu.danger.provider;

import java.util.HashMap;

import org.safedu.danger.Company;
import org.safedu.danger.Matter;
import org.safedu.danger.constanst.IDangerConstants;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.util.Log;

import org.safedu.danger.R;

public class DangerDBHelper extends SQLiteOpenHelper implements IDangerConstants {
	
	private static final String TAG = DangerDBHelper.class.getName();
	
	private static DangerDBHelper dbHelper;
	
	public static DangerDBHelper getInstance(Context context) {
		if ( dbHelper == null ) dbHelper = new DangerDBHelper(context);
		return dbHelper;
	}
	
	private static final String DATABASE_NAME = "DBMS_DANGER.db";
	private static final int DATABASE_VERSION = 10;

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

	public static final String[] COMPANY_ALL_COLUMNS = { COLUMN_ID
		, COLUMN_COMPANY_NAME
		, COLUMN_ADDRESS
		, COLUMN_GADDRESS
		, COLUMN_LATITUDE
		, COLUMN_LONGITUDE
		, COLUMN_ACCURACY
		, COLUMN_STATUS
	};

	public static final String[] MATTER_ALL_COLUMNS = { COLUMN_ID
			, COLUMN_COMPANY_CODE
			, COLUMN_MATTER_NAME
			, COLUMN_CAS_NO
			, COLUMN_OUT_QTY
			, COLUMN_MOVE_QTY
			, COLUMN_RISK_INFO
			, COLUMN_RESULT_PART
	};
	
	private DangerDBHelper(Context context) {
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
		Cursor cursor = database.query(TABLE_COMPANY, COMPANY_ALL_COLUMNS, 
				COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		company = Company.cursorToCompany(cursor);
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
		Cursor cursor = database.query(TABLE_MATTER, MATTER_ALL_COLUMNS, 
				COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		matter = Matter.cursorToMatter(cursor);
		cursor.close();
		
		return matter;
	}

	public Cursor getAllCompanys() {
		
		return getReadableDatabase().query(DangerDBHelper.TABLE_COMPANY, COMPANY_ALL_COLUMNS, null, null, null, null, null);
		
	}

	public Cursor searchCompanys(String searchString) {
		
		return getReadableDatabase().query(DangerDBHelper.TABLE_COMPANY, COMPANY_ALL_COLUMNS, 
				DangerDBHelper.COLUMN_ADDRESS + " like '%" + searchString + "%' "
				+ " OR " + DangerDBHelper.COLUMN_COMPANY_NAME + " like '%" + searchString + "%' ", 
				null, null, null, null);
		
	}

	public Cursor searchMatters(long companyId) {
		
		return getReadableDatabase().query(DangerDBHelper.TABLE_MATTER, MATTER_ALL_COLUMNS, 
				DangerDBHelper.COLUMN_COMPANY_CODE + " = " + companyId, 
				null, null, null, null);
		
	}
	
	public void deleteCompany(Company di) {
		long id = di.getId();
		System.out.println("DangerItem deleted with id: " + id);
		getWritableDatabase().delete(DangerDBHelper.TABLE_COMPANY, DangerDBHelper.COLUMN_ID + " = " + id, null);
	}

	private static HashMap<String, String> buildColumnMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(BaseColumns._ID, KEY_ID+" as " + KEY_ID);
		map.put(SearchManager.SUGGEST_COLUMN_TEXT_1,COLUMN_COMPANY_NAME+ " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
		map.put(SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA, COLUMN_ADDRESS + " AS " +SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA);
		map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, KEY_ID + " as " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID );
		return map;
	}
	
	public Cursor query(String selection, String[] selectionArgs,String[] columns) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(TABLE_COMPANY);
		builder.setProjectionMap(buildColumnMap());

		Cursor cursor = builder.query(getReadableDatabase(), new String[] {
				BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1,
				SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA,
				SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID }, DangerDBHelper.COLUMN_ADDRESS + " like '%" + selectionArgs[0] + "%' "
						+ " OR " + DangerDBHelper.COLUMN_COMPANY_NAME + " like '%" + selectionArgs[0] + "%' ",
				null, null, null, COLUMN_COMPANY_NAME + " asc ", "20");
		return cursor;
	}

	public Cursor getCompany(long id) {
		
		return getReadableDatabase().query(DangerDBHelper.TABLE_COMPANY, COMPANY_ALL_COLUMNS, 
				DangerDBHelper.COLUMN_ID + " = " + id , null, null, null, null);
		
	}

	public Cursor getMatter(long id) {
		
		return getReadableDatabase().query(DangerDBHelper.TABLE_MATTER, MATTER_ALL_COLUMNS, 
				DangerDBHelper.COLUMN_ID + " = " + id , null, null, null, null);
		
	}	

}
