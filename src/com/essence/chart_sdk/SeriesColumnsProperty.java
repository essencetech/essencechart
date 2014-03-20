package com.essence.chart_sdk;

import java.util.ArrayList;
import java.util.List;

import com.essence.chart.Chart;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;

public class SeriesColumnsProperty extends Dialog implements android.view.View.OnClickListener, OnCheckedChangeListener {

	private final int HEIGHT_LISTVIEW_ITEM = 65;
	
	public class ColumnProperty
	{
		public ColumnProperty()
		{
			Init();
		}
		
		public void Init()
		{
			bSeriesHide = false;
			bSeriesExclude = false;
			bSeriesPlotOn = false;
			bSeriesShowMarker = false;
			bSeriesAutoMarker = true;
			
			nColorHiLoGain = -1;
			nColorHiLoLoss = -1;
		}
		
		public int getColorHiLoGain()
		{
			int nColor = 0xFF000000;
			int rgbRed = nColorHiLoGain & 0x0000ff;
			int rgbGreen = nColorHiLoGain & 0x00ff00;
			int rgbBlue = nColorHiLoGain & 0xff0000;
			
			nColor = nColor + rgbRed + rgbGreen + rgbBlue; 
			
			return nColor;
		}
		
		public int getColorHiLoLoss()
		{
			int nColor = 0xFF000000;
			int rgbRed = nColorHiLoLoss & 0x0000ff;
			int rgbGreen = nColorHiLoLoss & 0x00ff00;
			int rgbBlue = nColorHiLoLoss & 0xff0000;
			
			nColor = nColor + rgbRed + rgbGreen + rgbBlue; 
			
			return nColor;
		}
		
		public boolean bSeriesHide;
		public boolean bSeriesExclude;
		public boolean bSeriesPlotOn;
		public boolean bSeriesShowMarker;
		public boolean bSeriesAutoMarker;
		
		public int nColorHiLoGain;
		public int nColorHiLoLoss;
	}
	private ColumnProperty m_ColumnProperty = null;
	
 
	public class SeriesRowLabelList
	{
		public SeriesRowLabelList()
		{
			Init();
		}
		
		public void Init()
		{
			if (null != labelList)
			{
				labelList.clear();
			}
			else
			{
				labelList = new ArrayList<String>();
			}
		}
		
		public void AddLabel(String str)
		{
			int nSize = labelList.size();
			labelList.add(nSize, str);
		}
		
		public String GetLabel(int index)
		{
			return labelList.get(index);
		}

		public List<String> labelList = null;
	}
	private SeriesRowLabelList m_RowLabelList = null;
	
	private Chart m_Chart = null;	
	
	private ColorPalette m_ColorPalette = null;	
	
	private ChartCheckBoxListener m_ChartCheckBoxListener = null;
	
	private int m_nActiveTabID;
	private int m_nCurrentColumn = -1;
	private int m_nSelectedRow = -1;
	
	Context m_pParent = null;
	
	
	private TextView	m_TextViewTitle = null;
	
	private RadioGroup radioGroupColumnsProperty = null;
	
	private	CheckBox	m_CheckBoxOptionsHideSeries = null;
	private	CheckBox	m_CheckBoxOptionsExcludeSeries = null;
	private	CheckBox	m_CheckBoxOptionsPlotOn = null;
	private	CheckBox	m_CheckBoxOptionsShowMarker = null;
	private	CheckBox	m_CheckBoxOptionsAutomaticMarker = null;
	
	private	Switch		m_SwitchOptionsHideSeries = null;
	private	Switch		m_SwitchOptionsExcludeSeries = null;
	private	Switch		m_SwitchOptionsPlotOn = null;
	private	Switch		m_SwitchOptionsShowMarker = null;
	private	Switch		m_SwitchOptionsAutomaticMarker = null;
	
	private Button		m_ButtonOptionsGainColorPreview = null;
	private Button		m_ButtonOptionsLossColorPreview = null;
	
	private ListView listViewSeriesRowsList = null;
	private ArrayAdapter<String> adapterlistViewSeriesRowsList = null;
	private Button m_ButtonSeriesRowsProperty = null;
	
