package com.essence.chart_sdk;

import com.essence.chart.Chart;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.TextView;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;

public class ChartProperty extends Dialog implements android.view.View.OnClickListener, OnCheckedChangeListener {

	public final int ESBrushStyleNull = 0;
	public final int ESBrushStyleSolid = 1;
	public final int ESBrushStylePattern = 2;
	public final int ESBrushStyleHatched = 3;
	
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
	
	public final int LOCATION_TOPLEFT = 0;
	public final int LOCATION_TOP = 1;
	public final int LOCATION_TOPRIGHT = 2;
	public final int LOCATION_LEFT = 3;
	public final int LOCATION_RIGHT = 4;
	public final int LOCATION_BOTTOMLEFT = 5;
	public final int LOCATION_BOTTOM = 6;
	public final int LOCATION_BOTTOMRIGHT = 7;
	public final int LOCATION_CUSTOM = 8;
	
	
	public final int FONT_STYLE_REGULAR = 0x00;
	public final int FONT_STYLE_BOLD = 0x01;
	public final int FONT_STYLE_ITALIC = 0x02;
	
	public final int ESFrameStyleNull = 0;
	public final int ESFrameStyleSingleLine = 1;
	public final int ESFrameStyleDoubleLine = 2;
	public final int ESFrameStyleThickInner = 3;
	public final int ESFrameStyleThickOuter = 4;
	
	public final int ESPictureLocationTypeCenter = 0;
	public final int ESPictureLocationTypeFit = 1;
	public final int ESPictureLocationTypeStretch = 2;
	public final int ESPictureLocationTypeTile = 3;
	public final int ESPictureLocationTypeFill = 4;
	
	public class ChartPropertyStringsetData
	{
		public ChartPropertyStringsetData()
		{
			Init();
		}
		
		public void Init()
		{
			m_strStringTitle = "";
		}

		public String m_strStringTitle;
	}
	private ChartPropertyStringsetData m_StringsetBackup = null;
	
	public class ChartPropertyLocationData
	{
		public ChartPropertyLocationData()
		{
			Init();
		}
		
		public void Init()
		{
			m_nLocation = -1;
		}

		public int m_nLocation;
	}
	private ChartPropertyLocationData m_LocationBackup = null;
	
	public class ChartPropertyFontData
	{
		public ChartPropertyFontData()
		{
			Init();
		}
		
		public void Init()
		{
			m_strName = "";
			m_nSize = -1;
			m_nStyle = -1;
			m_nColor = -1;
			m_bStrikeOut = false;
			m_bUnderLine = false;
		}
		
		public int getColor()
		{
			int nColor = 0xFF000000;
			int rgbRed = m_nColor & 0x0000ff;
			int rgbGreen = m_nColor & 0x00ff00;
			int rgbBlue = m_nColor & 0xff0000;
			
			nColor = nColor + rgbRed + rgbGreen + rgbBlue; 
			
			return nColor;
		}

		public String m_strName;
		public int m_nSize;
		public int m_nStyle;
		public int m_nColor;
		public boolean m_bStrikeOut;
		public boolean m_bUnderLine;
	}
	private ChartPropertyFontData m_FontBackup = null;
	
	public class ChartPropertyBackdropFrameData
	{
		public ChartPropertyBackdropFrameData()
		{
			Init();
		}
		
		public void Init()
		{
			m_nStyle = -1;
			m_nColor = -1;
		}
		
		public int getColor()
		{
			int nColor = 0xFF000000;
			int rgbRed = m_nColor & 0x0000ff;
			int rgbGreen = m_nColor & 0x00ff00;
			int rgbBlue = m_nColor & 0xff0000;
			
			nColor = nColor + rgbRed + rgbGreen + rgbBlue; 
			
			return nColor;
			
		}

		public int m_nStyle;
		public int m_nColor;
	}
	private ChartPropertyBackdropFrameData m_BackdropFrameBackup = null;
	private ChartPropertyBackdropFrameData m_BackdropFillBackup = null;
	
	public class ChartPropertyPictureData
	{
		public ChartPropertyPictureData()
		{
			Init();
		}
		
		public void Init()
		{
			m_strFile = "";
			m_nStyle = -1;
		}
		
		public String m_strFile;
		public int m_nStyle;
	}
	private ChartPropertyPictureData m_pictureBackup = null;
	
	
	private Chart m_Chart = null;
	
	private TextView m_TextViewMainTitle = null;
	
	private Button m_ButtonApply = null;
	private Button m_ButtonCancel = null;

	private RadioGroup radioGroupChartTitleProperty = null;
	private RadioGroup radioGroupChartLegendProperty = null;
	private RadioGroup radioGroupChartBackdropProperty = null;
	private RadioGroup radioGroupChartPlotBackdropProperty = null;
	private RadioGroup radioGroupChartXAxisProperty = null;
	private RadioGroup radioGroupChartYAxisProperty = null;
	private RadioGroup radioGroupChartY2AxisProperty = null;
	private RadioGroup radioGroupChartZAxisProperty = null;
	
	private RadioGroup radioGroupChartPropertyLocation = null;
	private RadioGroup radioGroupChartPropertyLocation2 = null;
	
	private RadioGroup radioGroupChartPropertyFontStyle = null;

	private RadioGroup radioGroupChartPropertyFontSize = null;

	private CheckBox	m_CheckBoxStrikeOut = null;
	private	Switch		m_SwitchStrikeOut = null;

	private CheckBox	m_CheckBoxUnderLine = null;
	private	Switch		m_SwitchUnderLine = null;

	private Button m_ButtonFontColorPreview = null;
	
	private EditText m_EditTextTitle = null;
	private Button m_ButtonTitleApply = null;
	
	private RadioGroup radioGroupChartPropertyBackdropStyle = null;

	private Button m_ButtonBackdropFrameColorPreview = null;

	private RadioGroup radioGroupChartPropertyPlotBackdropStyle = null;

	private Button m_ButtonPlotBackdropFrameColorPreview = null;
	
	private EditText m_EditTextPictureFile = null;
	private Button m_ButtonPictureFile = null;
	private Button m_ButtonPictureClear = null;
	private RadioGroup radioGroupChartPropertyPictureDrawStyle = null;
	
	private RadioGroup radioGroupFillType1FillPattern = null;
	private RadioGroup radioGroupFillType1EdgePattern = null;
	private Button m_ButtonFillType1FillColorPreview = null;
	private Button m_ButtonFillType1EdgeColorPreview = null;
	
	private RadioGroup radioGroupFillType2FillPattern = null;
	private Button m_ButtonFillType2FillColorPreview = null;
	
	private ColorPalette m_ColorPalette = null;
	
	private  CFileOpenDialog m_FileOpenDialog = null;
	private  DialogInterface m_FileOpenDialogInterface = null;
	
	private ChartCheckBoxListener m_ChartCheckBoxListener = null;
	
	private int m_nPropertyType;
	
	Context m_pParent = null;
	
	
	public ChartProperty(Context context, Chart esChart) {
		super(context);
		
		m_Chart = esChart;
		m_pParent = context;
	}

	public ChartProperty(Context context, int theme, Chart esChart) {
		super(context, theme);
		
		m_Chart = esChart;
		m_pParent = context;
	}

