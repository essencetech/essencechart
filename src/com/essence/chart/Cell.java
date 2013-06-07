package com.essence.chart;

public class Cell {
	protected Value	m_Value = null; 
	
	public Cell()
	{
		reset();
	}
	
	public Cell(String strValue)
	{
		reset();
		m_Value = new Value();
		
		setValue(strValue);
	}
	
	public Cell(double dValue)
	{
		reset();
		m_Value = new Value();
		
		setValue(dValue);
	}
	
	protected void reset()
	{
		m_Value = null;
	}
	
	public int getType()
	{
		return m_Value.m_Type;
	}
	
	public boolean isString()
	{
		return m_Value.isString();
	}
	
	public boolean isDouble()
	{
		return m_Value.isDouble();
	}
	
	public Value getValue()
	{
		return m_Value;
	}
	
	public String getValueString()
	{
		return m_Value.getValueString();
	}
	
	public double getValueDouble()
	{
		return m_Value.getValueDouble();
	}
	
	public void setValue(String strValue)
	{
		if (m_Value == null)
		{
			m_Value = new Value(strValue);
		}
		else
		{
			m_Value.setValue(strValue);
		}
	}
	
	public void setValue(double dValue)
	{
		if (m_Value == null)
		{
			m_Value = new Value(dValue);
		}
		else
		{
			m_Value.setValue(dValue);
		}
	}
	
	public void setValue(Value value)
	{
		if (m_Value == null)
		{
			m_Value = new Value(value);
		}
		else
		{
			m_Value.setValue(value);
		}
	}
}
