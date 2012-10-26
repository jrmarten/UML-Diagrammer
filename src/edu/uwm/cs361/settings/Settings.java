package edu.uwm.cs361.settings;

import java.io.File;
import java.util.HashMap;

import edu.uwm.cs361.Util;

public class Settings
{
	private static Settings glob;
	
	private HashMap<String, String> props = new HashMap<String, String>();
	
	
	public void set ( String key, String val )
	{
		props.put(key, val);
	}
	
	public void set ( String key, int val )
	{
		props.put(key, "" + val);
	}
	
	public void set ( String key, boolean val )
	{
		props.put( key, "" + val);
	}
	
	public String getString ( String key, String defaultVal )
	{
		String val = props.get(key);
		return (val == null ) ? defaultVal : val;
	}
	
	public int getInt ( String key, int defaultVal )
	{
		String dat = props.get( key );
		int val = 0;
		if ( dat == null ) val = defaultVal;
		else val = Integer.parseInt( dat );
		return val;
	}
	
	public boolean getBool ( String key, boolean defaultVal )
	{
		String dat = props.get( key );
		boolean val = false;
		if ( dat == null ) val = defaultVal;
		else val = Boolean.parseBoolean(dat);
		return val;
	}
	
	
	public static Settings getGlobal()
	{
		return glob;
	}
	
	static
	{
		getSettings();
	}
	
	private static final String home = System.getProperty("user.home");
	private static final String sep = System.getProperty("file.seperator");
	private static final String os = System.getProperty("os.name");
	
	private static void getSettings ( )
	{
		Util.dprint ( "Retreiving settings on " + os );
		
		File pref = new File ( getProgDir() + sep + "perferences.txt" );
		glob = new Settings ();
		
		if ( !pref.exists() )
			{
				setDefaultPreferences();
			}
		
		
	}
	
	public static String getProgDir ( )
	{
		return home + sep + ".uml-diagrammer";
	}
	
	private static void setDefaultPreferences()
	{
		
	}
}
