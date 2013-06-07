package com.essence.chart;

public class ChartLibrary {
	final static private String	m_strLibraryName = "essence.chart";
	final static private String	m_strLibraryVersion = "0.7.9";
	
	static {
        System.loadLibrary(m_strLibraryName + "." + m_strLibraryVersion);
    }
	
	public ChartLibrary()
	{
		
	}
	
	public String getVersion() {
		return m_strLibraryVersion;
	}
}
