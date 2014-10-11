package com.kimmyungsun.danger;

import com.google.android.gms.maps.GoogleMap;

public interface DangerMap {

	public abstract GoogleMap getGoogleMap();

	public abstract DangersDataSource getDangerDataSource();

	public abstract void setButtonStatus(int buttonStatus);

	public abstract int getButtonStatus();

}
