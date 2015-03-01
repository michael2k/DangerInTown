package com.kimmyungsun.danger;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RiskInfoArrayAdapter extends BaseAdapter {
	
	private Context context;
	private int layout;
	private List<String[]> list = new ArrayList<String[]>();
	private LayoutInflater inf;

	public RiskInfoArrayAdapter(Context context, int layout) {
		this.context = context;
		this.layout = layout;
		
		inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		makeList();
	}
	
	private void makeList() {
		list.add(new String[] {"사고대비물질", "급성독성(急性毒性)·폭발성 등이 강하여 사고발생의 가능성이 높거나 사고가 발생한 경우에 그 피해 규모가 클 것으로 우려되는 화학물질로서 사고 대비·대응계획이 필요하다고 인정되어 물질"});
		list.add(new String[] {"발암성", "암을 발생시키는 물질, 세계보건기구 국제암연구소(IARC)는 1급 인체 발암성 물질(1), 2급 인체 발암성 추정물질(2A), 3급 인체 발암성 가능물질(2B)으로 구분하고 있다."});
		list.add(new String[] {"생식독성", "생식능력의 장애(불임, 임신지연, 생리불순)을 일으키거나, 유산을 발생시키는 물질."});
		list.add(new String[] {"발달독성", "발달장애를 발생시키는 물질(엄마가 톨루엔에 노출되면 말이 늦는 문제 발생, 임신초기 화학물질 노출시 자폐 발생 등)"});
		list.add(new String[] {"환경호르몬", "인체의 호르몬과 유사하게 인식되어 호르몬을 교란시키는 물질"});
		list.add(new String[] {"변이원성", "세포 또는 생물집단에게 돌연변이를 발생시키는 물질"});
		list.add(new String[] {"잔류성/농축성/독성(PBTs)", "생태계에서 잘 없어지지 않고 먹이사슬을 통해 계속 축적되며, 사람 몸속에 들어와사 잘 빠져나가지 않는 독성물질"});
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
		
		TextView tstRiskInfoName = (TextView) convertView.findViewById(R.id.txtRiskInfoName);
		TextView txtRiskInfoDetail = (TextView) convertView.findViewById(R.id.txtRiskInfoDetail);
		
		String[] riskInfo = (String[]) list.get(position);
		
		tstRiskInfoName.setText(riskInfo[0]);
		txtRiskInfoDetail.setText(riskInfo[1]);
		
		return convertView;
	}

}
