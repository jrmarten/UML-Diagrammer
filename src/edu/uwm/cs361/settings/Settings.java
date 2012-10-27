package edu.uwm.cs361.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import edu.uwm.cs361.Util;

public class Settings
{
	private static Settings glob;
	private static final String ENTRY_DELIM = ";";
	private static final String KEYVAL_DELIM = ":";
	
	private HashMap<String, String> props = new HashMap<String, String>();
	
	//******************************************************************************
	
	//instances methods
	
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
	
	
	//***********************************************************
	
	//gets the Global settings
	
	public static Settings getGlobal()
	{
		if ( glob == null ) getSettings ( );
		return glob;
	}
	
	
	//***********************************************************
	
	//operating dependent code
	
	private static String home = System.getProperty("user.home");
	private static String sep = System.getProperty("file.separator");
	private static String os = System.getProperty("os.name");
	
	
	
	private static void getSettings ( )
	{
		Util.dprint ( "Retreiving settings on " + os );
		
		File pref = new File ( getProgDir() + "perferences.txt" );
		glob = new Settings ();
		
		if ( !pref.exists() ) setDefaultPreferences();
		
		Util.dprint( "Reading file: " + pref.getAbsolutePath() );
		
		try
			{
				Scanner in = new Scanner ( pref );
				String input;
				
				while ( in.hasNext() )
					{
						input = in.nextLine();
						
						String[] entries = input.split(ENTRY_DELIM);
						
						for ( String entry : entries )
							{
								entry = entry.trim();
								
								String[] keyval = entry.split(KEYVAL_DELIM);
								
								if ( keyval.length < 2 ) continue;
								if ( isEmpty(keyval[0]) || isEmpty ( keyval[1] ) ) continue;
								glob.set( keyval[0].trim(), keyval[1].trim());
							}
					}
			} catch (FileNotFoundException e)
			{
				Util.dprint( "File Not Found" );
			}
	}
	
	private static boolean isEmpty ( String x )
	{
		return x.trim().equals("");
	}
	
	public static String getProgDir ( )
	{
		String hide = (os.equalsIgnoreCase("linux") || os.equalsIgnoreCase("unix"))? ".":"";
		return home + sep + hide + "uml-diagrammer" + sep;
	}
	
	private static void setDefaultPreferences()
	{
		File prefs = new File ( getProgDir() + "perferences.txt" );
		File progDir = new File ( getProgDir() );
		if ( !progDir.exists() ) progDir.mkdir();
		
		Util.dprint("attempting to write to " + prefs.getAbsolutePath());
		try
			{
				PrintWriter out = new PrintWriter ( prefs );
				
				for ( String[] keyval : defaults )
					{
						String entry = keyval[0] + " : " + keyval[1] + ";";
						Util.dprint ( entry );
						out.println ( entry ); 
					}
				
				out.close();
			} catch (FileNotFoundException e) { Util.dprint( "Can't find file for writing" ); }
	}
	
	
	//*********************************************************
	
	//defaults if no settings file on computer
	private static String[][] defaults = 
		{
				{"templateDir", getProgDir() + "Templates" + sep }	
		};
}