	public ChartProperty(Context context, boolean cancelable,
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
        setContentView(R.layout.chart_property_dialog);
        
        
        this.getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
        
        setTitle(m_pParent.getString(R.string.chart_property));
        
        m_ChartCheckBoxListener = new ChartCheckBoxListener(m_ChartCheckBoxListenerCallback);

        m_TextViewMainTitle = (TextView)findViewById(R.id.text_main_title);
        
        m_ButtonApply = (Button)findViewById(R.id.button_chart_property_apply);
        if (m_ButtonApply != null) m_ButtonApply.setOnClickListener(this);
        m_ButtonApply.setVisibility(View.INVISIBLE);
        	
        m_ButtonCancel = (Button)findViewById(R.id.button_chart_property_cancel);
        if (m_ButtonCancel != null) m_ButtonCancel.setOnClickListener(this);
        m_ButtonCancel.setVisibility(View.INVISIBLE);

        radioGroupChartTitleProperty = (RadioGroup)findViewById(R.id.radio_chart_title_property);
		if (radioGroupChartTitleProperty != null) radioGroupChartTitleProperty.setOnCheckedChangeListener(this);

		radioGroupChartLegendProperty = (RadioGroup)findViewById(R.id.radio_chart_legend_property);
		if (radioGroupChartLegendProperty != null) radioGroupChartLegendProperty.setOnCheckedChangeListener(this);
		
		radioGroupChartBackdropProperty = (RadioGroup)findViewById(R.id.radio_chart_backdrop_property);
		if (radioGroupChartBackdropProperty != null) radioGroupChartBackdropProperty.setOnCheckedChangeListener(this);

		radioGroupChartPlotBackdropProperty = (RadioGroup)findViewById(R.id.radio_chart_plotbackdrop_property);
		if (radioGroupChartPlotBackdropProperty != null) radioGroupChartPlotBackdropProperty.setOnCheckedChangeListener(this);

		radioGroupChartXAxisProperty = (RadioGroup)findViewById(R.id.radio_chart_xaxis_property);
		if (radioGroupChartXAxisProperty != null) radioGroupChartXAxisProperty.setOnCheckedChangeListener(this);

		radioGroupChartYAxisProperty = (RadioGroup)findViewById(R.id.radio_chart_yaxis_property);
		if (radioGroupChartYAxisProperty != null) radioGroupChartYAxisProperty.setOnCheckedChangeListener(this);
		
		radioGroupChartY2AxisProperty = (RadioGroup)findViewById(R.id.radio_chart_y2axis_property);
		if (radioGroupChartY2AxisProperty != null) radioGroupChartY2AxisProperty.setOnCheckedChangeListener(this);

		radioGroupChartZAxisProperty = (RadioGroup)findViewById(R.id.radio_chart_zaxis_property);
		if (radioGroupChartZAxisProperty != null) radioGroupChartZAxisProperty.setOnCheckedChangeListener(this);

		radioGroupChartPropertyLocation =(RadioGroup)findViewById(R.id.radio_chart_property_location);
		if (radioGroupChartPropertyLocation != null) radioGroupChartPropertyLocation.setOnCheckedChangeListener(this);
		
		radioGroupChartPropertyLocation2 =(RadioGroup)findViewById(R.id.radio_chart_property_location2);
		if (radioGroupChartPropertyLocation2 != null) radioGroupChartPropertyLocation2.setOnCheckedChangeListener(this);

		radioGroupChartPropertyFontStyle =(RadioGroup)findViewById(R.id.radio_chart_property_font_style);
		if (radioGroupChartPropertyFontStyle != null) radioGroupChartPropertyFontStyle.setOnCheckedChangeListener(this);

		radioGroupChartPropertyFontSize =(RadioGroup)findViewById(R.id.radio_chart_property_font_size);
		if (radioGroupChartPropertyFontSize != null) radioGroupChartPropertyFontSize.setOnCheckedChangeListener(this);
		
		m_CheckBoxStrikeOut = (CheckBox)findViewById(R.id.checkbox_chart_property_font_strikeout);
		if (m_CheckBoxStrikeOut != null) m_CheckBoxStrikeOut.setOnCheckedChangeListener(m_ChartCheckBoxListener);
		
		m_SwitchStrikeOut = (Switch)findViewById(R.id.switch_chart_property_font_strikeout);
		if (m_SwitchStrikeOut != null) m_SwitchStrikeOut.setOnCheckedChangeListener(m_ChartCheckBoxListener);
		
		m_CheckBoxUnderLine = (CheckBox)findViewById(R.id.checkbox_chart_property_font_underline);
		if (m_CheckBoxUnderLine != null) m_CheckBoxUnderLine.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_SwitchUnderLine = (Switch)findViewById(R.id.switch_chart_property_font_underline);
		if (m_SwitchUnderLine != null) m_SwitchUnderLine.setOnCheckedChangeListener(m_ChartCheckBoxListener);

		m_ButtonFontColorPreview = (Button)findViewById(R.id.button_chart_property_font_color_preview);
        if (m_ButtonFontColorPreview != null) m_ButtonFontColorPreview.setOnClickListener(this);

        m_EditTextTitle = (EditText)findViewById(R.id.edit_chart_property_title);
        
        m_ButtonTitleApply = (Button)findViewById(R.id.button_chart_property_title_apply);
        if (m_ButtonTitleApply != null) m_ButtonTitleApply.setOnClickListener(this);

        radioGroupChartPropertyBackdropStyle =(RadioGroup)findViewById(R.id.radio_chart_property_backdropframe_style);
		if (radioGroupChartPropertyBackdropStyle != null) radioGroupChartPropertyBackdropStyle.setOnCheckedChangeListener(this);

		m_ButtonBackdropFrameColorPreview = (Button)findViewById(R.id.button_chart_property_backdropframe_color_preview);
        if (m_ButtonBackdropFrameColorPreview != null) m_ButtonBackdropFrameColorPreview.setOnClickListener(this);

        radioGroupChartPropertyPlotBackdropStyle =(RadioGroup)findViewById(R.id.radio_chart_property_plotbackdropframe_style);
		if (radioGroupChartPropertyPlotBackdropStyle != null) radioGroupChartPropertyPlotBackdropStyle.setOnCheckedChangeListener(this);

		m_ButtonPlotBackdropFrameColorPreview = (Button)findViewById(R.id.button_chart_property_plotbackdropframe_color_preview);
        if (m_ButtonPlotBackdropFrameColorPreview != null) m_ButtonPlotBackdropFrameColorPreview.setOnClickListener(this);
        
        m_EditTextPictureFile = (EditText)findViewById(R.id.edit_chart_property_picture_file);
    	m_ButtonPictureFile = (Button)findViewById(R.id.button_chart_property_picture_file);
    	if (m_ButtonPictureFile != null) m_ButtonPictureFile.setOnClickListener(this);
    	m_ButtonPictureClear = (Button)findViewById(R.id.button_chart_property_picture_clear);
    	if (m_ButtonPictureClear != null) m_ButtonPictureClear.setOnClickListener(this);
    	
    	radioGroupChartPropertyPictureDrawStyle = (RadioGroup)findViewById(R.id.radio_chart_property_picture_drawstyle);
		if (radioGroupChartPropertyPictureDrawStyle != null) radioGroupChartPropertyPictureDrawStyle.setOnCheckedChangeListener(this);
		
		radioGroupFillType1FillPattern = (RadioGroup)findViewById(R.id.radio_chart_property_fill_type1_fillpattern);
		if (radioGroupFillType1FillPattern != null) radioGroupFillType1FillPattern.setOnCheckedChangeListener(this);

		radioGroupFillType1EdgePattern = (RadioGroup)findViewById(R.id.radio_chart_property_fill_type1_edgestyle);
		if (radioGroupFillType1EdgePattern != null) radioGroupFillType1EdgePattern.setOnCheckedChangeListener(this);
		
		m_ButtonFillType1FillColorPreview = (Button)findViewById(R.id.button_chart_property_fill_type1_fillcolor_preview);
    	if (m_ButtonFillType1FillColorPreview != null) m_ButtonFillType1FillColorPreview.setOnClickListener(this);
    	
    	m_ButtonFillType1EdgeColorPreview = (Button)findViewById(R.id.button_chart_property_fill_type1_edgecolor_preview);
    	if (m_ButtonFillType1EdgeColorPreview != null) m_ButtonFillType1EdgeColorPreview.setOnClickListener(this);

		radioGroupFillType2FillPattern = (RadioGroup)findViewById(R.id.radio_chart_property_fill_type2_fillpattern);
		if (radioGroupFillType2FillPattern != null) radioGroupFillType2FillPattern.setOnCheckedChangeListener(this);

		m_ButtonFillType2FillColorPreview = (Button)findViewById(R.id.button_chart_property_fill_type2_fillcolor_preview);
    	if (m_ButtonFillType2FillColorPreview != null) m_ButtonFillType2FillColorPreview.setOnClickListener(this);
    	
        m_StringsetBackup = new ChartPropertyStringsetData();
        m_LocationBackup = new ChartPropertyLocationData();
		m_FontBackup = new ChartPropertyFontData();
		m_BackdropFrameBackup = new ChartPropertyBackdropFrameData();
		m_BackdropFillBackup = new ChartPropertyBackdropFrameData();
		m_pictureBackup = new ChartPropertyPictureData();
		
		m_ColorPalette = new ColorPalette(m_pParent, m_Chart);
		m_ColorPalette.requestWindowFeature(Window.FEATURE_NO_TITLE);
		m_ColorPalette.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    
	private ChartCheckBoxListenerCallback m_ChartCheckBoxListenerCallback = new ChartCheckBoxListenerCallback()
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
			updateChart( buttonView.getId() );
		}
	};
	
    private OnFileSelectedListener _OnFileSelected = new OnFileSelectedListener() {
		@Override
		public void onSelected(String path, String fileName)
		{
			if (fileName.length() > 0)
			{
				String fullPath = path + fileName;
				fullPath = fullPath.trim();
				updatePictureFilePath(fullPath);
			}
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
		case R.id.button_chart_property_apply:
			InitBackupData();
			this.dismiss();
			break;

		case R.id.button_chart_property_cancel:
			updateBackupData(false);
			InitBackupData();
			this.dismiss();
			break;
			
		case R.id.button_chart_property_title_apply:
		{
			switch (m_nPropertyType)
			{
			case R.id.button_chart_property_title:
				updateChartTitle(nId);
				break;
				
			case R.id.button_chart_property_xaxis:
				updateChartXAxis(nId);
				break;
				
			case R.id.button_chart_property_yaxis:
				updateChartYAxis(nId);
				break;
				
			case R.id.button_chart_property_y2axis:
				updateChartY2Axis(nId);
				break;
				
			case R.id.button_chart_property_zaxis:
				updateChartZAxis(nId);
				break;
			}
		}
			break;
			
		case R.id.button_chart_property_font_color_preview:
		case R.id.button_chart_property_backdropframe_color_preview:
		case R.id.button_chart_property_plotbackdropframe_color_preview:
		case R.id.button_chart_property_fill_type1_fillcolor_preview:
		case R.id.button_chart_property_fill_type1_edgecolor_preview:
		case R.id.button_chart_property_fill_type2_fillcolor_preview:
			m_ColorPalette.show();
			m_ColorPalette.SetPropertyType(m_nPropertyType, nId);
			break;
			
		case R.id.button_chart_property_picture_file:
			m_FileOpenDialog = new CFileOpenDialog(this.getContext());
			m_FileOpenDialog.setOnFileSelected(_OnFileSelected);

			if (null != m_FileOpenDialogInterface)
			{
				m_FileOpenDialogInterface.dismiss();
			}
			m_FileOpenDialogInterface = m_FileOpenDialog.Show();
			break;
			
		case R.id.button_chart_property_picture_clear:
		{
			String strNull = "";
			updatePictureFilePath(strNull);
		}
			break;
			
		default:
			break;
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		switch (checkedId)
		{
		case R.id.chart_title_property_title:
		case R.id.chart_title_property_location:
		case R.id.chart_title_property_font:
		case R.id.chart_title_property_fill:
			
		case R.id.chart_legend_property_location:
		case R.id.chart_legend_property_font:
		case R.id.chart_legend_property_frame:
		case R.id.chart_legend_property_fill:

		case R.id.chart_backdrop_property_frame:
		case R.id.chart_backdrop_property_picture:
		case R.id.chart_backdrop_property_fill:
			
		case R.id.chart_plotbackdrop_property_frame:
		case R.id.chart_plotbackdrop_property_picture:
		case R.id.chart_plotbackdrop_property_fill:
			
		case R.id.chart_xaxis_property_title:
		case R.id.chart_xaxis_property_font:
		case R.id.chart_xaxis_property_fill:
			
		case R.id.chart_yaxis_property_title:
		case R.id.chart_yaxis_property_font:
		case R.id.chart_yaxis_property_fill:
			
		case R.id.chart_y2axis_property_title:
		case R.id.chart_y2axis_property_font:
		case R.id.chart_y2axis_property_fill:
			
		case R.id.chart_zaxis_property_title:
		case R.id.chart_zaxis_property_font:
		case R.id.chart_zaxis_property_fill:
			
			showProperty(checkedId);
			break;

		default:
			updateChart(checkedId);
			break;
		}
		
	}
	
	protected void showProperty(int nId)
	{
		RelativeLayout relativeLayoutChartPropertyTitle = (RelativeLayout)findViewById(R.id.relative_layout_chart_property_title);
		relativeLayoutChartPropertyTitle.setVisibility(View.INVISIBLE);
		
		RelativeLayout relativeLayoutChartPropertyLocation = (RelativeLayout)findViewById(R.id.relative_layout_chart_property_location);
		relativeLayoutChartPropertyLocation.setVisibility(View.INVISIBLE);
		
		RelativeLayout relativeLayoutChartPropertyLocation2 = (RelativeLayout)findViewById(R.id.relative_layout_chart_property_location2);
		relativeLayoutChartPropertyLocation2.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutChartPropertyFont = (RelativeLayout)findViewById(R.id.relative_layout_chart_property_font);
		relativeLayoutChartPropertyFont.setVisibility(View.INVISIBLE);
		
		RelativeLayout relativeLayoutChartPropertyBackdropFrame = (RelativeLayout)findViewById(R.id.relative_layout_chart_property_backdropframe);
		relativeLayoutChartPropertyBackdropFrame.setVisibility(View.INVISIBLE);
		
		RelativeLayout relativeLayoutChartPropertyPlotBackdropFrame = (RelativeLayout)findViewById(R.id.relative_layout_chart_property_plotbackdropframe);
		relativeLayoutChartPropertyPlotBackdropFrame.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutChartPropertyPicture = (RelativeLayout)findViewById(R.id.relative_layout_chart_property_picture);
		relativeLayoutChartPropertyPicture.setVisibility(View.INVISIBLE);
		
		RelativeLayout relativeLayoutChartPropertyFillType1 = (RelativeLayout)findViewById(R.id.relative_layout_chart_property_fill_type1);
		relativeLayoutChartPropertyFillType1.setVisibility(View.INVISIBLE);
		
		RelativeLayout relativeLayoutChartPropertyFillType2 = (RelativeLayout)findViewById(R.id.relative_layout_chart_property_fill_type2);
		relativeLayoutChartPropertyFillType2.setVisibility(View.INVISIBLE);

		switch (nId)
		{
		case R.id.chart_title_property_title:
		case R.id.chart_xaxis_property_title:
		case R.id.chart_yaxis_property_title:
		case R.id.chart_y2axis_property_title:
		case R.id.chart_zaxis_property_title:
			relativeLayoutChartPropertyTitle.setVisibility(View.VISIBLE);
			break;
			
		case R.id.chart_title_property_location:
			relativeLayoutChartPropertyLocation2.setVisibility(View.VISIBLE);
			break;
			
		case R.id.chart_legend_property_location:
			relativeLayoutChartPropertyLocation.setVisibility(View.VISIBLE);
			break;
			
		case R.id.chart_title_property_font:
		case R.id.chart_legend_property_font:
		case R.id.chart_xaxis_property_font:
		case R.id.chart_yaxis_property_font:
		case R.id.chart_y2axis_property_font:
		case R.id.chart_zaxis_property_font:
			relativeLayoutChartPropertyFont.setVisibility(View.VISIBLE);
			break;

		case R.id.chart_legend_property_frame:
		case R.id.chart_backdrop_property_frame:
			relativeLayoutChartPropertyBackdropFrame.setVisibility(View.VISIBLE);
			break;
			
		case R.id.chart_backdrop_property_picture:
		case R.id.chart_plotbackdrop_property_picture:
			relativeLayoutChartPropertyPicture.setVisibility(View.VISIBLE);
			break;
			
		case R.id.chart_plotbackdrop_property_frame:
			relativeLayoutChartPropertyPlotBackdropFrame.setVisibility(View.VISIBLE);
			break;

		case R.id.chart_title_property_fill:
		case R.id.chart_xaxis_property_fill:
		case R.id.chart_yaxis_property_fill:
		case R.id.chart_y2axis_property_fill:
		case R.id.chart_zaxis_property_fill:
			relativeLayoutChartPropertyFillType1.setVisibility(View.VISIBLE);
			break;

		case R.id.chart_legend_property_fill:
		case R.id.chart_backdrop_property_fill:
		case R.id.chart_plotbackdrop_property_fill:
			relativeLayoutChartPropertyFillType2.setVisibility(View.VISIBLE);
			break;
			
		default:
			break;
		}
	}

	public void updateBackupData(boolean bUpdatData)
	{
		if (bUpdatData)
		{
			switch (m_nPropertyType)
			{
			case R.id.button_chart_property_title:
				m_LocationBackup.m_nLocation = m_Chart.getTitleLocation();
				
				m_StringsetBackup.m_strStringTitle = m_Chart.getTitleText();
				
				m_FontBackup.m_strName = m_Chart.getTitleFontName();
				m_FontBackup.m_nSize = m_Chart.getTitleFontSize();
				m_FontBackup.m_nStyle = m_Chart.getTitleFontStyle();
				m_FontBackup.m_nColor = m_Chart.getTitleFontColor();
				m_FontBackup.m_bStrikeOut = m_Chart.getTitleFontStrikeout();
				m_FontBackup.m_bUnderLine = m_Chart.getTitleFontUnderline();
				
				m_BackdropFrameBackup.m_nStyle = m_Chart.getTitleBackdropFrameStyle();
				m_BackdropFrameBackup.m_nColor = m_Chart.getTitleBackdropFrameColor();
				
				m_BackdropFillBackup.m_nStyle = m_Chart.getTitleBackdropFillStyle();
				m_BackdropFillBackup.m_nColor = m_Chart.getTitleBackdropFillColor();
				break;
				
			case R.id.button_chart_property_legend:
				m_LocationBackup.m_nLocation = m_Chart.getLegendLocation();
				
				m_FontBackup.m_strName = m_Chart.getLegendFontName();
				m_FontBackup.m_nSize = m_Chart.getLegendFontSize();
				m_FontBackup.m_nStyle = m_Chart.getLegendFontStyle();
				m_FontBackup.m_nColor = m_Chart.getLegendFontColor();
				m_FontBackup.m_bStrikeOut = m_Chart.getLegendFontStrikeout();
				m_FontBackup.m_bUnderLine = m_Chart.getLegendFontUnderline();
				
				m_BackdropFrameBackup.m_nStyle = m_Chart.getLegendBackdropStyle();
				m_BackdropFrameBackup.m_nColor = m_Chart.getLegendBackdropColor();
				
				m_BackdropFillBackup.m_nStyle = m_Chart.getLegendBackdropFillStyle();
				m_BackdropFillBackup.m_nColor = m_Chart.getLegendBackdropFillColor();

				break;
				
			case R.id.button_chart_property_backdrop:
				m_BackdropFrameBackup.m_nStyle = m_Chart.getChartBackdropStyle();
				m_BackdropFrameBackup.m_nColor = m_Chart.getChartBackdropColor();
				
				m_pictureBackup.m_strFile = m_Chart.getChartBackdropPicture();
				m_pictureBackup.m_nStyle = m_Chart.getChartBackdropPictureDrawStyle();
				
				m_BackdropFillBackup.m_nStyle = m_Chart.getChartBackdropFillStyle();
				m_BackdropFillBackup.m_nColor = m_Chart.getChartBackdropFillColor();
				break;

			case R.id.button_chart_property_plotbackdrop:
				m_BackdropFrameBackup.m_nStyle = m_Chart.getPlotBackdropStyle();
				m_BackdropFrameBackup.m_nColor = m_Chart.getPlotBackdropColor();
				
				m_pictureBackup.m_strFile = m_Chart.getPlotBackdropPicture();
				m_pictureBackup.m_nStyle = m_Chart.getPlotBackdropPictureDrawStyle();
				
				m_BackdropFillBackup.m_nStyle = m_Chart.getPlotFillStyle();
				m_BackdropFillBackup.m_nColor = m_Chart.getPlotFillColor();
				break;
				
			case R.id.button_chart_property_xaxis:
				m_StringsetBackup.m_strStringTitle = m_Chart.getXAxisTitle();
				
				m_FontBackup.m_strName = m_Chart.getXAxisTitleFontName();
				m_FontBackup.m_nSize = m_Chart.getXAxisTitleFontSize();
				m_FontBackup.m_nStyle = m_Chart.getXAxisTitleFontStyle();
				m_FontBackup.m_nColor = m_Chart.getXAxisTitleFontColor();
				m_FontBackup.m_bStrikeOut = m_Chart.getXAxisTitleFontStrikeout();
				m_FontBackup.m_bUnderLine = m_Chart.getXAxisTitleFontUnderline();
				
				m_BackdropFrameBackup.m_nStyle = m_Chart.getXAxisTitleBackdropFrameStyle();
				m_BackdropFrameBackup.m_nColor = m_Chart.getXAxisTitleBackdropFrameColor();
				
				m_BackdropFillBackup.m_nStyle = m_Chart.getXAxisTitleBackdropFillStyle();
				m_BackdropFillBackup.m_nColor = m_Chart.getXAxisTitleBackdropFillColor();
				break;
				
			case R.id.button_chart_property_yaxis:
				m_StringsetBackup.m_strStringTitle = m_Chart.getYAxisTitle();
				
				m_FontBackup.m_strName = m_Chart.getYAxisTitleFontName();
				m_FontBackup.m_nSize = m_Chart.getYAxisTitleFontSize();
				m_FontBackup.m_nStyle = m_Chart.getYAxisTitleFontStyle();
				m_FontBackup.m_nColor = m_Chart.getYAxisTitleFontColor();
				m_FontBackup.m_bStrikeOut = m_Chart.getYAxisTitleFontStrikeout();
				m_FontBackup.m_bUnderLine = m_Chart.getYAxisTitleFontUnderline();
				
				m_BackdropFrameBackup.m_nStyle = m_Chart.getYAxisTitleBackdropFrameStyle();
				m_BackdropFrameBackup.m_nColor = m_Chart.getYAxisTitleBackdropFrameColor();
				
				m_BackdropFillBackup.m_nStyle = m_Chart.getYAxisTitleBackdropFillStyle();
				m_BackdropFillBackup.m_nColor = m_Chart.getYAxisTitleBackdropFillColor();
				break;
				
			case R.id.button_chart_property_y2axis:
				m_StringsetBackup.m_strStringTitle = m_Chart.getY2AxisTitle();
				
				m_FontBackup.m_strName = m_Chart.getY2AxisTitleFontName();
				m_FontBackup.m_nSize = m_Chart.getY2AxisTitleFontSize();
				m_FontBackup.m_nStyle = m_Chart.getY2AxisTitleFontStyle();
				m_FontBackup.m_nColor = m_Chart.getY2AxisTitleFontColor();
				m_FontBackup.m_bStrikeOut = m_Chart.getY2AxisTitleFontStrikeout();
				m_FontBackup.m_bUnderLine = m_Chart.getY2AxisTitleFontUnderline();
				
				m_BackdropFrameBackup.m_nStyle = m_Chart.getY2AxisTitleBackdropFrameStyle();
				m_BackdropFrameBackup.m_nColor = m_Chart.getY2AxisTitleBackdropFrameColor();
				
				m_BackdropFillBackup.m_nStyle = m_Chart.getY2AxisTitleBackdropFillStyle();
				m_BackdropFillBackup.m_nColor = m_Chart.getY2AxisTitleBackdropFillColor();
				break;
				
			case R.id.button_chart_property_zaxis:
				m_StringsetBackup.m_strStringTitle = m_Chart.getZAxisTitle();
				
				m_FontBackup.m_strName = m_Chart.getZAxisTitleFontName();
				m_FontBackup.m_nSize = m_Chart.getZAxisTitleFontSize();
				m_FontBackup.m_nStyle = m_Chart.getZAxisTitleFontStyle();
				m_FontBackup.m_nColor = m_Chart.getZAxisTitleFontColor();
				m_FontBackup.m_bStrikeOut = m_Chart.getZAxisTitleFontStrikeout();
				m_FontBackup.m_bUnderLine = m_Chart.getZAxisTitleFontUnderline();
				
				m_BackdropFrameBackup.m_nStyle = m_Chart.getZAxisTitleBackdropFrameStyle();
				m_BackdropFrameBackup.m_nColor = m_Chart.getZAxisTitleBackdropFrameColor();
				
				m_BackdropFillBackup.m_nStyle = m_Chart.getZAxisTitleBackdropFillStyle();
				m_BackdropFillBackup.m_nColor = m_Chart.getZAxisTitleBackdropFillColor();
				break;

			default:
				break;
			}
		}
		else
		{
			switch (m_nPropertyType)
			{
			case R.id.button_chart_property_title:
				m_Chart.setTitleLocation(m_LocationBackup.m_nLocation);
				
				m_Chart.setTitleText(m_StringsetBackup.m_strStringTitle);
				
				m_Chart.setTitleFontName(m_FontBackup.m_strName);
				m_Chart.setTitleFontSize(m_FontBackup.m_nSize);
				m_Chart.setTitleFontStyle(m_FontBackup.m_nStyle);
				m_Chart.setTitleFontColor(m_FontBackup.m_nColor);
				m_Chart.setTitleFontStrikeout(m_FontBackup.m_bStrikeOut);
				m_Chart.setTitleFontUnderline(m_FontBackup.m_bUnderLine);
				
				m_Chart.setTitleBackdropFrameStyle(m_BackdropFrameBackup.m_nStyle);
				m_Chart.setTitleBackdropFrameColor(m_BackdropFrameBackup.m_nColor);
				
				m_Chart.setTitleBackdropFillStyle(m_BackdropFillBackup.m_nStyle);
				m_Chart.setTitleBackdropFillColor(m_BackdropFillBackup.m_nColor);
				break;
				
			case R.id.button_chart_property_legend:
				m_Chart.setLegendLocation(m_LocationBackup.m_nLocation);
				
				m_Chart.setLegendFontName(m_FontBackup.m_strName);
				m_Chart.setLegendFontSize(m_FontBackup.m_nSize);
				m_Chart.setLegendFontStyle(m_FontBackup.m_nStyle);
				m_Chart.setLegendFontColor(m_FontBackup.m_nColor);
				m_Chart.setLegendFontStrikeout(m_FontBackup.m_bStrikeOut);
				m_Chart.setLegendFontUnderline(m_FontBackup.m_bUnderLine);
				
				m_Chart.setLegendBackdropStyle(m_BackdropFrameBackup.m_nStyle);
				m_Chart.setLegendBackdropColor(m_BackdropFrameBackup.m_nColor);
				
				m_Chart.setLegendBackdropFillStyle(m_BackdropFillBackup.m_nStyle);
				m_Chart.setLegendBackdropFillColor(m_BackdropFillBackup.m_nColor);
				break;
				
			case R.id.button_chart_property_backdrop:
				m_Chart.setChartBackdropStyle(m_BackdropFrameBackup.m_nStyle);
				m_Chart.setChartBackdropColor(m_BackdropFrameBackup.m_nColor);
				
				m_Chart.setChartBackdropPicture(m_pictureBackup.m_strFile);
				m_Chart.setChartBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
				
				m_Chart.setChartBackdropFillStyle(m_BackdropFillBackup.m_nStyle);
				m_Chart.setChartBackdropFillColor(m_BackdropFillBackup.m_nColor);
				break;
				
			case R.id.button_chart_property_plotbackdrop:
				m_Chart.setPlotBackdropStyle(m_BackdropFrameBackup.m_nStyle);
				m_Chart.setPlotBackdropColor(m_BackdropFrameBackup.m_nColor);
				
				m_Chart.setPlotBackdropPicture(m_pictureBackup.m_strFile);
				m_Chart.setPlotBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
				
				m_Chart.setPlotFillStyle(m_BackdropFillBackup.m_nStyle);
				m_Chart.setPlotFillColor(m_BackdropFillBackup.m_nColor);
				break;
				
			case R.id.button_chart_property_xaxis:
				m_Chart.setXAxisTitle(m_StringsetBackup.m_strStringTitle);
				
				m_Chart.setXAxisTitleFontName(m_FontBackup.m_strName);
				m_Chart.setXAxisTitleFontSize(m_FontBackup.m_nSize);
				m_Chart.setXAxisTitleFontStyle(m_FontBackup.m_nStyle);
				m_Chart.setXAxisTitleFontColor(m_FontBackup.m_nColor);
				m_Chart.setXAxisTitleFontStrikeout(m_FontBackup.m_bStrikeOut);
				m_Chart.setXAxisTitleFontUnderline(m_FontBackup.m_bUnderLine);
				
				m_Chart.setXAxisTitleBackdropFrameStyle(m_BackdropFrameBackup.m_nStyle);
				m_Chart.setXAxisTitleBackdropFrameColor(m_BackdropFrameBackup.m_nColor);
				
				m_Chart.setXAxisTitleBackdropFillStyle(m_BackdropFillBackup.m_nStyle);
				m_Chart.setXAxisTitleBackdropFillColor(m_BackdropFillBackup.m_nColor);
				break;

			case R.id.button_chart_property_yaxis:
				m_Chart.setYAxisTitle(m_StringsetBackup.m_strStringTitle);
				
				m_Chart.setYAxisTitleFontName(m_FontBackup.m_strName);
				m_Chart.setYAxisTitleFontSize(m_FontBackup.m_nSize);
				m_Chart.setYAxisTitleFontStyle(m_FontBackup.m_nStyle);
				m_Chart.setYAxisTitleFontColor(m_FontBackup.m_nColor);
				m_Chart.setYAxisTitleFontStrikeout(m_FontBackup.m_bStrikeOut);
				m_Chart.setYAxisTitleFontUnderline(m_FontBackup.m_bUnderLine);
				
				m_Chart.setYAxisTitleBackdropFrameStyle(m_BackdropFrameBackup.m_nStyle);
				m_Chart.setYAxisTitleBackdropFrameColor(m_BackdropFrameBackup.m_nColor);
				
				m_Chart.setYAxisTitleBackdropFillStyle(m_BackdropFillBackup.m_nStyle);
				m_Chart.setYAxisTitleBackdropFillColor(m_BackdropFillBackup.m_nColor);
				break;
				
			case R.id.button_chart_property_y2axis:
				m_Chart.setY2AxisTitle(m_StringsetBackup.m_strStringTitle);
				
				m_Chart.setY2AxisTitleFontName(m_FontBackup.m_strName);
				m_Chart.setY2AxisTitleFontSize(m_FontBackup.m_nSize);
				m_Chart.setY2AxisTitleFontStyle(m_FontBackup.m_nStyle);
				m_Chart.setY2AxisTitleFontColor(m_FontBackup.m_nColor);
				m_Chart.setY2AxisTitleFontStrikeout(m_FontBackup.m_bStrikeOut);
				m_Chart.setY2AxisTitleFontUnderline(m_FontBackup.m_bUnderLine);
				
				m_Chart.setY2AxisTitleBackdropFrameStyle(m_BackdropFrameBackup.m_nStyle);
				m_Chart.setY2AxisTitleBackdropFrameColor(m_BackdropFrameBackup.m_nColor);
				
				m_Chart.setY2AxisTitleBackdropFillStyle(m_BackdropFillBackup.m_nStyle);
				m_Chart.setY2AxisTitleBackdropFillColor(m_BackdropFillBackup.m_nColor);
				break;

			case R.id.button_chart_property_zaxis:
				m_Chart.setZAxisTitle(m_StringsetBackup.m_strStringTitle);
				
				m_Chart.setZAxisTitleFontName(m_FontBackup.m_strName);
				m_Chart.setZAxisTitleFontSize(m_FontBackup.m_nSize);
				m_Chart.setZAxisTitleFontStyle(m_FontBackup.m_nStyle);
				m_Chart.setZAxisTitleFontColor(m_FontBackup.m_nColor);
				m_Chart.setZAxisTitleFontStrikeout(m_FontBackup.m_bStrikeOut);
				m_Chart.setZAxisTitleFontUnderline(m_FontBackup.m_bUnderLine);
				
				m_Chart.setZAxisTitleBackdropFrameStyle(m_BackdropFrameBackup.m_nStyle);
				m_Chart.setZAxisTitleBackdropFrameColor(m_BackdropFrameBackup.m_nColor);
				
				m_Chart.setZAxisTitleBackdropFillStyle(m_BackdropFillBackup.m_nStyle);
				m_Chart.setZAxisTitleBackdropFillColor(m_BackdropFillBackup.m_nColor);
				break;

			default:
				break;
			}
		}
	}
	
	public void updateCtrl()
	{
		switch (m_nPropertyType)
		{
		case R.id.button_chart_property_title:
			radioGroupChartTitleProperty.check(R.id.chart_title_property_location);
			radioGroupChartPropertyLocation2.check(getLocation2CtrlId(m_LocationBackup.m_nLocation));
			
			m_EditTextTitle.setText(m_StringsetBackup.m_strStringTitle);
			radioGroupChartPropertyFontStyle.check(getFontStyleCtrlId(m_FontBackup.m_nStyle));
			radioGroupChartPropertyFontSize.check(getFontSizeCtrlId(m_FontBackup.m_nSize));
			if (null != m_CheckBoxStrikeOut) m_CheckBoxStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_SwitchStrikeOut) m_SwitchStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_CheckBoxUnderLine) m_CheckBoxUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			if (null != m_SwitchUnderLine) m_SwitchUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			m_ButtonFontColorPreview.setBackgroundColor(m_FontBackup.getColor());
			
			radioGroupFillType1FillPattern.check(getFillType1PatternCtrlId(m_BackdropFillBackup.m_nStyle));
			m_ButtonFillType1FillColorPreview.setBackgroundColor(m_BackdropFillBackup.getColor());
			
			radioGroupFillType1EdgePattern.check(getFillType1EdgeTypeCtrlId(m_BackdropFrameBackup.m_nStyle));
			m_ButtonFillType1EdgeColorPreview.setBackgroundColor(m_BackdropFrameBackup.getColor());

			showProperty(R.id.chart_title_property_location);
			break;
			
		case R.id.button_chart_property_legend:
			radioGroupChartLegendProperty.check(R.id.chart_legend_property_location);
			radioGroupChartPropertyLocation.check(getLocationCtrlId(m_LocationBackup.m_nLocation));
			
			radioGroupChartPropertyFontStyle.check(getFontStyleCtrlId(m_FontBackup.m_nStyle));
			radioGroupChartPropertyFontSize.check(getFontSizeCtrlId(m_FontBackup.m_nSize));
			if (null != m_CheckBoxStrikeOut) m_CheckBoxStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_SwitchStrikeOut) m_SwitchStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_CheckBoxUnderLine) m_CheckBoxUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			if (null != m_SwitchUnderLine) m_SwitchUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			m_ButtonFontColorPreview.setBackgroundColor(m_FontBackup.getColor());
			
			radioGroupChartPropertyBackdropStyle.check(getBackdropStyleCtrlId(m_BackdropFrameBackup.m_nStyle));
			m_ButtonBackdropFrameColorPreview.setBackgroundColor(m_BackdropFrameBackup.getColor());
			
			radioGroupFillType2FillPattern.check(getFillType2PatternCtrlId(m_BackdropFillBackup.m_nStyle));
			m_ButtonFillType2FillColorPreview.setBackgroundColor(m_BackdropFillBackup.getColor());

			showProperty(R.id.chart_legend_property_location);
			break;
			
		case R.id.button_chart_property_backdrop:
			radioGroupChartBackdropProperty.check(R.id.chart_backdrop_property_frame);
			
			radioGroupChartPropertyBackdropStyle.check(getBackdropStyleCtrlId(m_BackdropFrameBackup.m_nStyle));
			m_ButtonBackdropFrameColorPreview.setBackgroundColor(m_BackdropFrameBackup.getColor());
			
			m_EditTextPictureFile.setText(m_pictureBackup.m_strFile);
			radioGroupChartPropertyPictureDrawStyle.check(getBackdropPictureDrawStyleCtrlId(m_pictureBackup.m_nStyle));
			
			radioGroupFillType2FillPattern.check(getFillType2PatternCtrlId(m_BackdropFillBackup.m_nStyle));
			m_ButtonFillType2FillColorPreview.setBackgroundColor(m_BackdropFillBackup.getColor());
			
			showProperty(R.id.chart_backdrop_property_frame);
			break;
			
		case R.id.button_chart_property_plotbackdrop:
			radioGroupChartPlotBackdropProperty.check(R.id.chart_plotbackdrop_property_frame);
			
			radioGroupChartPropertyPlotBackdropStyle.check(getPlotBackdropStyleCtrlId(m_BackdropFrameBackup.m_nStyle));
			m_ButtonPlotBackdropFrameColorPreview.setBackgroundColor(m_BackdropFrameBackup.getColor());
			
			m_EditTextPictureFile.setText(m_pictureBackup.m_strFile);
			radioGroupChartPropertyPictureDrawStyle.check(getBackdropPictureDrawStyleCtrlId(m_pictureBackup.m_nStyle));
			
			radioGroupFillType2FillPattern.check(getFillType2PatternCtrlId(m_BackdropFillBackup.m_nStyle));
			m_ButtonFillType2FillColorPreview.setBackgroundColor(m_BackdropFillBackup.getColor());
			
			showProperty(R.id.chart_plotbackdrop_property_frame);
			break;
			
		case R.id.button_chart_property_xaxis:
			radioGroupChartXAxisProperty.check(R.id.chart_xaxis_property_title);
			
			m_EditTextTitle.setText(m_StringsetBackup.m_strStringTitle);
			
			radioGroupChartPropertyFontStyle.check(getFontStyleCtrlId(m_FontBackup.m_nStyle));
			radioGroupChartPropertyFontSize.check(getFontSizeCtrlId(m_FontBackup.m_nSize));
			if (null != m_CheckBoxStrikeOut) m_CheckBoxStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_SwitchStrikeOut) m_SwitchStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_CheckBoxUnderLine) m_CheckBoxUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			if (null != m_SwitchUnderLine) m_SwitchUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			m_ButtonFontColorPreview.setBackgroundColor(m_FontBackup.getColor());
			
			radioGroupFillType1FillPattern.check(getFillType1PatternCtrlId(m_BackdropFillBackup.m_nStyle));
			m_ButtonFillType1FillColorPreview.setBackgroundColor(m_BackdropFillBackup.getColor());
			
			radioGroupFillType1EdgePattern.check(getFillType1EdgeTypeCtrlId(m_BackdropFrameBackup.m_nStyle));
			m_ButtonFillType1EdgeColorPreview.setBackgroundColor(m_BackdropFrameBackup.getColor());

			showProperty(R.id.chart_xaxis_property_title);
			break;
			
		case R.id.button_chart_property_yaxis:
			radioGroupChartYAxisProperty.check(R.id.chart_yaxis_property_title);
			
			m_EditTextTitle.setText(m_StringsetBackup.m_strStringTitle);
			
			radioGroupChartPropertyFontStyle.check(getFontStyleCtrlId(m_FontBackup.m_nStyle));
			radioGroupChartPropertyFontSize.check(getFontSizeCtrlId(m_FontBackup.m_nSize));
			if (null != m_CheckBoxStrikeOut) m_CheckBoxStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_SwitchStrikeOut) m_SwitchStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_CheckBoxUnderLine) m_CheckBoxUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			if (null != m_SwitchUnderLine) m_SwitchUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			m_ButtonFontColorPreview.setBackgroundColor(m_FontBackup.getColor());
			
			radioGroupFillType1FillPattern.check(getFillType1PatternCtrlId(m_BackdropFillBackup.m_nStyle));
			m_ButtonFillType1FillColorPreview.setBackgroundColor(m_BackdropFillBackup.getColor());
			
			radioGroupFillType1EdgePattern.check(getFillType1EdgeTypeCtrlId(m_BackdropFrameBackup.m_nStyle));
			m_ButtonFillType1EdgeColorPreview.setBackgroundColor(m_BackdropFrameBackup.getColor());

			showProperty(R.id.chart_yaxis_property_title);
			break;
			
		case R.id.button_chart_property_y2axis:
			radioGroupChartY2AxisProperty.check(R.id.chart_y2axis_property_title);
			
			m_EditTextTitle.setText(m_StringsetBackup.m_strStringTitle);
			
			radioGroupChartPropertyFontStyle.check(getFontStyleCtrlId(m_FontBackup.m_nStyle));
			radioGroupChartPropertyFontSize.check(getFontSizeCtrlId(m_FontBackup.m_nSize));
			if (null != m_CheckBoxStrikeOut) m_CheckBoxStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_SwitchStrikeOut) m_SwitchStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_CheckBoxUnderLine) m_CheckBoxUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			if (null != m_SwitchUnderLine) m_SwitchUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			m_ButtonFontColorPreview.setBackgroundColor(m_FontBackup.getColor());
			
			radioGroupFillType1FillPattern.check(getFillType1PatternCtrlId(m_BackdropFillBackup.m_nStyle));
			m_ButtonFillType1FillColorPreview.setBackgroundColor(m_BackdropFillBackup.getColor());
			
			radioGroupFillType1EdgePattern.check(getFillType1EdgeTypeCtrlId(m_BackdropFrameBackup.m_nStyle));
			m_ButtonFillType1EdgeColorPreview.setBackgroundColor(m_BackdropFrameBackup.getColor());

			showProperty(R.id.chart_y2axis_property_title);
			break;
			
		case R.id.button_chart_property_zaxis:
			radioGroupChartZAxisProperty.check(R.id.chart_zaxis_property_title);
			
			m_EditTextTitle.setText(m_StringsetBackup.m_strStringTitle);
			
			radioGroupChartPropertyFontStyle.check(getFontStyleCtrlId(m_FontBackup.m_nStyle));
			radioGroupChartPropertyFontSize.check(getFontSizeCtrlId(m_FontBackup.m_nSize));
			if (null != m_CheckBoxStrikeOut) m_CheckBoxStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_SwitchStrikeOut) m_SwitchStrikeOut.setChecked(m_FontBackup.m_bStrikeOut);
			if (null != m_CheckBoxUnderLine) m_CheckBoxUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			if (null != m_SwitchUnderLine) m_SwitchUnderLine.setChecked(m_FontBackup.m_bUnderLine);
			m_ButtonFontColorPreview.setBackgroundColor(m_FontBackup.getColor());
			
			radioGroupFillType1FillPattern.check(getFillType1PatternCtrlId(m_BackdropFillBackup.m_nStyle));
			m_ButtonFillType1FillColorPreview.setBackgroundColor(m_BackdropFillBackup.getColor());
			
			radioGroupFillType1EdgePattern.check(getFillType1EdgeTypeCtrlId(m_BackdropFrameBackup.m_nStyle));
			m_ButtonFillType1EdgeColorPreview.setBackgroundColor(m_BackdropFrameBackup.getColor());

			showProperty(R.id.chart_zaxis_property_title);
			break;

		default:
			break;
		}
	}
	
	public void updateChart(int nId)
	{
		switch (m_nPropertyType)
		{
		case R.id.button_chart_property_title:
			updateChartTitle(nId);
			break;
			
		case R.id.button_chart_property_legend:
			updateChartLegend(nId);
			break;
			
		case R.id.button_chart_property_backdrop:
			updateChartBackdropFrame(nId);
			break;
			
		case R.id.button_chart_property_plotbackdrop:
			updateChartPlotBackdropFrame(nId);
			break;
			
		case R.id.button_chart_property_xaxis:
			updateChartXAxis(nId);
			break;
			
		case R.id.button_chart_property_yaxis:
			updateChartYAxis(nId);
			break;
			
		case R.id.button_chart_property_y2axis:
			updateChartY2Axis(nId);
			break;
			
		case R.id.button_chart_property_zaxis:
			updateChartZAxis(nId);
			break;

		default:
			break;
		}
	}

	public void updateChartTitle(int nId)
	{
		switch (nId)
		{
		case R.id.button_chart_property_title_apply:
			String strTitle = m_EditTextTitle.getText().toString();
			m_Chart.setTitleText(strTitle);
			break;
			
		case R.id.chart_property_location2_topleft:
			m_Chart.setTitleLocation(LOCATION_TOPLEFT);
			break;
			
		case R.id.chart_property_location2_top:
			m_Chart.setTitleLocation(LOCATION_TOP);
			break;
			
		case R.id.chart_property_location2_topright:
			m_Chart.setTitleLocation(LOCATION_TOPRIGHT);
			break;
			
		case R.id.chart_property_location2_left:
			m_Chart.setTitleLocation(LOCATION_LEFT);
			break;
			
		case R.id.chart_property_location2_right:
			m_Chart.setTitleLocation(LOCATION_RIGHT);
			break;
			
		case R.id.chart_property_location2_bottomleft:
			m_Chart.setTitleLocation(LOCATION_BOTTOMLEFT);
			break;
			
		case R.id.chart_property_location2_bottom:
			m_Chart.setTitleLocation(LOCATION_BOTTOM);
			break;
			
		case R.id.chart_property_location2_bottomright:
			m_Chart.setTitleLocation(LOCATION_BOTTOMRIGHT);
			break;
			
		case R.id.chart_property_font_style_regular:
			m_Chart.setTitleFontStyle(FONT_STYLE_REGULAR);
			break;
			
		case R.id.chart_property_font_style_bold:
			m_Chart.setTitleFontStyle(FONT_STYLE_BOLD);
			break;
			
		case R.id.chart_property_font_size_8:
			m_Chart.setTitleFontSize(8);
			break;
		case R.id.chart_property_font_size_10:
			m_Chart.setTitleFontSize(10);
			break;
		case R.id.chart_property_font_size_12:
			m_Chart.setTitleFontSize(12);
			break;
		case R.id.chart_property_font_size_20:
			m_Chart.setTitleFontSize(20);
			break;
		case R.id.chart_property_font_size_24:
			m_Chart.setTitleFontSize(24);
			break;
		case R.id.chart_property_font_size_30:
			m_Chart.setTitleFontSize(30);
			break;
			
		case R.id.checkbox_chart_property_font_strikeout:
			m_Chart.setTitleFontStrikeout(m_CheckBoxStrikeOut.isChecked());
			break;
		case R.id.switch_chart_property_font_strikeout:
			m_Chart.setTitleFontStrikeout(m_SwitchStrikeOut.isChecked());
			break;
			
		case R.id.checkbox_chart_property_font_underline:
			m_Chart.setTitleFontUnderline(m_CheckBoxUnderLine.isChecked());
			break;
		case R.id.switch_chart_property_font_underline:
			m_Chart.setTitleFontUnderline(m_SwitchUnderLine.isChecked());
			break;
			
		case R.id.chart_property_fill_type1_fillpattern_none:
			m_Chart.setTitleBackdropFillStyle(ESBrushStyleNull);
			m_ButtonFillType1FillColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type1_fillpattern_solid:
			m_Chart.setTitleBackdropFillStyle(ESBrushStyleSolid);
			m_ButtonFillType1FillColorPreview.setEnabled(true);
			break;
		case R.id.chart_property_fill_type1_edgestyle_none:
			m_Chart.setTitleBackdropFrameStyle(ESPenStyleNull);
			m_ButtonFillType1EdgeColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type1_edgestyle_solid:
			m_Chart.setTitleBackdropFrameStyle(ESPenStyleSolid);
			m_ButtonFillType1EdgeColorPreview.setEnabled(true);
			break;
			
		default:
			break;
		}
	}
	
	public void updateChartLegend(int nId)
	{
		switch (nId)
		{
		case R.id.chart_property_location_left:
			m_Chart.setLegendLocation(LOCATION_LEFT);
			break;
			
		case R.id.chart_property_location_top:
			m_Chart.setLegendLocation(LOCATION_TOP);
			break;
			
		case R.id.chart_property_location_right:
			m_Chart.setLegendLocation(LOCATION_RIGHT);
			break;
			
		case R.id.chart_property_location_bottom:
			m_Chart.setLegendLocation(LOCATION_BOTTOM);
			break;
			
		case R.id.chart_property_location_topright:
			m_Chart.setLegendLocation(LOCATION_TOPRIGHT);
			break;
			
		case R.id.chart_property_font_style_regular:
			m_Chart.setLegendFontStyle(FONT_STYLE_REGULAR);
			break;
			
		case R.id.chart_property_font_style_bold:
			m_Chart.setLegendFontStyle(FONT_STYLE_BOLD);
			break;
			
		case R.id.chart_property_font_size_8:
			m_Chart.setLegendFontSize(8);
			break;
		case R.id.chart_property_font_size_10:
			m_Chart.setLegendFontSize(10);
			break;
		case R.id.chart_property_font_size_12:
			m_Chart.setLegendFontSize(12);
			break;
		case R.id.chart_property_font_size_20:
			m_Chart.setLegendFontSize(20);
			break;
		case R.id.chart_property_font_size_24:
			m_Chart.setLegendFontSize(24);
			break;
		case R.id.chart_property_font_size_30:
			m_Chart.setLegendFontSize(30);
			break;
			
		case R.id.checkbox_chart_property_font_strikeout:
			m_Chart.setLegendFontStrikeout(m_CheckBoxStrikeOut.isChecked());
			break;
		case R.id.switch_chart_property_font_strikeout:
			m_Chart.setLegendFontStrikeout(m_SwitchStrikeOut.isChecked());
			break;
			
		case R.id.checkbox_chart_property_font_underline:
			m_Chart.setLegendFontUnderline(m_CheckBoxUnderLine.isChecked());
			break;
		case R.id.switch_chart_property_font_underline:
			m_Chart.setLegendFontUnderline(m_SwitchUnderLine.isChecked());
			break;
			
		case R.id.chart_property_backdropframe_style_null:
			m_Chart.setLegendBackdropStyle(ESFrameStyleNull);
			m_ButtonBackdropFrameColorPreview.setEnabled(false);
			break;
			
		case R.id.chart_property_backdropframe_style_singleline:
			m_Chart.setLegendBackdropStyle(ESFrameStyleSingleLine);
			m_ButtonBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_backdropframe_style_doubleline:
			m_Chart.setLegendBackdropStyle(ESFrameStyleDoubleLine);
			m_ButtonBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_backdropframe_style_thickinner:
			m_Chart.setLegendBackdropStyle(ESFrameStyleThickInner);
			m_ButtonBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_backdropframe_style_thickouter:
			m_Chart.setLegendBackdropStyle(ESFrameStyleThickOuter);
			m_ButtonBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_fill_type2_fillpattern_none:
			m_Chart.setLegendBackdropFillStyle(ESBrushStyleNull);
			m_ButtonFillType2FillColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type2_fillpattern_solid:
			m_Chart.setLegendBackdropFillStyle(ESBrushStyleSolid);
			m_ButtonFillType2FillColorPreview.setEnabled(true);
			break;
			
		default:
			break;
		}
	}
	
	public void updateChartBackdropFrame(int nId)
	{
		switch (nId)
		{
		case R.id.chart_property_backdropframe_style_null:
			m_Chart.setChartBackdropStyle(ESFrameStyleNull);
			m_ButtonBackdropFrameColorPreview.setEnabled(false);
			break;
			
		case R.id.chart_property_backdropframe_style_singleline:
			m_Chart.setChartBackdropStyle(ESFrameStyleSingleLine);
			m_ButtonBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_backdropframe_style_doubleline:
			m_Chart.setChartBackdropStyle(ESFrameStyleDoubleLine);
			m_ButtonBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_backdropframe_style_thickinner:
			m_Chart.setChartBackdropStyle(ESFrameStyleThickInner);
			m_ButtonBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_backdropframe_style_thickouter:
			m_Chart.setChartBackdropStyle(ESFrameStyleThickOuter);
			m_ButtonBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_picture_drawstyle_actulsize:
			m_pictureBackup.m_nStyle = ESPictureLocationTypeCenter;
			m_Chart.setChartBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
			break;
		case R.id.chart_property_picture_drawstyle_fullsize:
			m_pictureBackup.m_nStyle = ESPictureLocationTypeFill;
			m_Chart.setChartBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
			break;
		case R.id.chart_property_picture_drawstyle_fitsize:
			m_pictureBackup.m_nStyle = ESPictureLocationTypeFit;
			m_Chart.setChartBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
			break;
		case R.id.chart_property_picture_drawstyle_stretch:
			m_pictureBackup.m_nStyle = ESPictureLocationTypeStretch;
			m_Chart.setChartBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
			break;
		case R.id.chart_property_picture_drawstyle_tiled:
			m_pictureBackup.m_nStyle = ESPictureLocationTypeTile;
			m_Chart.setChartBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
			break;
			
		case R.id.chart_property_fill_type2_fillpattern_none:
			m_Chart.setChartBackdropFillStyle(ESBrushStyleNull);
			m_ButtonFillType2FillColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type2_fillpattern_solid:
			m_Chart.setChartBackdropFillStyle(ESBrushStyleSolid);
			m_ButtonFillType2FillColorPreview.setEnabled(true);
			break;

		default:
			break;
		}
	}
	
	public void updateChartPlotBackdropFrame(int nId)
	{
		switch (nId)
		{
		case R.id.chart_property_plotbackdropframe_style_null:
			m_Chart.setPlotBackdropStyle(ESFrameStyleNull);
			m_ButtonPlotBackdropFrameColorPreview.setEnabled(false);
			break;
			
		case R.id.chart_property_plotbackdropframe_style_singleline:
			m_Chart.setPlotBackdropStyle(ESFrameStyleSingleLine);
			m_ButtonPlotBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_plotbackdropframe_style_doubleline:
			m_Chart.setPlotBackdropStyle(ESFrameStyleDoubleLine);
			m_ButtonPlotBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_plotbackdropframe_style_thickinner:
			m_Chart.setPlotBackdropStyle(ESFrameStyleThickInner);
			m_ButtonPlotBackdropFrameColorPreview.setEnabled(true);
			break;
			
		case R.id.chart_property_plotbackdropframe_style_thickouter:
			m_Chart.setPlotBackdropStyle(ESFrameStyleThickOuter);
			m_ButtonPlotBackdropFrameColorPreview.setEnabled(true);
			break;

		case R.id.chart_property_picture_drawstyle_actulsize:
			m_pictureBackup.m_nStyle = ESPictureLocationTypeCenter;
			m_Chart.setPlotBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
			break;
		case R.id.chart_property_picture_drawstyle_fullsize:
			m_pictureBackup.m_nStyle = ESPictureLocationTypeFill;
			m_Chart.setPlotBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
			break;
		case R.id.chart_property_picture_drawstyle_fitsize:
			m_pictureBackup.m_nStyle = ESPictureLocationTypeFit;
			m_Chart.setPlotBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
			break;
		case R.id.chart_property_picture_drawstyle_stretch:
			m_pictureBackup.m_nStyle = ESPictureLocationTypeStretch;
			m_Chart.setPlotBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
			break;
		case R.id.chart_property_picture_drawstyle_tiled:
			m_pictureBackup.m_nStyle = ESPictureLocationTypeTile;
			m_Chart.setPlotBackdropPictureDrawStyle(m_pictureBackup.m_nStyle);
			break;
			
		case R.id.chart_property_fill_type2_fillpattern_none:
			m_Chart.setPlotFillStyle(ESBrushStyleNull);
			m_ButtonFillType2FillColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type2_fillpattern_solid:
			m_Chart.setPlotFillStyle(ESBrushStyleSolid);
			m_ButtonFillType2FillColorPreview.setEnabled(true);
			break;
			
		default:
			break;
		}
	}
	
	public void updateChartXAxis(int nId)
	{
		switch (nId)
		{
		case R.id.button_chart_property_title_apply:
			String strTitle = m_EditTextTitle.getText().toString();
			m_Chart.setXAxisTitle(strTitle);
			break;
			
		case R.id.chart_property_font_style_regular:
			m_Chart.setXAxisTitleFontStyle(FONT_STYLE_REGULAR);
			break;
			
		case R.id.chart_property_font_style_bold:
			m_Chart.setXAxisTitleFontStyle(FONT_STYLE_BOLD);
			break;
			
		case R.id.chart_property_font_size_8:
			m_Chart.setXAxisTitleFontSize(8);
			break;
		case R.id.chart_property_font_size_10:
			m_Chart.setXAxisTitleFontSize(10);
			break;
		case R.id.chart_property_font_size_12:
			m_Chart.setXAxisTitleFontSize(12);
			break;
		case R.id.chart_property_font_size_20:
			m_Chart.setXAxisTitleFontSize(20);
			break;
		case R.id.chart_property_font_size_24:
			m_Chart.setXAxisTitleFontSize(24);
			break;
		case R.id.chart_property_font_size_30:
			m_Chart.setXAxisTitleFontSize(30);
			break;
			
		case R.id.checkbox_chart_property_font_strikeout:
			m_Chart.setXAxisTitleFontStrikeout(m_CheckBoxStrikeOut.isChecked());
			break;
		case R.id.switch_chart_property_font_strikeout:
			m_Chart.setXAxisTitleFontStrikeout(m_SwitchStrikeOut.isChecked());
			break;
			
		case R.id.checkbox_chart_property_font_underline:
			m_Chart.setXAxisTitleFontUnderline(m_CheckBoxUnderLine.isChecked());
			break;
		case R.id.switch_chart_property_font_underline:
			m_Chart.setXAxisTitleFontUnderline(m_SwitchUnderLine.isChecked());
			break;
			
		case R.id.chart_property_fill_type1_fillpattern_none:
			m_Chart.setXAxisTitleBackdropFillStyle(ESBrushStyleNull);
			m_ButtonFillType1FillColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type1_fillpattern_solid:
			m_Chart.setXAxisTitleBackdropFillStyle(ESBrushStyleSolid);
			m_ButtonFillType1FillColorPreview.setEnabled(true);
			break;
		case R.id.chart_property_fill_type1_edgestyle_none:
			m_Chart.setXAxisTitleBackdropFrameStyle(ESPenStyleNull);
			m_ButtonFillType1EdgeColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type1_edgestyle_solid:
			m_Chart.setXAxisTitleBackdropFrameStyle(ESPenStyleSolid);
			m_ButtonFillType1EdgeColorPreview.setEnabled(true);
			break;
			
		default:
			break;
		}
	}
	
	public void updateChartYAxis(int nId)
	{
		switch (nId)
		{
		case R.id.button_chart_property_title_apply:
			String strTitle = m_EditTextTitle.getText().toString();
			m_Chart.setYAxisTitle(strTitle);
			break;
			
		case R.id.chart_property_font_style_regular:
			m_Chart.setYAxisTitleFontStyle(FONT_STYLE_REGULAR);
			break;
			
		case R.id.chart_property_font_style_bold:
			m_Chart.setYAxisTitleFontStyle(FONT_STYLE_BOLD);
			break;
			
		case R.id.chart_property_font_size_8:
			m_Chart.setYAxisTitleFontSize(8);
			break;
		case R.id.chart_property_font_size_10:
			m_Chart.setYAxisTitleFontSize(10);
			break;
		case R.id.chart_property_font_size_12:
			m_Chart.setYAxisTitleFontSize(12);
			break;
		case R.id.chart_property_font_size_20:
			m_Chart.setYAxisTitleFontSize(20);
			break;
		case R.id.chart_property_font_size_24:
			m_Chart.setYAxisTitleFontSize(24);
			break;
		case R.id.chart_property_font_size_30:
			m_Chart.setYAxisTitleFontSize(30);
			break;
			
		case R.id.checkbox_chart_property_font_strikeout:
			m_Chart.setYAxisTitleFontStrikeout(m_CheckBoxStrikeOut.isChecked());
			break;
		case R.id.switch_chart_property_font_strikeout:
			m_Chart.setYAxisTitleFontStrikeout(m_SwitchStrikeOut.isChecked());
			break;
			
		case R.id.checkbox_chart_property_font_underline:
			m_Chart.setYAxisTitleFontUnderline(m_CheckBoxUnderLine.isChecked());
			break;
		case R.id.switch_chart_property_font_underline:
			m_Chart.setYAxisTitleFontUnderline(m_SwitchUnderLine.isChecked());
			break;
			
		case R.id.chart_property_fill_type1_fillpattern_none:
			m_Chart.setYAxisTitleBackdropFillStyle(ESBrushStyleNull);
			m_ButtonFillType1FillColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type1_fillpattern_solid:
			m_Chart.setYAxisTitleBackdropFillStyle(ESBrushStyleSolid);
			m_ButtonFillType1FillColorPreview.setEnabled(true);
			break;
		case R.id.chart_property_fill_type1_edgestyle_none:
			m_Chart.setYAxisTitleBackdropFrameStyle(ESPenStyleNull);
			m_ButtonFillType1EdgeColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type1_edgestyle_solid:
			m_Chart.setYAxisTitleBackdropFrameStyle(ESPenStyleSolid);
			m_ButtonFillType1EdgeColorPreview.setEnabled(true);
			break;
			
		default:
			break;
		}
	}
	
	public void updateChartY2Axis(int nId)
	{
		switch (nId)
		{
		case R.id.button_chart_property_title_apply:
			String strTitle = m_EditTextTitle.getText().toString();
			m_Chart.setY2AxisVisible(true);
			m_Chart.setY2AxisTitle(strTitle);
			break;
			
		case R.id.chart_property_font_style_regular:
			m_Chart.setY2AxisTitleFontStyle(FONT_STYLE_REGULAR);
			break;
			
		case R.id.chart_property_font_style_bold:
			m_Chart.setY2AxisTitleFontStyle(FONT_STYLE_BOLD);
			break;
			
		case R.id.chart_property_font_size_8:
			m_Chart.setY2AxisTitleFontSize(8);
			break;
		case R.id.chart_property_font_size_10:
			m_Chart.setY2AxisTitleFontSize(10);
			break;
		case R.id.chart_property_font_size_12:
			m_Chart.setY2AxisTitleFontSize(12);
			break;
		case R.id.chart_property_font_size_20:
			m_Chart.setY2AxisTitleFontSize(20);
			break;
		case R.id.chart_property_font_size_24:
			m_Chart.setY2AxisTitleFontSize(24);
			break;
		case R.id.chart_property_font_size_30:
			m_Chart.setY2AxisTitleFontSize(30);
			break;
			
		case R.id.checkbox_chart_property_font_strikeout:
			m_Chart.setY2AxisTitleFontStrikeout(m_CheckBoxStrikeOut.isChecked());
			break;
		case R.id.switch_chart_property_font_strikeout:
			m_Chart.setY2AxisTitleFontStrikeout(m_SwitchStrikeOut.isChecked());
			break;
			
		case R.id.checkbox_chart_property_font_underline:
			m_Chart.setY2AxisTitleFontUnderline(m_CheckBoxUnderLine.isChecked());
			break;
		case R.id.switch_chart_property_font_underline:
			m_Chart.setY2AxisTitleFontUnderline(m_SwitchUnderLine.isChecked());
			break;
			
		case R.id.chart_property_fill_type1_fillpattern_none:
			m_Chart.setY2AxisTitleBackdropFillStyle(ESBrushStyleNull);
			m_ButtonFillType1FillColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type1_fillpattern_solid:
			m_Chart.setY2AxisTitleBackdropFillStyle(ESBrushStyleSolid);
			m_ButtonFillType1FillColorPreview.setEnabled(true);
			break;
		case R.id.chart_property_fill_type1_edgestyle_none:
			m_Chart.setY2AxisTitleBackdropFrameStyle(ESPenStyleNull);
			m_ButtonFillType1EdgeColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type1_edgestyle_solid:
			m_Chart.setY2AxisTitleBackdropFrameStyle(ESPenStyleSolid);
			m_ButtonFillType1EdgeColorPreview.setEnabled(true);
			break;
			
		default:
			break;
		}
	}
	
	public void updateChartZAxis(int nId)
	{
		switch (nId)
		{
		case R.id.button_chart_property_title_apply:
			String strTitle = m_EditTextTitle.getText().toString();
			m_Chart.setZAxisTitle(strTitle);
			break;
			
		case R.id.chart_property_font_style_regular:
			m_Chart.setZAxisTitleFontStyle(FONT_STYLE_REGULAR);
			break;
			
		case R.id.chart_property_font_style_bold:
			m_Chart.setZAxisTitleFontStyle(FONT_STYLE_BOLD);
			break;
			
		case R.id.chart_property_font_size_8:
			m_Chart.setZAxisTitleFontSize(8);
			break;
		case R.id.chart_property_font_size_10:
			m_Chart.setZAxisTitleFontSize(10);
			break;
		case R.id.chart_property_font_size_12:
			m_Chart.setZAxisTitleFontSize(12);
			break;
		case R.id.chart_property_font_size_20:
			m_Chart.setZAxisTitleFontSize(20);
			break;
		case R.id.chart_property_font_size_24:
			m_Chart.setZAxisTitleFontSize(24);
			break;
		case R.id.chart_property_font_size_30:
			m_Chart.setZAxisTitleFontSize(30);
			break;
			
		case R.id.checkbox_chart_property_font_strikeout:
			m_Chart.setZAxisTitleFontStrikeout(m_CheckBoxStrikeOut.isChecked());
			break;
		case R.id.switch_chart_property_font_strikeout:
			m_Chart.setZAxisTitleFontStrikeout(m_SwitchStrikeOut.isChecked());
			break;
			
		case R.id.checkbox_chart_property_font_underline:
			m_Chart.setZAxisTitleFontUnderline(m_CheckBoxUnderLine.isChecked());
			break;
		case R.id.switch_chart_property_font_underline:
			m_Chart.setZAxisTitleFontUnderline(m_SwitchUnderLine.isChecked());
			break;
			
		case R.id.chart_property_fill_type1_fillpattern_none:
			m_Chart.setZAxisTitleBackdropFillStyle(ESBrushStyleNull);
			m_ButtonFillType1FillColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type1_fillpattern_solid:
			m_Chart.setZAxisTitleBackdropFillStyle(ESBrushStyleSolid);
			m_ButtonFillType1FillColorPreview.setEnabled(true);
			break;
		case R.id.chart_property_fill_type1_edgestyle_none:
			m_Chart.setZAxisTitleBackdropFrameStyle(ESPenStyleNull);
			m_ButtonFillType1EdgeColorPreview.setEnabled(false);
			break;
		case R.id.chart_property_fill_type1_edgestyle_solid:
			m_Chart.setZAxisTitleBackdropFrameStyle(ESPenStyleSolid);
			m_ButtonFillType1EdgeColorPreview.setEnabled(true);
			break;
			
		default:
			break;
		}
	}
	
	public void updatePictureFilePath(String strPath)
	{
		switch (m_nPropertyType)
		{
		case R.id.button_chart_property_backdrop:
			m_Chart.setChartBackdropPicture(strPath);
			m_EditTextPictureFile.setText(strPath);
			break;
			
		case R.id.button_chart_property_plotbackdrop:
			m_Chart.setPlotBackdropPicture(strPath);
			m_EditTextPictureFile.setText(strPath);
			break;
			
		default:
			break;
		}
	}
	
	protected void repositionColorPalette()
	{
		Window	window = getWindow();
		if (window == null)
		{
			return;
		}

		WindowManager.LayoutParams paramPalette = m_ColorPalette.getWindow().getAttributes();

		paramPalette.width = m_ColorPalette.GetWidth();
		paramPalette.height = m_ColorPalette.GetHeight();
		m_ColorPalette.getWindow().setAttributes(paramPalette);
	}
	
	public void InitBackupData()
	{
		m_nPropertyType = -1;
		
		m_StringsetBackup.Init();
		m_LocationBackup.Init();
		m_FontBackup.Init();
		m_BackdropFrameBackup.Init();
		m_BackdropFillBackup.Init();
	}
	
	public void SetPropertyType(int nType)
	{
		m_nPropertyType = nType;
		
		radioGroupChartTitleProperty.setVisibility(View.INVISIBLE);
		radioGroupChartLegendProperty.setVisibility(View.INVISIBLE);
		radioGroupChartBackdropProperty.setVisibility(View.INVISIBLE);
		radioGroupChartPlotBackdropProperty.setVisibility(View.INVISIBLE);
		radioGroupChartXAxisProperty.setVisibility(View.INVISIBLE);
		radioGroupChartYAxisProperty.setVisibility(View.INVISIBLE);
		radioGroupChartY2AxisProperty.setVisibility(View.INVISIBLE);
		radioGroupChartZAxisProperty.setVisibility(View.INVISIBLE);
		
		switch (m_nPropertyType)
		{
		case R.id.button_chart_property_title:
			radioGroupChartTitleProperty.setVisibility(View.VISIBLE);
			m_TextViewMainTitle.setText(R.string.chart_title_property);
			break;
			
		case R.id.button_chart_property_legend:
			radioGroupChartLegendProperty.setVisibility(View.VISIBLE);
			m_TextViewMainTitle.setText(R.string.chart_legend_property);
			break;

		case R.id.button_chart_property_backdrop:
		{
			radioGroupChartBackdropProperty.setVisibility(View.VISIBLE);
			
			m_TextViewMainTitle.setText(R.string.chart_backdrop_property);
		}
			break;
			
		case R.id.button_chart_property_plotbackdrop:
		{
			radioGroupChartPlotBackdropProperty.setVisibility(View.VISIBLE);
		
			m_TextViewMainTitle.setText(R.string.chart_plotbackdrop_property);
		}
			break;
			
		case R.id.button_chart_property_xaxis:
			radioGroupChartXAxisProperty.setVisibility(View.VISIBLE);
			m_TextViewMainTitle.setText(R.string.chart_xaxis_title_property);
			break;
			
		case R.id.button_chart_property_yaxis:
			radioGroupChartYAxisProperty.setVisibility(View.VISIBLE);
			m_TextViewMainTitle.setText(R.string.chart_yaxis_title_property);
			break;
			
		case R.id.button_chart_property_y2axis:
			radioGroupChartY2AxisProperty.setVisibility(View.VISIBLE);
			m_TextViewMainTitle.setText(R.string.chart_yaxis_title_property);
			break;
			
		case R.id.button_chart_property_zaxis:
			radioGroupChartZAxisProperty.setVisibility(View.VISIBLE);
			m_TextViewMainTitle.setText(R.string.chart_zaxis_title_property);
			break;
			
		default:
			break;
		}
		
		updateBackupData(true);
		updateCtrl();
	}
	
	public int getLocationCtrlId(int nLocation)
	{
		int nId = 0;
		if (LOCATION_LEFT == nLocation) nId = R.id.chart_property_location_left;
		else if (LOCATION_TOP == nLocation) nId = R.id.chart_property_location_top;
		else if (LOCATION_RIGHT == nLocation) nId = R.id.chart_property_location_right;
		else if (LOCATION_BOTTOM == nLocation) nId = R.id.chart_property_location_bottom;
		else if (LOCATION_TOPRIGHT == nLocation) nId = R.id.chart_property_location_topright;

		return nId;
	}

	public int getLocation2CtrlId(int nLocation)
	{
		int nId = 0;
		if (LOCATION_TOPLEFT == nLocation) nId = R.id.chart_property_location2_topleft;
		else if (LOCATION_TOP == nLocation) nId = R.id.chart_property_location2_top;
		else if (LOCATION_TOPRIGHT == nLocation) nId = R.id.chart_property_location2_topright;
		else if (LOCATION_LEFT == nLocation) nId = R.id.chart_property_location2_left;
		else if (LOCATION_RIGHT == nLocation) nId = R.id.chart_property_location2_right;
		else if (LOCATION_BOTTOMLEFT == nLocation) nId = R.id.chart_property_location2_bottomleft;
		else if (LOCATION_BOTTOM == nLocation) nId = R.id.chart_property_location2_bottom;
		else if (LOCATION_BOTTOMRIGHT == nLocation) nId = R.id.chart_property_location2_bottomright;

		return nId;
	}
	
	public int getFontTypeCtrlId(String strName)
	{
		int nId = 0;

		return nId;
	}
	
	public int getFontStyleCtrlId(int nStyle)
	{
		int nId = 0;
		if (FONT_STYLE_REGULAR == nStyle) nId = R.id.chart_property_font_style_regular;
		else if (FONT_STYLE_BOLD == nStyle) nId = R.id.chart_property_font_style_bold;

		return nId;
	}
	
	public int getFontSizeCtrlId(int nSize)
	{
		int nId = 0;
		if (8 >= nSize) nId = R.id.chart_property_font_size_8;
		else if(12 >= nSize) nId = R.id.chart_property_font_size_12;
		else if(20 >= nSize) nId = R.id.chart_property_font_size_20;
		else if(24 >= nSize) nId = R.id.chart_property_font_size_24;
		else if(30 >= nSize) nId = R.id.chart_property_font_size_30;
		else nId = R.id.chart_property_font_size_30;

		return nId;
	}
	
	public int getBackdropStyleCtrlId(int nStyle)
	{
		int nId = 0;
		if (ESFrameStyleNull == nStyle) nId = R.id.chart_property_backdropframe_style_null;
		else if(ESFrameStyleSingleLine == nStyle) nId = R.id.chart_property_backdropframe_style_singleline;
		else if(ESFrameStyleDoubleLine == nStyle) nId = R.id.chart_property_backdropframe_style_doubleline;
		else if(ESFrameStyleThickInner == nStyle) nId = R.id.chart_property_backdropframe_style_thickinner;
		else if(ESFrameStyleThickOuter == nStyle) nId = R.id.chart_property_backdropframe_style_thickouter;
		else nId = R.id.chart_property_backdropframe_style_null;

		return nId;
	}

	public int getPlotBackdropStyleCtrlId(int nStyle)
	{
		int nId = 0;
		if (ESFrameStyleNull == nStyle) nId = R.id.chart_property_plotbackdropframe_style_null;
		else if(ESFrameStyleSingleLine == nStyle) nId = R.id.chart_property_plotbackdropframe_style_singleline;
		else if(ESFrameStyleDoubleLine == nStyle) nId = R.id.chart_property_plotbackdropframe_style_doubleline;
		else if(ESFrameStyleThickInner == nStyle) nId = R.id.chart_property_plotbackdropframe_style_thickinner;
		else if(ESFrameStyleThickOuter == nStyle) nId = R.id.chart_property_plotbackdropframe_style_thickouter;
		else nId = R.id.chart_property_plotbackdropframe_style_null;

		return nId;
	}

	public int getBackdropPictureDrawStyleCtrlId(int nStyle)
	{
		int nId = -1;
		if (ESPictureLocationTypeCenter == nStyle) nId = R.id.chart_property_picture_drawstyle_actulsize;
		else if(ESPictureLocationTypeFill == nStyle) nId = R.id.chart_property_picture_drawstyle_fullsize;
		else if(ESPictureLocationTypeFit == nStyle) nId = R.id.chart_property_picture_drawstyle_fitsize;
		else if(ESPictureLocationTypeStretch == nStyle) nId = R.id.chart_property_picture_drawstyle_stretch;
		else if(ESPictureLocationTypeTile == nStyle) nId = R.id.chart_property_picture_drawstyle_tiled;

		return nId;
	}
	
	public int getFillType1PatternCtrlId(int nStyle)
	{
		int nId = -1;
		if (ESBrushStyleNull == nStyle) nId = R.id.chart_property_fill_type1_fillpattern_none;
		else if(ESBrushStyleSolid == nStyle) nId = R.id.chart_property_fill_type1_fillpattern_solid;

		return nId;
	}

	public int getFillType1EdgeTypeCtrlId(int nStyle)
	{
		int nId = -1;
		if (ESPenStyleNull == nStyle) nId = R.id.chart_property_fill_type1_edgestyle_none;
		else if(ESPenStyleSolid == nStyle) nId = R.id.chart_property_fill_type1_edgestyle_solid;

		return nId;
	}
	
	public int getFillType2PatternCtrlId(int nStyle)
	{
		int nId = -1;
		if (ESBrushStyleNull == nStyle) nId = R.id.chart_property_fill_type2_fillpattern_none;
		else if(ESBrushStyleSolid == nStyle) nId = R.id.chart_property_fill_type2_fillpattern_solid;

		return nId;
	}
	
	public void SetColorPreview(int nColor, int nId)
	{
		Button buttonColorPreview = (Button)findViewById(nId);
		if (null != buttonColorPreview){
			buttonColorPreview.setBackgroundColor(nColor);
		}
	}
	
}
