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
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;

public class SeriesDatapointLabelsProperty extends Dialog implements android.view.View.OnClickListener, OnCheckedChangeListener {

	private final int HEIGHT_LISTVIEW_ITEM = 65;
	
	public final int ESChLabelLocationTypeNone = 0;
	public final int ESChLabelLocationTypeAbovePoint = 1;
	public final int ESChLabelLocationTypeBelowPoint = 2;
	public final int ESChLabelLocationTypeCenter = 3;
	public final int ESChLabelLocationTypeBase = 4;
	public final int ESChLabelLocationTypeInside = 5;
	public final int ESChLabelLocationTypeOutside = 6;
	public final int ESChLabelLocationTypeLeft = 7;
	public final int ESChLabelLocationTypeRight = 8;
	
	public final int ESBrushStyleNull = 0;
	public final int ESBrushStyleSolid = 1;
	public final int ESBrushStylePattern = 2;
	public final int ESBrushStyleHatched = 3;

	public final int ESBrushPattern94Percent = 0;
	public final int ESBrushPattern88Percent = 1;  
	public final int ESBrushPattern75Percent = 2;
	public final int ESBrushPattern50Percent = 3;
	public final int ESBrushPattern25Percent = 4;
	public final int ESBrushPatternBoldHorizontal = 5;
	public final int ESBrushPatternBoldVertical = 6;
	public final int ESBrushPatternBoldDownDiagonal = 7;
	public final int ESBrushPatternBoldUpDiagonal = 8;
	public final int ESBrushPatternChecks = 9;
	public final int ESBrushPatternWeave = 10;
	public final int ESBrushPatternHorizontal = 11;
	public final int ESBrushPatternVertical = 12;
	public final int ESBrushPatternDownDiagonal = 13;
	public final int ESBrushPatternUpDiagonal = 14;
	public final int ESBrushPatternGrid = 15;
	public final int ESBrushPatternTrellis = 16;
	public final int ESBrushPatternInvertedTrellis = 17;
	public final int ESBrushPatternCount = 18;
	    
	public final int ESPenStyleNull = 0;
	public final int ESPenStyleSolid = 1;
	public final int ESPenStyleDashed = 2;
	public final int ESPenStyleDotted = 3;
	public final int ESPenStyleDashDot = 4;
	public final int ESPenStyleDashDotDot = 5;
	public final int ESPenStyleDitted = 6;
	public final int ESPenStyleDashDit = 7;
	public final int ESPenStyleDashDitDit = 8;
	public final int ESPenStyleNative = 9;

	public final int eFormat_NUMBER 	= 0x01;
	public final int eFormat_CURRENCY	= 0x02;
	public final int eFormat_DATE		= 0x04;
	public final int eFormat_TIME		= 0x08;
	public final int eFormat_PERCENTAGE	= 0x10;
	public final int eFormat_FRACTION	= 0x20;
	public final int eFormat_SCIENTIFIC	= 0x40;
	public final int eFormat_GENERAL	= 0xFF;
	
	public class DatapointLabelProperty
	{
		public DatapointLabelProperty()
		{
			Init();
		}
		
		public void Init()
		{
			bUseValueLabel = false;
			bUsePercentLabel = false;
			bUseSeriesNameLabel = false;
			bUsePointNameLabel = false;
			
			nTextLocation = 0;
			nLineStyle = 0;
		}
		
		public int getColor()
		{
			int nColor = 0xFF000000;

			return nColor;
		}
		
		public boolean bUseValueLabel;
		public boolean bUsePercentLabel;
		public boolean bUseSeriesNameLabel;
		public boolean bUsePointNameLabel;
		
		public int nTextLocation;
		public int nLineStyle;
	}
	private DatapointLabelProperty m_DatapointLabelProperty = null;
	
	public class DatapointFillProperty
	{
		public DatapointFillProperty()
		{
			Init();
		}
		
		public void Init()
		{
			nFillStyle = 0;
			nFillPattern = 0;
			nFillColor = 0;
			nFillPatternColor = 0;
			
			nEdgeStyle = 0;
			nEdgeWidth = 0;
			nEdgeColor = 0;
		}
		
		public int getFillColor()
		{
			int nColor = 0xFF000000;
			int rgbRed = nFillColor & 0x0000ff;
			int rgbGreen = nFillColor & 0x00ff00;
			int rgbBlue = nFillColor & 0xff0000;
			
			nColor = nColor + rgbRed + rgbGreen + rgbBlue; 
			
			return nColor;
		}
		public int getFillPatternColor()
		{
			int nColor = 0xFF000000;
			int rgbRed = nFillPatternColor & 0x0000ff;
			int rgbGreen = nFillPatternColor & 0x00ff00;
			int rgbBlue = nFillPatternColor & 0xff0000;
			
			nColor = nColor + rgbRed + rgbGreen + rgbBlue; 
			
			return nColor;
		}
		public int getFillEdgeColor()
		{
			int nColor = 0xFF000000;
			int rgbRed = nEdgeColor & 0x0000ff;
			int rgbGreen = nEdgeColor & 0x00ff00;
			int rgbBlue = nEdgeColor & 0xff0000;
			
			nColor = nColor + rgbRed + rgbGreen + rgbBlue; 
			
			return nColor;
		}
		
		public int nFillStyle;
		public int nFillPattern;
		public int nFillColor;
		public int nFillPatternColor;
		
		public int nEdgeStyle;
		public int nEdgeWidth;
		public int nEdgeColor;
	}
	private DatapointFillProperty m_DatapointFillProperty = null;
		
	private Chart m_Chart = null;	
	
	private ColorPalette m_ColorPalette = null;	
	
	private ChartCheckBoxListener m_ChartCheckBoxListener = null;
	
	private int m_nActiveTabID;
	private int m_nSelectedColumn = -1;
	private int m_nSelectedRow = -1;
	
	Context m_pParent = null;
	
	private TextView	m_TextViewTitle = null;

	private RadioGroup	radioGroupDatapointLabelProperty = null;
	
	private RadioGroup	radioGroupApperanceTextLoaction = null;
	private RadioGroup	radioGroupApperanceLineStyle = null;
	
	private	CheckBox	m_CheckBoxApperanceLabelValue = null;
	private	CheckBox	m_CheckBoxApperanceLabelPercent = null;
	private	CheckBox	m_CheckBoxApperanceLabelSeries = null;
	private	CheckBox	m_CheckBoxApperanceLabelPoint = null;
	
	private	Switch		m_SwitchApperanceLabelValue = null;
	private	Switch		m_SwitchApperanceLabelPercent = null;
	private	Switch		m_SwitchApperanceLabelSeries = null;
	private	Switch		m_SwitchApperanceLabelPoint = null;
	
	private RadioGroup	radioGroupFillFillPattern = null;
	private Button m_ButtonFillFillColorPreview = null;
	
	private RadioGroup	radioGroupFillEdgeStyle = null;

	private Button m_ButtonFillEdgeColorPreview = null;
	
	private EditText m_EditValueFormat = null;
	private Button m_ButtonApplyValueFormat = null;
	
	private Button m_ButtonValueFormatGeneral = null;
	private List<String> listValueFormatGeneral = null;
	private ArrayAdapter<String> adapterlistViewValueFormatListGeneral = null;
	private ListView listViewValueFormatListGeneral = null;

	private Button m_ButtonValueFormatNumber = null;
	private List<String> listValueFormatNumber = null;
	private ArrayAdapter<String> adapterlistViewValueFormatListNumber = null;
	private ListView listViewValueFormatListNumber = null;

	private Button m_ButtonValueFormatCurrency = null;
	private List<String> listValueFormatCurrency = null;
	private ArrayAdapter<String> adapterlistViewValueFormatListCurrency = null;
	private ListView listViewValueFormatListCurrency = null;
	
	private Button m_ButtonValueFormatDate = null;
	private List<String> listValueFormatDate = null;
	private ArrayAdapter<String> adapterlistViewValueFormatListDate = null;
	private ListView listViewValueFormatListDate = null;
	
	private Button m_ButtonValueFormatTime = null;
	private List<String> listValueFormatTime = null;
	private ArrayAdapter<String> adapterlistViewValueFormatListTime = null;
	private ListView listViewValueFormatListTime = null;
	
	private Button m_ButtonValueFormatPercentage = null;
	private List<String> listValueFormatPercentage = null;
	private ArrayAdapter<String> adapterlistViewValueFormatListPercentage = null;
	private ListView listViewValueFormatListPercentage = null;
	
