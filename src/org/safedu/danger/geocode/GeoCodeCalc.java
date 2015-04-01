package org.safedu.danger.geocode;

public class GeoCodeCalc{ 
	public static final double EarthRadiusInMiles = 3956.0; 
	public static final double EarthRadiusInKilometers = 6367.0; 
	public static double ToRadian(double val) { return val * (Math.PI / 180); } 
	public static double DiffRadian(double val1, double val2) { return ToRadian(val2) - ToRadian(val1); } 
	/// <summary> 
	/// Calculate the distance between two geocodes. Defaults to using Kilometer. 
	/// </summary> 
	public static double CalcDistance(double lat1, double lng1, double lat2, double lng2) { 
		return CalcDistance(lat1, lng1, lat2, lng2, GeoCodeCalcMeasurement.Kilometers); 
	}
	 /// <summary> 
	/// Calculate the distance between two geocodes. 
	/// </summary> 
	public static double CalcDistance(double lat1, double lng1, double lat2, double lng2, int m) { 
		double radius = GeoCodeCalc.EarthRadiusInKilometers; 
		if (m == GeoCodeCalcMeasurement.Miles) { 
			radius = GeoCodeCalc.EarthRadiusInMiles; 
		} 
		return radius * 2 * Math.asin( 
				Math.min(1, Math.sqrt( 
						( Math.pow(Math.sin((DiffRadian(lat1, lat2)) / 2.0), 2.0) 
								+ Math.cos(ToRadian(lat1)) 
								* Math.cos(ToRadian(lat2))
								* Math.pow(Math.sin((DiffRadian(lng1, lng2)) / 2.0), 2.0) ) ) ) ); 
	}
}
