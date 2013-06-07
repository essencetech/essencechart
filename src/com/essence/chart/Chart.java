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
	
	//Data Queue Animation value
	public final int 			_DMA_DURATION	= 500;
	public final int			_DMA_INTERVAL	= 1000;
	
	static final String			DEFAULT_FONT_NAME = "Droid Sans";
	static final int			DEFAULT_FONT_SIZE = 12;
	static final int			DEFAULT_CHART_WIDTH = 10;
	static final int			DEFAULT_CHART_HEIGHT = 10;
	
	// 
	static public final int		Chart_Type_Clustered_Column 						= 0x1111;	// 
	static public final int		Chart_Type_Stacked_Column	 						= 0x1112;	// 
	static public final int		Chart_Type_100percent_Stacked_Column	 			= 0x1113;	// 
	static public final int		Chart_Type_3D_Column	 							= 0x1121;	// 
	static public final int		Chart_Type_Stacked_Column_in_3D 					= 0x1122;	// 
	static public final int		Chart_Type_100percent_Stacked_Column_in_3D 			= 0x1123;	// 
	
	// 
	static public final int		Chart_Type_Line			 							= 0x1211;	// 
	static public final int		Chart_Type_Stacked_Line	 							= 0x1212;	// 
	static public final int		Chart_Type_Line_with_Markets			 			= 0x1213;	// 
	static public final int		Chart_Type_3D_Line		 							= 0x1221;	// 
	
	// 
	static public final int		Chart_Type_Pie			 							= 0x1311;	// 
	static public final int		Chart_Type_Exploded_Pie	 							= 0x1312;	// 
	static public final int		Chart_Type_3D_Pie		 							= 0x1321;	// 
	static public final int		Chart_Type_Exploded_pie_in_3D			 			= 0x1322;	// 
	
	// 
	static public final int		Chart_Type_Clustered_Bar 							= 0x1411;	// 
	static public final int		Chart_Type_Stacked_Bar	 							= 0x1412;	// 
	static public final int		Chart_Type_100percent_Stacked_Bar		 			= 0x1413;	// 
	static public final int		Chart_Type_Clustered_Bar_in_3D			 			= 0x1421;	// 
	static public final int		Chart_Type_Stacked_Bar_in_3D			 			= 0x1422;	// 
	static public final int		Chart_Type_100percent_Stacked_Bar_in_3D	 			= 0x1423;	// 
	
	// 
	static public final int		Chart_Type_Area			 							= 0x1511;	// 
	static public final int		Chart_Type_100percent_Stacked_Area		 			= 0x1512;	// 
	static public final int		Chart_Type_3D_Area		 							= 0x1521;	// 
	static public final int		Chart_Type_100percent_Stacked_Area_in_3D 			= 0x1522;	// 
	
	// 
	static public final int		Chart_Type_Scatter_with_only_Markers	 			= 0x1611;	// 
	static public final int		Chart_Type_Scatter_with_Straight_Lines_and_Markers	= 0x1612;	// 
	static public final int		Chart_Type_Scatter_with_Straight_Lines	 			= 0x1613;	// 
	
	// 
	static public final int		Chart_Type_High_Low_Close 							= 0x1711;	// 
	static public final int		Chart_Type_Open_High_Low_Close						= 0x1712;	// 
	
	// 
	static public final int		Chart_Type_Contour		 							= 0x1811;	// 
	static public final int		Chart_Type_3D_Surface	 							= 0x1821;	// 
	
	// 
	static public final int		Chart_Type_3D_Dougnut	 							= 0x1921;	// 
	static public final int		Chart_Type_Exploded_Dougnut_in_3D		 			= 0x1922;	// 
	
	// 
	static public final int		Chart_Type_Bubble		 							= 0x1a11;	// 
	
	// 
	static public final int		Chart_Type_Radar		 							= 0x1b11;	// 
	static public final int		Chart_Type_Radar_with_Markers			 			= 0x1b12;	// 
	
	// 
	static public final int		Chart_Type_Combine		 							= 0x1c11;	// 
	static public final int		Chart_Type_3D_Combine	 							= 0x1c21;	// 
	
	// 
	static public final int		Chart_Type_Unknown		 							= 0xffff;	// 

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
            // Parent has told us how big to be. So be it.
        	m_nChartWidth = widthSize;
        }
        else {
        	m_nChartWidth = DEFAULT_CHART_WIDTH;
        }
        
        if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST) {
            // Parent has told us how big to be. So be it.
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
	 
	 // Chart Interface
	 
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
			 //data first
		 
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
	
	 // native privat static interface
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
	 
	 // native public interface
	 public native float	getRotation();
	 public native void		setRotation(float degree);
	 public native float	getElevation();
	 public native void		setElevation(float degree);
	 public native float	getPerspective();
	 public native void		setPerspective(float degree);
	 public native int		getChartAlpha();
	 public native void		setChartAlpha(int alpha);
}
