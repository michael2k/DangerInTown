package com.kimmyungsun.danger;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class ResultInfo {
	
	private static final String TAG = ResultInfo.class.getName();
	
	private List<String> confidences = new ArrayList<String>();
	private List<String> highPossibles = new ArrayList<String>();
	private List<String> possibles = new ArrayList<String>();
	
	public ResultInfo(String resultInfo) {
		if ( resultInfo != null && !resultInfo.trim().isEmpty() ) {
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
						String[] s3 = s2[1].trim().split(",");
						for ( String s4 : s3 ) {
							confidences.add(s4.trim());
						}
					}
					
					if ( idx3 != 1 ) {
						s1 = resultInfo.substring(idx2, idx3);
						s2 = s1.trim().split(":");
						if ( s2.length > 1 ) {
							String[] s3 = s2[1].trim().split(",");
							for ( String s4 : s3 ) {
								highPossibles.add(s4.trim());
							}
						}
						
						s1 = resultInfo.substring(idx3);
						s2 = s1.trim().split(":");
						if ( s2.length > 1 ) {
							String[] s3 = s2[1].trim().split(",");
							for ( String s4 : s3 ) {
								possibles.add(s4.trim());
							}
						}
					}
				} else if ( idx3 != -1 ) {
					String s1 = resultInfo.substring(idx1, idx3);
					String[] s2 = s1.trim().split(":");
					if ( s2.length > 1 ) {
						String[] s3 = s2[1].trim().split(",");
						for ( String s4 : s3 ) {
							confidences.add(s4.trim());
						}
					}
					
					s1 = resultInfo.substring(idx3);
					s2 = s1.trim().split(":");
					if ( s2.length > 1 ) {
						String[] s3 = s2[1].trim().split(",");
						for ( String s4 : s3 ) {
							possibles.add(s4.trim());
						}
					}
				} else {
					String[] s2 = resultInfo.trim().split(":");
					if ( s2.length > 1 ) {
						String[] s3 = s2[1].trim().split(",");
						for ( String s4 : s3 ) {
							confidences.add(s4.trim());
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

	public List<String> getConfidences() {
		return confidences;
	}

	public List<String> getHighPossibles() {
		return highPossibles;
	}

	public List<String> getPossibles() {
		return possibles;
	}
	
	public String toString() {
		return confidences + ", " + highPossibles + ", " + possibles;
	}
	
	public String toColoredString() {
		StringBuilder sb = new StringBuilder();
//		return confidneces + ", " + highPossibles + ", " + possibles;
		if ( confidences.size() > 0 ) {
			sb.append("<font color=#ff0000>");
			for ( int i = 0; i < confidences.size(); i++ ) {
				if ( i > 0 ) sb.append(",");
				sb.append(confidences.get(i));
			}
			sb.append("</font>");
		}
		if ( highPossibles.size() > 0 ) {
			if ( sb.length() > 0) sb.append("<br />");
			sb.append("<font color=#ffff00>");
			for ( int i = 0; i < highPossibles.size(); i++ ) {
				if ( i > 0 ) sb.append(",");
				sb.append(highPossibles.get(i));
			}
			sb.append("</font>");
		}
		if ( possibles.size() > 0 ) {
			if ( sb.length() > 0) sb.append("<br />");
			sb.append("<font color=#ff8000>");
			for( int i = 0; i < possibles.size(); i++ ) {
				if ( i > 0 ) sb.append(",");
				sb.append(possibles.get(i));
			}
			sb.append("</font>");
		}
		return sb.toString();
	}
	

}
