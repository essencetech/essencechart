package com.essence.chart_sdk;

import com.essence.chart.Chart;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;

public class SeriesProperty extends Dialog implements android.view.View.OnClickListener, OnCheckedChangeListener {

	private final int HEIGHT_LISTVIEW_ITEM = 65;
	
	public class SeriesColumn
	{
		public SeriesColumn()
		{
			Init();
		}
		
		public void Init()
		{
			order = -1;
		}
		
		public int order;
	}
	
	public class SeriesColumnList
	{
		public SeriesColumnList()
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
			
			if (null != columnList)
			{
				columnList.clear();
			}
			else
			{
				columnList = new ArrayList<SeriesColumn>();
			}
			
			curIndex = -1;
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
		
		public void AddColumn(SeriesColumn col)
		{
			int nSize = columnList.size();
			columnList.add(nSize, col);
		}
		public SeriesColumn GetColumn(int index)
		{
			return columnList.get(index);
		}

		public List<String> labelList = null;
		public List<SeriesColumn> columnList = null;
		
		public int curIndex;
	}
	private SeriesColumnList m_ColumnPropertyList = null;
	
	public class SeriesColumnLabelList
	{
		public SeriesColumnLabelList()
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

	public final int COLUMN_TYPE_BAR = 0;
	public final int COLUMN_TYPE_HILOBAR = 1;

	public final int COLUMN_ORDER_UP = 1;
	public final int COLUMN_ORDER_DOWN = 2;
	public final int COLUMN_ORDER_STACK = 3;
	public final int COLUMN_ORDER_UNSTACK = 4;
	
	private Chart m_Chart = null;
	
	private ColorPalette m_ColorPalette = null;
	
	private int m_nActiveTabID;
	
	Context m_pParent = null;
	
	private RadioGroup radioGroupSeriesProperty = null;
	
	private ListView listViewSeriesTypeColumns = null;
	private ArrayAdapter<String> adapterlistViewSeriesType = null;
	private RadioGroup radioGroupSeriesPropertyType = null;
	
	private ListView listViewSeriesOrderColumns = null;
	private ArrayAdapter<String> adapterlistViewSeriesOrder = null;
	private Button m_ButtonSeriesOrderUp = null;
	private Button m_ButtonSeriesOrderDown = null;
	private Button m_ButtonSeriesOrderStack = null;
	private Button m_ButtonSeriesOrderUnstack = null;
	
	private ListView listViewSeriesColumnsList = null;
	private ArrayAdapter<String> adapterlistViewSeriesColumnsList = null;
	private Button m_ButtonSeriesColumnsProperty = null;
	
	public SeriesColumnsProperty m_DlgSeriesColumnsProperty = null;
	
	
	public SeriesProperty(Context context, Chart esChart) {
		super(context);
		
		m_Chart = esChart;
		m_pParent = context;
	}

	public SeriesProperty(Context context, int theme, Chart esChart) {
		super(context, theme);
		
		m_Chart = esChart;
		m_pParent = context;
	}

	public SeriesProperty(Context context, boolean cancelable,
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
        setContentView(R.layout.series_property_dialog);
        
        
        this.getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
        
        m_ColumnPropertyList = new SeriesColumnList();
        
		m_ColorPalette = new ColorPalette(m_pParent, m_Chart);
		m_ColorPalette.requestWindowFeature(Window.FEATURE_NO_TITLE);
		m_ColorPalette.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		
		radioGroupSeriesProperty = (RadioGroup)findViewById(R.id.radio_series_main_property);
		if (radioGroupSeriesProperty != null) radioGroupSeriesProperty.setOnCheckedChangeListener(this);

		adapterlistViewSeriesType = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, m_ColumnPropertyList.labelList);
		
		listViewSeriesTypeColumns = (ListView)findViewById(R.id.listview_series_property_type_column);
		if (listViewSeriesTypeColumns != null)
		{
			listViewSeriesTypeColumns.setAdapter(adapterlistViewSeriesType);
			listViewSeriesTypeColumns.setOnItemClickListener(m_ListViewitemClickListener);
			listViewSeriesTypeColumns.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
		radioGroupSeriesPropertyType =(RadioGroup)findViewById(R.id.radio_series_property_type);
		if (radioGroupSeriesPropertyType != null) radioGroupSeriesPropertyType.setOnCheckedChangeListener(this);
		
		adapterlistViewSeriesOrder = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, m_ColumnPropertyList.labelList);
		
