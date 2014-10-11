package com.kimmyungsun.danger;

import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;


public class OnMyLocationButtonClickHandler implements
		OnMyLocationButtonClickListener {
	
	
	public static final int BUTTON_DISABLED = 0;
	public static final int BUTTON_ENABLED = 1;
	
	private int buttonStatus = BUTTON_ENABLED;
	private DangerMap dangerMap;

	public OnMyLocationButtonClickHandler(DangerMap dangerMap) {
		this.dangerMap = dangerMap;
	}

	@Override
	public boolean onMyLocationButtonClick() {
		System.out.println("onMyLocationButtonClick");

		buttonStatus = ( buttonStatus + 1 ) % 2;
		
		dangerMap.setButtonStatus(buttonStatus);
		
		return false;
	}


}
