package com.kimmyungsun.danger;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
		
		Matter matter = (Matter) list.get(position);
		
		txtMatterName.setText(matter.getMatterName());
		
		List<String> riskInfos = Matter.getRiskInfos(matter);
		StringBuilder sb = new StringBuilder();
		for( int i = 0; i < riskInfos.size(); i++ ) {
			if (sb.length() > 0 ) sb.append(",");
			sb.append(riskInfos.get(i));
		}
		txtRiskInfo.setText(sb.toString());
		
		imgRiskInfo.setImageResource(Matter.getIconType(matter));
		
		return convertView;
	}

}
