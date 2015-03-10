package com.kimmyungsun.danger;

import android.database.Cursor;

public class Matter {
	

	/*
	 * 아이
	 */
	private int id;
	/*
	 * 회사
	 */
	private Company company;
	/*
	 * 회사코드
	 */
	private int companyCode;
	/*
	 * 물질명 
	 */
	private String matterName;
	/*
	 * CAS No.
	 */
	private String casNo;
	/*
	 * 배출량(kg)
	 */
	private float outQty;
	/*
	 * 이동량(kg)
	 */
	private float moveQty;
	/*
	 * 위험정보 
	 */
	private String riskInfo;
	/*
	 * 종양호발부위 
	 */
	private String resultPart;
	
	
	
	public int getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(int companyCode) {
		this.companyCode = companyCode;
	}
	public String getMatterName() {
		return matterName;
	}
	public void setMatterName(String matterName) {
		this.matterName = matterName;
	}
	public String getCasNo() {
		return casNo;
	}
	public void setCasNo(String casNo) {
		this.casNo = casNo;
	}
	public float getOutQty() {
		return outQty;
	}
	public void setOutQty(float outQty) {
		this.outQty = outQty;
	}
	public float getMoveQty() {
		return moveQty;
	}
	public void setMoveQty(float moveQty) {
		this.moveQty = moveQty;
	}
	public String getRiskInfo() {
		return riskInfo;
	}
	public void setRiskInfo(String riskInfo) {
		this.riskInfo = riskInfo;
	}
	public String getResultPart() {
		return resultPart;
	}
	public void setResultPart(String resultPart) {
		this.resultPart = resultPart;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if ( id > 0 ) sb.append(id).append(",");
		sb
			.append(companyCode).append(",")
			.append(matterName).append(",")
			.append(casNo).append(",")
			.append(outQty).append(",")
			.append(moveQty).append(",")
			.append(riskInfo).append(",")
			.append(resultPart)
		;
		return sb.toString();
	}
	
	public static int getIconType(Matter matter ) {
		int iconType = 0;
		
		if (matter.getRiskInfo().contains("사고대비")) {
			iconType = R.drawable.circle_red;
		} else if (matter.getRiskInfo().contains("발암")) {
			iconType = R.drawable.circle_yellow;
		} else if (matter.getRiskInfo().contains("생식독성")) {
			iconType = R.drawable.circle_orange;
		} else if (matter.getRiskInfo().contains("발달독성")) {
			iconType = R.drawable.circle_purple;
		} else {
			iconType = R.drawable.circle_white;
		}
		
		return iconType;
	}
	
	public static Matter cursorToMatter(Cursor cursor) {
		Matter matter = new Matter();
		matter.setId(cursor.getInt(0));
		matter.setCompanyCode(cursor.getInt(1));
		matter.setMatterName(cursor.getString(2));
		matter.setCasNo(cursor.getString(3));
		matter.setOutQty(cursor.getFloat(4));
		matter.setMoveQty(cursor.getFloat(5));
		matter.setRiskInfo(cursor.getString(6));
		matter.setResultPart(cursor.getString(7));
		return matter;
	}
	
	public static Matter newInstance(String s) {
		Matter matter = null;
		
		if ( s != null && !s.isEmpty() ) {
			matter = new Matter();
			String[] atts = s.split("\\|", -1);
			matter.setCompanyCode(Integer.valueOf(atts[0]));
			matter.setMatterName(atts[1]);
			matter.setCasNo(atts[2]);
			matter.setOutQty(atts[3].isEmpty() ? 0 : Float.valueOf(atts[3]));
			matter.setMoveQty(atts[4].isEmpty() ? 0 : Float.valueOf(atts[4]));
			matter.setRiskInfo(atts[5]);
			matter.setResultPart(atts[6]);
		}
		return matter;
	}
	
	

}