	private Button m_ButtonValueFormatFraction = null;
	private List<String> listValueFormatFraction = null;
	private ArrayAdapter<String> adapterlistViewValueFormatListFraction = null;
	private ListView listViewValueFormatListFraction = null;
	
	private Button m_ButtonValueFormatScientific = null;
	private List<String> listValueFormatScientific = null;
	private ArrayAdapter<String> adapterlistViewValueFormatListScientific = null;
	private ListView listViewValueFormatListScientific = null;
	
	private EditText m_EditPercentFormat = null;
	private Button m_ButtonApplyPercentFormat = null;
	
	private Button m_ButtonPercentFormatGeneral = null;
	private List<String> listPercentFormatGeneral = null;
	private ArrayAdapter<String> adapterlistViewPercentFormatListGeneral = null;
	private ListView listViewPercentFormatListGeneral = null;

	private Button m_ButtonPercentFormatNumber = null;
	private List<String> listPercentFormatNumber = null;
	private ArrayAdapter<String> adapterlistViewPercentFormatListNumber = null;
	private ListView listViewPercentFormatListNumber = null;

	private Button m_ButtonPercentFormatCurrency = null;
	private List<String> listPercentFormatCurrency = null;
	private ArrayAdapter<String> adapterlistViewPercentFormatListCurrency = null;
	private ListView listViewPercentFormatListCurrency = null;
	
	private Button m_ButtonPercentFormatDate = null;
	private List<String> listPercentFormatDate = null;
	private ArrayAdapter<String> adapterlistViewPercentFormatListDate = null;
	private ListView listViewPercentFormatListDate = null;
	
	private Button m_ButtonPercentFormatTime = null;
	private List<String> listPercentFormatTime = null;
	private ArrayAdapter<String> adapterlistViewPercentFormatListTime = null;
	private ListView listViewPercentFormatListTime = null;
	
	private Button m_ButtonPercentFormatPercentage = null;
	private List<String> listPercentFormatPercentage = null;
	private ArrayAdapter<String> adapterlistViewPercentFormatListPercentage = null;
	private ListView listViewPercentFormatListPercentage = null;
	
	private Button m_ButtonPercentFormatFraction = null;
	private List<String> listPercentFormatFraction = null;
	private ArrayAdapter<String> adapterlistViewPercentFormatListFraction = null;
	private ListView listViewPercentFormatListFraction = null;
	
	private Button m_ButtonPercentFormatScientific = null;
	private List<String> listPercentFormatScientific = null;
	private ArrayAdapter<String> adapterlistViewPercentFormatListScientific = null;
	private ListView listViewPercentFormatListScientific = null;

	public SeriesDatapointLabelsProperty(Context context, Chart esChart) {
		super(context);
		
		m_Chart = esChart;
		m_pParent = context;
	}

	public SeriesDatapointLabelsProperty(Context context, int theme, Chart esChart) {
		super(context, theme);
		
		m_Chart = esChart;
		m_pParent = context;
	}

	public SeriesDatapointLabelsProperty(Context context, boolean cancelable,
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
        setContentView(R.layout.series_datapointlabels_property_dialog);
        
        
        this.getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
        
        m_DatapointLabelProperty = new DatapointLabelProperty();
        m_DatapointFillProperty = new DatapointFillProperty();
        
        m_ChartCheckBoxListener = new ChartCheckBoxListener(m_ChartCheckBoxListenerCallback);

		m_ColorPalette = new ColorPalette(m_pParent, m_Chart);
		m_ColorPalette.requestWindowFeature(Window.FEATURE_NO_TITLE);
		m_ColorPalette.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		
		m_TextViewTitle = (TextView)findViewById(R.id.text_series_datapointlabels_title);

		radioGroupDatapointLabelProperty = (RadioGroup)findViewById(R.id.radio_series_datapointlabels_property);
		if (radioGroupDatapointLabelProperty != null) radioGroupDatapointLabelProperty.setOnCheckedChangeListener(this);
		
		radioGroupApperanceTextLoaction = (RadioGroup)findViewById(R.id.radio_series_datapointlabels_property_apperance_textloaction);
		if (radioGroupApperanceTextLoaction != null) radioGroupApperanceTextLoaction.setOnCheckedChangeListener(this);

		m_CheckBoxApperanceLabelValue = (CheckBox)findViewById(R.id.checkbox_series_datapointlabels_property_apperance_label_value);
		if (m_CheckBoxApperanceLabelValue != null) m_CheckBoxApperanceLabelValue.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_CheckBoxApperanceLabelPercent = (CheckBox)findViewById(R.id.checkbox_series_datapointlabels_property_apperance_label_percent);
		if (m_CheckBoxApperanceLabelPercent != null) m_CheckBoxApperanceLabelPercent.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_CheckBoxApperanceLabelSeries = (CheckBox)findViewById(R.id.checkbox_series_datapointlabels_property_apperance_label_seriesname);
		if (m_CheckBoxApperanceLabelSeries != null) m_CheckBoxApperanceLabelSeries.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_CheckBoxApperanceLabelPoint = (CheckBox)findViewById(R.id.checkbox_series_datapointlabels_property_apperance_label_pointname);
		if (m_CheckBoxApperanceLabelPoint != null) m_CheckBoxApperanceLabelPoint.setOnCheckedChangeListener(m_ChartCheckBoxListener);
		
		m_SwitchApperanceLabelValue = (Switch)findViewById(R.id.switch_series_datapointlabels_property_apperance_label_value);
		if (m_SwitchApperanceLabelValue != null) m_SwitchApperanceLabelValue.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_SwitchApperanceLabelPercent = (Switch)findViewById(R.id.switch_series_datapointlabels_property_apperance_label_percent);
		if (m_SwitchApperanceLabelPercent != null) m_SwitchApperanceLabelPercent.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_SwitchApperanceLabelSeries = (Switch)findViewById(R.id.switch_series_datapointlabels_property_apperance_label_seriesname);
		if (m_SwitchApperanceLabelSeries != null) m_SwitchApperanceLabelSeries.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_SwitchApperanceLabelPoint = (Switch)findViewById(R.id.switch_series_datapointlabels_property_apperance_label_pointname);
		if (m_SwitchApperanceLabelPoint != null) m_SwitchApperanceLabelPoint.setOnCheckedChangeListener(m_ChartCheckBoxListener);
		
		radioGroupFillFillPattern = (RadioGroup)findViewById(R.id.radio_series_datapointlabels_property_fill_fillpattern);
		if (radioGroupFillFillPattern != null) radioGroupFillFillPattern.setOnCheckedChangeListener(this);
		
		m_ButtonFillFillColorPreview = (Button)findViewById(R.id.button_series_datapointlabels_property_fill_fillcolor_preview);
    	if (m_ButtonFillFillColorPreview != null) m_ButtonFillFillColorPreview.setOnClickListener(this);
		
    	radioGroupFillEdgeStyle = (RadioGroup)findViewById(R.id.radio_series_datapointlabels_property_fill_edgestyle);
		if (radioGroupFillEdgeStyle != null) radioGroupFillEdgeStyle.setOnCheckedChangeListener(this);

		m_ButtonFillEdgeColorPreview = (Button)findViewById(R.id.button_series_datapointlabels_property_fill_edgecolor_preview);
    	if (m_ButtonFillEdgeColorPreview != null) m_ButtonFillEdgeColorPreview.setOnClickListener(this);

		m_EditValueFormat = (EditText)findViewById(R.id.edit_series_datapointlabels_property_valueformat);
		m_ButtonApplyValueFormat = (Button)findViewById(R.id.button_series_datapointlabels_property_valueformat);
    	if (m_ButtonApplyValueFormat != null) m_ButtonApplyValueFormat.setOnClickListener(this);
    	
    	m_ButtonValueFormatGeneral = (Button)findViewById(R.id.button_series_datapointlabels_property_valueformat_general);
    	if (m_ButtonValueFormatGeneral != null) m_ButtonValueFormatGeneral.setOnClickListener(this);

    	listValueFormatGeneral = new ArrayList<String>();
		adapterlistViewValueFormatListGeneral = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listValueFormatGeneral);
		
