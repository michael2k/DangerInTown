package com.kimmyungsun.danger;

public class DangerItem {
	private long id;
	private String address;
	private String gAddress;
	private float latitude;
	private float longitude;
	private int accuracy;
	private int status;
	
	public static DangerItem newInstance(String s) {
		DangerItem di = null;
		
		if ( s != null && !s.isEmpty() ) {
			di = new DangerItem();
			String[] atts = s.split(",");
			di.setAddress(atts[0]);
			di.setgAddress(atts[1]);
			di.setLatitude(Float.valueOf(atts[2]));
			di.setLongitude(Float.valueOf(atts[3]));
			di.setAccuracy(Integer.valueOf(atts[4]));
			di.setStatus(Integer.valueOf(atts[5]));
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
			.append(address).append(",")
			.append(gAddress).append(",")
			.append(latitude).append(",")
			.append(longitude).append(",")
			.append(accuracy).append(",")
			.append(status)
		;
		return sb.toString();
	}

}
