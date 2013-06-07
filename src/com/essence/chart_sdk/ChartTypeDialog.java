package com.essence.chart_sdk;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.essence.chart.Chart;

public class ChartTypeDialog extends Dialog implements OnCheckedChangeListener {
	private Chart	m_Chart = null;
	
	public ChartTypeDialog(Context context, Chart esChart) {
		super(context);
		
		m_Chart = esChart;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chart_type_dialog);
		
		RadioGroup radioGroupChart =(RadioGroup)findViewById(R.id.radio_chart_type);
		radioGroupChart.setOnCheckedChangeListener(this);
	}

	public ChartTypeDialog(Context context, int theme, Chart esChart) {
		super(context, theme);

		m_Chart = esChart;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chart_type_dialog);
		
		RadioGroup radioGroupChart =(RadioGroup)findViewById(R.id.radio_chart_type);
		radioGroupChart.setOnCheckedChangeListener(this);
	}

	public ChartTypeDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener, Chart esChart) {
		super(context, cancelable, cancelListener);

		m_Chart = esChart;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chart_type_dialog);
		
		RadioGroup radioGroupChart =(RadioGroup)findViewById(R.id.radio_chart_type);
		radioGroupChart.setOnCheckedChangeListener(this);
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if (m_Chart == null)
		{
			return;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		if (m_Chart == null)
		{
			return;
		}
		
		m_Chart.invalidate();
	}
	
	
}
