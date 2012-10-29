package edu.uwm.cs361.settings;

import java.io.File;
import java.util.LinkedList;

public class Style extends Settings
{
	private String selector;
	
	public Style ( String name )
	{
		selector = name;
	}
	
	public String getName( )
	{
		return selector;
	}
	
	
	public static Style getStyleObj ( String identifier )
	{
		
		
		return null;
	}
	
	
	private static LinkedList<Style> styleMods = new LinkedList<Style> ( );
	public static Style get ( String selector )
	{
		for ( Style s : styleMods )
			{
				if ( s.getName().equals(selector) )
						return s;
			}
		return null;
	}
	
	static
	{
		init();
	}
	
	private static void init ( )
	{
		Settings pref = Settings.getGlobal();
		String stylesheet = pref.getString( "StyleSheet", "File not found");
		File cssfile = new File ( stylesheet );
		
		if ( stylesheet.equals( "File not found") || !cssfile.exists() ) return;
		
		
		//more code to extract
		// information in css fashion.
	}
}