		listViewSeriesOrderColumns = (ListView)findViewById(R.id.listview_series_property_order_column);
		if (listViewSeriesOrderColumns != null)
		{
			listViewSeriesOrderColumns.setAdapter(adapterlistViewSeriesOrder);
			listViewSeriesOrderColumns.setOnItemClickListener(m_ListViewitemClickListener);
			listViewSeriesOrderColumns.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
		m_ButtonSeriesOrderUp = (Button)findViewById(R.id.button_series_property_order_up);
        if (m_ButtonSeriesOrderUp != null) m_ButtonSeriesOrderUp.setOnClickListener(this);
        
		m_ButtonSeriesOrderDown = (Button)findViewById(R.id.button_series_property_order_down);
        if (m_ButtonSeriesOrderDown != null) m_ButtonSeriesOrderDown.setOnClickListener(this);
        
        m_ButtonSeriesOrderStack = (Button)findViewById(R.id.button_series_property_order_stack);
        if (m_ButtonSeriesOrderStack != null) m_ButtonSeriesOrderStack.setOnClickListener(this);
        
        m_ButtonSeriesOrderUnstack = (Button)findViewById(R.id.button_series_property_order_unstack);
        if (m_ButtonSeriesOrderUnstack != null) m_ButtonSeriesOrderUnstack.setOnClickListener(this);
        
        adapterlistViewSeriesColumnsList = new ArrayAdapter<String>(this.m_pParent, android.R.layout.simple_list_item_single_choice, m_ColumnPropertyList.labelList);
		
        listViewSeriesColumnsList = (ListView)findViewById(R.id.listview_series_property_columns_column);
		if (listViewSeriesColumnsList != null)
		{
			listViewSeriesColumnsList.setAdapter(adapterlistViewSeriesColumnsList);
			listViewSeriesColumnsList.setOnItemClickListener(m_ListViewitemClickListener);
			listViewSeriesColumnsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		
		m_ButtonSeriesColumnsProperty = (Button)findViewById(R.id.button_series_property_columns_property);
        if (m_ButtonSeriesColumnsProperty != null) m_ButtonSeriesColumnsProperty.setOnClickListener(this);

        m_DlgSeriesColumnsProperty = new SeriesColumnsProperty(m_pParent, m_Chart);
        m_DlgSeriesColumnsProperty.requestWindowFeature(Window.FEATURE_NO_TITLE);
        m_DlgSeriesColumnsProperty.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
	
	@Override
	public void onClick(View v)
	{
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
		case R.id.series_main_property_columns:

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

	public void updateBackupData(boolean bUpdatData)
	{
		if (bUpdatData)
		{
			String strColumn = m_pParent.getString(R.string.chart_property_series_column);
			int nCount = m_Chart.getSeriesColCount();
			for (int i=0; i<nCount; i++)
			{
				String strLabel = m_Chart.getSeriesColLabel(i);
				if (strLabel.isEmpty())
				{
					int n = i+1;
					strLabel = strColumn + " "+ n; 
				}
				m_ColumnPropertyList.AddLabel(strLabel);
				
				
				SeriesColumn scol = new SeriesColumn();
				scol.order = m_Chart.getSeriesOrder(i);
				m_ColumnPropertyList.AddColumn(scol);
			}
			
			m_ColumnPropertyList.AddLabel(m_pParent.getString(R.string.chart_property_series_allcolumns));
			adapterlistViewSeriesType.notifyDataSetChanged();
			adapterlistViewSeriesOrder.notifyDataSetChanged();
		}
		else
		{
		}
	}
	
	public void InitCtrl()
	{
		m_ColumnPropertyList.curIndex = 0;
		listViewSeriesTypeColumns.setItemChecked(m_ColumnPropertyList.curIndex, true);
		updateSeriesTypeControl(m_ColumnPropertyList.curIndex);
		listViewSeriesOrderColumns.setItemChecked(m_ColumnPropertyList.curIndex, true);
		updateSeriesOrderControl(m_ColumnPropertyList.curIndex);
		listViewSeriesColumnsList.setItemChecked(m_ColumnPropertyList.curIndex, true);
		updateSeriesColumnsControl(m_ColumnPropertyList.curIndex);
		
		radioGroupSeriesProperty.check(R.id.series_main_property_columns);
		showProperty(R.id.series_main_property_columns);
	}
	
	public void InitBackupData()
	{
		m_ColumnPropertyList.Init();
	}
	
	protected void showProperty(int nId)
	{
		m_nActiveTabID = nId;
		
		RelativeLayout relativeLayoutSeriesPropertyType = (RelativeLayout)findViewById(R.id.relative_layout_series_property_type);
		relativeLayoutSeriesPropertyType.setVisibility(View.INVISIBLE);
		
		RelativeLayout relativeLayoutSeriesPropertyOrder = (RelativeLayout)findViewById(R.id.relative_layout_series_property_order);
		relativeLayoutSeriesPropertyOrder.setVisibility(View.INVISIBLE);

		RelativeLayout relativeLayoutSeriesPropertyColumns = (RelativeLayout)findViewById(R.id.relative_layout_series_property_columns);
		relativeLayoutSeriesPropertyColumns.setVisibility(View.INVISIBLE);

		switch (nId)
		{
		case R.id.series_main_property_columns:
			relativeLayoutSeriesPropertyColumns.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}


	public void updateChart(int nId)
	{
		switch (m_nActiveTabID)
		{
		case R.id.series_main_property_columns:
			m_DlgSeriesColumnsProperty.show();
			((EssenceChartActivity)m_pParent).repositionPropertyDialog();
			m_DlgSeriesColumnsProperty.SetCurrentColumn(m_ColumnPropertyList.curIndex);
			break;

		default:
			break;
		}
	}
	public void updateChartSeriesType(int nId)
	{
		switch (nId)
		{
		case R.id.series_property_column_type_bar:
			break;

		case R.id.series_property_column_type_hilo:
			break;

		default:
			break;
		}
	}
	public void updateChartSeriesOrder(int nId)
	{
		switch (nId)
		{
		case R.id.button_series_property_order_up:
		{
			int nOrder = m_Chart.getSeriesOrder(m_ColumnPropertyList.curIndex);
			m_Chart.setSeriesOrder(m_ColumnPropertyList.curIndex, nOrder-1);
		}
			break;
			
		case R.id.button_series_property_order_down:
		{
			int nOrder = m_Chart.getSeriesOrder(m_ColumnPropertyList.curIndex);
			m_Chart.setSeriesOrder(m_ColumnPropertyList.curIndex, nOrder+1);
		}
			break;
			
		case R.id.button_series_property_order_stack:
			break;
			
		case R.id.button_series_property_order_unstack:
			break;
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
		case R.id.series_main_property_columns:
			updateSeriesColumnsControl(nValue);
			break;

		default:
			break;
		}
	}
	public void updateSeriesTypeControl(int nCol)
	{
		if (m_ColumnPropertyList.curIndex != nCol)
		{
			m_ColumnPropertyList.curIndex = nCol;
		}
	}
	public void updateSeriesOrderControl(int nCol)
	{
		if (m_ColumnPropertyList.curIndex != nCol)
		{
			m_ColumnPropertyList.curIndex = nCol;
		}
	}
	public void updateSeriesColumnsControl(int nCol)
	{
		if (m_ColumnPropertyList.curIndex != nCol)
		{
			m_ColumnPropertyList.curIndex = nCol;
		}
		
		ViewGroup.LayoutParams paramListView = listViewSeriesColumnsList.getLayoutParams();
		paramListView.height = m_ColumnPropertyList.labelList.size() * HEIGHT_LISTVIEW_ITEM;
		listViewSeriesColumnsList.setLayoutParams(paramListView);
	}

	
	public int getColumnTypeCtrlId(int nType)
	{
		int nId = 0;
		if (COLUMN_TYPE_BAR == nType) nId = R.id.series_property_column_type_bar;
		else if (COLUMN_TYPE_HILOBAR == nType) nId = R.id.series_property_column_type_hilo;

		return nId;
	}
	public int getColumnOrderCtrlId(int nType)
	{
		int nId = 0;
		if (COLUMN_ORDER_UP == nType) nId = R.id.button_series_property_order_up;
		else if (COLUMN_ORDER_DOWN == nType) nId = R.id.button_series_property_order_down;
		else if (COLUMN_ORDER_STACK == nType) nId = R.id.button_series_property_order_stack;
		else if (COLUMN_ORDER_UNSTACK == nType) nId = R.id.button_series_property_order_unstack;

		return nId;
	}
	
}