	public SeriesDatapointLabelsProperty m_DlgSeriesDatapointLabelsProperty = null;
	
	
	public SeriesColumnsProperty(Context context, Chart esChart) {
		super(context);
		
		m_Chart = esChart;
		m_pParent = context;
	}

	public SeriesColumnsProperty(Context context, int theme, Chart esChart) {
		super(context, theme);
		
		m_Chart = esChart;
		m_pParent = context;
	}

	public SeriesColumnsProperty(Context context, boolean cancelable,
			OnCancelListener cancelListener, Chart esChart) {
		super(context, cancelable, cancelListener);
 
		m_Chart = esChart;
		m_pParent = context;
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		if (m_Chart == null)
		{
			return;
		}
        
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.series_columns_property_dialog);
        
        
        this.getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
        
        m_ColumnProperty = new ColumnProperty();
        m_RowLabelList = new SeriesRowLabelList();
        
        m_ChartCheckBoxListener = new ChartCheckBoxListener(m_ChartCheckBoxListenerCallback);
        
		m_ColorPalette = new ColorPalette(m_pParent, m_Chart);
		m_ColorPalette.requestWindowFeature(Window.FEATURE_NO_TITLE);
		m_ColorPalette.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		
		m_TextViewTitle = (TextView)findViewById(R.id.text_series_columns_title);

		radioGroupColumnsProperty = (RadioGroup)findViewById(R.id.radio_series_columns_property);
		if (radioGroupColumnsProperty != null) radioGroupColumnsProperty.setOnCheckedChangeListener(this);
		
		m_CheckBoxOptionsHideSeries = (CheckBox)findViewById(R.id.checkbox_series_columns_property_options_hideseries);
		if (m_CheckBoxOptionsHideSeries != null) m_CheckBoxOptionsHideSeries.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_CheckBoxOptionsExcludeSeries = (CheckBox)findViewById(R.id.checkbox_series_columns_property_options_excludeseries);
		if (m_CheckBoxOptionsExcludeSeries != null) m_CheckBoxOptionsExcludeSeries.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_CheckBoxOptionsPlotOn = (CheckBox)findViewById(R.id.checkbox_series_columns_property_options_ploton);
		if (m_CheckBoxOptionsPlotOn != null) m_CheckBoxOptionsPlotOn.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_CheckBoxOptionsShowMarker = (CheckBox)findViewById(R.id.checkbox_series_columns_property_options_showmarkers);
		if (m_CheckBoxOptionsShowMarker != null) m_CheckBoxOptionsShowMarker.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_CheckBoxOptionsAutomaticMarker = (CheckBox)findViewById(R.id.checkbox_series_columns_property_options_automarkers);
		if (m_CheckBoxOptionsAutomaticMarker != null) m_CheckBoxOptionsAutomaticMarker.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_SwitchOptionsHideSeries = (Switch)findViewById(R.id.switch_series_columns_property_options_hideseries);
		if (m_SwitchOptionsHideSeries != null) m_SwitchOptionsHideSeries.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_SwitchOptionsExcludeSeries = (Switch)findViewById(R.id.switch_series_columns_property_options_excludeseries);
		if (m_SwitchOptionsExcludeSeries != null) m_SwitchOptionsExcludeSeries.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_SwitchOptionsPlotOn = (Switch)findViewById(R.id.switch_series_columns_property_options_ploton);
		if (m_SwitchOptionsPlotOn != null) m_SwitchOptionsPlotOn.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_SwitchOptionsShowMarker = (Switch)findViewById(R.id.switch_series_columns_property_options_showmarkers);
		if (m_SwitchOptionsShowMarker != null) m_SwitchOptionsShowMarker.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_SwitchOptionsAutomaticMarker = (Switch)findViewById(R.id.switch_series_columns_property_options_automarkers);
		if (m_SwitchOptionsAutomaticMarker != null) m_SwitchOptionsAutomaticMarker.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_ButtonOptionsGainColorPreview = (Button)findViewById(R.id.button_series_columns_property_options_gaincolor_preview);
        if (m_ButtonOptionsGainColorPreview != null) m_ButtonOptionsGainColorPreview.setOnClickListener(this);
        
