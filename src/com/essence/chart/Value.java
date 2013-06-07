package com.essence.chart;

public class Value {
	public static final int VALUE_TYPE_UNKNOWN = 0;
	public static final int VALUE_TYPE_STRING = 1;
	public static final int VALUE_TYPE_DOUBLE = 2;
	
	protected	int		m_Type;
	protected	String	m_strValue;
	protected	double	m_dValue;
	
	public Value()
	{
		reset();
	}
	
	public Value(String strValue)
	{
		setValue(strValue);
	}
	
	public Value(double dValue)
	{
		setValue(dValue);
	}
	
	public Value(Value value)
	{
		setValue(value);
	}
	
	public void reset()
	{
		m_Type = VALUE_TYPE_UNKNOWN;
		m_strValue = "";
		m_dValue = 0.0;
	}
	
	protected void copyOf(Value value)
	{
		m_Type = value.m_Type;
		m_strValue = value.m_strValue;
		m_dValue = value.m_dValue;
	}
	
	public int getType()
	{
		return m_Type;
	}
	
	public boolean isString()
	{
		return m_Type == VALUE_TYPE_STRING ? true : false;
	}
	
	public boolean isDouble()
	{
		return m_Type == VALUE_TYPE_DOUBLE ? true : false;
	}
	
	public String getValueString()
	{
		return m_strValue;
	}
	
	public double getValueDouble()
	{
		return m_dValue;
	}
	
	public void setValue(String strValue)
	{
		reset();
		
		m_Type = VALUE_TYPE_STRING;
		m_strValue = strValue;
	}
	
	public void setValue(double dValue)
	{
		reset();
		
		m_Type = VALUE_TYPE_DOUBLE;
		m_dValue = dValue;
	}
	
	public void setValue(Value value)
	{
		reset();
		
		copyOf(value);
	}
}
