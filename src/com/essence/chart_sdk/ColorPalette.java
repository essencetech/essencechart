package com.essence.chart_sdk;

import com.essence.chart.Chart;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.essence.chart_sdk.EssenceChartActivity;

public class ColorPalette extends Dialog implements android.view.View.OnClickListener {

	public final int COUNT_COLOR = 24;
	
	private int m_Color[] =
	{
		0xffffffff, 0xffffea74, 0xfff4bace, 0xffffab7b, 0xffc5e0ff, 0xffdaecae,
		0xffdedede, 0xfff3c72a, 0xfff1798a, 0xffff8947, 0xff95bfee, 0xffbad872,
		0xff7f7f7f, 0xffeda500, 0xffc53447, 0xfff17730, 0xff6499d4, 0xff97bc3d,
        0xff000000, 0xffd47d00, 0xff930d20, 0xffc54e0a, 0xff4771a0, 0xff5c7d0a,
	};
	
	private Button m_ButtonColor[];
	
	private int m_nPropertyType;
	private int m_nCurrentColumn;
	private int m_nCurrentRow;
	private int m_nControlId;
	
	private Chart m_Chart = null;
	
	Context m_pParent = null;
	
	public ColorPalette(Context context, Chart esChart) {
		super(context);
		
		m_pParent = context;
		m_Chart = esChart;
	}

	public ColorPalette(Context context, int theme, Chart esChart) {
		super(context, theme);
		
		m_pParent = context;
		m_Chart = esChart;
	}

	public ColorPalette(Context context, boolean cancelable,
			OnCancelListener cancelListener, Chart esChart) {
		super(context, cancelable, cancelListener);
		
		m_pParent = context;
		m_Chart = esChart;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		if (m_Chart == null)
		{
			return;
		}
        
        setContentView(R.layout.colorpalette);
        
        this.getWindow().setGravity(Gravity.RIGHT|Gravity.BOTTOM);
        
        m_ButtonColor = new Button[COUNT_COLOR];
        for (int i=0; i<COUNT_COLOR; i++)
        {
        	m_ButtonColor[i] = (Button)findViewById(R.id.button_color_00 + i);
        	m_ButtonColor[i].setOnClickListener(this);
        	
        	m_ButtonColor[i].setBackgroundColor(m_Color[i]);
        }
    }

	@Override
	public void onClick(View v) {
		
		if (null == v)
		{
			return;
		}
		
		int nId = v.getId();
		
		if (nId >= R.id.button_color_00 &&
				nId <= R.id.button_color_23)
		{
			updateColor(nId);
			this.dismiss();
		}
	}
	
