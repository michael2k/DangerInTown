package com.kimmyungsun.danger;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
		
		ImageView imgRiskInfo = (ImageView) convertView.findViewById(R.id.imgiRiskInfo);
		TextView txtMatterName = (TextView) convertView.findViewById(R.id.txtiMatterName);
		TextView txtRiskInfo = (TextView) convertView.findViewById(R.id.txtiRiskInfo);
		
		Matter matter = (Matter) list.get(position);
		
		txtMatterName.setText(matter.getMatterName());
		txtRiskInfo.setText(matter.getRiskInfo());
		
//		int iconType;
//		 if ( matter.getRiskInfo().contains("사고대비")) {
//			 iconType = R.drawable.circle_red;
//		 } else if ( matter.getRiskInfo().contains("발암")) {
//			iconType = R.drawable.circle_yellow;
//		} else if ( matter.getRiskInfo().contains("생식독성")) {
//			iconType = R.drawable.circle_orange;
//		} else if ( matter.getRiskInfo().contains("발달독성")) {
//			iconType = R.drawable.circle_purple;
//		} else {
//			iconType = R.drawable.circle_white;
//		}
//		imgRiskInfo.setImageResource(iconType);
		imgRiskInfo.setImageResource(Matter.getIconType(matter));
		
		return convertView;
	}

}
