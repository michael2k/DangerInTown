package com.kimmyungsun.danger;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.graphics.Color;

public class Company {
	private long id;
	private String companyName;
	private String address;
	private String gAddress;
	private float latitude = 0f;
	private float longitude = 0f;
	private int accuracy = -1;
	private int status;
	
	private List<Matter> matters = new ArrayList<Matter>();
	
	public static Company newInstance(String s) {
		Company di = null;
		
		if ( s != null && !s.isEmpty() ) {
			di = new Company();
			String[] atts = s.split(",", -1);
			di.setId(Long.valueOf(atts[0]));
			di.setCompanyName(atts[1]);
			di.setAddress(atts[2]);
			di.setgAddress(atts[3]);
			di.setLatitude(atts[4].isEmpty() ? 0 : Float.valueOf(atts[4]));
			di.setLongitude(atts[5].isEmpty() ? 0 : Float.valueOf(atts[5]));
			di.setAccuracy(atts[6].isEmpty() ? -1 : Integer.valueOf(atts[6]));
			di.setStatus(Integer.valueOf(atts[7]));
		}
		return di;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getgAddress() {
		return gAddress;
	}
	public void setgAddress(String gAddress) {
		this.gAddress = gAddress;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if ( id > 0 ) sb.append(id).append(",");
		sb
			.append(companyName).append(",")
			.append(address).append(",")
			.append(gAddress).append(",")
			.append(latitude).append(",")
			.append(longitude).append(",")
			.append(accuracy).append(",")
			.append(status)
		;
		if ( matters.size() > 0 ) {
			sb.append(matters.toString());
		}
		return sb.toString();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void addMatter(Matter matter) {
		matters.add(matter);
	}

	public static Company cursorToCompany(Cursor cursor) {
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
	
	public static int getIconType( List<Matter> matters, int iSize ) {
		int iconType = 0;
		int iRed = 0;
		int iYellow = 0;
		int iOrange = 0;
		int iPurple = 0;
		int iDarkGreen = 0;
		int iBlue = 0;
		int iDarkBlue = 0;
		
		for (Matter matter : matters) {
			if (matter.getRiskInfo().contains("발암")) {
				iYellow++;
			} else if (matter.getRiskInfo().contains("사고대비")) {
				iRed++;
			} else if (matter.getRiskInfo().contains("생식독성")) {
				iOrange++;
			} else if (matter.getRiskInfo().contains("발달독성")) {
				iPurple++;
			} else if (matter.getRiskInfo().contains("환경호르몬")) {
				iDarkGreen++;
			} else if (matter.getRiskInfo().contains("변이원성")) {
				iBlue++;
			} else if (matter.getRiskInfo().contains("잔류성")
					|| matter.getRiskInfo().contains("농축성")
					|| matter.getRiskInfo().contains("독성") ) {
				iDarkBlue++;
			} 
		}
		if ( iSize == 0 ) {
			if ( iRed > 0 ) {
				iconType = R.drawable.marker_red_s;
			} else if ( iYellow > 0 ) {
				iconType = R.drawable.marker_yellow_s;
			} else if ( iOrange > 0 ) {
				iconType = R.drawable.marker_orange_s;
			} else if ( iPurple > 0 ) {
				iconType = R.drawable.marker_purple_s;
			} else if ( iDarkGreen > 0 ) {
				iconType = R.drawable.marker_dark_green_s;
			} else if ( iBlue > 0 ) {
				iconType = R.drawable.marker_blue_s;
			} else if ( iDarkBlue > 0 ) {
				iconType = R.drawable.marker_dark_blue_s;
			} else {
				iconType = R.drawable.marker_white_s;
			}
		} else {
			if ( iRed > 0 ) {
				iconType = R.drawable.marker_red_b;
			} else if ( iYellow > 0 ) {
				iconType = R.drawable.marker_yellow_b;
			} else if ( iOrange > 0 ) {
				iconType = R.drawable.marker_orange_b;
			} else if ( iPurple > 0 ) {
				iconType = R.drawable.marker_purple_b;
			} else if ( iDarkGreen > 0 ) {
				iconType = R.drawable.marker_dark_green_b;
			} else if ( iBlue > 0 ) {
				iconType = R.drawable.marker_blue_b;
			} else if ( iDarkBlue > 0 ) {
				iconType = R.drawable.marker_dark_blue_b;
			} else {
				iconType = R.drawable.marker_white_b;
			}
			
		}
		
		return iconType;
	}

}