	public void updateColor(int nId)
	{
		int nColor = getColor(nId);
		switch (m_nControlId)
		{
		case R.id.button_chart_property_fill_type1_fillcolor_preview:
			if (R.id.button_chart_property_title == m_nPropertyType)
			{
				m_Chart.setTitleBackdropFillColor(nColor);
			}
			else if (R.id.button_chart_property_xaxis == m_nPropertyType)
			{
				m_Chart.setXAxisTitleBackdropFillColor(nColor);
			}
			else if (R.id.button_chart_property_yaxis == m_nPropertyType)
			{
				m_Chart.setYAxisTitleBackdropFillColor(nColor);
			}
			else if (R.id.button_chart_property_y2axis == m_nPropertyType)
			{
				m_Chart.setY2AxisTitleBackdropFillColor(nColor);
			}
			else if (R.id.button_chart_property_zaxis == m_nPropertyType)
			{
				m_Chart.setZAxisTitleBackdropFillColor(nColor);
			}
			((EssenceChartActivity)m_pParent).m_DlgChartProperty.SetColorPreview(nColor, m_nControlId);
			break;
			
		case R.id.button_chart_property_fill_type1_edgecolor_preview:
			if (R.id.button_chart_property_title == m_nPropertyType)
			{
				m_Chart.setTitleBackdropFrameColor(nColor);
			}
			else if (R.id.button_chart_property_xaxis == m_nPropertyType)
			{
				m_Chart.setXAxisTitleBackdropFrameColor(nColor);
			}
			else if (R.id.button_chart_property_yaxis == m_nPropertyType)
			{
				m_Chart.setYAxisTitleBackdropFrameColor(nColor);
			}
			else if (R.id.button_chart_property_y2axis == m_nPropertyType)
			{
				m_Chart.setY2AxisTitleBackdropFrameColor(nColor);
			}
			else if (R.id.button_chart_property_zaxis == m_nPropertyType)
			{
				m_Chart.setZAxisTitleBackdropFrameColor(nColor);
			}
			((EssenceChartActivity)m_pParent).m_DlgChartProperty.SetColorPreview(nColor, m_nControlId);
			break;
			
		case R.id.button_chart_property_fill_type2_fillcolor_preview:
			if (R.id.button_chart_property_legend == m_nPropertyType)
			{
				m_Chart.setLegendBackdropFillColor(nColor);
			}
			else if (R.id.button_chart_property_backdrop == m_nPropertyType)
			{
				m_Chart.setChartBackdropFillColor(nColor);
			}
			else if (R.id.button_chart_property_plotbackdrop == m_nPropertyType)
			{
				m_Chart.setPlotFillColor(nColor);
			}
			((EssenceChartActivity)m_pParent).m_DlgChartProperty.SetColorPreview(nColor, m_nControlId);
			break;
			
		case R.id.button_chart_property_font_color_preview:
			if (R.id.button_chart_property_title == m_nPropertyType)
			{
				m_Chart.setTitleFontColor( nColor );
			}
			else if(R.id.button_chart_property_legend == m_nPropertyType)
			{
				m_Chart.setLegendFontColor( nColor );
			}
			else if(R.id.button_chart_property_xaxis == m_nPropertyType)
			{
				m_Chart.setXAxisTitleFontColor( nColor );
			}
			else if(R.id.button_chart_property_yaxis == m_nPropertyType)
			{
				m_Chart.setYAxisTitleFontColor( nColor );
			}
			else if(R.id.button_chart_property_y2axis == m_nPropertyType)
			{
				m_Chart.setY2AxisTitleFontColor( nColor );
			}
			else if(R.id.button_chart_property_zaxis == m_nPropertyType)
			{
				m_Chart.setZAxisTitleFontColor( nColor );
			}
			
			((EssenceChartActivity)m_pParent).m_DlgChartProperty.SetColorPreview(nColor, m_nControlId);
			break;

		case R.id.button_chart_property_backdropframe_color_preview:
			if (R.id.button_chart_property_legend == m_nPropertyType)
			{
				m_Chart.setLegendBackdropColor( nColor );
			}
			else if (R.id.button_chart_property_backdrop == m_nPropertyType)
			{
				m_Chart.setChartBackdropColor( nColor );
			}
			
			((EssenceChartActivity)m_pParent).m_DlgChartProperty.SetColorPreview(nColor, m_nControlId);
			break;
			
		case R.id.button_chart_property_plotbackdropframe_color_preview:
			m_Chart.setPlotBackdropColor( nColor );
			((EssenceChartActivity)m_pParent).m_DlgChartProperty.SetColorPreview(nColor, m_nControlId);
			break;
			
		case R.id.button_series_columns_property_options_gaincolor_preview:
			if (0 > m_nCurrentColumn)
			{
				int nCount = m_Chart.getSeriesColCount();
				for (int i=0; i<nCount; i++)
				{
					m_Chart.setSeriesOptionSeriesHiLoGainColor(i, nColor);
				}
			}
			else
			{
				m_Chart.setSeriesOptionSeriesHiLoGainColor(m_nCurrentColumn, nColor);
			}
			
			((EssenceChartActivity)m_pParent).m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.SetColorPreview(nColor, m_nControlId);
			break;
			
		case R.id.button_series_columns_property_options_losscolor_preview:
			if (0 > m_nCurrentColumn)
			{
				int nCount = m_Chart.getSeriesColCount();
				for (int i=0; i<nCount; i++)
				{
					m_Chart.setSeriesOptionSeriesHiLoLossColor(i, nColor);
				}
			}
			else
			{
				m_Chart.setSeriesOptionSeriesHiLoLossColor(m_nCurrentColumn, nColor);
			}
			
			((EssenceChartActivity)m_pParent).m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.SetColorPreview(nColor, m_nControlId);
			break;
			
		case R.id.button_series_datapointlabels_property_fill_fillcolor_preview:
		{
			int nCountCol = m_Chart.getSeriesColCount();
			int nCountRow = m_Chart.getSeriesRowCount();
			int iCol = 0;
			int iRow = 0;
			if (m_Chart.getSeriesColCount() > m_nCurrentColumn && 0 <= m_nCurrentColumn)
			{
				iCol = m_nCurrentColumn;
				nCountCol = m_nCurrentColumn+1;
			}
			if (m_Chart.getSeriesRowCount() > m_nCurrentRow && 0 <= m_nCurrentRow)
			{
				iRow = m_nCurrentRow;
				nCountRow = m_nCurrentRow+1;
			}
			
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointFillColor(col, row, nColor);
				}
			}
			((EssenceChartActivity)m_pParent).m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.m_DlgSeriesDatapointLabelsProperty.SetColorPreview(nColor, m_nControlId);
		}
			break;
			
		case R.id.button_series_datapointlabels_property_fill_edgecolor_preview:
		{
			int nCountCol = m_Chart.getSeriesColCount();
			int nCountRow = m_Chart.getSeriesRowCount();
			int iCol = 0;
			int iRow = 0;
			if (m_Chart.getSeriesColCount() > m_nCurrentColumn && 0 <= m_nCurrentColumn)
			{
				iCol = m_nCurrentColumn;
				nCountCol = m_nCurrentColumn+1;
			}
			if (m_Chart.getSeriesRowCount() > m_nCurrentRow && 0 <= m_nCurrentRow)
			{
				iRow = m_nCurrentRow;
				nCountRow = m_nCurrentRow+1;
			}
			
			for (int col=iCol; col<nCountCol; col++)
			{
				for (int row=iRow; row<nCountRow; row++)
				{
					m_Chart.setSeriesDatapointEdgeColor(col, row, nColor);
				}
			}
			((EssenceChartActivity)m_pParent).m_DlgSeriesProperty.m_DlgSeriesColumnsProperty.m_DlgSeriesDatapointLabelsProperty.SetColorPreview(nColor, m_nControlId);
		}
			break;
			
		default:
			break;
		}	
	}
	
	public void SetPropertyType(int nPropertyType, int nId)
	{
		m_nPropertyType = nPropertyType;
		m_nControlId = nId;
	}
	public void SetCurrentColumn(int nCol, int nId)
	{
		m_nCurrentColumn = nCol;
		m_nControlId = nId;
	}
	public void SetCurrentColumnRow(int nCol, int nRow, int nId)
	{
		m_nCurrentColumn = nCol;
		m_nCurrentRow = nRow;
		m_nControlId = nId;
	}
	
	public int GetWidth()
	{
		return m_ButtonColor[0].getWidth() * 8;
	}
	
	public int GetHeight()
	{
		return m_ButtonColor[0].getHeight() * 3;
	}
	
	public int getColor(int nId)
	{
		return m_Color[nId - R.id.button_color_00];
	}
}
