package org.safedu.danger.provider;

import java.util.ArrayList;
import java.util.List;

import org.safedu.danger.Company;
import org.safedu.danger.Matter;
import org.safedu.danger.constanst.IDangerConstants;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class DangersDataSource implements IDangerConstants {
	
	private Context context;
	
	public DangersDataSource(Context context) {
		this.context = context;
	}
	
	public List<Company> getAllCompanys() {
		
		List<Company> companys = new ArrayList<Company>();
		
		Cursor cursor = context.getContentResolver()
				.query(Uri.parse("content://" + AUTHORITY + "/companys"), DangerDBHelper.COMPANY_ALL_COLUMNS, null, null, null);
		
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
				.query(Uri.parse("content://" + AUTHORITY + "/companys/" + id), DangerDBHelper.COMPANY_ALL_COLUMNS, null, new String[] { String.valueOf(id) }, null);
		cursor.moveToFirst();
		Company c = Company.cursorToCompany(cursor);
		cursor.close();
		return c;
	}

	public List<Company> searchCompanys(String searchString) {
		List<Company> companys = new ArrayList<Company>();
		
		Cursor cursor = context.getContentResolver()
				.query(Uri.parse("content://" + AUTHORITY + "/companys"), DangerDBHelper.COMPANY_ALL_COLUMNS, null, new String[] { searchString }, null);
		
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
				.query(Uri.parse("content://" + AUTHORITY + "/matters"), DangerDBHelper.MATTER_ALL_COLUMNS, null, new String[] {String.valueOf(company.getId())}, null);
		
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

	public List<Matter> searchMatters(String companyId) {
		List<Matter> matters = new ArrayList<Matter>();
		
		Cursor cursor = context.getContentResolver()
				.query(Uri.parse("content://" + AUTHORITY + "/matters"), DangerDBHelper.MATTER_ALL_COLUMNS, null, new String[] {companyId}, null);
		
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

	public Matter getMatter(long id) {
		Cursor cursor = context.getContentResolver()
				.query(Uri.parse("content://" + AUTHORITY + "/matters/" + id), DangerDBHelper.MATTER_ALL_COLUMNS, null, new String[] { String.valueOf(id) }, null);
		cursor.moveToFirst();
		Matter m = Matter.cursorToMatter(cursor);
		cursor.close();
		return m;
	}

}
