package edu.uwm.cs361.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import edu.uwm.cs361.Util;

public class Settings
{
	private static final String ENTRY_DELIM = ";";
	private static final String KEYVAL_DELIM = ":";
	
	protected HashMap<String, String> props = new HashMap<String, String>();
	
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
		if ( dat == null || dat.length() == 0 ) return defaultVal; 
		
		int base = 0;
		if ( dat.charAt( 0 ) == '0' )
			{
			if ( dat.length() > 2 && dat.charAt(1) == 'x' )
				{
					base = 16;
					dat = dat.substring( 2 );
				}
			else
				{
					base = 8;
					dat = dat.substring( 0 );
				}
			}
		
		int val = 0;
		if ( dat == null ) val = defaultVal;
		else val = Integer.parseInt( dat, base );
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
	
	//operating dependent code

	
	public static Settings getSettings ( File f )
	{
		if ( !f.exists ( ) )
			{
				Util.dprint ( "Failed to read preferences: " + f.getAbsolutePath() );
			}
		File pref = f;
		Settings glob = new Settings ();
		
		Util.dprint( "Reading file: " + pref.getAbsolutePath() );
		
		try
			{
				Scanner in = new Scanner ( pref );
				String input;
				
				while ( in.hasNext() )
					{
						input = in.nextLine();
						if ( input.contains( "#" ) ) input.substring( input.indexOf( '#' ) ); 
						
						String[] entries = input.split(ENTRY_DELIM);
						
						for ( String entry : entries )
							{
								entry = entry.trim();
								
								String[] keyval = entry.split(KEYVAL_DELIM);
								
								if ( keyval.length < 2 ) continue;
								if ( keyval[0].trim().equals("") || 
										 keyval[1].trim().equals("") ) continue;
								glob.set( keyval[0].trim(), keyval[1].trim());
							}
					}
			} catch (FileNotFoundException e)
			{
				Util.dprint( "File Not Found" );
			}
		
		return glob;
	}
}