		listViewValueFormatListGeneral = (ListView)findViewById(R.id.listview_series_datapointlabels_property_valueformat_general);
		if (listViewValueFormatListGeneral != null)
		{
			listViewValueFormatListGeneral.setAdapter(adapterlistViewValueFormatListGeneral);
			listViewValueFormatListGeneral.setOnItemClickListener(m_ListViewitemClickListener);
			listViewValueFormatListGeneral.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonValueFormatNumber = (Button)findViewById(R.id.button_series_datapointlabels_property_valueformat_number);
    	if (m_ButtonValueFormatNumber != null) m_ButtonValueFormatNumber.setOnClickListener(this);
    	
		listValueFormatNumber = new ArrayList<String>();
		adapterlistViewValueFormatListNumber = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listValueFormatNumber);
		
		listViewValueFormatListNumber = (ListView)findViewById(R.id.listview_series_datapointlabels_property_valueformat_number);
		if (listViewValueFormatListNumber != null)
		{
			listViewValueFormatListNumber.setAdapter(adapterlistViewValueFormatListNumber);
			listViewValueFormatListNumber.setOnItemClickListener(m_ListViewitemClickListener);
			listViewValueFormatListNumber.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonValueFormatCurrency = (Button)findViewById(R.id.button_series_datapointlabels_property_valueformat_currency);
    	if (m_ButtonValueFormatCurrency != null) m_ButtonValueFormatCurrency.setOnClickListener(this);
    	
		listValueFormatCurrency = new ArrayList<String>();
		adapterlistViewValueFormatListCurrency = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listValueFormatCurrency);
		
		listViewValueFormatListCurrency = (ListView)findViewById(R.id.listview_series_datapointlabels_property_valueformat_currency);
		if (listViewValueFormatListCurrency != null)
		{
			listViewValueFormatListCurrency.setAdapter(adapterlistViewValueFormatListCurrency);
			listViewValueFormatListCurrency.setOnItemClickListener(m_ListViewitemClickListener);
			listViewValueFormatListCurrency.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonValueFormatDate = (Button)findViewById(R.id.button_series_datapointlabels_property_valueformat_date);
    	if (m_ButtonValueFormatDate != null) m_ButtonValueFormatDate.setOnClickListener(this);
    	
		listValueFormatDate = new ArrayList<String>();
		adapterlistViewValueFormatListDate = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listValueFormatDate);
		
		listViewValueFormatListDate = (ListView)findViewById(R.id.listview_series_datapointlabels_property_valueformat_date);
		if (listViewValueFormatListDate != null)
		{
			listViewValueFormatListDate.setAdapter(adapterlistViewValueFormatListDate);
			listViewValueFormatListDate.setOnItemClickListener(m_ListViewitemClickListener);
			listViewValueFormatListDate.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonValueFormatTime = (Button)findViewById(R.id.button_series_datapointlabels_property_valueformat_time);
    	if (m_ButtonValueFormatTime != null) m_ButtonValueFormatTime.setOnClickListener(this);
    	
		listValueFormatTime = new ArrayList<String>();
		adapterlistViewValueFormatListTime = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listValueFormatTime);
		
		listViewValueFormatListTime = (ListView)findViewById(R.id.listview_series_datapointlabels_property_valueformat_time);
		if (listViewValueFormatListTime != null)
		{
			listViewValueFormatListTime.setAdapter(adapterlistViewValueFormatListTime);
			listViewValueFormatListTime.setOnItemClickListener(m_ListViewitemClickListener);
			listViewValueFormatListTime.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonValueFormatPercentage = (Button)findViewById(R.id.button_series_datapointlabels_property_valueformat_percentage);
    	if (m_ButtonValueFormatPercentage != null) m_ButtonValueFormatPercentage.setOnClickListener(this);
    	
		listValueFormatPercentage = new ArrayList<String>();
		adapterlistViewValueFormatListPercentage = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listValueFormatPercentage);
		
		listViewValueFormatListPercentage = (ListView)findViewById(R.id.listview_series_datapointlabels_property_valueformat_percentage);
		if (listViewValueFormatListPercentage != null)
		{
			listViewValueFormatListPercentage.setAdapter(adapterlistViewValueFormatListPercentage);
			listViewValueFormatListPercentage.setOnItemClickListener(m_ListViewitemClickListener);
			listViewValueFormatListPercentage.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonValueFormatFraction = (Button)findViewById(R.id.button_series_datapointlabels_property_valueformat_fraction);
    	if (m_ButtonValueFormatFraction != null) m_ButtonValueFormatFraction.setOnClickListener(this);
    	
		listValueFormatFraction = new ArrayList<String>();
		adapterlistViewValueFormatListFraction = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listValueFormatFraction);
		
		listViewValueFormatListFraction = (ListView)findViewById(R.id.listview_series_datapointlabels_property_valueformat_fraction);
		if (listViewValueFormatListFraction != null)
		{
			listViewValueFormatListFraction.setAdapter(adapterlistViewValueFormatListFraction);
			listViewValueFormatListFraction.setOnItemClickListener(m_ListViewitemClickListener);
			listViewValueFormatListFraction.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonValueFormatScientific = (Button)findViewById(R.id.button_series_datapointlabels_property_valueformat_scientific);
    	if (m_ButtonValueFormatScientific != null) m_ButtonValueFormatScientific.setOnClickListener(this);
    	
		listValueFormatScientific = new ArrayList<String>();
		adapterlistViewValueFormatListScientific = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listValueFormatScientific);
		
		listViewValueFormatListScientific = (ListView)findViewById(R.id.listview_series_datapointlabels_property_valueformat_scientific);
		if (listViewValueFormatListScientific != null)
		{
			listViewValueFormatListScientific.setAdapter(adapterlistViewValueFormatListScientific);
			listViewValueFormatListScientific.setOnItemClickListener(m_ListViewitemClickListener);
			listViewValueFormatListScientific.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
		m_EditPercentFormat = (EditText)findViewById(R.id.edit_series_datapointlabels_property_percentformat);
		m_ButtonApplyPercentFormat = (Button)findViewById(R.id.button_series_datapointlabels_property_percentformat);
    	if (m_ButtonApplyPercentFormat != null) m_ButtonApplyPercentFormat.setOnClickListener(this);
    	
    	m_ButtonPercentFormatGeneral = (Button)findViewById(R.id.button_series_datapointlabels_property_percentformat_general);
    	if (m_ButtonPercentFormatGeneral != null) m_ButtonPercentFormatGeneral.setOnClickListener(this);

    	listPercentFormatGeneral = new ArrayList<String>();
		adapterlistViewPercentFormatListGeneral = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listPercentFormatGeneral);
		
		listViewPercentFormatListGeneral = (ListView)findViewById(R.id.listview_series_datapointlabels_property_percentformat_general);
		if (listViewPercentFormatListGeneral != null)
		{
			listViewPercentFormatListGeneral.setAdapter(adapterlistViewPercentFormatListGeneral);
			listViewPercentFormatListGeneral.setOnItemClickListener(m_ListViewitemClickListener);
			listViewPercentFormatListGeneral.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonPercentFormatNumber = (Button)findViewById(R.id.button_series_datapointlabels_property_percentformat_number);
    	if (m_ButtonPercentFormatNumber != null) m_ButtonPercentFormatNumber.setOnClickListener(this);
    	
		listPercentFormatNumber = new ArrayList<String>();
		adapterlistViewPercentFormatListNumber = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listPercentFormatNumber);
		
		listViewPercentFormatListNumber = (ListView)findViewById(R.id.listview_series_datapointlabels_property_percentformat_number);
		if (listViewPercentFormatListNumber != null)
		{
			listViewPercentFormatListNumber.setAdapter(adapterlistViewPercentFormatListNumber);
			listViewPercentFormatListNumber.setOnItemClickListener(m_ListViewitemClickListener);
			listViewPercentFormatListNumber.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonPercentFormatCurrency = (Button)findViewById(R.id.button_series_datapointlabels_property_percentformat_currency);
    	if (m_ButtonPercentFormatCurrency != null) m_ButtonPercentFormatCurrency.setOnClickListener(this);
    	
		listPercentFormatCurrency = new ArrayList<String>();
		adapterlistViewPercentFormatListCurrency = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listPercentFormatCurrency);
		
		listViewPercentFormatListCurrency = (ListView)findViewById(R.id.listview_series_datapointlabels_property_percentformat_currency);
		if (listViewPercentFormatListCurrency != null)
		{
			listViewPercentFormatListCurrency.setAdapter(adapterlistViewPercentFormatListCurrency);
			listViewPercentFormatListCurrency.setOnItemClickListener(m_ListViewitemClickListener);
			listViewPercentFormatListCurrency.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonPercentFormatDate = (Button)findViewById(R.id.button_series_datapointlabels_property_percentformat_date);
    	if (m_ButtonPercentFormatDate != null) m_ButtonPercentFormatDate.setOnClickListener(this);
    	
		listPercentFormatDate = new ArrayList<String>();
		adapterlistViewPercentFormatListDate = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listPercentFormatDate);
		
