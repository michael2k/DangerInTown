package com.kimmyungsun.danger;

import java.util.ArrayList;
import java.util.List;

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

}
