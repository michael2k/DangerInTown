package com.kimmyungsun.danger.provider;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.kimmyungsun.danger.Company;
import com.kimmyungsun.danger.Matter;
import com.kimmyungsun.danger.constanst.IDangerConstants;

public class DangersDataSource implements IDangerConstants {
	
	private Context context;
	
	public DangersDataSource(Context context) {
		this.context = context;
	}
	
	public List<Company> getAllCompanys() {
		
		List<Company> companys = new ArrayList<Company>();
		
		Cursor cursor = context.getContentResolver()
				.query(Uri.parse("content://" + DANGER_AUTHORITY + "/companys"), DangerDBHelper.COMPANY_ALL_COLUMNS, null, null, null);
		
		cursor.moveToFirst();
		while( !cursor.isAfterLast()) {
			Company di = Company.cursorToCompany(cursor);
			companys.add(di);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return companys;
	}
	
	public Company getCompany(long id) {
		Cursor cursor = context.getContentResolver()
				.query(Uri.parse("content://" + DANGER_AUTHORITY + "/companys/" + id), DangerDBHelper.COMPANY_ALL_COLUMNS, null, new String[] { String.valueOf(id) }, null);
		cursor.moveToFirst();
		Company c = Company.cursorToCompany(cursor);
		cursor.close();
		return c;
	}

	public List<Company> searchCompanys(String searchString) {
		List<Company> companys = new ArrayList<Company>();
		
		Cursor cursor = context.getContentResolver()
				.query(Uri.parse("content://" + DANGER_AUTHORITY + "/companys"), DangerDBHelper.COMPANY_ALL_COLUMNS, null, new String[] { searchString }, null);
		
		cursor.moveToFirst();
		while( !cursor.isAfterLast()) {
			Company company = Company.cursorToCompany(cursor);
			companys.add(company);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return companys;
	}

	public List<Matter> searchMatters(Company company) {
		List<Matter> matters = new ArrayList<Matter>();
		
		Cursor cursor = context.getContentResolver()
				.query(Uri.parse("content://" + DANGER_AUTHORITY + "/matters"), DangerDBHelper.MATTER_ALL_COLUMNS, null, new String[] {String.valueOf(company.getId())}, null);
		
		cursor.moveToFirst();
		while( !cursor.isAfterLast()) {
			Matter matter = Matter.cursorToMatter(cursor);
			matters.add(matter);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return matters;
	}

}
