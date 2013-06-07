package com.essence.chart;

import android.view.MotionEvent;

public interface ChartCallback {
	public boolean onTouchEventBefore(MotionEvent event);
	public boolean onTouchEventAfter(MotionEvent event);
}
