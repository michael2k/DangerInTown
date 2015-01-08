package com.kimmyungsun.danger;

import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DangersDataSource {
	
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	
	public DangersDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getReadableDatabase();
	}
	
	public void close() {
		database.close();
	}
	
	
	public void deleteCompany(Company company) {
		dbHelper.deleteCompany(company);
	}
	
	public List<Company> getAllCompanys() {
		return dbHelper.getAllCompanys();
	}

	public List<Company> searchCompanys(String searchString) {
		return dbHelper.searchCompanys(searchString);
	}

	public List<Matter> searchMatters(Company company) {
		return dbHelper.searchMatters(company);
	}

}
