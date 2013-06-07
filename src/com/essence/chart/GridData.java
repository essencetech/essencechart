package com.essence.chart;

public class GridData {
	public static final int GRIDDATA_DAFAULT_ROW = 5;
	public static final int GRIDDATA_DEFAULT_COLUMN = 5;
	
	private	Row[]		m_Rows = null;
	private	int			m_nRows = 0;
	private	int			m_nColumns = 0;
	
	public GridData()
	{
		setRange(GRIDDATA_DAFAULT_ROW, GRIDDATA_DEFAULT_COLUMN);
	}
	
	public GridData(int rows, int columns)
	{
		setRange(rows, columns);
	}
	
	public void clear()
	{
		if (m_Rows != null)
		{
			for (int i = 0; i < m_nRows; i++)
			{
				m_Rows[i] = null;
			}
		}
		
		m_nRows = 0;
		m_nColumns = 0;
		m_Rows = null;
	}
	
	public int	getRows()
	{
		return m_nRows;
	}
	
	public int	getColumns()
	{
		return m_nColumns;
	}
	
	public void setRange(int rows, int columns)
	{
		clear();

	    m_nRows = rows;
	    m_nColumns = columns;

	    if (m_nRows > 0 && m_nColumns > 0)
	    {
	        m_Rows = new Row[m_nRows];
	        for (int i = 0; i < m_nRows; i++)
	        {
	        	m_Rows[i] = new Row(m_nColumns);
//	            m_Rows[i].setSize(m_nColumns);
	        }
	    }
	}
	
	public Cell getCell(int row, int column)
	{
		if (m_Rows == null || row < 0 || row >= m_nRows || column < 0 || column >= m_nColumns)
		{
			return null;
		}
		
		return m_Rows[row].getCell(column);
	}
	
	public Value getValue(int row, int column)
	{
		if (m_Rows == null || row < 0 || row >= m_nRows || column < 0 || column >= m_nColumns)
		{
			return null;
		}
		
		return (m_Rows[row].getCell(column)).getValue();
	}
	
	public boolean setCell(int row, int column, double dValue)
	{
		if (m_Rows == null || row < 0 || row >= m_nRows || column < 0 || column >= m_nColumns)
		{
			return false;
		}
		
		m_Rows[row].setCell(column, dValue);
		
		return true;
	}
	
	public boolean setCell(int row, int column, String strValue)
	{
		if (m_Rows == null || row < 0 || row >= m_nRows || column < 0 || column >= m_nColumns)
		{
			return false;
		}
		
		m_Rows[row].setCell(column, strValue);
		
		return true;
	}
}
