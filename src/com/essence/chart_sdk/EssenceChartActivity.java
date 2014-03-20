package com.essence.chart_sdk;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.view.Gravity;

import com.essence.chart.Chart;
import com.essence.chart.ChartCallback;
import com.essence.chart.GridData;

import java.util.Map;
import java.util.HashMap;

public class EssenceChartActivity extends Activity implements View.OnClickListener, OnCheckedChangeListener {
	private	Chart		m_Chart = null;
	private	String		m_Version = "unknown";
	private	float		m_fChartZoom = 1.0f;
	private long		m_lOldTick = 0;
	private	Random		m_Random = null;
	
	private Button		m_ButtonChartType = null;
	private Button		m_ButtonChartStyle = null;
	private SeekBar		m_SeekBarChartSize = null;
	private SeekBar		m_SeekBarChartRotateX = null;
	private SeekBar		m_SeekBarChartRotateY = null;
	private SeekBar		m_SeekBarChartRotateZ = null;
	private SeekBar		m_SeekBarChartAlpha = null;
	private CheckBox	m_CheckBoxChartXAxis = null;
	private CheckBox	m_CheckBoxChartYAxis = null;
	private CheckBox	m_CheckBoxChartY2Axis = null;
	private CheckBox	m_CheckBoxChartZAxis = null;
	private CheckBox	m_CheckBoxChartTitle = null;
	private CheckBox	m_CheckBoxChartLegend = null;
	private CheckBox	m_CheckBoxChartAnimation = null;
	private CheckBox	m_CheckBoxChartAnimationLoop = null;
	private CheckBox	m_CheckBoxChartDataQueue = null;
	private	Switch		m_SwitchChartXAxis = null;
	private	Switch		m_SwitchChartYAxis = null;
	private	Switch		m_SwitchChartY2Axis = null;
	private	Switch		m_SwitchChartZAxis = null;
	private	Switch		m_SwitchChartTitle = null;
	private	Switch		m_SwitchChartLegend = null;
	private	Switch		m_SwitchChartAnimation = null;
	private	Switch		m_SwitchChartAnimationLoop = null;
	private Switch 		m_SwitchChartDataQueue = null;
	private ChartSeekBarListener			m_ChartSeekBarListener = null;
	private ChartCheckBoxListener			m_ChartCheckBoxListener = null;
	private Button		m_ButtonChartPropertyTitle = null;
	private Button		m_ButtonChartPropertyLegend = null;
	private Button		m_ButtonChartPropertyBackdrop = null;
	private Button		m_ButtonChartPropertyPlotBackdrop = null;
	private Button		m_ButtonChartPropertyXAxis = null;
	private Button		m_ButtonChartPropertyYAxis = null;
	private Button		m_ButtonChartPropertyY2Axis = null;
	private Button		m_ButtonChartPropertyZAxis = null;
	private Button		m_ButtonSeriesProperty = null;
	
	public ChartProperty m_DlgChartProperty = null;
	public SeriesProperty m_DlgSeriesProperty = null;
	
	public class ApplyAxisPerChartStyle
	{
		public ApplyAxisPerChartStyle(boolean x, boolean y, boolean y2, boolean z)
		{
			X = x;
			Y = y;
			Y2 = y2;
			Z = z;
		}

		public boolean X;
		public boolean Y;
		public boolean Y2;
		public boolean Z;
	}
	
