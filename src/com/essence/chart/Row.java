package com.essence.chart;

public class Row {

	private	Cell[]		m_Cells = null;
	private	int			m_nSize = 0;
	
	public Row()
	{
		m_nSize = 0;
		m_Cells = null;
	}
	
	public Row(int size)
	{
		setSize(size);
	}
	
	public int	getSize()
	{
		return m_nSize;
	}
	
	public void setSize(int size)
	{
		for(int i = 0; i < m_nSize; i++)
		{
			m_Cells[i] = null;
		}
		m_Cells = null;
		
		m_nSize = size;
		if (m_nSize > 0)
		{
			m_Cells = new Cell[m_nSize];
		}
	}
	
	public Cell getCell(int column)
	{
		if (m_Cells == null || column < 0 || column >= m_nSize)
		{
			return null;
		}
		
		return m_Cells[column];
	}
	
	public boolean setCell(int column, double dValue)
	{
		if (m_Cells == null || column < 0 || column >= m_nSize)
		{
			return false;
		}
		
		Cell	cell = m_Cells[column];
		if (cell == null)
		{
			m_Cells[column] = new Cell(dValue);
		}
		else
		{
			m_Cells[column].setValue(dValue);
		}
		
		return true;
	}
	
	public boolean setCell(int column, String strValue)
	{
		if (m_Cells == null || column < 0 || column >= m_nSize)
		{
			return false;
		}
		
		Cell	cell = m_Cells[column];
		if (cell == null)
		{
			m_Cells[column] = new Cell(strValue);
		}
		else
		{
			m_Cells[column].setValue(strValue);
		}
		
		return true;
	}
}
