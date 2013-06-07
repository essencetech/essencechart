package com.essence.chart_sdk;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ChartSeekBarListener implements OnSeekBarChangeListener {
	private ChartSeekBarListenerCallback m_onChartSeekBarListener = null;
	
	ChartSeekBarListener(ChartSeekBarListenerCallback callbacks)
	{
		m_onChartSeekBarListener = callbacks;
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (m_onChartSeekBarListener != null)
		{
			m_onChartSeekBarListener.onProgressChanged(seekBar, progress, fromUser);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		if (m_onChartSeekBarListener != null)
		{
			m_onChartSeekBarListener.onStartTrackingTouch(seekBar);
		}
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (m_onChartSeekBarListener != null)
		{
			m_onChartSeekBarListener.onStopTrackingTouch(seekBar);
		}
	}

}