	Map<String, ApplyAxisPerChartStyle> m_mapApplyAxisPerChartStyle = new HashMap<String, ApplyAxisPerChartStyle>();
	private ChartSeekBarListenerCallback	m_ChartSeekBarListenerCallback = new ChartSeekBarListenerCallback()
	{

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (m_Chart == null || seekBar == null)
			{
				return;
			}
			
			int nID = seekBar.getId();
			
			if (nID == R.id.seekbar_chart_size)
			{
				m_fChartZoom = (progress / 100.0f);
				updateChild();
			}
			else if (nID == R.id.seekbar_chart_rotate_x)
			{
				m_Chart.setRotation(progress);
			}
			else if (nID == R.id.seekbar_chart_rotate_y)
			{
				m_Chart.setElevation(progress - 90);
			}
			else if (nID == R.id.seekbar_chart_rotate_z)
			{
				m_Chart.setPerspective((progress + 50) / 100.0f);
			}
			else if (nID == R.id.seekbar_chart_alpha)
			{
				m_Chart.setChartAlpha(progress);
			}
			else
			{
				return;
			}
			
			m_Chart.postInvalidate();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			
		}
	};
	
	private ChartCheckBoxListenerCallback	m_ChartCheckBoxListenerCallback = new ChartCheckBoxListenerCallback()
	{

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
			if (m_Chart == null || buttonView == null) {
				return;
			}

			int nID = buttonView.getId();

			if (nID == R.id.checkbox_chart_xaxis || nID == R.id.switch_chart_xaxis) {
				m_Chart.setXAxisVisible(isChecked);
				m_ButtonChartPropertyXAxis.setEnabled(isChecked);
			}
			else if (nID == R.id.checkbox_chart_yaxis || nID == R.id.switch_chart_yaxis) {
				m_Chart.setYAxisVisible(isChecked);
				m_ButtonChartPropertyYAxis.setEnabled(isChecked);
			}
			else if (nID == R.id.checkbox_chart_y2axis || nID == R.id.switch_chart_y2axis) {
				m_Chart.setY2AxisVisible(isChecked);
				m_ButtonChartPropertyY2Axis.setEnabled(isChecked);
			}
			else if (nID == R.id.checkbox_chart_zaxis || nID == R.id.switch_chart_zaxis) {
				m_Chart.setZAxisVisible(isChecked);
				m_ButtonChartPropertyZAxis.setEnabled(isChecked);
			}
			else if (nID == R.id.checkbox_chart_title || nID == R.id.switch_chart_title) {
				m_Chart.setTitleVisible(isChecked);
				
				m_ButtonChartPropertyTitle.setEnabled(isChecked);
			}
			else if (nID == R.id.checkbox_chart_legend || nID == R.id.switch_chart_legend) {
				m_Chart.setLegendVisible(isChecked);
				m_ButtonChartPropertyLegend.setEnabled(isChecked);
			}
			else if (nID == R.id.switch_chart_animation
					|| nID == R.id.switch_chart_animation_loop
					|| nID == R.id.checkbox_chart_animation
					|| nID == R.id.checkbox_chart_animation_loop) {
				updateAnimationButton(nID, isChecked);
			}
			else if (nID == R.id.switch_chart_data_queue
					|| nID == R.id.checkbox_chart_data_queue) {
				updateAnimationButton(nID, isChecked);
			}
			else {
				return;
			}
			
			m_Chart.postInvalidate();
		}
	};
	
	private ChartCallback	m_ChartCallback = new ChartCallback()
	{
		public boolean onTouchEventBefore(MotionEvent event)
		{
			hideShideMenuView();
			
			return false;
		}
		
		public boolean onTouchEventAfter(MotionEvent event)
		{
			if (m_Chart == null 
					|| m_SeekBarChartRotateX == null
					|| m_SeekBarChartRotateY == null
					|| m_SeekBarChartRotateZ == null)
				{
					return false;
				}
				
			m_SeekBarChartRotateX.setProgress((int)m_Chart.getRotation());
			m_SeekBarChartRotateY.setProgress((int)(m_Chart.getElevation() + 90.0f));
			m_SeekBarChartRotateZ.setProgress((int)((m_Chart.getPerspective() * 100.0f) - 50.0f));
				
			return false;
		}
	};
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg){
	           super.handleMessage(msg);	           
	           
	           if (m_Chart.isDataQueue() == true)
	           {
	        	   upateDataQueue();	           
	           }

	           this.sendEmptyMessageDelayed(0, m_Chart._DMA_INTERVAL);           
	   }
	};
	
	private void upateDataQueue() {
		boolean bIsPossible = false;
		if (m_SwitchChartDataQueue != null) {
			if (m_SwitchChartDataQueue.isChecked() == true) {
				bIsPossible = true;
			}
		}
		else if (m_CheckBoxChartDataQueue != null) {
			if (m_CheckBoxChartDataQueue.isChecked() == true) {
				bIsPossible = true;
			}
		}
		
		if (bIsPossible == false) {
			return;
		}
		
		long time = System.currentTimeMillis();
		if ((time - m_lOldTick) >= m_Chart._DMA_INTERVAL)
		{
			int nRows = 1;
			int nColumns = 3;
			GridData	gridData = new GridData(nRows, nColumns);
			
			for (int nRow = 0; nRow < nRows; nRow++)
			{
				for(int nColumn = 0; nColumn < nColumns; nColumn++)
				{
					double dValue = m_Random.nextInt(2000);
					gridData.setCell(nRow, nColumn, dValue);
				}
			}
			
			m_Chart.pushData(gridData, -1);
			
			m_lOldTick = time;
		}
	}
	
	public int getRotation()
	{
		WindowManager	windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		Display			display = windowManager.getDefaultDisplay();
		
		int nRotation = display.getRotation();
		
		return nRotation;
	}
	
	public int	getOrientation() {
		WindowManager	windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		Display 		display = windowManager.getDefaultDisplay();
		DisplayMetrics	displayMetrics = new DisplayMetrics();
		
		display.getMetrics(displayMetrics);
		
		if (displayMetrics.widthPixels > displayMetrics.heightPixels)
		{
			return Configuration.ORIENTATION_LANDSCAPE;
		}
		
		return Configuration.ORIENTATION_PORTRAIT;
	}
	
	protected DisplayMetrics getDisplayMetrics() {
		WindowManager	windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		Display 		display = windowManager.getDefaultDisplay();
		DisplayMetrics	displayMetrics = new DisplayMetrics();
		
		display.getMetrics(displayMetrics);
        
		return displayMetrics;
	}
	
	protected boolean updateChild()
	{
		if (m_Chart == null)
		{
			return false;
		}
		
		Window	window = getWindow();
		if (window == null)
		{
			return false;
		}
		
		int							nOrientation = getOrientation();
		int							nTopBarHeight = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		DisplayMetrics				displayMetrics = getDisplayMetrics();
		int							nViewWidth = 0;
		int							nViewHeight = 0;
		LayoutParams 				layoutParamsChart = m_Chart.getLayoutParams();
		WindowManager.LayoutParams	layoutParamsWindowManager = new WindowManager.LayoutParams();
		ScrollView					scrollViewChartAdjustment = (ScrollView)findViewById(R.id.scrollview_chart_adjustment);
		
		if (displayMetrics == null || layoutParamsChart == null || layoutParamsWindowManager == null || scrollViewChartAdjustment == null)
		{
			return false;
		}
		
		if (nOrientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			nViewWidth = displayMetrics.widthPixels;
			nViewHeight = displayMetrics.heightPixels - nTopBarHeight;
			
			layoutParamsChart.width = (int)(m_fChartZoom * nViewHeight);
			layoutParamsChart.height = (int)(m_fChartZoom * nViewHeight);
			layoutParamsWindowManager.x = (nViewHeight - layoutParamsChart.height) / 2;
			layoutParamsWindowManager.y = (nViewHeight - layoutParamsChart.height) / 2;
			
			{
				ViewGroup.MarginLayoutParams layout_position = new ViewGroup.MarginLayoutParams(m_Chart.getLayoutParams());
				
				layout_position.setMargins(layoutParamsWindowManager.x, layoutParamsWindowManager.y, layoutParamsWindowManager.x, layoutParamsWindowManager.y);
				m_Chart.setLayoutParams(new RelativeLayout.LayoutParams(layoutParamsChart));
				m_Chart.setLayoutParams(new RelativeLayout.LayoutParams(layout_position));
			}
			
			{
				ViewGroup.MarginLayoutParams layout_position = new ViewGroup.MarginLayoutParams(scrollViewChartAdjustment.getLayoutParams());
				layout_position.setMargins(nViewHeight, 0 , 0, 0);
				scrollViewChartAdjustment.setLayoutParams(new RelativeLayout.LayoutParams(layout_position));
			}
		}
		else
		{
			nViewWidth = displayMetrics.widthPixels;
			nViewHeight = displayMetrics.heightPixels - nTopBarHeight;
			
			layoutParamsChart.width = (int)(m_fChartZoom * nViewWidth);
			layoutParamsChart.height = (int)(m_fChartZoom * nViewWidth);
			layoutParamsWindowManager.x = (nViewWidth - layoutParamsChart.width) / 2;
			layoutParamsWindowManager.y = (nViewWidth - layoutParamsChart.width) / 2;
			
			{
				ViewGroup.MarginLayoutParams layout_position = new ViewGroup.MarginLayoutParams(m_Chart.getLayoutParams());
				
				layout_position.setMargins(layoutParamsWindowManager.x, layoutParamsWindowManager.y, layoutParamsWindowManager.x, layoutParamsWindowManager.y);
				m_Chart.setLayoutParams(new RelativeLayout.LayoutParams(layoutParamsChart));
				m_Chart.setLayoutParams(new RelativeLayout.LayoutParams(layout_position));
			}
			
			
			{
				ViewGroup.MarginLayoutParams layout_position = new ViewGroup.MarginLayoutParams(scrollViewChartAdjustment.getLayoutParams());
				layout_position.setMargins(0, nViewWidth, 0, 0);
				scrollViewChartAdjustment.setLayoutParams(new RelativeLayout.LayoutParams(layout_position));
			}
		}
		
		{
			RelativeLayout relativeLayoutChartType = (RelativeLayout)findViewById(R.id.relative_layout_chart_type);
			RelativeLayout relativeLayoutChartColorTemplate = (RelativeLayout)findViewById(R.id.relative_layout_chart_color_template);
			ViewGroup.MarginLayoutParams marginLayoutParamsChartAdjustment = (ViewGroup.MarginLayoutParams)scrollViewChartAdjustment.getLayoutParams();
			
			if (relativeLayoutChartType == null || relativeLayoutChartColorTemplate == null || marginLayoutParamsChartAdjustment == null)
			{
				return false;
			}
			
			if (relativeLayoutChartType.getVisibility() == View.VISIBLE)
			{
				ViewGroup.MarginLayoutParams marginLayoutParamsChartType = new ViewGroup.MarginLayoutParams(marginLayoutParamsChartAdjustment);

				marginLayoutParamsChartType.setMargins(marginLayoutParamsChartAdjustment.leftMargin, marginLayoutParamsChartAdjustment.topMargin, marginLayoutParamsChartAdjustment.rightMargin, marginLayoutParamsChartAdjustment.bottomMargin);
				relativeLayoutChartType.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParamsChartType));
			}
			
			if (relativeLayoutChartColorTemplate.getVisibility() == View.VISIBLE)
			{
				ViewGroup.MarginLayoutParams marginLayoutParamsChartColorTemplate = new ViewGroup.MarginLayoutParams(marginLayoutParamsChartAdjustment);

				marginLayoutParamsChartColorTemplate.setMargins(marginLayoutParamsChartAdjustment.leftMargin, marginLayoutParamsChartAdjustment.topMargin, marginLayoutParamsChartAdjustment.rightMargin, marginLayoutParamsChartAdjustment.bottomMargin);
				relativeLayoutChartColorTemplate.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParamsChartColorTemplate));
			}
		}
		
		return true;
	}
	
	protected void showSlideMenuView(int id)
	{
		ScrollView		scrollViewChartAdjustment = (ScrollView)findViewById(R.id.scrollview_chart_adjustment);
		RelativeLayout	releativeLayoutMenuView = (RelativeLayout)findViewById(id);
		
		if (scrollViewChartAdjustment == null || releativeLayoutMenuView == null)
		{
			return;
		}
		
		if (releativeLayoutMenuView.getVisibility() == View.INVISIBLE)
		{
			ViewGroup.MarginLayoutParams marginLayoutParamsChartAdjustment = (ViewGroup.MarginLayoutParams)scrollViewChartAdjustment.getLayoutParams();
			
			if (marginLayoutParamsChartAdjustment == null)
			{
				return;
			}
			
			ViewGroup.MarginLayoutParams marginLayoutParamsChartType = new ViewGroup.MarginLayoutParams(marginLayoutParamsChartAdjustment);

			marginLayoutParamsChartType.setMargins(marginLayoutParamsChartAdjustment.leftMargin, marginLayoutParamsChartAdjustment.topMargin, marginLayoutParamsChartAdjustment.rightMargin, marginLayoutParamsChartAdjustment.bottomMargin);
			releativeLayoutMenuView.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParamsChartType));

			Animation animationShow = AnimationUtils.loadAnimation(this, R.anim.slide_from_left);
			
			animationShow.setStartOffset(0);
			releativeLayoutMenuView.startAnimation(animationShow);
			releativeLayoutMenuView.setVisibility(View.VISIBLE);
		}
	}
	
	protected boolean hideSlideMenuView(int id)
	{
		ScrollView		scrollViewChartAdjustment = (ScrollView)findViewById(R.id.scrollview_chart_adjustment);
		RelativeLayout	releativeLayoutMenuView = (RelativeLayout)findViewById(id);
		
		if (scrollViewChartAdjustment == null || releativeLayoutMenuView == null)
		{
			return false;
		}
		
		if (releativeLayoutMenuView.getVisibility() == View.VISIBLE)
		{
			Animation animationHide = AnimationUtils.loadAnimation(this, R.anim.slide_to_left);

			animationHide.setStartOffset(0);
			releativeLayoutMenuView.startAnimation(animationHide);
			releativeLayoutMenuView.setVisibility(View.INVISIBLE);
			
			return true;
		}
		
		return false;
	}
	
	protected boolean hideShideMenuView()
	{
		RelativeLayout releativeLayoutChartType = (RelativeLayout) findViewById(R.id.relative_layout_chart_type);
		if (releativeLayoutChartType != null && releativeLayoutChartType.getVisibility() == View.VISIBLE)
		{
			return hideSlideMenuView(R.id.relative_layout_chart_type);			
		}
		
		RelativeLayout releativeLayoutChartStyle = (RelativeLayout) findViewById(R.id.relative_layout_chart_color_template);
		if (releativeLayoutChartStyle.getVisibility() == View.VISIBLE)
		{
			return hideSlideMenuView(R.id.relative_layout_chart_color_template);	
		}
		
		return false;
	}
	
	protected void toggleSlideMenuView(int id)
	{
		ScrollView		scrollViewChartAdjustment = (ScrollView)findViewById(R.id.scrollview_chart_adjustment);
		RelativeLayout	releativeLayoutMenuView = (RelativeLayout)findViewById(id);
		
		if (scrollViewChartAdjustment == null || releativeLayoutMenuView == null)
		{
			return;
		}
		
		if (releativeLayoutMenuView.getVisibility() == View.VISIBLE)
		{
			Animation animationHide = AnimationUtils.loadAnimation(this, R.anim.slide_to_left);

			animationHide.setStartOffset(0);
			releativeLayoutMenuView.startAnimation(animationHide);
			releativeLayoutMenuView.setVisibility(View.INVISIBLE);
		}
		else
		{
			ViewGroup.MarginLayoutParams marginLayoutParamsChartAdjustment = (ViewGroup.MarginLayoutParams)scrollViewChartAdjustment.getLayoutParams();
			
			if (marginLayoutParamsChartAdjustment == null)
			{
				return;
			}
			
			ViewGroup.MarginLayoutParams marginLayoutParamsChartType = new ViewGroup.MarginLayoutParams(marginLayoutParamsChartAdjustment);

			marginLayoutParamsChartType.setMargins(marginLayoutParamsChartAdjustment.leftMargin, marginLayoutParamsChartAdjustment.topMargin, marginLayoutParamsChartAdjustment.rightMargin, marginLayoutParamsChartAdjustment.bottomMargin);
			releativeLayoutMenuView.setLayoutParams(new RelativeLayout.LayoutParams(marginLayoutParamsChartType));

			Animation animationShow = AnimationUtils.loadAnimation(this, R.anim.slide_from_left);
			
			animationShow.setStartOffset(0);
			releativeLayoutMenuView.startAnimation(animationShow);
			releativeLayoutMenuView.setVisibility(View.VISIBLE);
		}
	}
	
	protected void toogleEnableAnimationButton()
	{
		if (m_Chart == null)
		{
			return;
		}
		
		boolean	bAnimation = m_Chart.isAnimation();
		boolean bDataQueue = m_Chart.isDataQueue();
		
		if (bAnimation)
		{
			if (m_SwitchChartAnimation != null && m_SwitchChartAnimationLoop != null) {
				m_SwitchChartAnimation.setEnabled(true);
			}
			
			if (m_CheckBoxChartAnimation != null && m_CheckBoxChartAnimationLoop != null) {
				m_CheckBoxChartAnimation.setEnabled(true);
			}			
		}
		else
		{
			if (m_SwitchChartAnimation != null && m_SwitchChartAnimationLoop != null) {
				m_SwitchChartAnimation.setEnabled(false);
			}
			
			if (m_CheckBoxChartAnimation != null && m_CheckBoxChartAnimationLoop != null) {
				m_CheckBoxChartAnimation.setEnabled(false);
			}
		}
		
		if (m_SwitchChartAnimationLoop != null) {
			m_SwitchChartAnimationLoop.setEnabled(m_SwitchChartAnimation.isEnabled());
		}
		
		if (m_CheckBoxChartAnimationLoop != null) {
			m_CheckBoxChartAnimationLoop.setEnabled(m_CheckBoxChartAnimation.isEnabled());
		}
		
		if (m_SwitchChartDataQueue != null) {
			m_SwitchChartDataQueue.setEnabled(bDataQueue);
		}
		if (m_CheckBoxChartDataQueue != null){
			m_CheckBoxChartDataQueue.setEnabled(bDataQueue);
		}
		
	}
	
	protected boolean updateAnimationButton(int id, boolean isChecked)
	{
		if (id == R.id.switch_chart_animation) {
			
			boolean bLoop = false;
			
			if (m_SwitchChartAnimationLoop != null) {
				bLoop = m_SwitchChartAnimationLoop.isChecked();
				m_SwitchChartAnimationLoop.setEnabled(isChecked);
			}
			
			if (isChecked) {
				m_Chart.beginAnimation(bLoop);
			}
			else {
				m_Chart.endAnimation();
			}
		}
		else if (id == R.id.switch_chart_animation_loop) {
			
			if (m_SwitchChartAnimationLoop == null) {
				return false;
			}
			
			if (m_SwitchChartAnimation.isChecked()) {
				m_Chart.beginAnimation(isChecked);
			}
		}
		
		if (id == R.id.checkbox_chart_animation) {
			
			boolean bLoop = false;
			
			if (m_CheckBoxChartAnimationLoop != null) {
				bLoop = m_CheckBoxChartAnimationLoop.isChecked();
				m_CheckBoxChartAnimationLoop.setEnabled(isChecked);
			}
			
			if (isChecked) {
				m_Chart.beginAnimation(bLoop);
			}
			else {
				m_Chart.endAnimation();
			}
		}
		else if (id == R.id.checkbox_chart_animation_loop) {
			
			if (m_CheckBoxChartAnimationLoop == null) {
				return false;
			}
			
			if (m_CheckBoxChartAnimation.isChecked()) {
				m_Chart.beginAnimation(isChecked);
			}
		}
		
		if (id == R.id.switch_chart_data_queue) {
			if (m_SwitchChartDataQueue == null) {
				return false;
			}
			
			if (m_SwitchChartDataQueue.isChecked() == true) {
				m_Chart.beginDataQueue();
			} else {
				m_Chart.endDataQueue();
			}
		}
		
		if (id == R.id.checkbox_chart_data_queue) {
			if (m_CheckBoxChartDataQueue == null) {
				return false;
			}
			
			if (m_CheckBoxChartDataQueue.isChecked() == true) {
				m_Chart.beginDataQueue();
			} else {
				m_Chart.endDataQueue();
			}
		}
		
		return true;
	}
	
	protected void updateAnimationButton()
	{
		if (m_SwitchChartAnimation != null)
		{
			if (m_SwitchChartAnimation.isEnabled())
			{
				updateAnimationButton(R.id.switch_chart_animation, m_SwitchChartAnimation.isChecked());
			}
		}
		
		if (m_CheckBoxChartAnimation != null)
		{
			if (m_CheckBoxChartAnimation.isEnabled())
			{
				updateAnimationButton(R.id.checkbox_chart_animation, m_CheckBoxChartAnimation.isChecked());
			}
		}
	}
	
	protected String getChartNameByType(int type)
	{
		if (type == Chart.Chart_Type_Clustered_Column) {
			return getString(R.string.chart_type_class01_sub01);
		}
		else if (type == Chart.Chart_Type_Stacked_Column) {
			return getString(R.string.chart_type_class01_sub02);
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Column) {
			return getString(R.string.chart_type_class01_sub03);
		}
		else if (type == Chart.Chart_Type_3D_Column) {
			return getString(R.string.chart_type_class01_sub04);
		}
		else if (type == Chart.Chart_Type_Stacked_Column_in_3D) {
			return getString(R.string.chart_type_class01_sub05);
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Column_in_3D) {
			return getString(R.string.chart_type_class01_sub06);
		}
		else if (type == Chart.Chart_Type_Line) {
			return getString(R.string.chart_type_class02_sub01);
		}
		else if (type == Chart.Chart_Type_Stacked_Line) {
			return getString(R.string.chart_type_class02_sub02);
		}
		else if (type == Chart.Chart_Type_Line_with_Markets) {
			return getString(R.string.chart_type_class02_sub03);
		}
		else if (type == Chart.Chart_Type_3D_Line) {
			return getString(R.string.chart_type_class02_sub04);
		}
		else if (type == Chart.Chart_Type_Pie) {
			return getString(R.string.chart_type_class03_sub01);
		}
		else if (type == Chart.Chart_Type_Exploded_Pie) {
			return getString(R.string.chart_type_class03_sub02);
		}
		else if (type == Chart.Chart_Type_3D_Pie) {
			return getString(R.string.chart_type_class03_sub03);
		}
		else if (type == Chart.Chart_Type_Exploded_pie_in_3D) {
			return getString(R.string.chart_type_class03_sub04);
		}
		else if (type == Chart.Chart_Type_Clustered_Bar) {
			return getString(R.string.chart_type_class04_sub01);
		}
		else if (type == Chart.Chart_Type_Stacked_Bar) {
			return getString(R.string.chart_type_class04_sub02);
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Bar) {
			return getString(R.string.chart_type_class04_sub03);
		}
		else if (type == Chart.Chart_Type_Clustered_Bar_in_3D) {
			return getString(R.string.chart_type_class04_sub04);
		}
		else if (type == Chart.Chart_Type_Stacked_Bar_in_3D) {
			return getString(R.string.chart_type_class04_sub05);
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Bar_in_3D) {
			return getString(R.string.chart_type_class04_sub06);
		}
		else if (type == Chart.Chart_Type_Area) {
			return getString(R.string.chart_type_class05_sub01);
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Area) {
			return getString(R.string.chart_type_class05_sub02);
		}
		else if (type == Chart.Chart_Type_3D_Area) {
			return getString(R.string.chart_type_class05_sub03);
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Area_in_3D) {
			return getString(R.string.chart_type_class05_sub04);
		}
		else if (type == Chart.Chart_Type_Scatter_with_only_Markers) {
			return getString(R.string.chart_type_class06_sub01);
		}
		else if (type == Chart.Chart_Type_Scatter_with_Straight_Lines_and_Markers) {
			return getString(R.string.chart_type_class06_sub02);
		}
		else if (type == Chart.Chart_Type_Scatter_with_Straight_Lines) {
			return getString(R.string.chart_type_class06_sub03);
		}
		else if (type == Chart.Chart_Type_High_Low_Close) {
			return getString(R.string.chart_type_class07_sub01);
		}
		else if (type == Chart.Chart_Type_Open_High_Low_Close) {
			return getString(R.string.chart_type_class07_sub02);
		}
		else if (type == Chart.Chart_Type_Contour) {
			return getString(R.string.chart_type_class08_sub01);
		}
		else if (type == Chart.Chart_Type_3D_Surface) {
			return getString(R.string.chart_type_class08_sub02);
		}
		else if (type == Chart.Chart_Type_3D_Dougnut) {
			return getString(R.string.chart_type_class09_sub01);
		}
		else if (type == Chart.Chart_Type_Exploded_Dougnut_in_3D) {
			return getString(R.string.chart_type_class09_sub02);
		}
		else if (type == Chart.Chart_Type_Bubble) {
			return getString(R.string.chart_type_class10_sub01);
		}
		else if (type == Chart.Chart_Type_Radar) {
			return getString(R.string.chart_type_class11_sub01);
		}
		else if (type == Chart.Chart_Type_Radar_with_Markers) {
			return getString(R.string.chart_type_class11_sub02);
		}
		else if (type == Chart.Chart_Type_Combine) {
			return getString(R.string.chart_type_class12_sub01);
		}
		else if (type == Chart.Chart_Type_3D_Combine) {
			return getString(R.string.chart_type_class12_sub02);
		}
		
		return getString(R.string.chart_type_unknown);
	}
	
	protected int getChartIDByType(int type)
	{
		if (type == Chart.Chart_Type_Clustered_Column) {
			return R.id.chart_type_class01_sub01;
		}
		else if (type == Chart.Chart_Type_Stacked_Column) {
			return R.id.chart_type_class01_sub02;
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Column) {
			return R.id.chart_type_class01_sub03;
		}
		else if (type == Chart.Chart_Type_3D_Column) {
			return R.id.chart_type_class01_sub04;
		}
		else if (type == Chart.Chart_Type_Stacked_Column_in_3D) {
			return R.id.chart_type_class01_sub05;
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Column_in_3D) {
			return R.id.chart_type_class01_sub06;
		}
		else if (type == Chart.Chart_Type_Line) {
			return R.id.chart_type_class02_sub01;
		}
		else if (type == Chart.Chart_Type_Stacked_Line) {
			return R.id.chart_type_class02_sub02;
		}
		else if (type == Chart.Chart_Type_Line_with_Markets) {
			return R.id.chart_type_class02_sub03;
		}
		else if (type == Chart.Chart_Type_3D_Line) {
			return R.id.chart_type_class02_sub04;
		}
		else if (type == Chart.Chart_Type_Pie) {
			return R.id.chart_type_class03_sub01;
		}
		else if (type == Chart.Chart_Type_Exploded_Pie) {
			return R.id.chart_type_class03_sub02;
		}
		else if (type == Chart.Chart_Type_3D_Pie) {
			return R.id.chart_type_class03_sub03;
		}
		else if (type == Chart.Chart_Type_Exploded_pie_in_3D) {
			return R.id.chart_type_class03_sub04;
		}
		else if (type == Chart.Chart_Type_Clustered_Bar) {
			return R.id.chart_type_class04_sub01;
		}
		else if (type == Chart.Chart_Type_Stacked_Bar) {
			return R.id.chart_type_class04_sub02;
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Bar) {
			return R.id.chart_type_class04_sub03;
		}
		else if (type == Chart.Chart_Type_Clustered_Bar_in_3D) {
			return R.id.chart_type_class04_sub04;
		}
		else if (type == Chart.Chart_Type_Stacked_Bar_in_3D) {
			return R.id.chart_type_class04_sub05;
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Bar_in_3D) {
			return R.id.chart_type_class04_sub06;
		}
		else if (type == Chart.Chart_Type_Area) {
			return R.id.chart_type_class05_sub01;
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Area) {
			return R.id.chart_type_class05_sub02;
		}
		else if (type == Chart.Chart_Type_3D_Area) {
			return R.id.chart_type_class05_sub03;
		}
		else if (type == Chart.Chart_Type_100percent_Stacked_Area_in_3D) {
			return R.id.chart_type_class05_sub04;
		}
		else if (type == Chart.Chart_Type_Scatter_with_only_Markers) {
			return R.id.chart_type_class06_sub01;
		}
		else if (type == Chart.Chart_Type_Scatter_with_Straight_Lines_and_Markers) {
			return R.id.chart_type_class06_sub02;
		}
		else if (type == Chart.Chart_Type_Scatter_with_Straight_Lines) {
			return R.id.chart_type_class06_sub03;
		}
		else if (type == Chart.Chart_Type_High_Low_Close) {
			return R.id.chart_type_class07_sub01;
		}
		else if (type == Chart.Chart_Type_Open_High_Low_Close) {
			return R.id.chart_type_class07_sub02;
		}
		else if (type == Chart.Chart_Type_Contour) {
			return R.id.chart_type_class08_sub01;
		}
		else if (type == Chart.Chart_Type_3D_Surface) {
			return R.id.chart_type_class08_sub02;
		}
		else if (type == Chart.Chart_Type_3D_Dougnut) {
			return R.id.chart_type_class09_sub01;
		}
		else if (type == Chart.Chart_Type_Exploded_Dougnut_in_3D) {
			return R.id.chart_type_class09_sub02;
		}
		else if (type == Chart.Chart_Type_Bubble) {
			return R.id.chart_type_class10_sub01;
		}
		else if (type == Chart.Chart_Type_Radar) {
			return R.id.chart_type_class11_sub01;
		}
		else if (type == Chart.Chart_Type_Radar_with_Markers) {
			return R.id.chart_type_class11_sub02;
		}
		else if (type == Chart.Chart_Type_Combine) {
			return R.id.chart_type_class12_sub01;
		}
		else if (type == Chart.Chart_Type_3D_Combine) {
			return R.id.chart_type_class12_sub02;
		}
		
		return -1;
	}
	
	protected int getChartTypeByID(int id)
	{
		if (id == R.id.chart_type_class01_sub01) {
			return Chart.Chart_Type_Clustered_Column;
		}
		else if (id == R.id.chart_type_class01_sub02) {
			return Chart.Chart_Type_Stacked_Column;
		}
		else if (id == R.id.chart_type_class01_sub03) {
			return Chart.Chart_Type_100percent_Stacked_Column;
		}
		else if (id == R.id.chart_type_class01_sub04) {
			return Chart.Chart_Type_3D_Column;
		}
		else if (id == R.id.chart_type_class01_sub05) {
			return Chart.Chart_Type_Stacked_Column_in_3D;
		}
		else if (id == R.id.chart_type_class01_sub06) {
			return Chart.Chart_Type_100percent_Stacked_Column_in_3D;
		}
		else if (id == R.id.chart_type_class02_sub01) {
			return Chart.Chart_Type_Line;
		}
		else if (id == R.id.chart_type_class02_sub02) {
			return Chart.Chart_Type_Stacked_Line;
		}
		else if (id == R.id.chart_type_class02_sub03) {
			return Chart.Chart_Type_Line_with_Markets;
		}
		else if (id == R.id.chart_type_class02_sub04) {
			return Chart.Chart_Type_3D_Line;
		}
		else if (id == R.id.chart_type_class03_sub01) {
			return Chart.Chart_Type_Pie;
		}
		else if (id == R.id.chart_type_class03_sub02) {
			return Chart.Chart_Type_Exploded_Pie;
		}
		else if (id == R.id.chart_type_class03_sub03) {
			return Chart.Chart_Type_3D_Pie;
		}
		else if (id == R.id.chart_type_class03_sub04) {
			return Chart.Chart_Type_Exploded_pie_in_3D;
		}
		else if (id == R.id.chart_type_class04_sub01) {
			return Chart.Chart_Type_Clustered_Bar;
		}
		else if (id == R.id.chart_type_class04_sub02) {
			return Chart.Chart_Type_Stacked_Bar;
		}
		else if (id == R.id.chart_type_class04_sub03) {
			return Chart.Chart_Type_100percent_Stacked_Bar;
		}
		else if (id == R.id.chart_type_class04_sub04) {
			return Chart.Chart_Type_Clustered_Bar_in_3D;
		}
		else if (id == R.id.chart_type_class04_sub05) {
			return Chart.Chart_Type_Stacked_Bar_in_3D;
		}
		else if (id == R.id.chart_type_class04_sub06) {
			return Chart.Chart_Type_100percent_Stacked_Bar_in_3D;
		}
		else if (id == R.id.chart_type_class05_sub01) {
			return Chart.Chart_Type_Area;
		}
		else if (id == R.id.chart_type_class05_sub02) {
			return Chart.Chart_Type_100percent_Stacked_Area;
		}
		else if (id == R.id.chart_type_class05_sub03) {
			return Chart.Chart_Type_3D_Area;
		}
		else if (id == R.id.chart_type_class05_sub04) {
			return Chart.Chart_Type_100percent_Stacked_Area_in_3D;
		}
		else if (id == R.id.chart_type_class06_sub01) {
			return Chart.Chart_Type_Scatter_with_only_Markers;
		}
		else if (id == R.id.chart_type_class06_sub02) {
			return Chart.Chart_Type_Scatter_with_Straight_Lines_and_Markers;
		}
		else if (id == R.id.chart_type_class06_sub03) {
			return Chart.Chart_Type_Scatter_with_Straight_Lines;
		}
		else if (id == R.id.chart_type_class07_sub01) {
			return Chart.Chart_Type_High_Low_Close;
		}
		else if (id == R.id.chart_type_class07_sub02) {
			return Chart.Chart_Type_Open_High_Low_Close;
		}
		else if (id == R.id.chart_type_class08_sub01) {
			return Chart.Chart_Type_Contour;
		}
		else if (id == R.id.chart_type_class08_sub02) {
			return Chart.Chart_Type_3D_Surface;
		}
		else if (id == R.id.chart_type_class09_sub01) {
			return Chart.Chart_Type_3D_Dougnut;
		}
		else if (id == R.id.chart_type_class09_sub02) {
			return Chart.Chart_Type_Exploded_Dougnut_in_3D;
		}
		else if (id == R.id.chart_type_class10_sub01) {
			return Chart.Chart_Type_Bubble;
		}
		else if (id == R.id.chart_type_class11_sub01) {
			return Chart.Chart_Type_Radar;
		}
		else if (id == R.id.chart_type_class11_sub02) {
			return Chart.Chart_Type_Radar_with_Markers;
		}
		else if (id == R.id.chart_type_class12_sub01) {
			return Chart.Chart_Type_Combine;
		}
		else if (id == R.id.chart_type_class12_sub02) {
			return Chart.Chart_Type_3D_Combine;
		}
		
		return Chart.Chart_Type_Unknown;
	}
	
	public void InitApplyAxisPerChartStyle()
	{
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Clustered_Column),							new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Stacked_Column),							new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_100percent_Stacked_Column),					new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_3D_Column),									new ApplyAxisPerChartStyle(true, true, true, true));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Stacked_Column_in_3D),						new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_100percent_Stacked_Column_in_3D),			new ApplyAxisPerChartStyle(true, true, true, false));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Line),										new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Stacked_Line),								new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Line_with_Markets),							new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_3D_Line),									new ApplyAxisPerChartStyle(true, true, true, true));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Pie),										new ApplyAxisPerChartStyle(false, false, false, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Exploded_Pie),								new ApplyAxisPerChartStyle(false, false, false, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_3D_Pie),									new ApplyAxisPerChartStyle(false, false, false, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Exploded_pie_in_3D),						new ApplyAxisPerChartStyle(false, false, false, false));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Clustered_Bar),								new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Stacked_Bar),								new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_100percent_Stacked_Bar),					new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Clustered_Bar_in_3D),						new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Stacked_Bar_in_3D),							new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_100percent_Stacked_Bar_in_3D),				new ApplyAxisPerChartStyle(true, true, true, false));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Area),										new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_100percent_Stacked_Area),					new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_3D_Area),									new ApplyAxisPerChartStyle(true, true, true, true));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_100percent_Stacked_Area_in_3D),				new ApplyAxisPerChartStyle(true, true, true, false));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Scatter_with_only_Markers),					new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Scatter_with_Straight_Lines_and_Markers),	new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Scatter_with_Straight_Lines),				new ApplyAxisPerChartStyle(true, true, true, false));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_High_Low_Close),							new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Open_High_Low_Close),						new ApplyAxisPerChartStyle(true, true, true, false));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Contour),									new ApplyAxisPerChartStyle(true, false, false, true));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_3D_Surface),								new ApplyAxisPerChartStyle(true, true, true, true));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_3D_Dougnut),								new ApplyAxisPerChartStyle(false, false, false, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Exploded_Dougnut_in_3D),					new ApplyAxisPerChartStyle(false, false, false, false));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Bubble),									new ApplyAxisPerChartStyle(true, true, true, false));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Radar),										new ApplyAxisPerChartStyle(false, false, false, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Radar_with_Markers),						new ApplyAxisPerChartStyle(false, false, false, false));
		
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_Combine),									new ApplyAxisPerChartStyle(true, true, true, false));
		m_mapApplyAxisPerChartStyle.put(getChartNameByType(Chart.Chart_Type_3D_Combine),								new ApplyAxisPerChartStyle(true, true, true, true));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		try {
			PackageInfo i = getPackageManager().getPackageInfo(getPackageName(), 0);
			m_Version = i.versionName;
			setTitle(getString(R.string.app_name) + " - " + m_Version);
		} catch(NameNotFoundException e) {

		}
		
		m_Chart = (Chart)findViewById(R.id.chart01);
		m_ButtonChartType = (Button)findViewById(R.id.button_chart_type);
		m_ButtonChartStyle = (Button)findViewById(R.id.button_chart_style);
		m_SeekBarChartSize = (SeekBar)findViewById(R.id.seekbar_chart_size);
		m_SeekBarChartRotateX = (SeekBar)findViewById(R.id.seekbar_chart_rotate_x);
		m_SeekBarChartRotateY = (SeekBar)findViewById(R.id.seekbar_chart_rotate_y);
		m_SeekBarChartRotateZ = (SeekBar)findViewById(R.id.seekbar_chart_rotate_z);
		m_SeekBarChartAlpha = (SeekBar)findViewById(R.id.seekbar_chart_alpha);
		m_CheckBoxChartXAxis = (CheckBox)findViewById(R.id.checkbox_chart_xaxis);
		m_CheckBoxChartYAxis = (CheckBox)findViewById(R.id.checkbox_chart_yaxis);
		m_CheckBoxChartY2Axis = (CheckBox)findViewById(R.id.checkbox_chart_y2axis);
		m_CheckBoxChartZAxis = (CheckBox)findViewById(R.id.checkbox_chart_zaxis);
		m_CheckBoxChartTitle = (CheckBox)findViewById(R.id.checkbox_chart_title);
		m_CheckBoxChartLegend = (CheckBox)findViewById(R.id.checkbox_chart_legend);
		m_CheckBoxChartAnimation = (CheckBox)findViewById(R.id.checkbox_chart_animation);
		m_CheckBoxChartAnimationLoop = (CheckBox)findViewById(R.id.checkbox_chart_animation_loop);
		m_CheckBoxChartDataQueue = (CheckBox)findViewById(R.id.checkbox_chart_data_queue);
		m_SwitchChartXAxis = (Switch)findViewById(R.id.switch_chart_xaxis);
		m_SwitchChartYAxis = (Switch)findViewById(R.id.switch_chart_yaxis);
		m_SwitchChartY2Axis = (Switch)findViewById(R.id.switch_chart_y2axis);
		m_SwitchChartZAxis = (Switch)findViewById(R.id.switch_chart_zaxis);
		m_SwitchChartTitle = (Switch)findViewById(R.id.switch_chart_title);
		m_SwitchChartLegend = (Switch)findViewById(R.id.switch_chart_legend);
		m_SwitchChartAnimation = (Switch)findViewById(R.id.switch_chart_animation);
		m_SwitchChartAnimationLoop = (Switch)findViewById(R.id.switch_chart_animation_loop);
		m_SwitchChartDataQueue = (Switch)findViewById(R.id.switch_chart_data_queue);
		m_ChartSeekBarListener = new ChartSeekBarListener(m_ChartSeekBarListenerCallback);
		m_ChartCheckBoxListener = new ChartCheckBoxListener(m_ChartCheckBoxListenerCallback);
		
		m_ButtonChartPropertyTitle = (Button)findViewById(R.id.button_chart_property_title);
		m_ButtonChartPropertyTitle.setOnClickListener(this);

		m_ButtonChartPropertyLegend = (Button)findViewById(R.id.button_chart_property_legend);
		m_ButtonChartPropertyLegend.setOnClickListener(this);
		
		m_ButtonChartPropertyBackdrop = (Button)findViewById(R.id.button_chart_property_backdrop);
		m_ButtonChartPropertyBackdrop.setOnClickListener(this);
		
		m_ButtonChartPropertyPlotBackdrop = (Button)findViewById(R.id.button_chart_property_plotbackdrop);
		m_ButtonChartPropertyPlotBackdrop.setOnClickListener(this);
		
		m_ButtonChartPropertyXAxis = (Button)findViewById(R.id.button_chart_property_xaxis);
		m_ButtonChartPropertyXAxis.setOnClickListener(this);
		
		m_ButtonChartPropertyYAxis = (Button)findViewById(R.id.button_chart_property_yaxis);
		m_ButtonChartPropertyYAxis .setOnClickListener(this);
		
		m_ButtonChartPropertyY2Axis = (Button)findViewById(R.id.button_chart_property_y2axis);
		m_ButtonChartPropertyY2Axis .setOnClickListener(this);
		
		m_ButtonChartPropertyZAxis = (Button)findViewById(R.id.button_chart_property_zaxis);
		m_ButtonChartPropertyZAxis .setOnClickListener(this);

		m_ButtonSeriesProperty = (Button)findViewById(R.id.button_chart_property_series);
		m_ButtonSeriesProperty.setOnClickListener(this);

		
		m_DlgChartProperty = new ChartProperty(this, m_Chart);
		m_DlgChartProperty.requestWindowFeature(Window.FEATURE_NO_TITLE);
		m_DlgChartProperty.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		m_DlgSeriesProperty = new SeriesProperty(this, m_Chart);
		m_DlgSeriesProperty.requestWindowFeature(Window.FEATURE_NO_TITLE);
		m_DlgSeriesProperty.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		
		if (m_Chart == null 
			|| m_ButtonChartType == null
			|| m_ButtonChartStyle == null
			|| m_SeekBarChartSize == null
			|| m_SeekBarChartRotateX == null
			|| m_SeekBarChartRotateY == null
			|| m_SeekBarChartRotateZ == null
			|| m_SeekBarChartAlpha == null
			|| m_ChartSeekBarListener == null
			|| (m_CheckBoxChartXAxis == null && m_SwitchChartXAxis == null)
			|| (m_CheckBoxChartYAxis == null && m_SwitchChartYAxis == null)
			|| (m_CheckBoxChartY2Axis == null && m_SwitchChartY2Axis == null)
			|| (m_CheckBoxChartZAxis == null && m_SwitchChartZAxis == null)
			|| (m_CheckBoxChartTitle == null && m_SwitchChartTitle == null)
			|| (m_CheckBoxChartLegend == null && m_SwitchChartLegend == null)
			|| (m_SwitchChartAnimation == null && m_SwitchChartAnimationLoop == null && m_CheckBoxChartAnimation == null && m_CheckBoxChartAnimationLoop == null)
			|| (m_SwitchChartDataQueue == null && m_CheckBoxChartDataQueue == null)
			|| m_ButtonChartPropertyTitle == null
			|| m_ButtonChartPropertyLegend == null
			|| m_ButtonChartPropertyBackdrop == null
			|| m_ButtonChartPropertyPlotBackdrop == null
			|| m_DlgChartProperty == null
			|| m_DlgSeriesProperty == null)
		{
			return;
		}
		
		m_Chart.setCallback(m_ChartCallback);
		
		int nChartType = m_Chart.getChartType();
		m_ButtonChartType.setOnClickListener(this);
		m_ButtonChartStyle.setOnClickListener(this);
		m_ButtonChartType.setText(getChartNameByType(nChartType));
		m_ButtonChartStyle.setText(R.string.chart_color_template_00 + m_Chart.getColorTemplate());
		
		RadioGroup radioGroupChartType =(RadioGroup)findViewById(R.id.radio_chart_type);
		if (radioGroupChartType != null)
		{
			radioGroupChartType.check(getChartIDByType(nChartType));
			radioGroupChartType.setOnCheckedChangeListener(this);
		}
		
		RadioGroup radioGroupChartStyle =(RadioGroup)findViewById(R.id.radio_chart_color_template);
		if (radioGroupChartStyle != null)
		{
			radioGroupChartStyle.setOnCheckedChangeListener(this);
		}
		
		m_SeekBarChartSize.setMax(100);
		m_SeekBarChartRotateX.setMax(360);
		m_SeekBarChartRotateY.setMax(180);
		m_SeekBarChartRotateZ.setMax(950);
		m_SeekBarChartAlpha.setMax(255);
		
		m_SeekBarChartSize.setProgress((int)(m_fChartZoom * 100.0f));
		m_SeekBarChartRotateX.setProgress((int)m_Chart.getRotation());
		m_SeekBarChartRotateY.setProgress((int)(m_Chart.getElevation() + 90.0f));
		m_SeekBarChartRotateZ.setProgress((int)((m_Chart.getPerspective() * 100.0f) - 50.0f));
		m_SeekBarChartAlpha.setProgress(m_Chart.getChartAlpha());
		
		if (m_CheckBoxChartXAxis != null)
		{
			m_CheckBoxChartXAxis.setChecked(m_Chart.getXAxisVisible());
			m_CheckBoxChartXAxis.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyXAxis.setEnabled(m_CheckBoxChartXAxis.isChecked());
		}
		if (m_CheckBoxChartYAxis != null)
		{
			m_CheckBoxChartYAxis.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyYAxis.setEnabled(m_CheckBoxChartYAxis.isChecked());
		}
		if (m_CheckBoxChartY2Axis != null)
		{
			m_CheckBoxChartY2Axis.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyY2Axis.setEnabled(m_CheckBoxChartY2Axis.isChecked());
		}
		if (m_CheckBoxChartZAxis != null)
		{
			m_CheckBoxChartZAxis.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyZAxis.setEnabled(m_CheckBoxChartZAxis.isChecked());
		}
		if (m_CheckBoxChartTitle != null)
		{
			m_CheckBoxChartTitle.setChecked(m_Chart.getTitleVisible());
			m_CheckBoxChartTitle.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyTitle.setEnabled(m_CheckBoxChartTitle.isChecked());
		}
		if (m_CheckBoxChartLegend != null)
		{
			m_CheckBoxChartLegend.setChecked(m_Chart.isLegendVisible());
			m_CheckBoxChartLegend.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyLegend.setEnabled(m_CheckBoxChartLegend.isChecked());
		}
		if (m_CheckBoxChartAnimation != null)
		{
			m_CheckBoxChartAnimation.setOnCheckedChangeListener(m_ChartCheckBoxListener);
		}
		if (m_CheckBoxChartAnimationLoop != null)
		{
			m_CheckBoxChartAnimationLoop.setOnCheckedChangeListener(m_ChartCheckBoxListener);
		}
		if (m_CheckBoxChartDataQueue != null)
		{
			m_CheckBoxChartDataQueue.setOnCheckedChangeListener(m_ChartCheckBoxListener);
		}
		if (m_SwitchChartXAxis != null)
		{
			m_SwitchChartXAxis.setChecked(m_Chart.getXAxisVisible());
			m_SwitchChartXAxis.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyXAxis.setEnabled(m_SwitchChartXAxis.isChecked());
		}
		if (m_SwitchChartYAxis != null)
		{
			m_SwitchChartYAxis.setChecked(m_Chart.getYAxisVisible());
			m_SwitchChartYAxis.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyYAxis.setEnabled(m_SwitchChartYAxis.isChecked());
		}
		if (m_SwitchChartY2Axis != null)
		{
			m_SwitchChartY2Axis.setChecked(m_Chart.getY2AxisVisible());
			m_SwitchChartY2Axis.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyY2Axis.setEnabled(m_SwitchChartY2Axis.isChecked());
		}
		if (m_SwitchChartZAxis != null)
		{
			m_SwitchChartZAxis.setChecked(m_Chart.getZAxisVisible());
			m_SwitchChartZAxis.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyZAxis.setEnabled(m_SwitchChartZAxis.isChecked());
		}
		if (m_SwitchChartTitle != null)
		{
			m_SwitchChartTitle.setChecked(m_Chart.getTitleVisible());
			m_SwitchChartTitle.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyTitle.setEnabled(m_SwitchChartTitle.isChecked());
		}
		if (m_SwitchChartLegend != null)
		{
			m_SwitchChartLegend.setChecked(m_Chart.isLegendVisible());
			m_SwitchChartLegend.setOnCheckedChangeListener(m_ChartCheckBoxListener);
			
			m_ButtonChartPropertyLegend.setEnabled(m_SwitchChartLegend.isChecked());
		}
		if (m_SwitchChartAnimation != null)
		{
			m_SwitchChartAnimation.setOnCheckedChangeListener(m_ChartCheckBoxListener);
		}
		if (m_SwitchChartAnimationLoop != null)
		{
			m_SwitchChartAnimationLoop.setOnCheckedChangeListener(m_ChartCheckBoxListener);
		}
		if (m_SwitchChartDataQueue != null)
		{
			m_SwitchChartDataQueue.setOnCheckedChangeListener(m_ChartCheckBoxListener);
		}
		
		m_SeekBarChartSize.setOnSeekBarChangeListener(m_ChartSeekBarListener);
		m_SeekBarChartRotateX.setOnSeekBarChangeListener(m_ChartSeekBarListener);
		m_SeekBarChartRotateY.setOnSeekBarChangeListener(m_ChartSeekBarListener);
		m_SeekBarChartRotateZ.setOnSeekBarChangeListener(m_ChartSeekBarListener);
		m_SeekBarChartAlpha.setOnSeekBarChangeListener(m_ChartSeekBarListener);
		
		toogleEnableAnimationButton();
		
		m_Random = new Random();
		mHandler.sendEmptyMessage(1);
		
		InitApplyAxisPerChartStyle();

		ApplyAxisPerChartStyle apply = (ApplyAxisPerChartStyle)m_mapApplyAxisPerChartStyle.get(getChartNameByType(m_Chart.getChartType()));
		
		if (null != m_CheckBoxChartXAxis) {
			m_CheckBoxChartXAxis.setChecked(apply.X);
			m_CheckBoxChartXAxis.setEnabled(apply.X);
		}
		if (null != m_SwitchChartXAxis) {
			m_SwitchChartXAxis.setChecked(apply.X);
			m_SwitchChartXAxis.setEnabled(apply.X);
		}

		if (null != m_CheckBoxChartYAxis) {
			m_CheckBoxChartYAxis.setChecked(apply.Y);
			m_CheckBoxChartYAxis.setEnabled(apply.Y);
		}
		if (null != m_SwitchChartYAxis) {
			m_SwitchChartYAxis.setChecked(apply.Y);
			m_SwitchChartYAxis.setEnabled(apply.Y);
		}
		
		if (null != m_CheckBoxChartY2Axis) {
			m_CheckBoxChartY2Axis.setChecked(apply.Y2);
			m_CheckBoxChartY2Axis.setEnabled(apply.Y2);
		}
		if (null != m_SwitchChartY2Axis) {
			m_SwitchChartY2Axis.setChecked(apply.Y2);
			m_SwitchChartY2Axis.setEnabled(apply.Y2);
		}
		
		if (null != m_CheckBoxChartZAxis) {
			m_CheckBoxChartZAxis.setChecked(apply.Z);
			m_CheckBoxChartZAxis.setEnabled(apply.Z);
		}
		if (null != m_SwitchChartZAxis) {
			m_SwitchChartZAxis.setChecked(apply.Z);
			m_SwitchChartZAxis.setEnabled(apply.Z);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int	nItemID = item.getItemId();
		if (nItemID == R.id.menu_about)	{
			AlertDialog.Builder aboutDialogBuilder = new AlertDialog.Builder(this);
			aboutDialogBuilder.setMessage(getString(R.string.blogger_url)).setCancelable(
					false).setPositiveButton(getString(R.string.button_visit_blogger),
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Uri uri = Uri.parse("http://essenceware.blogspot.com");
							 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							 startActivity(intent);
						}
					}).setNegativeButton(getString(R.string.button_close),
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			
			AlertDialog aboutDialog = aboutDialogBuilder.create();
			aboutDialog.setTitle(getString(R.string.app_name) + " - " + m_Version);
			aboutDialog.setIcon(R.drawable.ic_launcher);
			aboutDialog.show();
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		updateChild();
		
		repositionPropertyDialog();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		
		updateChild();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			hideShideMenuView();
		}
		
		return super.onTouchEvent(event);
	}
	
	@Override
	public void onBackPressed() {
		
		if (hideShideMenuView())
		{
			return;
		}
		
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		if (v == null)
		{
			return;
		}
		
		int	nID = v.getId();
		
		if (nID == R.id.button_chart_type)
		{
			toggleSlideMenuView(R.id.relative_layout_chart_type);
		}
		else if (nID == R.id.button_chart_style)
		{
			toggleSlideMenuView(R.id.relative_layout_chart_color_template);
		}
		else if (nID == R.id.button_chart_property_xaxis ||
				nID == R.id.button_chart_property_yaxis ||
				nID == R.id.button_chart_property_y2axis ||
				nID == R.id.button_chart_property_zaxis ||
				nID == R.id.button_chart_property_title ||
				nID == R.id.button_chart_property_legend ||
				nID == R.id.button_chart_property_backdrop ||
				nID == R.id.button_chart_property_plotbackdrop)
		{
			m_DlgChartProperty.show();
			repositionPropertyDialog();
			m_DlgChartProperty.SetPropertyType(nID);
		}
		else if (nID == R.id.button_chart_property_series)
		{
			m_DlgSeriesProperty.show();
			repositionPropertyDialog();
			m_DlgSeriesProperty.SetPropertyType(nID);
		}
		else
		{
			hideShideMenuView();
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		if (m_Chart == null || group == null)
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
				m_ButtonChartStyle.setText(R.string.chart_color_template_00 + nIndex);
			}
		}
		else if (checkedId >= R.id.chart_type_class01_sub01 && checkedId <= R.id.chart_type_class12_sub02)
		{
			int	nChartType = getChartTypeByID(checkedId);
			m_Chart.setChartType(nChartType);
			m_ButtonChartType.setText(getChartNameByType(nChartType));
			toogleEnableAnimationButton();
			updateAnimationButton();
			
			ApplyAxisPerChartStyle apply = (ApplyAxisPerChartStyle)m_mapApplyAxisPerChartStyle.get(getChartNameByType(nChartType));
			
			if (null != m_CheckBoxChartXAxis) {
				m_CheckBoxChartXAxis.setChecked(apply.X);
				m_CheckBoxChartXAxis.setEnabled(apply.X);
			}
			if (null != m_SwitchChartXAxis) {
				m_SwitchChartXAxis.setChecked(apply.X);
				m_SwitchChartXAxis.setEnabled(apply.X);
			}

			if (null != m_CheckBoxChartYAxis) {
				m_CheckBoxChartYAxis.setChecked(apply.Y);
				m_CheckBoxChartYAxis.setEnabled(apply.Y);
			}
			if (null != m_SwitchChartYAxis) {
				m_SwitchChartYAxis.setChecked(apply.Y);
				m_SwitchChartYAxis.setEnabled(apply.Y);
			}
			
			if (null != m_CheckBoxChartY2Axis) {
				m_CheckBoxChartY2Axis.setChecked(apply.Y2);
				m_CheckBoxChartY2Axis.setEnabled(apply.Y2);
			}
			if (null != m_SwitchChartY2Axis) {
				m_SwitchChartY2Axis.setChecked(apply.Y2);
				m_SwitchChartY2Axis.setEnabled(apply.Y2);
			}
			
			if (null != m_CheckBoxChartZAxis) {
				m_CheckBoxChartZAxis.setChecked(apply.Z);
				m_CheckBoxChartZAxis.setEnabled(apply.Z);
			}
			if (null != m_SwitchChartZAxis) {
				m_SwitchChartZAxis.setChecked(apply.Z);
				m_SwitchChartZAxis.setEnabled(apply.Z);
			}
			
			if (nChartType == Chart.Chart_Type_Combine)
			 {
				m_Chart.setSeriesType(0, Chart.Chart_Type_Line_with_Markets);
				 m_Chart.setSeriesType(1, Chart.Chart_Type_Line);
				 m_Chart.setSeriesType(2, Chart.Chart_Type_Clustered_Column);
				 m_Chart.setSeriesType(3, Chart.Chart_Type_Area);
			 }
			 else if (nChartType == Chart.Chart_Type_3D_Combine)
			 {
				 m_Chart.setSeriesType(0, Chart.Chart_Type_3D_Line);
				 m_Chart.setSeriesType(1, Chart.Chart_Type_3D_Column);
				 m_Chart.setSeriesType(2, Chart.Chart_Type_3D_Area);
			 }
			
			if (nChartType == Chart.Chart_Type_Line || nChartType == Chart.Chart_Type_Line_with_Markets)
			 {
				 m_Chart.setYAxisMaximum(true, 2000);
			 }
			 else if (nChartType == Chart.Chart_Type_Stacked_Line)
			 {
				 m_Chart.setYAxisMaximum(true, 6000);
			 }
		}
		else
		{
			return;
		}
			
		m_Chart.invalidate();
	}
	
	protected void repositionPropertyDialog()
	{
		Window	window = getWindow();
		if (window == null)
		{
			return;
		}
		
		int nOrientation = getOrientation();
		int nTopBarHeight = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		DisplayMetrics displayMetrics = getDisplayMetrics();
		
		int nViewWidth = 0;
		int nViewHeight = 0;
		nViewWidth = displayMetrics.widthPixels;
		nViewHeight = displayMetrics.heightPixels - nTopBarHeight;
		
		ViewGroup.LayoutParams paramChart = m_Chart.getLayoutParams();
		WindowManager.LayoutParams paramChartProperty = m_DlgChartProperty.getWindow().getAttributes();
		WindowManager.LayoutParams paramSeriesProperty = m_DlgSeriesProperty.getWindow().getAttributes();
		
		if (nOrientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			paramChartProperty.width = nViewWidth - paramChart.width + 100;
			paramChartProperty.height = paramChart.height + 100;
			m_DlgChartProperty.getWindow().setAttributes(paramChartProperty);
			m_DlgChartProperty.getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
			
			paramSeriesProperty.width = nViewWidth - paramChart.width + 100;
			paramSeriesProperty.height = paramChart.height + 100;
			m_DlgSeriesProperty.getWindow().setAttributes(paramSeriesProperty);
			m_DlgSeriesProperty.getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
			
			if (null != m_DlgSeriesProperty.m_DlgSeriesColumnsProperty)
			{
				WindowManager.LayoutParams paramSeriesColumnsProperty = m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.getWindow().getAttributes();
				
				paramSeriesColumnsProperty.width = nViewWidth - paramChart.width + 100;
				paramSeriesColumnsProperty.height = paramChart.height + 100;
				m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.getWindow().setAttributes(paramSeriesColumnsProperty);
				m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
			}

			if (null != m_DlgSeriesProperty.m_DlgSeriesColumnsProperty &&
					null != m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.m_DlgSeriesDatapointLabelsProperty)
			{
				WindowManager.LayoutParams paramSeriesColumnsDatapointLableProperty = m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.m_DlgSeriesDatapointLabelsProperty.getWindow().getAttributes();
				
				paramSeriesColumnsDatapointLableProperty.width = nViewWidth - paramChart.width + 100;
				paramSeriesColumnsDatapointLableProperty.height = paramChart.height + 100;
				m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.m_DlgSeriesDatapointLabelsProperty.getWindow().setAttributes(paramSeriesColumnsDatapointLableProperty);
				m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.m_DlgSeriesDatapointLabelsProperty.getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
			}
		}
		else
		{
			paramChartProperty.width = paramChart.width + 100;
			paramChartProperty.height = nViewHeight - paramChart.height + 100;
			m_DlgChartProperty.getWindow().setAttributes(paramChartProperty);
			m_DlgChartProperty.getWindow().setGravity(Gravity.LEFT|Gravity.BOTTOM);
			
			paramSeriesProperty.width = paramChart.width + 100;
			paramSeriesProperty.height = nViewHeight - paramChart.height + 100;
			m_DlgSeriesProperty.getWindow().setAttributes(paramSeriesProperty);
			m_DlgSeriesProperty.getWindow().setGravity(Gravity.LEFT|Gravity.BOTTOM);
			
			if (null != m_DlgSeriesProperty.m_DlgSeriesColumnsProperty)
			{
				WindowManager.LayoutParams paramSeriesColumnsProperty = m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.getWindow().getAttributes();
				paramSeriesColumnsProperty.width = paramChart.width + 100;
				paramSeriesColumnsProperty.height = nViewHeight - paramChart.height + 100;
				m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.getWindow().setAttributes(paramSeriesColumnsProperty);
				m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.getWindow().setGravity(Gravity.LEFT|Gravity.BOTTOM);
			}
			
			if (null != m_DlgSeriesProperty.m_DlgSeriesColumnsProperty &&
					null != m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.m_DlgSeriesDatapointLabelsProperty)
			{
				WindowManager.LayoutParams paramSeriesColumnsDatapointLableProperty = m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.m_DlgSeriesDatapointLabelsProperty.getWindow().getAttributes();
				
				paramSeriesColumnsDatapointLableProperty.width = paramChart.width + 100;
				paramSeriesColumnsDatapointLableProperty.height = nViewHeight - paramChart.height + 100;
				m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.m_DlgSeriesDatapointLabelsProperty.getWindow().setAttributes(paramSeriesColumnsDatapointLableProperty);
				m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.m_DlgSeriesDatapointLabelsProperty.getWindow().setGravity(Gravity.LEFT|Gravity.BOTTOM);
			}
		}
	}

}
