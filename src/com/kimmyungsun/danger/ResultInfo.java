package com.kimmyungsun.danger;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class ResultInfo {
	
	private static final String TAG = ResultInfo.class.getName();
	
	private List<String> confidneces = new ArrayList<String>();
	private List<String> highPossibles = new ArrayList<String>();
	private List<String> possibles = new ArrayList<String>();
	
	public ResultInfo(String resultInfo) {
		if ( resultInfo != null && !resultInfo.isEmpty() ) {
			Log.d(TAG, resultInfo);
			
			int idx1 = resultInfo.indexOf("확실");
			int idx2 = resultInfo.indexOf("가능성 높음");
			int idx3 = resultInfo.indexOf("가능성 있음");
			Log.d(TAG, "idx1[" + idx1 + "], idx2[" + idx2 + "], idx3[" + idx3 + "]");
			
			if ( idx1 != -1 ) {
				if ( idx2 != -1 ) {
					String s1 = resultInfo.substring(idx1, idx2);
					String[] s2 = s1.trim().split(":");
					if ( s2.length > 1 ) {
						String[] s3 = s2[1].trim().split(" ");
						for ( String s4 : s3 ) {
							confidneces.add(s4.trim());
						}
					}
					
					if ( idx3 != 1 ) {
						s1 = resultInfo.substring(idx2, idx3);
						s2 = s1.trim().split(":");
						if ( s2.length > 1 ) {
							String[] s3 = s2[1].trim().split(" ");
							for ( String s4 : s3 ) {
								highPossibles.add(s4.trim());
							}
						}
						
						s1 = resultInfo.substring(idx3);
						s2 = s1.trim().split(":");
						if ( s2.length > 1 ) {
							String[] s3 = s2[1].trim().split(" ");
							for ( String s4 : s3 ) {
								possibles.add(s4.trim());
							}
						}
					}
				} else if ( idx3 != -1 ) {
					String s1 = resultInfo.substring(idx1, idx3);
					String[] s2 = s1.trim().split(":");
					if ( s2.length > 1 ) {
						String[] s3 = s2[1].trim().split(" ");
						for ( String s4 : s3 ) {
							confidneces.add(s4.trim());
						}
					}
					
					s1 = resultInfo.substring(idx3);
					s2 = s1.trim().split(":");
					if ( s2.length > 1 ) {
						String[] s3 = s2[1].trim().split(" ");
						for ( String s4 : s3 ) {
							possibles.add(s4.trim());
						}
					}
				} else {
					String[] s2 = resultInfo.trim().split(":");
					if ( s2.length > 1 ) {
						String[] s3 = s2[1].trim().split(" ");
						for ( String s4 : s3 ) {
							confidneces.add(s4.trim());
						}
					}
				}
			} else {
				if ( idx2 != -1 ) {
					if ( idx3 != -1 ) {
						String s1 = resultInfo.substring(idx2, idx3);
						String[] s2 = s1.trim().split(":");
						if ( s2.length > 1 ) {
							String[] s3 = s2[1].trim().split(" ");
							for ( String s4 : s3 ) {
								highPossibles.add(s4.trim());
							}
						}
						
						s1 = resultInfo.substring(idx3);
						s2 = s1.trim().split(":");
						if ( s2.length > 1 ) {
							String[] s3 = s2[1].trim().split(" ");
							for ( String s4 : s3 ) {
								possibles.add(s4.trim());
							}
						}
						
					} else {
						String[] s2 = resultInfo.trim().split(":");
						if ( s2.length > 1 ) {
							String[] s3 = s2[1].trim().split(" ");
							for ( String s4 : s3 ) {
								highPossibles.add(s4.trim());
							}
						}
					}
				} else {
					if ( idx3 != -1 ) {
						String s1 = resultInfo.substring(idx3);
						String[] s2 = s1.trim().split(":");
						if ( s2.length > 1 ) {
							String[] s3 = s2[1].trim().split(" ");
							for ( String s4 : s3 ) {
								possibles.add(s4.trim());
							}
						}
					}
				}
			}
		}
	}

	public List<String> getConfidneces() {
		return confidneces;
	}

	public List<String> getHighPossibles() {
		return highPossibles;
	}

	public List<String> getPossibles() {
		return possibles;
	}
	
	public String toString() {
		return confidneces + ", " + highPossibles + ", " + possibles;
	}
	

}