		listViewPercentFormatListDate = (ListView)findViewById(R.id.listview_series_datapointlabels_property_percentformat_date);
		if (listViewPercentFormatListDate != null)
		{
			listViewPercentFormatListDate.setAdapter(adapterlistViewPercentFormatListDate);
			listViewPercentFormatListDate.setOnItemClickListener(m_ListViewitemClickListener);
			listViewPercentFormatListDate.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonPercentFormatTime = (Button)findViewById(R.id.button_series_datapointlabels_property_percentformat_time);
    	if (m_ButtonPercentFormatTime != null) m_ButtonPercentFormatTime.setOnClickListener(this);
    	
		listPercentFormatTime = new ArrayList<String>();
		adapterlistViewPercentFormatListTime = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listPercentFormatTime);
		
		listViewPercentFormatListTime = (ListView)findViewById(R.id.listview_series_datapointlabels_property_percentformat_time);
		if (listViewPercentFormatListTime != null)
		{
			listViewPercentFormatListTime.setAdapter(adapterlistViewPercentFormatListTime);
			listViewPercentFormatListTime.setOnItemClickListener(m_ListViewitemClickListener);
			listViewPercentFormatListTime.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonPercentFormatPercentage = (Button)findViewById(R.id.button_series_datapointlabels_property_percentformat_percentage);
    	if (m_ButtonPercentFormatPercentage != null) m_ButtonPercentFormatPercentage.setOnClickListener(this);
    	
		listPercentFormatPercentage = new ArrayList<String>();
		adapterlistViewPercentFormatListPercentage = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listPercentFormatPercentage);
		
		listViewPercentFormatListPercentage = (ListView)findViewById(R.id.listview_series_datapointlabels_property_percentformat_percentage);
		if (listViewPercentFormatListPercentage != null)
		{
			listViewPercentFormatListPercentage.setAdapter(adapterlistViewPercentFormatListPercentage);
			listViewPercentFormatListPercentage.setOnItemClickListener(m_ListViewitemClickListener);
			listViewPercentFormatListPercentage.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonPercentFormatFraction = (Button)findViewById(R.id.button_series_datapointlabels_property_percentformat_fraction);
    	if (m_ButtonPercentFormatFraction != null) m_ButtonPercentFormatFraction.setOnClickListener(this);
    	
		listPercentFormatFraction = new ArrayList<String>();
		adapterlistViewPercentFormatListFraction = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listPercentFormatFraction);
		
		listViewPercentFormatListFraction = (ListView)findViewById(R.id.listview_series_datapointlabels_property_percentformat_fraction);
		if (listViewPercentFormatListFraction != null)
		{
			listViewPercentFormatListFraction.setAdapter(adapterlistViewPercentFormatListFraction);
			listViewPercentFormatListFraction.setOnItemClickListener(m_ListViewitemClickListener);
			listViewPercentFormatListFraction.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
    	m_ButtonPercentFormatScientific = (Button)findViewById(R.id.button_series_datapointlabels_property_percentformat_scientific);
    	if (m_ButtonPercentFormatScientific != null) m_ButtonPercentFormatScientific.setOnClickListener(this);
    	
		listPercentFormatScientific = new ArrayList<String>();
		adapterlistViewPercentFormatListScientific = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, listPercentFormatScientific);
		