        m_ButtonOptionsLossColorPreview = (Button)findViewById(R.id.button_series_columns_property_options_losscolor_preview);
        if (m_ButtonOptionsLossColorPreview != null) m_ButtonOptionsLossColorPreview.setOnClickListener(this);

        adapterlistViewSeriesRowsList = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, m_RowLabelList.labelList);
		
        listViewSeriesRowsList = (ListView)findViewById(R.id.listview_series_columns_property_datapointlabels_row);
		if (listViewSeriesRowsList != null)
		{
			listViewSeriesRowsList.setAdapter(adapterlistViewSeriesRowsList);
			listViewSeriesRowsList.setOnItemClickListener(m_ListViewitemClickListener);
			listViewSeriesRowsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
		m_ButtonSeriesRowsProperty = (Button)findViewById(R.id.button_series_columns_property_datapointlabels_row_property);
        if (m_ButtonSeriesRowsProperty != null) m_ButtonSeriesRowsProperty.setOnClickListener(this);

        m_DlgSeriesDatapointLabelsProperty = new SeriesDatapointLabelsProperty(m_pParent, m_Chart);
        m_DlgSeriesDatapointLabelsProperty.requestWindowFeature(Window.FEATURE_NO_TITLE);
        m_DlgSeriesDatapointLabelsProperty.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }
    
    private AdapterView.OnItemClickListener m_ListViewitemClickListener = new AdapterView.OnItemClickListener()
    {
    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	{
    		if (null == view)
    		{
    			return;
    		}
    		
    		int nId = view.getId();
    		
    		updateControl(nId, position);
    	}
	};

	private ChartCheckBoxListenerCallback m_ChartCheckBoxListenerCallback = new ChartCheckBoxListenerCallback()
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
			updateChart( buttonView.getId() );
		}
	};

	@Override
	public void onClick(View v) {
		
		if (null == v)
		{
			return;
		}
		
		int nId = v.getId();
		
		switch (nId)
		{
		case R.id.button_series_columns_property_options_gaincolor_preview:
		case R.id.button_series_columns_property_options_losscolor_preview:
			m_ColorPalette.show();
			m_ColorPalette.SetCurrentColumn(m_nCurrentColumn, nId);
			break;
			
		case R.id.button_series_columns_property_datapointlabels_row_property:
			m_DlgSeriesDatapointLabelsProperty.show();
			((EssenceChartActivity)m_pParent).repositionPropertyDialog();
			m_DlgSeriesDatapointLabelsProperty.SetSelectedColumnRow(m_nCurrentColumn, m_nSelectedRow);
			break;

		default:
			updateChart(nId);
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		switch (checkedId)
		{
		case R.id.series_columns_property_options:
		case R.id.series_columns_property_datapointlabels:
			showProperty(checkedId);
			break;

		default:
			updateChart(checkedId);
			break;
		}
		
	}
	
	public void SetPropertyType(int nType)
	{
		InitBackupData();
		updateBackupData(true);
		InitCtrl();
	}
	public void SetCurrentColumn(int nCol)
	{
		m_nCurrentColumn = nCol;
		
		InitBackupData();
		updateBackupData(true);
		InitCtrl();
	}
	
	public void updateBackupData(boolean bUpdatData)
	{
		if (bUpdatData)
		{
			m_ColumnProperty.bSeriesHide = m_Chart.getSeriesOptionSeriesHide(m_nCurrentColumn);
			m_ColumnProperty.bSeriesExclude = m_Chart.getSeriesOptionSeriesExclude(m_nCurrentColumn);
			m_ColumnProperty.bSeriesShowMarker = m_Chart.getSeriesOptionSeriesMarkersShow(m_nCurrentColumn);
			m_ColumnProperty.bSeriesAutoMarker = m_Chart.getSeriesOptionSeriesAutoMarkers(m_nCurrentColumn);
			
			m_ColumnProperty.nColorHiLoGain = m_Chart.getSeriesOptionSeriesHiLoGainColor(m_nCurrentColumn);
			m_ColumnProperty.nColorHiLoLoss = m_Chart.getSeriesOptionSeriesHiLoLossColor(m_nCurrentColumn);
			
			String strRow = m_pParent.getString(R.string.chart_property_series_datapointlabels_rows);
			int nCount = m_Chart.getSeriesRowCount();
			for (int i=0; i<nCount; i++)
			{
				String strLabel = m_Chart.getSeriesRowLabel(i);
				if (strLabel.isEmpty())
				{
					int n = i+1;
					strLabel = strRow + " "+ n; 
				}
				m_RowLabelList.AddLabel(strLabel);
			}
			
			m_RowLabelList.AddLabel(m_pParent.getString(R.string.chart_property_series_datapointlabels_allrows));
			adapterlistViewSeriesRowsList.notifyDataSetChanged();
		}
		else
		{
		}
	}
	
	public void InitCtrl()
	{
		String strTitle;
		int nCount = m_Chart.getSeriesColCount();
		if (nCount <= m_nCurrentColumn || 0 > m_nCurrentColumn)
		{
			strTitle = m_pParent.getString(R.string.chart_property_series_column_property) +
					"(" +
					m_pParent.getString(R.string.chart_property_series_allcolumns) +
					")";
		}
		else
		{
			strTitle = m_pParent.getString(R.string.chart_property_series_column_property) +
					"(" +
					m_Chart.getSeriesColLabel(m_nCurrentColumn) +
					")";
		}
		m_TextViewTitle.setText(strTitle);
		
		updateColumnOptionsControl(-1);
		
		m_nSelectedRow = m_Chart.getSeriesRowCount();
		listViewSeriesRowsList.setItemChecked(m_nSelectedRow, true);
		updateColumnDatapointLabelsControl(m_nSelectedRow);
		
		radioGroupColumnsProperty.check(R.id.series_columns_property_options);
		showProperty(R.id.series_columns_property_options);
	}
	
	public void InitBackupData()
	{
		m_ColumnProperty.Init();
		m_RowLabelList.Init();
	}
	
	protected void showProperty(int nId)
	{
		m_nActiveTabID = nId;
		
		RelativeLayout relativeLayoutOptions = (RelativeLayout)findViewById(R.id.relative_layout_series_columns_property_options);
		relativeLayoutOptions.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutLines = (RelativeLayout)findViewById(R.id.relative_layout_series_columns_property_lines);
		relativeLayoutLines.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutGuideLines = (RelativeLayout)findViewById(R.id.relative_layout_series_columns_property_guidelines);
		relativeLayoutGuideLines.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutDatapoint = (RelativeLayout)findViewById(R.id.relative_layout_series_columns_property_datapoint);
		relativeLayoutDatapoint.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutDatapointLabels = (RelativeLayout)findViewById(R.id.relative_layout_series_columns_property_datapointlabels);
		relativeLayoutDatapointLabels.setVisibility(View.INVISIBLE);

		switch (nId)
		{
		case R.id.series_columns_property_options:
			relativeLayoutOptions.setVisibility(View.VISIBLE);
			break;

		case R.id.series_columns_property_datapointlabels:
			relativeLayoutDatapointLabels.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

	public void updateChart(int nId)
	{
		switch (m_nActiveTabID)
		{
		case R.id.series_columns_property_options:
			updateColumnOptions(nId);
			break;

		case R.id.series_columns_property_datapointlabels:
			updateColumnDatapointLabels(nId);
			break;

		default:
			break;
		}
	}	
	public void updateColumnOptions(int nId)
	{
		switch (nId)
		{
		case R.id.checkbox_series_columns_property_options_hideseries:
		case R.id.switch_series_columns_property_options_hideseries:
		{
			int nCount = m_Chart.getSeriesColCount();
			if (nCount <= m_nCurrentColumn || 0 > m_nCurrentColumn)
			{
				for (int i=0; i<nCount; i++)
				{
					if (null != m_CheckBoxOptionsHideSeries)
						m_Chart.setSeriesOptionSeriesHide(i, m_CheckBoxOptionsHideSeries.isChecked());
					if (null != m_SwitchOptionsHideSeries)
						m_Chart.setSeriesOptionSeriesHide(i, m_SwitchOptionsHideSeries.isChecked());
				}
			}
			else
			{
				if (null != m_CheckBoxOptionsHideSeries)
					m_Chart.setSeriesOptionSeriesHide(m_nCurrentColumn, m_CheckBoxOptionsHideSeries.isChecked());
				if (null != m_SwitchOptionsHideSeries)
					m_Chart.setSeriesOptionSeriesHide(m_nCurrentColumn, m_SwitchOptionsHideSeries.isChecked());
			}
		}

		break;

		case R.id.checkbox_series_columns_property_options_excludeseries:
		case R.id.switch_series_columns_property_options_excludeseries:
		{
			int nCount = m_Chart.getSeriesColCount();
			if (nCount <= m_nCurrentColumn || 0 > m_nCurrentColumn)
			{
				for (int i=0; i<nCount; i++)
				{
					if (null != m_CheckBoxOptionsExcludeSeries)
						m_Chart.setSeriesOptionSeriesExclude(i, m_CheckBoxOptionsExcludeSeries.isChecked());
					if (null != m_SwitchOptionsExcludeSeries)
						m_Chart.setSeriesOptionSeriesExclude(i, m_SwitchOptionsExcludeSeries.isChecked());
				}
			}
			else
			{
				if (null != m_CheckBoxOptionsExcludeSeries)
					m_Chart.setSeriesOptionSeriesExclude(m_nCurrentColumn, m_CheckBoxOptionsExcludeSeries.isChecked());
				if (null != m_SwitchOptionsExcludeSeries)
					m_Chart.setSeriesOptionSeriesExclude(m_nCurrentColumn, m_SwitchOptionsExcludeSeries.isChecked());
			}
		}
		break;

		case R.id.checkbox_series_columns_property_options_ploton:
		case R.id.switch_series_columns_property_options_ploton:

			break;

		case R.id.checkbox_series_columns_property_options_showmarkers:
		case R.id.switch_series_columns_property_options_showmarkers:
		{
			int nCount = m_Chart.getSeriesColCount();
			if (nCount <= m_nCurrentColumn || 0 > m_nCurrentColumn)
			{
				for (int i=0; i<nCount; i++)
				{
					if (null != m_CheckBoxOptionsShowMarker)
						m_Chart.setSeriesOptionSeriesMarkersShow(i, m_CheckBoxOptionsShowMarker.isChecked());
					if (null != m_SwitchOptionsShowMarker)
						m_Chart.setSeriesOptionSeriesMarkersShow(i, m_SwitchOptionsShowMarker.isChecked());
				}
			}
			else
			{
				if (null != m_CheckBoxOptionsShowMarker)
					m_Chart.setSeriesOptionSeriesMarkersShow(m_nCurrentColumn, m_CheckBoxOptionsShowMarker.isChecked());
				if (null != m_SwitchOptionsShowMarker)
					m_Chart.setSeriesOptionSeriesMarkersShow(m_nCurrentColumn, m_SwitchOptionsShowMarker.isChecked());
			}
		}
		break;

		case R.id.checkbox_series_columns_property_options_automarkers:
		case R.id.switch_series_columns_property_options_automarkers:
		{
			int nCount = m_Chart.getSeriesColCount();
			if (nCount <= m_nCurrentColumn || 0 > m_nCurrentColumn)
			{
				for (int i=0; i<nCount; i++)
				{
					if (null != m_CheckBoxOptionsAutomaticMarker)
						m_Chart.setSeriesOptionSeriesAutoMarkers(i, m_CheckBoxOptionsAutomaticMarker.isChecked());
					if (null != m_SwitchOptionsAutomaticMarker)
						m_Chart.setSeriesOptionSeriesAutoMarkers(i, m_SwitchOptionsAutomaticMarker.isChecked());
				}
			}
			else
			{
				if (null != m_CheckBoxOptionsAutomaticMarker)
					m_Chart.setSeriesOptionSeriesAutoMarkers(m_nCurrentColumn, m_CheckBoxOptionsAutomaticMarker.isChecked());
				if (null != m_SwitchOptionsAutomaticMarker)
					m_Chart.setSeriesOptionSeriesAutoMarkers(m_nCurrentColumn, m_SwitchOptionsAutomaticMarker.isChecked());
			}
		}
		break;

		default:
			break;
		}
	}
	public void updateColumnLines(int nId)
	{
		switch (nId)
		{

		default:
			break;
		}
	}
	public void updateColumnGuidlines(int nId)
	{
		switch (nId)
		{

		default:
			break;
		}
	}
	public void updateColumnDatapoint(int nId)
	{
		switch (nId)
		{

		default:
			break;
		}
	}
	public void updateColumnDatapointLabels(int nId)
	{
		switch (nId)
		{

		default:
			break;
		}
	}
	
	public void SetColorPreview(int nColor, int nId)
	{
		Button buttonColorPreview = (Button)findViewById(nId);
		if (null != buttonColorPreview){
			buttonColorPreview.setBackgroundColor(nColor);
		}
	}
	
	public void updateControl(int nId, int nValue)
	{
		switch (m_nActiveTabID)
		{
		case R.id.series_columns_property_options:
			updateColumnOptionsControl(nId);
			break;

		case R.id.series_columns_property_datapointlabels:
			updateColumnDatapointLabelsControl(nValue);
			break;

		default:
			break;
		}
	}
	public void updateColumnOptionsControl(int nId)
	{
		if (null != m_CheckBoxOptionsHideSeries) m_CheckBoxOptionsHideSeries.setChecked(m_ColumnProperty.bSeriesHide);
		if (null != m_CheckBoxOptionsExcludeSeries) m_CheckBoxOptionsExcludeSeries.setChecked(m_ColumnProperty.bSeriesExclude);
		if (null != m_CheckBoxOptionsPlotOn) m_CheckBoxOptionsPlotOn.setChecked(false);
		if (null != m_CheckBoxOptionsShowMarker) m_CheckBoxOptionsShowMarker.setChecked(m_ColumnProperty.bSeriesShowMarker);
		if (null != m_CheckBoxOptionsAutomaticMarker) m_CheckBoxOptionsAutomaticMarker.setChecked(m_ColumnProperty.bSeriesAutoMarker);

		if (null != m_SwitchOptionsHideSeries) m_SwitchOptionsHideSeries.setChecked(m_ColumnProperty.bSeriesHide);
		if (null != m_SwitchOptionsExcludeSeries) m_SwitchOptionsExcludeSeries.setChecked(m_ColumnProperty.bSeriesExclude);
		if (null != m_SwitchOptionsPlotOn) m_SwitchOptionsPlotOn.setChecked(false);
		if (null != m_SwitchOptionsShowMarker) m_SwitchOptionsShowMarker.setChecked(m_ColumnProperty.bSeriesShowMarker);
		if (null != m_SwitchOptionsAutomaticMarker) m_SwitchOptionsAutomaticMarker.setChecked(m_ColumnProperty.bSeriesAutoMarker);
		
		m_ButtonOptionsGainColorPreview.setBackgroundColor(m_ColumnProperty.getColorHiLoGain());
		m_ButtonOptionsLossColorPreview.setBackgroundColor(m_ColumnProperty.getColorHiLoLoss());
	}
	public void updateColumnLinesControl(int nId)
	{
	}
	public void updateColumnGuidlinesControl(int nId)
	{
	}
	public void updateColumnDatapointControl(int nId)
	{
	}
	public void updateColumnDatapointLabelsControl(int nRow)
	{
		m_nSelectedRow = nRow;
		
		ViewGroup.LayoutParams paramListView = listViewSeriesRowsList.getLayoutParams();
		paramListView.height = m_RowLabelList.labelList.size() * HEIGHT_LISTVIEW_ITEM;
		listViewSeriesRowsList.setLayoutParams(paramListView);
	}
	
	public int getColumnOptionsCtrlId(int nType)
	{
		int nId = 0;

		return nId;
	}
	public int getColumnLinesCtrlId(int nType)
	{
		int nId = 0;

		return nId;
	}
	public int getColumnGuidlinesCtrlId(int nType)
	{
		int nId = 0;

		return nId;
	}
	public int getColumnDatapointCtrlId(int nType)
	{
		int nId = 0;

		return nId;
	}
	public int getColumnDatapointLabelsCtrlId(int nType)
	{
		int nId = 0;

		return nId;
	}

}
