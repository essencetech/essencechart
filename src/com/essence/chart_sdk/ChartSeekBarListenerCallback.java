package com.essence.chart_sdk;

import android.widget.SeekBar;

public interface ChartSeekBarListenerCallback {
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);
	public void onStartTrackingTouch(SeekBar seekBar);
	public void onStopTrackingTouch(SeekBar seekBar);
}