		listViewPercentFormatListScientific = (ListView)findViewById(R.id.listview_series_datapointlabels_property_percentformat_scientific);
		if (listViewPercentFormatListScientific != null)
		{
			listViewPercentFormatListScientific.setAdapter(adapterlistViewPercentFormatListScientific);
			listViewPercentFormatListScientific.setOnItemClickListener(m_ListViewitemClickListener);
			listViewPercentFormatListScientific.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
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
    		
    		int nId = parent.getId();
    		
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
		updateChart(nId);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		switch (checkedId)
		{
		case R.id.series_datapointlabels_property_apperance:
		case R.id.series_datapointlabels_property_fill:
		case R.id.series_datapointlabels_property_valueformat:
		case R.id.series_datapointlabels_property_percentformat:
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
	public void SetSelectedColumnRow(int nCol, int nRow)
	{
		m_nSelectedColumn = nCol;
		m_nSelectedRow = nRow;
		
		InitBackupData();
		updateBackupData(true);
		InitCtrl();
	}
	
	public void updateBackupData(boolean bUpdatData)
	{
		if (bUpdatData)
		{
			int nCol = 0;
			int nRow = 0;
			if (m_Chart.getSeriesColCount() > m_nSelectedColumn && 0 <= m_nSelectedColumn)
			{
				nCol = m_nSelectedColumn;
			}
			if (m_Chart.getSeriesRowCount() > m_nSelectedRow+1 && 0 <= m_nSelectedRow)
			{
				nRow = m_nSelectedRow;
			}
			
			m_DatapointLabelProperty.nTextLocation = m_Chart.getSeriesDataPointLabelLocation(nCol, nRow);
			m_DatapointLabelProperty.bUseValueLabel = m_Chart.getSeriesDatapointLabelTypeValue(nCol, nRow);
			m_DatapointLabelProperty.bUsePercentLabel = m_Chart.getSeriesDatapointLabelTypePercent(nCol, nRow);
			m_DatapointLabelProperty.bUseSeriesNameLabel = m_Chart.getSeriesDatapointLabelTypeSeriesName(nCol, nRow);
			m_DatapointLabelProperty.bUsePointNameLabel = m_Chart.getSeriesDatapointLabelTypePointName(nCol, nRow);
			
			m_DatapointFillProperty.nFillStyle = m_Chart.getSeriesDatapointFillStyle(nCol, nRow);
			m_DatapointFillProperty.nFillPattern = m_Chart.getSeriesDatapointFillPattern(nCol, nRow);
			m_DatapointFillProperty.nFillColor = m_Chart.getSeriesDatapointFillColor(nCol, nRow);
			m_DatapointFillProperty.nFillPatternColor = m_Chart.getSeriesDatapointFillPatternColor(nCol, nRow);
			m_DatapointFillProperty.nEdgeStyle = m_Chart.getSeriesDatapointEdgeStyle(nCol, nRow);
			m_DatapointFillProperty.nEdgeWidth = m_Chart.getSeriesDatapointEdgeWidth(nCol, nRow);
			m_DatapointFillProperty.nEdgeColor = m_Chart.getSeriesDatapointEdgeColor(nCol, nRow);

			initValueFormat(eFormat_NUMBER);
			initValueFormat(eFormat_CURRENCY);
			initValueFormat(eFormat_DATE);
			initValueFormat(eFormat_TIME);
			initValueFormat(eFormat_PERCENTAGE);
			initValueFormat(eFormat_FRACTION);
			initValueFormat(eFormat_SCIENTIFIC);
			initValueFormat(eFormat_GENERAL);
			
			initPercentFormat(eFormat_NUMBER);
			initPercentFormat(eFormat_CURRENCY);
			initPercentFormat(eFormat_DATE);
			initPercentFormat(eFormat_TIME);
			initPercentFormat(eFormat_PERCENTAGE);
			initPercentFormat(eFormat_FRACTION);
			initPercentFormat(eFormat_SCIENTIFIC);
			initPercentFormat(eFormat_GENERAL);
		}
		else
		{
		}
	}
	
	public void initValueFormat(int typeFormat)
	{
		String stdTorken = "_";
		String strFormatList = m_Chart.getSeriesDataPointFormatList(typeFormat);
		String[] stdFormat = strFormatList.split(stdTorken);
		
		switch (typeFormat)
		{
		case eFormat_NUMBER:
			for (int i=0; i<stdFormat.length; i++)
			{
				listValueFormatNumber.add(stdFormat[i]);
			}
			break;
			
		case eFormat_CURRENCY:
			for (int i=0; i<stdFormat.length; i++)
			{
				listValueFormatCurrency.add(stdFormat[i]);
			}
			break;
			
		case eFormat_DATE:
			for (int i=0; i<stdFormat.length; i++)
			{
				listValueFormatDate.add(stdFormat[i]);
			}
			break;
			
		case eFormat_TIME:
			for (int i=0; i<stdFormat.length; i++)
			{
				listValueFormatTime.add(stdFormat[i]);
			}
			break;
			
		case eFormat_PERCENTAGE:
			for (int i=0; i<stdFormat.length; i++)
			{
				listValueFormatPercentage.add(stdFormat[i]);
			}
			break;
			
		case eFormat_FRACTION:
			for (int i=0; i<stdFormat.length; i++)
			{
				listValueFormatFraction.add(stdFormat[i]);
			}
			break;
			
		case eFormat_SCIENTIFIC:
			for (int i=0; i<stdFormat.length; i++)
			{
				listValueFormatScientific.add(stdFormat[i]);
			}
			break;
			
		case eFormat_GENERAL:
			for (int i=0; i<stdFormat.length; i++)
			{
				listValueFormatGeneral.add(stdFormat[i]);
			}
			break;
		}
	}
	
	public void initPercentFormat(int typeFormat)
	{
		String stdTorken = "_";
		String strFormatList = m_Chart.getSeriesDataPointFormatList(typeFormat);
		String[] stdFormat = strFormatList.split(stdTorken);
		
		switch (typeFormat)
		{
		case eFormat_NUMBER:
			for (int i=0; i<stdFormat.length; i++)
			{
				listPercentFormatNumber.add(stdFormat[i]);
			}
			break;
			
		case eFormat_CURRENCY:
			for (int i=0; i<stdFormat.length; i++)
			{
				listPercentFormatCurrency.add(stdFormat[i]);
			}
			break;
			
		case eFormat_DATE:
			for (int i=0; i<stdFormat.length; i++)
			{
				listPercentFormatDate.add(stdFormat[i]);
			}
			break;
			
		case eFormat_TIME:
			for (int i=0; i<stdFormat.length; i++)
			{
				listPercentFormatTime.add(stdFormat[i]);
			}
			break;
			
		case eFormat_PERCENTAGE:
			for (int i=0; i<stdFormat.length; i++)
			{
				listPercentFormatPercentage.add(stdFormat[i]);
			}
			break;
			
		case eFormat_FRACTION:
			for (int i=0; i<stdFormat.length; i++)
			{
				listPercentFormatFraction.add(stdFormat[i]);
			}
			break;
			
		case eFormat_SCIENTIFIC:
			for (int i=0; i<stdFormat.length; i++)
			{
				listPercentFormatScientific.add(stdFormat[i]);
			}
			break;
			
		case eFormat_GENERAL:
			for (int i=0; i<stdFormat.length; i++)
			{
				listPercentFormatGeneral.add(stdFormat[i]);
			}
			break;
		}
	}
	
	public void InitCtrl()
	{
		String strTitle;
		if (m_Chart.getSeriesRowCount() <= m_nSelectedRow || 0 > m_nSelectedRow)
		{
			strTitle = m_pParent.getString(R.string.chart_property_series_datapoint_property) +
					"(" +
					m_pParent.getString(R.string.chart_property_series_datapointlabels_allrows) +
					")";
		}
		else
		{
			strTitle = m_pParent.getString(R.string.chart_property_series_datapoint_property) +
					"(" +
					m_Chart.getSeriesRowLabel(m_nSelectedRow) +
					")";
		}
		m_TextViewTitle.setText(strTitle);
		
		radioGroupDatapointLabelProperty.check(R.id.series_datapointlabels_property_apperance);
		showProperty(R.id.series_datapointlabels_property_apperance);
		
		updateDatapointLabelsApperanceControl(0, 0);
		updateDatapointFillControl(0, 0);
		updateDatapointLabelsValueFormatControl(0, 0);
		updateDatapointLabelsPercentFormatControl(0, 0);
		updateDatapointLabelsFontControl(0, 0);
	}
	
	public void InitBackupData()
	{
		m_DatapointLabelProperty.Init();
		m_DatapointFillProperty.Init();
		
		listValueFormatGeneral.clear();
		listValueFormatNumber.clear();
		listValueFormatCurrency.clear();
		listValueFormatDate.clear();
		listValueFormatTime.clear();
		listValueFormatPercentage.clear();
		listValueFormatFraction.clear();
		listValueFormatScientific.clear();
		
		listPercentFormatGeneral.clear();
		listPercentFormatNumber.clear();
		listPercentFormatCurrency.clear();
		listPercentFormatDate.clear();
		listPercentFormatTime.clear();
		listPercentFormatPercentage.clear();
		listPercentFormatFraction.clear();
		listPercentFormatScientific.clear();
	}
	
	protected void showProperty(int nId)
	{
		m_nActiveTabID = nId;
		
		RelativeLayout relativeLayoutApperance = (RelativeLayout)findViewById(R.id.relative_layout_series_datapointlabels_property_apperance);
		relativeLayoutApperance.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutFill = (RelativeLayout)findViewById(R.id.relative_layout_series_datapointlabels_property_fill);
		relativeLayoutFill.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutValueFormat = (RelativeLayout)findViewById(R.id.relative_layout_series_datapointlabels_property_valueformat);
		relativeLayoutValueFormat.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutPercentFormat = (RelativeLayout)findViewById(R.id.relative_layout_series_datapointlabels_property_percentformat);
		relativeLayoutPercentFormat.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutFont = (RelativeLayout)findViewById(R.id.relative_layout_series_datapointlabels_property_font);
		relativeLayoutFont.setVisibility(View.INVISIBLE);

		switch (nId)
		{
		case R.id.series_datapointlabels_property_apperance:
			relativeLayoutApperance.setVisibility(View.VISIBLE);
			break;
			
		case R.id.series_datapointlabels_property_fill:
			relativeLayoutFill.setVisibility(View.VISIBLE);
			break;
			
		case R.id.series_datapointlabels_property_valueformat:
			relativeLayoutValueFormat.setVisibility(View.VISIBLE);
			break;
			
		case R.id.series_datapointlabels_property_percentformat:
			relativeLayoutPercentFormat.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}
	
	public void updateChart(int nId)
	{
		switch (m_nActiveTabID)
		{
		case R.id.series_datapointlabels_property_apperance:
			updateDatapointLabelsApperance(nId);
			break;
			
		case R.id.series_datapointlabels_property_fill:
			updateDatapointLabelsFill(nId);
			break;
			
		case R.id.series_datapointlabels_property_valueformat:
			updateDatapointLabelsValueFormat(nId);
			break;
			
		case R.id.series_datapointlabels_property_percentformat:
			updateDatapointLabelsPercentFormat(nId);
			break;

		default:
			break;
		}
	}	
	public void updateDatapointLabelsApperance(int nId)
	{
		int nCountCol = m_Chart.getSeriesColCount();
		int nCountRow = m_Chart.getSeriesRowCount();
		int iCol = 0;
		int iRow = 0;
		if (m_Chart.getSeriesColCount() > m_nSelectedColumn && 0 <= m_nSelectedColumn)
		{
			iCol = m_nSelectedColumn;
			nCountCol = m_nSelectedColumn+1;
		}
		if (m_Chart.getSeriesRowCount() > m_nSelectedRow && 0 <= m_nSelectedRow)
		{
			iRow = m_nSelectedRow;
			nCountRow = m_nSelectedRow+1;
		}
		
		switch (nId)
		{
		case R.id.series_datapointlabels_property_apperance_textloaction_none:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDataPointLabelLocation(col, row, ESChLabelLocationTypeNone);
				}
			}
			updateControl(R.id.radio_series_datapointlabels_property_apperance_textloaction, 0);
			break;
		
		case R.id.series_datapointlabels_property_apperance_textloaction_above:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDataPointLabelLocation(col, row, ESChLabelLocationTypeAbovePoint);
				}
			}
			updateControl(R.id.radio_series_datapointlabels_property_apperance_textloaction, 1);
			break;

		case R.id.series_datapointlabels_property_apperance_textloaction_below:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDataPointLabelLocation(col, row, ESChLabelLocationTypeBelowPoint);
				}
			}
			updateControl(R.id.radio_series_datapointlabels_property_apperance_textloaction, 2);
			break;

		case R.id.series_datapointlabels_property_apperance_textloaction_center:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDataPointLabelLocation(col, row, ESChLabelLocationTypeCenter);
				}
			}
			updateControl(R.id.radio_series_datapointlabels_property_apperance_textloaction, 3);
			break;

		case R.id.series_datapointlabels_property_apperance_textloaction_base:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDataPointLabelLocation(col, row, ESChLabelLocationTypeBase);
				}
			}
			updateControl(R.id.radio_series_datapointlabels_property_apperance_textloaction, 4);
			break;

		case R.id.checkbox_series_datapointlabels_property_apperance_label_value:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointLabelTypeValue(col, row, m_CheckBoxApperanceLabelValue.isChecked());
				}
			}
			break;
		case R.id.switch_series_datapointlabels_property_apperance_label_value:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointLabelTypeValue(col, row, m_SwitchApperanceLabelValue.isChecked());
				}
			}
			break;
			
		case R.id.checkbox_series_datapointlabels_property_apperance_label_percent:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointLabelTypePercent(col, row, m_CheckBoxApperanceLabelPercent.isChecked());
				}
			}
		case R.id.switch_series_datapointlabels_property_apperance_label_percent:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointLabelTypePercent(col, row, m_SwitchApperanceLabelPercent.isChecked());
				}
			}
			break;
			
		case R.id.checkbox_series_datapointlabels_property_apperance_label_seriesname:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointLabelTypeSeriesName(col, row, m_CheckBoxApperanceLabelSeries.isChecked());
				}
			}
		case R.id.switch_series_datapointlabels_property_apperance_label_seriesname:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointLabelTypeSeriesName(col, row, m_SwitchApperanceLabelSeries.isChecked());
				}
			}
			break;
			
		case R.id.checkbox_series_datapointlabels_property_apperance_label_pointname:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointLabelTypePointName(col, row, m_CheckBoxApperanceLabelPoint.isChecked());
				}
			}
		case R.id.switch_series_datapointlabels_property_apperance_label_pointname:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointLabelTypePointName(col, row, m_SwitchApperanceLabelPoint.isChecked());
				}
			}
			break;
			
		default:
			break;
		}
	}
	public void updateDatapointLabelsFill(int nId)
	{
		int nCountCol = m_Chart.getSeriesColCount();
		int nCountRow = m_Chart.getSeriesRowCount();
		int iCol = 0;
		int iRow = 0;
		if (m_Chart.getSeriesColCount() > m_nSelectedColumn && 0 <= m_nSelectedColumn)
		{
			iCol = m_nSelectedColumn;
			nCountCol = m_nSelectedColumn+1;
		}
		if (m_Chart.getSeriesRowCount() > m_nSelectedRow && 0 <= m_nSelectedRow)
		{
			iRow = m_nSelectedRow;
			nCountRow = m_nSelectedRow+1;
		}
		
		switch (nId)
		{
		case R.id.button_series_datapointlabels_property_fill_fillcolor_preview:
			m_ColorPalette.show();
			m_ColorPalette.SetCurrentColumnRow(m_nSelectedColumn, m_nSelectedRow, nId);
			break;
			
		case R.id.button_series_datapointlabels_property_fill_edgecolor_preview:
			m_ColorPalette.show();
			m_ColorPalette.SetCurrentColumnRow(m_nSelectedColumn, m_nSelectedRow, nId);
			break;
			
		case R.id.series_datapointlabels_property_fill_fillpattern_none:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointFillPattern(col, row, ESBrushStyleNull, 0);
				}
			}
			updateControl(R.id.radio_series_datapointlabels_property_fill_fillpattern, ESBrushStyleNull);
			break;

		case R.id.series_datapointlabels_property_fill_fillpattern_solid:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointFillPattern(col, row, ESBrushStyleSolid, 0);
				}
			}
			updateControl(R.id.radio_series_datapointlabels_property_fill_fillpattern, ESBrushStyleSolid);
			break;
			
		case R.id.series_datapointlabels_property_fill_edgestyle_none:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointEdgeStyle(col, row, ESPenStyleNull);
				}
			}
			updateControl(R.id.radio_series_datapointlabels_property_fill_edgestyle, ESPenStyleNull);
			break;
		case R.id.series_datapointlabels_property_fill_edgestyle_solid:
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointEdgeStyle(col, row, ESPenStyleSolid);
				}
			}
			updateControl(R.id.radio_series_datapointlabels_property_fill_edgestyle, ESPenStyleSolid);
			break;
			
			default:
				break;
		}
	}
	public void updateDatapointLabelsValueFormat(int nId)
	{
		switch (nId)
		{
		case R.id.button_series_datapointlabels_property_valueformat:
		{
			int nCountCol = m_Chart.getSeriesColCount();
			int nCountRow = m_Chart.getSeriesRowCount();
			int iCol = 0;
			int iRow = 0;
			if (m_Chart.getSeriesColCount() > m_nSelectedColumn && 0 <= m_nSelectedColumn)
			{
				iCol = m_nSelectedColumn;
				nCountCol = m_nSelectedColumn+1;
			}
			if (m_Chart.getSeriesRowCount() > m_nSelectedRow && 0 <= m_nSelectedRow)
			{
				iRow = m_nSelectedRow;
				nCountRow = m_nSelectedRow+1;
			}
			
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					String strFormat = m_EditValueFormat.getText().toString();
					m_Chart.setSeriesDataPointLabelValueFormat(col, row, strFormat);
				}
			}
		}
			break;
		
		case R.id.button_series_datapointlabels_property_valueformat_general:
		{
			ViewGroup.LayoutParams paramListView = listViewValueFormatListGeneral.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reduceValueFormatListView();
			if (doExpand)
			{
				paramListView.height = listValueFormatGeneral.size() * HEIGHT_LISTVIEW_ITEM;
				listViewValueFormatListGeneral.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_valueformat_number:
		{
			ViewGroup.LayoutParams paramListView = listViewValueFormatListNumber.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}

			reduceValueFormatListView();
			if (doExpand)
			{
				paramListView.height = listValueFormatNumber.size() * HEIGHT_LISTVIEW_ITEM;
				listViewValueFormatListNumber.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_valueformat_currency:
		{
			ViewGroup.LayoutParams paramListView = listViewValueFormatListCurrency.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reduceValueFormatListView();
			if (doExpand)
			{
				paramListView.height = listValueFormatCurrency.size() * HEIGHT_LISTVIEW_ITEM;
				listViewValueFormatListCurrency.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_valueformat_date:
		{
			ViewGroup.LayoutParams paramListView = listViewValueFormatListDate.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reduceValueFormatListView();
			if (doExpand)
			{
				paramListView.height = listValueFormatDate.size() * HEIGHT_LISTVIEW_ITEM;
				listViewValueFormatListDate.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_valueformat_time:
		{
			ViewGroup.LayoutParams paramListView = listViewValueFormatListTime.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reduceValueFormatListView();
			if (doExpand)
			{
				paramListView.height = listValueFormatTime.size() * HEIGHT_LISTVIEW_ITEM;
				listViewValueFormatListTime.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_valueformat_percentage:
		{
			ViewGroup.LayoutParams paramListView = listViewValueFormatListPercentage.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reduceValueFormatListView();
			if (doExpand)
			{
				paramListView.height = listValueFormatPercentage.size() * HEIGHT_LISTVIEW_ITEM;
				listViewValueFormatListPercentage.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_valueformat_fraction:
		{
			ViewGroup.LayoutParams paramListView = listViewValueFormatListFraction.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reduceValueFormatListView();
			if (doExpand)
			{
				paramListView.height = listValueFormatFraction.size() * HEIGHT_LISTVIEW_ITEM;
				listViewValueFormatListFraction.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_valueformat_scientific:
		{
			ViewGroup.LayoutParams paramListView = listViewValueFormatListScientific.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reduceValueFormatListView();
			if (doExpand)
			{
				paramListView.height = listValueFormatScientific.size() * HEIGHT_LISTVIEW_ITEM;
				listViewValueFormatListScientific.setLayoutParams(paramListView);
			}
		}
			break;

		default:
			break;
		}
	}
	public void updateDatapointLabelsPercentFormat(int nId)
	{
		switch (nId)
		{
		case R.id.button_series_datapointlabels_property_percentformat:
		{
			int nCountCol = m_Chart.getSeriesColCount();
			int nCountRow = m_Chart.getSeriesRowCount();
			int iCol = 0;
			int iRow = 0;
			if (m_Chart.getSeriesColCount() > m_nSelectedColumn && 0 <= m_nSelectedColumn)
			{
				iCol = m_nSelectedColumn;
				nCountCol = m_nSelectedColumn+1;
			}
			if (m_Chart.getSeriesRowCount() > m_nSelectedRow && 0 <= m_nSelectedRow)
			{
				iRow = m_nSelectedRow;
				nCountRow = m_nSelectedRow+1;
			}
			
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					String strFormat = m_EditPercentFormat.getText().toString();
					m_Chart.setSeriesDataPointLabelPercentFormat(col, row, strFormat);
				}
			}
		}
			break;
		
		case R.id.button_series_datapointlabels_property_percentformat_general:
		{
			ViewGroup.LayoutParams paramListView = listViewPercentFormatListGeneral.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reducePercentFormatListView();
			if (doExpand)
			{
				paramListView.height = listPercentFormatGeneral.size() * HEIGHT_LISTVIEW_ITEM;
				listViewPercentFormatListGeneral.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_percentformat_number:
		{
			ViewGroup.LayoutParams paramListView = listViewPercentFormatListNumber.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}

			reducePercentFormatListView();
			if (doExpand)
			{
				paramListView.height = listPercentFormatNumber.size() * HEIGHT_LISTVIEW_ITEM;
				listViewPercentFormatListNumber.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_percentformat_currency:
		{
			ViewGroup.LayoutParams paramListView = listViewPercentFormatListCurrency.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reducePercentFormatListView();
			if (doExpand)
			{
				paramListView.height = listPercentFormatCurrency.size() * HEIGHT_LISTVIEW_ITEM;
				listViewPercentFormatListCurrency.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_percentformat_date:
		{
			ViewGroup.LayoutParams paramListView = listViewPercentFormatListDate.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reducePercentFormatListView();
			if (doExpand)
			{
				paramListView.height = listPercentFormatDate.size() * HEIGHT_LISTVIEW_ITEM;
				listViewPercentFormatListDate.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_percentformat_time:
		{
			ViewGroup.LayoutParams paramListView = listViewPercentFormatListTime.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reducePercentFormatListView();
			if (doExpand)
			{
				paramListView.height = listPercentFormatTime.size() * HEIGHT_LISTVIEW_ITEM;
				listViewPercentFormatListTime.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_percentformat_percentage:
		{
			ViewGroup.LayoutParams paramListView = listViewPercentFormatListPercentage.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reducePercentFormatListView();
			if (doExpand)
			{
				paramListView.height = listPercentFormatPercentage.size() * HEIGHT_LISTVIEW_ITEM;
				listViewPercentFormatListPercentage.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_percentformat_fraction:
		{
			ViewGroup.LayoutParams paramListView = listViewPercentFormatListFraction.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reducePercentFormatListView();
			if (doExpand)
			{
				paramListView.height = listPercentFormatFraction.size() * HEIGHT_LISTVIEW_ITEM;
				listViewPercentFormatListFraction.setLayoutParams(paramListView);
			}
		}
			break;

		case R.id.button_series_datapointlabels_property_percentformat_scientific:
		{
			ViewGroup.LayoutParams paramListView = listViewPercentFormatListScientific.getLayoutParams();
			boolean doExpand = false; 
			if (0 >= paramListView.height)
			{
				doExpand = true;
			}
			
			reducePercentFormatListView();
			if (doExpand)
			{
				paramListView.height = listPercentFormatScientific.size() * HEIGHT_LISTVIEW_ITEM;
				listViewPercentFormatListScientific.setLayoutParams(paramListView);
			}
		}
			break;

		default:
			break;
		}
	}
	public void updateDatapointLabelsFont(int nId)
	{
		switch (nId)
		{

		default:
			break;
		}
	}
	
	public void reduceValueFormatListView()
	{
		ViewGroup.LayoutParams paramListViewFormatGeneral = listViewValueFormatListGeneral.getLayoutParams();
		paramListViewFormatGeneral.height = 0;
		listViewValueFormatListGeneral.setLayoutParams(paramListViewFormatGeneral);
		
		ViewGroup.LayoutParams paramListViewFormatNumber = listViewValueFormatListNumber.getLayoutParams();
		paramListViewFormatNumber.height = 0;
		listViewValueFormatListNumber.setLayoutParams(paramListViewFormatNumber);

		ViewGroup.LayoutParams paramListViewFormatCurrency = listViewValueFormatListCurrency.getLayoutParams();
		paramListViewFormatCurrency.height = 0;
		listViewValueFormatListCurrency.setLayoutParams(paramListViewFormatCurrency);
		
		ViewGroup.LayoutParams paramListViewFormatDate = listViewValueFormatListDate.getLayoutParams();
		paramListViewFormatDate.height = 0;
		listViewValueFormatListDate.setLayoutParams(paramListViewFormatDate);

		ViewGroup.LayoutParams paramListViewFormatTime = listViewValueFormatListTime.getLayoutParams();
		paramListViewFormatTime.height = 0;
		listViewValueFormatListTime.setLayoutParams(paramListViewFormatTime);

		ViewGroup.LayoutParams paramListViewFormatPercentage = listViewValueFormatListPercentage.getLayoutParams();
		paramListViewFormatPercentage.height = 0;
		listViewValueFormatListPercentage.setLayoutParams(paramListViewFormatPercentage);

		ViewGroup.LayoutParams paramListViewFormatFraction = listViewValueFormatListFraction.getLayoutParams();
		paramListViewFormatFraction.height = 0;
		listViewValueFormatListFraction.setLayoutParams(paramListViewFormatFraction);

		ViewGroup.LayoutParams paramListViewFormatScientific = listViewValueFormatListScientific.getLayoutParams();
		paramListViewFormatScientific.height = 0;
		listViewValueFormatListScientific.setLayoutParams(paramListViewFormatScientific);
	}
	public void reducePercentFormatListView()
	{
		ViewGroup.LayoutParams paramListViewFormatGeneral = listViewPercentFormatListGeneral.getLayoutParams();
		paramListViewFormatGeneral.height = 0;
		listViewPercentFormatListGeneral.setLayoutParams(paramListViewFormatGeneral);
		
		ViewGroup.LayoutParams paramListViewFormatNumber = listViewPercentFormatListNumber.getLayoutParams();
		paramListViewFormatNumber.height = 0;
		listViewPercentFormatListNumber.setLayoutParams(paramListViewFormatNumber);

		ViewGroup.LayoutParams paramListViewFormatCurrency = listViewPercentFormatListCurrency.getLayoutParams();
		paramListViewFormatCurrency.height = 0;
		listViewPercentFormatListCurrency.setLayoutParams(paramListViewFormatCurrency);
		
		ViewGroup.LayoutParams paramListViewFormatDate = listViewPercentFormatListDate.getLayoutParams();
		paramListViewFormatDate.height = 0;
		listViewPercentFormatListDate.setLayoutParams(paramListViewFormatDate);

		ViewGroup.LayoutParams paramListViewFormatTime = listViewPercentFormatListTime.getLayoutParams();
		paramListViewFormatTime.height = 0;
		listViewPercentFormatListTime.setLayoutParams(paramListViewFormatTime);

		ViewGroup.LayoutParams paramListViewFormatPercentage = listViewPercentFormatListPercentage.getLayoutParams();
		paramListViewFormatPercentage.height = 0;
		listViewPercentFormatListPercentage.setLayoutParams(paramListViewFormatPercentage);

		ViewGroup.LayoutParams paramListViewFormatFraction = listViewPercentFormatListFraction.getLayoutParams();
		paramListViewFormatFraction.height = 0;
		listViewPercentFormatListFraction.setLayoutParams(paramListViewFormatFraction);

		ViewGroup.LayoutParams paramListViewFormatScientific = listViewPercentFormatListScientific.getLayoutParams();
		paramListViewFormatScientific.height = 0;
		listViewPercentFormatListScientific.setLayoutParams(paramListViewFormatScientific);
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
		case R.id.series_datapointlabels_property_apperance:
			updateDatapointLabelsApperanceControl(nId, nValue);
			break;
			
		case R.id.series_datapointlabels_property_fill:
			updateDatapointFillControl(nId, nValue);
			break;
			
		case R.id.series_datapointlabels_property_valueformat:
			updateDatapointLabelsValueFormatControl(nId, nValue);
			break;
			
		case R.id.series_datapointlabels_property_percentformat:
			updateDatapointLabelsPercentFormatControl(nId, nValue);
			break;

		default:
			break;
		}
	}
	public void updateDatapointLabelsApperanceControl(int nId, int nValue)
	{
		if (0 < nId)
		{
			switch (nId)
			{
			case R.id.radio_series_datapointlabels_property_apperance_textloaction:
				if (radioGroupApperanceTextLoaction != null)
					radioGroupApperanceTextLoaction.setEnabled(nValue>0?true:false);

				if (radioGroupApperanceLineStyle != null)
					radioGroupApperanceLineStyle.setEnabled(nValue>0?true:false);

				if (m_CheckBoxApperanceLabelValue != null)
					m_CheckBoxApperanceLabelValue.setEnabled(nValue>0?true:false);

				if (m_CheckBoxApperanceLabelPercent != null)
					m_CheckBoxApperanceLabelPercent.setEnabled(nValue>0?true:false);

				if (m_CheckBoxApperanceLabelSeries != null)
					m_CheckBoxApperanceLabelSeries.setEnabled(nValue>0?true:false);

				if (m_CheckBoxApperanceLabelPoint != null)
					m_CheckBoxApperanceLabelPoint.setEnabled(nValue>0?true:false);

				if (m_SwitchApperanceLabelValue != null)
					m_SwitchApperanceLabelValue.setEnabled(nValue>0?true:false);

				if (m_SwitchApperanceLabelPercent != null)
					m_SwitchApperanceLabelPercent.setEnabled(nValue>0?true:false);

				if (m_SwitchApperanceLabelSeries != null)
					m_SwitchApperanceLabelSeries.setEnabled(nValue>0?true:false);

				if (m_SwitchApperanceLabelPoint != null)
					m_SwitchApperanceLabelPoint.setEnabled(nValue>0?true:false);
				break;
			}
		}
		else
		{
			if (radioGroupApperanceTextLoaction != null)
				radioGroupApperanceTextLoaction.check(getCtrlIdDatapointLabelsApperance(m_DatapointLabelProperty.nTextLocation));

			if (radioGroupApperanceLineStyle != null)
				radioGroupApperanceLineStyle.check(getCtrlIdDatapointLabelsApperance(m_DatapointLabelProperty.nTextLocation));

			if (m_CheckBoxApperanceLabelValue != null)
				m_CheckBoxApperanceLabelValue.setChecked(m_DatapointLabelProperty.bUseValueLabel);

			if (m_CheckBoxApperanceLabelPercent != null)
				m_CheckBoxApperanceLabelPercent.setChecked(m_DatapointLabelProperty.bUsePercentLabel);

			if (m_CheckBoxApperanceLabelSeries != null)
				m_CheckBoxApperanceLabelSeries.setChecked(m_DatapointLabelProperty.bUseSeriesNameLabel);

			if (m_CheckBoxApperanceLabelPoint != null)
				m_CheckBoxApperanceLabelPoint.setChecked(m_DatapointLabelProperty.bUsePointNameLabel);

			if (m_SwitchApperanceLabelValue != null)
				m_SwitchApperanceLabelValue.setChecked(m_DatapointLabelProperty.bUseValueLabel);

			if (m_SwitchApperanceLabelPercent != null)
				m_SwitchApperanceLabelPercent.setChecked(m_DatapointLabelProperty.bUsePercentLabel);

			if (m_SwitchApperanceLabelSeries != null)
				m_SwitchApperanceLabelSeries.setChecked(m_DatapointLabelProperty.bUseSeriesNameLabel);

			if (m_SwitchApperanceLabelPoint != null)
				m_SwitchApperanceLabelPoint.setChecked(m_DatapointLabelProperty.bUsePointNameLabel);
		}
	}
	public void updateDatapointFillControl(int nId, int nValue)
	{
		if (0 < nId)
		{
			switch (nId)
			{
			case R.id.radio_series_datapointlabels_property_fill_edgestyle:
				m_ButtonFillEdgeColorPreview.setEnabled(nValue>0?true:false);
				break;
				
			case R.id.radio_series_datapointlabels_property_fill_fillpattern:
				if (ESBrushStyleNull == nValue)
				{
					m_ButtonFillFillColorPreview.setEnabled(false);
				}
				else if (ESBrushStyleSolid == nValue)
				{
					m_ButtonFillFillColorPreview.setEnabled(true);
				}
				else
				{
					m_ButtonFillFillColorPreview.setEnabled(true);
				}
				break;

			default:
				break;
			}
		}
		else
		{
			radioGroupFillFillPattern.check( getCtrlIdDatapointFillFillPattern(m_DatapointFillProperty.nFillStyle, m_DatapointFillProperty.nFillPattern) );
			m_ButtonFillFillColorPreview.setBackgroundColor(m_DatapointFillProperty.getFillColor());
			
			radioGroupFillEdgeStyle.check( getCtrlIdDatapointFillEdgeStyle(m_DatapointFillProperty.nEdgeStyle) );
			m_ButtonFillEdgeColorPreview.setBackgroundColor(m_DatapointFillProperty.getFillEdgeColor());
		}
	}
	public void updateDatapointLabelsValueFormatControl(int nId, int nValue)
	{
		if (0 < nId)
		{
			listViewValueFormatListGeneral.setItemChecked(-1, true);
			listViewValueFormatListNumber.setItemChecked(-1, true);
			listViewValueFormatListCurrency.setItemChecked(-1, true);
			listViewValueFormatListDate.setItemChecked(-1, true);
			listViewValueFormatListTime.setItemChecked(-1, true);
			listViewValueFormatListPercentage.setItemChecked(-1, true);
			listViewValueFormatListFraction.setItemChecked(-1, true);
			listViewValueFormatListScientific.setItemChecked(-1, true);

			ListView listViewFormatList = (ListView)findViewById(nId);
			listViewFormatList.setItemChecked(nValue, true);

			Object obj = listViewFormatList.getItemAtPosition(nValue);
			if (null != obj)
			{
				String str = obj.toString();
				m_EditValueFormat.setText(str);
			}
		}
		else
		{
		}
	}
	public void updateDatapointLabelsPercentFormatControl(int nId, int nValue)
	{
		if (0 < nId)
		{
			listViewPercentFormatListGeneral.setItemChecked(-1, true);
			listViewPercentFormatListNumber.setItemChecked(-1, true);
			listViewPercentFormatListCurrency.setItemChecked(-1, true);
			listViewPercentFormatListDate.setItemChecked(-1, true);
			listViewPercentFormatListTime.setItemChecked(-1, true);
			listViewPercentFormatListPercentage.setItemChecked(-1, true);
			listViewPercentFormatListFraction.setItemChecked(-1, true);
			listViewPercentFormatListScientific.setItemChecked(-1, true);

			ListView listViewFormatList = (ListView)findViewById(nId);
			listViewFormatList.setItemChecked(nValue, true);

			Object obj = listViewFormatList.getItemAtPosition(nValue);
			if (null != obj)
			{
				String str = obj.toString();
				m_EditPercentFormat.setText(str);
			}
		}
		else
		{
		}
	}
	public void updateDatapointLabelsFontControl(int nId, int nValue)
	{
	}

	public int getCtrlIdDatapointLabelsApperance(int nType)
	{
		int nId = 0;
		if (ESChLabelLocationTypeNone == nType) nId = R.id.series_datapointlabels_property_apperance_textloaction_none;
		else if (ESChLabelLocationTypeAbovePoint == nType) nId = R.id.series_datapointlabels_property_apperance_textloaction_above;
		else if (ESChLabelLocationTypeBelowPoint == nType) nId = R.id.series_datapointlabels_property_apperance_textloaction_below;
		else if (ESChLabelLocationTypeCenter == nType) nId = R.id.series_datapointlabels_property_apperance_textloaction_center;
		else if (ESChLabelLocationTypeBase == nType) nId = R.id.series_datapointlabels_property_apperance_textloaction_base;

		return nId;
	}
	public int getCtrlIdDatapointFillFillPattern(int nValue, int nValue2)
	{
		int nId = 0;
		if (ESBrushStyleNull == nValue) nId = R.id.series_datapointlabels_property_fill_fillpattern_none;
		else if (ESBrushStyleSolid == nValue) nId = R.id.series_datapointlabels_property_fill_fillpattern_solid;
		
		return nId;
	}
	public int getCtrlIdDatapointFillEdgeStyle(int nValue)
	{
		int nId = 0;
		if (ESPenStyleNull == nValue) nId = R.id.series_datapointlabels_property_fill_edgestyle_none;
		else if (ESPenStyleSolid == nValue) nId = R.id.series_datapointlabels_property_fill_edgestyle_solid;

		return nId;
	}
	public int getCtrlIdDatapointFillEdgeWidth(int nValue)
	{
		int nId = 0;

		return nId;
	}
	public int getCtrlIdDatapointLabelsValueFormat(int nType)
	{
		int nId = 0;

		return nId;
	}
	public int getCtrlIdDatapointLabelsPercentFormat(int nType)
	{
		int nId = 0;

		return nId;
	}
	public int getCtrlIdDatapointLabelsFont(int nType)
	{
		int nId = 0;

		return nId;
	}

}
