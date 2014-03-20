package com.essence.chart;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Chart extends View {
	public	int					m_NativeChart;
	private	ChartLibrary		m_Library = null;
	private	ChartCallback		m_Callback = null;
	private	Bitmap				m_Bitmap = null;
	private	Random				m_Random = null;
	private	int					m_nAlpha = 0;
	private	int					m_nAlphaDirection = 1;
	private	float				m_fTouchDownX = 0.0f;
	private float				m_fTouchDownY = 0.0f;
	private	int					m_nChartWidth = 500;
	private	int					m_nChartHeight = 500;
	private	float				m_fRotate = 0.0f;
	private	float				m_fElevation = 0.0f;
	private	boolean				m_bAnimationRender = false;
	private boolean				m_bDataQueue = false;
	private int					m_nOldChartGroup = 0x0000;
	private int					m_nCurChartType = 0;
	
	public final int 			_DMA_DURATION	= 500;
	public final int			_DMA_INTERVAL	= 1000;
	
	static final String			DEFAULT_FONT_NAME = "Droid Sans";
	static final int			DEFAULT_FONT_SIZE = 12;
	static final int			DEFAULT_CHART_WIDTH = 10;
	static final int			DEFAULT_CHART_HEIGHT = 10;
	
	static public final int		Chart_Type_Clustered_Column 						= 0x1111; 
	static public final int		Chart_Type_Stacked_Column	 						= 0x1112; 
	static public final int		Chart_Type_100percent_Stacked_Column	 			= 0x1113; 
	static public final int		Chart_Type_3D_Column	 							= 0x1121; 
	static public final int		Chart_Type_Stacked_Column_in_3D 					= 0x1122; 
	static public final int		Chart_Type_100percent_Stacked_Column_in_3D 			= 0x1123; 
	
	static public final int		Chart_Type_Line			 							= 0x1211; 
	static public final int		Chart_Type_Stacked_Line	 							= 0x1212; 
	static public final int		Chart_Type_Line_with_Markets			 			= 0x1213; 
	static public final int		Chart_Type_3D_Line		 							= 0x1221; 
	
	static public final int		Chart_Type_Pie			 							= 0x1311; 
	static public final int		Chart_Type_Exploded_Pie	 							= 0x1312; 
	static public final int		Chart_Type_3D_Pie		 							= 0x1321; 
	static public final int		Chart_Type_Exploded_pie_in_3D			 			= 0x1322; 
	
	static public final int		Chart_Type_Clustered_Bar 							= 0x1411; 
	static public final int		Chart_Type_Stacked_Bar	 							= 0x1412; 
	static public final int		Chart_Type_100percent_Stacked_Bar		 			= 0x1413; 
	static public final int		Chart_Type_Clustered_Bar_in_3D			 			= 0x1421; 
	static public final int		Chart_Type_Stacked_Bar_in_3D			 			= 0x1422; 
	static public final int		Chart_Type_100percent_Stacked_Bar_in_3D	 			= 0x1423; 
	
	static public final int		Chart_Type_Area			 							= 0x1511; 
	static public final int		Chart_Type_100percent_Stacked_Area		 			= 0x1512; 
	static public final int		Chart_Type_3D_Area		 							= 0x1521; 
	static public final int		Chart_Type_100percent_Stacked_Area_in_3D 			= 0x1522; 
	
	static public final int		Chart_Type_Scatter_with_only_Markers	 			= 0x1611; 
	static public final int		Chart_Type_Scatter_with_Straight_Lines_and_Markers	= 0x1612; 
	static public final int		Chart_Type_Scatter_with_Straight_Lines	 			= 0x1613; 
	
	static public final int		Chart_Type_High_Low_Close 							= 0x1711; 
	static public final int		Chart_Type_Open_High_Low_Close						= 0x1712; 
	
	static public final int		Chart_Type_Contour		 							= 0x1811; 
	static public final int		Chart_Type_3D_Surface	 							= 0x1821; 
	
	static public final int		Chart_Type_3D_Dougnut	 							= 0x1921; 
	static public final int		Chart_Type_Exploded_Dougnut_in_3D		 			= 0x1922; 
	
	static public final int		Chart_Type_Bubble		 							= 0x1a11; 
	
	static public final int		Chart_Type_Radar		 							= 0x1b11; 
	static public final int		Chart_Type_Radar_with_Markers			 			= 0x1b12; 
	
	static public final int		Chart_Type_Combine		 							= 0x1c11; 
	static public final int		Chart_Type_3D_Combine	 							= 0x1c21; 
	
	static public final int		Chart_Type_Unknown		 							= 0xffff; 

	class RowCol
	{
		 public static final int Columns           = 0;
		 public static final int Rows              = 1;
	}
	
	public Chart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		m_Library = new ChartLibrary();
		m_NativeChart = native_init();
		m_Bitmap = Bitmap.createBitmap(m_nChartWidth, m_nChartHeight, Bitmap.Config.ARGB_8888);
		m_Random = new Random();
		m_fRotate = getRotation();
		m_fElevation = getElevation();
		
		setChartType(Chart_Type_3D_Column);
	}

	public Chart(Context context, AttributeSet attrs) {
		super(context, attrs);

		m_Library = new ChartLibrary();
		m_NativeChart = native_init();
		m_Bitmap = Bitmap.createBitmap(m_nChartWidth, m_nChartHeight, Bitmap.Config.ARGB_8888);
		m_Random = new Random();
		m_fRotate = getRotation();
		m_fElevation = getElevation();
		
		setChartType(Chart_Type_3D_Column);
	}

	public Chart(Context context) {
		super(context);

		m_Library = new ChartLibrary();
		m_NativeChart = native_init();
		m_Bitmap = Bitmap.createBitmap(m_nChartWidth, m_nChartHeight, Bitmap.Config.ARGB_8888);
		m_Random = new Random();
		m_fRotate = getRotation();
		m_fElevation = getElevation();
		
		setChartType(Chart_Type_3D_Column);
	}
	
	private void TestInputData_00()
	{
		setTitle("Quarterly stocks");
		
		String[]	strColumns = { "Stock", "Samsung", "Apple", "Google", "LG" };
		String[]	strRows = { "Month", "January", "February", "March", "April" };
		
		InputData(strRows.length, strColumns.length, strColumns, strRows, 2000, null);
	}
	
	private void TestInputData_01()
	{		
		setTitle("Quarterly stocks");
		
		String[]	strColumns = { "Stock", "Samsung", "Apple", "Google", "LG" };
		String[]	strRows = { "Quarter", "1 Quarter", "2 Quarter" };
		
		InputData(strRows.length, strColumns.length, strColumns, strRows, 5000, null);
	}
	
	private void TestInputData_02()
	{		
		setTitle("Stocks per second");
		
		String[]	strColumns = { "Stock", "Samsung", "Apple", "Google" };
		String[]	strRows = { "Second", "30 sec ago", "20 sec ago", "10 sec ago", "Now" };
		
		strRows[1] = ((_DMA_INTERVAL / 1000) * 3) + " sec ago";
		strRows[2] = ((_DMA_INTERVAL / 1000) * 2) + " sec ago";
		strRows[3] = ((_DMA_INTERVAL / 1000) * 1) + " sec ago";
		
		InputData(strRows.length, strColumns.length, strColumns, strRows, 1800, null);
	}
	
	private void TestInputData_03()
	{		
		setTitle("");
		
		String[]	strColumns = { "Area", "Seoul", "Gangwon-do", "Gyeonggi-do", "Jeolla-do" };
		String[]	strRows = { "Division", "People" };
		
		int[][] nFixedValue = {
				{ 19820, 2321, 7903, 5420 }
		};
		
		InputData(strRows.length, strColumns.length, strColumns, strRows, 0, nFixedValue);
	}
	
	private void TestInputData_04()
	{		
		setTitle("Quarterly stocks");
		
		String[]	strColumns = { "Stock", "Samsung", "Apple" };
		String[]	strRows = { "Quarter", "1 Quarter", "2 Quarter", "3 Quarter", "4 Quarter" };
		
		InputData(strRows.length, strColumns.length, strColumns, strRows, 4800, null);
	}
	
	private void TestInputData_05()
	{		
		setTitle("Monthly stocks");
		
		String[]	strColumns = { "Stock", "Samsung", "Apple", "LG" };
		String[]	strRows = { "Month", "January", "February", "March" };
		
		InputData(strRows.length, strColumns.length, strColumns, strRows, 3000, null);
	}
	
	private void TestInputData_06()
	{		
		setTitle("Number of employees by number of A/S");
		
		String[]	strColumns = { "", "Samsung", "", "LG", "" };
		String[]	strRows = { "", "", "", "", "", "" };
		
		int[][] nFixedValue = new int[5][4];
		nFixedValue[0][0] = 10;
		nFixedValue[0][1] = 64;
		nFixedValue[0][2] = 10;
		nFixedValue[0][3] = 53;
		
		nFixedValue[1][0] = 20;
		nFixedValue[1][1] = 134;
		nFixedValue[1][2] = 20;
		nFixedValue[1][3] = 110;
		
		nFixedValue[2][0] = 50;
		nFixedValue[2][1] = 280;
		nFixedValue[2][2] = 50;
		nFixedValue[2][3] = 277;
		
		nFixedValue[3][0] = 70;
		nFixedValue[3][1] = 320;
		nFixedValue[3][2] = 70;
		nFixedValue[3][3] = 337;
		
		nFixedValue[4][0] = 150;
		nFixedValue[4][1] = 421;
		nFixedValue[4][2] = 150;
		nFixedValue[4][3] = 445;
		
		InputData(strRows.length, strColumns.length, strColumns, strRows, 0, nFixedValue);
	}
	
	private void TestInputData_07(int nNeededColumns)
	{		
		setTitle("Kospi (in thousands)");
		
		String[]	strColumns = { "", "Kospi", "", "", "" };
		String[]	strRows = { "", "SEC", "Hyundai-car", "POSCO", "Mobis" };
		
		int[][] nFixedValue = {
				{ 1324, 1428, 1526, 1587 },
				{ 198, 230, 128, 110 },
				{ 314, 398, 350, 289 },
				{ 257, 268, 288, 280 }
		};
		
		
		InputData(strRows.length, nNeededColumns + 1, strColumns, strRows, 0, nFixedValue);
	}
	
	private void TestInputData_08()
	{		
		setTitle("Tree count by mountain height");
		
		String[]	strColumns = { "Height", "200m", "400m", "600m", "800m" };
		String[]	strRows = { "Tree", "Cherry blossom", "Acorn", "Pine", "White pine" };
		
		int[][] nFixedValue = {
				{ 120, 87, 43, 24 },
				{ 18, 34, 87, 121 },
				{ 21, 83, 320, 240 },
				{ 32, 65, 82, 24 }
		};
		
		
		InputData(strRows.length, strColumns.length, strColumns, strRows, 0, nFixedValue);
	}
	
	private void TestInputData_09()
	{		
		setTitle("Sales / income / recall (Nokia)");
		
		String[]	strColumns = { "", "Nokia", "", "" };
		String[]	strRows = { "Sales", "100", "200", "300", "400" };
		
		int[][] nFixedValue = {
				{ 100, 740, 10 },
				{ 200, 1430, 20 },
				{ 300, 2200, 30 },
				{ 400, 2800, 50 }
		};
		
		InputData(strRows.length, strColumns.length, strColumns, strRows, 0, nFixedValue);
	}
	
	private void TestInputData_10()
	{		
		setTitle("Quarterly tourism visitors");
		
		String[]	strColumns = { "Visitors", "Korea", "China", "Japan" };
		String[]	strRows = { "Quarter", "1 Quarter", "2 Quarter", "3 Quarter", "4 Quarter" };
		
		int[][] nFixedValue = {
				{ 804, 1200, 983 },
				{ 765, 1824, 990 },
				{ 897, 1687, 1101 },
				{ 240, 1100, 1300 }
		};
		
		InputData(strRows.length, strColumns.length, strColumns, strRows, 0, nFixedValue);
	}
	
	private void TestInputData_11(int nNeededColumns)
	{
		setTitle("Monthly stocks");
		
		String[]	strColumns = { "Stock", "Samsung", "Apple", "Google", "LG", "MS" };
		String[]	strRows = { "Month", "January", "February", "March", "April", "May" };
		
		InputData(strRows.length, nNeededColumns + 1, strColumns, strRows, 2000, null);
	}
	
	private void InputData(int nRows, int nColumns, String[] strColumns, String[] strRows, int nRandomMax, int[][] nFixedValue)
	{
		GridData	gridData = new GridData(nRows, nColumns);
		
		for (int nRow = 0; nRow < nRows; nRow++)
		{
			for(int nColumn = 0; nColumn < nColumns; nColumn++)
			{
				if (nRow == 0)
				{
					gridData.setCell(nRow, nColumn, strColumns[nColumn]);
				}
				else if (nColumn == 0)
				{
					gridData.setCell(nRow, nColumn, strRows[nRow]);
				}
				else
				{
					double dValue = 0.0;
					if (nFixedValue == null)
					{
						dValue = m_Random.nextInt(nRandomMax);
					}
					else
					{
						dValue = nFixedValue[nRow - 1][nColumn - 1];
					}
					gridData.setCell(nRow, nColumn, dValue);
				}
			}
		}
		
		setSourceData(gridData, RowCol.Columns);
	}
	
	private boolean IsDifferentChartGroup(int type)
	{
		type = (type >> 8) << 8;
		if (m_nOldChartGroup == type)
		{
			if (type == 0x1700 || type == 0x1c00)
			{
				return true;
			}
			return false;
		}
		
		m_nOldChartGroup = type;
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint	paint = new Paint();
		paint.setARGB(0xff, 0xff, 0x00, 0x00);
		
		m_Bitmap = Bitmap.createBitmap(m_nChartWidth, m_nChartHeight, Bitmap.Config.ARGB_8888);
		
		native_draw(m_NativeChart, m_Bitmap);
		
		m_nAlpha += m_nAlphaDirection;
		if (m_nAlpha >= 255)
		{
			m_nAlphaDirection = -10;
			m_nAlpha = 255;
		}
		if (m_nAlpha < 0)
		{
			m_nAlphaDirection = 10;
			m_nAlpha = 0;
		}
		
		canvas.drawBitmap(m_Bitmap, 0, 0, paint);
		
		if (m_bAnimationRender || m_bDataQueue)
		{
			postInvalidate();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        
        if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
        	m_nChartWidth = widthSize;
        }
        else {
        	m_nChartWidth = DEFAULT_CHART_WIDTH;
        }
        
        if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST) {
        	m_nChartHeight = heightSize;
        }
        else {
   			m_nChartHeight = DEFAULT_CHART_HEIGHT;
        }
        
        if (m_nChartWidth < DEFAULT_CHART_WIDTH || m_nChartWidth < DEFAULT_CHART_HEIGHT)
		{
			m_nChartWidth = DEFAULT_CHART_WIDTH;
			m_nChartHeight = DEFAULT_CHART_HEIGHT;
		}

		setMeasuredDimension(m_nChartWidth, m_nChartHeight); 
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean	bTouchEvent = false;
		
		if (m_Callback != null)
		{
			boolean bResult = m_Callback.onTouchEventBefore(event);
			
			if (bResult == true)
			{
				return true;
			}
		}
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			m_fTouchDownX = event.getX();
			m_fTouchDownY = event.getY();
			bTouchEvent =  true;
		}
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			m_fRotate = getRotation();
			m_fElevation = getElevation();
			bTouchEvent =  true;
		}
		else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float	fRotation = m_fRotate + ((event.getX() - m_fTouchDownX) / 5);
			float	fElevation = m_fElevation + ((event.getY() - m_fTouchDownY) / 5);
			
			if (fRotation < 0.0f)
			{
				fRotation = 0.0f;
			}
			else if (fRotation > 360.0f)
			{
				fRotation = 360.0f;
			}
			
			if (fElevation < -90.0f)
			{
				fElevation = -90.0f;
			}
			else if (fElevation > 90.0f)
			{
				fElevation = 90.0f;
			}
			
			setRotation(fRotation);
			setElevation(fElevation);
		     
			postInvalidate();
			
			bTouchEvent = true;
		}
		
		if (m_Callback != null)
		{
			boolean bResult = m_Callback.onTouchEventAfter(event);
			
			if (bResult == true)
			{
				return true;
			}
		}
		
		if (bTouchEvent)
		{
			return true;
		}
		
		return super.onTouchEvent(event);
	}
	
	 @Override
	 protected void finalize() throws Throwable {
		 try {
			 finalizer(m_NativeChart);
		 } finally {
			 super.finalize();
		 }
	 }
	 
	 public void setCallback(ChartCallback callback)
	 {
		 m_Callback = callback;
	 }
	 
	 
	 public	int	getChartType() {
		 return native_getChartType(m_NativeChart);
	 }
	 
	 public void setChartType(int type) {
		 
		 if (IsDifferentChartGroup(type) == true)
		 {
			 switch (type)
			 {
				 case Chart_Type_Clustered_Column:			
				 case Chart_Type_Stacked_Column:	 		
				 case Chart_Type_100percent_Stacked_Column:	 	
				 case Chart_Type_3D_Column:	 			
				 case Chart_Type_Stacked_Column_in_3D: 		
				 case Chart_Type_100percent_Stacked_Column_in_3D:
				 {
					 TestInputData_01();
				 }
				 break;
				 
				 case Chart_Type_Line:			
				 case Chart_Type_Stacked_Line:	 	
				 case Chart_Type_Line_with_Markets:	
				 case Chart_Type_3D_Line:		
				 {
					 TestInputData_02();
				 }
				 break;
				 
				 case Chart_Type_Pie:			
				 case Chart_Type_Exploded_Pie:	 	
				 case Chart_Type_3D_Pie:		
				 case Chart_Type_Exploded_pie_in_3D:
				 case Chart_Type_3D_Dougnut:	 		
				 case Chart_Type_Exploded_Dougnut_in_3D:
				 {
					 TestInputData_03();
				 }
				 break;
				 
				 case Chart_Type_Clustered_Bar: 		
				 case Chart_Type_Stacked_Bar:	 		
				 case Chart_Type_100percent_Stacked_Bar:	
				 case Chart_Type_Clustered_Bar_in_3D:		
				 case Chart_Type_Stacked_Bar_in_3D:		
				 case Chart_Type_100percent_Stacked_Bar_in_3D:
				 {
					 TestInputData_04();
				 }
				 break;
				 
				 case Chart_Type_Area:			 	
				 case Chart_Type_100percent_Stacked_Area:	
				 case Chart_Type_3D_Area:		 	
				 case Chart_Type_100percent_Stacked_Area_in_3D:
				 {
					 TestInputData_05();
				 }
				 break;
				 
				 case Chart_Type_Scatter_with_only_Markers:	 	
				 case Chart_Type_Scatter_with_Straight_Lines_and_Markers:
				 case Chart_Type_Scatter_with_Straight_Lines:	 	
				 {
					 TestInputData_06();
				 }
				 break;
				 
				 case Chart_Type_High_Low_Close: 	
				 {
					 TestInputData_07(3);
				 }
				 break;
				 
				 case Chart_Type_Open_High_Low_Close:
				 {
					 TestInputData_07(4);
				 }
				 break;
				 
				 case Chart_Type_Contour:	
				 case Chart_Type_3D_Surface:
				 {
					 TestInputData_08();
				 }
				 break;
				 
				 case Chart_Type_Bubble:
				 {
					 TestInputData_09();
				 }
				 break;
				 
				 case Chart_Type_Radar:		
				 case Chart_Type_Radar_with_Markers:
				 {
					 TestInputData_10();
				 }
				 break;
				 
				 case Chart_Type_Combine:	
				 {
					 TestInputData_11(4);
				 }
				 break;
				 
				 case Chart_Type_3D_Combine:
				 {
					 TestInputData_11(3);
				 }
				 break;

				 
				 default:
				 {
					 TestInputData_00();
				 }
				 break;
			 }
		 }
		 
		 native_setChartType(m_NativeChart, type);
		 
		 m_nCurChartType = type;
	 }
	 
	 public void setSourceData(GridData gridData, int plotBy)
	 {
		 native_setSourceData(m_NativeChart, gridData, plotBy);
	 }
	 
	 public void setFont(String strFont, int size)
	 {
		 native_setFont(m_NativeChart, strFont, size);
	 }
	 
	 public int getColorTemplateCount()
	 {
		 return native_getColorTemplateCount(m_NativeChart);
	 }
	 
	 public int getColorTemplate()
	 {
		 return native_getColorTemplate(m_NativeChart);
	 }
	 
	 public void setColorTemplate(int index)
	 {
		 native_setColorTemplate(m_NativeChart, index); 
	 }
	 
	 public boolean isLegendVisible()
	 {
		 return native_isLegendVisible(m_NativeChart);
	 }
	 
	 public void setLegendVisible(boolean visible)
	 {
		 native_setLegendVisible(m_NativeChart, visible);
	 }
	 public void setLegendLocation(int nLocation)
	 {
		 native_setLegendLocation(m_NativeChart, nLocation);
		 invalidate();
	 }
	 public int getLegendLocation()
	 {
		 return native_getLegendLocation(m_NativeChart);
	 }

	 public void setLegendFontName(String name)
	 {
		 native_setLegendFontName(m_NativeChart, name);
		 invalidate();
	 }
	 public String getLegendFontName()
	 {
		 return native_getLegendFontName(m_NativeChart);
	 }

	 public void setLegendFontSize(int nSize)
	 {
		 native_setLegendFontSize(m_NativeChart, nSize);
		 invalidate();
	 }
	 public int getLegendFontSize()
	 {
		 return native_getLegendFontSize(m_NativeChart);
	 }

	 public void setLegendFontStyle(int nStyle)
	 {
		 native_setLegendFontStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getLegendFontStyle()
	 {
		 return native_getLegendFontStyle(m_NativeChart);
	 }

	 public void setLegendFontColor(int nColor)
	 {
		 native_setLegendFontColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getLegendFontColor()
	 {
		 return native_getLegendFontColor(m_NativeChart);
	 }
	 
	 public void setLegendFontStrikeout(boolean bUse)
	 {
		 native_setLegendFontStrikeout(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getLegendFontStrikeout()
	 {
		 return native_getLegendFontStrikeout(m_NativeChart);
	 }

	 public void setLegendFontUnderline(boolean bUse)
	 {
		 native_setLegendFontUnderline(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getLegendFontUnderline()
	 {
		 return native_getLegendFontUnderline(m_NativeChart);
	 }

	 public void setLegendBackdropStyle(int nStyle)
	 {
		 native_setLegendBackdropStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getLegendBackdropStyle()
	 {
		 return native_getLegendBackdropStyle(m_NativeChart);
	 }

	 public void setLegendBackdropColor(int nColor)
	 {
		 native_setLegendBackdropColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getLegendBackdropColor()
	 {
		 return native_getLegendBackdropColor(m_NativeChart);
	 }
	 
	 public void setLegendPosition(float fX, float fY, float fWidth, float fHeight)
	 {
		 native_setLegendPosition(m_NativeChart, fX, fY, fWidth, fHeight);
		 invalidate();
	 }
	 public float getLegendPositionX()
	 {
		 return native_getLegendPositionX(m_NativeChart);
	 }
	 public float getLegendPositionY()
	 {
		 return native_getLegendPositionY(m_NativeChart);
	 }
	 public float getLegendPositionWidth()
	 {
		 return native_getLegendPositionWidth(m_NativeChart);
	 }
	 public float getLegendPositionHeight()
	 {
		 return native_getLegendPositionHeight(m_NativeChart);
	 }
	 
	 public void setLegendBackdropFillStyle(int nStyle)
	 {
		 native_setLegendBackdropFillStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getLegendBackdropFillStyle()
	 {
		 return native_getLegendBackdropFillStyle(m_NativeChart);
	 }
	 public void setLegendBackdropFillColor(int nColor)
	 {
		 native_setLegendBackdropFillColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getLegendBackdropFillColor()
	 {
		 return native_getLegendBackdropFillColor(m_NativeChart);
	 }
	 
	 public int getChartBackdropStyle()
	 {
		 return native_getChartBackdropStyle(m_NativeChart);
	 }
	 public void setChartBackdropStyle(int nStyle)
	 {
		 native_setChartBackdropStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getChartBackdropColor()
	 {
		 return native_getChartBackdropColor(m_NativeChart);
	 }
	 public void setChartBackdropColor(int nColor)
	 {
		 native_setChartBackdropColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public void setChartBackdropPicture(String strName)
	 {
		 native_setChartBackdropPicture(m_NativeChart, strName);
		 invalidate();
	 }
	 public String getChartBackdropPicture()
	 {
		 return native_getChartBackdropPicture(m_NativeChart);
	 }
	 public void setChartBackdropPictureDrawStyle(int nStyle)
	 {
		 native_setChartBackdropPictureDrawStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getChartBackdropPictureDrawStyle()
	 {
		 return native_getChartBackdropPictureDrawStyle(m_NativeChart);
	 }
	 
	 public void setChartBackdropFillStyle(int nStyle)
	 {
		 native_setChartBackdropFillStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getChartBackdropFillStyle()
	 {
		 return native_getChartBackdropFillStyle(m_NativeChart);
	 }
	 public void setChartBackdropFillColor(int nColor)
	 {
		 native_setChartBackdropFillColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getChartBackdropFillColor()
	 {
		 return native_getChartBackdropFillColor(m_NativeChart);
	 }

	 public int getPlotBackdropStyle()
	 {
		 return native_getPlotBackdropStyle(m_NativeChart);
	 }
	 public void setPlotBackdropStyle(int nStyle)
	 {
		 native_setPlotBackdropStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getPlotBackdropColor()
	 {
		 return native_getPlotBackdropColor(m_NativeChart);
	 }
	 public void setPlotBackdropColor(int nColor)
	 {
		 native_setPlotBackdropColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public void setPlotBackdropPicture(String strName)
	 {
		 native_setPlotBackdropPicture(m_NativeChart, strName);
		 invalidate();
	 }
	 public String getPlotBackdropPicture()
	 {
		 return native_getPlotBackdropPicture(m_NativeChart);
	 }
	 public void setPlotBackdropPictureDrawStyle(int nStyle)
	 {
		 native_setPlotBackdropPictureDrawStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getPlotBackdropPictureDrawStyle()
	 {
		 return native_getPlotBackdropPictureDrawStyle(m_NativeChart);
	 }
	 
	 public void setPlotPosition(float fX, float fY, float fWidth, float fHeight)
	 {
		 native_setPlotPosition(m_NativeChart, fX, fY, fWidth, fHeight);
		 invalidate();
	 }
	 public float getPlotPositionX()
	 {
		 return native_getPlotPositionX(m_NativeChart);
	 }
	 public float getPlotPositionY()
	 {
		 return native_getPlotPositionY(m_NativeChart);
	 }
	 public float getPlotPositionWidth()
	 {
		 return native_getPlotPositionWidth(m_NativeChart);
	 }
	 public float getPlotPositionHeight()
	 {
		 return native_getPlotPositionHeight(m_NativeChart);
	 }
	 
	 public void setPlotFillStyle(int nStyle)
	 {
		 native_setPlotFillStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getPlotFillStyle()
	 {
		 return native_getPlotFillStyle(m_NativeChart);
	 }
	 public void setPlotFillColor(int nColor)
	 {
		 native_setPlotFillColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getPlotFillColor()
	 {
		 return native_getPlotFillColor(m_NativeChart);
	 }
	 
	 public void setTitleText(String strTitle)
	 {
		 setTitle(strTitle);
		 invalidate();
	 }
	 public String getTitleText()
	 {
		 return native_getTitleText(m_NativeChart);
	 }

	 public void setTitleVisible(boolean bVisible)
	 {
		 native_setTitleVisible(m_NativeChart, bVisible);
		 invalidate();
	 }
	 public boolean getTitleVisible()
	 {
		 return native_getTitleVisible(m_NativeChart);
	 }

	 public void setTitleLocation(int nLocation)
	 {
		 native_setTitleLocation(m_NativeChart, nLocation);
		 invalidate();
	 }
	 public int getTitleLocation()
	 {
		 return native_getTitleLocation(m_NativeChart);
	 }

	 public void setTitleFontName(String name)
	 {
		 native_setTitleFontName(m_NativeChart, name);
		 invalidate();
	 }
	 public String getTitleFontName()
	 {
		 return native_getTitleFontName(m_NativeChart);
	 }

	 public void setTitleFontSize(int nSize)
	 {
		 native_setTitleFontSize(m_NativeChart, nSize);
		 invalidate();
	 }
	 public int getTitleFontSize()
	 {
		 return native_getTitleFontSize(m_NativeChart);
	 }

	 public void setTitleFontStyle(int nStyle)
	 {
		 native_setTitleFontStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getTitleFontStyle()
	 {
		 return native_getTitleFontStyle(m_NativeChart);
	 }

	 public void setTitleFontStrikeout(boolean bUse)
	 {
		 native_setTitleFontStrikeout(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getTitleFontStrikeout()
	 {
		 return native_getTitleFontStrikeout(m_NativeChart);
	 }

	 public void setTitleFontUnderline(boolean bUse)
	 {
		 native_setTitleFontUnderline(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getTitleFontUnderline()
	 {
		 return native_getTitleFontUnderline(m_NativeChart);
	 }

	 public void setTitleFontColor(int nColor)
	 {
		 native_setTitleFontColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getTitleFontColor()
	 {
		 return native_getTitleFontColor(m_NativeChart);
	 }

	 public void setTitleBackdropFillStyle(int nStyle)
	 {
		 native_setTitleBackdropFillStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getTitleBackdropFillStyle()
	 {
		 return native_getTitleBackdropFillStyle(m_NativeChart);
	 }
	 public void setTitleBackdropFillColor(int nColor)
	 {
		 native_setTitleBackdropFillColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getTitleBackdropFillColor()
	 {
		 return native_getTitleBackdropFillColor(m_NativeChart);
	 }
	 
	 public void setTitleBackdropFrameStyle(int nStyle)
	 {
		 native_setTitleBackdropFrameStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getTitleBackdropFrameStyle()
	 {
		 return native_getTitleBackdropFrameStyle(m_NativeChart);
	 }
	 public void setTitleBackdropFrameColor(int nColor)
	 {
		 native_setTitleBackdropFrameColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getTitleBackdropFrameColor()
	 {
		 return native_getTitleBackdropFrameColor(m_NativeChart);
	 }
	 
	 public void setXAxisVisible(boolean bVisible)
	 {
		 native_setXAxisVisible(m_NativeChart, bVisible);
		 invalidate();
	 }
	 public boolean getXAxisVisible()
	 {
		 return native_getXAxisVisible(m_NativeChart);
	 }

	 public void setXAxisTitle(String strTitle)
	 {
		 native_setXAxisTitle(m_NativeChart, strTitle);
		 invalidate();
	 }
	 public String getXAxisTitle()
	 {
		 return native_getXAxisTitle(m_NativeChart);
	 }
	 
	 public void setXAxisTitleFontName(String name)
	 {
		 native_setXAxisTitleFontName(m_NativeChart, name);
		 invalidate();
	 }
	 public String getXAxisTitleFontName()
	 {
		 return native_getXAxisTitleFontName(m_NativeChart);
	 }

	 public void setXAxisTitleFontSize(int nSize)
	 {
		 native_setXAxisTitleFontSize(m_NativeChart, nSize);
		 invalidate();
	 }
	 public int getXAxisTitleFontSize()
	 {
		 return native_getXAxisTitleFontSize(m_NativeChart);
	 }

	 public void setXAxisTitleFontStyle(int nStyle)
	 {
		 native_setXAxisTitleFontStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getXAxisTitleFontStyle()
	 {
		 return native_getXAxisTitleFontStyle(m_NativeChart);
	 }
	 
	 public void setXAxisTitleFontColor(int nColor)
	 {
		 native_setXAxisTitleFontColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getXAxisTitleFontColor()
	 {
		 return native_getXAxisTitleFontColor(m_NativeChart);
	 }
	 
	 public void setXAxisTitleFontStrikeout(boolean bUse)
	 {
		 native_setXAxisTitleFontStrikeout(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getXAxisTitleFontStrikeout()
	 {
		 return native_getXAxisTitleFontStrikeout(m_NativeChart);
	 }

	 public void setXAxisTitleFontUnderline(boolean bUse)
	 {
		 native_setXAxisTitleFontUnderline(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getXAxisTitleFontUnderline()
	 {
		 return native_getXAxisTitleFontUnderline(m_NativeChart);
	 }
	 
	 public void setXAxisFontName(String name)
	 {
		 native_setXAxisFontName(m_NativeChart, name);
		 invalidate();
	 }
	 public String getXAxisFontName()
	 {
		 return native_getXAxisFontName(m_NativeChart);
	 }

	 public void setXAxisFontSize(int nSize)
	 {
		 native_setXAxisFontSize(m_NativeChart, nSize);
		 invalidate();
	 }
	 public int getXAxisFontSize()
	 {
		 return native_getXAxisFontSize(m_NativeChart);
	 }

	 public void setXAxisFontStyle(int nStyle)
	 {
		 native_setXAxisFontStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getXAxisFontStyle()
	 {
		 return native_getXAxisFontStyle(m_NativeChart);
	 }
	 
	 public void setXAxisFontColor(int nColor)
	 {
		 native_setXAxisFontColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getXAxisFontColor()
	 {
		 return native_getXAxisFontColor(m_NativeChart);
	 }
	 
	 public void setXAxisFontStrikeout(boolean bUse)
	 {
		 native_setXAxisFontStrikeout(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getXAxisFontStrikeout()
	 {
		 return native_getXAxisFontStrikeout(m_NativeChart);
	 }

	 public void setXAxisFontUnderline(boolean bUse)
	 {
		 native_setXAxisFontUnderline(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getXAxisFontUnderline()
	 {
		 return native_getXAxisFontUnderline(m_NativeChart);
	 }
	 
	 public void setXAxisTitleBackdropFillStyle(int nStyle)
	 {
		 native_setXAxisTitleBackdropFillStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getXAxisTitleBackdropFillStyle()
	 {
		 return native_getXAxisTitleBackdropFillStyle(m_NativeChart);
	 }
	 public void setXAxisTitleBackdropFillColor(int nColor)
	 {
		 native_setXAxisTitleBackdropFillColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getXAxisTitleBackdropFillColor()
	 {
		 return native_getXAxisTitleBackdropFillColor(m_NativeChart);
	 }
	 
	 public void setXAxisTitleBackdropFrameStyle(int nStyle)
	 {
		 native_setXAxisTitleBackdropFrameStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getXAxisTitleBackdropFrameStyle()
	 {
		 return native_getXAxisTitleBackdropFrameStyle(m_NativeChart);
	 }
	 public void setXAxisTitleBackdropFrameColor(int nColor)
	 {
		 native_setXAxisTitleBackdropFrameColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getXAxisTitleBackdropFrameColor()
	 {
		 return native_getXAxisTitleBackdropFrameColor(m_NativeChart);
	 }
	 
	 public void setYAxisVisible(boolean bVisible)
	 {
		 native_setYAxisVisible(m_NativeChart, bVisible);
		 invalidate();
	 }
	 public boolean getYAxisVisible()
	 {
		 return native_getYAxisVisible(m_NativeChart);
	 }

	 public void setYAxisTitle(String strTitle)
	 {
		 native_setYAxisTitle(m_NativeChart, strTitle);
		 invalidate();
	 }
	 public String getYAxisTitle()
	 {
		 return native_getYAxisTitle(m_NativeChart);
	 }
	 
	 public void setYAxisTitleFontName(String name)
	 {
		 native_setYAxisTitleFontName(m_NativeChart, name);
		 invalidate();
	 }
	 public String getYAxisTitleFontName()
	 {
		 return native_getYAxisTitleFontName(m_NativeChart);
	 }

	 public void setYAxisTitleFontSize(int nSize)
	 {
		 native_setYAxisTitleFontSize(m_NativeChart, nSize);
		 invalidate();
	 }
	 public int getYAxisTitleFontSize()
	 {
		 return native_getYAxisTitleFontSize(m_NativeChart);
	 }

	 public void setYAxisTitleFontStyle(int nStyle)
	 {
		 native_setYAxisTitleFontStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getYAxisTitleFontStyle()
	 {
		 return native_getYAxisTitleFontStyle(m_NativeChart);
	 }
	 
	 public void setYAxisTitleFontColor(int nColor)
	 {
		 native_setYAxisTitleFontColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getYAxisTitleFontColor()
	 {
		 return native_getYAxisTitleFontColor(m_NativeChart);
	 }
	 
	 public void setYAxisTitleFontStrikeout(boolean bUse)
	 {
		 native_setYAxisTitleFontStrikeout(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getYAxisTitleFontStrikeout()
	 {
		 return native_getYAxisTitleFontStrikeout(m_NativeChart);
	 }

	 public void setYAxisTitleFontUnderline(boolean bUse)
	 {
		 native_setYAxisTitleFontUnderline(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getYAxisTitleFontUnderline()
	 {
		 return native_getYAxisTitleFontUnderline(m_NativeChart);
	 }

	 public void setYAxisFontName(String name)
	 {
		 native_setYAxisFontName(m_NativeChart, name);
		 invalidate();
	 }
	 public String getYAxisFontName()
	 {
		 return native_getYAxisFontName(m_NativeChart);
	 }

	 public void setYAxisFontSize(int nSize)
	 {
		 native_setYAxisFontSize(m_NativeChart, nSize);
		 invalidate();
	 }
	 public int getYAxisFontSize()
	 {
		 return native_getYAxisFontSize(m_NativeChart);
	 }

	 public void setYAxisFontStyle(int nStyle)
	 {
		 native_setYAxisFontStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getYAxisFontStyle()
	 {
		 return native_getYAxisFontStyle(m_NativeChart);
	 }
	 
	 public void setYAxisFontColor(int nColor)
	 {
		 native_setYAxisFontColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getYAxisFontColor()
	 {
		 return native_getYAxisFontColor(m_NativeChart);
	 }
	 
	 public void setYAxisFontStrikeout(boolean bUse)
	 {
		 native_setYAxisFontStrikeout(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getYAxisFontStrikeout()
	 {
		 return native_getYAxisFontStrikeout(m_NativeChart);
	 }

	 public void setYAxisFontUnderline(boolean bUse)
	 {
		 native_setYAxisFontUnderline(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getYAxisFontUnderline()
	 {
		 return native_getYAxisFontUnderline(m_NativeChart);
	 }
	 
	 public void setYAxisTitleBackdropFillStyle(int nStyle)
	 {
		 native_setYAxisTitleBackdropFillStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getYAxisTitleBackdropFillStyle()
	 {
		 return native_getYAxisTitleBackdropFillStyle(m_NativeChart);
	 }
	 public void setYAxisTitleBackdropFillColor(int nColor)
	 {
		 native_setYAxisTitleBackdropFillColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getYAxisTitleBackdropFillColor()
	 {
		 return native_getYAxisTitleBackdropFillColor(m_NativeChart);
	 }
	 
	 public void setYAxisTitleBackdropFrameStyle(int nStyle)
	 {
		 native_setYAxisTitleBackdropFrameStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getYAxisTitleBackdropFrameStyle()
	 {
		 return native_getYAxisTitleBackdropFrameStyle(m_NativeChart);
	 }
	 public void setYAxisTitleBackdropFrameColor(int nColor)
	 {
		 native_setYAxisTitleBackdropFrameColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getYAxisTitleBackdropFrameColor()
	 {
		 return native_getYAxisTitleBackdropFrameColor(m_NativeChart);
	 }
	 
	 public void setY2AxisVisible(boolean bVisible)
	 {
		 native_setY2AxisVisible(m_NativeChart, bVisible);
		 invalidate();
	 }
	 public boolean getY2AxisVisible()
	 {
		 return native_getY2AxisVisible(m_NativeChart);
	 }

	 public void setY2AxisTitle(String strTitle)
	 {
		 native_setY2AxisTitle(m_NativeChart, strTitle);
		 invalidate();
	 }
	 public String getY2AxisTitle()
	 {
		 return native_getY2AxisTitle(m_NativeChart);
	 }
	 
	 public void setY2AxisTitleFontName(String name)
	 {
		 native_setY2AxisTitleFontName(m_NativeChart, name);
		 invalidate();
	 }
	 public String getY2AxisTitleFontName()
	 {
		 return native_getY2AxisTitleFontName(m_NativeChart);
	 }

	 public void setY2AxisTitleFontSize(int nSize)
	 {
		 native_setY2AxisTitleFontSize(m_NativeChart, nSize);
		 invalidate();
	 }
	 public int getY2AxisTitleFontSize()
	 {
		 return native_getY2AxisTitleFontSize(m_NativeChart);
	 }

	 public void setY2AxisTitleFontStyle(int nStyle)
	 {
		 native_setY2AxisTitleFontStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getY2AxisTitleFontStyle()
	 {
		 return native_getY2AxisTitleFontStyle(m_NativeChart);
	 }
	 
	 public void setY2AxisTitleFontColor(int nColor)
	 {
		 native_setY2AxisTitleFontColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getY2AxisTitleFontColor()
	 {
		 return native_getY2AxisTitleFontColor(m_NativeChart);
	 }
	 
	 public void setY2AxisTitleFontStrikeout(boolean bUse)
	 {
		 native_setY2AxisTitleFontStrikeout(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getY2AxisTitleFontStrikeout()
	 {
		 return native_getY2AxisTitleFontStrikeout(m_NativeChart);
	 }

	 public void setY2AxisTitleFontUnderline(boolean bUse)
	 {
		 native_setY2AxisTitleFontUnderline(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getY2AxisTitleFontUnderline()
	 {
		 return native_getY2AxisTitleFontUnderline(m_NativeChart);
	 }

	 public void setY2AxisFontName(String name)
	 {
		 native_setY2AxisFontName(m_NativeChart, name);
		 invalidate();
	 }
	 public String getY2AxisFontName()
	 {
		 return native_getY2AxisFontName(m_NativeChart);
	 }

	 public void setY2AxisFontSize(int nSize)
	 {
		 native_setY2AxisFontSize(m_NativeChart, nSize);
		 invalidate();
	 }
	 public int getY2AxisFontSize()
	 {
		 return native_getY2AxisFontSize(m_NativeChart);
	 }

	 public void setY2AxisFontStyle(int nStyle)
	 {
		 native_setY2AxisFontStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getY2AxisFontStyle()
	 {
		 return native_getY2AxisFontStyle(m_NativeChart);
	 }
	 
	 public void setY2AxisFontColor(int nColor)
	 {
		 native_setY2AxisFontColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getY2AxisFontColor()
	 {
		 return native_getY2AxisFontColor(m_NativeChart);
	 }
	 
	 public void setY2AxisFontStrikeout(boolean bUse)
	 {
		 native_setY2AxisFontStrikeout(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getY2AxisFontStrikeout()
	 {
		 return native_getY2AxisFontStrikeout(m_NativeChart);
	 }

	 public void setY2AxisFontUnderline(boolean bUse)
	 {
		 native_setY2AxisFontUnderline(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getY2AxisFontUnderline()
	 {
		 return native_getY2AxisFontUnderline(m_NativeChart);
	 }
	 
	 public void setY2AxisTitleBackdropFillStyle(int nStyle)
	 {
		 native_setY2AxisTitleBackdropFillStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getY2AxisTitleBackdropFillStyle()
	 {
		 return native_getY2AxisTitleBackdropFillStyle(m_NativeChart);
	 }
	 public void setY2AxisTitleBackdropFillColor(int nColor)
	 {
		 native_setY2AxisTitleBackdropFillColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getY2AxisTitleBackdropFillColor()
	 {
		 return native_getY2AxisTitleBackdropFillColor(m_NativeChart);
	 }
	 
	 public void setY2AxisTitleBackdropFrameStyle(int nStyle)
	 {
		 native_setY2AxisTitleBackdropFrameStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getY2AxisTitleBackdropFrameStyle()
	 {
		 return native_getY2AxisTitleBackdropFrameStyle(m_NativeChart);
	 }
	 public void setY2AxisTitleBackdropFrameColor(int nColor)
	 {
		 native_setY2AxisTitleBackdropFrameColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getY2AxisTitleBackdropFrameColor()
	 {
		 return native_getY2AxisTitleBackdropFrameColor(m_NativeChart);
	 }
	 
	 public void setZAxisVisible(boolean bVisible)
	 {
		 native_setZAxisVisible(m_NativeChart, bVisible);
		 invalidate();
	 }
	 public boolean getZAxisVisible()
	 {
		 return native_getZAxisVisible(m_NativeChart);
	 }

	 public void setZAxisTitle(String strTitle)
	 {
		 native_setZAxisTitle(m_NativeChart, strTitle);
		 invalidate();
	 }
	 public String getZAxisTitle()
	 {
		 return native_getZAxisTitle(m_NativeChart);
	 }
	 
	 public void setZAxisTitleFontName(String name)
	 {
		 native_setZAxisTitleFontName(m_NativeChart, name);
		 invalidate();
	 }
	 public String getZAxisTitleFontName()
	 {
		 return native_getZAxisTitleFontName(m_NativeChart);
	 }

	 public void setZAxisTitleFontSize(int nSize)
	 {
		 native_setZAxisTitleFontSize(m_NativeChart, nSize);
		 invalidate();
	 }
	 public int getZAxisTitleFontSize()
	 {
		 return native_getZAxisTitleFontSize(m_NativeChart);
	 }

	 public void setZAxisTitleFontStyle(int nStyle)
	 {
		 native_setZAxisTitleFontStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getZAxisTitleFontStyle()
	 {
		 return native_getZAxisTitleFontStyle(m_NativeChart);
	 }
	 
	 public void setZAxisTitleFontColor(int nColor)
	 {
		 native_setZAxisTitleFontColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getZAxisTitleFontColor()
	 {
		 return native_getZAxisTitleFontColor(m_NativeChart);
	 }
	 
	 public void setZAxisTitleFontStrikeout(boolean bUse)
	 {
		 native_setZAxisTitleFontStrikeout(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getZAxisTitleFontStrikeout()
	 {
		 return native_getZAxisTitleFontStrikeout(m_NativeChart);
	 }

	 public void setZAxisTitleFontUnderline(boolean bUse)
	 {
		 native_setZAxisTitleFontUnderline(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getZAxisTitleFontUnderline()
	 {
		 return native_getZAxisTitleFontUnderline(m_NativeChart);
	 }

	 public void setZAxisFontName(String name)
	 {
		 native_setZAxisFontName(m_NativeChart, name);
		 invalidate();
	 }
	 public String getZAxisFontName()
	 {
		 return native_getZAxisFontName(m_NativeChart);
	 }

	 public void setZAxisFontSize(int nSize)
	 {
		 native_setZAxisFontSize(m_NativeChart, nSize);
		 invalidate();
	 }
	 public int getZAxisFontSize()
	 {
		 return native_getZAxisFontSize(m_NativeChart);
	 }

	 public void setZAxisFontStyle(int nStyle)
	 {
		 native_setZAxisFontStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getZAxisFontStyle()
	 {
		 return native_getZAxisFontStyle(m_NativeChart);
	 }
	 
	 public void setZAxisFontColor(int nColor)
	 {
		 native_setZAxisFontColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getZAxisFontColor()
	 {
		 return native_getZAxisFontColor(m_NativeChart);
	 }
	 
	 public void setZAxisFontStrikeout(boolean bUse)
	 {
		 native_setZAxisFontStrikeout(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getZAxisFontStrikeout()
	 {
		 return native_getZAxisFontStrikeout(m_NativeChart);
	 }

	 public void setZAxisFontUnderline(boolean bUse)
	 {
		 native_setZAxisFontUnderline(m_NativeChart, bUse);
		 invalidate();
	 }
	 public boolean getZAxisFontUnderline()
	 {
		 return native_getZAxisFontUnderline(m_NativeChart);
	 }
	 
	 public void setZAxisTitleBackdropFillStyle(int nStyle)
	 {
		 native_setZAxisTitleBackdropFillStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getZAxisTitleBackdropFillStyle()
	 {
		 return native_getZAxisTitleBackdropFillStyle(m_NativeChart);
	 }
	 public void setZAxisTitleBackdropFillColor(int nColor)
	 {
		 native_setZAxisTitleBackdropFillColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getZAxisTitleBackdropFillColor()
	 {
		 return native_getZAxisTitleBackdropFillColor(m_NativeChart);
	 }
	 
	 public void setZAxisTitleBackdropFrameStyle(int nStyle)
	 {
		 native_setZAxisTitleBackdropFrameStyle(m_NativeChart, nStyle);
		 invalidate();
	 }
	 public int getZAxisTitleBackdropFrameStyle()
	 {
		 return native_getZAxisTitleBackdropFrameStyle(m_NativeChart);
	 }
	 public void setZAxisTitleBackdropFrameColor(int nColor)
	 {
		 native_setZAxisTitleBackdropFrameColor(m_NativeChart, nColor);
		 invalidate();
	 }
	 public int getZAxisTitleBackdropFrameColor()
	 {
		 return native_getZAxisTitleBackdropFrameColor(m_NativeChart);
	 }
	 
	 public int getSeriesColCount()
	 {
		 return native_getSeriesColCount(m_NativeChart);
	 }
	 public int getSeriesRowCount()
	 {
		 return native_getSeriesRowCount(m_NativeChart);
	 }

	 public String getSeriesColLabel(int nCol)
	 {
		 return native_getSeriesColLabel(m_NativeChart, nCol);
	 }
	 public String getSeriesRowLabel(int nRow)
	 {
		 return native_getSeriesRowLabel(m_NativeChart, nRow);
	 }

	 public void setSeriesDataPointLabelLocation(int nCol, int nRow, int nLocation)
	 {
		 native_setSeriesDataPointLabelLocation(m_NativeChart, nCol, nRow, nLocation);
		 invalidate();
	 }
	 public int getSeriesDataPointLabelLocation(int nCol, int nRow)
	 {
		 return native_getSeriesDataPointLabelLocation(m_NativeChart, nCol, nRow);
	 }

	 public void setSeriesDataPointLabelValueFormat(int nCol, int nRow, String stdFormat)
	 {
		 native_setSeriesDataPointLabelValueFormat(m_NativeChart, nCol, nRow, stdFormat);
		 invalidate();
	 }
	 public String getSeriesDataPointLabelValueFormat(int nCol, int nRow)
	 {
		 return native_getSeriesDataPointLabelValueFormat(m_NativeChart, nCol, nRow);
	 }
	 
	 public void setSeriesDataPointLabelPercentFormat(int nCol, int nRow, String stdFormat)
	 {
		 native_setSeriesDataPointLabelPercentFormat(m_NativeChart, nCol, nRow, stdFormat);
		 invalidate();
	 }
	 public String getSeriesDataPointLabelPercentFormat(int nCol, int nRow)
	 {
		 return native_getSeriesDataPointLabelPercentFormat(m_NativeChart, nCol, nRow);
	 }

	 public String getSeriesDataPointFormatList(int nType)
	 {
		 return native_getSeriesDataPointFormatList(m_NativeChart, nType);
	 }

	 public void setSeriesOrder(int nCol, int nOrder)
	 {
		 native_setSeriesOrder(m_NativeChart, nCol, nOrder);
		 invalidate();
	 }
	 public int getSeriesOrder(int nCol)
	 {
		 return native_getSeriesOrder(m_NativeChart, nCol);
	 }
	 
	 public void setSeriesOptionSeriesHide(int nCol, boolean bHide)
	 {
		 native_setSeriesOptionSeriesHide(m_NativeChart, nCol, bHide);
		 invalidate();
	 }
	 public boolean getSeriesOptionSeriesHide(int nCol)
	 {
		 return native_getSeriesOptionSeriesHide(m_NativeChart, nCol);
	 }
	 
	 public void setSeriesOptionSeriesExclude(int nCol, boolean bFlag)
	 {
		 native_setSeriesOptionSeriesExclude(m_NativeChart, nCol, bFlag);
		 invalidate();
	 }
	 public boolean getSeriesOptionSeriesExclude(int nCol)
	 {
		 return native_getSeriesOptionSeriesExclude(m_NativeChart, nCol);
	 }
	 
	 public void setSeriesOptionSeriesMarkersShow(int nCol, boolean bFlag)
	 {
		 native_setSeriesOptionSeriesMarkersShow(m_NativeChart, nCol, bFlag);
		 invalidate();
	 }
	 public boolean getSeriesOptionSeriesMarkersShow(int nCol)
	 {
		 return native_getSeriesOptionSeriesMarkersShow(m_NativeChart, nCol);
	 }
	 
	 public void setSeriesOptionSeriesAutoMarkers(int nCol, boolean bFlag)
	 {
		 native_setSeriesOptionSeriesAutoMarkers(m_NativeChart, nCol, bFlag);
		 invalidate();
	 }
	 public boolean getSeriesOptionSeriesAutoMarkers(int nCol)
	 {
		 return native_getSeriesOptionSeriesAutoMarkers(m_NativeChart, nCol);
	 }
	 
	 public void setSeriesOptionSeriesHiLoGainColor(int nCol, int nColor)
	 {
		 native_setSeriesOptionSeriesHiLoGainColor(m_NativeChart, nCol, nColor);
		 invalidate();
	 }
	 public int getSeriesOptionSeriesHiLoGainColor(int nCol)
	 {
		 return native_getSeriesOptionSeriesHiLoGainColor(m_NativeChart, nCol);
	 }
	 
	 public void setSeriesOptionSeriesHiLoLossColor(int nCol, int nColor)
	 {
		 native_setSeriesOptionSeriesHiLoLossColor(m_NativeChart, nCol, nColor);
		 invalidate();
	 }
	 public int getSeriesOptionSeriesHiLoLossColor(int nCol)
	 {
		 return native_getSeriesOptionSeriesHiLoLossColor(m_NativeChart, nCol);
	 }

	 public void setSeriesDatapointLabelTypeValue(int nCol, int nRow, boolean bUse)
	 {
		 native_setSeriesDatapointLabelTypeValue(m_NativeChart, nCol, nRow, bUse);
		 invalidate();
	 }
	 public boolean getSeriesDatapointLabelTypeValue(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointLabelTypeValue(m_NativeChart, nCol, nRow);
	 }
	 
	 public void setSeriesDatapointLabelTypePercent(int nCol, int nRow, boolean bUse)
	 {
		 native_setSeriesDatapointLabelTypePercent(m_NativeChart, nCol, nRow, bUse);
		 invalidate();
	 }
	 public boolean getSeriesDatapointLabelTypePercent(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointLabelTypePercent(m_NativeChart, nCol, nRow);
	 }
	 
	 public void setSeriesDatapointLabelTypeSeriesName(int nCol, int nRow, boolean bUse)
	 {
		 native_setSeriesDatapointLabelTypeSeriesName(m_NativeChart, nCol, nRow, bUse);
		 invalidate();
	 }
	 public boolean getSeriesDatapointLabelTypeSeriesName(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointLabelTypeSeriesName(m_NativeChart, nCol, nRow);
	 }
	 
	 public void setSeriesDatapointLabelTypePointName(int nCol, int nRow, boolean bUse)
	 {
		 native_setSeriesDatapointLabelTypePointName(m_NativeChart, nCol, nRow, bUse);
		 invalidate();
	 }
	 public boolean getSeriesDatapointLabelTypePointName(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointLabelTypePointName(m_NativeChart, nCol, nRow);
	 }
	 
	 public boolean setSeriesDatapointFillPattern(int nCol, int nRow, int nStyle, int nPattern)
	 {
		 boolean bRe = native_setSeriesDatapointFillPattern(m_NativeChart, nCol, nRow, nStyle, nPattern);
		 invalidate();
		 return bRe;
	 }
	 public int getSeriesDatapointFillStyle(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointFillStyle(m_NativeChart, nCol, nRow);
	 }
	 public int getSeriesDatapointFillPattern(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointFillPattern(m_NativeChart, nCol, nRow);
	 }
	 public void setSeriesDatapointFillColor(int nCol, int nRow, int nColor)
	 {
		 native_setSeriesDatapointFillColor(m_NativeChart, nCol, nRow, nColor);
		 invalidate();
	 }
	 public int getSeriesDatapointFillColor(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointFillColor(m_NativeChart, nCol, nRow);
	 }
	 public void setSeriesDatapointFillPatternColor(int nCol, int nRow, int nColor)
	 {
		 native_setSeriesDatapointFillPatternColor(m_NativeChart, nCol, nRow, nColor);
		 invalidate();
	 }
	 public int getSeriesDatapointFillPatternColor(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointFillPatternColor(m_NativeChart, nCol, nRow);
	 }

	 public void setSeriesDatapointEdgeStyle(int nCol, int nRow, int nStyle)
	 {
		 native_setSeriesDatapointEdgeStyle(m_NativeChart, nCol, nRow, nStyle);
		 invalidate();
	 }
	 public int getSeriesDatapointEdgeStyle(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointEdgeStyle(m_NativeChart, nCol, nRow);
	 }
	 public void setSeriesDatapointEdgeWidth(int nCol, int nRow, int nWidth)
	 {
		 native_setSeriesDatapointEdgeWidth(m_NativeChart, nCol, nRow, nWidth);
		 invalidate();
	 }
	 public int getSeriesDatapointEdgeWidth(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointEdgeWidth(m_NativeChart, nCol, nRow);
	 }
	 public void setSeriesDatapointEdgeColor(int nCol, int nRow, int nColor)
	 {
		 native_setSeriesDatapointEdgeColor(m_NativeChart, nCol, nRow, nColor);
		 invalidate();
	 }
	 public int getSeriesDatapointEdgeColor(int nCol, int nRow)
	 {
		 return native_getSeriesDatapointEdgeColor(m_NativeChart, nCol, nRow);
	 }
	 
	 
	 
	 public boolean isAnimation()
	 {
		 return native_isAnimation(m_NativeChart);
	 }
	 
	 public boolean isDataQueue()
	 {
		 return native_isDataQueue(m_NativeChart);
	 }
	 
	 public int getAnimationStatus()
	 {
		 return native_getAnimationStatus(m_NativeChart);
	 }
	 
	 public void beginAnimation(boolean loop)
	 {
		 native_beginAnimation(m_NativeChart, loop);
		 m_bAnimationRender = true;
		 postInvalidate();
	 }
	 
	 public void endAnimation()
	 {
		 native_endAnimation(m_NativeChart);
		 m_bAnimationRender = false;
		 postInvalidate();
	 }
	 
	 public void beginDataQueue()
	 {
		 m_bDataQueue = true;
		 postInvalidate();
	 }
	 
	 public void endDataQueue()
	 {
		 m_bDataQueue = false;
		 postInvalidate();
	 }
	 
	 public void addFallbackFontPath(String strFontPath)
	 {
		 native_addFallbackFontPath(m_NativeChart, strFontPath);
	 }
	 
	 public void removeFallbackFontPath(String strFontPath)
	 {
		 native_removeFallbackFontPath(m_NativeChart, strFontPath);
	 }
	 
	 public void setTitle(String strTitle)
	 {
		 native_setTitle(m_NativeChart, strTitle);
	 }
	 
	 public void setSeriesType(int nSeries, int nType)
	 {
		 native_setSeriesType(m_NativeChart, nSeries, nType);
	 }
	 
	 public boolean pushData(GridData gridData, int nDuration)
	 {
		 if (nDuration <= 0)
		 {
			 nDuration = _DMA_DURATION;
		 }
		 
		 boolean bResult =	native_pushData(m_NativeChart, gridData, m_nChartWidth, m_nChartHeight, nDuration);
		 postInvalidate();
		 return bResult;
	 }
	 
	 public void setYAxisMaximum(boolean bAllowMaximum, double dMax)
	 {
		 native_setYAxisMaximum(m_NativeChart, bAllowMaximum, dMax);
	 }
	 
	 public void setDataSeriesInRow(int nPlotBy)
	 {
		 native_setDataSeriesInRow(m_NativeChart, nPlotBy);
	 }
	
	 private static native int		native_init();
	 private static native void		finalizer(int nativeChart);
	 private static native int		native_draw(int nativeChart, Bitmap bitmap);
	 private static native int		native_getChartType(int nativeChart);
	 private static native void		native_setChartType(int nativeChart, int type);
	 private static native float	native_getRotation(int nativeChart);
	 private static native void		native_setRotation(int nativeChart, float degree);
	 private static native float	native_getElevation(int nativeChart);
	 private static native void		native_setElevation(int nativeChart, float degree);
	 private static native float	native_getPerspective(int nativeChart);
	 private static native void		native_setPerspective(int nativeChart, float degree);
	 private static native int		native_getAlpha(int nativeChart);
	 private static native void		native_setAlpha(int nativeChart, int alpha);
	 private static native int   	native_getColorTemplateCount(int nativeChart);
	 private static native int   	native_getColorTemplate(int nativeChart);
	 private static native void   	native_setColorTemplate(int nativeChart, int index);
	 private static native boolean  native_isLegendVisible(int nativeChart);
	 private static native void   	native_setLegendVisible(int nativeChart, boolean visible);
	 private static native void		native_setLegendLocation(int nativeChart, int nLocation);
	 private static native int		native_getLegendLocation(int nativeChart);

	 private static native void		native_setLegendFontName(int nativeChart, String name);
	 private static native String	native_getLegendFontName(int nativeChart);

	 private static native void		native_setLegendFontSize(int nativeChart, int nSize);
	 private static native int		native_getLegendFontSize(int nativeChart);

	 private static native void		native_setLegendFontStyle(int nativeChart, int nStyle);
	 private static native int		native_getLegendFontStyle(int nativeChart);

	 private static native void		native_setLegendFontColor(int nativeChart, int nColor);
	 private static native int		native_getLegendFontColor(int nativeChart);

	 private static native void		native_setLegendFontStrikeout(int nativeChart, boolean bUse);
	 private static native boolean	native_getLegendFontStrikeout(int nativeChart);

	 private static native void		native_setLegendFontUnderline(int nativeChart, boolean bUse);
	 private static native boolean	native_getLegendFontUnderline(int nativeChart);

	 private static native void		native_setLegendBackdropStyle(int nativeChart, int nStyle);
	 private static native int		native_getLegendBackdropStyle(int nativeChart);

	 private static native int		native_getLegendBackdropColor(int nativeChart);
	 private static native void		native_setLegendBackdropColor(int nativeChart, int nColor);
	 
	 private static native void		native_setLegendPosition(int nativeChart, float fX, float fY, float fWidth, float fHeight);
	 private static native float	native_getLegendPositionX(int nativeChart);
	 private static native float	native_getLegendPositionY(int nativeChart);
	 private static native float	native_getLegendPositionWidth(int nativeChart);
	 private static native float	native_getLegendPositionHeight(int nativeChart);
	 
	 private static native void		native_setLegendBackdropFillStyle(int nativeChart, int nStyle);
	 private static native int		native_getLegendBackdropFillStyle(int nativeChart);
	 private static native void		native_setLegendBackdropFillColor(int nativeChart, int nColor);
	 private static native int		native_getLegendBackdropFillColor(int nativeChart);

	 private static native int      native_getChartBackdropStyle(int nativeChart);
	 private static native void     native_setChartBackdropStyle(int nativeChart, int nStyle);
	 private static native int      native_getChartBackdropColor(int nativeChart);
	 private static native void     native_setChartBackdropColor(int nativeChart, int nColor);
	 private static native void     native_setChartBackdropPicture(int nativeChart, String strFile);
	 private static native String   native_getChartBackdropPicture(int nativeChart);
	 private static native void     native_setChartBackdropPictureDrawStyle(int nativeChart, int nStyle);
	 private static native int      native_getChartBackdropPictureDrawStyle(int nativeChart);
	 
	 private static native void		native_setChartBackdropFillStyle(int nativeChart, int nStyle);
	 private static native int		native_getChartBackdropFillStyle(int nativeChart);
	 private static native void		native_setChartBackdropFillColor(int nativeChart, int nColor);
	 private static native int		native_getChartBackdropFillColor(int nativeChart);
	 
	 private static native int      native_getPlotBackdropStyle(int nativeChart);
	 private static native void     native_setPlotBackdropStyle(int nativeChart, int nStyle);
	 private static native int      native_getPlotBackdropColor(int nativeChart);
	 private static native void     native_setPlotBackdropColor(int nativeChart, int nColor);
	 private static native void     native_setPlotBackdropPicture(int nativeChart, String strFile);
	 private static native String   native_getPlotBackdropPicture(int nativeChart);
	 private static native void     native_setPlotBackdropPictureDrawStyle(int nativeChart, int nStyle);
	 private static native int      native_getPlotBackdropPictureDrawStyle(int nativeChart);
	 
	 private static native void		native_setPlotPosition(int nativeChart, float fX, float fY, float fWidth, float fHeight);
	 private static native float	native_getPlotPositionX(int nativeChart);
	 private static native float	native_getPlotPositionY(int nativeChart);
	 private static native float	native_getPlotPositionWidth(int nativeChart);
	 private static native float	native_getPlotPositionHeight(int nativeChart);
	 
	 private static native void		native_setPlotFillStyle(int nativeChart, int nStyle);
	 private static native int		native_getPlotFillStyle(int nativeChart);
	 private static native void		native_setPlotFillColor(int nativeChart, int nColor);
	 private static native int		native_getPlotFillColor(int nativeChart);
	 
	 private static native void		native_setTitleText(int nativeChart, String strTitle);
	 private static native String	native_getTitleText(int nativeChart);

	 private static native void		native_setTitleVisible(int nativeChart, boolean bVisible);
	 private static native boolean	native_getTitleVisible(int nativeChart);

	 private static native void		native_setTitleLocation(int nativeChart, int nLocation);
	 private static native int		native_getTitleLocation(int nativeChart);

	 private static native void		native_setTitleFontName(int nativeChart, String name);
	 private static native String	native_getTitleFontName(int nativeChart);

	 private static native void		native_setTitleFontSize(int nativeChart, int nSize);
	 private static native int		native_getTitleFontSize(int nativeChart);

	 private static native void		native_setTitleFontStyle(int nativeChart, int nStyle);
	 private static native int		native_getTitleFontStyle(int nativeChart);

	 private static native void		native_setTitleFontStrikeout(int nativeChart, boolean bUse);
	 private static native boolean	native_getTitleFontStrikeout(int nativeChart);

	 private static native void		native_setTitleFontUnderline(int nativeChart, boolean bUse);
	 private static native boolean	native_getTitleFontUnderline(int nativeChart);

	 private static native void		native_setTitleFontColor(int nativeChart, int nColor);
	 private static native int		native_getTitleFontColor(int nativeChart);
	 
	 private static native void		native_setTitleBackdropFillStyle(int nativeChart, int nStyle);
	 private static native int		native_getTitleBackdropFillStyle(int nativeChart);
	 private static native void		native_setTitleBackdropFillColor(int nativeChart, int nColor);
	 private static native int		native_getTitleBackdropFillColor(int nativeChart);

	 private static native void		native_setTitleBackdropFrameStyle(int nativeChart, int nStyle);
	 private static native int		native_getTitleBackdropFrameStyle(int nativeChart);
	 private static native void		native_setTitleBackdropFrameColor(int nativeChart, int nColor);
	 private static native int		native_getTitleBackdropFrameColor(int nativeChart);
	 
	 private static native void		native_setXAxisVisible(int nativeChart, boolean bVisible);
	 private static native boolean	native_getXAxisVisible(int nativeChart);

	 private static native void		native_setXAxisTitle(int nativeChart, String strTitle);
	 private static native String	native_getXAxisTitle(int nativeChart);
	 
	 private static native void		native_setXAxisTitleFontName(int nativeChart, String name);
	 private static native String	native_getXAxisTitleFontName(int nativeChart);

	 private static native void		native_setXAxisTitleFontSize(int nativeChart, int nSize);
	 private static native int		native_getXAxisTitleFontSize(int nativeChart);

	 private static native void		native_setXAxisTitleFontStyle(int nativeChart, int nStyle);
	 private static native int		native_getXAxisTitleFontStyle(int nativeChart);

	 private static native void		native_setXAxisTitleFontColor(int nativeChart, int nColor);
	 private static native int		native_getXAxisTitleFontColor(int nativeChart);
	 
	 private static native void		native_setXAxisTitleFontStrikeout(int nativeChart, boolean bUse);
	 private static native boolean	native_getXAxisTitleFontStrikeout(int nativeChart);

	 private static native void		native_setXAxisTitleFontUnderline(int nativeChart, boolean bUse);
	 private static native boolean	native_getXAxisTitleFontUnderline(int nativeChart);
	 
	 private static native void		native_setXAxisFontName(int nativeChart, String name);
	 private static native String	native_getXAxisFontName(int nativeChart);

	 private static native void		native_setXAxisFontSize(int nativeChart, int nSize);
	 private static native int		native_getXAxisFontSize(int nativeChart);

	 private static native void		native_setXAxisFontStyle(int nativeChart, int nStyle);
	 private static native int		native_getXAxisFontStyle(int nativeChart);

	 private static native void		native_setXAxisFontColor(int nativeChart, int nColor);
	 private static native int		native_getXAxisFontColor(int nativeChart);
	 
	 private static native void		native_setXAxisFontStrikeout(int nativeChart, boolean bUse);
	 private static native boolean	native_getXAxisFontStrikeout(int nativeChart);

	 private static native void		native_setXAxisFontUnderline(int nativeChart, boolean bUse);
	 private static native boolean	native_getXAxisFontUnderline(int nativeChart);
	 
	 private static native void		native_setXAxisTitleBackdropFillStyle(int nativeChart, int nStyle);
	 private static native int		native_getXAxisTitleBackdropFillStyle(int nativeChart);
	 private static native void		native_setXAxisTitleBackdropFillColor(int nativeChart, int nColorUse);
	 private static native int		native_getXAxisTitleBackdropFillColor(int nativeChart);
	 
	 private static native void		native_setXAxisTitleBackdropFrameStyle(int nativeChart, int nStyle);
	 private static native int		native_getXAxisTitleBackdropFrameStyle(int nativeChart);
	 private static native void		native_setXAxisTitleBackdropFrameColor(int nativeChart, int nColorUse);
	 private static native int		native_getXAxisTitleBackdropFrameColor(int nativeChart);
	 
	 private static native void		native_setYAxisVisible(int nativeChart, boolean bVisible);
	 private static native boolean	native_getYAxisVisible(int nativeChart);

	 private static native void		native_setYAxisTitle(int nativeChart, String strTitle);
	 private static native String	native_getYAxisTitle(int nativeChart);
	 
	 private static native void		native_setYAxisTitleFontName(int nativeChart, String name);
	 private static native String	native_getYAxisTitleFontName(int nativeChart);

	 private static native void		native_setYAxisTitleFontSize(int nativeChart, int nSize);
	 private static native int		native_getYAxisTitleFontSize(int nativeChart);

	 private static native void		native_setYAxisTitleFontStyle(int nativeChart, int nStyle);
	 private static native int		native_getYAxisTitleFontStyle(int nativeChart);

	 private static native void		native_setYAxisTitleFontColor(int nativeChart, int nColor);
	 private static native int		native_getYAxisTitleFontColor(int nativeChart);
	 
	 private static native void		native_setYAxisTitleFontStrikeout(int nativeChart, boolean bUse);
	 private static native boolean	native_getYAxisTitleFontStrikeout(int nativeChart);

	 private static native void		native_setYAxisTitleFontUnderline(int nativeChart, boolean bUse);
	 private static native boolean	native_getYAxisTitleFontUnderline(int nativeChart);

	 private static native void		native_setYAxisFontName(int nativeChart, String name);
	 private static native String	native_getYAxisFontName(int nativeChart);

	 private static native void		native_setYAxisFontSize(int nativeChart, int nSize);
	 private static native int		native_getYAxisFontSize(int nativeChart);

	 private static native void		native_setYAxisFontStyle(int nativeChart, int nStyle);
	 private static native int		native_getYAxisFontStyle(int nativeChart);

	 private static native void		native_setYAxisFontColor(int nativeChart, int nColor);
	 private static native int		native_getYAxisFontColor(int nativeChart);
	 
	 private static native void		native_setYAxisFontStrikeout(int nativeChart, boolean bUse);
	 private static native boolean	native_getYAxisFontStrikeout(int nativeChart);

	 private static native void		native_setYAxisFontUnderline(int nativeChart, boolean bUse);
	 private static native boolean	native_getYAxisFontUnderline(int nativeChart);
	 
	 private static native void		native_setYAxisTitleBackdropFillStyle(int nativeChart, int nStyle);
	 private static native int		native_getYAxisTitleBackdropFillStyle(int nativeChart);
	 private static native void		native_setYAxisTitleBackdropFillColor(int nativeChart, int nColorUse);
	 private static native int		native_getYAxisTitleBackdropFillColor(int nativeChart);
	 
	 private static native void		native_setYAxisTitleBackdropFrameStyle(int nativeChart, int nStyle);
	 private static native int		native_getYAxisTitleBackdropFrameStyle(int nativeChart);
	 private static native void		native_setYAxisTitleBackdropFrameColor(int nativeChart, int nColorUse);
	 private static native int		native_getYAxisTitleBackdropFrameColor(int nativeChart);
	 
	 private static native void		native_setY2AxisVisible(int nativeChart, boolean bVisible);
	 private static native boolean	native_getY2AxisVisible(int nativeChart);

	 private static native void		native_setY2AxisTitle(int nativeChart, String strTitle);
	 private static native String	native_getY2AxisTitle(int nativeChart);
	 
	 private static native void		native_setY2AxisTitleFontName(int nativeChart, String name);
	 private static native String	native_getY2AxisTitleFontName(int nativeChart);

	 private static native void		native_setY2AxisTitleFontSize(int nativeChart, int nSize);
	 private static native int		native_getY2AxisTitleFontSize(int nativeChart);

	 private static native void		native_setY2AxisTitleFontStyle(int nativeChart, int nStyle);
	 private static native int		native_getY2AxisTitleFontStyle(int nativeChart);

	 private static native void		native_setY2AxisTitleFontColor(int nativeChart, int nColor);
	 private static native int		native_getY2AxisTitleFontColor(int nativeChart);
	 
	 private static native void		native_setY2AxisTitleFontStrikeout(int nativeChart, boolean bUse);
	 private static native boolean	native_getY2AxisTitleFontStrikeout(int nativeChart);

	 private static native void		native_setY2AxisTitleFontUnderline(int nativeChart, boolean bUse);
	 private static native boolean	native_getY2AxisTitleFontUnderline(int nativeChart);

	 private static native void		native_setY2AxisFontName(int nativeChart, String name);
	 private static native String	native_getY2AxisFontName(int nativeChart);

	 private static native void		native_setY2AxisFontSize(int nativeChart, int nSize);
	 private static native int		native_getY2AxisFontSize(int nativeChart);

	 private static native void		native_setY2AxisFontStyle(int nativeChart, int nStyle);
	 private static native int		native_getY2AxisFontStyle(int nativeChart);

	 private static native void		native_setY2AxisFontColor(int nativeChart, int nColor);
	 private static native int		native_getY2AxisFontColor(int nativeChart);
	 
	 private static native void		native_setY2AxisFontStrikeout(int nativeChart, boolean bUse);
	 private static native boolean	native_getY2AxisFontStrikeout(int nativeChart);

	 private static native void		native_setY2AxisFontUnderline(int nativeChart, boolean bUse);
	 private static native boolean	native_getY2AxisFontUnderline(int nativeChart);
	 
	 private static native void		native_setY2AxisTitleBackdropFillStyle(int nativeChart, int nStyle);
	 private static native int		native_getY2AxisTitleBackdropFillStyle(int nativeChart);
	 private static native void		native_setY2AxisTitleBackdropFillColor(int nativeChart, int nColorUse);
	 private static native int		native_getY2AxisTitleBackdropFillColor(int nativeChart);
	 
	 private static native void		native_setY2AxisTitleBackdropFrameStyle(int nativeChart, int nStyle);
	 private static native int		native_getY2AxisTitleBackdropFrameStyle(int nativeChart);
	 private static native void		native_setY2AxisTitleBackdropFrameColor(int nativeChart, int nColorUse);
	 private static native int		native_getY2AxisTitleBackdropFrameColor(int nativeChart);
	 
	 private static native void		native_setZAxisVisible(int nativeChart, boolean bVisible);
	 private static native boolean	native_getZAxisVisible(int nativeChart);

	 private static native void		native_setZAxisTitle(int nativeChart, String strTitle);
	 private static native String	native_getZAxisTitle(int nativeChart);
	 
	 private static native void		native_setZAxisTitleFontName(int nativeChart, String name);
	 private static native String	native_getZAxisTitleFontName(int nativeChart);

	 private static native void		native_setZAxisTitleFontSize(int nativeChart, int nSize);
	 private static native int		native_getZAxisTitleFontSize(int nativeChart);

	 private static native void		native_setZAxisTitleFontStyle(int nativeChart, int nStyle);
	 private static native int		native_getZAxisTitleFontStyle(int nativeChart);

	 private static native void		native_setZAxisTitleFontColor(int nativeChart, int nColor);
	 private static native int		native_getZAxisTitleFontColor(int nativeChart);
	 
	 private static native void		native_setZAxisTitleFontStrikeout(int nativeChart, boolean bUse);
	 private static native boolean	native_getZAxisTitleFontStrikeout(int nativeChart);

	 private static native void		native_setZAxisTitleFontUnderline(int nativeChart, boolean bUse);
	 private static native boolean	native_getZAxisTitleFontUnderline(int nativeChart);

	 private static native void		native_setZAxisFontName(int nativeChart, String name);
	 private static native String	native_getZAxisFontName(int nativeChart);

	 private static native void		native_setZAxisFontSize(int nativeChart, int nSize);
	 private static native int		native_getZAxisFontSize(int nativeChart);

	 private static native void		native_setZAxisFontStyle(int nativeChart, int nStyle);
	 private static native int		native_getZAxisFontStyle(int nativeChart);

	 private static native void		native_setZAxisFontColor(int nativeChart, int nColor);
	 private static native int		native_getZAxisFontColor(int nativeChart);
	 
	 private static native void		native_setZAxisFontStrikeout(int nativeChart, boolean bUse);
	 private static native boolean	native_getZAxisFontStrikeout(int nativeChart);

	 private static native void		native_setZAxisFontUnderline(int nativeChart, boolean bUse);
	 private static native boolean	native_getZAxisFontUnderline(int nativeChart);
	 
	 private static native void		native_setZAxisTitleBackdropFillStyle(int nativeChart, int nStyle);
	 private static native int		native_getZAxisTitleBackdropFillStyle(int nativeChart);
	 private static native void		native_setZAxisTitleBackdropFillColor(int nativeChart, int nColorUse);
	 private static native int		native_getZAxisTitleBackdropFillColor(int nativeChart);
	 
	 private static native void		native_setZAxisTitleBackdropFrameStyle(int nativeChart, int nStyle);
	 private static native int		native_getZAxisTitleBackdropFrameStyle(int nativeChart);
	 private static native void		native_setZAxisTitleBackdropFrameColor(int nativeChart, int nColorUse);
	 private static native int		native_getZAxisTitleBackdropFrameColor(int nativeChart);
	 
	 private static native int		native_getSeriesColCount(int nativeChart);
	 private static native int		native_getSeriesRowCount(int nativeChart);
	
	 private static native String	native_getSeriesColLabel(int nativeChart, int nCol);
	 private static native String	native_getSeriesRowLabel(int nativeChart, int nRow);
	
	 private static native void		native_setSeriesDataPointLabelLocation(int nativeChart, int nCol, int nRow, int nLocation);
	 private static native int		native_getSeriesDataPointLabelLocation(int nativeChart, int nCol, int nRow);
	
	 private static native void		native_setSeriesDataPointLabelValueFormat(int nativeChart, int nCol, int nRow, String stdFormat);
	 private static native String	native_getSeriesDataPointLabelValueFormat(int nativeChart, int nCol, int nRow);
	
	 private static native void		native_setSeriesDataPointLabelPercentFormat(int nativeChart, int nCol, int nRow, String stdFormat);
	 private static native String	native_getSeriesDataPointLabelPercentFormat(int nativeChart, int nCol, int nRow);

	 private static native String	native_getSeriesDataPointFormatList(int nativeChart, int nType);
	
	 private static native void		native_setSeriesOrder(int nativeChart, int nCol, int nOrder);
	 private static native int		native_getSeriesOrder(int nativeChart, int nCol);
	 
	 private static native void		native_setSeriesOptionSeriesHide(int nativeChart, int nCol, boolean bHide);
	 private static native boolean	native_getSeriesOptionSeriesHide(int nativeChart, int nCol);

	 private static native void		native_setSeriesOptionSeriesExclude(int nativeChart, int nCol, boolean bFlag);
	 private static native boolean	native_getSeriesOptionSeriesExclude(int nativeChart, int nCol);
	 
	 private static native void		native_setSeriesOptionSeriesMarkersShow(int nativeChart, int nCol, boolean bFlag);
	 private static native boolean	native_getSeriesOptionSeriesMarkersShow(int nativeChart, int nCol);
	 
	 private static native void		native_setSeriesOptionSeriesAutoMarkers(int nativeChart, int nCol, boolean bFlag);
	 private static native boolean	native_getSeriesOptionSeriesAutoMarkers(int nativeChart, int nCol);
	 
	 private static native void		native_setSeriesOptionSeriesHiLoGainColor(int nativeChart, int nCol, int nColor);
	 private static native int		native_getSeriesOptionSeriesHiLoGainColor(int nativeChart, int nCol);
	 
	 private static native void		native_setSeriesOptionSeriesHiLoLossColor(int nativeChart, int nCol, int nColor);
	 private static native int		native_getSeriesOptionSeriesHiLoLossColor(int nativeChart, int nCol);
	 
	 private static native void		native_setSeriesDatapointLabelTypeValue(int nativeChart, int nCol, int nRow, boolean bUse);
	 private static native boolean	native_getSeriesDatapointLabelTypeValue(int nativeChart, int nCol, int nRow);
	 
	 private static native void		native_setSeriesDatapointLabelTypePercent(int nativeChart, int nCol, int nRow, boolean bUse);
	 private static native boolean	native_getSeriesDatapointLabelTypePercent(int nativeChart, int nCol, int nRow);
	 
	 private static native void		native_setSeriesDatapointLabelTypeSeriesName(int nativeChart, int nCol, int nRow, boolean bUse);
	 private static native boolean	native_getSeriesDatapointLabelTypeSeriesName(int nativeChart, int nCol, int nRow);
	 
	 private static native void		native_setSeriesDatapointLabelTypePointName(int nativeChart, int nCol, int nRow, boolean bUse);
	 private static native boolean	native_getSeriesDatapointLabelTypePointName(int nativeChart, int nCol, int nRow);

	 private static native boolean	native_setSeriesDatapointFillPattern(int nativeChart, int nCol, int nRow, int nStyle, int nPattern);
	 private static native int		native_getSeriesDatapointFillStyle(int nativeChart, int nCol, int nRow);
	 private static native int		native_getSeriesDatapointFillPattern(int nativeChart, int nCol, int nRow);
	 private static native void		native_setSeriesDatapointFillColor(int nativeChart, int nCol, int nRow, int nColor);
	 private static native int		native_getSeriesDatapointFillColor(int nativeChart, int nCol, int nRow);
	 private static native void		native_setSeriesDatapointFillPatternColor(int nativeChart, int nCol, int nRow, int nColor);
	 private static native int		native_getSeriesDatapointFillPatternColor(int nativeChart, int nCol, int nRow);

	 private static native void		native_setSeriesDatapointEdgeStyle(int nativeChart, int nCol, int nRow, int nStyle);
	 private static native int		native_getSeriesDatapointEdgeStyle(int nativeChart, int nCol, int nRow);
	 private static native void		native_setSeriesDatapointEdgeWidth(int nativeChart, int nCol, int nRow, int nWidth);
	 private static native int		native_getSeriesDatapointEdgeWidth(int nativeChart, int nCol, int nRow);
	 private static native void		native_setSeriesDatapointEdgeColor(int nativeChart, int nCol, int nRow, int nColor);
	 private static native int		native_getSeriesDatapointEdgeColor(int nativeChart, int nCol, int nRow);

	    
	 private static native Value	native_getValue(int nativeChart, int row, int column);
	 private static native void		native_setValue(int nativeChart, int row, int column, Value value);
	 private static native String	native_getRowLabel(int nativeChart, int row, int column);
	 private static native void		native_setRowLabel(int nativeChart, int row, int column, String label);
	 private static native String	native_getColumnLabel(int nativeChart, int row, int column);
	 private static native void		native_setColumnLabel(int nativeChart, int row, int column, String label);
	 private static native void		native_setSourceData(int nativeChart, GridData gridData, int plotBy);
	 private static native void		native_setFont(int nativeChart, String strFont, int size);
	 private static native boolean	native_isAnimation(int nativeChart);
	 private static native boolean	native_isDataQueue(int nativeChart);
	 private static native int		native_getAnimationStatus(int nativeChart);
	 private static native void		native_beginAnimation(int nativeChart, boolean loop);
	 private static native void		native_endAnimation(int nativeChart);
	 private static native void		native_addFallbackFontPath(int nativeChart, String strFontPath);
	 private static native void		native_removeFallbackFontPath(int nativeChart, String strFontPath);
	 private static native void		native_setTitle(int nativeChart, String strTitle);
	 private static native void		native_setSeriesType(int nativeChart, int nSeries, int nType);
	 private static native boolean	native_pushData(int nativeChart, GridData gridData, int nCanvasWidth, int nCanvasHeight, int nDuration);
	 private static native void		native_setYAxisMaximum(int nativeChart, boolean bAllowMaximum, double dMax);
	 private static native void		native_setDataSeriesInRow(int nativeChart, int nPlotBy);
	 
	 public native float	getRotation();
	 public native void		setRotation(float degree);
	 public native float	getElevation();
	 public native void		setElevation(float degree);
	 public native float	getPerspective();
	 public native void		setPerspective(float degree);
	 public native int		getChartAlpha();
	 public native void		setChartAlpha(int alpha);
}
