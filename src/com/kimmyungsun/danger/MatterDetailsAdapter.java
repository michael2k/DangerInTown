package com.kimmyungsun.danger;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MatterDetailsAdapter extends BaseAdapter {
	
	private Context context;
	private int layout;
	private List<Matter> list;
	private LayoutInflater inf;

	public MatterDetailsAdapter(Context context, int layout, List<Matter> list) {
		this.context = context;
		this.layout = layout;
		this.list = list;
		
		inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if ( convertView == null ) {
			convertView = inf.inflate(layout, null);
		}
		
		ImageView imgRiskInfo = (ImageView) convertView.findViewById(R.id.imgRiskInfo);
		TextView txtMatterName = (TextView) convertView.findViewById(R.id.txtMatterName);
		TextView txtRiskInfo = (TextView) convertView.findViewById(R.id.txtRiskInfo);
		TextView txtEtcInfo = (TextView) convertView.findViewById(R.id.txtEtcInfo);
		TextView txtResultInfo = (TextView) convertView.findViewById(R.id.txtResultInfo);
		
		Matter matter = (Matter) list.get(position);
		
		txtMatterName.setText(matter.getMatterName());
		txtRiskInfo.setText(matter.getRiskInfo());
		txtEtcInfo.setText("CAS No:" + matter.getCasNo() + ", 배출량(kg):" + matter.getOutQty() + ", 이동량(kg):" + matter.getMoveQty());
		txtResultInfo.setText(matter.getResultPart());
		
		int iconType;
		if ( matter.getRiskInfo().contains("발암")) {
//			ic.setText("노랑");
			iconType = Color.YELLOW;
		} else if ( matter.getRiskInfo().contains("사고대비")) {
//			ic.setText("주랑");
			iconType = Color.RED;
		} else if ( matter.getRiskInfo().contains("생식독성")) {
//			ic.setText("주랑");
			iconType = Color.BLUE;
		} else if ( matter.getRiskInfo().contains("발달독성")) {
//			ic.setText("보라");
			iconType = Color.MAGENTA;
		} else {
			iconType = R.color.bold_grey;
//			iconType = Color.RED;
		}
		imgRiskInfo.setBackgroundColor(iconType);
		
		return convertView;
	}

}
