package com.essence.chart_sdk;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class ChartCheckBoxListener implements OnCheckedChangeListener {
	private ChartCheckBoxListenerCallback m_onChartCheckBoxListener = null;
	
	ChartCheckBoxListener(ChartCheckBoxListenerCallback callbacks)
	{
		m_onChartCheckBoxListener = callbacks;
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (m_onChartCheckBoxListener != null)
		{
			m_onChartCheckBoxListener.onCheckedChanged(buttonView, isChecked);
		}
	}

}
