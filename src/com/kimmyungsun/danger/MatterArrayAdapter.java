package com.kimmyungsun.danger;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MatterArrayAdapter extends BaseAdapter {
	
	private Context context;
	private int layout;
	private List<Matter> list;
	private LayoutInflater inf;

	public MatterArrayAdapter(Context context, int layout, List<Matter> list) {
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
		
		TextView txtRiskIcon = (TextView) convertView.findViewById(R.id.txtRiskIcon);
		TextView txtMatterName = (TextView) convertView.findViewById(R.id.txtMatterName);
		TextView txtRiskInfo = (TextView) convertView.findViewById(R.id.txtRiskInfo);
		
		Matter matter = (Matter) list.get(position);
		
		txtMatterName.setText(matter.getMatterName());
		txtRiskInfo.setText(matter.getRiskInfo());
		
		String iconType;
		if ( matter.getRiskInfo().contains("발암")) {
//			ic.setText("노랑");
			iconType = "노랑";
		} else if ( matter.getRiskInfo().contains("생식독성")) {
//			ic.setText("주랑");
			iconType = "주랑";
		} else if ( matter.getRiskInfo().contains("발달독성")) {
//			ic.setText("보라");
			iconType = "보라";
		} else {
			iconType = "없음";
		}
		txtRiskIcon.setText(iconType);
		
		return convertView;
	}

}
