package com.essence.chart_sdk;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.essence.chart.Chart;

public class ChartColorTemplateDialog extends Dialog implements OnCheckedChangeListener {
	private Chart	m_Chart = null;
	
	public ChartColorTemplateDialog(Context context, Chart esChart) {
		super(context);
		
		m_Chart = esChart;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chart_color_template_dialog);
		
		RadioGroup radioGroupChart =(RadioGroup)findViewById(R.id.radio_chart_color_template);
		radioGroupChart.setOnCheckedChangeListener(this);
	}

	public ChartColorTemplateDialog(Context context, int theme, Chart esChart) {
		super(context, theme);

		m_Chart = esChart;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chart_type_dialog);
		
		RadioGroup radioGroupChart =(RadioGroup)findViewById(R.id.radio_chart_color_template);
		radioGroupChart.setOnCheckedChangeListener(this);
	}

	public ChartColorTemplateDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener, Chart esChart) {
		super(context, cancelable, cancelListener);

		m_Chart = esChart;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chart_type_dialog);
		
		RadioGroup radioGroupChart =(RadioGroup)findViewById(R.id.radio_chart_color_template);
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
		
		int	nTemplate = m_Chart.getColorTemplate();
		RadioGroup radioGroupChart =(RadioGroup)findViewById(R.id.radio_chart_color_template);

		if (radioGroupChart == null)
		{
			return;
		}
		
		radioGroupChart.check(R.id.chart_color_template_00 + nTemplate);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		if (m_Chart == null)
		{
			return;
		}
		
		if (checkedId >= R.id.chart_color_template_00 && checkedId <= R.id.chart_color_template_32)
		{
			int	nIndex = checkedId - R.id.chart_color_template_00;
			int	nTemplateCount = m_Chart.getColorTemplateCount();
			
			if (nTemplateCount > 0 && nIndex < nTemplateCount)
			{
				m_Chart.setColorTemplate(nIndex);
			}
		}
		
	
		m_Chart.invalidate();
	}
	
	
}
